package com.frozen.emqproducer.controller;

import com.frozen.emqproducer.config.MqttConfiguration;
import com.frozen.emqproducer.config.PushCallback;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MQTTServerController {

    String topic = "MQTT_PRODUCER_TOPIC";

    @RequestMapping("/")
    public String sayHello() {
        return "Hello !";
    }

    @Autowired
    private MqttConfiguration mqttConfiguration;

        @GetMapping("/send/msg")
    public boolean send() throws MqttException {
        String msg = "frozen";
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "==========:" + msg);
        {
            log.info("MQ===public=== 入参:topic:{};content:{}", topic, msg);
            MqttMessage message = new MqttMessage(msg.getBytes());
            message.setQos(mqttConfiguration.getQos());
            /**
             * Retained为true时MQ会保留最后一条发送的数据，当断开再次订阅即会接收到这最后一次的数据
             */
            message.setRetained(true);
            try {
                MqttClient mqttClient = this.connect(mqttConfiguration.getPublishClientId(), mqttConfiguration.getUsername(),
                        mqttConfiguration.getPassword());
                // 判定是否需要重新连接
            /*String clientId =  UUID.randomUUID().toString() +
                    "[" + InetAddress.getLocalHost().getHostAddress() + "]";*/
                if (mqttClient == null || !mqttClient.isConnected() || !mqttClient.getClientId().equals(mqttConfiguration.getPublishClientId())) {
                    mqttClient = this.connect(mqttConfiguration.getPublishClientId(), mqttConfiguration.getUsername(),
                            mqttConfiguration.getPassword());
                }
                mqttClient.publish(topic, message);
                log.info("emq已发topic: {} - message: {}", topic, message);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    private MqttClient connect(String clientId, String userName, String password) throws MqttException {
        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(mqttConfiguration.getConnectionTimeout());
        options.setKeepAliveInterval(mqttConfiguration.getKeepAliveInterval());

        MqttClient client = new MqttClient(mqttConfiguration.getHost(), clientId, persistence);
        client.setCallback(new PushCallback());
        client.connect(options);
        return client;
    }
}
