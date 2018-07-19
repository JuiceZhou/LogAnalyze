package com.summerzhou.storm.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.summerzhou.storm.domain.LogMessage;
import com.summerzhou.storm.utils.LogAnalyzeUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * 原为kafkaSpout，现在模拟数据输出，传输LogMessage的json串
 */
public class RandomSpout extends BaseRichSpout {
    private static Logger logger = Logger.getLogger(RandomSpout.class);
    //ack-fail机制
    private Map<String,Values> msgMap;
    private SpoutOutputCollector collector;
    //数据集
    private List<LogMessage> logsList;
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        logsList = new ArrayList<>();
        msgMap = new HashMap<>();
        logsList.add(new LogMessage(1,"http://www.bilibili.tv","http://www.bilibili.tv/product?id=1002","nancy"));
        logsList.add(new LogMessage(1,"http://www.bilibili.tv","http://www.bilibili.tv/product?id=1002","nancy1"));
        logsList.add(new LogMessage(1,"http://www.bilibili.tv","http://www.bilibili.tv/product?id=1002","nancy2"));
        logsList.add(new LogMessage(1,"http://www.bilibili.tv","http://www.bilibili.tv/product?id=1002","nancy3"));
        logsList.add(new LogMessage(1,"http://www.bilibili.tv","http://www.bilibili.tv/product?id=1002","nancy4"));
        logsList.add(new LogMessage(1,"http://www.bilibili.tv","http://www.bilibili.tv/product?id=1002","nancy5"));
        logsList.add(new LogMessage(1,"http://www.bilibili.tv","http://www.bilibili.tv/product?id=1002","nancy6"));
        logsList.add(new LogMessage(1,"http://www.bilibili.tv","http://www.bilibili.tv/product?id=1002","nancy7"));
        logsList.add(new LogMessage(1,"http://www.bilibili.tv","http://www.bilibili.tv/product?id=1002","tom"));
        logsList.add(new LogMessage(1,"http://www.baidu.com","http://www.bilibili.tv/product?id=1003","zz"));
        logsList.add(new LogMessage(1,"http://www.baidu.com","http://www.bilibili.tv/product?id=1003","zz1"));
        logsList.add(new LogMessage(1,"http://www.baidu.com","http://www.bilibili.tv/product?id=1003","zz2"));
        logsList.add(new LogMessage(1,"http://www.baidu.com","http://www.bilibili.tv/product?id=1003","zz3"));
        logsList.add(new LogMessage(1,"http://www.baidu.com","http://www.bilibili.tv/product?id=1003","zz4"));
        logsList.add(new LogMessage(1,"http://www.baidu.com","http://www.bilibili.tv/product?id=1003","zz5"));
        logsList.add(new LogMessage(1,"http://www.baidu.com","http://www.bilibili.tv/product?id=1003","zz5"));
        logsList.add(new LogMessage(2,"http://www.bilibili.tv","http://www.bilibili.tv/product?id=1003","zz1"));
        logsList.add(new LogMessage(2,"http://www.bilibili.tv","http://www.bilibili.tv/product?id=1003","zz2"));
        logsList.add(new LogMessage(2,"http://www.bilibili.tv","http://www.bilibili.tv/product?id=1003","zz3"));
        logsList.add(new LogMessage(2,"http://www.bilibili.tv","http://www.bilibili.tv/product?id=1003","zz7"));
        logsList.add(new LogMessage(2,"http://www.bilibili.tv","http://www.bilibili.tv/product?id=1003","zz0"));
    }

    @Override
    public void nextTuple() {
        int index = new Random().nextInt(logsList.size());
        LogMessage logMessage = logsList.get(index);
        Values tuple = new Values(LogAnalyzeUtils.toJson(logMessage));
        String msgId = UUID.randomUUID().toString().replace("-","");
        msgMap.put(msgId,tuple);
        collector.emit(tuple,msgId);
        //控制输出的速度
        try {
            Thread.sleep(3000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(("logmessage")));
    }

    @Override
    public void ack(Object msgId) {
        msgMap.remove(msgId);
    }

    @Override
    public void fail(Object msgId) {
        Values tuple = msgMap.get(msgId);
        collector.emit(tuple,msgId);
    }
}
