package com.fiuady.android.compustore;

import android.app.Dialog;
import android.app.DialogFragment;
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
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
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
    public List<Customers> CustomersonRV = new ArrayList<Customers>();

    private class CustomersHolder extends RecyclerView.ViewHolder {

        private TextView lastname;
        private TextView firstname;
        private TextView id;

        public CustomersHolder(View itemView) {
            super(itemView);
            lastname = (TextView) itemView.findViewById(R.id.txtlastname);
            firstname = (TextView) itemView.findViewById(R.id.txtfirstname);
            id = (TextView) itemView.findViewById(R.id.txt_id);
            final Inventory inventory2 = new Inventory(getApplicationContext());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition();
                    CharSequence options[] = new CharSequence[]{"Modificar", "Eliminar"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(client.this);
                    builder.setCancelable(true);
                    builder.setTitle("Elige una opción: ");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                final Dialog dialog1 = new Dialog(client.this);
                                dialog1.setTitle("Editar Cliente");
                                dialog1.setContentView(R.layout.add_client_design);
                                dialog1.show();

                                final EditText EditText_FirstName = (EditText) dialog1.findViewById(R.id.EditText_ClientfirstName);
                                final EditText EditText_LastName = (EditText) dialog1.findViewById(R.id.EditText_ClientlastName);
                                final EditText EditText_Address = (EditText) dialog1.findViewById(R.id.EditText_ClientAddress);
                                final EditText EditText_phone1_lada = (EditText) dialog1.findViewById(R.id.EditText_phonelada);
                                final EditText EditText_phone1 = (EditText) dialog1.findViewById(R.id.EditText_phone);
                                final EditText EditText_phone2_lada = (EditText) dialog1.findViewById(R.id.EditText_phonelada2);
                                final EditText EditText_phone2 = (EditText) dialog1.findViewById(R.id.EditText_phone2);
                                final EditText EditText_phone3_lada = (EditText) dialog1.findViewById(R.id.EditText_phonelada3);
                                final EditText EditText_phone3 = (EditText) dialog1.findViewById(R.id.EditText_phone3);
                                final EditText EditText_Email = (EditText) dialog1.findViewById(R.id.EditText_e_mail);
                                final CheckBox ChB_phone1 = (CheckBox) dialog1.findViewById(R.id.CheckBox_phone1);
                                final CheckBox ChB_phone2 = (CheckBox) dialog1.findViewById(R.id.CheckBox_phone2);
                                final CheckBox ChB_phone3 = (CheckBox) dialog1.findViewById(R.id.CheckBox_phone3);
                                final CheckBox ChB_e_mail = (CheckBox) dialog1.findViewById(R.id.CheckBox_e_mail);
                                final TextView tittle = (TextView)dialog1.findViewById(R.id.add_client_title);
                                Button btn_Editclient = (Button) dialog1.findViewById(R.id.btn_confirm);
                                Button btn_cancel = (Button) dialog1.findViewById(R.id.btn_cancel);
                                tittle.setText("Editar Cliente");
                                btn_Editclient.setText("Editar");

                                ChB_phone1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (ChB_phone1.isChecked() == true) {
                                            EditText_phone1.setEnabled(true);
                                            EditText_phone1_lada.setEnabled(true);
                                        } else {
                                            EditText_phone1.setEnabled(false);
                                            EditText_phone1_lada.setEnabled(false);
                                        }
                                    }
                                });
                                ChB_phone2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (ChB_phone2.isChecked() == true) {
                                            EditText_phone2.setEnabled(true);
                                            EditText_phone2_lada.setEnabled(true);
                                        } else {
                                            EditText_phone2.setEnabled(false);
                                            EditText_phone2_lada.setEnabled(false);
                                        }
                                    }
                                });
                                ChB_phone3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (ChB_phone3.isChecked() == true) {
                                            EditText_phone3.setEnabled(true);
                                            EditText_phone3_lada.setEnabled(true);
                                        } else {
                                            EditText_phone3.setEnabled(false);
                                            EditText_phone3_lada.setEnabled(false);
                                        }
                                    }
                                });
                                ChB_e_mail.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (ChB_e_mail.isChecked() == true) {
                                            EditText_Email.setEnabled(true);
                                        } else {
                                            EditText_Email.setEnabled(false);
                                        }
                                    }
                                });
                                EditText_FirstName.setText(String.valueOf(CustomersonRV.get(pos).getFirst_name()));
                                EditText_LastName.setText(String.valueOf(CustomersonRV.get(pos).getLast_name()));
                                EditText_Address.setText(String.valueOf(CustomersonRV.get(pos).getAddress()));
                                EditText_Email.setText(String.valueOf(CustomersonRV.get(pos).getE_mail()));
                                if(CustomersonRV.get(pos).getPhone1()!=null) {
                                    ChB_phone1.setChecked(true);
                                    String aux = "";
                                    String aux1 = "";

                                    for (int x = 0; x < 3; x++) {
                                        aux = aux + CustomersonRV.get(pos).getPhone1().toString().charAt(x);
                                    }
                                    for (int x = 4; x < 11; x++) {
                                        aux1 = aux1 + CustomersonRV.get(pos).getPhone1().toString().charAt(x);
                                    }
                                    EditText_phone1.setText(aux1);
                                    EditText_phone1_lada.setText(aux);
                                }
                                if(CustomersonRV.get(pos).getPhone2()!= null) {
                                    ChB_phone2.setChecked(true);
                                    String aux = "";
                                    String aux1 = "";

                                    for (int x = 0; x < 3; x++) {
                                        aux = aux + CustomersonRV.get(pos).getPhone2().toString().charAt(x);
                                    }
                                    for (int x = 4; x < 11; x++) {
                                        aux1 = aux1 + CustomersonRV.get(pos).getPhone2().toString().charAt(x);
                                    }
                                    EditText_phone2.setText(aux1);
                                    EditText_phone2_lada.setText(aux);
                                }
                                if(CustomersonRV.get(pos).getPhone3()!= null) {
                                    ChB_phone3.setChecked(true);
                                    String aux = "";
                                    String aux1 = "";

                                    for (int x = 0; x < 3; x++) {
                                        aux = aux + CustomersonRV.get(pos).getPhone3().toString().charAt(x);
                                    }
                                    for (int x = 4; x < 11; x++) {
                                        aux1 = aux1 + CustomersonRV.get(pos).getPhone3().toString().charAt(x);
                                    }
                                    EditText_phone3.setText(aux1);
                                    EditText_phone3_lada.setText(aux);
                                }
                                if(CustomersonRV.get(pos).getE_mail()!=null)
                                {
                                    ChB_e_mail.setChecked(true);
                                }

                                btn_cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog1.cancel();
                                    }
                                });
                               btn_Editclient.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       String AuxPhone1 = "";
                                       String AuxPhone2="";
                                       String AuxPhone3="";
                                       String AuxEmail = "";
                                       if(ChB_phone1.isChecked()==false){AuxPhone1=null;}else{AuxPhone1 = EditText_phone1_lada.getText().toString()+"-"+EditText_phone1.getText().toString();}
                                       if(ChB_phone2.isChecked()==false){AuxPhone2=null;}else{AuxPhone2 = EditText_phone2_lada.getText().toString()+"-"+EditText_phone2.getText().toString();}
                                       if(ChB_phone3.isChecked()==false){AuxPhone3=null;}else{AuxPhone3 = EditText_phone3_lada.getText().toString()+"-"+EditText_phone3.getText().toString();}
                                       if (ChB_e_mail.isChecked()==false){AuxEmail=null;}else{AuxEmail= EditText_Email.getText().toString();}
                                       Customers customerU = new Customers(CustomersonRV.get(pos).getId(),
                                               EditText_FirstName.getText().toString(),EditText_LastName.getText().toString(),
                                               EditText_Address.getText().toString(),AuxPhone1,AuxPhone2,AuxPhone3,AuxEmail);
                                       //inventory2.updateCustomer(customerU);//Checarupdate
                                       adapter = new CustomersAdapter(inventory2.getAllCustomers());
                                       recyclerView.setAdapter(adapter);
                                       CustomersonRV = inventory2.getAllCustomers();
                                   }
                               });
                            } else if (which == 1) {

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(client.this);
                                builder1.setTitle("¿Deseas borrar este cliente?");
                                builder1.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        Inventory inventory = new Inventory(client.this);
                                        if(inventory.deleteCustomer(CustomersonRV.get(pos)))
                                        {
                                            Toast.makeText(client.this,"Se eliminó exitosamente " +
                                                    "el cliente",Toast.LENGTH_SHORT).show();
                                            adapter = new client.CustomersAdapter(inventory.getAllCustomers());
                                            recyclerView.setAdapter(adapter);
                                            CustomersonRV =inventory.getAllCustomers();

                                        }
                                        else
                                        {
                                            Toast.makeText(client.this,"No se pudo eliminar  el cliente. " +
                                                    "Existen pedidos ",Toast.LENGTH_SHORT).show();
                                        }
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
                    //Toast.makeText(getApplicationContext(), String.valueOf(CustomersonRV.get(pos).getId()),Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bindCustomers(Customers customer) {

            lastname.setText(customer.getLast_name());
            firstname.setText(customer.getFirst_name());
            id.setText(String.valueOf(customer.getId()));
        }
    }

    private class CustomersAdapter extends RecyclerView.Adapter<CustomersHolder> {

        private List<Customers> customers;

        public CustomersAdapter(List<Customers> customers) {
            this.customers = customers;
        }

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
        final Inventory inventory = new Inventory(getApplicationContext());
        backbutton = (ImageButton) findViewById(R.id.imageButtonBack);
        addclientbutton = (ImageButton) findViewById(R.id.imageButtonAdd);
        Edittextname = (EditText) findViewById(R.id.edittext_name);
        recyclerView = (RecyclerView) findViewById(R.id.clients_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        findclientebtn = (ImageButton) findViewById(R.id.Imagebtn_findclient);

        spinner = (MultiSelectionSpinner) findViewById(R.id.mySpinner1);
        final List<String> Items_Spinners = new ArrayList<String>();

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
                String firstname = "";
                String lastname = "";
                String address = "";
                String phone = "";
                String email = "";
                String descrip = Edittextname.getText().toString();

                Inventory inventory1 = new Inventory(getApplicationContext());
                for (Integer searchby : spinner.getSelectedIndicies()) {
                    if (searchby == 0) {
                        firstname = "first_name";
                    }
                    if (searchby == 1) {
                        lastname = "last_name";
                    }
                    if (searchby == 2) {
                        address = "address";
                    }
                    if (searchby == 3) {
                        phone = "phone1 or phone2 or phone3";
                    }
                    if (searchby == 4) {
                        email = "e_mail";
                    }
                }
                List<Customers> clientsfounded = new ArrayList<Customers>();
                clientsfounded = inventory1.findby(firstname, lastname, address, phone, email, descrip);
                adapter = new CustomersAdapter(clientsfounded);
                recyclerView.setAdapter(adapter);
                CustomersonRV = clientsfounded;
                //Toast.makeText(getApplicationContext(), String.valueOf(CustomersonRV.size()),Toast.LENGTH_SHORT).show();

                if (firstname.length() == 0) {
                    if (lastname.length() == 0) {
                        if (address.length() == 0) {
                            if (phone.length() == 0) {
                                if (email.length() == 0) {
                                    List<Customers> listprove = new ArrayList<Customers>();
                                    adapter = new CustomersAdapter(listprove);
                                    recyclerView.setAdapter(adapter);


                                    listprove = inventory1.getAllCustomers();
                                    adapter = new CustomersAdapter(listprove);
                                    recyclerView.setAdapter(adapter);
                                    CustomersonRV = listprove;
                                    //Toast.makeText(getApplicationContext(), String.valueOf(CustomersonRV.size()),Toast.LENGTH_SHORT).show();


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
                final Dialog dialog = new Dialog(client.this);
                dialog.setTitle("Agregar Cliente");
                dialog.setContentView(R.layout.add_client_design);
                dialog.show();

                final EditText EditText_FirstName = (EditText) dialog.findViewById(R.id.EditText_ClientfirstName);
                final EditText EditText_LastName = (EditText) dialog.findViewById(R.id.EditText_ClientlastName);
                final EditText EditText_Address = (EditText) dialog.findViewById(R.id.EditText_ClientAddress);
                final EditText EditText_phone1_lada = (EditText) dialog.findViewById(R.id.EditText_phonelada);
                final EditText EditText_phone1 = (EditText) dialog.findViewById(R.id.EditText_phone);
                final EditText EditText_phone2_lada = (EditText) dialog.findViewById(R.id.EditText_phonelada2);
                final EditText EditText_phone2 = (EditText) dialog.findViewById(R.id.EditText_phone2);
                final EditText EditText_phone3_lada = (EditText) dialog.findViewById(R.id.EditText_phonelada3);
                final EditText EditText_phone3 = (EditText) dialog.findViewById(R.id.EditText_phone3);
                final EditText EditText_Email = (EditText) dialog.findViewById(R.id.EditText_e_mail);
                final CheckBox ChB_phone1 = (CheckBox) dialog.findViewById(R.id.CheckBox_phone1);
                final CheckBox ChB_phone2 = (CheckBox) dialog.findViewById(R.id.CheckBox_phone2);
                final CheckBox ChB_phone3 = (CheckBox) dialog.findViewById(R.id.CheckBox_phone3);
                final CheckBox ChB_e_mail = (CheckBox) dialog.findViewById(R.id.CheckBox_e_mail);
                Button btn_addclient = (Button) dialog.findViewById(R.id.btn_confirm);
                Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);


                ChB_phone1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ChB_phone1.isChecked() == true) {
                            EditText_phone1.setEnabled(true);
                            EditText_phone1_lada.setEnabled(true);
                        } else {
                            EditText_phone1.setEnabled(false);
                            EditText_phone1_lada.setEnabled(false);
                        }
                    }
                });
                ChB_phone2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ChB_phone2.isChecked() == true) {
                            EditText_phone2.setEnabled(true);
                            EditText_phone2_lada.setEnabled(true);
                        } else {
                            EditText_phone2.setEnabled(false);
                            EditText_phone2_lada.setEnabled(false);
                        }
                    }
                });
                ChB_phone3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ChB_phone3.isChecked() == true) {
                            EditText_phone3.setEnabled(true);
                            EditText_phone3_lada.setEnabled(true);
                        } else {
                            EditText_phone3.setEnabled(false);
                            EditText_phone3_lada.setEnabled(false);
                        }
                    }
                });
                ChB_e_mail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ChB_e_mail.isChecked() == true) {
                            EditText_Email.setEnabled(true);
                        } else {
                            EditText_Email.setEnabled(false);
                        }
                    }
                });


                btn_addclient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String firstname = "";
                        String lastname = "";
                        String address = "";
                        String phone1 = "";
                        String phone2 = "";
                        String phone3 = "";
                        String email = "";
                        firstname = EditText_FirstName.getText().toString();
                        lastname = EditText_LastName.getText().toString();
                        address = EditText_Address.getText().toString();
                        if (ChB_phone1.isChecked() == false) {
                            phone1 = null;
                        } else {
                            phone1 = "'" + EditText_phone1_lada.getText().toString() + "-" + EditText_phone1.getText().toString() + "'";
                        }
                        if (ChB_phone2.isChecked() == false) {
                            phone2 = null;
                        } else {
                            phone2 = "'" + EditText_phone2_lada.getText().toString() + "-" + EditText_phone2.getText().toString() + "'";
                        }
                        if (ChB_phone3.isChecked() == false) {
                            phone3 = null;
                        } else {
                            phone3 = "'" + EditText_phone3_lada.getText().toString() + "-" + EditText_phone3.getText().toString() + "'";
                        }
                        if (ChB_e_mail.isChecked() == false) {
                            email = null;
                        } else {
                            email = "'" + EditText_Email.getText().toString() + "'";
                        }
                        inventory.addCustomer(firstname, lastname, address, phone1, phone2, phone3, email);
                        Toast.makeText(getApplicationContext(), "El cliente ha sido agregado exitosamente", Toast.LENGTH_SHORT).show();
                        adapter = new CustomersAdapter(inventory.getAllCustomers());
                        recyclerView.setAdapter(adapter);
                        CustomersonRV = inventory.getAllCustomers();
                        dialog.cancel();
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

            }
        });
    }
}
