package com.loktra.githubcommits.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.loktra.githubcommits.R;
import com.loktra.githubcommits.model.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ayushdeothia on 02/08/17.
 */

public class CommitRecyclerAdapter extends RecyclerView.Adapter<CommitRecyclerAdapter.CommitViewHolder>{
    private List<Item> itemList;
    private Context mContext;

    public CommitRecyclerAdapter(List<Item> itemList, Context mContext) {
        this.itemList = itemList;
        this.mContext = mContext;
    }

    @Override
    public CommitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_commit_recycler_view, parent, false);

        return new CommitRecyclerAdapter.CommitViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommitViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.personName.setText(item.getAuthor().getLogin());
        holder.commitID.setText("commit: " + item.getSha());
        holder.commitMessage.setText("commit: " + item.getCommit().getMessage());
        Glide.with(mContext).load(item.getAuthor().getAvatarUrl()).apply(RequestOptions.placeholderOf(R.color.colorPrimary)).into(holder.authorPic);
    }

    @Override
    public int getItemCount() {
        if (itemList == null) return 0;
        return itemList.size();
    }

    public class CommitViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.author_image)
        ImageView authorPic;

        @BindView(R.id.tv_person_name)
        TextView personName;

        @BindView(R.id.tv_commit_message)
        TextView commitMessage;

        @BindView(R.id.tv_commit_id)
        TextView commitID;

        public CommitViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
