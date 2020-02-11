package com.example.menubi.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menubi.Interface.ItemClickListener;
import com.example.menubi.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txtOrderId ,txtOrderStatus ,txtOrderPhone;
    //public ImageView img_cart_count;
    private ItemClickListener itemClickListener;
    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        txtOrderStatus = (TextView)itemView.findViewById(R.id.order_status);
        txtOrderId = (TextView)itemView.findViewById(R.id.order_id);
        txtOrderPhone = (TextView)itemView.findViewById(R.id.order_refT);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
       itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
