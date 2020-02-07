package com.example.menubi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager mScreenPager;
    IntroViewPageAdapter introViewPageAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Menu du restaurant", " Cette page Contient l'ensemble des variétés que nous proposons dans notre menu ",R.drawable.food));
        mList.add(new ScreenItem("Entrées", "Dans cette page vous avez les plats que nous proposons en entréesqu'elles soient chaudes ou froides",R.drawable.menuStart));
        mList.add(new ScreenItem("Espace de paiement", "Vous pairez facilment votre commande via les syst_mes de transfert existant",R.drawable.payment));
        mList.add(new ScreenItem("Espace d'appréciation", "Dans cette page vous allez nous aidé à pouvoir améliorer notre ualité de service et les plats que nous proposons",R.drawable.note));

        mScreenPager = findViewById(R.id.screen_viewpager);
        introViewPageAdapter = new IntroViewPageAdapter(this, mList);
        mScreenPager.setAdapter(introViewPageAdapter);
    }
}
