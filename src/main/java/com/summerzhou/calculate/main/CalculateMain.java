package com.summerzhou.calculate.main;

import com.summerzhou.calculate.cal.OneDayCalculate;
import com.summerzhou.calculate.cal.OneHourCalculate;
import com.summerzhou.calculate.cal.OneMinuteCalculate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CalculateMain {
    public static void main(String[] args) {
        //创建定时执行线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
        //每60s计算一次
        scheduledExecutorService.scheduleAtFixedRate(new OneMinuteCalculate(),0,60,TimeUnit.SECONDS);
        //每1h计算一次
        scheduledExecutorService.scheduleAtFixedRate(new OneHourCalculate(),0,60,TimeUnit.MINUTES);
        //每1天计算一次
        scheduledExecutorService.scheduleAtFixedRate(new OneDayCalculate(),0,24,TimeUnit.HOURS);
    }
}
