package com.fiuady.android.compustore;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class orders extends AppCompatActivity {

    private class OrdersHolder extends RecyclerView.ViewHolder{
        private ImageView image_status;
        private TextView id_order,id_status,client_name,date_name;
        public OrdersHolder(final View itemView) {
            super(itemView);
            image_status = (ImageView)itemView.findViewById(R.id.Image_View_Status);
            id_order=(TextView)itemView.findViewById(R.id.Txt_Id_order);
            id_status=(TextView)itemView.findViewById(R.id.Txt_Id_status);
            client_name= (TextView)itemView.findViewById(R.id.Txt_client_order);
            date_name = (TextView)itemView.findViewById(R.id.Txt_date_order);
        }
        public void bindOrders(Order order) {
            if (order.getStatus_id()== 0){image_status.setImageResource(R.drawable.pending);id_status.setText("Pendiente");}
            else if (order.getStatus_id()== 1){image_status.setImageResource(R.drawable.cancel);id_status.setText("Cancelado");}
            else if (order.getStatus_id()== 2){image_status.setImageResource(R.drawable.confirmed);id_status.setText("Confirmado");}
            else if (order.getStatus_id()== 3){image_status.setImageResource(R.drawable.ontransit);id_status.setText("En transito");}
            else {image_status.setImageResource(R.drawable.finished);id_status.setText("Finalizado");}

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

    private class OrdersAdapter extends RecyclerView.Adapter<OrdersHolder>{
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
    private RecyclerView recyclerView;
    private OrdersAdapter OrdersAdapter;
    private Spinner spinerstatus;
    private Spinner spinner_clients;
    private ImageButton add_order;
    private Button btn_oldDate, btn_newDate;
    private CheckBox ChB_oldDate,ChB_newDate;
    List<Customers> clientsfounded = new ArrayList<Customers>();
    private boolean pendiente_IsSel=false,cancelado_IsSel=false,confirmado_IsSel=false,entransito_IsSel=false,finalizado_IsSel =false;
    private int oldDate_DayX,oldDate_MonthX,oldDate_YearX, i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        // Definición de componenetes gráficos
        add_order = (ImageButton)findViewById(R.id.imageButtonAddOrder);
        spinerstatus = (Spinner)findViewById(R.id.SpinerStatus);
        spinner_clients = (Spinner)findViewById(R.id.spinner_clients);
        btn_oldDate = (Button)findViewById(R.id.Btn_Selectolddate);
        btn_newDate = (Button)findViewById(R.id.Btn_SelectNewdate);
        ChB_oldDate = (CheckBox)findViewById(R.id.ChB_oldDate);
        ChB_newDate = (CheckBox)findViewById(R.id.ChB_newDate);
        recyclerView = (RecyclerView) findViewById(R.id.orders_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Componentes Auxiliares
        Inventory inventory = new Inventory(getApplicationContext());
        clientsfounded= inventory.getAllCustomers();
        List<String> spinnerArray =  new ArrayList<String>();

        //Llenar los componentes
        spinnerArray.add("TODOS");
        for (Customers customer: clientsfounded){
            spinnerArray.add(String.valueOf(customer.getLast_name())+" "+String.valueOf(customer.getFirst_name()));}
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_clients.setAdapter(adapter);


        if (spinner_clients.getSelectedItem().toString().equals("TODOS")){
            OrdersAdapter = new OrdersAdapter(inventory.getAllOrders());
            recyclerView.setAdapter(OrdersAdapter);}
        else{
            int aux;
            for (Customers customer: clientsfounded){
                if (spinner_clients.getSelectedItem().toString().equals((customer.getLast_name()+" "+customer.getFirst_name()))){
                    aux = customer.getId();
                    OrdersAdapter = new OrdersAdapter(inventory.getSpecificClientOrders(aux));
                    recyclerView.setAdapter(OrdersAdapter);
                }
            }


        }


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
                    final Button Btn_acept = (Button)dialog.findViewById(R.id.btn_aceptar);
                    final Button Btn_cancel= (Button)dialog.findViewById(R.id.btn_cancelar);
                    ChB_pendiente.setChecked ( pendiente_IsSel );
                    ChB_Cancelado.setChecked ( cancelado_IsSel );
                    ChB_Confirmado.setChecked( confirmado_IsSel);
                    ChB_EnTransito.setChecked( entransito_IsSel);
                    ChB_Finalizado.setChecked( finalizado_IsSel);
                    Btn_acept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pendiente_IsSel =  ChB_pendiente.isChecked();
                            cancelado_IsSel =  ChB_Cancelado.isChecked();
                            confirmado_IsSel = ChB_Confirmado.isChecked();
                            entransito_IsSel = ChB_EnTransito.isChecked();
                            finalizado_IsSel = ChB_Finalizado.isChecked();
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
            final Calendar c= Calendar.getInstance();
                oldDate_DayX = c.get(Calendar.DAY_OF_MONTH);
                oldDate_MonthX= c.get(Calendar.MONTH);
                oldDate_YearX =c.get(Calendar.YEAR);

                new DatePickerDialog(orders.this,listener,oldDate_YearX,oldDate_MonthX,oldDate_DayX).show();
            }
        });
        btn_newDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c= Calendar.getInstance();
                oldDate_DayX = c.get(Calendar.DAY_OF_MONTH);
                oldDate_MonthX= c.get(Calendar.MONTH);
                oldDate_YearX =c.get(Calendar.YEAR);

                new DatePickerDialog(orders.this,listener1,oldDate_YearX,oldDate_MonthX,oldDate_DayX).show();

                int aux;
                for (Customers customer: clientsfounded){
                    if (spinner_clients.getSelectedItem().toString().equals((customer.getLast_name()+" "+customer.getFirst_name()))){
                        aux = customer.getId();
                        Inventory inventory2 = new Inventory(getApplicationContext());
                        OrdersAdapter = new OrdersAdapter(inventory2.getSpecificClientOrders(aux));
                        recyclerView.setAdapter(OrdersAdapter);
                    }
                }
            }
        });

        ChB_newDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ChB_newDate.isChecked()){btn_newDate.setEnabled(true);}
                else {btn_newDate.setEnabled(false);}
            }
        });
        ChB_oldDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ChB_oldDate.isChecked()){btn_oldDate.setEnabled(true);}
                else {btn_oldDate.setEnabled(false);}



            }
        });

    }

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            if (month <10 && dayOfMonth<10){btn_oldDate.setText("0"+dayOfMonth+"-0"+ month+"-"+year);}
            else if (dayOfMonth<10){btn_oldDate.setText("0"+dayOfMonth+"-"+ month+"-"+year);}
            else if (month <10){btn_oldDate.setText(dayOfMonth+"-0"+ month+"-"+year);}
            else {btn_oldDate.setText(dayOfMonth+"-"+ month+"-"+year);}

        }
    };
    DatePickerDialog.OnDateSetListener listener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            if (month <10 && dayOfMonth<10){btn_newDate.setText("0"+dayOfMonth+"-0"+ month+"-"+year);}
            else if (dayOfMonth<10){btn_newDate.setText("0"+dayOfMonth+"-"+ month+"-"+year);}
            else if (month <10){btn_newDate.setText(dayOfMonth+"-0"+ month+"-"+year);}
            else {btn_newDate.setText(dayOfMonth+"-"+ month+"-"+year);}

        }
    };


}
