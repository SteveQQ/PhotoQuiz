package com.steveq.photoquiz.adapters;


import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.steveq.photoquiz.FilesUtils;
import com.steveq.photoquiz.R;
import com.steveq.photoquiz.database.DatabaseManager;
import com.steveq.photoquiz.database.model.Objects;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>{

    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final String TAG = QuestionsAdapter.class.getSimpleName();
    private List<Objects> data;
    private AppCompatActivity mActivity;
    private FilesUtils mFilesUtils;
    private long mPlayerId;

    public QuestionsAdapter(AppCompatActivity activity, long id) {
        this.mActivity = activity;
        data = DatabaseManager.getInstance(mActivity).getRandomListObject();
        mFilesUtils = new FilesUtils(activity);
        mPlayerId = id;
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
        holder.cameraImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri outUri = mFilesUtils.getOutputUri(mPlayerId);
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
                mActivity.startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);
                Log.d(TAG, outUri.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
