package com.wzq.tbmp.service.asyn;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.ActorRefRoutee;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.SmallestMailboxRoutingLogic;

/**
 * @author sloanwu
 * @version 创建时间：2014年7月13日 下午9:25:16 类说明 JMS负载路由Actor
 */

public class AsynTaskBalanceRouteActor extends UntypedActor {
	private static Logger logger = Logger.getLogger(AsynTaskBalanceRouteActor.class);

	private List<Routee> routees;
	private Router router;
	{
		routees = new ArrayList<Routee>();
		for (int index = 0; index < 10; index++) {
			ActorRef r = getContext().actorOf(Props.create(AsynTaskActor.class));
			getContext().watch(r);
			routees.add(new ActorRefRoutee(r));
		}
		router = new Router(new SmallestMailboxRoutingLogic(), routees);
	}

	@Override
	public void onReceive(Object m) throws Exception {
		if(m instanceof AsynTask){
			router.route(m, getSender());
		} else {
			logger.debug("can't handle msg: " + m);
			unhandled(m);
		}
	}

}
