package com.summerzhou.calculate.cal;

import com.summerzhou.calculate.cache.CacheData;
import com.summerzhou.calculate.domain.RedisRecord;
import com.summerzhou.calculate.utils.CalculateUtils;
import com.summerzhou.storm.dao.LogAnalyzeDao;
import com.summerzhou.storm.utils.DataUtils;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class OneMinuteCalculate implements Runnable {
    @Override
    public void run() {
        //获取当前日期
        String now_date = DataUtils.getYMDDate();
        String hour = DataUtils.getTime().split(":")[0];
        String minute = DataUtils.getTime().split(":")[1];
        //如果到达一天的24:00，清空缓存中的数据
        if(hour == "24" && minute == "00"){
            CacheData.clearCache();
        }
        //获取当前redis中的所有数据
        List<RedisRecord> nowRecordList =  CalculateUtils.getNowData(now_date);
        //获取当前数据和缓冲数据的差值
        List<RedisRecord> differenceList = CalculateUtils.getDifferenceData(nowRecordList);
        //将差值存入指定的表中
        new LogAnalyzeDao().save2DataBase(differenceList,"minute");
    }

    public static void main(String[] args) {
        String now_date = DataUtils.getYMDDate();
        String hour = DataUtils.getTime().split(":")[0];
        String minute = DataUtils.getTime().split(":")[1];
        System.out.println(now_date+":"+hour+":"+minute);
    }
}
