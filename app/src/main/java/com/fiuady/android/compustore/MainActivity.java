package com.fiuady.android.compustore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fiuady.android.compustore.db.Category;
import com.fiuady.android.compustore.db.Inventory;

public class MainActivity extends AppCompatActivity {


    private ImageButton products;
    private ImageButton categories;
    private ImageButton assemblies;
    private ImageButton clients;
    private ImageButton orders;
    private ImageButton reports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        products = (ImageButton)findViewById(R.id.imageButtonProducts);
        categories = (ImageButton)findViewById(R.id.imageButtonCategories);
        assemblies = (ImageButton)findViewById(R.id.imageButtonAssemblies);
        clients = (ImageButton)findViewById(R.id.imageButtonClients);
        orders = (ImageButton)findViewById(R.id.imageButtonOrders);
        reports = (ImageButton)findViewById(R.id.imageButtonReportes);


        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CategoriesActivity.class);
                startActivity(i);

            }
        });

        clients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, client.class);
                startActivity(i);
            }
        });

        assemblies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AssembliesActivity.class);
                startActivity(i);
            }
        });

        products.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ProductosActivity.class);
                startActivity(i);
            }
        });




    }
}
