package com.example.EcommerceShop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Category extends AppCompatActivity
{
    private ImageView tshirts, sportsTshirts, femailDresses, sweaters;
    private ImageView glasses, hatsCaps, walletsBags, shoes;
    private ImageView headphones, Laptops, watches, mobilePhones;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        tshirts.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Category.this, activity_category)
            }
        });
    }
}
