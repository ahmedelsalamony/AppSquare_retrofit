package com.example.ahmed.appsquare_retrofit.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmed.appsquare_retrofit.MyApplication;
import com.example.ahmed.appsquare_retrofit.R;
import com.example.ahmed.appsquare_retrofit.listener.ItemClickListener;
import com.example.ahmed.appsquare_retrofit.model.RepoResult;

import java.util.List;

/**
 * Created by ahmed on 5/10/2017.
 */

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {
    private List<RepoResult> repos;
    private int rowLayout;
    private Context context;
    Button btnRepo,btnOwner;



    public RepoAdapter(List<RepoResult> repos, int rowLayout, Context context) {
        this.repos = repos;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RepoViewHolder holder, int position) {
        animate(holder);
        holder.txtRepoName.setText(repos.get(position).getName());
        holder.txtDescription.setText(repos.get(position).getDescription());
        holder.txtOwnerUserName.setText(repos.get(position).getOwnerResults().getLogin());
        if (repos.get(position).isFork()){
            holder.linearContainer.setBackgroundColor(Color.WHITE);
        }else if (!repos.get(position).isFork()){
            holder.linearContainer.setBackgroundColor(Color.GREEN);
        }

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, final int position, boolean isLongClick)
            {
                if (isLongClick)
                {
                    Dialog d=new Dialog(context);
                    d.setContentView(R.layout.custom_dialog);
                    d.setTitle("choose one ");
                    btnRepo=(Button)d.findViewById(R.id.custom_dialog_btnRepo);
                    btnOwner=(Button)d.findViewById(R.id.custom_dialog_btnOwner);
                    d.show();
                    btnRepo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(MainActivity.this, data_moModel.getRepoHTMLURL(), Toast.LENGTH_SHORT).show();
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(repos.get(position).getHtmlUrl()));
                            context.startActivity(browserIntent);
                        }
                    });

                    btnOwner.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(repos.get(position).getOwnerResults().getHtmlUrl()));
                            context.startActivity(browserIntent);
                        }
                    });}

            }
        });



    }

    @Override
    public int getItemCount() {
        return repos.size();
    }


    public class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView txtRepoName, txtDescription, txtOwnerUserName;
        LinearLayout linearContainer;
        private ItemClickListener clickListener;
        public RepoViewHolder(View convertView) {
            super(convertView);
            txtRepoName = (TextView) convertView.findViewById(R.id.single_row_txtRepoName);
            txtDescription = (TextView) convertView.findViewById(R.id.single_row_txtDescription);
            txtOwnerUserName = (TextView) convertView.findViewById(R.id.single_row_txtOwnerUserName);
            linearContainer=(LinearLayout) convertView.findViewById(R.id.single_row_containerListItem);
            convertView.setOnClickListener(this);
            convertView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener){
            this.clickListener=itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }

    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }
}
