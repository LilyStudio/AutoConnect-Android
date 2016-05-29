package com.padeoe.njunet.util;

/**
 * Created by padeoe on 2016/5/10.
 */
public interface MyObserver<T> {
    void update(MyObservable myObservable, T data);
}
