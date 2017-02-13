package com.xiaoyuan.costlist;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 输入框对话框
 * Created by Administrator on 2017/2/7.
 */
public class EditDialog extends Dialog {

    private Context context;


    public EditDialog(Context context) {
        super(context);
        this.context = context;
        initDialog();
    }


    public EditDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        initDialog();
    }

    //初始化dialog内容
    private void initDialog() {
        View v = LayoutInflater.from(context).inflate(R.layout.et_dialog, null);
        final EditText et = (EditText) v.findViewById(R.id.dialog_et);
        Button but = (Button) v.findViewById(R.id.dialog_but);
        final CheckBox box = (CheckBox) v.findViewById(R.id.checkbox);
        setContentView(v);
        setCanceledOnTouchOutside(false);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moneyStr = et.getText().toString().trim();
                if (moneyStr.equals("")) {
                    Toast.makeText(context, "请输入适当金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                float money = Float.parseFloat(moneyStr);
                if (money <= 0) {
                    Toast.makeText(context, "请输入适当金额", Toast.LENGTH_SHORT).show();
                } else if (onAddClickListener != null) {
                    if (box.isChecked()) {
                        onAddClickListener.onAddClick(-money, getDate());
                    } else {
                        onAddClickListener.onAddClick(money, getDate());
                    }
                    et.setText("");
                    box.setChecked(false);
                }
            }
        });
    }


    private String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return sdf.format(new Date());
    }

    public interface OnAddClickListener {
        void onAddClick(float money, String date);
    }

    private OnAddClickListener onAddClickListener;

    public void setOnAddClickListener(OnAddClickListener onAddClickListener) {
        this.onAddClickListener = onAddClickListener;
    }
}
