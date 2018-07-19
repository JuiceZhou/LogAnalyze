package com.summerzhou.storm.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Job封装类 , 对应表log_analyze_job
 */
public class LogAnalyzeJob implements Serializable {
    private String jobId;//Jobid
    private String jobName;//自定义的job名
    private Integer jobType;//'1:浏览日志、2:点击日志、3:搜索日志、4:购买日志'
    private String businessId;//所属业务线
    private Integer status;//0:下线 、1:在线'
    private String createUser;
    private String updateUser;
    private Date createDate;
    private Date updataDate;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdataDate() {
        return updataDate;
    }

    public void setUpdataDate(Date updataDate) {
        this.updataDate = updataDate;
    }

    @Override
    public String toString() {
        return "LogAnalyzeJob{" +
                "jobId='" + jobId + '\'' +
                ", jobName='" + jobName + '\'' +
                ", jobType='" + jobType + '\'' +
                ", businessId='" + businessId + '\'' +
                ", status='" + status + '\'' +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", createDate=" + createDate +
                ", updataDate=" + updataDate +
                '}';
    }
}
