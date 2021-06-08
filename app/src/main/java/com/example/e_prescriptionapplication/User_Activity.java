package com.example.e_prescriptionapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class User_Activity extends AppCompatActivity {

    Button mPharmcistBtn, mPhysicianBtn, mPatientBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mPharmcistBtn= findViewById(R.id.pharmacistBtn);
        mPhysicianBtn= findViewById(R.id.physicianBtn);
        mPatientBtn= findViewById(R.id.patientBtn);

        mPharmcistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PharmacistLogin.class));
            }
        });

        mPhysicianBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PhysicianLogin.class));
            }
        });

        mPatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}