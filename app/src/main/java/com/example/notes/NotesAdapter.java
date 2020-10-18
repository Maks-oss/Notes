package com.example.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    private ArrayList<Note> mDataset;
    private View.OnClickListener mOnClickListener;
    private View.OnLongClickListener mOnTouchListener;

    public void setmOnTouchListener(View.OnLongClickListener mOnTouchListener) {
        this.mOnTouchListener = mOnTouchListener;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView date,name;
        public LinearLayout linearLayout;
        public MyViewHolder(View v) {
            super(v);
            date=v.findViewById(R.id.date);
            name=v.findViewById(R.id.Name);
            linearLayout=v.findViewById(R.id.layout);
        }
    }
    public void setOnClickListener(View.OnClickListener mOnClickListener){
        this.mOnClickListener=mOnClickListener;
    }


    public NotesAdapter(ArrayList<Note> myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public NotesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);
        v.setOnClickListener(mOnClickListener);
        v.setOnLongClickListener(mOnTouchListener);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(NotesAdapter.MyViewHolder holder, final int position) {
        Note a=mDataset.get(position);
        if(a!=null) {
               holder.name.setText(a.getName());
               holder.date.setText(a.getDate());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
