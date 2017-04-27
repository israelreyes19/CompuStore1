package com.fiuady.android.compustore;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.android.compustore.db.Assemblies;
import com.fiuady.android.compustore.db.Inventory;
import com.fiuady.android.compustore.db.Products;

import java.util.List;

public class AddAssemblyActivity extends AppCompatActivity {

    public static final int CODE_EDIT = 1;
    public static boolean saveState;
    public class ProductHolder extends RecyclerView.ViewHolder {
        private TextView txtId;
        private TextView txtCategory;
        private TextView txtProduct;
        private TextView txtPrice;
        private TextView txtQuantity;
        private TextView txtIdTag;
        private TextView txtCategoryTag;
        private TextView txtPriceTag;
        private TextView txtQuantityTag;


        public ProductHolder(View itemView) {
            super(itemView);
            txtCategory = (TextView) itemView.findViewById(R.id.txt_category);
            txtId = (TextView) itemView.findViewById(R.id.txt_id);
            txtProduct = (TextView) itemView.findViewById(R.id.txt_description);
            txtPrice = (TextView) itemView.findViewById(R.id.txt_price);
            txtQuantity = (TextView) itemView.findViewById(R.id.txt_qty);

            txtCategoryTag = (TextView) itemView.findViewById(R.id.ID_TAG);
            txtIdTag = (TextView) itemView.findViewById(R.id.category_TAG);
            txtPriceTag = (TextView) itemView.findViewById(R.id.price_TAG);
            txtQuantityTag = (TextView) itemView.findViewById(R.id.qty_TAG);

            txtId.setVisibility(View.GONE);
            txtCategory.setVisibility(View.GONE);
            txtPrice.setVisibility(View.GONE);
            txtQuantity.setVisibility(View.GONE);
            txtIdTag.setVisibility(View.GONE);
            txtCategoryTag.setVisibility(View.GONE);
            txtPriceTag.setVisibility(View.GONE);
            txtQuantityTag.setVisibility(View.GONE);


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
                            if (which == 0) {
                                final Products product = adapter.products.get(pos);
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(AddAssemblyActivity.this);
                                builder1.setTitle("Editar Producto");

                                builder1.setMessage(product.getDescription() + " \nCantidad: ");
                                final NumberPicker picker = new NumberPicker(AddAssemblyActivity.this);
                                picker.setMaxValue(99);
                                picker.setMinValue(1);
                                builder1.setView(picker);
                                builder1.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        picker.getValue();
                                        Inventory inventory = new Inventory(AddAssemblyActivity.this);
                                        inventory.updateProductInAssembly(auxAssembly, product, picker.getValue());

                                        adapter = new ProductsAdapter(inventory.getAssemblyProducts(auxAssembly));
                                        recyclerView.setAdapter(adapter);
                                    }
                                });
                                builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                builder1.show();
                            } else if (which == 1) {
                                final Products products = adapter.products.get(pos);
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(AddAssemblyActivity.this);
                                builder1.setTitle("Eliminar Producto");
                                builder1.setMessage("¿Está seguro que desea eliminar este producto del ensamble?");
                                builder1.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Inventory inventory = new Inventory(AddAssemblyActivity.this);
                                        inventory.deleteProductInAssembly(auxAssembly, products);
                                        Toast.makeText(AddAssemblyActivity.this, "Se ha eliminado el producto", Toast.LENGTH_SHORT).show();
                                        adapter = new ProductsAdapter(inventory.getAssemblyProducts(auxAssembly));
                                        recyclerView.setAdapter(adapter);
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
                }
            });
        }

        public void bindProducts(Products product) {
            txtId.setText(String.valueOf(product.getId()));
            txtCategory.setText(String.valueOf(product.getCategory_id()));
            txtProduct.setText(product.getDescription());
            txtPrice.setText(String.valueOf(product.getPrice()));
            txtQuantity.setText(String.valueOf(product.getQty()));


        }

    }

    private class ProductsAdapter extends RecyclerView.Adapter<ProductHolder> {
        private List<Products> products;

        public ProductsAdapter(List<Products> products) {
            this.products = products;
        }

        @Override
        public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.product_list_item, parent, false);
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

    private List<Products> list;

    private Assemblies auxAssembly = new Assemblies(9999, "");
    private EditText assemblyDescription;
    private ImageButton arrowButton;
    private ImageButton addButton;
    private TextView title;
    private Button saveButton;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Toast.makeText(getApplicationContext(), "Cerrando", Toast.LENGTH_SHORT).show();

        if(!AssembliesActivity.edit)
        {

            Inventory inventory = new Inventory(AddAssemblyActivity.this);
            inventory.emptyNDeleteAux();
        }
        else if(AssembliesActivity.edit)
        {
            Inventory inventory = new Inventory(AddAssemblyActivity.this);
            inventory.emptyNDeleteAux();
        }
        //setResult(RESULT_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RESULT_OK==resultCode)
        {
            Inventory inventory = new Inventory(AddAssemblyActivity.this);
            adapter = new ProductsAdapter(inventory.getAssemblyProducts(auxAssembly));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assembly);
        final Inventory inventory = new Inventory(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.assemblies_recycler_view);
        arrowButton = (ImageButton) findViewById(R.id.imageButtonBack);
        addButton = (ImageButton) findViewById(R.id.imageButtonAdd);
        assemblyDescription = (EditText) findViewById(R.id.assemblyName_text);
        saveButton = (Button) findViewById(R.id.saveButton);
        title = (TextView) findViewById(R.id.title_add);


        int display_mode = getResources().getConfiguration().orientation;

        if (display_mode == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


        } else {
            GridLayoutManager manager = new GridLayoutManager(AddAssemblyActivity.this,2,GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);
            Toast.makeText(AddAssemblyActivity.this, "El que lo lea", Toast.LENGTH_SHORT).show();
        }
        if (AssembliesActivity.edit) //Editar ensamble
        {
            assemblyDescription.setText(AssembliesActivity.selectedAssembly.getDescription());

            //Toast.makeText(getApplicationContext(), "Editando", Toast.LENGTH_SHORT).show();
            //assemblyDescription.setEnabled(false);
            title.setText("Editar ensamble");


            //adapter = new ProductsAdapter(inventory.getAssemblyProducts(AssembliesActivity.selectedAssembly));
            //recyclerView.setAdapter(adapter);
            list = inventory.getAssemblyProducts(AssembliesActivity.selectedAssembly);
            //AssembliesActivity.selectedAssembly.setDescription("");
            //AssembliesActivity.selectedAssembly.setId(9999);

            inventory.addAuxAssembly(auxAssembly);
            for(Products products : list)
            {
                inventory.addProductInAssemblyWithQty(products, auxAssembly);
            }

            adapter = new ProductsAdapter(inventory.getAssemblyProducts(auxAssembly));
            recyclerView.setAdapter(adapter);

        } else if (!AssembliesActivity.edit) // Agregar nuevo ensamble
        {
            AssembliesActivity.selectedAssembly.setDescription("");
            AssembliesActivity.selectedAssembly.setId(9999);
            inventory.addAuxAssembly(AssembliesActivity.selectedAssembly);

        }
        arrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AssembliesActivity.edit) {
                    if (inventory.updateAssembly(AssembliesActivity.selectedAssembly, assemblyDescription.getText().toString())) {
                        //setResult(RESULT_OK);
                        Toast.makeText(AddAssemblyActivity.this, AssembliesActivity.selectedAssembly.getDescription(), Toast.LENGTH_SHORT).show();

                        inventory.transferProductsToAnotherAssembly(AssembliesActivity.selectedAssembly);
                        inventory.deleteAux();
                        setResult(RESULT_OK);
                        Toast.makeText(AddAssemblyActivity.this, "Se actualizó la información del ensamble", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(AddAssemblyActivity.this, "ERROR: El nuevo nombre del ensamble ya esta asignado a otro ensamble", Toast.LENGTH_LONG).show();

                    }
                    finish();
                }
                else
                {
                    AssembliesActivity.selectedAssembly.setDescription(assemblyDescription.getText().toString());
                    if(inventory.addAssembly(AssembliesActivity.selectedAssembly)){
                    inventory.transferProductsToDefinitiveAssembly();
                    inventory.deleteAux();
                        setResult(RESULT_OK);}
                    else
                    {
                        Toast.makeText(AddAssemblyActivity.this, "ERROR: El nuevo nombre del ensamble ya esta asignado a otro ensamble", Toast.LENGTH_LONG).show();

                    }
                        finish();
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddAssemblyActivity.this,AddNewProductActivity.class);
                startActivityForResult(i,2);

            }
        });
    }
}
