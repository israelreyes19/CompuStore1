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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import android.widget.SearchView;

import com.fiuady.android.compustore.db.Category;
import com.fiuady.android.compustore.db.Customers;
import com.fiuady.android.compustore.db.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class client extends AppCompatActivity {
    public List<Customers> CustomersonRV = new ArrayList<Customers>();

    private class CustomersHolder extends RecyclerView.ViewHolder {

        private TextView lastname;
        private TextView firstname;
        private TextView id;

        public CustomersHolder(final View itemView) {
            super(itemView);
            lastname = (TextView) itemView.findViewById(R.id.txtlastname);
            firstname = (TextView) itemView.findViewById(R.id.txtfirstname);
            id = (TextView) itemView.findViewById(R.id.txt_id);
            final Inventory inventory2 = new Inventory(getApplicationContext());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition();
                    POS_AUX = pos;
                    ANITEMWASCLICK_AUX = true;
                    PopupMenu popupMenu = new PopupMenu(client.this, itemView);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                    if (inventory2.canudeletecustomer(CustomersonRV.get(pos)) == false) {
                        popupMenu.getMenu().clear();
                        popupMenu.getMenu().add("Editar");
                    }

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().length() == "Eliminar".length()) {
                                ANITEMWASCLICK_AUX = false;
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(client.this);
                                builder1.setTitle("¿Deseas borrar este cliente?");
                                builder1.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        Inventory inventory = new Inventory(client.this);
                                        if (inventory.deleteCustomer(CustomersonRV.get(pos))) {
                                            Toast.makeText(client.this, "Se eliminó exitosamente " +
                                                    "el cliente", Toast.LENGTH_SHORT).show();
                                            adapter = new client.CustomersAdapter(inventory.getAllCustomers());
                                            recyclerView.setAdapter(adapter);
                                            CustomersonRV = inventory.getAllCustomers();

                                        } else {
                                            Toast.makeText(client.this, "No se pudo eliminar  el cliente. " +
                                                    "Existen pedidos ", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                builder1.show();
                            } else {
                                ANITEMWASCLICK_AUX = false;
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
                                final TextView tittle = (TextView) dialog1.findViewById(R.id.add_client_title);
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
                                if (CustomersonRV.get(pos).getPhone1() != null) {
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
                                if (CustomersonRV.get(pos).getPhone2() != null) {
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
                                if (CustomersonRV.get(pos).getPhone3() != null) {
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
                                if (CustomersonRV.get(pos).getE_mail() != null) {
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
                                        String AuxPhone2 = "";
                                        String AuxPhone3 = "";
                                        String AuxEmail = "";
                                        if (ChB_phone1.isChecked() == false) {
                                            AuxPhone1 = null;
                                        } else {
                                            AuxPhone1 = "'" + EditText_phone1_lada.getText().toString() + "-" + EditText_phone1.getText().toString() + "'";
                                        }
                                        if (ChB_phone2.isChecked() == false) {
                                            AuxPhone2 = null;
                                        } else {
                                            AuxPhone2 = "'" + EditText_phone2_lada.getText().toString() + "-" + EditText_phone2.getText().toString() + "'";
                                        }
                                        if (ChB_phone3.isChecked() == false) {
                                            AuxPhone3 = null;
                                        } else {
                                            AuxPhone3 = "'" + EditText_phone3_lada.getText().toString() + "-" + EditText_phone3.getText().toString() + "'";
                                        }
                                        if (ChB_e_mail.isChecked() == false) {
                                            AuxEmail = null;
                                        } else {
                                            AuxEmail = "'" + EditText_Email.getText().toString() + "'";
                                        }
                                        Customers customerU = new Customers(CustomersonRV.get(pos).getId(),
                                                EditText_FirstName.getText().toString(), EditText_LastName.getText().toString(),
                                                EditText_Address.getText().toString(), AuxPhone1, AuxPhone2, AuxPhone3, AuxEmail);
                                        inventory2.updateCustomer(customerU);
                                        Toast.makeText(getApplicationContext(), "El cliente fue actualizado", Toast.LENGTH_SHORT).show();
                                        adapter = new CustomersAdapter(inventory2.getAllCustomers());
                                        CustomersonRV = inventory2.getAllCustomers();
                                        recyclerView.setAdapter(adapter);
                                        dialog1.cancel();
                                    }
                                });
                            }
                            return true;
                        }
                    });
                    popupMenu.show();


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
    private ImageButton backbutton;
    private ImageButton addclientbutton;
    private SearchView SV_Client;
    MultiSelectionSpinner spinner;
    private final static String KEY_FINDCLIENTSWASCLICK = "find_clienteswasclick", KEY_ADDCLIENTEWASCLICK = "com.fiuady.android.compustore.ADDCLIENT";
    static final String KEY_ADDCLIENTE_FIRSTNAME = "KEY_ADDCLIENTE_FIRSTNAME", KEY_ADDCLIENTE_LASTNAME = "KEY_ADDCLIENTE_LASTNAME";
    static final String KEY_ADDCLIENTE_ADDRESS = "KEY_ADDCLIENTE_ADDRESS", KEY_ADDCLIENTE_EMAIL = "KEY_ADDCLIENTE_EMAIL";
    static final String KEY_ADDCLIENTE_LADA1 = "KEY_ADDCLIENTE_LADA1", KEY_ADDCLIENTE_PHONE1 = "KEY_ADDCLIENTE_PHONE1";
    static final String KEY_ADDCLIENTE_LADA2 = "KEY_ADDCLIENTE_LADA2", KEY_ADDCLIENTE_PHONE2 = "KEY_ADDCLIENTE_PHONE2";
    static final String KEY_ADDCLIENTE_LADA3 = "KEY_ADDCLIENTE_LADA3", KEY_ADDCLIENTE_PHONE3 = "KEY_ADDCLIENTE_PHONE3";
    static final String KEY_ADDCLIENTE_CHKBP1 = "KEY_ADDCLIENTE_CHKBP1", KEY_ADDCLIENTE_CHKBP2 = "KEY_ADDCLIENTE_CHKBP2";
    static final String KEY_ADDCLIENTE_CHKBP3 = "KEY_ADDCLIENTE_CHKBP3", KEY_ADDCLIENTE_CHKBE = "KEY_ADDCLIENTE_CHKBE";
    static final String KEY_POS_AUX = "KEY_POS_AUX", KEY_ANITEMWASCLICK_AUX="KEY_ANITEMWASCLICK_AUX";
    private String ADD_CLIENT_FIRSTNAME = "", ADD_CLIENT_LASTNAME = "", ADD_CLIENT_ADDRESS = "", ADD_CLIENT_EMAIL = "",
            ADD_CLIENT_LADA1 = "", ADD_CLIENT_PHONE1 = "", ADD_CLIENT_LADA2 = "", ADD_CLIENT_PHONE2 = "", ADD_CLIENT_LADA3 = "", ADD_CLIENT_PHONE3 = "";
    private Boolean searchwasclick = false, addclientwasclick = false, ADD_CLIENT_CHB1=false,ADD_CLIENT_CHB2=false,
            ADD_CLIENT_CHB3=false,ADD_CLIENT_CHBE=false,ANITEMWASCLICK_AUX=false;
    private int POS_AUX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        final Inventory inventory = new Inventory(getApplicationContext());
        backbutton = (ImageButton) findViewById(R.id.imageButtonBack);
        addclientbutton = (ImageButton) findViewById(R.id.imageButtonAdd);
        recyclerView = (RecyclerView) findViewById(R.id.clients_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SV_Client = (SearchView) findViewById(R.id.SearchView_client);
        CustomersonRV = inventory.getAllCustomers();
        ShowClientes(CustomersonRV);

        if (savedInstanceState != null) {
            addclientwasclick = savedInstanceState.getBoolean(KEY_ADDCLIENTEWASCLICK, false);
            ADD_CLIENT_FIRSTNAME = savedInstanceState.getString(KEY_ADDCLIENTE_FIRSTNAME);
            ADD_CLIENT_LASTNAME = savedInstanceState.getString(KEY_ADDCLIENTE_LASTNAME);
            ADD_CLIENT_ADDRESS = savedInstanceState.getString(KEY_ADDCLIENTE_ADDRESS);
            ADD_CLIENT_LADA1 = savedInstanceState.getString(KEY_ADDCLIENTE_LADA1);
            ADD_CLIENT_PHONE1= savedInstanceState.getString(KEY_ADDCLIENTE_PHONE1);
            ADD_CLIENT_LADA2 = savedInstanceState.getString(KEY_ADDCLIENTE_LADA2);
            ADD_CLIENT_PHONE2= savedInstanceState.getString(KEY_ADDCLIENTE_PHONE2);
            ADD_CLIENT_LADA3 = savedInstanceState.getString(KEY_ADDCLIENTE_LADA3);
            ADD_CLIENT_PHONE3= savedInstanceState.getString(KEY_ADDCLIENTE_PHONE3);
            ADD_CLIENT_EMAIL = savedInstanceState.getString(KEY_ADDCLIENTE_EMAIL);
            ADD_CLIENT_CHB1 = savedInstanceState.getBoolean(KEY_ADDCLIENTE_CHKBP1,false);
            ADD_CLIENT_CHB2 = savedInstanceState.getBoolean(KEY_ADDCLIENTE_CHKBP2,false);
            ADD_CLIENT_CHB3 = savedInstanceState.getBoolean(KEY_ADDCLIENTE_CHKBP3,false);
            ADD_CLIENT_CHBE = savedInstanceState.getBoolean(KEY_ADDCLIENTE_CHKBE,false);


            if (addclientwasclick) {
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
                final Button btn_addclient = (Button) dialog.findViewById(R.id.btn_confirm);
                Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                EditText_FirstName.setText(ADD_CLIENT_FIRSTNAME);
                EditText_LastName.setText(ADD_CLIENT_LASTNAME);
                EditText_Address.setText(ADD_CLIENT_ADDRESS);
                EditText_phone1_lada.setText(ADD_CLIENT_LADA1);
                EditText_phone1.setText(ADD_CLIENT_PHONE1);
                EditText_phone2_lada.setText(ADD_CLIENT_LADA2);
                EditText_phone2.setText(ADD_CLIENT_PHONE2);
                EditText_phone3_lada.setText(ADD_CLIENT_LADA3);
                EditText_phone3.setText(ADD_CLIENT_PHONE3);
                EditText_Email.setText(ADD_CLIENT_EMAIL);
                ChB_phone1.setChecked(ADD_CLIENT_CHB1);
                ChB_phone2.setChecked(ADD_CLIENT_CHB2);
                ChB_phone3.setChecked(ADD_CLIENT_CHB3);
                ChB_e_mail.setChecked(ADD_CLIENT_CHBE);
                if (ChB_phone1.isChecked() == true) {EditText_phone1.setEnabled(true);EditText_phone1_lada.setEnabled(true);}
                else {EditText_phone1.setEnabled(false);EditText_phone1_lada.setEnabled(false);}
                if (ChB_phone2.isChecked() == true) {EditText_phone2.setEnabled(true);EditText_phone2_lada.setEnabled(true);}
                else {EditText_phone2.setEnabled(false);EditText_phone2_lada.setEnabled(false);}
                if (ChB_phone3.isChecked() == true) {EditText_phone3.setEnabled(true);EditText_phone3_lada.setEnabled(true);}
                else {EditText_phone3.setEnabled(false);EditText_phone3_lada.setEnabled(false);}
                if (ChB_e_mail.isChecked() == true) {EditText_Email.setEnabled(true);}
                else {EditText_Email.setEnabled(false);}
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
                        ShowClientes(inventory.getAllCustomers());
                        CustomersonRV = inventory.getAllCustomers();
                        dialog.cancel();
                        addclientwasclick = false;
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        addclientwasclick = false;
                    }
                });
            }
            if (ANITEMWASCLICK_AUX)
            {

                //Toast.makeText(getApplicationContext(),String.valueOf(CustomersonRV.size()),Toast.LENGTH_LONG).show();
            }
        }

        spinner = (MultiSelectionSpinner) findViewById(R.id.mySpinner1);
        final List<String> Items_Spinners = new ArrayList<String>();

        Items_Spinners.add("Nombre");
        Items_Spinners.add("Apellido");
        Items_Spinners.add("Dirección");
        Items_Spinners.add("Telefono");
        Items_Spinners.add("e_mail");

        spinner.setItems(Items_Spinners);
        List<Customers> listprove = new ArrayList<Customers>();
        //recyclerView.setAdapter(adapter);


        SV_Client.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
// do something on text submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
// do something when text changes
                searchwasclick = true;
                boolean bool = searchwasclick;
                String firstname = "";
                String lastname = "";
                String address = "";
                String phone = "";
                String email = "";
                String descrip = newText;

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
                List<Customers> clientsfounded = new ArrayList<>();
                clientsfounded = inventory1.findby(firstname, lastname, address, phone, email, descrip);
                adapter = new CustomersAdapter(clientsfounded);
                recyclerView.setAdapter(adapter);
                CustomersonRV = clientsfounded;

                return false;
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
                addclientwasclick = true;
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
                final Button btn_addclient = (Button) dialog.findViewById(R.id.btn_confirm);
                Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);


                ChB_phone1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ChB_phone1.isChecked() == true) {
                            ADD_CLIENT_CHB1= true;
                            EditText_phone1.setEnabled(true);
                            EditText_phone1_lada.setEnabled(true);
                        } else {
                            ADD_CLIENT_CHB1= false;
                            EditText_phone1.setEnabled(false);
                            EditText_phone1_lada.setEnabled(false);
                        }
                    }
                });
                ChB_phone2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ChB_phone2.isChecked() == true) {
                            ADD_CLIENT_CHB2= true;
                            EditText_phone2.setEnabled(true);
                            EditText_phone2_lada.setEnabled(true);
                        } else {
                            ADD_CLIENT_CHB2= false;
                            EditText_phone2.setEnabled(false);
                            EditText_phone2_lada.setEnabled(false);
                        }
                    }
                });
                ChB_phone3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ChB_phone3.isChecked() == true) {
                            ADD_CLIENT_CHB3= true;
                            EditText_phone3.setEnabled(true);
                            EditText_phone3_lada.setEnabled(true);
                        } else {
                            ADD_CLIENT_CHB3=false;
                            EditText_phone3.setEnabled(false);
                            EditText_phone3_lada.setEnabled(false);
                        }
                    }
                });
                ChB_e_mail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ChB_e_mail.isChecked() == true) {
                            ADD_CLIENT_CHBE= true;
                            EditText_Email.setEnabled(true);
                        } else {
                            ADD_CLIENT_CHBE = false;
                            EditText_Email.setEnabled(false);
                        }
                    }
                });
                EditText_FirstName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ADD_CLIENT_FIRSTNAME = String.valueOf(s);

                    }
                });
                EditText_LastName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ADD_CLIENT_LASTNAME = String.valueOf(s);

                    }
                });
                EditText_Address.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ADD_CLIENT_ADDRESS = String.valueOf(s);
                    }
                });
                EditText_phone1_lada.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ADD_CLIENT_LADA1 = String.valueOf(s);

                    }
                });
                EditText_phone1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ADD_CLIENT_PHONE1 = String.valueOf(s);
                    }
                });
                EditText_phone2_lada.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ADD_CLIENT_LADA2 = String.valueOf(s);
                    }
                });
                EditText_phone2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ADD_CLIENT_PHONE2 = String.valueOf(s);
                    }
                });
                EditText_phone3_lada.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ADD_CLIENT_LADA3 = String.valueOf(s);
                    }
                });
                EditText_phone3.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ADD_CLIENT_PHONE3 = String.valueOf(s);
                    }
                });
                EditText_Email.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ADD_CLIENT_EMAIL = String.valueOf(s);
                    }
                });
                btn_addclient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if ((EditText_FirstName.getText().length()==0)||(EditText_LastName.getText().length()==0)){
                            Toast.makeText(getApplicationContext(),"Agrega NOMBRE y APELLiDO",Toast.LENGTH_LONG).show();
                        }
                        else{
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
                            ShowClientes(inventory.getAllCustomers());
                            CustomersonRV = inventory.getAllCustomers();
                            dialog.cancel();
                        }

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

    public void ShowClientes(List<Customers> customers) {
        adapter = new CustomersAdapter(customers);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_FINDCLIENTSWASCLICK, searchwasclick);
        outState.putBoolean(KEY_ADDCLIENTEWASCLICK, addclientwasclick);
        outState.putString(KEY_ADDCLIENTE_FIRSTNAME, ADD_CLIENT_FIRSTNAME);
        outState.putString(KEY_ADDCLIENTE_LASTNAME, ADD_CLIENT_LASTNAME);
        outState.putString(KEY_ADDCLIENTE_ADDRESS, ADD_CLIENT_ADDRESS);
        outState.putString(KEY_ADDCLIENTE_LADA1, ADD_CLIENT_LADA1);
        outState.putString(KEY_ADDCLIENTE_PHONE1, ADD_CLIENT_PHONE1);
        outState.putString(KEY_ADDCLIENTE_LADA2, ADD_CLIENT_LADA2);
        outState.putString(KEY_ADDCLIENTE_PHONE2, ADD_CLIENT_PHONE2);
        outState.putString(KEY_ADDCLIENTE_LADA3, ADD_CLIENT_LADA3);
        outState.putString(KEY_ADDCLIENTE_PHONE3, ADD_CLIENT_PHONE3);
        outState.putString(KEY_ADDCLIENTE_EMAIL, ADD_CLIENT_EMAIL);
        outState.putBoolean(KEY_ADDCLIENTE_CHKBE, ADD_CLIENT_CHBE);
        outState.putBoolean(KEY_ADDCLIENTE_CHKBP1, ADD_CLIENT_CHB1);
        outState.putBoolean(KEY_ADDCLIENTE_CHKBP2, ADD_CLIENT_CHB2);
        outState.putBoolean(KEY_ADDCLIENTE_CHKBP3, ADD_CLIENT_CHB3);


        //outState.putInt(KEY_POS_AUX,POS_AUX);
        //outState.putBoolean(KEY_ANITEMWASCLICK_AUX,ANITEMWASCLICK_AUX);
        //agregar listas
    }
}
