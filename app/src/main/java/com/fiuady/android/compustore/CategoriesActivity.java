package com.fiuady.android.compustore;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.fiuady.android.compustore.db.Category;
import com.fiuady.android.compustore.db.Inventory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import java.util.List;

public class CategoriesActivity extends AppCompatActivity {



    private class CategoryHolder extends RecyclerView.ViewHolder
    {

        private TextView txtDescription;

        public CategoryHolder(View itemView) {
            super(itemView);
            txtDescription = (TextView) itemView.findViewById(R.id.description_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition();
                    CharSequence options[] = new CharSequence[]{"Modificar", "Eliminar"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(CategoriesActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Elige una opción: ");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            final Category category = adapter.categories.get(pos);
                            //Toast.makeText(getApplicationContext(), String.valueOf(which),Toast.LENGTH_SHORT).show();

                            if(which == 0)
                            {

                                //Intent j = new Intent(CategoriesActivity.this, EditCActivity.class);
                                //startActivity(j);
                                //j.putExtra(EditCActivity.DESCRIPTION_ID_EDIT, "Tu mama");
                                //Toast.makeText(getApplicationContext(), category.getDescription(),Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(CategoriesActivity.this);
                                builder1.setTitle("Nueva descripción");
                                final EditText input = new EditText(CategoriesActivity.this);
                                //input.setId(TEXT_ID);
                                input.setText(category.getDescription());
                                builder1.setView(input);
                                builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        String value = input.getText().toString();
                                        category.setDescription(value);
                                        Inventory inventory = new Inventory(CategoriesActivity.this);
                                        inventory.updateCategory(category);
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
                            else if(which ==1)
                            {

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(CategoriesActivity.this);
                                builder1.setTitle("¿Deseas borrar esta categoría?");
                                builder1.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        Inventory inventory = new Inventory(CategoriesActivity.this);
                                        if(inventory.deleteCategory(category))
                                        {
                                            //recyclerView.setAdapter(adapter);

                                            Toast.makeText(CategoriesActivity.this,"Se eliminó exitosamente " +
                                                    "la categoría",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(CategoriesActivity.this,"No se pudo eliminar la categoría. " +
                                                    "Existen productos de la categoría",Toast.LENGTH_SHORT).show();
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
                                recyclerView.setAdapter(adapter);

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

        public void bindCategory(Category category)
        {

            txtDescription.setText(category.getDescription());
        }
    }
    private class CategoriesAdapter extends RecyclerView.Adapter<CategoryHolder>
    {

        private List<Category> categories;
        public CategoriesAdapter(List<Category> categories){this.categories = categories;}

        @Override
        public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view   = getLayoutInflater().inflate(R.layout.categories_list_item, parent, false);
            return new CategoryHolder(view);
        }

        @Override
        public void onBindViewHolder(CategoryHolder holder, int position) {
            holder.bindCategory(categories.get(position));
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }
    }

    private RecyclerView recyclerView;
    private CategoriesAdapter adapter;

    private ImageButton arrowButton;
    private ImageButton addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        final Inventory inventory = new Inventory(getApplicationContext());


        recyclerView = (RecyclerView) findViewById(R.id.categories_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrowButton = (ImageButton) findViewById(R.id.imageButtonBack);
        addButton = (ImageButton) findViewById(R.id.imageButtonAdd);

        adapter = new CategoriesAdapter(inventory.getAllCategories());
        recyclerView.setAdapter(adapter);

        arrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CategoriesActivity.this);
                builder.setTitle("Agregar categoría");
                builder.setMessage("Nueva categoría: ");
                final EditText input = new EditText(CategoriesActivity.this);
                builder.setView(input);
                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        //category.setDescription(value);
                        //Inventory inventory = new Inventory(CategoriesActivity.this);
                        //inventory.updateCategory(category);
                        //recyclerView.setAdapter(adapter);
                        Category cat = new Category(9999, value);
                        inventory.addCategory(cat);
                        recyclerView.setAdapter(adapter);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.show();

            }
        });

    }
}
