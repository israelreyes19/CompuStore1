package com.fiuady.android.compustore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.fiuady.android.compustore.db.Category;
import com.fiuady.android.compustore.db.Inventory;
import com.fiuady.android.compustore.db.Products;

import java.util.List;


public class ProductosActivity extends AppCompatActivity {

    private Spinner Sproducts;
    private Inventory inventory;
    private ImageButton Buscador;    //nuevo
    private EditText EditorProductos;
    public String item;

    private class ProductsHolder extends RecyclerView.ViewHolder
    {


        private TextView id;
        private TextView category;
        private  TextView description;
        private TextView price;
        private TextView quantity;


        public ProductsHolder(View itemView) {
            super(itemView);
            category = (TextView) itemView.findViewById(R.id.txt_category);
            id = (TextView) itemView.findViewById(R.id.txt_id);
            description = (TextView) itemView.findViewById(R.id.txt_description);
            price =  (TextView) itemView.findViewById(R.id.txt_price);
            quantity = (TextView) itemView.findViewById(R.id.txt_qty);

        }

        public void bindProducts(Products products)
        {

            category.setText(String.valueOf(products.getCategory_id()));

            description.setText(products.getDescription());
            price.setText(String.valueOf(products.getPrice()));
            quantity.setText(String.valueOf(products.getQty()));
            id.setText(String.valueOf(products.getId()));
        }
    }

    private class ProductsAdapter extends RecyclerView.Adapter<ProductsHolder>
    {

        private List<Products> products;
        public ProductsAdapter(List<Products> products){this.products = products;}

        @Override
        public ProductsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
            return new ProductsHolder(v);

        }

        @Override
        public void onBindViewHolder(ProductsHolder holder, int position) {
            holder.bindProducts(products.get(position));
        }

        @Override
        public int getItemCount() {
            return products.size();
        }
    }


    private RecyclerView recyclerView;
    private ProductsAdapter adapter2;
    private Toolbar toolbar;
    private ImageButton backbutton;
    private ImageButton addclientbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        inventory = new Inventory(getApplicationContext());

        Sproducts =  (Spinner)findViewById(R.id.products);
        Buscador = (ImageButton)findViewById(R.id.search_products);              //nuevo
        EditorProductos = (EditText)findViewById(R.id.search_products_editbox);

        ArrayAdapter<String > adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        Sproducts.setAdapter((adapter));

        List<Products> products = inventory.getallProducts();


        adapter.add("Todos");
        //  adapter.add("Disco duro");
        //   adapter.add("Memoria");
        //  adapter.add("Monitor");
        // adapter.add("Procesador");
        // adapter.add("Tarjeta madre");
        // adapter.add("Tarjeta de video");
        //adapter.add("Tarjeta de sonido");

        for (Category c : inventory.getAllCategories())
        {
            adapter.add(c.getDescription());
        }


        recyclerView = (RecyclerView) findViewById(R.id.products_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

       // adapter2 = new ProductsAdapter(products);
        //recyclerView.setAdapter(adapter2);

        Sproducts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                item = parent.getItemAtPosition(position).toString();
/*
                if(item == "Todos")
                {
                    adapter2 = new ProductsAdapter(inventory.getallProducts());
                    recyclerView.setAdapter(adapter2);
                }

                else
                {
                    adapter2 = new ProductsAdapter(inventory.getonecategoryproduct(item));
                    recyclerView.setAdapter(adapter2);
                }
            */

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Buscador.setOnClickListener(new View.OnClickListener(){     //nuevo
            @Override
            public void onClick(View v) {

                //String item = Sproducts.getItemAtPosition()

                String producto_buscado =  EditorProductos.getText().toString();

                if(producto_buscado == "")

                {
                    if(item == "Todos")
                    {
                        adapter2 = new ProductsAdapter(inventory.getallProducts());
                        recyclerView.setAdapter(adapter2);
                    }

                    else
                    {
                        adapter2 = new ProductsAdapter(inventory.getonecategoryproduct(item));
                        recyclerView.setAdapter(adapter2);
                    }

                }
                else
                {
                    if(item == "Todos")
                    {
                        adapter2 = new ProductsAdapter(inventory.getallProductsineverycategory(producto_buscado));
                        recyclerView.setAdapter(adapter2);
                    }

                    else
                    {
                        adapter2 = new ProductsAdapter(inventory.getoneProducts(item,producto_buscado));
                        recyclerView.setAdapter(adapter2);
                    }

                }

            }
        });

    }
}
