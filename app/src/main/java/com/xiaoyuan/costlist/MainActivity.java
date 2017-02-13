package com.xiaoyuan.costlist;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主页面
 */
public class MainActivity extends AppCompatActivity implements MainAdapter.MyOnLongClickListener {

    @Bind(R.id.root)
    CoordinatorLayout root;

    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.total_price)
    TextView total_price;

    private List<Record> data = new ArrayList<>();
    private MainAdapter adapter;
    private EditDialog dialog;
    private ConfirmDialog confirmDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        initData();
        initOperation();
    }

    private void initOperation() {
        dialog = new EditDialog(this, R.style.dialog);
        dialog.setOnAddClickListener(new EditDialog.OnAddClickListener() {
            @Override
            public void onAddClick(float money, String date) {
                dialog.dismiss();
                data.add(new Record(money, date));
                getTotalPrice();
                SharedPreUtil.getInstance(MainActivity.this).saveData(data);
                adapter.notifyDataSetChanged();
            }
        });
        confirmDialog = new ConfirmDialog(this, R.style.dialog);
        adapter.setMyOnLongClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        List<Record> list = SharedPreUtil.getInstance(this).getData();
        if (list != null) {
            data.addAll(list);
        }
        adapter = new MainAdapter(this, data);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.addItemDecoration(new MyItemDecoration(this, manager.getOrientation()));
        recycler.setAdapter(adapter);
        getTotalPrice();
    }


    /**
     * 计算总价
     */
    private void getTotalPrice() {
        if (data != null && data.size() > 0) {
            float price = 0.0f;
            for (Record item : data) {
                price += item.money;
            }
            price = new BigDecimal(price).setScale(1, BigDecimal.ROUND_HALF_UP)
                    .floatValue();
            total_price.setText("￥" + price);
        } else {
            total_price.setText("￥0.0");
        }
    }

    /**
     * 添加操作
     *
     * @param v
     */
    public void onButtonClick(View v) {
        dialog.show();
    }

    /**
     * Snack 提示
     * @param s
     */
    private void showSnack(String s) {
        Snackbar.make(root, s, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //删除操作
        if (item.getItemId() == R.id.delete) {
            confirmDialog.setOnYesClickListener(new ConfirmDialog.OnYesClickListener() {
                @Override
                public void onYesClick() {
                    SharedPreUtil.getInstance(MainActivity.this).clearAll();
                    data.clear();
                    confirmDialog.dismiss();
                    adapter.notifyDataSetChanged();
                    showSnack("删除成功~");
                    getTotalPrice();
                }
            });
            confirmDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * item 的长按事件
     *
     * @param v
     * @param position
     */
    @Override
    public void onLongClick(View v, final int position) {
        confirmDialog.setOnYesClickListener(new ConfirmDialog.OnYesClickListener() {
            @Override
            public void onYesClick() {
                data.remove(position);
                SharedPreUtil.getInstance(MainActivity.this).saveData(data);
                confirmDialog.dismiss();
                adapter.notifyDataSetChanged();
                getTotalPrice();
            }
        });
        confirmDialog.show();
    }

}
