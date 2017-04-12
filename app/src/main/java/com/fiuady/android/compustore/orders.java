package com.fiuady.android.compustore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class orders extends AppCompatActivity {
    private ImageButton add_order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        add_order = (ImageButton)findViewById(R.id.imageButtonAddOrder);


      // add_order.setOnClickListener(new View.OnClickListener() {
      //     @Override
      //     public void onClick(View v) {
      //
      //     }
      // });
    }
}
