package com.example.menubi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menubi.Common.Common;
import com.example.menubi.Model.Request;
import com.example.menubi.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference request;
    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        database=FirebaseDatabase.getInstance();
        request = database.getReference("Request");
        recyclerView= (RecyclerView)findViewById(R.id.listOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadOrder(Common.curentUser.getRefTable());
        
    }

    private void loadOrder(String refTable) {
        adapter= new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                request.orderByChild("refTable").equalTo(refTable)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, Request request, int i) {
                        orderViewHolder.txtOrderId.setText(adapter.getRef(i).getKey());
                        orderViewHolder.txtOrderStatus.setText(convertCodeToStatus(request.getStatus()));
                        orderViewHolder.txtOrderPhone.setText(request.getRefTable());
                      
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status) {
        if (status.equals("0")){
            return "Placer";
        }
        else if (status.equals("1")){
            return "Sur mon Chemin";
        }
        else return "Envoye";
    }
}
