package yll.self.testapp.compont.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import yll.self.testapp.utils.LogUtil;

public class TestFragment extends Fragment {
    

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.yll( "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.yll( "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.yll( "onCreateView");
        TextView tv = new TextView(getActivity());
        tv.setText("1111111111");
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    @Override
    public void onStart() {
        LogUtil.yll( "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        LogUtil.yll( "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        LogUtil.yll( "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        LogUtil.yll( "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        LogUtil.yll( "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        LogUtil.yll( "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        LogUtil.yll( "onAttach");
        super.onDetach();
    }
}
