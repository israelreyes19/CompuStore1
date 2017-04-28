package com.fiuady.android.compustore;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.android.compustore.db.Assemblies;
import com.fiuady.android.compustore.db.Customers;
import com.fiuady.android.compustore.db.Inventory;
import com.fiuady.android.compustore.db.Order;
import com.fiuady.android.compustore.db.Order_assemblies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class modify_order extends AppCompatActivity {

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
                    final PopupMenu popupMenu = new PopupMenu(modify_order.this, itemView);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu_modify_status, popupMenu.getMenu());
                    popupMenu.getMenu().clear();
                    popupMenu.getMenu().add("Modificar");
                    popupMenu.getMenu().add("Eliminar");
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals("Modificar")) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(modify_order.this);
                                builder1.setTitle("¿Cuantas unidades desea que tenga el ensamble?");
                                View product_view = getLayoutInflater().inflate(R.layout.add_product_quantity, null);
                                final NumberPicker agregar_cantidad = (NumberPicker) product_view.findViewById(R.id.qty_numberPicker);
                                int qty_actual = 1;
                                for (Order_assemblies oa : CurrentOA) {
                                    if (oa.getAssembly_id() == AssembliesOnRV.get(pos).getId()) {
                                        //qty_actual=oa.getQty();
                                        Toast.makeText(getApplicationContext(), "Cantidad actual " + String.valueOf(oa.getQty()), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                for (Order_assemblies oa : NewOA) {
                                    if (oa.getAssembly_id() == AssembliesOnRV.get(pos).getId()) {
                                        //qty_actual=oa.getQty();
                                        Toast.makeText(getApplicationContext(), "Cantidad actual " + String.valueOf(oa.getQty()), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                agregar_cantidad.setMinValue(qty_actual);
                                agregar_cantidad.setMaxValue(100);

                                builder1.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        String qty_nueva = String.valueOf(agregar_cantidad.getValue());
                                        for (Order_assemblies oa : CurrentOA) {
                                            if (oa.getAssembly_id() == AssembliesOnRV.get(pos).getId()) {
                                                oa.setQty(Integer.valueOf(qty_nueva));
                                            }
                                        }
                                        for (Order_assemblies oa : NewOA) {
                                            if (oa.getAssembly_id() == AssembliesOnRV.get(pos).getId()) {
                                                oa.setQty(Integer.valueOf(qty_nueva));
                                            }
                                        }

                                    }
                                });
                                builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                builder1.setView(product_view);

                                builder1.show();
                            } else {
                                for (Order_assemblies oa:CurrentOA)
                                {
                                    if (oa.getAssembly_id()==AssembliesOnRV.get(pos).getId())
                                    {
                                        OAtoDelete.add(oa);
                                        AssembliesOnRV.remove(oa);
                                    }
                                }
                                for(Order_assemblies oa: NewOA)
                                {
                                    if(oa.getAssembly_id()==AssembliesOnRV.get(pos).getId())
                                    {
                                        OrderAssembliesOnRV.remove(oa);
                                        AssembliesOnRV.remove(oa);

                                    }
                                }
                                Toast.makeText(getApplicationContext(), "Fue removido " + String.valueOf(AssembliesOnRV.get(pos).getDescription()), Toast.LENGTH_SHORT).show();
                                AssembliesOnRV.remove(pos);
                                showAssembliesonRV(AssembliesOnRV);

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


    private ImageButton ImBtn_backactivity, ImBtn_add_assembly;
    private RecyclerView RV_Asemblies;
    private Button btn_confirm, btn_cancel;
    private AssembliesAdapter adapter;
    private Inventory inventory;
    private int id_assembly_receive_aux,id_Order_aux;
    List<Customers> clientsfounded = new ArrayList<Customers>();
    private List<Order> AllOrders = new ArrayList<Order>();
    private List<Order_assemblies> OrderAssembliesOnRV = new ArrayList<Order_assemblies>();
    private List<Assemblies> AssembliesOnRV = new ArrayList<Assemblies>();
    private List<Order_assemblies> CurrentOA = new ArrayList<Order_assemblies>();
    private List<Order_assemblies> NewOA = new ArrayList<Order_assemblies>();
    private List<Order_assemblies> OAtoDelete = new ArrayList<Order_assemblies>();

    private TextView Txt_NameClient;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            id_assembly_receive_aux = data.getIntExtra("Aux_id_assembly1", 50);
            Inventory inventory2 = new Inventory(getApplicationContext());
            Assemblies assemblieAux = inventory2.getAssembliebyId(id_assembly_receive_aux);


            boolean flag = false;
            for (Assemblies assembly : AssembliesOnRV) {
                if (assembly.getId() == assemblieAux.getId()) // ya hay un ensamble de ese tipo en la pantalla actual
                {
                    for (Order_assemblies orderA: OrderAssembliesOnRV)
                    {
                        if (orderA.getAssembly_id() == assemblieAux.getId()) {
                            orderA.setQty(orderA.getQty() + 1);
                            flag = true;
                        }
                    }
                    for (Order_assemblies orderA: CurrentOA)
                    {
                        if (orderA.getAssembly_id() == assemblieAux.getId()) {
                            orderA.setQty(orderA.getQty() + 1);
                            flag = true;
                        }
                    }
                    for (Order_assemblies orderAs: NewOA)
                    {
                        if (orderAs.getAssembly_id() == assemblieAux.getId()) {
                            int aux = orderAs.getQty();
                            flag=true;
                            //orderAs.setQty(orderAs.getQty() + 1);

                        }
                    }


                }
            }
            if (flag == false) // se agrega mas uno
            {
                AssembliesOnRV.add(assemblieAux);
                Order_assemblies new_oa = new Order_assemblies(id_Order_aux, assemblieAux.getId(), 1);
                NewOA.add(new_oa);
                OrderAssembliesOnRV.add(new_oa);
            }
            AlphabeticOrder();
            showAssembliesonRV(AssembliesOnRV);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_order);
        //declarar componentes gráficos
        ImBtn_backactivity = (ImageButton) findViewById(R.id.BackActivy_modify_order);
        ImBtn_add_assembly = (ImageButton) findViewById(R.id.imageButtonAddAssambly_modify_order);
        RV_Asemblies = (RecyclerView) findViewById(R.id.recyclerview_assamblys_add_order);
        int display_mode = getResources().getConfiguration().orientation;
        if (display_mode== Configuration.ORIENTATION_PORTRAIT)
        {
            RV_Asemblies.setLayoutManager(new LinearLayoutManager(this));
        }
        else {
            GridLayoutManager manager = new GridLayoutManager(modify_order.this,2);
            RV_Asemblies.setLayoutManager(manager);
        }
        btn_confirm = (Button) findViewById(R.id.btn_confirm_modify_order);
        btn_cancel = (Button) findViewById(R.id.btn_cancel_modify_order);
        Txt_NameClient = (TextView) findViewById(R.id.Txt_ClientName_modifyorder);

        //declarar componentesaux
        inventory = new Inventory(getApplicationContext());
        clientsfounded = inventory.getAllCustomers();
        AllOrders = inventory.getAllOrders();
        int order_id = getIntent().getIntExtra("Order_Id", 50);
        id_Order_aux=order_id;
        for (Order order : AllOrders) {
            if (order.getId() == order_id) {
                for (Customers client : clientsfounded) {
                    if (order.getCustomer_id() == client.getId()) {
                        Txt_NameClient.setText(client.getLast_name() + client.getFirst_name());
                    }
                }
            }

        }
        OrderAssembliesOnRV = inventory.getOrderAssemblies_for_an_Order(order_id);
        CurrentOA = inventory.getOrderAssemblies_for_an_Order(order_id); //lista auxiliar que hay que actualizar
        for (Order_assemblies oa : OrderAssembliesOnRV) {
            AssembliesOnRV.add(inventory.getAssembliebyId(oa.getAssembly_id()));

        }
        AlphabeticOrder();
        showAssembliesonRV(AssembliesOnRV);
        //Eventos de los componentes
        ImBtn_add_assembly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(modify_order.this, addassembly_orderassemblies.class);
                startActivityForResult(i, 2);
            }
        });
        ImBtn_backactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AssembliesOnRV.size()==0)
                {
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(modify_order.this);
                    dialogo1.setTitle("Importante");
                    dialogo1.setMessage("AGREGA UN ENSAMBLE A LA ORDEN");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                        }
                    });
                    dialogo1.show();
                }
                else
                {
                    for (Order_assemblies oa : CurrentOA) {inventory.UpdateOrderAssemblyQty(oa);}
                    for (Order_assemblies oa : NewOA) {inventory.AddOrder_assembly(oa.getId(),oa.getAssembly_id(),oa.getQty());}
                    Toast.makeText(modify_order.this, "La orden fue modificada", Toast.LENGTH_SHORT).show();
                    if (OAtoDelete.size()!=0)
                    {
                        for (Order_assemblies oa: OAtoDelete)
                        {
                            inventory.DeleteOrderAssembly(oa);
                        }
                    }
                    finish();
                }


            }
        });
    }

    public void showAssembliesonRV(List<Assemblies> List) {
        adapter = new AssembliesAdapter(List);
        RV_Asemblies.setAdapter(adapter);
    }

    public void AlphabeticOrder() {
        List<String> stringaux = new ArrayList<>();
        List<Assemblies> assembliesaux = new ArrayList<Assemblies>();
        for (Assemblies assemblies : AssembliesOnRV) {
            stringaux.add(assemblies.getDescription());
        }
        Collections.sort(stringaux); // ya acomodo por orden alfabetico
        for (String aux : stringaux) {
            for (Assemblies assemblie : AssembliesOnRV) {
                if (aux.equals(assemblie.getDescription())) {
                    assembliesaux.add(assemblie);
                }
            }
        }

        AssembliesOnRV = assembliesaux;
    }

}
