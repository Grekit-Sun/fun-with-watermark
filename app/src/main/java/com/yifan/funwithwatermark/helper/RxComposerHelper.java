package com.yifan.funwithwatermark.helper;


import android.content.Context;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 调度类
 */
public class RxComposerHelper {

    public static <T> ObservableTransformer<T, T> observableIO2Main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> flowableIO2Main() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

//    private static <T> ObservableSource<T> composeContext(Context context, Observable<T> observable) {
//        if (context instanceof RxActivity) {
//            return observable.compose(((RxActivity) context).bindUntilEvent(ActivityEvent.DESTROY));
//        } else if (context instanceof RxFragmentActivity) {
//            return observable.compose(((RxFragmentActivity) context).bindUntilEvent(ActivityEvent.DESTROY));
//        } else if (context instanceof RxAppCompatActivity) {
//            return observable.compose(((RxAppCompatActivity) context).bindUntilEvent(ActivityEvent.DESTROY));
//        } else {
//            return observable;
//        }
//    }
}
