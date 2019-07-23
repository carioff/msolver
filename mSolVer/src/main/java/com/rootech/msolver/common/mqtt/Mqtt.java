package com.rootech.msolver.common.mqtt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mqtt implements MqttCallback {
	
	private static final Logger logger = LoggerFactory.getLogger(Mqtt.class);
	
	private static String Broker;
	private static String Client_ID;
	private static String UserName;
	private static String Passwd;
	private static MqttAsyncClient Client;
	private static MqttMessage message;
	private static MemoryPersistence persistence;
	private static MqttConnectOptions connOpts;
	private static String topic;
	List<String> FileStrList = new ArrayList<>();
	String FileName;
	
	public Mqtt(String broker, String client_id, String username, String passwd) {
		this.Broker = broker;
		this.Client_ID = client_id;
		this.UserName = username;
		this.Passwd = passwd;
	}

	public void init(String topic) {
		this.topic = topic;
		this.persistence = new MemoryPersistence();
		try {
			Client = new MqttAsyncClient(this.Broker, this.Client_ID, this.persistence);
			Client.setCallback(this);

			connOpts = new MqttConnectOptions();
			// TO DO AFTER 190625
			// if(Client_ID!=null && Passwd != null){
//                connOpts.setUserName(this.UserName);
//                connOpts.setPassword(this.Passwd.toCharArray());
			// }
			connOpts.setCleanSession(true);
			logger.info("Connecting to broker: " + this.Broker);

			Client.connect(connOpts);

//			logger.info("Connected");

			message = new MqttMessage();
		} catch (MqttException me) {
			logger.error("reason " + me.getReasonCode());
			logger.error("msg " + me.getMessage());
			logger.error("loc " + me.getLocalizedMessage());
			logger.error("cause " + me.getCause());
			logger.error("excep " + me);
			me.printStackTrace();
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			Client.disconnect();
			Client.close();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void publish(String msg, int qos) {
		message.setQos(qos);
		message.setPayload(msg.getBytes());

		try {
			Client.publish(topic, message);
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void subscribe(int qos) {
		try {
			Client.subscribe(topic, qos);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getTopic() {
		return topic;
	}

	@Override
	public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
//		logger.debug("Message arrived : " + new String(mqttMessage.getPayload(), "UTF-8"));
//		PushMessage message = new PushMessage();
//		message.setMessage(new String(mqttMessage.getPayload(), "UTF-8"));
//		message.setType(MessageType.INFO);
		if(!topic.contains("file")) {
			String msg = new String(mqttMessage.getPayload(), "UTF-8");
			logger.debug("Message arrived len: " +msg.length() + " Byte len: "  + msg.getBytes().length);
			logger.debug("Message arrived : " + msg);
		} else {
			transferedFileSave(mqttMessage);
		}
	}

	private void transferedFileSave(MqttMessage mqttMessage) throws UnsupportedEncodingException, IOException {
		String msg = new String(mqttMessage.getPayload(), "UTF-8");
		if(msg.contains("FileTransferStart")) {
			String[] msgArr = msg.split(",");
			FileName = msgArr[1];
		} else if (msg.contains("FileTransferEnd")) {
			Path path = Paths.get("C:\\Dev\\" + FileName);
			OpenOption[] options = new OpenOption[] {
		            StandardOpenOption.CREATE,
		            StandardOpenOption.APPEND};
			Files.write(path, FileStrList, options);
			FileStrList.clear();
		} else {
			FileStrList.add(msg);
		}
	}

	@Override
	public void connectionLost(Throwable cause) {
		logger.info("Lost Connection." + cause.getCause());
		sleep(50000);
		getConnection(topic);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
//		logger.debug("Message with " + iMqttDeliveryToken + " delivered.");
	}
	
	static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * connetction and subscribe
	 * @param topic
	 */
	public void getConnection(String topic) {
		this.init(topic);
		sleep(1000);
		this.subscribe(2);
	}
}
