package com.summerzhou.calculate.domain;

import java.util.Date;

/**
 * 用来记录从redis中获取的数据
 */
public class RedisRecord {
    private String jobName;
    private String jobId;
    private Integer pv;
    private Long uv;
    private String date;



    public RedisRecord() {

    }

    public RedisRecord(String jobName, String jobId, Integer pv, Long uv, String date) {
        this.jobName = jobName;
        this.jobId = jobId;
        this.pv = pv;
        this.uv = uv;
        this.date = date;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public Long getUv() {
        return uv;
    }

    public void setUv(Long uv) {
        this.uv = uv;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "RedisRecord{" +
                "jobName='" + jobName + '\'' +
                ", jobId='" + jobId + '\'' +
                ", pv=" + pv +
                ", uv=" + uv +
                ", date=" + date +
                '}';
    }
}
