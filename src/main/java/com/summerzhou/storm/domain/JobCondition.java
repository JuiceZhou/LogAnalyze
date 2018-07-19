package com.summerzhou.storm.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * JobCondition封装类，对应表log_analyze_job_condition
 * CREATE TABLE `log_analyze_job_condition` (
 *   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
 *   `jobId` int(11) NOT NULL COMMENT '任务编号',
 *   `field` varchar(50) CHARACTER SET latin1 NOT NULL COMMENT '用来比较的字段名称',
 *   `value` varchar(250) CHARACTER SET latin1 NOT NULL COMMENT '参与比较的字段值',
 *   `compare` int(1) NOT NULL COMMENT '1:包含 2:等于',
 *   `createUser` varchar(50) CHARACTER SET latin1 NOT NULL COMMENT '创建用户',
 *   `updateUser` varchar(50) CHARACTER SET latin1 NOT NULL COMMENT '修改用户',
 *   `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
 *   `updateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
 *   KEY `id` (`id`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
 */
public class JobCondition implements Serializable {
    private String id;
    private String jobId;//所属的job
    private String field;//用来比较的字段名称，如 ip/os
    private String value;//参与比较的字段值 如 192.168.25.2
    private Integer compare;//'1:包含 2:等于'
    private String createUser;
    private String updateUser;
    private Date createDate;
    private Date updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getCompare() {
        return compare;
    }

    public void setCompare(Integer compare) {
        this.compare = compare;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "JobCondition{" +
                "id='" + id + '\'' +
                ", jobId='" + jobId + '\'' +
                ", field='" + field + '\'' +
                ", value='" + value + '\'' +
                ", compare='" + compare + '\'' +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
