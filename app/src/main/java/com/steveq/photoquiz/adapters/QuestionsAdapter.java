package com.steveq.photoquiz.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.steveq.photoquiz.R;
import com.steveq.photoquiz.database.DatabaseManager;
import com.steveq.photoquiz.database.model.Objects;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>{

    private List<Objects> data;
    private Context mContext;

    public QuestionsAdapter(Context ctx) {
        this.mContext = ctx;
        data = DatabaseManager.getInstance(mContext).getRandomListObject();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder{
        TextView questionTextView;
        ImageButton cameraImageButton;
        ImageView statusImageView;

        public QuestionViewHolder(View itemView) {
            super(itemView);

            questionTextView = (TextView) itemView.findViewById(R.id.questionTextView);
            cameraImageButton = (ImageButton) itemView.findViewById(R.id.cameraImageButton);
            statusImageView = (ImageView) itemView.findViewById(R.id.questionStatusImageView);
        }
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_row, parent, false);

        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        holder.questionTextView.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
