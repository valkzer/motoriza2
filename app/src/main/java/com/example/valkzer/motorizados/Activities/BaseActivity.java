package com.example.valkzer.motorizados.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.transition.Visibility;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;

import com.squareup.picasso.Picasso;
import com.example.valkzer.motorizados.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    private FirebaseUser                   currentUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth                   mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = getAuthStateListener();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    protected void setUpNavigation()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        navigationView.requestLayout();
    }

    protected FirebaseAuth.AuthStateListener getAuthStateListener()
    {
        return new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user           = firebaseAuth.getCurrentUser();
                MenuItem     menuItemLogin  = navigationView.getMenu().findItem(R.id.nav_Login);
                MenuItem     menuDeliveries = navigationView.getMenu().findItem(R.id.nav_Customer);
                MenuItem     menuCustomers  = navigationView.getMenu().findItem(R.id.nav_Delivery);
                currentUser = user;
                if (user != null) {
                    String user_name      = user.getDisplayName();
                    String user_email     = user.getEmail();
                    Uri    user_photo_uri = user.getPhotoUrl();

                    TextView  userEmail   = ((TextView) (navigationView.getHeaderView(0).findViewById(R.id.txtUserEmail)));
                    TextView  txtUserName = ((TextView) (navigationView.getHeaderView(0).findViewById(R.id.txtUserName)));
                    ImageView userPicture = ((ImageView) (navigationView.getHeaderView(0).findViewById(R.id.imgPicture)));


                    if (user_name != null) {
                        txtUserName.setText(user_name);
                    }

                    if (user_email != null) {
                        userEmail.setText(user_email);
                    }

                    if (user_photo_uri != null) {
                        Picasso.with(getApplicationContext()).load(user_photo_uri).into(userPicture);
                    }

                    menuItemLogin.setVisible(false);
                    menuDeliveries.setVisible(true);
                    menuCustomers.setVisible(true);

                } else {
                    menuItemLogin.setVisible(true);
                    menuDeliveries.setVisible(false);
                    menuCustomers.setVisible(false);
                }
            }
        };
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
            if (currentUser != null) {
                Intent loginIntent = new Intent(this, DeliveryListActivity.class);
                startActivity(loginIntent);
            } else {
                Toast.makeText(this.getApplicationContext(), R.string.ErrorMessage, Toast.LENGTH_SHORT).show();
                invalidateOptionsMenu();
            }
        } else if (id == R.id.nav_Login) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        } else if (id == R.id.nav_Customer) {

            if (currentUser != null) {
                Intent customer = new Intent(this, CreateCustomerActivity.class);
                startActivity(customer);
            } else {

                Toast.makeText(this.getApplicationContext(), R.string.ErrorMessage, Toast.LENGTH_SHORT).show();
                invalidateOptionsMenu();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
