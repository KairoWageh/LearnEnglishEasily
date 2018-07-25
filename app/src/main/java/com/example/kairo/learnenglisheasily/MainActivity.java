package com.example.kairo.learnenglisheasily;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        ViewPager viewPager = (ViewPager) findViewById( R.id.viewpager );
        CategoryAdapter adapter = new CategoryAdapter(this,getSupportFragmentManager());

        viewPager.setAdapter( adapter );
        // Find the tab layout that shows the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);
        /*TextView numbersList = findViewById(R.id.numbers);
        final TextView familyMembersList = findViewById(R.id.family);
        TextView colorsList = findViewById(R.id.colors);
        TextView phrasesList = findViewById(R.id.phrases);
        numbersList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                // Create a new intent to open numbers activity

                Intent numbersActivity = new Intent(MainActivity.this,NumbersActivity.class);
                startActivity(numbersActivity);
            }

        });

        familyMembersList.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create a new intent to open familyMembers activity

                Intent familyMembersActivity = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(familyMembersActivity);
            }
        } );

        colorsList.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create a new intent to open colors activity

                Intent colorsActivity = new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(colorsActivity);
            }
        } );

        phrasesList.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create a new intent to open phrases activity

                Intent phrasesActivity = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(phrasesActivity);
            }
        } );*/
    }

    // This method opens the numbers actiity

    /*public void openNumbersList(View view) {
        Intent numbersActivity = new Intent(this, NumbersActivity.class);
        startActivity(numbersActivity);
    }*/
}
