/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.icucse.android.kannada;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.graphics.Color;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private boolean isFirstRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //Intialize shared preferences
                SharedPreferences preferences =
                        PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                isFirstRun = preferences.getBoolean("firstStart",true);

                if(isFirstRun){
                    startActivity(new Intent(MainActivity.this,IntroActivity.class));

                    //Make a new preferences editor
                    SharedPreferences.Editor e = preferences.edit();

                    //edit preferences to false because we dont want to run it again
                    e.putBoolean("firstStart",false);
                    e.apply();
                }
            }
        });
        t.start();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kannada for Noobs");
        //toolbar.setNavigationIcon(R.drawable.ic_action_ka);

        // Find the view pager that will allow the user to swipe between fragments
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        CategoriesAdapter adapter = new CategoriesAdapter(getSupportFragmentManager());

        //Set the adapter on the viewpager
        viewPager.setAdapter(adapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_names);
        tabLayout.setupWithViewPager(viewPager);

        toolbar.setBackgroundColor(getResources().getColor(R.color.category_numbers));

        tabLayout.setBackgroundColor(getResources().getColor(R.color.category_numbers_toolbar));
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition()==0){
                    tabLayout.setBackgroundColor(getResources().getColor(R.color.category_numbers));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.category_numbers_toolbar));
                    viewPager.setCurrentItem(tab.getPosition());
                }
                else if(tabLayout.getSelectedTabPosition()==1){
                    tabLayout.setBackgroundColor(getResources().getColor(R.color.category_colors));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.category_colors_toolbar));
                    viewPager.setCurrentItem(tab.getPosition());
                }
                else if(tabLayout.getSelectedTabPosition()==2){
                    tabLayout.setBackgroundColor(getResources().getColor(R.color.category_family));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.category_family_toolbar));
                    viewPager.setCurrentItem(tab.getPosition());
                }
                else{
                    tabLayout.setBackgroundColor(getResources().getColor(R.color.category_phrases));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.category_phrases_toolbar));
                    viewPager.setCurrentItem(tab.getPosition());
                }
                tabLayout.setTabTextColors(Color.BLACK,Color.WHITE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
               tabLayout.setBackgroundColor(getResources().getColor(R.color.primary_color));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_items,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.about_us_id:
                Intent aboutActivity = new Intent(MainActivity.this,AboutUs.class);
                startActivity(aboutActivity);
                break;
            case R.id.contact_us_id:
                Toast.makeText(this,"Clicked on Contact us",Toast.LENGTH_SHORT).show();
                break;
            case R.id.translate:
                startActivity(new Intent(MainActivity.this,TranslateActivity.class));
                break;
            default:
                break;
        }

        return true;
    }

    /*public void openNumbers(View view){
        Intent numbersClass = new Intent(this,NumbersActivity.class);
        startActivity(numbersClass);
    }*/
}
