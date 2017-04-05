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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageButton Addproductbtn;

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
        Addproductbtn = (ImageButton) findViewById(R.id.imageButtonAddProducts);


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


        Addproductbtn.setOnClickListener(new View.OnClickListener() {     //nuevo
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductosActivity.this);
                View product_view = getLayoutInflater().inflate(R.layout.add_product, null);
               // LinearLayout product_layout = new LinearLayout(ProductosActivity.this);
               // product_layout.setOrientation(LinearLayout.VERTICAL);
                final EditText nombre = (EditText) product_view.findViewById(R.id.Nombre_producto);
                final EditText precio = (EditText) product_view.findViewById(R.id.Precio_producto);
                final Spinner catgeorias_posibles = (Spinner) product_view.findViewById(R.id.product_spinner);
                ArrayAdapter<String > add_adapter = new ArrayAdapter<String>(ProductosActivity.this, android.R.layout.simple_spinner_dropdown_item);
                catgeorias_posibles.setAdapter((add_adapter));
                for (Category c : inventory.getAllCategories())
                {
                    add_adapter.add(c.getDescription());
                }
                //final String text = "0";

                builder.setTitle("Agregar nuevo producto");


                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                      if(!nombre.getText().toString().isEmpty() && !precio.getText().toString().isEmpty())
                      {
                          String text = catgeorias_posibles.getSelectedItem().toString();
                          int aux = inventory.return_categroty_id(text);
                          // String aux = inventory.return_categroty_id(text);
                          String valor_nombre = nombre.getText().toString();
                          String valor_precio = precio.getText().toString();
                          String categoria = String.valueOf(aux);
                          String cantidad = "0";
                          inventory.addProduct(999,categoria,valor_nombre,valor_precio,cantidad);
                          Toast.makeText(ProductosActivity.this,"Producto guardado exitosamente", Toast.LENGTH_SHORT).show();
                        }
                        else
                      {
                          Toast.makeText(ProductosActivity.this,"Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
                      }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

               // builder.show();
                builder.setView(product_view);

              //  builder.setView(product_layout);
                builder.show();
            }
        });



    }
}
