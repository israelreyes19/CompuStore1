package com.fiuady.android.compustore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ReportesActivity extends AppCompatActivity {


    private ImageButton Stock;
    private ImageButton Confrim_order;
    private ImageButton Sales;
    private ImageButton backbuttonp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        backbuttonp = (ImageButton) findViewById(R.id.imageButtonBackreportes);
        Stock = (ImageButton)findViewById(R.id.imageButtonstockout);
        Confrim_order = (ImageButton)findViewById(R.id.imageButton_order_confirmed);
        Sales = (ImageButton)findViewById(R.id.imageButtonSales);

        Stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportesActivity.this, MissingStockActivity.class);
                startActivity(i);
            }
        });

        Sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportesActivity.this, SalesActivity.class);
                startActivity(i);
            }
        });

        Confrim_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportesActivity.this, ConfirmOrderActivity.class);
                startActivity(i);
            }
        });

        backbuttonp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
