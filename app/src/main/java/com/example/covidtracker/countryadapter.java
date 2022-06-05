package com.example.covidtracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class countryadapter extends RecyclerView.Adapter<countryadapter.countryViewHolder>{

    private Context context;
    private List<countryData> list;

    public countryadapter(Context context, List<countryData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public countryViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.country_item_layout,parent,false);
        return new countryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull countryadapter.countryViewHolder holder, int position) {
        countryData data=list.get(position);
        holder.countrycases.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getCases())));
        holder.countryname.setText(data.getCountry());
        holder.sno.setText(String.valueOf(position+1));
        Map<String,String> img=data.getCountryInfo();
        Glide.with(context).load(img.get("flag")).into(holder.countryimage);
        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(context,MainActivity.class);
            intent.putExtra("country",data.getCountry());
            context.startActivity(intent);
        });
    }

    public void filterData(List<countryData> filterList)
    {
        list=filterList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class countryViewHolder extends RecyclerView.ViewHolder {

        private TextView sno,countryname,countrycases;
        private ImageView countryimage;

        public countryViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            sno=itemView.findViewById(R.id.sno);
            countryname=itemView.findViewById(R.id.countryname);
            countrycases=itemView.findViewById(R.id.countrycases);
            countryimage=itemView.findViewById(R.id.countryimage);
        }
    }
}
