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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.zxiu.angelsjob.AngelsJob;
import com.zxiu.angelsjob.R;
import com.zxiu.angelsjob.bean.User;
import com.zxiu.angelsjob.util.MyVolley;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zxiu.angelsjob.R.id.display_name;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String TAG = "MainActivity";
    int RC_SIGN_IN = 10086;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mCurrentUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mUserDatabaseRef;
    private ValueEventListener mValueEventListener;


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
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        initAuth();
        initDatabase();
    }

    protected void initAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mCurrentUser = firebaseAuth.getCurrentUser();
                initDatabase();
                if (mCurrentUser != null) {
                    TextView displayName = (TextView) navigationView.getHeaderView(0).findViewById(display_name);
                    displayName.setText(mCurrentUser.getDisplayName());
                    TextView email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email);
                    email.setText(mCurrentUser.getEmail());
                    NetworkImageView photo = (NetworkImageView) navigationView.getHeaderView(0).findViewById(R.id.photo);
                    photo.setImageUrl(mCurrentUser.getPhotoUrl().toString(), MyVolley.getInstance(MainActivity.this).getImageLoader());
                    Toast.makeText(MainActivity.this, "FirebaseAuth Signed in " + mCurrentUser.getDisplayName(), Toast.LENGTH_LONG).show();
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

        Log.i("FirebaseMessaging", "Token=" + FirebaseInstanceId.getInstance().getToken());
        FirebaseMessaging.getInstance().unsubscribeFromTopic("fragment_personal_info");
    }

    public void initDatabase() {
        if (mCurrentUser != null) {
            mDatabase = FirebaseDatabase.getInstance();
            mUserDatabaseRef = FirebaseDatabase.getInstance().getReference("users").child(mCurrentUser.getUid());
            mValueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User.setCurrentUser(dataSnapshot.getValue(User.class));
                    Log.i(TAG, "dataSnapshot is: " + dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "Failed to read value.", databaseError.toException());
                }
            };
            mUserDatabaseRef.addValueEventListener(mValueEventListener);
        } else {
            mDatabase = null;
            mUserDatabaseRef = null;
        }
        AngelsJob.currentUserDatabaseRef = mUserDatabaseRef;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        if (mUserDatabaseRef != null && mValueEventListener != null) {
            mUserDatabaseRef.removeEventListener(mValueEventListener);
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
            changeTo(PersonalInfoActivity.class);
        } else if (id == R.id.nav_curriculum_vitae) {
            changeTo(CurriculumVitaeActivity.class);
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

    private void changeTo(Class clazz) {
        startActivity(new Intent(this, clazz));
    }


}
