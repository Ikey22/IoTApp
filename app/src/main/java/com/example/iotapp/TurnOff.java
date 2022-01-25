package com.example.iotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.iotapp.helper.MqttHelper;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class TurnOff extends AppCompatActivity {


    MqttHelper mqttHelper;
    ImageButton btnOff;


    public MqttAndroidClient mqttAndroidClient;
    String TAG;
    String serverUri;

    String clientId;
    String subscriptionTopic ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_off);

        TAG = "";
        btnOff =  findViewById(R.id.powerButtonFragmentDark);
        mqttHelper = new MqttHelper(getApplicationContext());
        subscriptionTopic = "KodeHauz/ProjectCreate/Device";
        clientId = "ExampleAndroidClient";
        serverUri = "tcp://m16.cloudmqtt.com:15998";
        final MqttMessage message = new MqttMessage();
        message.setPayload("Yes".getBytes());
        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(),serverUri, clientId);

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttHelper.publish("off");
                Intent intent = new Intent(TurnOff.this,MainActivity.class);
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
}