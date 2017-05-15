package com.litingzhe.justandroid.netService;

import com.litingzhe.justandroid.netdb.net.model.NewList;
import com.litingzhe.justandroid.utils.netutils.RetrofitUtils;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/28 下午1:40.
 * 类描述：网络层接口
 */


public class NetWork extends RetrofitUtils {


    protected static final NetService service = getRetrofit().create(NetService.class);

    //设缓存有效期为1天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，使用缓存
    protected static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置。不使用缓存
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";
    private static final String API_SERVER = "https://api.tianapi.com/keji";


    private interface NetService {

        //POST请求
        //@FormUrlEncoded
        //@POST("url")
        //不设置缓存 @Headers("Cache-Control: public," + CACHE_CONTROL_NETWORK)
        //GET请求，设置缓存
//        @Headers("Cache-Control: public," + CACHE_CONTROL_CACHE)
        @GET
        Observable<NewList> getNewsList(@Url String url, @Query("key") String key, @Query("num") String num);

    }

    //
    //POST请求
    public static void getNewsList(String key, String num, Observer<NewList> observer) {
        setSubscribe(service.getNewsList(API_SERVER, key, num), observer);
    }
//

//    }

    /**
     * 插入观察者
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }


}
