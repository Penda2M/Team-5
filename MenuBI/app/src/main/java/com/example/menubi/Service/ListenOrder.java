package com.example.menubi.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.menubi.Model.Request;
import com.example.menubi.OrderStatus;
import com.example.menubi.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class ListenOrder extends Service implements ChildEventListener {
    FirebaseDatabase db;
    DatabaseReference orders;
    public ListenOrder() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = FirebaseDatabase.getInstance();
        orders = db.getReference("Request");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        orders.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;


    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Request request = dataSnapshot.getValue(Request.class);
        if (request.getStatus().equals("0")){
            showNotification(dataSnapshot.getKey(),request);
        }
    }

    private void showNotification(String key, Request request) {
        Intent intent = new Intent(getBaseContext(), OrderStatus.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intent,0);
        NotificationCompat.Builder builder =new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setTicker("EDMTDev")
                .setContentInfo("Nouvelle Commande")
                .setContentText("Vous avez une nouvelle notification"+key)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);

        int randomint = new Random().nextInt(9999-1)+1;
        notificationManager.notify(randomint,builder.build());

    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
