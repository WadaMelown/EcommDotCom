package com.example.S6105692;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.S6105692.Model.Location;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;




public class CreateListingActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private EditText InputItem, InputDescription, InputAmount, InputAddress, InputNumber;
    private Button createLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        InputItem = findViewById(R.id.item_name);
        InputDescription =  findViewById(R.id.item_description);
        InputAmount =  findViewById(R.id.item_amount);
        InputAddress =  findViewById(R.id.address_create_listing);
        InputNumber =  findViewById(R.id.create_listing_phone_number);

        createLocation =  findViewById(R.id.create_listing_button);
        mAuth = FirebaseAuth.getInstance();

        createLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Location location = new Location(InputItem.getText().toString(), InputDescription.getText().toString(), InputAmount.getText().toString(), InputAddress.getText().toString(), InputNumber.getText().toString());
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("users").document((mAuth.getCurrentUser().getUid()));
                docRef.collection("Locations").add(location).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task)
                    {
                        // Do Whatever here
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        // add failure
                    }
                });
            }
        });

    }


}
