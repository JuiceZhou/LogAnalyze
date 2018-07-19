package com.summerzhou.storm.utils;

import com.google.gson.Gson;
import com.summerzhou.storm.constant.Constants;
import com.summerzhou.storm.dao.LogAnalyzeDao;
import com.summerzhou.storm.domain.JobCondition;
import com.summerzhou.storm.domain.LogAnalyzeJob;
import com.summerzhou.storm.domain.LogMessage;
import org.apache.log4j.Logger;
import redis.clients.jedis.ShardedJedis;

import java.util.*;

/**
 * 主要方法类
 */
public class LogAnalyzeUtils {
    private static Logger logger = Logger.getLogger(LogAnalyzeUtils.class);
    //此map用于储存<job类型,和对应的List<Job>>
    private static Map<Integer,List<LogAnalyzeJob>> jobMap;
    //此map用来储存<jobId,和对应的Job条件List<JobCondition>>
    private static Map<String,List<JobCondition>> jobConditionMap;
    //锁
    private static boolean flag = false;

    static{
        reload();
    }

    /**
     * 用于加载数据库，填充两个map
     */
    private static void reload() {
        if(jobMap == null){
            loadJobMap();
        }
        if(jobConditionMap == null){
            loadConditionMap();
        }
    }

    /**
     * 加载jobConditionMap
     */
    private static void loadConditionMap() {
        jobConditionMap = new HashMap<>();
        List<JobCondition> allJobConditionsList = new LogAnalyzeDao().getAllJobConditions();
        for(JobCondition condition:allJobConditionsList){
            String jobId = condition.getJobId();
            List<JobCondition> jobConditions = jobConditionMap.get(jobId);
            if(jobConditions == null){
                jobConditions = new ArrayList<>();
            }
            jobConditions.add(condition);
            jobConditionMap.put(jobId,jobConditions);
        }
    }

    /**
     * 加载JobMap
     */
    private static void loadJobMap() {
        jobMap = new HashMap<>();
        List<LogAnalyzeJob> jobsList = new LogAnalyzeDao().getAllJobs();
        for(LogAnalyzeJob job : jobsList){
            Integer jobType = job.getJobType();
            List<LogAnalyzeJob> typeJobList = jobMap.get(jobType);
            if(typeJobList == null){
                typeJobList = new ArrayList<>();
            }
            typeJobList.add(job);
            jobMap.put(jobType,typeJobList);
        }
    }

    public static void main(String[] args) {


    }

    /**
     * 每10分钟更新一次数据库规则
     */
    public static void updateRule() {
        String time = DataUtils.getTime();
        Integer minute = Integer.valueOf(time.split(":")[1]);
        if(minute % 10 == 0){
            reloadDB();
        }else {
            flag = true;
        }
    }

    /**
     * 定时更新规则
     */
    private synchronized static void reloadDB() {
        if(flag){
            long start = System.currentTimeMillis();
            loadJobMap();
            loadConditionMap();
            flag = false;
            logger.info("更新规则完成，当前时间为："+DataUtils.getWholeDate()+",耗时："+(System.currentTimeMillis()-start));
        }
    }

    /**
     * 对json数据进行解析
     * @param line
     * @return
     */
    public static LogMessage parseLine(String line) {
        //解析json串
        LogMessage logMessage = new Gson().fromJson(line, LogMessage.class);
        return logMessage;
    }

    /**
     * 判断message的类型是否是指定的四种
     * @param message
     * @return
     */
    public static boolean isValidType(LogMessage message) {
        Integer type = message.getType();
        if(type == Constants.PURCHASE_LOG || type == Constants.SEARCH_LOG || type == Constants.CLICK_LOG
                || type == Constants.REVIRE_LOG){
            return true;
        }
        return false;
    }

    /**
     * 计算pv uv
     * pv key :  "logs_"+jobId+"_"+jobName+"_pv_"+当前日期 value = pv
     * uv key : "logs_"+jobId+"_"+jobName+"_uv_"+当前日期   value :set
     * @param logMessage
     */
    public static void calculate(LogMessage logMessage) {
        if(jobMap == null){
            reload();
        }

        //根据当前message的type获取对应的jobList
        Integer type = logMessage.getType();
        List<LogAnalyzeJob> jobList = jobMap.get(type);
        //对应每个job，都有自己的condition，然后将此logMessage和逐个condition匹配，如果全部配上，则进行计算
        for(LogAnalyzeJob job : jobList){
            //获取jobId;
            String jobId = job.getJobId();
            //获取对应的ConditionList
            List<JobCondition> jobConditions = jobConditionMap.get(jobId);
            //每个Job的标识
            boolean flag = false;
            for(JobCondition condition : jobConditions){
                //对于每一个条件，都有对应的区间，找出message中对应区间的值
                String messageValue = LogAnalyzeUtils.getFieldValue(condition.getField(),logMessage);
                //condition中field对应的value
                String conditionValue = condition.getValue();
                Integer compare = condition.getCompare();
                //compare分为等于:2和包含:1
                if(compare == 1 && messageValue.contains(conditionValue)){
                    flag = true;
                }else if(compare == 2 && messageValue.equals(conditionValue)){
                    flag = true;
                }else {
                    flag = false;
                }
                //只要message不满足一个条件就代表不符合当前job的条件，直接跳出
                if(!flag){
                    logger.info(logMessage+"不符合"+job.getJobId());
                    break;
                }
            }
            //如果当前message满足所有当前job的条件，则计算pv，uv
            if(flag){
                String pvKey = "logs_"+jobId+"_"+job.getJobName()+"_pv_"+DataUtils.getYMDDate();
                String uvKey = "logs_"+jobId+"_"+job.getJobName()+"_uv_"+DataUtils.getYMDDate();
                //存入redis
                //redis需要使用分布式连接池，单个jedis处理会报错
                ShardedJedis jedis = null;
                try {
                    jedis = MyJedisPoolUtils.getJedis();
                    jedis.incr(pvKey);
                    //uv需要出重，用set作为value，里面储存用户名
                    jedis.sadd(uvKey, logMessage.getUsername());
                    logger.info("写入redis完成：" + pvKey + ";" + uvKey);
                }finally {
                    MyJedisPoolUtils.close(jedis);
                }



            }
        }
    }

    /**
     * 获取message中对应field的值
     * @param field
     * @return
     */
    private static String getFieldValue(String field,LogMessage logMessage) {
        if(field.contains("request")){
            return logMessage.getRequestUrl();
        }
        if(field.contains("refer")){
            return logMessage.getReferUrl();
        }
        if(field.contains("os")){
            return logMessage.getOs();
        }
        if(field.contains("username")){
            return logMessage.getUsername();
        }
        return null;
    }

    public static String toJson(LogMessage message) {
        return new Gson().toJson(message);
    }
}
