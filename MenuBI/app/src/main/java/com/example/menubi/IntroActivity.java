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
        mList.add(new ScreenItem("Application", " Menu Bi est votre nouvelle application pour la dématérialisaattion du menu en papier. Plus besoin de lire sans savoir à quoi nous attendre ; en quelques click vous allez activité vos papilles et savoir à quoi vous attendre",R.drawable.food));
        mList.add(new ScreenItem("Menu du restaurant", "Dans cette page vous avez les plats que nous proposons en dans notre menu qu'ils soient en entrées qu'elles soient chaudes ou froides, en plat principal comme en dessert et en collations. Des variétés exceptionnelles dans notre menu pour une satisfaction complète ",R.drawable.menu));
        mList.add(new ScreenItem("Espace de paiement", "Vous paierez facillement votre commande depuis votre table sur cette interface oubien via les systèmes de transfert existant",R.drawable.payment));
        mList.add(new ScreenItem("Espace d'appréciation", "Commandez, mangez à votre aise mais aussi apportez votre participation en notons notre qualité de service ou la saveur et présentation de vos plats que nous proposons",R.drawable.note));

        mScreenPager = findViewById(R.id.screen_viewpager);
        introViewPageAdapter = new IntroViewPageAdapter(this, mList);
        mScreenPager.setAdapter(introViewPageAdapter);
    }
}
