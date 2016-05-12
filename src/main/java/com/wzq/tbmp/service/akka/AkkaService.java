package com.wzq.tbmp.service.akka;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import scala.concurrent.duration.Duration;
import akka.actor.ActorSystem;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/** 
 * @author sloanwu 
 * @version 创建时间：2014年7月31日 上午11:21:00 
 * 类说明
 * 
 */

public class AkkaService {
	private static final Logger logger = Logger.getLogger(AkkaService.class);
	
	private static AkkaService instance = new AkkaService();

	private ActorSystem actorSystem;

	public static AkkaService getInstance(){
		return instance;
	}
	
	public void init() {
		logger.info("Start ActorSystem...");
		actorSystem = ActorSystem.create("BoiServer", createConfig());
		logger.info("Start ActorSystem...OK");
	}
	
	private Config createConfig() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("akka.loglevel", "ERROR");
		map.put("akka.stdout-loglevel", "ERROR");
		
		//forkjoinpool默认线程数 max(min(cpu线程数 * parallelism-factor, parallelism-max), 8)
		map.put("akka.actor.default-dispatcher.fork-join-executor.parallelism-factor", "100");
		map.put("akka.actor.default-dispatcher.fork-join-executor.parallelism-max", "100");
		
		return ConfigFactory.parseMap(map);
	}

	public void dispose() {
		logger.info("Shutdown ActorSystem...");
		if (actorSystem != null) {
			actorSystem.shutdown();
			actorSystem.awaitTermination(Duration.apply(60, TimeUnit.SECONDS));
		}
		logger.info("Shutdown ActorSystem...OK");
	}
	
	public ActorSystem getActorSystem(){
		return actorSystem;
	}
	
}
