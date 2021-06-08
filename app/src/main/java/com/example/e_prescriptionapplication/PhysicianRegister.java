package com.example.e_prescriptionapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PhysicianRegister extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mFullName,mRegistNo, mEmail,mPassword,mConfirm;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physician_register);
        mFullName   = findViewById(R.id.fullName);
        mRegistNo   = findViewById(R.id.RegistNo);
        mEmail      = findViewById(R.id.Email);
        mPassword   = findViewById(R.id.Password);
        mConfirm    = findViewById(R.id.Confirm);
        mRegisterBtn= findViewById(R.id.registerBtn);
        mLoginBtn   = findViewById(R.id.createText);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar= findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = mFullName.getText().toString().trim();
                String registNo = mRegistNo.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String confirm = mConfirm.getText().toString().trim();

                if(TextUtils.isEmpty(fullname)) {
                    mFullName.setError("Full Name is Required");
                }

                if(TextUtils.isEmpty(registNo)) {
                    mRegistNo.setError("Registration Number is Required");
                }

                if(registNo.length()!=6){
                    mRegistNo.setError("Registration Number must be 6 characters");
                    return;
                }

                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length()<8){
                    mPassword.setError("Password must NOT be less than 8 characters");
                    return;
                }

                if(TextUtils.isEmpty(confirm)){
                    mConfirm.setError("Password Confirmation is required.");
                    return;
                }
                if(!password.equals(confirm)){
                    mConfirm.setError("Password does NOT match");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(PhysicianRegister.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection(("Users")).document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("Full Name", fullname);
                            user.put("Registration Number", registNo);
                            user.put("Email", email);
                            documentReference.set(user).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                                Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(PhysicianRegister.this, "Error!!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PhysicianRegister.class));
            }
        });
    }
}