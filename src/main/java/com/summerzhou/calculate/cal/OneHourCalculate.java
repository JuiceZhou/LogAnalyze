package com.summerzhou.calculate.cal;

import clojure.lang.Compiler;
import com.summerzhou.calculate.domain.RedisRecord;
import com.summerzhou.calculate.utils.CalculateUtils;
import com.summerzhou.storm.dao.LogAnalyzeDao;
import com.summerzhou.storm.utils.DataUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 每小时获得一次增量数据
 */
public class OneHourCalculate implements Runnable {
    private static Logger logger = Logger.getLogger(OneHourCalculate.class);
    @Override
    public void run() {
        Calendar calendar = Calendar.getInstance();
        int minute = calendar.get(Calendar.MINUTE);
        //当分钟数为59时，进入数据库求增量数据和
        if(minute == 59){
            //获取当前小时数
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            String startTime = DataUtils.getYMDDate()+" "+hour+":00:00";
            String endTime = DataUtils.getYMDDate()+" "+hour+":59:59";
            List<RedisRecord> redisRecordList = new ArrayList<>();
            redisRecordList = CalculateUtils.getRecordForTime(startTime,endTime);
            //保存到dataBase
            new LogAnalyzeDao().save2DataBase(redisRecordList,"hour");
        }
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.MINUTE));
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String startTime = DataUtils.getYMDDate()+" "+hour+":00:00";
        String endTime = DataUtils.getYMDDate()+" "+hour+":59:59";
        System.out.println(startTime);
        System.out.println(endTime);
    }
}
