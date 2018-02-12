package com.example.philsmile.exercise1.Adapters;

/**
 * Created by philsmile on 2/9/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by philsmile on 2/8/2018.
 */

import com.example.philsmile.exercise1.Classes.User;
import com.example.philsmile.exercise1.MapsActivity;
import com.example.philsmile.exercise1.R;

import java.util.List;

public class UsersRecyclerViewAdapter extends RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder> {

    private List<User> usersList;
    private Context context;

    public UsersRecyclerViewAdapter(ArrayList<User> cLst, Context ctx) {
        usersList = cLst;
        context = ctx;
    }

    @Override
    public UsersRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        UsersRecyclerViewAdapter.ViewHolder viewHolder = new UsersRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UsersRecyclerViewAdapter.ViewHolder holder, int position) {
        final User user = usersList.get(position);

        String street = user.getAddress().getStreet().toString();
        String suite = user.getAddress().getSuite().toString();
        String city = user.getAddress().getCity().toString();
        final String address = street+' '+suite+' '+city;
        final String lat = user.getAddress().getGeo().getLat().toString();
        final String lng = user.getAddress().getGeo().getLng().toString();

        holder.tvName.setText(user.getName());
        holder.tvMobile.setText(user.getPhone());
        holder.tvAddress.setText((CharSequence) address);

        holder.tvMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("tel:" +user.getPhone().toString()));
                context.startActivity(intent);
            }
        });

        holder.tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MapsActivity.class);
                intent.putExtra("address",address);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView tvName;
        public TextView tvMobile;
        public TextView tvAddress;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.name_txt);
            tvMobile = (TextView) view.findViewById(R.id.mobile_txt);
            tvAddress = (TextView) view.findViewById(R.id.address_txt);

        }

    }
}

