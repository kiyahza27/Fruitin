package com.zakiyahhamidah.fruitinapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zakiyahhamidah.fruitinapp.Model.Data;
import com.zakiyahhamidah.fruitinapp.Model.DataResponse;
import com.zakiyahhamidah.fruitinapp.R;

import java.util.ArrayList;
import java.util.List;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.ViewHolder> {

    private List<DataResponse> dataResponseList = new ArrayList<>();
    private List<Data> dataList = new ArrayList<>();
    private Context context;

    public BookmarksAdapter(Context context, List<DataResponse> dataResponseList, List<Data> dataList){
        this.dataResponseList = dataResponseList;
        this.dataList = dataList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bm_items,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.favTextView.setText(dataResponseList.get(position).getName());
        holder.favImageView.setImageResource(dataList.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return dataResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView favTextView;
        Button favBtn;
        ImageView favImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favTextView = itemView.findViewById(R.id.bm);
            favBtn = itemView.findViewById(R.id.bmBtn);
            favImageView = itemView.findViewById(R.id.img_bm);
        }
    }
}
