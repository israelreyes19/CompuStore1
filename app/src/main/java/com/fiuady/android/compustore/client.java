package com.fiuady.android.compustore;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.android.compustore.db.Category;
import com.fiuady.android.compustore.db.Customers;
import com.fiuady.android.compustore.db.Inventory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class client extends AppCompatActivity {

    private class CustomersHolder extends RecyclerView.ViewHolder
    {

        private TextView lastname;
        private TextView firstname;
        private TextView id;

        public CustomersHolder(View itemView) {
            super(itemView);
            lastname = (TextView) itemView.findViewById(R.id.txtlastname);
            firstname = (TextView) itemView.findViewById(R.id.txtfirstname);
            id = (TextView) itemView.findViewById(R.id.txt_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    //CharSequence options[] = new CharSequence[]{"Modificar", "Eliminar"};
                    //AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    Toast.makeText(getApplicationContext(), String.valueOf(pos),Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bindCustomers(Customers customer)
        {

            lastname.setText(customer.getLast_name());
            firstname.setText(customer.getFirst_name());
            id.setText(String.valueOf(customer.getId()));
        }
    }

    private class CustomersAdapter extends RecyclerView.Adapter<CustomersHolder>
    {

        private List<Customers> customers;
        public CustomersAdapter(List<Customers> customers){this.customers = customers;}

        @Override
        public CustomersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //View v   = getLayoutInflater().inflate(R.layout.client_list_item, parent, false);
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_list_item, parent, false);
            return new CustomersHolder(v);

        }

        @Override
        public void onBindViewHolder(CustomersHolder holder, int position) {
            holder.bindCustomers(customers.get(position));
        }

        @Override
        public int getItemCount() {
            return customers.size();
        }
    }


    private RecyclerView recyclerView;
    private CustomersAdapter adapter;
    private Toolbar toolbar;
    private EditText Edittextname;
    private ImageButton backbutton;
    private ImageButton addclientbutton;
    private ImageButton findclientebtn;
    MultiSelectionSpinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        Inventory inventory = new Inventory(getApplicationContext());
        backbutton = (ImageButton)findViewById(R.id.imageButtonBack);
        addclientbutton=(ImageButton)findViewById(R.id.imageButtonAdd);
        Edittextname = (EditText)findViewById(R.id.edittext_name);
        recyclerView = (RecyclerView) findViewById(R.id.clients_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        findclientebtn = (ImageButton)findViewById(R.id.Imagebtn_findclient);

        spinner = (MultiSelectionSpinner) findViewById(R.id.mySpinner1);
        final List<String>  Items_Spinners = new ArrayList<String>();

        Items_Spinners.add("Nombre");
        Items_Spinners.add("Apellido");
        Items_Spinners.add("Dirección");
        Items_Spinners.add("Telefono");
        Items_Spinners.add("e_mail");

        spinner.setItems(Items_Spinners);


        List<Customers> listprove = new ArrayList<Customers>();
        listprove = inventory.getAllCustomers();

        adapter = new CustomersAdapter(listprove);
        //recyclerView.setAdapter(adapter);

        findclientebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname= "";
                String lastname= "";
                String address= "";
                String phone= "";
                String email= "";
                String descrip= Edittextname.getText().toString();

                Inventory inventory1 = new Inventory(getApplicationContext());
                for (Integer searchby: spinner.getSelectedIndicies())
                {
                    if (searchby == 0){firstname= "first_name";}
                    if (searchby == 1){lastname= "last_name";}
                    if (searchby == 2){address= "address";}
                    if (searchby == 3){phone ="phone1 or phone2 or phone3";}
                    if (searchby == 4){email= "e_mail";}
                }
                List<Customers> clientsfounded= new ArrayList<Customers>();
                clientsfounded = inventory1.findby(firstname,lastname,address,phone,email,descrip);
                adapter = new CustomersAdapter(clientsfounded);
                recyclerView.setAdapter(adapter);
                if (firstname.length() ==  0)
                {
                    if(lastname.length() ==0)
                    {
                        if( address.length() ==0)
                        {
                          if( phone.length() ==0)
                          {
                              if( email.length()==0)
                              {
                                  List<Customers> listprove = new ArrayList<Customers>();
                                  adapter = new CustomersAdapter(listprove);
                                  recyclerView.setAdapter(adapter);


                                  listprove = inventory1.getAllCustomers();
                                  adapter = new CustomersAdapter(listprove);
                                  recyclerView.setAdapter(adapter);
                              }
                          }
                        }
                    }
                }
        }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addclientbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(client.this);
                builder.setTitle("Agregar Cliente");
                builder.setMessage("Nuevo cliente: ");
                final EditText ID_input        = new EditText(client.this);
                final EditText LastName_input  = new EditText(client.this);
                final EditText FirstName_input = new EditText(client.this);
                final EditText Adress_input    = new EditText(client.this);
                final EditText phone1_input    = new EditText(client.this);
                final EditText phone2_input    = new EditText(client.this);
                final EditText phone3_input    = new EditText(client.this);
                final EditText e_mail_input    = new EditText(client.this);


                builder.setView(ID_input        );
                builder.setView(LastName_input  );
                builder.setView(FirstName_input );
                builder.setView(Adress_input    );
                builder.setView(phone1_input    );
                builder.setView(phone2_input    );
                builder.setView(phone3_input    );
                builder.setView(e_mail_input    );


                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                     //  String ID_input        = ID_input.getText().toString();
                     //  String LastName_input  = LastName_input.getText().toString();
                     //  String FirstName_input = FirstName_input.getText().toString();
                     //  String Adress_input    = Adress_input.getText().toString();
                     //  String phone1_input    = phone1_input.getText().toString();
                     //  String phone2_input    = phone2_input.getText().toString();
                     //  String phone3_input    = phone3_input.getText().toString();
                     //  String e_mail_input    = e_mail_input.getText().toString();


                        //inventory.addCategory(cat);
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
