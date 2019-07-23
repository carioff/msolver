package com.rootech.msolver.common.mqtt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//@WebListener
public class MqttObserver implements ServletContextListener {
	String MqttServer = "tcp://10.10.19.28:9001";
	String client_id = "TomcatServer";
	String username = "";
	String passwd = "";
//	String topic = "$SYS/broker/uptime";
	String topic = "linux/file";
//	String topic = "linux/longtxt";
	
	String tmpFilePath = "C:\\Dev\\apache-jmeter-5.1.1\\";
	String tmpFileName = "README.md";

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("init start"); // 초기화와 동시에 실행되는 부분
		Mqtt readFromOtherMQTT = new Mqtt(MqttServer, client_id, username, passwd);
		readFromOtherMQTT.getConnection(topic);
		
//		Path path1 = Paths.get(tmpFilePath + tmpFileName);
//		List<String> list = new ArrayList<>();
//
//		try (Stream<String> stream = Files.lines(path1)){
//			list = stream.collect(Collectors.toList());
//			list = stream
//					.filter(line -> !line.startsWith("##"))
//					.map(String::toUpperCase)
//					.collect(Collectors.toList());
//			readFromOtherMQTT.publish(file, 1);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		list.forEach(System.out::println);
//		filePublish(readFromOtherMQTT, list);
//		longTxtPub(readFromOtherMQTT, list);
	}

	private void longTxtPub(Mqtt readFromOtherMQTT, List<String> list) {
		StringBuffer sb = new StringBuffer();
//		StringBuilder sb = new StringBuilder();
		for (String line : list) {
			sb.append(line);
			sb.append(System.lineSeparator());
		}
		for (int i = 0; i < 10; i++) {
			sb.append(sb.toString());
		}
		readFromOtherMQTT.publish(sb.toString(), 2);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("init destory"); // Destory와 동시에 실행되는 부분
	}

	private void filePublish(Mqtt readFromOtherMQTT, List<String> list) {
		readFromOtherMQTT.publish("FileTransferStart," + tmpFileName, 1);
		Mqtt.sleep(7);
		for (int i = 0; i < 100; i++) {
			for (String line : list) {
				readFromOtherMQTT.publish(line, 1);
				Mqtt.sleep(7);
			}
		}
		readFromOtherMQTT.publish("FileTransferEnd," + tmpFileName, 1);
		Mqtt.sleep(7);
	}
}
