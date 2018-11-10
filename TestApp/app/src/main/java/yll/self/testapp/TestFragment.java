package yll.self.testapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TestFragment extends Fragment {


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("yll", "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("yll", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("yll", "onCreateView");
        TextView tv = new TextView(getActivity());
        tv.setText("1111111111");
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    @Override
    public void onStart() {
        Log.e("yll", "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.e("yll", "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("yll", "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("yll", "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.e("yll", "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e("yll", "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.e("yll", "onAttach");
        super.onDetach();
    }
}
