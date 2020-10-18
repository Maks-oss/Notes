package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private  RecyclerView recyclerView;
    private  ArrayList<Note> arrayList;
    private NotesAdapter notesAdapter;
    private SQLiteDatabase database;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.notes);
        database=new SQLiteDatabase(this);
        linearLayoutManager= new LinearLayoutManager(this);

        arrayList = database.listNotes();
       //database.clear();
        notesAdapter=new NotesAdapter(arrayList);
        notesAdapter.setOnClickListener(new MyOnClickListener());
        notesAdapter.setmOnTouchListener(new MyOnTouchListener());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(notesAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        switch (item.getItemId())
        {
            case R.id.add:

                arrayList.add(new Note("NAME"+(arrayList.size()+1),formatter.format(date)));
                notesAdapter.notifyItemChanged(arrayList.size()-1);
                notesAdapter.notifyDataSetChanged();


                recyclerView.scrollToPosition(Objects.requireNonNull(recyclerView.getAdapter()).getItemCount()-1);

                database.addNote(arrayList.get(arrayList.size()-1));
                break;
            case R.id.delete:

                ArrayList<Note>arr=new ArrayList<>(arrayList);
                for (int i = 0; i < arr.size(); i++) {

                            if (Objects.requireNonNull(linearLayoutManager.findViewByPosition(i)).getBackground().getConstantState() == getResources().getDrawable(R.drawable.color).getConstantState()) {
                                Objects.requireNonNull(linearLayoutManager.findViewByPosition(i)).setBackgroundColor(Color.DKGRAY);
                                Iterator iterator = arrayList.iterator();
                                while (iterator.hasNext()) {
                                    if (arr.get(i).equals(iterator.next())) {
                                        iterator.remove();
                                        database.deleteNote(arr.get(i).getName());
                                    }
                                }
                                notesAdapter.notifyItemRemoved(i);
                                notesAdapter.notifyItemRangeChanged(i, arrayList.size());
                            }


                }


                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int itemPosition = recyclerView.getChildLayoutPosition(view);
            Note item = arrayList.get(itemPosition);
            Intent intent=new Intent(MainActivity.this, ContentFragment.class);
            intent.putExtra("Note",item);
            startActivity(intent);

        }
    }

    private class MyOnTouchListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View view) {
            int itemPosition = recyclerView.getChildLayoutPosition(view);


                    if (Objects.requireNonNull(linearLayoutManager.findViewByPosition(itemPosition)).getBackground().getConstantState() == getResources().getDrawable(R.drawable.color).getConstantState()) {
                        Objects.requireNonNull(linearLayoutManager.findViewByPosition(itemPosition)).setBackgroundColor(Color.DKGRAY);
                    } else {
                        Objects.requireNonNull(linearLayoutManager.findViewByPosition(itemPosition)).setBackground(getResources().getDrawable(R.drawable.color));
                    }

                return true;
        }
    }
}