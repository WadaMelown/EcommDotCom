package com.example.S6105692;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity
{
    private Button LogoutButton;
    private Button SearchListing;
    private Button CreateListing;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        Log.d("Current User", "Current User: " +  mAuth.getCurrentUser().getUid());
        DocumentReference docRef = db.collection("users").document((mAuth.getCurrentUser().getUid()));

        docRef.collection("Locations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot document: task.getResult()) {
                        String location = document.get("Location").toString();
                        Log.d("DOCUMENTO", "User locations: " +  location);
                    }
                }
            }
        });

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if(document.exists()) {
                    Log.d("DOCUMENTO", document.getData().toString());
                }
            }
        });
//        db.collection(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()) {
//                    for(QueryDocumentSnapshot document: task.getResult()) {
//                        String number = document.get("phone_number").toString();
//                        Log.d("tesewfewfe", number);
//                    }
//                }
//            }
//        });

        LogoutButton    = (Button) findViewById(R.id.logout_button);
        SearchListing   =   (Button) findViewById(R.id.search_listing_button);
        CreateListing   =   (Button) findViewById(R.id.create_listing_button);



        LogoutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                mAuth.signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        SearchListing.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        CreateListing.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(HomeActivity.this, CreateListingActivity.class);
                startActivity(intent);
            }
        });
    }

}
