package com.example.inmotestapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> implements Filterable {

    private List<User> dataUser;
    private List<User> dataUserFull;
    public Context context;

    public final int TYPE_USER = 0;
    public final int TYPE_LOAD = 1;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;




    public CustomAdapter(List<User> dataUser, Context context) {
        this.dataUser = dataUser;
        this.context=  context;
        dataUserFull = new ArrayList<>(dataUser);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_layout,parent,false);
        return new CustomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {

        customViewHolder.textUser.setText(dataUser.get(i).getName());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataUser.get(i).getAvatar_url())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(customViewHolder.imgUser);

    }

    @Override
    public int getItemCount() {
        return dataUser.size();
    }

    @Override
    public Filter getFilter() {
        return dataFilter;
    }

    public Filter dataFilter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<User> filteredlist = new ArrayList<>();

            if(constraint == null|| constraint.length() ==0){
                filteredlist.addAll(dataUserFull);
            }
            else{
                String patternfill = constraint.toString().toLowerCase().trim();

                for(User item: dataUserFull){
                    if(item.getName().toLowerCase().contains(patternfill)){
                        filteredlist.add(item);
                    }
                }
            }

            FilterResults rs = new FilterResults();
            rs.values = filteredlist;
            return rs;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataUser.clear();
            dataUser.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };



    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mview;
        TextView textUser ;
        ImageView imgUser;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
            textUser = mview.findViewById(R.id.user);
            imgUser = mview.findViewById(R.id.image);
        }
    }


    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }


    interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }



}
