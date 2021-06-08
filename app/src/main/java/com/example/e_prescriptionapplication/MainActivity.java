package com.example.e_prescriptionapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout= findViewById(R.id.drawer_layout);
    }

    public void ClickMenu(View view){
        //Open Drawer
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        //Open Drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        //Close drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        //Close drawer layout
        //Check condition
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            //When drawer is open
            //Close Drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickProfile(View view){
        //Recreate activity
        rediectActivity(this,Profile_Activity.class);
    }

    public void ClickHome(View view){
        //Recreate activity
        recreate();
    }

    public void ClickView(View view){
        //Redirect activity to Extra Curricular Activity
        //rediectActivity(this,Extra_Curricular_Activity.class);
    }

    public void ClickChat(View view){
        //Redirect activity to Recreational Activity
        //rediectActivity(this,Recreational_Activity.class);
    }

    public void ClickFund(View view){
        //Redirect activity to Fund-Raiser Activity
        //rediectActivity(this,Fund_Raiser_Activity.class);
    }

    public static void rediectActivity(Activity activity, Class aClass) {
        //Intialize intent
        Intent intent= new Intent(activity, aClass);
        //Set Flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Start Activity
        activity.startActivity(intent);
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //close drawer
        MainActivity.closeDrawer(drawerLayout);
    }
}
