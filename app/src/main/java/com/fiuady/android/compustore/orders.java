package com.fiuady.android.compustore;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
                    if (OrdersOnRV.get(pos).getStatus_id() == order_status.get(0).getId()) {
                        popupMenu.getMenu().add("Modificar Orden");
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().length() == "Modificar Estado".length()) {
                                PopupMenu popupMenu1 = new PopupMenu(orders.this, itemView);
                                popupMenu1.getMenuInflater().inflate(R.menu.popup_menu_modify_status, popupMenu1.getMenu());

                                if (OrdersOnRV.get(pos).getStatus_id() == 0) {
                                    popupMenu1.getMenu().add("Confirmado");
                                    popupMenu1.getMenu().add("Cancelado");
                                } else if (OrdersOnRV.get(pos).getStatus_id() == 1) {
                                    popupMenu1.getMenu().add("Pendiente");
                                } else if (OrdersOnRV.get(pos).getStatus_id() == 2) {
                                    popupMenu1.getMenu().add("En transito");
                                } else if (OrdersOnRV.get(pos).getStatus_id() == 3) {
                                    popupMenu1.getMenu().add("Finalizado");
                                } else if (OrdersOnRV.get(pos).getStatus_id() == 4) {
                                    Toast.makeText(getApplicationContext(), "La compra ha finalizado exitosamente", Toast.LENGTH_SHORT).show();

                                }

                                popupMenu1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        final Dialog dialog = new Dialog(orders.this);
                                        dialog.setTitle("Cambiar estado del pedido");
                                        dialog.setContentView(R.layout.change_order_state);
                                        dialog.show();
                                        final TextView txt_change_log = (TextView) dialog.findViewById(R.id.txt_change_log_changeorderstate);
                                        final TextView txt_currentdate = (TextView) dialog.findViewById(R.id.txt_currentdate_changeorderstate);
                                        final EditText EdTxt_New_Change_log = (EditText) dialog.findViewById(R.id.Edtxt_addcomentaries_changeorderstatus);
                                        final Button btn_confirm_log_change = (Button) dialog.findViewById(R.id.btn_confirm_changestatusorder);
                                        final Button btn_cancel_log_change = (Button) dialog.findViewById(R.id.btn_cancel_changestatusorder);
                                        txt_change_log.setText(OrdersOnRV.get(pos).getChange_log());
                                        txt_currentdate.setText("Fecha actual: " + getTodayCalendar());
                                        if (item.getTitle().toString().equals("Confirmado")) {
                                            i = 2;
                                        }
                                        if (item.getTitle().toString().equals("Cancelado")) {
                                            i = 1;
                                        }
                                        if (item.getTitle().toString().equals("Pendiente")) {
                                            i = 0;
                                        }
                                        if (item.getTitle().toString().equals("En transito")) {
                                            i = 3;
                                        }
                                        if (item.getTitle().toString().equals("Finalizado")) {
                                            i = 4;
                                        }
                                        btn_confirm_log_change.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String get_newchangelog = "";
                                                get_newchangelog = String.valueOf(txt_change_log.getText());

                                                get_newchangelog = get_newchangelog + " " + EdTxt_New_Change_log.getText().toString() + "-" + getTodayCalendar() + "\n";
                                                inventory.UpdateOrder_status(OrdersOnRV.get(pos).getId(), i, get_newchangelog);
                                                Toast.makeText(getApplicationContext(), "Se ha actualizado correctamente", Toast.LENGTH_SHORT).show();
                                                ShowOrders();
                                                dialog.cancel();
                                            }
                                        });
                                        btn_cancel_log_change.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.cancel();
                                            }
                                        });
                                        return false;
                                    }
                                });
                                popupMenu1.show();


                            } else { // se seleccionó modificar Orden
                                Intent i = new Intent(orders.this, modify_order.class);
                                i.putExtra("Order_Id", OrdersOnRV.get(pos).getId());
                                startActivity(i);

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
    private boolean pendiente_IsSel = false, cancelado_IsSel = false, confirmado_IsSel = false, entransito_IsSel = false,
            finalizado_IsSel = false, Old_date_IsSel=false, New_date_IsSel=false, SelectingStatus=false;
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
        int display_mode = getResources().getConfiguration().orientation;
        if (display_mode == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            GridLayoutManager manager = new GridLayoutManager(orders.this, 3);
            recyclerView.setLayoutManager(manager);
        }

        //Componentes Auxiliares
        inventory = new Inventory(getApplicationContext());
        clientsfounded = inventory.getAllCustomers();
        List<String> spinnerArray = new ArrayList<String>();
        order_status = inventory.getAllOrder_Status();

        //Llenar los componentes
        spinnerArray.add("TODOS");
        for (Customers customer : clientsfounded) {
            spinnerArray.add(String.valueOf(customer.getLast_name()) + " " + String.valueOf(customer.getFirst_name()));
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_clients.setAdapter(adapter);
        ChB_oldDate.setChecked(Old_date_IsSel);
        ChB_newDate.setChecked(New_date_IsSel);
        if (savedInstanceState != null) {
            pendiente_IsSel =  savedInstanceState.getBoolean("pendiente_IsSel");
            cancelado_IsSel =  savedInstanceState.getBoolean("cancelado_IsSel");
            confirmado_IsSel = savedInstanceState.getBoolean("confirmado_IsSel");
            entransito_IsSel = savedInstanceState.getBoolean("entransito_IsSel");
            finalizado_IsSel = savedInstanceState.getBoolean("finalizado_IsSel");
            Old_date_IsSel=  savedInstanceState.getBoolean("olddate_IsSel");
            New_date_IsSel=  savedInstanceState.getBoolean("newdate_IsSel");
            SelectingStatus = savedInstanceState.getBoolean("SelectingStatus");
            ChB_oldDate.setChecked(Old_date_IsSel);
            ChB_newDate.setChecked(New_date_IsSel);
            String auxOld = savedInstanceState.getString("Old_date_String");
            String auxNew = savedInstanceState.getString("New_date_String");
            btn_oldDate.setText(auxOld);
            btn_newDate.setText(auxNew);
            if (New_date_IsSel) {btn_newDate.setEnabled(true);} else {btn_newDate.setEnabled(false);}
            if (Old_date_IsSel) {btn_oldDate.setEnabled(true);} else {btn_oldDate.setEnabled(false);}
            if(SelectingStatus)
            {
                SelectingStatus = true;
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
               // ChB_pendiente.setOnClickListener(new View.OnClickListener() {
               //     @Override
               //     public void onClick(View v) {
               //         pendiente_IsSel = ChB_pendiente.isChecked();
               //     }
               // });
               // ChB_Cancelado.setOnClickListener(new View.OnClickListener() {
               //     @Override
               //     public void onClick(View v) {
               //         cancelado_IsSel = ChB_Cancelado.isChecked();
               //     }
               // });
               // ChB_Confirmado.setOnClickListener(new View.OnClickListener() {
               //     @Override
               //     public void onClick(View v) {
               //         confirmado_IsSel = ChB_Confirmado.isChecked();
               //     }
               // });
               // ChB_EnTransito.setOnClickListener(new View.OnClickListener() {
               //     @Override
               //     public void onClick(View v) {
               //         entransito_IsSel = ChB_EnTransito.isChecked();
               //     }
               // });
               // ChB_Finalizado.setOnClickListener(new View.OnClickListener() {
               //     @Override
               //     public void onClick(View v) {
               //         finalizado_IsSel = ChB_Finalizado.isChecked();
               //     }
               // });
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
                        ShowOrders();
                        SelectingStatus =false;
                        dialog.cancel();

                    }
                });
                Btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SelectingStatus = false;
                        dialog.cancel();

                    }
                });
            }
        }
        //Eventos de los componentes

        final View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SelectingStatus = true;
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
                  // ChB_pendiente.setOnClickListener(new View.OnClickListener() {
                  //     @Override
                  //     public void onClick(View v) {
                  //         pendiente_IsSel = ChB_pendiente.isChecked();
                  //     }
                  // });
                  // ChB_Cancelado.setOnClickListener(new View.OnClickListener() {
                  //     @Override
                  //     public void onClick(View v) {
                  //         cancelado_IsSel = ChB_Cancelado.isChecked();
                  //     }
                  // });
                  // ChB_Confirmado.setOnClickListener(new View.OnClickListener() {
                  //     @Override
                  //     public void onClick(View v) {
                  //         confirmado_IsSel = ChB_Confirmado.isChecked();
                  //     }
                  // });
                  // ChB_EnTransito.setOnClickListener(new View.OnClickListener() {
                  //     @Override
                  //     public void onClick(View v) {
                  //         entransito_IsSel = ChB_EnTransito.isChecked();
                  //     }
                  // });
                  // ChB_Finalizado.setOnClickListener(new View.OnClickListener() {
                  //     @Override
                  //     public void onClick(View v) {
                  //         finalizado_IsSel = ChB_Finalizado.isChecked();
                  //     }
                  // });
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
                            ShowOrders();
                            SelectingStatus =false;
                            dialog.cancel();

                        }
                    });
                    Btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SelectingStatus = false;
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
                ShowOrders();
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
                ShowOrders();

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
                ShowOrders();

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
                ShowOrders();


            }
        });

        spinner_clients.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        Object item = parent.getItemAtPosition(pos);
                        ShowOrders();

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
        add_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(orders.this, add_order.class);
                startActivityForResult(i, 3);
            }
        });
    }

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            if (month < 10 && dayOfMonth < 10) {
                btn_oldDate.setText("0" + dayOfMonth + "-0" + (month + 1) + "-" + year);
            } else if (dayOfMonth < 10) {
                btn_oldDate.setText("0" + dayOfMonth + "-" + (month + 1) + "-" + year);
            } else if (month < 10) {
                btn_oldDate.setText(dayOfMonth + "-0" + (month + 1) + "-" + year);
            } else {
                btn_oldDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
            }
            ShowOrders();

        }
    };
    DatePickerDialog.OnDateSetListener listener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            if (month < 10 && dayOfMonth < 10) {
                btn_newDate.setText("0" + dayOfMonth + "-0" + (month + 1) + "-" + year);
            } else if (dayOfMonth < 10) {
                btn_newDate.setText("0" + dayOfMonth + "-" + (month + 1) + "-" + year);
            } else if (month < 10) {
                btn_newDate.setText(dayOfMonth + "-0" + (month + 1) + "-" + year);
            } else {
                btn_newDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
            }
            ShowOrders();
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

    public String StatusDescription(int id_status) {
        String aux = null;
        if (id_status == 0) {
            aux = "Pendiente";
        } else if (id_status == 1) {
            aux = "Cancelado";
        } else if (id_status == 2) {
            aux = "Confirmado";
        } else if (id_status == 3) {
            aux = "En transito";
        } else if (id_status == 4) {
            aux = "Finalizado";
        } else {
            aux = "No existe";
        }
        return aux;
    }

    public boolean BuyDateIsonLimits(boolean ChB_oldateIsChecked, boolean ChB_newdateIsChecked, String Buydate, String oldDate, String newDate) {
        boolean aux = false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date Date_buyDate = new Date();
        try {
            Date_buyDate = sdf.parse(Buydate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date Date_olddate = new Date();
        try {
            Date_olddate = sdf.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date Date_newdate = new Date();
        try {
            Date_newdate = sdf.parse(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (ChB_newdateIsChecked && ChB_oldateIsChecked) {
            if ((Date_buyDate.compareTo(Date_olddate) >= 0) && (Date_buyDate.compareTo(Date_newdate) <= 0)) {
                aux = true;
            }
        } else if ((ChB_newdateIsChecked == true) && (ChB_oldateIsChecked == false)) {
            if (Date_buyDate.compareTo(Date_newdate) <= 0) {
                aux = true;
            }
        } else if ((ChB_newdateIsChecked == false) && (ChB_oldateIsChecked == true)) {
            if (Date_buyDate.compareTo(Date_olddate) >= 0) {
                aux = true;
            }
        }
        return aux;
    }

    public void ShowOrders() {
        if (spinner_clients.getSelectedItem().toString().equals("TODOS")) {
            if (pendiente_IsSel && cancelado_IsSel && confirmado_IsSel && entransito_IsSel && finalizado_IsSel) {
                OrdersOnRV = inventory.getAllOrders();
                Order_ordersbydate();
                OrdersAdapter = new OrdersAdapter(OrdersOnRV);
                recyclerView.setAdapter(OrdersAdapter);
            } else {
                OrdersOnRV = inventory.getAllordersWithSpecificsStatusOrders(pendiente_IsSel, cancelado_IsSel, confirmado_IsSel, entransito_IsSel, finalizado_IsSel);
                Order_ordersbydate();
                OrdersAdapter = new OrdersAdapter(OrdersOnRV);
                recyclerView.setAdapter(OrdersAdapter);

            }
        } else {
            int aux;
            for (Customers customer : clientsfounded) {
                if (spinner_clients.getSelectedItem().toString().equals((customer.getLast_name() + " " + customer.getFirst_name()))) {
                    aux = customer.getId();
                    OrdersOnRV = inventory.getSpecificiOrdersWithCustomerandStatus(pendiente_IsSel, cancelado_IsSel, confirmado_IsSel, entransito_IsSel, finalizado_IsSel, aux);
                    //OrdersAdapter = new OrdersAdapter(inventory.getSpecificiOrdersWithCustomerandStatus(pendiente_IsSel, cancelado_IsSel, confirmado_IsSel, entransito_IsSel, finalizado_IsSel, aux));
                    Order_ordersbydate();
                    OrdersAdapter = new OrdersAdapter(OrdersOnRV);
                    recyclerView.setAdapter(OrdersAdapter);
                }
            }


        }
        if (ChB_oldDate.isChecked() || ChB_newDate.isChecked()) {
            List<Order> AuxList = new ArrayList<Order>();
            for (Order order : OrdersOnRV) {
                if (BuyDateIsonLimits(ChB_oldDate.isChecked(), ChB_newDate.isChecked(), order.getDate(), String.valueOf(btn_oldDate.getText()), String.valueOf(btn_newDate.getText()))) {
                    AuxList.add(order);
                }
            }
            OrdersOnRV = AuxList;
            Order_ordersbydate();
            OrdersAdapter = new OrdersAdapter(OrdersOnRV);
            recyclerView.setAdapter(OrdersAdapter);
        }
    }

    public void Order_ordersbydate() {
        for (int i = 0; i < OrdersOnRV.size() - 1; i++) {
            for (int j = 0; j < OrdersOnRV.size() - 1; j++) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date Date1 = new Date();
                try {
                    Date1 = sdf.parse(String.valueOf(OrdersOnRV.get(j).getDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date Date2 = new Date();
                try {
                    Date2 = sdf.parse(String.valueOf(OrdersOnRV.get(j + 1).getDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (Date1.compareTo(Date2) < 0) {
                    Order tmp = OrdersOnRV.get(j + 1);
                    OrdersOnRV.set(j + 1, OrdersOnRV.get(j));
                    OrdersOnRV.set(j, tmp);
                }

            }
        }
    }

    public String getTodayCalendar() {
        String aux = "";
        final Calendar c = Calendar.getInstance();
        oldDate_DayX = c.get(Calendar.DAY_OF_MONTH);
        oldDate_MonthX = c.get(Calendar.MONTH);
        oldDate_YearX = c.get(Calendar.YEAR);
        if (oldDate_MonthX < 10 && oldDate_DayX < 10) {
            aux = "0" + oldDate_DayX + "-0" + (oldDate_MonthX + 1) + "-" + oldDate_YearX;
        } else if (oldDate_DayX < 10) {
            aux = "0" + oldDate_DayX + "-" + (oldDate_MonthX + 1) + "-" + oldDate_YearX;
        } else if (oldDate_MonthX < 10) {
            aux = oldDate_DayX + "-0" + (oldDate_MonthX + 1) + "-" + oldDate_YearX;
        } else {
            aux = oldDate_DayX + "-" + (oldDate_MonthX + 1) + "-" + oldDate_YearX;
        }


        return aux;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            ShowOrders();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("pendiente_IsSel", pendiente_IsSel);
        outState.putBoolean("cancelado_IsSel", cancelado_IsSel);
        outState.putBoolean("confirmado_IsSel", confirmado_IsSel);
        outState.putBoolean("entransito_IsSel", entransito_IsSel);
        outState.putBoolean("finalizado_IsSel", finalizado_IsSel);
        outState.putBoolean("olddate_IsSel", ChB_oldDate.isChecked());
        outState.putBoolean("newdate_IsSel", ChB_newDate.isChecked());
        outState.putString("Old_date_String", btn_oldDate.getText().toString());
        outState.putString("New_date_String", btn_newDate.getText().toString());
        outState.putBoolean("SelectingStatus",SelectingStatus);

    }
}
