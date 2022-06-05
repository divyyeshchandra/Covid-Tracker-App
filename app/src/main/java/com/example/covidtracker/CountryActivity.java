package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<countryData>list;
    private ProgressDialog dialog;
    private EditText searchBar;
    private countryadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        recyclerView=findViewById(R.id.countries);
        list=new ArrayList<>();
        searchBar=findViewById(R.id.searchBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter= new countryadapter(this,list);
        recyclerView.setAdapter(adapter);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();

        ApiUtilities.getApiInterface().getCountryData().enqueue(new Callback<List<countryData>>() {
            @Override
            public void onResponse(Call<List<countryData>> call, Response<List<countryData>> response) {
                list.addAll(response.body());
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<countryData>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(CountryActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String Text) {
        List<countryData> filterList=new ArrayList<>();
        for(countryData items: list)
        {
            if(items.getCountry().toLowerCase().contains(Text.toLowerCase())){
                filterList.add(items);
            }
        }
        adapter.filterData(filterList);
    }
}