package com.example.iotapp.helper;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class MqttHelper {

    public MqttAndroidClient mqttAndroidClient;
    final String serverUri;

    final String clientId;
    final String subscriptionTopic ;

    final String username ;
    final String password ;
    final String TAG;

    public MqttHelper(Context context){
         clientId = "ExampleAndroidClient";
         subscriptionTopic = "KodeHauz/ProjectCreate/Device";
        TAG = "";
         username = "xokaxvwt";
         password = "MGlEiIwOHM9-";
        serverUri = "tcp://m16.cloudmqtt.com:15998";
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("mqtt", s);
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Mqtt", mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
        connect();
    }

    public void setCallback(MqttCallbackExtended callback) {
        mqttAndroidClient.setCallback(callback);
    }

    private void connect(){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());

        try {

            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Failed to connect to: " + serverUri + exception.toString());
                }
            });


        } catch (MqttException ex){
            ex.printStackTrace();
        }
    }


    private void subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("Mqtt","Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Subscribed fail!");
                }
            });

        } catch (MqttException ex) {
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

//    public void publish (String topic, String msg, int qos , Boolean retained  ){
//        qos = 1;
//        retained = false;
//        try {
//            MqttMessage message = new MqttMessage();
//            message.setPayload(msg.getBytes());
//            message.setQos(qos);
//            message.isRetained();
//
//            mqttAndroidClient.publish(topic, message, null, new IMqttActionListener() {
//                @Override
//                public void onSuccess(IMqttToken asyncActionToken) {
//                    Log.d(TAG, "$msg published to $topic");
//                }
//
//                @Override
//                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                    Log.d(TAG, "Failed to publish $msg to $topic");
//                }
//            });
//        }catch (MqttException e){
//            e.printStackTrace();
//        }
//    }

    public void publish() throws MqttException {
        String broker = "tcp://m16.cloudmqtt.com:15998";
        String topicName = "KodeHauz/ProjectCreate/Device";
        int qos = 1;
        String clientId = "ExampleAndroidClient";

        MqttClient mqttClient = new MqttClient(broker,clientId);
//Mqtt ConnectOptions is used to set the additional features to mqtt message

        MqttConnectOptions connOpts = new MqttConnectOptions();

        connOpts.setCleanSession(true); //no persistent session
        connOpts.setKeepAliveInterval(1000);


        MqttMessage message = new MqttMessage("Ed Sheeran".getBytes());

        message.setQos(qos);     //sets qos level 1
        message.setRetained(true); //sets retained message

        MqttTopic topic2 = mqttClient.getTopic(topicName);

        mqttClient.connect(connOpts); //connects the broker with connect options
        topic2.publish(message);    // publishes the message to the topic(test/topic)
    }
}
