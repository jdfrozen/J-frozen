package com.example.emqconsumer.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class IMqttWrapperServiceImpl implements InitializingBean {

    @Autowired
    private MqttConfiguration mqttConfiguration;

    String topic = "MQTT_PRODUCER_TOPIC";
    @Autowired
    private SubscribeConn subscribeConn;
    @PostConstruct
    public void subscribe() {
        log.info("MQ===subscribe=== 入参:topic:{}", topic);
        MqttClient mqttClient = subscribeConn.getMqttClient();
        // 判定是否需要重新连接
        if (mqttClient==null || !mqttClient.isConnected() || !mqttClient.getClientId().equals(mqttConfiguration.getSubscribeClientId())) {
            mqttClient = subscribeConn.getConn();
        }
        try {
            // 订阅消息
            int[] qos = {mqttConfiguration.getQos()};
            mqttClient.subscribe(new String[]{topic},qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
