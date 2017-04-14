package com.fiuady.android.compustore;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.android.compustore.db.Customers;
import com.fiuady.android.compustore.db.Inventory;
import com.fiuady.android.compustore.db.Order;
import com.fiuady.android.compustore.db.Order_status;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class orders extends AppCompatActivity {

    private class OrdersHolder extends RecyclerView.ViewHolder {
        private ImageView image_status;
        private TextView id_order, id_status, client_name, date_name;

        public OrdersHolder(final View itemView) {
            super(itemView);
            image_status = (ImageView) itemView.findViewById(R.id.Image_View_Status);
            id_order = (TextView) itemView.findViewById(R.id.Txt_Id_order);
            id_status = (TextView) itemView.findViewById(R.id.Txt_Id_status);
            client_name = (TextView) itemView.findViewById(R.id.Txt_client_order);
            date_name = (TextView) itemView.findViewById(R.id.Txt_date_order);

            //When u touch a item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition();
                    final PopupMenu popupMenu = new PopupMenu(orders.this, itemView);
                    popupMenu.getMenuInflater().inflate(R.menu.pop_menu_order, popupMenu.getMenu());
                    if (OrdersOnRV.get(pos).getStatus_id() == order_status.get(0).getId()) {popupMenu.getMenu().add("Modificar Orden");}
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().length()== "Modificar Estado".length()){
                                PopupMenu popupMenu1 = new PopupMenu(orders.this, itemView);
                                popupMenu1.getMenuInflater().inflate(R.menu.popup_menu_modify_status, popupMenu1.getMenu());

                                if (OrdersOnRV.get(pos).getStatus_id()==0){popupMenu1.getMenu().add("Confirmado");popupMenu1.getMenu().add("Cancelado");}
                                else  if (OrdersOnRV.get(pos).getStatus_id()==1){popupMenu1.getMenu().add("Pendiente");}
                                else  if (OrdersOnRV.get(pos).getStatus_id()==2){popupMenu1.getMenu().add("En transito");}
                                else  if (OrdersOnRV.get(pos).getStatus_id()==3){popupMenu1.getMenu().add("Finalizado");}
                                popupMenu1.show();
                            }
                            else { // se seleccionó modificar Orden

                            }
                            return false;
                        }
                    });

                    popupMenu.show();
                }
            });
        }

        public void bindOrders(Order order) {
            if (order.getStatus_id() == 0) {
                image_status.setImageResource(R.drawable.pending);
                id_status.setText("Pendiente");
            } else if (order.getStatus_id() == 1) {
                image_status.setImageResource(R.drawable.cancel);
                id_status.setText("Cancelado");
            } else if (order.getStatus_id() == 2) {
                image_status.setImageResource(R.drawable.confirmed);
                id_status.setText("Confirmado");
            } else if (order.getStatus_id() == 3) {
                image_status.setImageResource(R.drawable.ontransit);
                id_status.setText("En transito");
            } else {
                image_status.setImageResource(R.drawable.finished);
                id_status.setText("Finalizado");
            }

            id_order.setText(String.valueOf(order.getId()));
            client_name.setText(String.valueOf(order.getCustomer_id()));
            date_name.setText(String.valueOf(order.getDate()));
            //  (0, 'Pendiente',
            //  (1, 'Cancelado',
            //  (2, 'Confirmado',
            // (3, 'En tránsito'
            // (4, 'Finalizado',
        }
    }

    private class OrdersAdapter extends RecyclerView.Adapter<OrdersHolder> {
        private List<Order> orders;

        public OrdersAdapter(List<Order> orders) {
            this.orders = orders;
        }

        @Override
        public OrdersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
            return new OrdersHolder(v);
        }

        @Override
        public void onBindViewHolder(OrdersHolder holder, int position) {
            holder.bindOrders(orders.get(position));
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }
    }

    private Inventory inventory;
    private RecyclerView recyclerView;
    private OrdersAdapter OrdersAdapter;
    private Spinner spinerstatus;
    private Spinner spinner_clients;
    private ImageButton add_order, backactiviy;
    private Button btn_oldDate, btn_newDate;
    private CheckBox ChB_oldDate, ChB_newDate;
    List<Customers> clientsfounded = new ArrayList<Customers>();
    private boolean pendiente_IsSel = false, cancelado_IsSel = false, confirmado_IsSel = false, entransito_IsSel = false, finalizado_IsSel = false;
    private int oldDate_DayX, oldDate_MonthX, oldDate_YearX, i;
    private TextView txt_status_order;
    private List<Order> OrdersOnRV = new ArrayList<Order>();
    private List<Order_status> order_status = new ArrayList<Order_status>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        // Definición de componenetes gráficos
        add_order = (ImageButton) findViewById(R.id.imageButtonAddOrder);
        backactiviy = (ImageButton) findViewById(R.id.BackActivy);
        spinerstatus = (Spinner) findViewById(R.id.SpinerStatus);
        spinner_clients = (Spinner) findViewById(R.id.spinner_clients);
        btn_oldDate = (Button) findViewById(R.id.Btn_Selectolddate);
        btn_newDate = (Button) findViewById(R.id.Btn_SelectNewdate);
        ChB_oldDate = (CheckBox) findViewById(R.id.ChB_oldDate);
        ChB_newDate = (CheckBox) findViewById(R.id.ChB_newDate);
        txt_status_order = (TextView) findViewById(R.id.Txt_status_order);
        recyclerView = (RecyclerView) findViewById(R.id.orders_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //Componentes Auxiliares
        inventory = new Inventory(getApplicationContext());
        clientsfounded = inventory.getAllCustomers();
        List<String> spinnerArray = new ArrayList<String>();
        order_status = inventory.getAllOrder_Status();
        //for (Order_status status: order_status){Toast.makeText(getApplicationContext(),String.valueOf(status.getId()),Toast.LENGTH_SHORT).show();}

        //Llenar los componentes
        spinnerArray.add("TODOS");
        for (Customers customer : clientsfounded) {
            spinnerArray.add(String.valueOf(customer.getLast_name()) + " " + String.valueOf(customer.getFirst_name()));
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_clients.setAdapter(adapter);


        //Eventos de los componentes

        final View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    final Dialog dialog = new Dialog(orders.this);
                    dialog.setTitle("Estados: ");
                    dialog.setContentView(R.layout.spinnercheckbox_options);
                    dialog.show();
                    //Definicion de componentes dentro del dialogo
                    final CheckBox ChB_pendiente = (CheckBox) dialog.findViewById(R.id.checkBox_pendiente);
                    final CheckBox ChB_Cancelado = (CheckBox) dialog.findViewById(R.id.checkBox2_cancelado);
                    final CheckBox ChB_Confirmado = (CheckBox) dialog.findViewById(R.id.checkBox3_confirmado);
                    final CheckBox ChB_EnTransito = (CheckBox) dialog.findViewById(R.id.checkBox4_entransito);
                    final CheckBox ChB_Finalizado = (CheckBox) dialog.findViewById(R.id.checkBox5_finalizado);
                    final Button Btn_acept = (Button) dialog.findViewById(R.id.btn_aceptar);
                    final Button Btn_cancel = (Button) dialog.findViewById(R.id.btn_cancelar);
                    ChB_pendiente.setChecked(pendiente_IsSel);
                    ChB_Cancelado.setChecked(cancelado_IsSel);
                    ChB_Confirmado.setChecked(confirmado_IsSel);
                    ChB_EnTransito.setChecked(entransito_IsSel);
                    ChB_Finalizado.setChecked(finalizado_IsSel);
                    Btn_acept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pendiente_IsSel = ChB_pendiente.isChecked();
                            cancelado_IsSel = ChB_Cancelado.isChecked();
                            confirmado_IsSel = ChB_Confirmado.isChecked();
                            entransito_IsSel = ChB_EnTransito.isChecked();
                            finalizado_IsSel = ChB_Finalizado.isChecked();
                            String auxS = "";
                            if (pendiente_IsSel) {
                                auxS = auxS + "Pendiente/ ";
                            }
                            if (cancelado_IsSel) {
                                auxS = auxS + "Cancelado/ ";
                            }
                            if (confirmado_IsSel) {
                                auxS = auxS + "Confirmado/ ";
                            }
                            if (entransito_IsSel) {
                                auxS = auxS + "En transito/ ";
                            }
                            if (finalizado_IsSel) {
                                auxS = auxS + "Finalizado ";
                            }
                            txt_status_order.setText(auxS);
                            if (spinner_clients.getSelectedItem().toString().equals("TODOS")) {
                                if (pendiente_IsSel && cancelado_IsSel && confirmado_IsSel && entransito_IsSel) {
                                    OrdersAdapter = new OrdersAdapter(inventory.getAllOrders());
                                    recyclerView.setAdapter(OrdersAdapter);
                                    OrdersOnRV = inventory.getAllOrders();
                                } else {
                                    OrdersAdapter = new OrdersAdapter(inventory.getAllordersWithSpecificsStatusOrders(pendiente_IsSel, cancelado_IsSel, confirmado_IsSel, entransito_IsSel, finalizado_IsSel));
                                    recyclerView.setAdapter(OrdersAdapter);
                                    OrdersOnRV = inventory.getAllordersWithSpecificsStatusOrders(pendiente_IsSel, cancelado_IsSel, confirmado_IsSel, entransito_IsSel, finalizado_IsSel);
                                }
                            } else {
                                int aux;
                                for (Customers customer : clientsfounded) {
                                    if (spinner_clients.getSelectedItem().toString().equals((customer.getLast_name() + " " + customer.getFirst_name()))) {
                                        aux = customer.getId();
                                        OrdersAdapter = new OrdersAdapter(inventory.getSpecificiOrdersWithCustomerandStatus(pendiente_IsSel, cancelado_IsSel, confirmado_IsSel, entransito_IsSel, finalizado_IsSel, aux));
                                        recyclerView.setAdapter(OrdersAdapter);
                                        OrdersOnRV = inventory.getSpecificiOrdersWithCustomerandStatus(pendiente_IsSel, cancelado_IsSel, confirmado_IsSel, entransito_IsSel, finalizado_IsSel, aux);

                                    }
                                }


                            }

                            dialog.cancel();
                        }
                    });
                    Btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();

                        }
                    });

                }
                return false;
            }
        };
        spinerstatus.setOnTouchListener(spinnerOnTouch);

        btn_oldDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                oldDate_DayX = c.get(Calendar.DAY_OF_MONTH);
                oldDate_MonthX = c.get(Calendar.MONTH);
                oldDate_YearX = c.get(Calendar.YEAR);

                new DatePickerDialog(orders.this, listener, oldDate_YearX, oldDate_MonthX, oldDate_DayX).show();
            }
        });
        btn_newDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                oldDate_DayX = c.get(Calendar.DAY_OF_MONTH);
                oldDate_MonthX = c.get(Calendar.MONTH);
                oldDate_YearX = c.get(Calendar.YEAR);

                new DatePickerDialog(orders.this, listener1, oldDate_YearX, oldDate_MonthX, oldDate_DayX).show();

            }
        });

        ChB_newDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ChB_newDate.isChecked()) {
                    btn_newDate.setEnabled(true);
                } else {
                    btn_newDate.setEnabled(false);
                }
            }
        });
        ChB_oldDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ChB_oldDate.isChecked()) {
                    btn_oldDate.setEnabled(true);
                } else {
                    btn_oldDate.setEnabled(false);
                }


            }
        });

        spinner_clients.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        Object item = parent.getItemAtPosition(pos);
                        if (spinner_clients.getSelectedItem().toString().equals("TODOS")) {
                            if (pendiente_IsSel && cancelado_IsSel && confirmado_IsSel && entransito_IsSel) {
                                OrdersAdapter = new OrdersAdapter(inventory.getAllOrders());
                                recyclerView.setAdapter(OrdersAdapter);
                                OrdersOnRV = inventory.getAllOrders();
                            } else {
                                OrdersAdapter = new OrdersAdapter(inventory.getAllordersWithSpecificsStatusOrders(pendiente_IsSel, cancelado_IsSel, confirmado_IsSel, entransito_IsSel, finalizado_IsSel));
                                recyclerView.setAdapter(OrdersAdapter);
                                OrdersOnRV = inventory.getAllordersWithSpecificsStatusOrders(pendiente_IsSel, cancelado_IsSel, confirmado_IsSel, entransito_IsSel, finalizado_IsSel);

                            }
                        } else {
                            int aux;
                            for (Customers customer : clientsfounded) {
                                if (spinner_clients.getSelectedItem().toString().equals((customer.getLast_name() + " " + customer.getFirst_name()))) {
                                    aux = customer.getId();
                                    OrdersAdapter = new OrdersAdapter(inventory.getSpecificiOrdersWithCustomerandStatus(pendiente_IsSel, cancelado_IsSel, confirmado_IsSel, entransito_IsSel, finalizado_IsSel, aux));
                                    recyclerView.setAdapter(OrdersAdapter);
                                    OrdersOnRV = inventory.getSpecificiOrdersWithCustomerandStatus(pendiente_IsSel, cancelado_IsSel, confirmado_IsSel, entransito_IsSel, finalizado_IsSel, aux);
                                }
                            }


                        }

                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        backactiviy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            if (month < 10 && dayOfMonth < 10) {
                btn_oldDate.setText("0" + dayOfMonth + "-0" + month + "-" + year);
            } else if (dayOfMonth < 10) {
                btn_oldDate.setText("0" + dayOfMonth + "-" + month + "-" + year);
            } else if (month < 10) {
                btn_oldDate.setText(dayOfMonth + "-0" + month + "-" + year);
            } else {
                btn_oldDate.setText(dayOfMonth + "-" + month + "-" + year);
            }

        }
    };
    DatePickerDialog.OnDateSetListener listener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            if (month < 10 && dayOfMonth < 10) {
                btn_newDate.setText("0" + dayOfMonth + "-0" + month + "-" + year);
            } else if (dayOfMonth < 10) {
                btn_newDate.setText("0" + dayOfMonth + "-" + month + "-" + year);
            } else if (month < 10) {
                btn_newDate.setText(dayOfMonth + "-0" + month + "-" + year);
            } else {
                btn_newDate.setText(dayOfMonth + "-" + month + "-" + year);
            }

        }
    };

    public Order_status findStatus(int Status_id) {
        Order_status ord = null;
        for (Order_status orderstatus : order_status) {
            if (orderstatus.getId() == Status_id) {
                ord = orderstatus;
            }
        }
        return ord;
    }

    public String StatusDescription(int id_status)
    {
        String aux=null;
        if (id_status == 0){aux = "Pendiente";}
        else if (id_status == 1){aux = "Cancelado";}
        else if (id_status == 2){aux = "Confirmado";}
        else if (id_status == 3){aux = "En transito";}
        else if (id_status == 4){aux = "Finalizado";}
        else {aux = "No existe";}
        return aux;
    }
}
