package com.xiaoyuan.costlist;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * 确认对话框
 * Created by Administrator on 2017/2/7.
 */
public class ConfirmDialog extends Dialog implements View.OnClickListener {

    private Context context;

    public ConfirmDialog(Context context) {
        super(context);
        this.context = context;
        initDialog();
    }

    public ConfirmDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        initDialog();
    }

    private void initDialog() {
        View v = LayoutInflater.from(context).inflate(R.layout.confirm_dialog, null);
        Button yes = (Button) v.findViewById(R.id.yes);
        Button no = (Button) v.findViewById(R.id.no);
        setContentView(v);
        setCanceledOnTouchOutside(false);
        no.setOnClickListener(this);
        yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes:
                if (onYesClickListener != null) {
                    onYesClickListener.onYesClick();
                }
                break;
            case R.id.no:
                dismiss();
                break;
        }
    }

    public interface OnYesClickListener {
        void onYesClick();
    }

    private OnYesClickListener onYesClickListener;

    public void setOnYesClickListener(OnYesClickListener onYesClickListener) {
        this.onYesClickListener = onYesClickListener;
    }
}
