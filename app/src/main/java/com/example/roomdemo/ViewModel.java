package com.example.roomdemo;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private Repo mRepository;
    private LiveData<List<Word>> mAllWords;
    public ViewModel(Application application) {
        super(application);
        mRepository = new Repo(application);
        mAllWords = mRepository.getAllWords();
    }
    LiveData<List<Word>> getAllWords() { return mAllWords; }
    public void insert(Word word) {
        mRepository.insert(word);
    }
    public void deleteWord(Word word) {
        mRepository.deleteWord(word);
    }
    public void deleteAll() {
        mRepository.deleteAll();
    }
}
