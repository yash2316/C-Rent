package com.example.test;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<ModelClass> carList;
    private OnItemClickListener listener;



    public RecyclerViewAdapter(List<ModelClass>carList) {
        this.carList = carList;

    }

    public void setFilteredList(List<ModelClass> filteredList){
        this.carList = filteredList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        int resource = carList.get(position).getImage();
        String name = carList.get(position).getCarName();
        String prc = carList.get(position).getPrice();
        String rtg = carList.get(position).getRating();

        holder.setData(resource, name, prc, rtg);


    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView pic;
        private TextView nm;
        private TextView p;
        private TextView r;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            pic = itemView.findViewById(R.id.imageView);
            nm = itemView.findViewById(R.id.car_name);
            p = itemView.findViewById(R.id.price);
            r = itemView.findViewById(R.id.rating2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });


        }

        public void setData(int resource, String name, String prc, String rtg) {

            pic.setImageResource(resource);
            nm.setText(name);
            p.setText(prc + "₹");
            r.setText(rtg);

        }


    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}