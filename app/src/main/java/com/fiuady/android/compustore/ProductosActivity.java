package com.fiuady.android.compustore;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    public String texto_escrito;
    public boolean chk_empty_list;
    private ImageButton Addproductbtn;
    private final String KEY_A0= "R0";
    private final String KEY_A1= "R1";
    private final String KEY_A2= "R2";
    private final String KEY_A3= "R3";
    private final String KEY_A4= "R4";
    private final String KEY_A5= "R5";
    private final String KEY_A6= "R6";



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
                    CharSequence options[] = new CharSequence[]{"Agregar stock","Modificar", "Eliminar"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductosActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Elige una opción: ");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            final Products productos = adapter2.products.get(pos);

                            if (which == 0)
                            {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(ProductosActivity.this);
                                builder1.setTitle("¿Cuantas unidades desea que tenga el producto?");
                                View product_view = getLayoutInflater().inflate(R.layout.add_product_quantity, null);
                                final NumberPicker agregar_cantidad = (NumberPicker)product_view.findViewById(R.id.qty_numberPicker);
                                //final Spinner agregar_cantidad = (Spinner) product_view.findViewById(R.id.product_spinner);
                               // ArrayAdapter<String > add_quantity_adapter = new ArrayAdapter<String>(ProductosActivity.this, android.R.layout.simple_spinner_dropdown_item);
                               // agregar_cantidad.setAdapter((add_quantity_adapter));
                                int qty_actual = productos.getQty();
                                agregar_cantidad.setMinValue(qty_actual);
                                agregar_cantidad.setMaxValue(100);
                              //  for(int i= qty_actual; i<=50; i++) {
                              //  }
                                builder1.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                String qty_nueva = String.valueOf(agregar_cantidad.getValue());
                                String producto_id = String.valueOf(productos.getId());
                                inventory.add_stock(producto_id,qty_nueva);
                                        Toast.makeText(ProductosActivity.this,"La cantidad fue agregada exitosamente " ,Toast.LENGTH_SHORT).show();
                                        update_recyclerview();
                                    }
                                });
                                builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                // builder.show();
                                builder1.setView(product_view);

                                //  builder.setView(product_layout);
                                builder1.show();

                            }

                           else if (which == 1)
                            {

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(ProductosActivity.this);
                                builder1.setTitle("Modificar producto existente");
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
                                nombre.setText(productos.getDescription());
                                final String aux_text = productos.getDescription();

                                double aux1 = productos.getPrice();
                                double division = (aux1)/100;

                                precio.setText(String.format("%.2f",division));

                                builder1.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {


                                        String text = catgeorias_posibles.getSelectedItem().toString();
                                        int aux = inventory.return_categroty_id(text);
                                        // String aux = inventory.return_categroty_id(text);
                                        String valor_nombre = nombre.getText().toString();
                                        String valor_precio = precio.getText().toString();
                                        String categoria = String.valueOf(aux);
                                        String cantidad = String.valueOf(productos.getQty());
                                        String ID = String.valueOf(productos.getId());

                                        String aux2 = valor_precio;

                                        if(aux2.contains("."))
                                        {
                                            aux2 = aux2.replace(".","");
                                        }
                                        else
                                        {
                                            aux2 = aux2 + "00";
                                        }


                                        if(!nombre.getText().toString().isEmpty() && !precio.getText().toString().isEmpty() && !aux2.equals("000"))
                                        {
                                            if(valor_nombre.equals(aux_text)){
                                                inventory.Update_product(ID,categoria,valor_nombre,aux2,cantidad);

                                                Toast.makeText(ProductosActivity.this,"El producto fue modificado exitosamente " ,Toast.LENGTH_SHORT).show();
                                                update_recyclerview();
                                        }
                                        else
                                            {

                                                if(inventory.check_product(valor_nombre) && aux2 != "000"){
                                                    inventory.Update_product(ID,categoria,valor_nombre,aux2,cantidad);

                                                    Toast.makeText(ProductosActivity.this,"El producto fue modificado exitosamente " ,Toast.LENGTH_SHORT).show();
                                                    update_recyclerview();
                                                }
                                                else
                                                {
                                                    Toast.makeText(ProductosActivity.this,"Operación no exitosa. "+
                                                            "Ya existe un producto con ese nombre " ,Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }

                                        else
                                        {
                                            Toast.makeText(ProductosActivity.this,"Operación no exitosa. "+
                                                    "Debe llenar todos los campos y el precio no puede ser 0.00", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                                builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                // builder.show();
                                builder1.setView(product_view);

                                //  builder.setView(product_layout);
                                builder1.show();


                            }
                            else if (which == 2)
                            {

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(ProductosActivity.this);
                                builder1.setTitle("¿Deseas borrar este producto?");
                                builder1.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        Inventory inventory = new Inventory(ProductosActivity.this);

                                        if(inventory.DeleteProduct(String.valueOf(productos.getId())))
                                        {
                                            //recyclerView.setAdapter(adapter);

                                            Toast.makeText(ProductosActivity.this,"Se eliminó exitosamente " +
                                                    "el producto",Toast.LENGTH_SHORT).show();
                                            update_recyclerview();
                                        }
                                        else
                                        {
                                            Toast.makeText(ProductosActivity.this,"No se pudo eliminar el producto. " +
                                                    "Existen ensambles con dicho producto",Toast.LENGTH_SHORT).show();
                                        }
                                        //recyclerView.setAdapter(adapter);
                                    }
                                });
                                builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });

                                builder1.show();
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

            double aux1 = products.getPrice();
            double division = (aux1)/100;
            category.setText(String.valueOf(products.getCategory_id()));
            description.setText(products.getDescription());
            price.setText(String.format("%.2f",division));
           // price.setText(String.valueOf(division));
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
    private ImageButton backbuttonp;
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
        backbuttonp = (ImageButton) findViewById(R.id.imageButtonBackProductos);

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

        //update_recyclerview();
       adapter2 = new ProductsAdapter(products);
        recyclerView.setAdapter(adapter2);


        backbuttonp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Sproducts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                item = parent.getItemAtPosition(position).toString();
/*
                if(adapter2.getItemCount() == 0)
                {
                    chk_empty_list = true;
                }
                else {
                    chk_empty_list = false;
                }

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

                      if(!nombre.getText().toString().isEmpty() && !precio.getText().toString().isEmpty() )
                      {
                          String text = catgeorias_posibles.getSelectedItem().toString();
                          int aux = inventory.return_categroty_id(text);
                          // String aux = inventory.return_categroty_id(text);
                          String valor_nombre = nombre.getText().toString();
                          String valor_precio = precio.getText().toString();
                          String categoria = String.valueOf(aux);
                          String cantidad = "0";

                          String aux2 = valor_precio;

                          if(aux2.contains("."))
                          {
                              aux2 = aux2.replace(".","");
                          }
                          else
                          {
                              aux2 = aux2 + "00";
                          }

                          if(inventory.check_product(valor_nombre) && !aux2.equals("000")){
                              inventory.addProduct(999,categoria,valor_nombre,aux2,cantidad);
                              Toast.makeText(ProductosActivity.this,"Producto guardado exitosamente", Toast.LENGTH_SHORT).show();
                              adapter2 = new ProductsAdapter(inventory.getallProductsineverycategory(valor_nombre));
                              recyclerView.setAdapter(adapter2);
                          }
                          else
                          {

                              Toast.makeText(ProductosActivity.this," Operación no exitosa. " +
                                      "Ya existe un producto con ese nombre o puso un precio de 0.00" ,Toast.LENGTH_SHORT).show();

                          }

                        }
                        else
                      {
                          Toast.makeText(ProductosActivity.this,"Operación no exitosa. "+
                                  "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
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


        if(savedInstanceState != null)
        {


            item =savedInstanceState.getString(KEY_A0,"");
            texto_escrito =savedInstanceState.getString(KEY_A1,"");
            chk_empty_list= savedInstanceState.getBoolean(KEY_A2,false);

            if(chk_empty_list == false)
            {
                update_recyclerview();
            }
            //txtOther.setText("Haciendo " + counter + " click(s) en Android");

        }

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


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Test app", "OnSaveInstanceState...");
        outState.putString(KEY_A0,item);
        outState.putString(KEY_A1,texto_escrito);
        outState.putBoolean(KEY_A2,chk_empty_list);
    }

}
