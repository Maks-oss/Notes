package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ContentFragment extends AppCompatActivity {

    private static final String TAG = "list";
    private EditText text,name;
    private SQLiteDatabase database;
    private Note note;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_content);
        note = (Note) getIntent().getSerializableExtra("Note");
        text=findViewById(R.id.notesText);
        name=findViewById(R.id.notesTitle);
        database=new SQLiteDatabase(this);
        assert note != null;
        name.setText(note.getName().substring(0,4).equals("NAME")?"":note.getName());
        text.setText(note.getText());
    }

    @Override
    public void onBackPressed() {

        String oldName=new String(note.getName());
        note.setName(name.getText().toString());
        note.setText(text.getText().toString());
        database.updateNote(note,oldName);
        Intent intent=new Intent(ContentFragment.this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
