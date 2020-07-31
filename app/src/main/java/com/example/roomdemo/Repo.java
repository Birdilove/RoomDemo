package com.example.roomdemo;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

public class Repo {
    private TextDao TextDao;
    private LiveData<List<Word>> mAllWords;
    Repo(Application application) {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        TextDao = db.wordDao();
        mAllWords = TextDao.getAlphabetizedWords();
    }
    public void insert(Word word) {
        new insertAsyncTask(TextDao).execute(word);
    }
    public void deleteAll() {
        new deleteAllEntriesAsyncTask(TextDao).execute();
    }
    public void deleteWord(Word word) {
        new deleteEntryAsyncTask(TextDao).execute(word);
    }
    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }
    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {
        private TextDao mAsyncTaskDao;
        insertAsyncTask(TextDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    private static class deleteEntryAsyncTask extends AsyncTask<Word, Void, Void> {
        private TextDao mAsyncTaskDao;
        deleteEntryAsyncTask(TextDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.deleteEntry(params[0]);
            return null;
        }
    }
    private static class deleteAllEntriesAsyncTask extends AsyncTask<Void, Void, Void> {
        private TextDao mAsyncTaskDao;
        deleteAllEntriesAsyncTask(TextDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
