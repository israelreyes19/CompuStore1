package com.fiuady.android.compustore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fiuady.android.compustore.db.Inventory;
import com.fiuady.android.compustore.db.Products;
import com.fiuady.android.compustore.db.Sales;

import java.util.ArrayList;
import java.util.List;

public class SalesActivity extends AppCompatActivity {
    private Spinner Anio_ventas;
    private Inventory inventory;
    private TextView id;
    private TextView category;
    private  TextView description;
    private TextView price;
    private TextView quantity;
    private String item = "2016";
    private ImageView Imagen_meses;
    //private String aux_month;
    int i = 0;

   // private List<String> meses = new ArrayList<String>();
    public class Meses
    {
       private String meses;

        public Meses(String meses) {
            this.meses = meses;
            this.meses = meses;
        }

        public String getMeses() {
            return meses;
        }

        public void setMeses(String meses) {
            this.meses = meses;
        }
    }


    private class SalesHolder extends RecyclerView.ViewHolder
    {

        private TextView price;
        private TextView mes;
        private  TextView prueba;

        public SalesHolder(View itemView) {
            super(itemView);

            price = (TextView) itemView.findViewById(R.id.ventas_mes);
            mes =  (TextView) itemView.findViewById(R.id.mes_value);
            Imagen_meses = (ImageView) itemView.findViewById(R.id.estaciones);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition();
                    final Meses meses= adapter3.meses.get(pos);
                    String GetMonth= meses.getMeses();
                    String GetYear = item;
                    Intent intent = new Intent(SalesActivity.this, SalesOrdersActivity.class);
                    intent.putExtra(SalesOrdersActivity.Month_Value,GetMonth);
                    intent.putExtra(SalesOrdersActivity.Year_Value,GetYear);
                    startActivity(intent);
                }
            });

        }

        public void bindProducts(Meses meses)
        {
            //List<Sales> meses = Auxiliar_function(sales);

            mes.setText(meses.getMeses());
            String auxi = meses.getMeses();
            double j = 0;
            //i++;
            Imagen_meses.setImageResource(R.drawable.calendario);

           // List<Sales> sales = inventory.getSales();

            if (auxi.equals("Enero")) {
                //Imagen_meses.setImageResource(R.drawable.enero);

                for (Sales sales : inventory.getSales()
                        ) {



                    String aux_month = sales.getDate();
                    String aux3 = Character.toString(aux_month.charAt(3));
                    String aux4 = Character.toString(aux_month.charAt(4));
                    String aux6 =  Character.toString(aux_month.charAt(6));
                    String aux7 =  Character.toString(aux_month.charAt(7));
                    String aux8 =  Character.toString(aux_month.charAt(8));
                    String aux9 =  Character.toString(aux_month.charAt(9));
                    String anio = aux6 + aux7 + aux8 + aux9;

                    String aux5 = aux3 + aux4;

             if(item.equals(anio))
             {
                 if (aux5.equals("01")) {

                     double aux1 = sales.getTotal_cost();
                     double division = (aux1) / 100;
                     j = j + division;
                     // price.setText(String.format("%.2f", division));
                     // prueba.setText(aux5);
                 }

             }
             }
                if(j == 0)
                {
                    price.setText("0.00");
                }
                else
                {
                    price.setText(String.format("%.2f", j));
                }

            }

                else if (auxi.equals("Febrero")) {
                   // Imagen_meses.setImageResource(R.drawable.febrero);
                    for (Sales sales : inventory.getSales()
                            ) {
                        String aux_month = sales.getDate();
                        String aux3 = Character.toString(aux_month.charAt(3));
                        String aux4 = Character.toString(aux_month.charAt(4));
                        String aux6 =  Character.toString(aux_month.charAt(6));
                        String aux7 =  Character.toString(aux_month.charAt(7));
                        String aux8 =  Character.toString(aux_month.charAt(8));
                        String aux9 =  Character.toString(aux_month.charAt(9));
                        String anio = aux6 + aux7 + aux8 + aux9;

                        String aux5 = aux3 + aux4;

                        if(item.equals(anio))
                        {
                            if (aux5.equals("02")) {
                                double aux1 = sales.getTotal_cost();
                                double division = (aux1) / 100;
                                j = j + division;
                                // price.setText(String.format("%.2f", division));
                                // prueba.setText(aux5);
                            }

                        }
                    }
                    if(j == 0)
                    {
                        price.setText("0.00");
                    }
                    else
                    {
                        price.setText(String.format("%.2f", j));
                    }
                }

                   else if (auxi.equals("Marzo")) {
                       // Imagen_meses.setImageResource(R.drawable.marzo);
                        for (Sales sales : inventory.getSales()
                                ) {
                            String aux_month = sales.getDate();
                            String aux3 = Character.toString(aux_month.charAt(3));
                            String aux4 = Character.toString(aux_month.charAt(4));
                            String aux6 =  Character.toString(aux_month.charAt(6));
                            String aux7 =  Character.toString(aux_month.charAt(7));
                            String aux8 =  Character.toString(aux_month.charAt(8));
                            String aux9 =  Character.toString(aux_month.charAt(9));
                            String anio = aux6 + aux7 + aux8 + aux9;


                            String aux5 = aux3 + aux4;

                            if(item.equals(anio))
                            {
                                if (aux5.equals("03")) {
                                    double aux1 = sales.getTotal_cost();
                                    double division = (aux1) / 100;
                                    j = j + division;
                                    // price.setText(String.format("%.2f", division));
                                    // prueba.setText(aux5);
                                }

                            }
                        }

                        if(j == 0)
                        {
                            price.setText("0.00");
                        }
                        else
                        {
                            price.setText(String.format("%.2f", j));
                        }

                    }
                       else if (auxi.equals("Abril"))
                        {
                          //  Imagen_meses.setImageResource(R.drawable.abril);
                            for ( Sales sales : inventory.getSales()
                                    )
                            {
                                String aux_month = sales.getDate();
                                String aux3 = Character.toString(aux_month.charAt(3));
                                String aux4 = Character.toString(aux_month.charAt(4));
                                String aux6 =  Character.toString(aux_month.charAt(6));
                                String aux7 =  Character.toString(aux_month.charAt(7));
                                String aux8 =  Character.toString(aux_month.charAt(8));
                                String aux9 =  Character.toString(aux_month.charAt(9));
                                String anio = aux6 + aux7 + aux8 + aux9;


                                String aux5 = aux3 + aux4;
                               // prueba.setText(aux5);
                                if(item.equals(anio))
                                {
                                    if (aux5.equals("04")) {
                                        double aux1 = sales.getTotal_cost();
                                        double division = (aux1) / 100;
                                        j = j + division;
                                        // price.setText(String.format("%.2f", division));
                                        // prueba.setText(aux5);
                                    }

                                }
                            }

                            if(j == 0)
                            {
                                price.setText("0.00");
                            }
                            else
                            {
                                price.setText(String.format("%.2f", j));
                            }

            }

           else if (auxi.equals("Mayo")) {
               // Imagen_meses.setImageResource(R.drawable.mayo);
                for (Sales sales : inventory.getSales()
                        ) {
                    String aux_month = sales.getDate();
                    String aux3 = Character.toString(aux_month.charAt(3));
                    String aux4 = Character.toString(aux_month.charAt(4));
                    String aux6 =  Character.toString(aux_month.charAt(6));
                    String aux7 =  Character.toString(aux_month.charAt(7));
                    String aux8 =  Character.toString(aux_month.charAt(8));
                    String aux9 =  Character.toString(aux_month.charAt(9));
                    String anio = aux6 + aux7 + aux8 + aux9;

                    String aux5 = aux3 + aux4;
                   // prueba.setText(aux5);
                    if(item.equals(anio))
                    {
                        if (aux5.equals("05")) {
                            double aux1 = sales.getTotal_cost();
                            double division = (aux1) / 100;
                            j = j + division;
                            // price.setText(String.format("%.2f", division));
                            // prueba.setText(aux5);
                        }

                    }

                }
                if(j == 0)
                {
                    price.setText("0.00");
                }
                else
                {
                    price.setText(String.format("%.2f", j));
                }
            }

           else if (auxi.equals("Junio")) {
                //Imagen_meses.setImageResource(R.drawable.junio);
                for (Sales sales : inventory.getSales()
                        ) {
                    String aux_month = sales.getDate();
                    String aux3 = Character.toString(aux_month.charAt(3));
                    String aux4 = Character.toString(aux_month.charAt(4));
                    String aux6 =  Character.toString(aux_month.charAt(6));
                    String aux7 =  Character.toString(aux_month.charAt(7));
                    String aux8 =  Character.toString(aux_month.charAt(8));
                    String aux9 =  Character.toString(aux_month.charAt(9));
                    String anio = aux6 + aux7 + aux8 + aux9;


                    String aux5 = aux3 + aux4;
                   // prueba.setText(aux5);

                    if(item.equals(anio))
                    {
                        if (aux5.equals("06")) {
                            double aux1 = sales.getTotal_cost();
                            double division = (aux1) / 100;
                            j = j + division;
                            // price.setText(String.format("%.2f", division));
                            // prueba.setText(aux5);
                        }

                    }
                }
                if(j == 0)
                {
                    price.setText("0.00");
                }
                else
                {
                    price.setText(String.format("%.2f", j));
                }

            }

           else if (auxi.equals("Julio")) {
               // Imagen_meses.setImageResource(R.drawable.julio);

                for (Sales sales : inventory.getSales()
                        ) {
                    String aux_month = sales.getDate();
                    String aux3 = Character.toString(aux_month.charAt(3));
                    String aux4 = Character.toString(aux_month.charAt(4));
                    String aux6 =  Character.toString(aux_month.charAt(6));
                    String aux7 =  Character.toString(aux_month.charAt(7));
                    String aux8 =  Character.toString(aux_month.charAt(8));
                    String aux9 =  Character.toString(aux_month.charAt(9));
                    String anio = aux6 + aux7 + aux8 + aux9;


                    String aux5 = aux3 + aux4;

                    if(item.equals(anio))
                    {
                        if (aux5.equals("07")) {
                            double aux1 = sales.getTotal_cost();
                            double division = (aux1) / 100;
                            j = j + division;
                            // price.setText(String.format("%.2f", division));
                            // prueba.setText(aux5);
                        }

                    }

                }
                if(j == 0)
                {
                    price.setText("0.00");
                }
                else
                {
                    price.setText(String.format("%.2f", j));
                }
            }
          else  if (auxi.equals("Agosto"))
            {

               // Imagen_meses.setImageResource(img[7]);

               // Imagen_meses.setImageResource(R.drawable.agosto);

                for ( Sales sales : inventory.getSales()
                        )
                {
                    String aux_month = sales.getDate();
                    String aux3 = Character.toString(aux_month.charAt(3));
                    String aux4 = Character.toString(aux_month.charAt(4));
                    String aux6 =  Character.toString(aux_month.charAt(6));
                    String aux7 =  Character.toString(aux_month.charAt(7));
                    String aux8 =  Character.toString(aux_month.charAt(8));
                    String aux9 =  Character.toString(aux_month.charAt(9));
                    String anio = aux6 + aux7 + aux8 + aux9;

                    String aux5 = aux3 + aux4;

                    if(item.equals(anio))
                    {
                        if (aux5.equals("08")) {
                            double aux1 = sales.getTotal_cost();
                            double division = (aux1) / 100;
                            j = j + division;
                            // price.setText(String.format("%.2f", division));
                            // prueba.setText(aux5);
                        }

                    }

                }
                if(j == 0)
                {
                    price.setText("0.00");
                }
                else
                {
                    price.setText(String.format("%.2f", j));
                }

            }

          else  if (auxi.equals("Septiembre")) {

                //Imagen_meses.setImageResource(R.drawable.septiembre);

                for (Sales sales : inventory.getSales()
                        ) {
                    String aux_month = sales.getDate();
                    String aux3 = Character.toString(aux_month.charAt(3));
                    String aux4 = Character.toString(aux_month.charAt(4));
                    String aux6 =  Character.toString(aux_month.charAt(6));
                    String aux7 =  Character.toString(aux_month.charAt(7));
                    String aux8 =  Character.toString(aux_month.charAt(8));
                    String aux9 =  Character.toString(aux_month.charAt(9));
                    String anio = aux6 + aux7 + aux8 + aux9;


                    String aux5 = aux3 + aux4;

                    if(item.equals(anio))
                    {
                        if (aux5.equals("09")) {
                            double aux1 = sales.getTotal_cost();
                            double division = (aux1) / 100;
                            j = j + division;
                            // price.setText(String.format("%.2f", division));
                            // prueba.setText(aux5);
                        }

                    }

                }
                if(j == 0)
                {
                    price.setText("0.00");
                }
                else
                {
                    price.setText(String.format("%.2f", j));
                }

            }

           else if (auxi.equals("Octubre")) {

               // Imagen_meses.setImageResource(R.drawable.octubre);

                for (Sales sales : inventory.getSales()
                        ) {
                    String aux_month = sales.getDate();
                    String aux3 = Character.toString(aux_month.charAt(3));
                    String aux4 = Character.toString(aux_month.charAt(4));
                    String aux6 =  Character.toString(aux_month.charAt(6));
                    String aux7 =  Character.toString(aux_month.charAt(7));
                    String aux8 =  Character.toString(aux_month.charAt(8));
                    String aux9 =  Character.toString(aux_month.charAt(9));
                    String anio = aux6 + aux7 + aux8 + aux9;

                    String aux5 = aux3 + aux4;

                    if(item.equals(anio))
                    {
                        if (aux5.equals("10")) {
                            double aux1 = sales.getTotal_cost();
                            double division = (aux1) / 100;
                            j = j + division;
                            // price.setText(String.format("%.2f", division));
                            // prueba.setText(aux5);
                        }

                    }

                }
                if(j == 0)
                {
                    price.setText("0.00");
                }
                else
                {
                    price.setText(String.format("%.2f", j));
                }
            }

           else if (auxi.equals("Noviembre")) {

               // Imagen_meses.setImageResource(R.drawable.noviembre);

                for (Sales sales : inventory.getSales()
                        ) {
                    String aux_month = sales.getDate();
                    String aux3 = Character.toString(aux_month.charAt(3));
                    String aux4 = Character.toString(aux_month.charAt(4));
                    String aux6 =  Character.toString(aux_month.charAt(6));
                    String aux7 =  Character.toString(aux_month.charAt(7));
                    String aux8 =  Character.toString(aux_month.charAt(8));
                    String aux9 =  Character.toString(aux_month.charAt(9));
                    String anio = aux6 + aux7 + aux8 + aux9;


                    String aux5 = aux3 + aux4;

                    if(item.equals(anio))
                    {
                        if (aux5.equals("11")) {
                            double aux1 = sales.getTotal_cost();
                            double division = (aux1) / 100;
                            j = j + division;
                            // price.setText(String.format("%.2f", division));
                            // prueba.setText(aux5);
                        }

                    }

                }
                if(j == 0)
                {
                    price.setText("0.00");
                }
                else
                {
                    price.setText(String.format("%.2f", j));
                }

            }
          else  if (auxi.equals("Diciembre"))
            {

               // Imagen_meses.setImageResource(R.drawable.diciembre);

                for ( Sales sales : inventory.getSales()
                        )
                {
                    String aux_month = sales.getDate();
                    String aux3 = Character.toString(aux_month.charAt(3));
                    String aux4 = Character.toString(aux_month.charAt(4));
                    String aux6 =  Character.toString(aux_month.charAt(6));
                    String aux7 =  Character.toString(aux_month.charAt(7));
                    String aux8 =  Character.toString(aux_month.charAt(8));
                    String aux9 =  Character.toString(aux_month.charAt(9));
                    String anio = aux6 + aux7 + aux8 + aux9;

                    String aux5 = aux3 + aux4;

                    if(item.equals(anio))
                    {
                        if (aux5.equals("12")) {
                            double aux1 = sales.getTotal_cost();
                            double division = (aux1) / 100;
                            j = j + division;
                            // price.setText(String.format("%.2f", division));
                            // prueba.setText(aux5);
                        }

                    }
                }
                if(j == 0)
                {
                    price.setText("0.00");
                }
                else
                {
                    price.setText(String.format("%.2f", j));
                }
            }

        }
    }
    private class ProductsAdapter extends RecyclerView.Adapter<SalesActivity.SalesHolder>
    {

        private List<Meses> meses;
        public ProductsAdapter(List<Meses> meses){this.meses = meses;}

        @Override
        public SalesActivity.SalesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_list_item, parent, false);
            return new SalesActivity.SalesHolder(v);

        }

        @Override
        public void onBindViewHolder(SalesActivity.SalesHolder holder, int position) {
            holder.bindProducts(meses.get(position));
        }

        @Override
        public int getItemCount() {
            return meses.size();
        }
    }

    private RecyclerView recyclerView;
    private SalesActivity.ProductsAdapter adapter3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        final List<Meses> list = new ArrayList<Meses>();
        list.add(new Meses("Enero"));
        list.add(new Meses("Febrero"));
        list.add(new Meses("Marzo"));
        list.add(new Meses("Abril"));
        list.add(new Meses("Mayo"));
        list.add(new Meses("Junio"));
        list.add(new Meses("Julio"));
        list.add(new Meses("Agosto"));
        list.add(new Meses("Septiembre"));
        list.add(new Meses("Octubre"));
        list.add(new Meses("Noviembre"));
        list.add(new Meses("Diciembre"));
        Anio_ventas =  (Spinner)findViewById(R.id.spinner_ventas);

        inventory = new Inventory(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.Sales_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //adapter3 = new ProductsAdapter(inventory.getSales());
        adapter3 = new ProductsAdapter(list);
        recyclerView.setAdapter(adapter3);


        ArrayAdapter<String > adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        Anio_ventas.setAdapter((adapter));

        adapter.add("2016");
        adapter.add("2017");
        adapter.add("2018");
        adapter.add("2019");
        adapter.add("2020");
        adapter.add("2021");
        adapter.add("2022");
        adapter.add("2023");
        adapter.add("2024");
        adapter.add("2025");




        Anio_ventas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                item = parent.getItemAtPosition(position).toString();
                adapter3 = new ProductsAdapter(list);
                recyclerView.setAdapter(adapter3);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

}
