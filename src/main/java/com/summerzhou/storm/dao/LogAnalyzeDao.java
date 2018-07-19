package com.summerzhou.storm.dao;

import com.summerzhou.calculate.domain.RedisRecord;
import com.summerzhou.storm.domain.JobCondition;
import com.summerzhou.storm.domain.LogAnalyzeJob;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;


import java.sql.SQLException;
import java.util.List;


/**
 * 使用dbutils作为jdbc工具
 */
public class LogAnalyzeDao {
    private static Logger logger = Logger.getLogger(LogAnalyzeDao.class);
    private QueryRunner queryRunner;
    public LogAnalyzeDao(){
        queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
    }


    /**
     * 将redis的数据存入database中，根据参数的不同分别存入分钟、小时、全天量数据
     * @param differenceList
     * @param condition
     */
    public void save2DataBase(List<RedisRecord> differenceList, String condition) {
        String table_name = "log_analyze_job_" + condition + "_append";
        String sql = "insert into " + table_name + "(jobName,jobId,pv,uv) values(?,?,?,?)";
        for(RedisRecord redisRecord:differenceList) {
            try {
                queryRunner.update(sql,redisRecord.getJobName(),redisRecord.getJobId(),redisRecord.getPv(),redisRecord.getUv());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取所有Jobs
     * @return
     */
    public List<LogAnalyzeJob> getAllJobs() {
        String sql = "select * from log_analyze_job where status = '1'";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(LogAnalyzeJob.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有status=1的job的condition
     * @return
     */
    public List<JobCondition> getAllJobConditions() {
        String sql = "SELECT c.* FROM log_analyze.log_analyze_job_condition c " +
                "left join log_analyze_job j on c.jobId = j.jobId where j.status = 1";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(JobCondition.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<RedisRecord> getRecordForTime(String startTime, String endTime) {
        String sql = "select jobName,jobId,SUM(pv) as 'pv',SUM(uv) as 'uv' from `log_analyze_job_minute_append`" +
                " c where" + " createTime between ? and ? group by jobId;";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(RedisRecord.class),startTime,endTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        LogAnalyzeDao dao = new LogAnalyzeDao();
        List<RedisRecord> recordForTime = dao.getRecordForTime("2018-07-19 20:00:00", "2018-07-19 20:59:59");
        System.out.println(recordForTime);
    }

}
