package com.fiuady.android.compustore;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.android.compustore.db.Inventory;
import com.fiuady.android.compustore.db.Products;

import java.util.List;

public class AddAssemblyActivity extends AppCompatActivity {

    public class ProductHolder extends RecyclerView.ViewHolder
    {
        private TextView txtId;
        private TextView txtCategory;
        private TextView txtProduct;
        private TextView txtPrice;
        private TextView txtQuantity;




        public ProductHolder(View itemView) {
            super(itemView);
            txtCategory = (TextView) itemView.findViewById(R.id.txt_category);
            txtId = (TextView) itemView.findViewById(R.id.txt_id);
            txtProduct = (TextView) itemView.findViewById(R.id.txt_description);
            txtPrice =  (TextView) itemView.findViewById(R.id.txt_price);
            txtQuantity = (TextView) itemView.findViewById(R.id.txt_qty);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition();
                    CharSequence options[] = new CharSequence[]{"Modificar", "Eliminar"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddAssemblyActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Elige una opción: ");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            });
        }

        public void bindProducts(Products product)
        {
            txtId.setText(String.valueOf(product.getId()));
            txtCategory.setText(String.valueOf(product.getCategory_id()));
            txtProduct.setText(product.getDescription());
            txtPrice.setText(String.valueOf(product.getPrice()));
            txtQuantity.setText(String.valueOf(product.getQty()));


        }

    }

    private class ProductsAdapter extends RecyclerView.Adapter<ProductHolder>
    {
        private List<Products> products;
        public ProductsAdapter(List<Products> products){this.products = products;}

        @Override
        public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view   = getLayoutInflater().inflate(R.layout.product_list_item, parent, false);
            return new ProductHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductHolder holder, int position) {

            holder.bindProducts(products.get(position));
        }

        @Override
        public int getItemCount() {
            return products.size();
        }
    }

    private RecyclerView recyclerView;
    private ProductsAdapter adapter;

    private EditText assemblyDescription ;
    private ImageButton arrowButton;
    private ImageButton addButton;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assembly);
        final Inventory inventory = new Inventory(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.assemblies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrowButton = (ImageButton) findViewById(R.id.imageButtonBack);
        addButton = (ImageButton) findViewById(R.id.imageButtonAdd);
        assemblyDescription = (EditText) findViewById(R.id.assemblyName_text);
        title = (TextView) findViewById(R.id.title_add);
        if(AssembliesActivity.edit) //Editar ensamble
        {
            assemblyDescription.setText(AssembliesActivity.selectedAssembly.getDescription());

            //Toast.makeText(getApplicationContext(), "Editando", Toast.LENGTH_SHORT).show();
            //assemblyDescription.setEnabled(false);
            title.setText("Editar ensamble");
            adapter = new ProductsAdapter(inventory.getAssemblyProducts(AssembliesActivity.selectedAssembly));
            recyclerView.setAdapter(adapter);

        }
        else if(!AssembliesActivity.edit) // Agregar nuevo ensamble
        {
            //Toast.makeText(getApplicationContext(), "Agregando", Toast.LENGTH_SHORT).show();

        }
        arrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
