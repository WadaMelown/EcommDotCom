package com.example.S6105692;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.S6105692.Model.Location;
import com.example.S6105692.R;

public class ActiveAdminCategory extends AppCompatActivity
{
    private String CategoryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_admin_category);
        Intent referenceIntent = getIntent();
        Location retrievedLocation = (Location)referenceIntent.getSerializableExtra("reference");

        Log.d("LOCATIONS", "onCreate: " + retrievedLocation);
    }
}