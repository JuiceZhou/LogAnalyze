package com.summerzhou.calculate.utils;

import com.summerzhou.calculate.cache.CacheData;
import com.summerzhou.calculate.domain.RedisRecord;
import com.summerzhou.storm.dao.LogAnalyzeDao;
import com.summerzhou.storm.domain.LogAnalyzeJob;
import com.summerzhou.storm.utils.DataUtils;
import com.summerzhou.storm.utils.MyJedisPoolUtils;
import org.apache.log4j.Logger;
import redis.clients.jedis.ShardedJedis;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * calculate的公用方法
 */
public class CalculateUtils {
    private static Logger logger = Logger.getLogger(CalculateUtils.class);
    /**
     * 从redis中获取当前所有job对应的uv/pv
     * pv key :  "logs_"+jobId+"_"+jobName+"_pv_"+当前日期
     * uv key : "logs_"+jobId+"_"+jobName+"_uv_"+当前日期
     * @param now_date
     * @return
     */
    public static List<RedisRecord> getNowData(String now_date) {
        //获取所有的job，每个job对应一个RedisRecord
        List<LogAnalyzeJob> allJobsList = new LogAnalyzeDao().getAllJobs();
        List<RedisRecord> nowRecordList = new ArrayList<>();
        for(LogAnalyzeJob job : allJobsList){
            String jobName = job.getJobName();
            String jobId= job.getJobId();
            String pvKey = "logs_"+jobId+"_"+jobName+"_pv_"+now_date;
            String uvKey = "logs_"+jobId+"_"+jobName+"_uv_"+now_date;
            ShardedJedis shardedJedis = MyJedisPoolUtils.getJedis();
            String pv = shardedJedis.get(pvKey);
            Long uv = shardedJedis.scard(uvKey);
            if(pv == null){
                pv = "0";
            }
            RedisRecord redisRecord = new RedisRecord(jobName,jobId,Integer.valueOf(pv.trim()),uv,now_date);
            nowRecordList.add(redisRecord);
        }
        logger.info("读取当前redis中的数据完成...");
        return nowRecordList;
    }

    public static void main(String[] args) {
        List<RedisRecord> nowData = CalculateUtils.getNowData(DataUtils.getYMDDate());
        for(RedisRecord redisRecord:nowData){
            System.out.println(redisRecord);
        }
    }

    /**
     * 计算出当前数据和缓存数据的差值
     * @param nowRecordList
     * @return
     */
    public static List<RedisRecord> getDifferenceData(List<RedisRecord> nowRecordList) {
        List<RedisRecord> differenceDataList = new ArrayList<>();
        for(RedisRecord redisRecord : nowRecordList){
            String pvKey = "logs_"+redisRecord.getJobId()+"_"+redisRecord.getJobName()+"_pv_"+redisRecord.getDate();
            String uvKey = "logs_"+redisRecord.getJobId()+"_"+redisRecord.getJobName()+"_uv_"+redisRecord.getDate();
            Integer dPv = CacheData.getDifferentPv(pvKey,redisRecord.getPv());
            Long dUv = CacheData.getDifferentUv(uvKey, redisRecord.getUv());
            RedisRecord dRecord = new RedisRecord(redisRecord.getJobName(),redisRecord.getJobId(),dPv,dUv,redisRecord.getDate());
            differenceDataList.add(dRecord);
        }
        logger.info("计算差值完成....");
        return differenceDataList;
    }

    /**
     * 计算给定时间区间内的指标
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<RedisRecord> getRecordForTime(String startTime, String endTime) {
        return new LogAnalyzeDao().getRecordForTime(startTime,endTime);
    }
}
