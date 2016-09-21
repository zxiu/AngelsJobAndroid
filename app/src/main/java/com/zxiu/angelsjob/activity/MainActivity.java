package com.zxiu.angelsjob.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.zxiu.angelsjob.R;
import com.zxiu.angelsjob.util.MyVolley;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zxiu.angelsjob.R.id.displayName;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    String TAG = "MainActivity";
    int RC_SIGN_IN = 10086;

    @BindView(R.id.nav_view)
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    TextView displayName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.displayName);
                    displayName.setText(user.getDisplayName());
                    TextView email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email);
                    email.setText(user.getEmail());
                    NetworkImageView photo = (NetworkImageView) navigationView.getHeaderView(0).findViewById(R.id.photo);
                    photo.setImageUrl(user.getPhotoUrl().toString(), MyVolley.getInstance(MainActivity.this).getImageLoader());
                    Toast.makeText(MainActivity.this, "FirebaseAuth Signed in " + user.getDisplayName(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "FirebaseAuth Not Signed in ", Toast.LENGTH_LONG).show();
                    startActivityForResult(
                    // Get an instance of AuthUI based on the default app
                    AuthUI.getInstance().createSignInIntentBuilder().setProviders(
                            AuthUI.EMAIL_PROVIDER,
                            AuthUI.GOOGLE_PROVIDER,
                            AuthUI.FACEBOOK_PROVIDER).build(),
                    RC_SIGN_IN);
                }

            }
        };

//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        Log.i("FirebaseAuth", "auth=" + auth.getCurrentUser());
//        if (auth.getCurrentUser() != null) {
//            Log.i("FirebaseAuth", "Providers=" + auth.getCurrentUser().getProviders());
//            Toast.makeText(this, "FirebaseAuth Signed in " + auth.getCurrentUser().getDisplayName(), Toast.LENGTH_LONG).show();
//
//        } else {
//            Toast.makeText(this, "FirebaseAuth Not Signed in", Toast.LENGTH_LONG).show();
//            startActivityForResult(
//                    // Get an instance of AuthUI based on the default app
//                    AuthUI.getInstance().createSignInIntentBuilder().setProviders(
//                            AuthUI.EMAIL_PROVIDER,
//                            AuthUI.GOOGLE_PROVIDER,
//                            AuthUI.FACEBOOK_PROVIDER).build(),
//                    RC_SIGN_IN);
//        }
//        FirebaseMessaging.getInstance().subscribeToTopic("test");

        Log.i("FirebaseMessaging", "Token=" + FirebaseInstanceId.getInstance().getToken());
        FirebaseMessaging.getInstance().unsubscribeFromTopic("test");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        Log.i(TAG, "displayName= " + displayName);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("FirebaseAuth", "requestCode=" + requestCode + " resultCode=" + resultCode);
        if (requestCode == RC_SIGN_IN) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            Log.i("FirebaseAuth", "auth2=" + auth.getCurrentUser());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_personal_info) {

        } else if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
