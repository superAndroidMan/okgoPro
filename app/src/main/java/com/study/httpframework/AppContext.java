package com.study.httpframework;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.study.httpframework.model.UserInfoBean;

import java.util.Properties;
import java.util.Stack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AppContext extends Application implements Application.ActivityLifecycleCallbacks {


    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    private Stack<Activity> mActivityList;

    public static AppContext getmInstance() {
        return mInstance;
    }
    public static AppContext mInstance;

    public Stack<Activity> getmActivityList() {
        return mActivityList;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mActivityList = new Stack<>();
        registerActivityLifecycleCallbacks(this);
    }

    /**
     * 获取cookie时传AppConfig.CONF_COOKIE
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        String res = AppConfig.getAppConfig(this).get(key);
        return res;
    }

    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }

    /**
     * 保存登录信息
     *
     * @param user 用户信息
     */
    public void saveUserInfo(final UserInfoBean user) {
        setProperties(new Properties() {
//            {
//                String value = String.valueOf(user.getUuid());
//                if (!TextUtils.isEmpty(user.getUuid()))
//                    setProperty("user.uid", value);
//                if (!TextUtils.isEmpty(user.getRealName()))
//                    setProperty("user.nickname", String.valueOf(user.getRealName()));
//                if (!TextUtils.isEmpty(user.getSex()))
//                    setProperty("user.sex", String.valueOf(user.getSex()));
//                if (!TextUtils.isEmpty(user.getMedicalHistrory()))
//                    setProperty("user.medicalHistrory", String.valueOf(user.getMedicalHistrory()));
//                if (!TextUtils.isEmpty(user.getPastHistory()))
//                    setProperty("user.medicalHistrory", String.valueOf(user.getPastHistory()));
//                if (!TextUtils.isEmpty(user.getAlleryg()))
//                    setProperty("user.allergy", String.valueOf(user.getAlleryg()));
//                if (!TextUtils.isEmpty(user.getDrugAllergy()))
//                    setProperty("user.allergy", String.valueOf(user.getDrugAllergy()));
//                if (!TextUtils.isEmpty(user.getBirthDate()))
//                    setProperty("user.birthDate", String.valueOf(user.getBirthDate()));
//            }
        });
    }

    public void setProperties(Properties ps) {
        AppConfig.getAppConfig(this).set(ps);
    }

    public Properties getProperties() {
        return AppConfig.getAppConfig(this).get();
    }

    public void setProperty(String key, String value) {

        AppConfig.getAppConfig(this).set(key, value);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        mActivityList.add(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if(mActivityList.contains(activity)){
            mActivityList.remove(activity);
        }
    }
}