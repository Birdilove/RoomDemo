package com.example.roomdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class AddText extends AppCompatActivity {
    public static final String REPLY = "Room Demo";
    private EditText editWord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        editWord = findViewById(R.id.edit_word);
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(editWord.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String word = editWord.getText().toString();
                replyIntent.putExtra(REPLY, word);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}