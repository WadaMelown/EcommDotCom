package com.example.S6105692;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.S6105692.R;

public class BestSellers extends AppCompatActivity
{
    private ImageView tshirts, sportsTshirts, femaleDresses, sweaters;
    private ImageView glasses, hatsCaps, walletsBags, shoes;
    private ImageView headphones, Laptops, watches, mobilePhones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_sellers);

        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BestSellers.this, ActiveAdminCategory.class);
                intent.putExtra("category", "tshirts");
                startActivity(intent);

            }
        });

        sportsTshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BestSellers.this, ActiveAdminCategory.class);
                intent.putExtra("category", "sportsTshirts");
                startActivity(intent);
            }
        });

        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BestSellers.this, ActiveAdminCategory.class);
                intent.putExtra("category", "femaledresses");
                startActivity(intent);
            }
        });

        sweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BestSellers.this, ActiveAdminCategory.class);
                intent.putExtra("category", "sweaters");
                startActivity(intent);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BestSellers.this, ActiveAdminCategory.class);
                intent.putExtra("category", "glasses");
                startActivity(intent);
            }
        });

        hatsCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BestSellers.this, ActiveAdminCategory.class);
                intent.putExtra("category", "Hats Caps");
                startActivity(intent);
            }
        });


        walletsBags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BestSellers.this, ActiveAdminCategory.class);
                intent.putExtra("category", "Wallets Purses");
                startActivity(intent);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BestSellers.this, ActiveAdminCategory.class);
                intent.putExtra("category", "Shoes");
                startActivity(intent);
            }
        });

        headphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BestSellers.this, ActiveAdminCategory.class);
                intent.putExtra("category", "Headphones");
                startActivity(intent);
            }
        });

        Laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BestSellers.this, ActiveAdminCategory.class);
                intent.putExtra("category", "Laptops");
                startActivity(intent);
            }
        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BestSellers.this, ActiveAdminCategory.class);
                intent.putExtra("category", "Watches");
                startActivity(intent);
            }
        });

        mobilePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(BestSellers.this, ActiveAdminCategory.class);
                intent.putExtra("category", "MobilePhones");
                startActivity(intent);
            }
        });
    }
}
