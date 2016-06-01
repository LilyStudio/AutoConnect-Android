/**
 * modified by padeoe on 2016/5/10.
 */
/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.padeoe.njunet.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable is used to notify a group of Observer objects when a change
 * occurs. On creation, the set of myObservers is empty. After a change occurred,
 * the application can call the {@link #notifyObservers()} method. This will
 * cause the invocation of the {@code update()} method of all registered
 * Observers. The order of invocation is not specified. This implementation will
 * call the Observers in the order they registered. Subclasses are completely
 * free in what order they call the update methods.
 *
 * @see MyObserver
 */
public class MyObservable<T> {

    List<MyObserver> myObservers = new ArrayList<MyObserver>();

    boolean changed = false;

    /**
     * Constructs a new {@code Observable} object.
     */
    public MyObservable() {
    }

    /**
     * Adds the specified observer to the list of myObservers. If it is already
     * registered, it is not added a second time.
     *
     * @param myObserver the Observer to add.
     */
    public void addObserver(MyObserver myObserver) {
        if (myObserver == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (!myObservers.contains(myObserver))
                myObservers.add(myObserver);
        }
    }

    /**
     * Clears the changed flag for this {@code Observable}. After calling
     * {@code clearChanged()}, {@code hasChanged()} will return {@code false}.
     */
    protected void clearChanged() {
        changed = false;
    }

    /**
     * Returns the number of myObservers registered to this {@code Observable}.
     *
     * @return the number of myObservers.
     */
    public int countObservers() {
        return myObservers.size();
    }

    /**
     * Removes the specified observer from the list of myObservers. Passing null
     * won't do anything.
     *
     * @param observer the observer to remove.
     */
    public synchronized void deleteObserver(MyObservable observer) {
        myObservers.remove(observer);
    }

    /**
     * Removes all myObservers from the list of myObservers.
     */
    public synchronized void deleteObservers() {
        myObservers.clear();
    }

    /**
     * Returns the changed flag for this {@code Observable}.
     *
     * @return {@code true} when the changed flag for this {@code Observable} is
     * set, {@code false} otherwise.
     */
    public boolean hasChanged() {
        return changed;
    }

    /**
     * If {@code hasChanged()} returns {@code true}, calls the {@code update()}
     * method for every observer in the list of myObservers using null as the
     * argument. Afterwards, calls {@code clearChanged()}.
     * <p/>
     * Equivalent to calling {@code notifyObservers(null)}.
     */
    public void notifyObservers() {
        notifyObservers(null);
    }

    /**
     * If {@code hasChanged()} returns {@code true}, calls the {@code update()}
     * method for every Observer in the list of myObservers using the specified
     * argument. Afterwards calls {@code clearChanged()}.
     *
     * @param data the argument passed to {@code update()}.
     */
    @SuppressWarnings("unchecked")
    public void notifyObservers(T data) {
        int size = 0;
        MyObserver[] arrays = null;
        synchronized (this) {
            if (hasChanged()) {
                clearChanged();
                size = myObservers.size();
                arrays = new MyObserver[size];
                myObservers.toArray(arrays);
            }
        }
        if (arrays != null) {
            for (MyObserver observer : arrays) {
                observer.update(this, data);
            }
        }
    }

    /**
     * Sets the changed flag for this {@code Observable}. After calling
     * {@code setChanged()}, {@code hasChanged()} will return {@code true}.
     */
    protected void setChanged() {
        changed = true;
    }
}

