package yll.self.testapp.userinterface.lottie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import yll.self.testapp.R;

/**
 * Created by yll on 17/6/15.
 */

public class LottieActivity extends Activity {

    private ArrayList<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        init();
    }

    private void init(){
        ListView lv_list = (ListView) findViewById(R.id.lv_list);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LottieActivity.this, LottieShowActivity.class);
                intent.putExtra("fileName", dataList.get(i));
                startActivity(intent);
            }
        });
        loadData();
        LottieAdapter adapter = new LottieAdapter();
        lv_list.setAdapter(adapter);
    }

    private void loadData(){
        dataList.clear();
        dataList.add("lottiefiles.com - Animated Graph.json");
        dataList.add("lottiefiles.com - ATM.json");
        dataList.add("lottiefiles.com - Beating Heart.json");
        dataList.add("lottiefiles.com - Camera.json");
        dataList.add("lottiefiles.com - Countdown.json");
        dataList.add("lottiefiles.com - Curly Hair.json");
        dataList.add("lottiefiles.com - Day Night.json");
        dataList.add("lottiefiles.com - Emoji Shock.json");
        dataList.add("lottiefiles.com - Emoji Tongue.json");
        dataList.add("lottiefiles.com - Emoji Wink.json");
        dataList.add("lottiefiles.com - Favorite Star.json");
        dataList.add("lottiefiles.com - Gears.json");
        dataList.add("lottiefiles.com - Im Thirsty.json");
        dataList.add("llottiefiles.com - Loader 5.json");
        dataList.add("lottiefiles.com - Loading 1.json");
        dataList.add("lottiefiles.com - Loading 2.json");
        dataList.add("lottiefiles.com - Loading 3.json");
        dataList.add("lottiefiles.com - Loading 4.json");
        dataList.add("lottiefiles.com - Mail Sent.json");
        dataList.add("lottiefiles.com - Notifications.json");
        dataList.add("lottiefiles.com - Nudge.json");
        dataList.add("lottiefiles.com - Octopus.json");
        dataList.add("lottiefiles.com - Permission.json");
        dataList.add("lottiefiles.com - Play and Like It.json");
        dataList.add("lottiefiles.com - Postcard.json");
        dataList.add("lottiefiles.com - Preloader.json");
        dataList.add("lottiefiles.com - Progress Success.json");
        dataList.add("lottiefiles.com - React.json");
        dataList.add("lottiefiles.com - Retweet.json");
        dataList.add("lottiefiles.com - Star Wars Rey.json");
        dataList.add("lottiefiles.com - Tadah Image.json");
        dataList.add("lottiefiles.com - Tadah Video.json");
        dataList.add("lottiefiles.com - Touch ID.json");
        dataList.add("lottiefiles.com - Video Camera.json");
        dataList.add("lottiefiles.com - VR Sickness.json");
        dataList.add("lottiefiles.com - VR.json");
        dataList.add("LottieLogo1.json");
        dataList.add("LottieLogo2.json");
        dataList.add("lottifiles.com - QR Scan.json");
    }

    private class LottieAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int i) {
            return dataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Holder holder;
            if (view == null){
                view = LayoutInflater.from(LottieActivity.this).inflate(R.layout.view_item_lottie, null);
                holder = new Holder();
                holder.tv_item = (TextView) view.findViewById(R.id.tv_item);
                view.setTag(holder);
            }else {
                holder = (Holder) view.getTag();
            }
            holder.tv_item.setText(dataList.get(i));
            return view;
        }

        class Holder{
            TextView tv_item;
        }
    }

}


