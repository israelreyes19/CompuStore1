package com.fiuady.android.compustore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class AddAssemblyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assembly);
        if(AssembliesActivity.edit)
        {
            Toast.makeText(getApplicationContext(), "Editando", Toast.LENGTH_SHORT).show();
        }
        else if(!AssembliesActivity.edit)
        {
            Toast.makeText(getApplicationContext(), "Agregando", Toast.LENGTH_SHORT).show();

        }
    }
}
