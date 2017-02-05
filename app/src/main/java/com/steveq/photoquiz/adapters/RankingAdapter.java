package com.steveq.photoquiz.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.steveq.photoquiz.R;
import com.steveq.photoquiz.database.DatabaseManager;
import com.steveq.photoquiz.database.model.Players;

import java.util.ArrayList;
import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {

    private List<Players> data;
    private Context mContext;

    public RankingAdapter(Context ctx){
        mContext = ctx;
        data = DatabaseManager.getInstance(ctx).getBestPlayers();
    }

    public class RankingViewHolder extends RecyclerView.ViewHolder{

        TextView nameTextView;
        TextView scoreTextView;
        ImageView starImageView;

        public RankingViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            scoreTextView = (TextView) itemView.findViewById(R.id.scoreTextView);
            starImageView = (ImageView) itemView.findViewById(R.id.startImageView);
        }
    }

    public void updateAdapter(){
        data = DatabaseManager.getInstance(mContext).getBestPlayers();
        notifyDataSetChanged();
    }


    @Override
    public RankingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_row, parent, false);
        return new RankingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RankingViewHolder holder, int position) {
        holder.nameTextView.setText(data.get(position).getName());
        holder.scoreTextView.setText(String.valueOf(data.get(position).getScore()));
        if(position > 3){
            holder.starImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
