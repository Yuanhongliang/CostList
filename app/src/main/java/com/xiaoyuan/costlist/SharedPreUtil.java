package com.xiaoyuan.costlist;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 利用SharedPreferences 存储数据
 * Created by Administrator on 2017/2/7.
 */
public class SharedPreUtil {

    private static SharedPreUtil instance;
    private static SharedPreferences preferences;
    private static String costlist = "cost_list";
    private Gson gson = new Gson();

    //单例模式
    public static SharedPreUtil getInstance(Context context) {
        if(instance==null){
            synchronized (SharedPreUtil.class){
                if(instance==null){
                    instance = new SharedPreUtil();
                    preferences = context.getSharedPreferences(costlist,Context.MODE_PRIVATE);
                }
            }
        }
        return instance;
    }
    private SharedPreUtil(){}

    /**
     * 保存数据
     * @param data 要保存的数据
     */
    public void saveData(List<Record> data){
        String listStr = gson.toJson(data);
        preferences.edit().putString(costlist,listStr).commit();
    }

    /**
     * 获取数据
     * @return 数据集合
     */
    public List<Record> getData(){
        String listStr = preferences.getString(costlist,"");
        return gson.fromJson(listStr,new TypeToken<List<Record>>(){}.getType());
    }

    /**
     * 清除所有数据
     */
    public  void clearAll(){
        preferences.edit().clear().commit();
    }

}
