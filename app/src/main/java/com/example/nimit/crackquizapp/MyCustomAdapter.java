package com.example.nimit.crackquizapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyCustomAdapter extends RecyclerView.Adapter{

    private List<UserRecord> userRecordList;
    private TextView textViewFirstName, textViewStudentGrade;


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewFirstName = (TextView)itemView.findViewById(R.id.textViewFirstName);
            textViewStudentGrade = (TextView)itemView.findViewById(R.id.textViewStudentGrade);
        }
    }

    public MyCustomAdapter(List<UserRecord> userRecordList){
        this.userRecordList = userRecordList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       UserRecord userRecord = userRecordList.get(position);
       textViewFirstName.setText(userRecord.fname);
       textViewStudentGrade.setText(userRecord.grade+"");

    }

    @Override
    public int getItemCount() {
        return userRecordList.size();
    }
}
