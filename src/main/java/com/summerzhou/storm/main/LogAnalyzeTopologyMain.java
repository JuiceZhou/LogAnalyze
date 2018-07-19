package com.summerzhou.storm.main;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import com.summerzhou.storm.bolt.CalculateBolt;
import com.summerzhou.storm.bolt.FilterBolt;
import com.summerzhou.storm.spout.RandomSpout;

public class LogAnalyzeTopologyMain {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("randomSpout",new RandomSpout(),2);
        builder.setBolt("filterBolt",new FilterBolt(),2).shuffleGrouping("randomSpout");
        builder.setBolt("CalculateBolt", new CalculateBolt(),2).shuffleGrouping("filterBolt");

        Config config = new Config();
        config.setNumWorkers(2);

        if(args.length > 0){
            //集群
            try {
                StormSubmitter.submitTopology(args[0],config,builder.createTopology());
            } catch (AlreadyAliveException e) {
                e.printStackTrace();
            } catch (InvalidTopologyException e) {
                e.printStackTrace();
            }
        }else {
            //本地
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("loganalyze",config,builder.createTopology());
        }
    }
}
