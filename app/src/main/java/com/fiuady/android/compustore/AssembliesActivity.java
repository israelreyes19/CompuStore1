package com.fiuady.android.compustore;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.fiuady.android.compustore.db.Assemblies;
import com.fiuady.android.compustore.db.Inventory;

import java.util.List;

public class AssembliesActivity extends AppCompatActivity {

    public static Assemblies selectedAssembly = new Assemblies(9999,"");

    public static boolean edit;

    public class AssembliesHolder extends RecyclerView.ViewHolder {
        private TextView txtDescription;
        private TextView tagDescription;


        public AssembliesHolder(View itemView) {
            super(itemView);
            txtDescription = (TextView) itemView.findViewById(R.id.description_text);
            tagDescription = (TextView) itemView.findViewById(R.id.description_tag);
            tagDescription.setText("Ensamble: ");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition();
                    CharSequence options[] = new CharSequence[]{"Modificar", "Eliminar"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(AssembliesActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Elige una opción: ");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final Assemblies assembly = adapter.list.get(pos);
                            selectedAssembly = assembly;
                            if (which == 0) {
                                edit = true;
                                Intent i = new Intent(AssembliesActivity.this, AddAssemblyActivity.class);
                                startActivity(i);
                            } else if (which == 1) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(AssembliesActivity.this);
                                builder1.setTitle("¿Deseas borrar este ensamble?");
                                builder1.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        Inventory inventory = new Inventory(AssembliesActivity.this);
                                        if (inventory.deleteAssemblies(assembly)) {
                                            //recyclerView.setAdapter(adapter);

                                            Toast.makeText(AssembliesActivity.this, "Se eliminó exitosamente " +
                                                    "el ensamble", Toast.LENGTH_SHORT).show();
                                            adapter = new AssembliesAdapter(inventory.getAllAssemblies());
                                            recyclerView.setAdapter(adapter);

                                        } else {
                                            Toast.makeText(AssembliesActivity.this, "No se pudo eliminar el ensamble. " +
                                                    "El ensamble esta asignado a una orden", Toast.LENGTH_SHORT).show();
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
                }
            });

        }

        public void bindAssemblies(Assemblies assemblies) {
            txtDescription.setText(assemblies.getDescription());
        }
    }

    private class AssembliesAdapter extends RecyclerView.Adapter<AssembliesHolder> {

        private List<Assemblies> list;

        public AssembliesAdapter(List<Assemblies> assemblies) {
            list = assemblies;
        }

        @Override
        public AssembliesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.categories_list_item, parent, false);
            return new AssembliesHolder(view);
        }

        @Override
        public void onBindViewHolder(AssembliesHolder holder, int position) {

            holder.bindAssemblies(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private RecyclerView recyclerView;
    private AssembliesAdapter adapter;

    private ImageButton arrowButton;
    private ImageButton addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assemblies);
        final Inventory inventory = new Inventory(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.categories_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrowButton = (ImageButton) findViewById(R.id.imageButtonBack);
        addButton = (ImageButton) findViewById(R.id.imageButtonAdd);

        adapter = new AssembliesAdapter(inventory.getAllAssemblies());
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
                edit = false;
                Intent i = new Intent(AssembliesActivity.this, AddAssemblyActivity.class);
                startActivityForResult(i,AddAssemblyActivity.CODE_EDIT);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            Inventory inventory = new Inventory(AssembliesActivity.this);
            adapter = new AssembliesAdapter(inventory.getAllAssemblies());
            recyclerView.setAdapter(adapter);
            Toast.makeText(AssembliesActivity.this,"Puta madre",Toast.LENGTH_SHORT).show();
        }
        //Inventory inventory = new Inventory(AssembliesActivity.this);
        //adapter = new AssembliesAdapter(inventory.getAllAssemblies());
        recyclerView.setAdapter(adapter);
        //Toast.makeText(AssembliesActivity.this,"Puta madre",Toast.LENGTH_SHORT).show();

    }
}
