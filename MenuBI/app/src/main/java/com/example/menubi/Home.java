package com.example.menubi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menubi.Common.Common;
import com.example.menubi.Interface.ItemClickListener;
import com.example.menubi.Model.Categorie;
import com.example.menubi.Service.ListenOrder;
import com.example.menubi.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseDatabase bd;
    DatabaseReference categorie;
    TextView txtFullName;
    RecyclerView reciMenu;
    RecyclerView.LayoutManager layoutManager;
     FirebaseRecyclerAdapter<Categorie, MenuViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);
        //init database
        bd = FirebaseDatabase.getInstance();
        categorie= bd.getReference("Categorie");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartInten = new Intent(Home.this, Cart.class);
                startActivity(cartInten);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       /* mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/
       //Definir le nom d'utilisateur
        View hdView = navigationView.getHeaderView(0);
        txtFullName = (TextView)hdView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.curentUser.getProfil());
        reciMenu =(RecyclerView) findViewById(R.id.reci_menu);
        reciMenu.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        reciMenu.setLayoutManager(layoutManager);
        loadMenu();
        Intent service = new Intent(Home.this, ListenOrder.class);
        startService(service);
    }

    private void loadMenu() {
        adapter = new FirebaseRecyclerAdapter<Categorie, MenuViewHolder>(Categorie.class, R.layout.menu_item, MenuViewHolder.class,categorie) {
            @Override
            protected void populateViewHolder(MenuViewHolder menuViewHolder, Categorie categorie, int i) {
                menuViewHolder.txtMenuName.setText(categorie.getName());
                Picasso.with(getBaseContext()).load(categorie.getImage()).into(menuViewHolder.imageView);
                final Categorie clckItem = categorie;
                menuViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean islongClick) {
                        //recup Categori et une nouvelle activite
                        Intent it = new Intent(Home.this, ListePlat.class);
                        //CategorieId est la cle primaire
                        it.putExtra("CategorieId",adapter.getRef(position).getKey());
                        startActivity(it);
                    }
                });
            }
        };
        reciMenu.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.nav_menu){}
        else if (id == R.id.nav_cart){
            Intent intentC = new Intent(Home.this, Cart.class);
            startActivity(intentC);
        }
        else if (id == R.id.nav_orders){
            Intent intentO = new Intent(Home.this, OrderStatus.class);
            startActivity(intentO);
        }
        DrawerLayout dr = (DrawerLayout)findViewById(R.id.drawer_layout);
        dr.closeDrawer(GravityCompat.START);
        return true;
    }

    /*@Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }*/
}
