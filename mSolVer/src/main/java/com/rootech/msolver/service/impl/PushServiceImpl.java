package com.rootech.msolver.service.impl;

import org.springframework.stereotype.Service;

import com.rootech.msolver.common.mqtt.Mqtt;
import com.rootech.msolver.common.mqtt.MqttToMqtt;
import com.rootech.msolver.service.PushService;

@Service("PushService")
public class PushServiceImpl implements PushService{
	String MqttServer1 = "tcp://10.10.19.28:1883";
	String MqttServer2 = "tcp://10.10.19.28:1883";
	String client_id = "PushService";
	String username = "";
	String passwd = "";
	String topic = "/test";
	String msg = "Input your Message";
	
	@Override
	public void initPush(String userId) {

//		Mqtt ReadFromOtherMQTT = new Mqtt(MqttServer2, userId, username, passwd);
//		ReadFromOtherMQTT.init(topic);
//
//		sleep(1000);
//
//		ReadFromOtherMQTT.subscribe(0);
//		
//		sleep(1000);
		
	}

	static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void publishMsg(String msg) {
		MqttToMqtt ReadAndSend = new MqttToMqtt(MqttServer1, client_id, username, passwd, new Mqtt(MqttServer2, client_id, username, passwd), msg);
		ReadAndSend.init();
		
	}
}
