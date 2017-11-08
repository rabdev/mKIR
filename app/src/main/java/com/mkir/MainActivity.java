package com.mkir;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.mkir.fragments.AddEvent;
import com.mkir.fragments.AddPatient;
import com.mkir.fragments.AddTest;
import com.mkir.fragments.Calendar;
import com.mkir.fragments.Home;
import com.mkir.fragments.Login;
import com.mkir.fragments.MyPatients;
import com.mkir.fragments.Profile;
import com.mkir.fragments.Tests;

import static com.mkir.R.id.action_profile;
import static com.mkir.R.id.bottom_navbar;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private ViewPager homeviewPager;
    private TabLayout tabLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private SharedPreferences preferences;
    private ImageView user_menu, add_patient;
    BottomNavigationView bottomNavigationView;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getPreferences(0);

        if (preferences.getBoolean(Constants.IS_LOGGED_IN,true)){
            setContentView(R.layout.activity_main);
        } else {

            Login login = new Login();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, login, login.getTag())
                    .commit();
        }


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navbar);
        bottomNavigationView.setVisibility(View.VISIBLE);
        BottomNavigationItemView mypatients = (BottomNavigationItemView) bottomNavigationView.findViewById(R.id.action_mypatients);
        BottomNavigationItemView search = (BottomNavigationItemView) bottomNavigationView.findViewById(R.id.action_search);
        mypatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setVisibility(View.GONE);
                MyPatients myPatients_fr = new MyPatients();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, myPatients_fr, myPatients_fr.getTag())
                        .addToBackStack("1")
                        .commit();

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Keresés", Toast.LENGTH_SHORT).show();
            }
        });

        add_patient = (ImageView) findViewById(R.id.add_patient);
        add_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPatient addPatient = new AddPatient();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, addPatient, addPatient.getTag())
                        .addToBackStack("1")
                        .commit();
            }
        });

        user_menu= (ImageView) findViewById(R.id.user_menu);
        user_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu usermenu= new PopupMenu(MainActivity.this,user_menu);
                usermenu.getMenuInflater().inflate(R.menu.menu_user,usermenu.getMenu());
                usermenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_profile:
                                Profile profile = new Profile();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_frame, profile, profile.getTag())
                                        .addToBackStack("1")
                                        .commit();
                                bottomNavigationView.setVisibility(View.GONE);
                                return true;
                            case R.id.action_exit:
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean(Constants.IS_LOGGED_IN,false);
                                editor.putString(Constants.NAME,"");
                                editor.putString(Constants.UNIQUE_ID,"");
                                editor.apply();
                                Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                finish();
                                startActivity(intent);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                usermenu.show();
            }
        });

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        homeviewPager = (ViewPager) findViewById(R.id.home_container);
        homeviewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.home_tab);
        tabLayout.setupWithViewPager(homeviewPager);
        /*tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    BottomNavigationItemView bottomNavigationItemView = (BottomNavigationItemView) bottomNavigationView.findViewById(R.id.action_add);
                    bottomNavigationItemView.setTitle("Betegfelvétel");
                    bottomNavigationItemView.setIcon(getDrawable(R.drawable.ic_add_patient_lr));
                    bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AddPatient addPatient = new AddPatient();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.content_frame, addPatient, addPatient.getTag())
                                    .addToBackStack("1")
                                    .commit();
                        }
                    });
                } else if (position == 1) {
                    BottomNavigationItemView bottomNavigationItemView = (BottomNavigationItemView) bottomNavigationView.findViewById(R.id.action_add);
                    bottomNavigationItemView.setTitle("Vizsgálatkérés");
                    bottomNavigationItemView.setIcon(getDrawable(R.drawable.ic_add_test_lr));
                    bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AddTest addTest = new AddTest();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.content_frame, addTest, addTest.getTag())
                                    .addToBackStack("1")
                                    .commit();
                        }
                    });
                } else {
                    BottomNavigationItemView bottomNavigationItemView = (BottomNavigationItemView) bottomNavigationView.findViewById(R.id.action_add);
                    bottomNavigationItemView.setTitle("Új időpont");
                    bottomNavigationItemView.setIcon(getDrawable(R.drawable.ic_add_event_lr));
                    bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AddEvent addEvent = new AddEvent();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.content_frame, addEvent, addEvent.getTag())
                                    .addToBackStack("1")
                                    .commit();
                        }
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/


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
        //if (id == R.id.action_settings) {
        //   return true;}

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Calendar calendar = new Calendar();
                    return calendar;
                    /*Home home = new Home();
                    return home;*/
                case 1:
                    Tests tests = new Tests();
                    return tests;
                /*case 2:
                    Calendar calendar = new Calendar();
                    return calendar;*/
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Kezdőlap";
                case 1:
                    return "Vizsgálatok";
                /*case 2:
                    return "Naptár";*/
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        index = getSupportFragmentManager().getBackStackEntryCount();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (index!=0){
            index=index-1;
            FragmentManager.BackStackEntry backStackEntry0 = fragmentManager.getBackStackEntryAt(index);
            String tag = backStackEntry0.getName();
            if(tag.equals("1")){
                bottomNavigationView.setVisibility(View.VISIBLE);
            } else if (tag.equals("child")) {
                Toast.makeText(this, "Szevasz", Toast.LENGTH_SHORT).show();
                //fragmentManager.getanager().popBackStackImmediate();
            }
        }

        super.onBackPressed();

    }


}
