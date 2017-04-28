package com.fiuady.android.compustore;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.android.compustore.db.Assemblies;
import com.fiuady.android.compustore.db.Category;
import com.fiuady.android.compustore.db.Inventory;
import com.fiuady.android.compustore.db.Products;

import java.util.List;


public class AddNewProductActivity extends AppCompatActivity {

    private Spinner Sproducts;
    private Inventory inventory;
    private ImageButton Buscador;    //nuevo
    private EditText EditorProductos;
    public String item;
    public String texto_escrito;
    //private ImageButton Addproductbtn;

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition();
                    CharSequence options[] = new CharSequence[]{"Agregar al ensamble"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddNewProductActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Elige una opción: ");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            final Products productos = adapter2.products.get(pos);

                            if (which == 0)
                            {

                                Assemblies assemblies = new Assemblies(9999,"");
                                if(inventory.addProductInAssembly(productos,assemblies))
                                {
                                    Toast.makeText(AddNewProductActivity.this,"Se agregó el producto al ensamble",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(AddNewProductActivity.this,"El producto ya esta en el ensamble",Toast.LENGTH_SHORT).show();
                                }
                                setResult(RESULT_OK);
                                finish();
                            }

                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                    //Toast.makeText(getApplicationContext(), String.valueOf(pos),Toast.LENGTH_SHORT).show();
                }
            });
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
        setContentView(R.layout.activity_add_new_product);
        inventory = new Inventory(getApplicationContext());

        Sproducts =  (Spinner)findViewById(R.id.products);
        Buscador = (ImageButton)findViewById(R.id.search_products);              //nuevo
        EditorProductos = (EditText)findViewById(R.id.search_products_editbox);
        //Addproductbtn = (ImageButton) findViewById(R.id.imageButtonAddProducts);


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


        Buscador.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //String item = Sproducts.getItemAtPosition()

                String producto_buscado =  EditorProductos.getText().toString();

                texto_escrito = producto_buscado;

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

    public void update_recyclerview ()
    {
        if(texto_escrito == "")

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
                adapter2 = new ProductsAdapter(inventory.getallProductsineverycategory(texto_escrito));
                recyclerView.setAdapter(adapter2);
            }

            else
            {
                adapter2 = new ProductsAdapter(inventory.getoneProducts(item,texto_escrito));
                recyclerView.setAdapter(adapter2);
            }

        }
    }


}

