package com.example.e_prescriptionapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile_Activity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    TextView mFullName, mRegistNo, mEmail;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        drawerLayout = findViewById(R.id.drawer_layout);

        mFullName = findViewById(R.id.fullName);
        mRegistNo = findViewById(R.id.RegistNo);
        mEmail = findViewById(R.id.Email);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                mFullName.setText(documentSnapshot.getString("Full Name"));
                mEmail.setText(documentSnapshot.getString("Email"));
                mRegistNo.setText(documentSnapshot.getString("Registration Number"));
            }
        });
    }

    public void ClickMenu(View view) {
        //Open Drawer
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        //Open Drawer layout
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        //Close drawer
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickProfile(View view) {
        //Recreate activity
        recreate();
    }

    public void ClickHome(View view) {
        //Recreate activity
        MainActivity.rediectActivity(this, MainActivity.class);
    }

    public void ClickView(View view) {
        //Redirect activity to Extra Curricular Activity
        //redirectActivity(this,Extra_Curricular_Activity.class);
    }

    public void ClickChat(View view) {
        //Redirect activity to Recreational Activity
        //redirectActivity(this,Recreational_Activity.class);
    }

    public void ClickFund(View view) {
        //Redirect activity to Fund-Raiser Activity
        //redirectActivity(this,Fund_Raiser_Activity.class);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}
