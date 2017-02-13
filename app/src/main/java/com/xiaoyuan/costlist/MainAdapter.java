package com.xiaoyuan.costlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * 主页面适配器
 * Created by Administrator on 2017/2/7.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> implements View.OnLongClickListener {

    private Context context;
    private List<Record> data;


    public MainAdapter(Context context, List<Record> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.date.setText(data.get(position).date);
        holder.money.setText("￥" + data.get(position).money);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }




    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        TextView date, money;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            date = (TextView) itemView.findViewById(R.id.date);
            money = (TextView) itemView.findViewById(R.id.money);
        }

        @Override
        public boolean onLongClick(View v) {
            if (myOnLongClickListener != null) {
                myOnLongClickListener.onLongClick(v, getAdapterPosition());
            }
            return true;
        }
    }

    public interface MyOnLongClickListener {
        void onLongClick(View v, int position);
    }

    private MyOnLongClickListener myOnLongClickListener;

    public void setMyOnLongClickListener(MyOnLongClickListener myOnLongClickListener) {
        this.myOnLongClickListener = myOnLongClickListener;
    }
}
