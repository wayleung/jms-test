package com.way.jms.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class AppConsumer {
	private static final String url="tcp://127.0.0.1:61616";
	
	private static final String queueName="queue-test";
	
	public static void main(String[] args) throws JMSException {

		
		//1.创建ConnectionFactory
		ConnectionFactory connectionFactory =  new ActiveMQConnectionFactory(url);
		
		//2.创建Connection
		Connection connection = connectionFactory.createConnection();
		
		//3.启动连接
		connection.start();
		
		//4.创建会话 true false 是否事务处理
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//5.创建一个目标
		Destination destination =  session.createQueue(queueName);
		
		//6.创建一个消费者
		MessageConsumer consumer = session.createConsumer(destination);
		
		//7.创建一个监听器
		consumer.setMessageListener(new MessageListener() {
			
			public void onMessage(Message message) {
				// TODO Auto-generated method stub
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("接受消息"+textMessage.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		//8.关闭连接 不能关闭因为接收消息是一个异步的过程
		//connection.close();
	
	}
}
