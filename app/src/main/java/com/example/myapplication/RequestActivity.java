package com.example.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Arrays;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RequestActivity extends Activity {
    LinearLayout scroll;
    Order order;
    ImageView tester;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_request);
        Navigation.setupNavigation(this);
        scroll =  findViewById(R.id.scrollwheel);
        //TODO:THESE LINES ARE FOR TESTING
        for(int i=0; i<6; i++)
        {
            insertorder("mc", 10);
            insertorder("koi", 3);
        }
        //TODO:THEY CREATE A BUNCH OF ORDERFRAMES IN THE SCROLLVIEW

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    //TODO:STRINGS AND INTEGERS ARE FOR TESTING BECAUSE ORDER DOESN'T EXIST
    private void insertorder(String store, int points)//Inserts order widgets into scrollview
    {
        View ord = getLayoutInflater().inflate(R.layout.orderframe, null);//Creates an orderframe view
        switch(store)
        {
            case "mc"://strings are just for testing
                order = new Order("McDonalds", "Pentagon", "L1",
                        Arrays.asList("Fish Burger","French Fries"), Arrays.asList(3,2), Arrays.asList(3.5,2.2),R.drawable.mc_donald,2.6);
                storecard.mcd(ord, this, points);//static method of stordercard, formats orderframe into McDonald's widget(example)
                break;
            case "koi":
                order = new Order("Koi", "Changi City Point", "#B1-18",
                        Arrays.asList("Jumbo Milk Tea"), Arrays.asList(1), Arrays.asList(5.8),R.drawable.jumbomilktea_removebg_preview,5.1);
                storecard.koi(ord, this, points);//static method of stordercard, formats orderframe into Koi widget
                break;

        }
        ord.setTag(order);
        ord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TEST",  store);
                Order selorder = (Order)v.getTag();
                Intent intent = new Intent(RequestActivity.this, PickupActivity.class);
                intent.putExtra("Selected Order", selorder);
                startActivity(intent);
            }
        });
        scroll.addView(ord);
    }
}