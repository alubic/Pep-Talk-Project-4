package owlslubic.peptalkapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import owlslubic.peptalkapp.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = "MainActivity";

    private BottomSheetBehavior mBottomSheetBehavior;
    private TextView mBottomSheetHeading, mBottomSheetTopText, mBottomSheetBottomText, mPepTalkTextView;
    private FloatingActionButton mFab;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

//        setUpFirebaseAuth();


    }


    //making a method to do this so the oncreate stays clean and pretty aww
    private void initViews() {

        mPepTalkTextView = (TextView) findViewById(R.id.textview_main_circular);

        mPepTalkTextView.setText("Sign Up");//temp

        mPepTalkTextView.setOnClickListener(this);


        //TODO fuck with the toolbar here
        //toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        //nav drawer setup
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(mToggle);
        mToggle.syncState();
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);


        //bottom sheet
        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheetLayout));
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mBottomSheetHeading = (TextView) findViewById(R.id.textview_bottomSheetHeading);
        mBottomSheetBottomText = (TextView) findViewById(R.id.textview_bottomsheet_bottom);
        mBottomSheetTopText = (TextView) findViewById(R.id.textview_bottomsheet_top);



        //fab
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);//is this ok to put in here?
    }



    //NAV DRAWER MENU options
    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {//this is in the overflow menu

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_peptalks) {
            Intent intent = new Intent(MainActivity.this, PepTalkListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_checklist) {
            Intent intent = new Intent(MainActivity.this, ChecklistActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_resources) {
            Toast.makeText(MainActivity.this, "resources", Toast.LENGTH_SHORT).show();

            launchBottomSheetFromNav();

            mBottomSheetHeading.setText("More Resources");
            mBottomSheetTopText.setText("");
            mBottomSheetBottomText.setText("");

        } else if (id == R.id.nav_instructions) {
            Toast.makeText(MainActivity.this, "instructions", Toast.LENGTH_SHORT).show();

            launchBottomSheetFromNav();

            mBottomSheetHeading.setText("Instructions");
            mBottomSheetTopText.setText("");
            mBottomSheetBottomText.setText("");


        } else if (id == R.id.nav_about) {
            Toast.makeText(MainActivity.this, "about!", Toast.LENGTH_SHORT).show();

            launchBottomSheetFromNav();

            mBottomSheetHeading.setText("About");
            mBottomSheetTopText.setText("");
            mBottomSheetBottomText.setText("");

        } else if (id == R.id.nav_logout) {

//            if (!isUserLoggedIn()){
//                Log.i(TAG, "USER IS NOT LOGGED IN");
//                mDrawer.closeDrawer(GravityCompat.START);
//            }
//            logUserOut();

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }






    //temporarily moving this to custo dialog class

//    public static void createUserWithEmailAndPassword(String email, String password) {
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        //"if sign in succeeds, the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener."
//
//                        //^not sure what logic they talkin bout but we'll see
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "onComplete: " + "CREATE USER WAS SUCCESSFUL");
//                        }
//
//
//                        //if sign in fails:
//                        if (!task.isSuccessful()) {
//                            Log.d(TAG, "onComplete: SIGN UP USER FAILED");
//                        }
//
//                    }
//                });
//    }





    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fab:

                break;
            case R.id.textview_main_circular:
                //launch pep talk view
                //but temporarily it'll be a signup thing
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);


//            case R.id.button_temp_bottom_sheet://temp
//                Toast.makeText(MainActivity.this, "bottom sheets", Toast.LENGTH_SHORT).show();
//                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
//                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                } else if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
//                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                } else {
//                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                }
//                break;


        }

    }




    private void launchBottomSheetFromNav() {
        //close nav drawer
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        }

        //then open bottomsheet
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }






}
