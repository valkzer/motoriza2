package com.example.valkzer.motorizados.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valkzer.motorizados.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();




        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        getInfoUser();
    }


    private void getInfoUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = user;
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            TextView userEmail = ((TextView)(navigationView.getHeaderView(0).findViewById(R.id.txtUserEmail)));
            userEmail.setText(email);

            TextView userName = ((TextView)(navigationView.getHeaderView(0).findViewById(R.id.txtUserName)));
            if (name!= null){
                userName.setText(name);
            }
            else{
                userName.setText(R.string.userName);
            }


            ImageView userPicture = ((ImageView)(navigationView.getHeaderView(0).findViewById(R.id.imgPicture)));
            if (photoUrl != null){
                Picasso.with(this.getApplicationContext()).load(photoUrl).into(userPicture);
            }
            //Picasso.with(this.getApplicationContext()).load(photoUrl).fit().into(userPicture);


            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
    }
    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_Login) {
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
         if (id == R.id.nav_Delivery) {
            if(currentUser != null){
                Intent loginIntent = new Intent(this, DeliveryListActivity.class);
                startActivity(loginIntent);
            }
            else{
                Toast.makeText(this.getApplicationContext(),R.string.ErrorMessage,Toast.LENGTH_SHORT).show();
                invalidateOptionsMenu();
            }
        } else if (id == R.id.nav_Login) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }else if (id == R.id.nav_Customer) {

            if(currentUser != null){
            Intent customer  = new Intent(this, CreateCustomerActivity.class );
            startActivity(customer);
            }
            else{

                Toast.makeText(this.getApplicationContext(),R.string.ErrorMessage,Toast.LENGTH_SHORT).show();
                invalidateOptionsMenu();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
