package com.example.fakenewsdetection.ui.home;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.fakenewsdetection.db.DBHelper;

import java.util.ArrayList;

public class PageViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ModelHistory>> history = new MutableLiveData<>();
    public void setHistory(ArrayList<ModelHistory> item) {
        history.setValue(item);
    }

    public LiveData<ArrayList<ModelHistory>> getHistory() {
        return history;
    }


//    private LiveData<String> mText = Transformations.map(mIndex, new Function<ArrayList<ModelHistory>, String>() {
//        @Override
//        public String apply(ArrayList<ModelHistory> input) {
//            return null;
//        }
//
////        @Override
////        public String apply(Integer input) {
////            return "Hello world from section: " + input;
////        }
//    });

//    public void setIndex(int index) {
//        mIndex.setValue(index);
//    }

//    public LiveData<String> getText() {
//        return mText;
//    }

}
