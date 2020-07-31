package com.example.roomdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewModel viewModel;
    private List<Word> mWords;
    CustomAdapter adapter;
    StaggeredGridLayoutManager staggaggeredGridLayoutManager;
    public static final int REQUESTCODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new CustomAdapter(this, (v, position) -> {
            Intent intent = new Intent(MainActivity.this, AddText.class);
        });
        staggaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(staggaggeredGridLayoutManager);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.getAllWords().observe(this, adapter::setWords);
        Button add = findViewById(R.id.button);
        add.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddText.class);
            startActivityForResult(intent, REQUESTCODE);
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    // We are not implementing onMove() in this app
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    // When the use swipes a word,
                    // delete that word from the database.
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Word myWord = adapter.getWordAtPosition(position);
                        Toast.makeText(MainActivity.this,
                                getString(R.string.delete) + " " +
                                        myWord.getWord(), Toast.LENGTH_LONG).show();

                        // Delete the word
                        viewModel.deleteWord(myWord);
                    }
                });
        // Attach the item touch helper to the recycler view
        helper.attachToRecyclerView(recyclerView);
    }

    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(AddText.REPLY));
            viewModel.insert(word);
        } else {
            Toast.makeText(getApplicationContext(), R.string.empty, Toast.LENGTH_LONG).show();
        }
    }

    public void deleteEntry(RecyclerView.ViewHolder viewHolder){
        int position = viewHolder.getAdapterPosition();
        Word myWord = adapter.getWordAtPosition(position);
        Toast.makeText(MainActivity.this,
                getString(R.string.delete) + " " +
                        myWord.getWord(), Toast.LENGTH_LONG).show();

        // Delete the word
        viewModel.deleteWord(myWord);
    }

}