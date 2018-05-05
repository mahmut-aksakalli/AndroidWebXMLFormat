package hr.ferit.mahmutaksakalli.androidwebxmlformat.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import hr.ferit.mahmutaksakalli.androidwebxmlformat.model.NewsInfo;


public class NewsListViewModel extends ViewModel {

    private MutableLiveData<List<NewsInfo>> mData =
            new MutableLiveData<>();

    private MutableLiveData<List<String>> mCategories =
            new MutableLiveData<>();

    public LiveData<List<NewsInfo>> getData() {
        return mData;
    }

    public void setData(List<NewsInfo> data){
        mData.postValue(data);
    }

    public LiveData<List<String>> getCategories() {
        return mCategories;
    }

    public void setCategories(List<String> data){
        mCategories.postValue(data);
    }
}
