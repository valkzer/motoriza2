package com.example.valkzer.motorizados.Activities;

import android.net.Uri;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.GravityCompat;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.design.widget.FloatingActionButton;

import com.squareup.picasso.Picasso;
import com.example.valkzer.motorizados.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    protected void setUpNavigation()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

    @Override
    protected void onResume()
    {
        super.onResume();
        getInfoUser();
    }


    private void getInfoUser()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = user;
        if (user != null) {
            String name     = user.getDisplayName();
            String email    = user.getEmail();
            Uri    photoUrl = user.getPhotoUrl();

            TextView userEmail = ((TextView) (navigationView.getHeaderView(0).findViewById(R.id.txtUserEmail)));
            userEmail.setText(email);

            TextView userName = ((TextView) (navigationView.getHeaderView(0).findViewById(R.id.txtUserName)));
            if (name != null) {
                userName.setText(name);
            } else {
                userName.setText(R.string.userName);
            }

            ImageView userPicture = ((ImageView) (navigationView.getHeaderView(0).findViewById(R.id.imgPicture)));
            if (photoUrl != null) {
                Picasso.with(this.getApplicationContext()).load(photoUrl).into(userPicture);
            }

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
        if (id == R.id.nav_Form) {
            // Handle the camera action
        } else if (id == R.id.nav_Delivery) {
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
