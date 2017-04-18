package com.fiuady.android.compustore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.android.compustore.db.Assemblies;
import com.fiuady.android.compustore.db.Inventory;

import java.util.ArrayList;
import java.util.List;

//import android.support.v7.widget.SearchView;

public class addassembly_orderassemblies extends AppCompatActivity {

    public class AssembliesHolder extends RecyclerView.ViewHolder {
        private TextView txtDescription;
        private TextView tagDescription;


        public AssembliesHolder(final View itemView) {
            super(itemView);
            txtDescription = (TextView) itemView.findViewById(R.id.description_text);
            tagDescription = (TextView) itemView.findViewById(R.id.description_tag);
            tagDescription.setText("Ensamble: ");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition();
                    final PopupMenu popupMenu = new PopupMenu(addassembly_orderassemblies.this, itemView);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu_modify_status, popupMenu.getMenu());
                    popupMenu.getMenu().add("Agregar");
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals("Agregar")) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(addassembly_orderassemblies.this);
                                builder1.setTitle("Agregar ensamble a orden: "+ String.valueOf(AssembliesOnRV.get(pos).getDescription()));
                                builder1.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        int value;
                                        value = AssembliesOnRV.get(pos).getId();
                                        Toast.makeText(getApplicationContext(),"Se ha agregado a la lista de órdenes ",Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent();
                                        intent.putExtra("Aux_id_assembly1", value); //value should be your string from the edittext
                                        setResult(RESULT_OK,intent);
                                        dialog.cancel();
                                        finish();
                                    }
                                });
                                builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });

                                builder1.show();
                            }
                            return false;
                        }
                    });
                    popupMenu.show();

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
    private SearchView searchView;
    private Inventory inventory;
    private CheckBox ChB_ShowAllAssemblies;
    private ImageButton Img_btn_backActivity;
    private List<Assemblies> AssembliesOnRV = new ArrayList<Assemblies>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addassembly_orderassemblies);
        //Declarar los componentes
        recyclerView = (RecyclerView) findViewById(R.id.assemblies_recyclerview_add_order_aux);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView = (SearchView) findViewById(R.id.SearchView_order_aux);
        Img_btn_backActivity = (ImageButton) findViewById(R.id.imageButtonBack_order_aux);
        ChB_ShowAllAssemblies = (CheckBox) findViewById(R.id.ChB_ShowAllAssemblies);
        //Componentes auxiliares
        inventory = new Inventory(getApplicationContext());

        //eventos componentes
        Img_btn_backActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ChB_ShowAllAssemblies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ChB_ShowAllAssemblies.isChecked()) {
                    AssembliesOnRV = inventory.getAllAssemblies();
                    showAssembliesonRV(AssembliesOnRV);
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
// do something on text submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
// do something when text changes
                AssembliesOnRV = inventory.getAssembliesbyDescription(String.valueOf(searchView.getQuery()));
                showAssembliesonRV(AssembliesOnRV);
                return false;
            }
        });
    }

    public void showAssembliesonRV(List<Assemblies> List) {
        adapter = new AssembliesAdapter(List);
        recyclerView.setAdapter(adapter);
    }
}
