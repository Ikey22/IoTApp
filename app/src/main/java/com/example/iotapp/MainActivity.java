package com.example.iotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.iotapp.helper.MqttHelper;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {

    MqttHelper mqttHelper;
    TextView subscribe_text;
    ImageButton btnOn;


    public MqttAndroidClient mqttAndroidClient;
    String TAG;
     String serverUri;

     String clientId;
     String subscriptionTopic ;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TAG = "";
        btnOn =  findViewById(R.id.powerButtonFragmentLight);
        subscribe_text = findViewById(R.id.text_subscribe);
        mqttHelper = new MqttHelper(getApplicationContext());
        subscriptionTopic = "KodeHauz/ProjectCreate/Device";
        clientId = "ExampleAndroidClient";
        serverUri = "tcp://m16.cloudmqtt.com:15998";
        final MqttMessage message = new MqttMessage();
          message.setPayload("Yes".getBytes());
        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(),serverUri, clientId);

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttHelper.publish("on");
                Intent intent = new Intent(MainActivity.this,TurnOff.class);
                startActivity(intent);

            }

        });

        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug", mqttMessage.toString());
                subscribe_text.setText(mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });

//        dataReceived.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MqttHelper mqttHelperr = new MqttHelper(getApplicationContext());
//                try {
//                    mqttHelperr.publish();
//                } catch (MqttException e) {
//                    e.printStackTrace();
//                }
//                Log.w("Debug", "Erron don happen");
//            }
//        });

    }



}