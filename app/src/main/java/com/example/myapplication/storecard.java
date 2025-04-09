package com.example.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.core.content.ContextCompat;

import java.util.List;


public class storecard//used to format orderframe for store
{
    View card;
    String name;
    String place;
    String address;
    Drawable icon;
    Context context;
    //Specifics
    List<String> Food;
    int quantity;
    List<Double> cost;
    storecard()
    {


    }
    //Constructor called in store format methods
    //View of layout orderframe, Store name, Store location, Store address, Drawable icon of store, context, points earned from order.
    storecard(View card, String name, String place, String address, Drawable icon, Context context, int points,List<String> food, List<Double> cost, int quantity)
    {
        this.card = card;
        this.name = name;
        this.place = place;
        this.address = address;
        this.icon = icon;
        this.quantity = quantity;
        this.cost = cost;
        this.Food = food;
        editcard(context);
        editdistance(context, points);

    }
    void editcard(Context context)//Used in constructor to format widget
    {
        ImageView cardicon = card.findViewById(R.id.storeimage);
        cardicon.setBackground(icon);
        TextView cardname = card.findViewById(R.id.storename);
        cardname.setText(context.getString(R.string.storename, name));
        TextView cardplace = card.findViewById(R.id.storeplace);
        cardplace.setText(context.getString(R.string.storeplace, place));
        TextView cardaddress = card.findViewById(R.id.storeaddress);
        cardaddress.setText(context.getString(R.string.storeaddress, address));
    }

    void editdistance(Context context, int p)
    {
        TextView distance = card.findViewById(R.id.distance);
        String d = "SHORT";
        switch(p)
        {
            case 5:
                d = "MEDIUM";
            case 10:
                d= "LONG";

        }
        distance.setText(context.getString(R.string.distance, d, p));

    }
    //Store format methods
    static void mcd(View card, Context context, int points, List<String> food, List<Double> cost, int qty)//McDonald's widget? Just an example obviously
    {
        new storecard(card, "McDonalds", "Pentagon", "idk", ContextCompat.getDrawable(context, R.drawable.mc_donald), context, points, food, cost, qty);
    }
    static void koi(View card, Context context, int points,List<String> food, List<Double> cost, int qty)//Koi widget
    {
        new storecard(card, "Koi", "Changi City Point", "#B1-18", ContextCompat.getDrawable(context, R.drawable.jumbomilktea_removebg_preview), context, points, food, cost, qty);
    }


}