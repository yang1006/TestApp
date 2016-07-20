package yll.self.testapp.compont.broadCast;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import yll.self.testapp.R;

/**
 * Created by yll on 2016/1/20.
 * 用于发送广播的activity
 */
public class SendBroadCastActivity extends Activity implements View.OnClickListener {

    public final String UnOrderBroadAction = "yll.self.testapp.UnOrderBroadAction";
    public final String OrderBroadAction = "yll.self.testapp.OrderBroadAction";

    private MyUnorderedBroadCastA broadCastA;
    private MyUnorderedBroadCastB broadCastB;

    private MyOrderBroadCastA orderBroadCastA;
    private MyOrderBroadCastB orderBroadCastB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_broadcast);
        init();
//        registerUnorderBroadCasts();
        registerOrderBroadCasts();
    }

    private void init(){
        findViewById(R.id.tv_send1).setOnClickListener(this);
        findViewById(R.id.tv_send2).setOnClickListener(this);
    }

    /**动态注册各个广播接收器*/
    private void registerUnorderBroadCasts(){
        IntentFilter intentFilterA = new IntentFilter(UnOrderBroadAction);
        intentFilterA.setPriority(777);
        IntentFilter intentFilterB = new IntentFilter(UnOrderBroadAction);
        intentFilterB.setPriority(888);
        broadCastA = new MyUnorderedBroadCastA();
        broadCastB = new MyUnorderedBroadCastB();
        //优先级不一样时优先级更大的先接收， 优先级一样谁先注册谁就先接收
        registerReceiver(broadCastA, intentFilterA);
        registerReceiver(broadCastB, intentFilterB);
    }

    /**注册有序广播接收器们*/
    private void registerOrderBroadCasts(){
        IntentFilter intentFilterA = new IntentFilter(OrderBroadAction);
        intentFilterA.setPriority(999);
        IntentFilter intentFilterB = new IntentFilter(OrderBroadAction);
        intentFilterB.setPriority(888);
        orderBroadCastA = new MyOrderBroadCastA();
        orderBroadCastB = new MyOrderBroadCastB();
        registerReceiver(orderBroadCastA, intentFilterA);
        registerReceiver(orderBroadCastB, intentFilterB);
    }


    private void unregisterReceivers(){
        unregisterReceiver(broadCastA);
        unregisterReceiver(broadCastB);
    }

    private void unRegisterOrderReceivers(){
        unregisterReceiver(orderBroadCastA);
        unregisterReceiver(orderBroadCastB);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tv_send1:
                intent = new Intent(UnOrderBroadAction);
                sendBroadcast(intent);
                break;
            case R.id.tv_send2:
                intent = new Intent(OrderBroadAction);
                sendOrderedBroadcast(intent, null);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceivers();
        unRegisterOrderReceivers();
    }

}
