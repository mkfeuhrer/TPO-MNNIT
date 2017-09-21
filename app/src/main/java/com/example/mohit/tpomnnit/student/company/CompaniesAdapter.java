package com.example.mohit.tpomnnit.student.company;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohit.tpomnnit.R;

import java.util.List;

/**
 * Created by neera on 9/2/2017.
 */

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.MyViewHolder> {
    private List<Companies> companiesList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView question, category, difficulty;

        public MyViewHolder(View view) {
            super(view);
            question = (TextView) view.findViewById(R.id.textQues);
            category = (TextView) view.findViewById(R.id.textCategory);
            difficulty = (TextView) view.findViewById(R.id.textDifficulty);
        }
    }
    public CompaniesAdapter(List<Companies> companiesList)
    {
        this.companiesList=companiesList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.company_row,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Companies ques=companiesList.get(position);
        holder.question.setText(ques.getName());
        holder.difficulty.setText(ques.getCtc());
        holder.category.setText(ques.getLocation());
    }

    @Override
    public int getItemCount() {
        return companiesList.size();
    }
}

