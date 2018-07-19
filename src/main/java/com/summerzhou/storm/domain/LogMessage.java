package com.summerzhou.storm.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志消息类
 */
public class LogMessage implements Serializable {
    private Integer type;//1:浏览日志、2:点击日志、3:搜索日志、4:购买日志'
    private String hrefTag;//浏览标签
    private String hrefContent;//标签内容
    private String referUrl;//来源地址
    private String requestUrl;//请求网址
    private Date clickTime;//点击时间
    private String browserName;//浏览器名称
    private String browserVersion;//浏览器版本
    private String language;//浏览器语言
    private String os;//操作系统
    private String screen;//屏幕尺寸
    private String coordinate;//鼠标坐标
    private String username;//用户名
    private String systemId;//系统标号

    public LogMessage(Integer type,String referUrl,String requestUrl,String username){
        this.type = type;
        this.referUrl = referUrl;
        this.requestUrl = requestUrl;
        this.username = username;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getHrefTag() {
        return hrefTag;
    }

    public void setHrefTag(String hrefTag) {
        this.hrefTag = hrefTag;
    }

    public String getHrefContent() {
        return hrefContent;
    }

    public void setHrefContent(String hrefContent) {
        this.hrefContent = hrefContent;
    }

    public String getReferUrl() {
        return referUrl;
    }

    public void setReferUrl(String referUrl) {
        this.referUrl = referUrl;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Date getClickTime() {
        return clickTime;
    }

    public void setClickTime(Date clickTime) {
        this.clickTime = clickTime;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Override
    public String toString() {
        return "LogMessage{" +
                "type=" + type +
                ", hrefTag='" + hrefTag + '\'' +
                ", hrefContent='" + hrefContent + '\'' +
                ", referUrl='" + referUrl + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", clickTime=" + clickTime +
                ", browserName='" + browserName + '\'' +
                ", browserVersion='" + browserVersion + '\'' +
                ", language='" + language + '\'' +
                ", os='" + os + '\'' +
                ", screen='" + screen + '\'' +
                ", coordinate='" + coordinate + '\'' +
                ", username='" + username + '\'' +
                ", systemId='" + systemId + '\'' +
                '}';
    }
}
