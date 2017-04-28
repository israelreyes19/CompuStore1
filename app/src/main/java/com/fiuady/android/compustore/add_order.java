package com.fiuady.android.compustore;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.android.compustore.db.Assemblies;
import com.fiuady.android.compustore.db.Customers;
import com.fiuady.android.compustore.db.Inventory;
import com.fiuady.android.compustore.db.Order_assemblies;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class add_order extends AppCompatActivity {

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
                    final PopupMenu popupMenu = new PopupMenu(add_order.this, itemView);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu_modify_status, popupMenu.getMenu());
                    popupMenu.getMenu().clear();
                    popupMenu.getMenu().add("Modificar");
                    popupMenu.getMenu().add("Eliminar");
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getTitle().equals("Modificar")){
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(add_order.this);
                                builder1.setTitle("¿Cuantas unidades desea que tenga el ensamble?");
                                View product_view = getLayoutInflater().inflate(R.layout.add_product_quantity, null);
                                final NumberPicker agregar_cantidad = (NumberPicker)product_view.findViewById(R.id.qty_numberPicker);
                                int qty_actual = 1;
                                for (Order_assemblies oa: OrderAssembliesOnRV)
                                {
                                    if (oa.getAssembly_id()==AssembliesOnRV.get(pos).getId())
                                    {
                                        //qty_actual=oa.getQty();
                                        Toast.makeText(getApplicationContext(),"Cantidad actual "+String.valueOf(oa.getQty()),Toast.LENGTH_SHORT).show();
                                    }
                                }
                                agregar_cantidad.setMinValue(qty_actual);
                                agregar_cantidad.setMaxValue(100);

                                builder1.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        String qty_nueva = String.valueOf(agregar_cantidad.getValue());
                                        for (Order_assemblies oa: OrderAssembliesOnRV)
                                        {
                                            if (oa.getAssembly_id()==AssembliesOnRV.get(pos).getId())
                                            {
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
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Fue removido "+String.valueOf(AssembliesOnRV.get(pos).getDescription()),Toast.LENGTH_SHORT).show();

                                    for (Order_assemblies oa: OrderAssembliesOnRV)
                                    {
                                        if(AssembliesOnRV.get(pos).getId()==oa.getAssembly_id())
                                        {
                                            OrderAssembliesOnRV.remove(oa);
                                        }
                                    }

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
    private Spinner Spnr_clients;
    private RecyclerView RV_Asemblies;
    private Button btn_confirm,btn_cancel;
    private AssembliesAdapter adapter;
    private Inventory inventory;
    private int id_assembly_receive_aux;
    List<Customers> clientsfounded = new ArrayList<Customers>();
    private ArrayList<Assemblies> AssembliesOnRV = new ArrayList<Assemblies>();
    private ArrayList<Order_assemblies> OrderAssembliesOnRV = new ArrayList<Order_assemblies>();
    private boolean clickingconfirm = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RESULT_OK==resultCode)
        {
            id_assembly_receive_aux = data.getIntExtra("Aux_id_assembly1",1234);
            Inventory inventory2= new Inventory(getApplicationContext());
            Assemblies assemblieAux =inventory2.getAssembliebyId(id_assembly_receive_aux);

            boolean flag=false;
            for (Assemblies assembly: AssembliesOnRV)
            {
                if (assembly.getId() == assemblieAux.getId()) // ya hay un ensamble de ese tipo
                {
                    for (Order_assemblies Order_a :OrderAssembliesOnRV )
                    {
                        if (Order_a.getAssembly_id()==assemblieAux.getId())
                        {
                            flag=true;
                        }
                    }

                }
            }
            if(flag ==true) // se agrega mas uno
            {for (Order_assemblies oa: OrderAssembliesOnRV){if (oa.getAssembly_id()==assemblieAux.getId()){oa.setQty(oa.getQty()+1);}}}
            else {
                AssembliesOnRV.add(assemblieAux);
                Order_assemblies new_oa = new Order_assemblies(inventory.getAllOrders().size(),assemblieAux.getId(),1);
                OrderAssembliesOnRV.add(new_oa);
            }
            AlphabeticOrder();
            showAssembliesonRV(AssembliesOnRV);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        //declarar componentes gráficos
        ImBtn_backactivity= (ImageButton)findViewById(R.id.BackActivy_add_order);
        ImBtn_add_assembly= (ImageButton)findViewById(R.id.imageButtonAddAssambly_add_order);
        Spnr_clients = (Spinner)findViewById(R.id.spinner_clients_add_order);
        RV_Asemblies = (RecyclerView)findViewById(R.id.recyclerview_assamblys_add_order);
        int display_mode = getResources().getConfiguration().orientation;
        if (display_mode== Configuration.ORIENTATION_PORTRAIT)
        {
            RV_Asemblies.setLayoutManager(new LinearLayoutManager(this));
        }
        else {
            GridLayoutManager manager = new GridLayoutManager(add_order.this,2);
            RV_Asemblies.setLayoutManager(manager);
        }
        btn_confirm = (Button)findViewById(R.id.btn_confirm_add_order);
        btn_cancel= (Button)findViewById(R.id.btn_cancel_add_order);

        //declarar componentesaux
        inventory= new Inventory(getApplicationContext());
        clientsfounded = inventory.getAllCustomers();
        List<String> spinnerArray = new ArrayList<String>();

        //Llenar los componentes
        for (Customers customer : clientsfounded) {
            spinnerArray.add(String.valueOf(customer.getLast_name()) + " " + String.valueOf(customer.getFirst_name()));
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spnr_clients.setAdapter(adapter);

        if (savedInstanceState != null) {
            AssembliesOnRV = savedInstanceState.getParcelableArrayList("key");
            OrderAssembliesOnRV = savedInstanceState.getParcelableArrayList("key1");
            clickingconfirm = savedInstanceState.getBoolean("clickingconfirm");

            if (AssembliesOnRV.size() > 0) {showAssembliesonRV(AssembliesOnRV);}
            if (clickingconfirm)
            {
                if (AssembliesOnRV.size()==0)
                {
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(add_order.this);
                    dialogo1.setTitle("Importante");
                    dialogo1.setMessage("AGREGA UN ENSAMBLE A LA ORDEN");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            clickingconfirm = false;
                        }
                    });
                    dialogo1.show();

                }
                else {
                    inventory.AddOrder(inventory.getAllOrders().size(), getCustomerOnSpinner().getId(), getTodayCalendar());
                    for (Order_assemblies oa : OrderAssembliesOnRV) {
                        inventory.AddOrder_assembly(oa.getId(), oa.getAssembly_id(), oa.getQty());
                    }
                    Toast.makeText(getApplicationContext(), "Se ha agregado correctamente la orden", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    clickingconfirm = false;
                    finish();
                }
            }
        }
        //eventos de los componentes
        ImBtn_backactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImBtn_add_assembly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(add_order.this, addassembly_orderassemblies.class);
                //i.putExtra("Aux_id_assembly",id_assembly_receive_aux);
                startActivityForResult(i,2);
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
                clickingconfirm=true;
                if (AssembliesOnRV.size()==0)
                {
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(add_order.this);
                    dialogo1.setTitle("Importante");
                    dialogo1.setMessage("AGREGA UN ENSAMBLE A LA ORDEN");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            clickingconfirm = false;
                        }
                    });
                    dialogo1.show();

                }
                else {
                    inventory.AddOrder(inventory.getAllOrders().size(), getCustomerOnSpinner().getId(), getTodayCalendar());
                    for (Order_assemblies oa : OrderAssembliesOnRV) {
                        inventory.AddOrder_assembly(oa.getId(), oa.getAssembly_id(), oa.getQty());
                    }
                    Toast.makeText(getApplicationContext(), "Se ha agregado correctamente la orden", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    clickingconfirm = false;
                    finish();
                }

            }
        });

    }
    public void showAssembliesonRV(List<Assemblies> List) {
        adapter = new AssembliesAdapter(List);
        RV_Asemblies.setAdapter(adapter);
    }
    public void AlphabeticOrder()
    {
        List<String > stringaux = new ArrayList<>();
        ArrayList<Assemblies> assembliesaux = new ArrayList<Assemblies>();
        for (Assemblies assemblies :AssembliesOnRV)
        {
            stringaux.add(assemblies.getDescription());
        }
        Collections.sort(stringaux); // ya acomodo por orden alfabetico
        for (String aux: stringaux)
        {
            for (Assemblies assemblie: AssembliesOnRV)
            {
                if (aux.equals(assemblie.getDescription()))
                {
                    assembliesaux.add(assemblie);
                }
            }
        }

        AssembliesOnRV = assembliesaux;
    }
    public String getTodayCalendar()
    {   int oldDate_DayX,oldDate_MonthX,oldDate_YearX;
        String aux = "";
        final Calendar c = Calendar.getInstance();
        oldDate_DayX = c.get(Calendar.DAY_OF_MONTH);
        oldDate_MonthX = c.get(Calendar.MONTH);
        oldDate_YearX = c.get(Calendar.YEAR);
        if (oldDate_MonthX < 10 && oldDate_DayX < 10) {
            aux ="0" + oldDate_DayX + "-0" + (oldDate_MonthX + 1) + "-" + oldDate_YearX;
        } else if (oldDate_DayX < 10) {
            aux = "0" + oldDate_DayX + "-" + (oldDate_MonthX + 1) + "-" + oldDate_YearX;
        } else if (oldDate_MonthX < 10) {
            aux = oldDate_DayX + "-0" + (oldDate_MonthX + 1) + "-" + oldDate_YearX;
        } else {
            aux=oldDate_DayX + "-" + (oldDate_MonthX + 1) + "-" + oldDate_YearX;
        }

// failed to open zip file gradle´s dependency cache may be corrupt
        return aux;
    }
    public Customers getCustomerOnSpinner()
    {
        Customers c= new Customers(123,"","","","","","","");
        for (Customers customer : clientsfounded) {
            if (Spnr_clients.getSelectedItem().toString().equals((customer.getLast_name() + " " + customer.getFirst_name()))) {
                c= customer;

            }
        }
        return c;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("key", AssembliesOnRV);
        outState.putParcelableArrayList("key1", OrderAssembliesOnRV);
        outState.putBoolean("clickingconfirm",clickingconfirm);

    }
}
