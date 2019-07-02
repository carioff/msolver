package com.rootech.msolver.common.mqtt;

import java.awt.TrayIcon.MessageType;

import javax.annotation.Resource;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.rootech.msolver.vo.PushMessage;

public class Mqtt implements MqttCallback {
	private static String Broker;
	private static String Client_ID;
	private static String UserName;
	private static String Passwd;
	private static MqttAsyncClient Client;
	private static MqttMessage message;
	private static MemoryPersistence persistence;
	private static MqttConnectOptions connOpts;
	private static String topic;

	@Resource(name = "MessagePusher")
	private MessagePusher template;
//	@Autowired
//	private PushService pushService;
//	@Autowired
//	MessagePusher messagePusher;

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
			System.out.println("Connecting to broker: " + this.Broker);

			Client.connect(connOpts);

			System.out.println("Connected");

			message = new MqttMessage();
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
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
		System.out.println("Message arrived : " + new String(mqttMessage.getPayload(), "UTF-8"));

		PushMessage message = new PushMessage();
		message.setMessage(new String(mqttMessage.getPayload(), "UTF-8"));
		message.setType(MessageType.INFO);
//		this.template.sendMessage(message);
//        MessagePusher messagePusher = new MessagePusher();
//        messagePusher.sendMessage(message);

//        messagePusher.message(message);
	}

	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("Lost Connection." + cause.getCause());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
		System.out.println("Message with " + iMqttDeliveryToken + " delivered.");
	}

//    private void requestInitialized(ServletRequestEvent requestEvent) {
//        if (!(requestEvent.getServletRequest() instanceof HttpServletRequest)) {
//            throw new IllegalArgumentException(
//                    "Request is not an HttpServletRequest: "
//                            + requestEvent.getServletRequest());
//        }
//
//        HttpServletRequest request = (HttpServletRequest) requestEvent
//                .getServletRequest();
//        ServletRequestAttributes attributes = new ServletRequestAttributes(
//                request);
//        request.setAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE, attributes);
//        LocaleContextHolder.setLocale(request.getLocale());
//        RequestContextHolder.setRequestAttributes(attributes);
//    }

}
