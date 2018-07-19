package com.summerzhou.calculate.cal;

import clojure.lang.Compiler;
import com.summerzhou.calculate.domain.RedisRecord;
import com.summerzhou.calculate.utils.CalculateUtils;
import com.summerzhou.storm.dao.LogAnalyzeDao;
import com.summerzhou.storm.utils.DataUtils;

import java.util.Calendar;
import java.util.List;

/**
 * 计算每天的增量
 */
public class OneDayCalculate implements Runnable {
    @Override
    public void run() {
        Calendar calendar= Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        if(hour == 23 && minute == 59 ){
            //进行计算
            String startTime = DataUtils.getYMDDate() + " 00:00:00";
            String endTime = DataUtils.getYMDDate() + "23:59:59";
            List<RedisRecord> recordForTime = CalculateUtils.getRecordForTime(startTime, endTime);
            new LogAnalyzeDao().save2DataBase(recordForTime,"day");
        }
    }
}
