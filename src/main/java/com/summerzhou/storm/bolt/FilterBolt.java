package com.summerzhou.storm.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.summerzhou.storm.domain.LogMessage;
import com.summerzhou.storm.utils.LogAnalyzeUtils;
import org.apache.log4j.Logger;

/**
 * 上接spout，用来过滤数据，判断日志是否满足指定要求
 */
public class FilterBolt extends BaseBasicBolt {
    private static Logger logger = Logger.getLogger(FilterBolt.class);
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {

        //获取数据
        String line = input.getString(0);
        //解析数据
        LogMessage message = LogAnalyzeUtils.parseLine(line);
        if(message == null || !LogAnalyzeUtils.isValidType(message)){
            return;
        }
        //满足条件，发送到下一个Bolt
        collector.emit(new Values(LogAnalyzeUtils.toJson(message)));

        //定时更新数据库规则
        LogAnalyzeUtils.updateRule();
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("log"));
    }
}
