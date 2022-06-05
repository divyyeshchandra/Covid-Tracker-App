package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView totalConfirm,totalActive,totalRecovered,totalDeath,totalTests;
    private TextView todayDeath,todayConfirm,todayRecovered,Date;
    private List<countryData> list;
    private PieChart piechart;
    String country="India";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=new ArrayList<>();
        if(getIntent().getStringExtra("country") !=null)
        {
            country=getIntent().getStringExtra("country");
        }
        init();
        TextView cname=findViewById(R.id.cname);
        cname.setText(country);
        cname.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this,CountryActivity.class)));
        ApiUtilities.getApiInterface().getCountryData()
                .enqueue(new Callback<List<countryData>>() {
                    @Override
                    public void onResponse(Call<List<countryData>> call, Response<List<countryData>> response) {
                        list.addAll(response.body());
                        for(int i=0;i<list.size();i++)
                        {
                            if(list.get(i).getCountry().equals(country)){
                                int confirm=Integer.parseInt(list.get(i).getCases());
                                int active=Integer.parseInt(list.get(i).getActive());
                                int recovered=Integer.parseInt(list.get(i).getRecovered());
                                int death=Integer.parseInt(list.get(i).getDeaths());
                                int tests=Integer.parseInt(list.get(i).getTests());
                                int todayrecovered=Integer.parseInt(list.get(i).getTodayRecovered());
                                int todaydeath=Integer.parseInt(list.get(i).getTodayDeaths());
                                int todayconfirm=Integer.parseInt(list.get(i).getTodayCases());


                                totalActive.setText(NumberFormat.getInstance().format(active));
                                totalConfirm.setText(NumberFormat.getInstance().format(confirm));
                                totalDeath.setText(NumberFormat.getInstance().format(death));
                                totalRecovered.setText(NumberFormat.getInstance().format(recovered));
                                todayConfirm.setText(NumberFormat.getInstance().format(todayconfirm));
                                todayRecovered.setText(NumberFormat.getInstance().format(todayrecovered));
                                todayDeath.setText(NumberFormat.getInstance().format(todaydeath));
                                totalTests.setText(NumberFormat.getInstance().format(tests));
                                setText(list.get(i).getUpdated());


                                piechart.addPieSlice(new PieModel("Confirm",confirm,getResources().getColor(R.color.yellow)));
                                piechart.addPieSlice(new PieModel("Active",active,getResources().getColor(R.color.blue_pie)));
                                piechart.addPieSlice(new PieModel("Death",death,getResources().getColor(R.color.red_pie)));
                                piechart.addPieSlice(new PieModel("Recovered",recovered,getResources().getColor(R.color.green_pie)));
                                piechart.startAnimation();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<countryData>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setText(String updated) {
        DateFormat dateFormat=new SimpleDateFormat("MMM dd,yyyy");
        long millisecond=Long.parseLong(updated);
        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);
        Date.setText("Updated at"+" "+ dateFormat.format(calendar.getTime()));
    }

    private void init()
    {
        totalConfirm=findViewById(R.id.totalConfirm);
        todayConfirm=findViewById(R.id.todayConfirm);
        totalActive=findViewById(R.id.totalActive);
        totalRecovered=findViewById(R.id.totalRecovered);
        todayRecovered=findViewById(R.id.todayRecovered);
        totalDeath=findViewById(R.id.totalDeath);
        todayDeath=findViewById(R.id.todayDeath);
        totalTests=findViewById(R.id.totalTests);
        Date=findViewById(R.id.date);
        piechart=findViewById(R.id.piechart);
    }

}