package com.summerzhou.storm.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import com.summerzhou.storm.domain.LogMessage;
import com.summerzhou.storm.utils.LogAnalyzeUtils;
import org.apache.log4j.Logger;

/**
 * 上接已过滤过得Message，并计算pv,uv，放入redis
 */
public class CalculateBolt extends BaseBasicBolt {
    private static Logger logger = Logger.getLogger(CalculateBolt.class);
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {

        //获取数据
        String jsonMessage = (String) input.getValue(0);
        LogMessage logMessage = LogAnalyzeUtils.parseLine(jsonMessage);
        LogAnalyzeUtils.calculate(logMessage);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
