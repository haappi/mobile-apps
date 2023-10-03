package io.github.haappi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ConcurrentHashMap;

public class SharedViewModel<T> {
    private static SharedViewModel instance;

    private final ConcurrentHashMap<String, T> dataMap = new ConcurrentHashMap<>();
    public void setData(String name, T value) {
        dataMap.put(name, value);
    }

    public static SharedViewModel getInstance() {
        if (instance == null) {
            instance = new SharedViewModel();
        }
        return instance;
    }

    private SharedViewModel() {
    }

    public T getData(String key) {
        return dataMap.get(key);
    }
}

