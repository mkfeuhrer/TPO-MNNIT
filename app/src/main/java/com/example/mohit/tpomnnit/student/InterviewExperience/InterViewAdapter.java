package com.example.mohit.tpomnnit.student.InterviewExperience;

/**
 * Created by mohit on 23/9/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohit.tpomnnit.R;

import java.util.List;

public class InterViewAdapter extends RecyclerView.Adapter<com.example.mohit.tpomnnit.student.InterviewExperience.InterViewAdapter.MyViewHolder> {
    private List<Interview> interviewList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,company,date;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.textName);
            company = (TextView) view.findViewById(R.id.textCompany);
            date = (TextView) view.findViewById(R.id.textDate);
            view.setClickable(true);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopup(view);
                    Log.e("listing","Item selected is "+getPosition());
                }
            });
        }


    }
    public InterViewAdapter(List<Interview> interviewList)
    {
        this.interviewList = interviewList;
    }
    @Override
    public com.example.mohit.tpomnnit.student.InterviewExperience.InterViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.interview_row,parent,false);
        return new com.example.mohit.tpomnnit.student.InterviewExperience.InterViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Interview ques=interviewList.get(position);
        holder.name.setText(ques.getName());
        holder.company.setText(ques.getCompany());
        holder.date.setText(ques.getDate());
    }

    @Override
    public int getItemCount() {
        return interviewList.size();
    }

    public void showPopup(View view) {
        View popupView = LayoutInflater.from(view.getContext()).inflate(R.layout.popup_layout, null);
        float density= view.getResources().getDisplayMetrics().density;
        final PopupWindow popupWindow = new PopupWindow(popupView, (int)density * 300 , (int)density*400);
        popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        Button btnDismiss = (Button) popupView.findViewById(R.id.ib_close);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_SCROLL) {
                    popupWindow.dismiss();
                }

                if (event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                    popupWindow.dismiss();
                }
                return false;
            }
        });
        popupWindow.showAsDropDown(popupView, 0, 0);
    }
}


