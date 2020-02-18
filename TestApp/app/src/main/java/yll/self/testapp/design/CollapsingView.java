package yll.self.testapp.design;

import android.app.Activity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yll.self.testapp.R;
import yll.self.testapp.utils.RecyclerItemClickListener;

/**
 * Created by yanglinlong on 17/11/14.
 */

public class CollapsingView {

    private View root;
    private RecyclerView recycler;
    private MyRecycleAdapter adapter;
    private Activity activity;

    public CollapsingView(Activity activity){
        this.activity = activity;
        root = LayoutInflater.from(activity).inflate(R.layout.view_collapsing, null);
        recycler = (RecyclerView) root.findViewById(R.id.recycler);
        initRecyclerView();
    }

    public View getRoot(){
        return root;
    }


    private void initRecyclerView(){
        adapter = new MyRecycleAdapter();
        recycler.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);

        recycler.addOnItemTouchListener( new RecyclerItemClickListener(activity,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position >= 0 &&position < list.size()){
                            list.remove(position);
                            adapter.notifyItemRemoved(position);
                        }
                    }
                }));

    }

    private List<String> list;
    class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyViewHolder>{

        MyRecycleAdapter(){
            list = new ArrayList<>();
            for (int i= 0; i < 100; i++){
                list.add(i+"");
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            TextView textView = new TextView(activity);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            return new MyViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int i) {
            viewHolder.textView.setText(list.get(i));
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        protected class MyViewHolder extends RecyclerView.ViewHolder{
            protected TextView textView;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.textView  = (TextView) itemView;
            }
        }
    }
}
