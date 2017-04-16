package com.fiuady.android.compustore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.fiuady.android.compustore.db.Customers;
import com.fiuady.android.compustore.db.Inventory;

import java.util.ArrayList;
import java.util.List;

public class add_order extends AppCompatActivity {
    private ImageButton ImBtn_backactivity, ImBtn_add_assembly;
    private Spinner Spnr_clients;
    private RecyclerView RV_Asemblies;
    private Button btn_confirm,btn_cancel;
    private Inventory inventory;
    private List<Customers> clients= new ArrayList<Customers>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        //declarar componentes gráficos
        ImBtn_backactivity= (ImageButton)findViewById(R.id.BackActivy_add_order);
        ImBtn_add_assembly= (ImageButton)findViewById(R.id.imageButtonAddAssambly_add_order);
        Spnr_clients = (Spinner)findViewById(R.id.spinner_clients_add_order);
        RV_Asemblies = (RecyclerView)findViewById(R.id.recyclerview_assamblys_add_order);
        btn_confirm = (Button)findViewById(R.id.btn_confirm_add_order);
        btn_cancel= (Button)findViewById(R.id.btn_cancel_add_order);

        //declarar componentesaux
        inventory= new Inventory(getApplicationContext());


        //eventos de los componentes
        ImBtn_backactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
