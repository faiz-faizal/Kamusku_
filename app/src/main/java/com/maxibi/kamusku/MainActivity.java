package com.maxibi.kamusku;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxibi.kamusku.Word;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final String TAG = this.getClass().getName();
    public ListView listView;
    //public Button buttonSearch;
    public EditText editText;
    public TextView textView;
    public ImageButton ibBookmark;
    NavigationView navigationView = null;
    Toolbar toolbar = null;
    DrawerLayout drawer;
    boolean twice = false;
    private DatabaseAccess databaseAccess;
    private ArrayList<Word> quotes;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        ibBookmark = (ImageButton)findViewById(R.id.ibBookmark);


        ////////////////////////////////////////////////////////////////////////////////////////////


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.isChecked())
                    menuItem.setChecked(false);
                else
                    menuItem.setChecked(true);

                drawer.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.nav_bookmark:
                        Log.d("abc", "running............");
                        Intent intentd = new Intent(MainActivity.this, BookmarkActivity.class);
                        startActivity(intentd);
                        return true;
                    case R.id.nav_About:
                        AlertDialog.Builder a_builder1 = new AlertDialog.Builder(MainActivity.this);
                        a_builder1.setMessage("KamusKu version 1.0 from Maxibi IT Solutions. blablablablablablablablablablablabla" +
                                "developer Faiz Faizal");
                        AlertDialog alert1 = a_builder1.create();
                        alert1.setTitle("KamusKu 1.0");
                        alert1.show();
                        return true;
                    case R.id.nav_share:
                        //only support png image
                        Uri imageUri = Uri.parse("android.resource://com.maxibi.testing/drawable/"+R.drawable.logo);
                        Intent chooser;
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                        intent.putExtra(Intent.EXTRA_TEXT,"Please Support Us By Downloading This Apps");
                        chooser = Intent.createChooser(intent, "Send Image");
                        startActivity(chooser);
                        return true;
                    case R.id.nav_logout:
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                        a_builder.setMessage("Do you want to close this App?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        finish();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.cancel();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.show();
                }
                return true;
            }
        });





        ////////////////////////////////////////////////////////////////////////////////////////////





        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.test);

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        quotes = databaseAccess.getQuotes(); // dapatkan semua qoutes

        customAdapter = new CustomAdapter(this, quotes, R.layout.list_item);

        listView.setAdapter(customAdapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                customAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        ////////////////////////////////////////////////////////////////////////////////////////////





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(MainActivity.this, PopUp.class);

                Log.d("TEST", "TRY " + adapterView + " " + view + " " + position);
                for (int i = 0; i < quotes.size(); i++) {
                    if ((quotes.get(i).getBm()).equals(customAdapter.getItemIndex(position))) {
                        intent.putExtra("Word", quotes.get(i).bm);
                        intent.putExtra("Definition", quotes.get(i).bi);
                        intent.putExtra("Bookmark",quotes.get(i).bookmark);
                    }
                }

                startActivity(intent);
            }
        });




        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseAccess.open();
        quotes = databaseAccess.getQuotes();
        customAdapter.refresh(quotes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {

        Log.d(TAG, "click");

        if (twice == true){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }
        twice = true;
        Log.d(TAG, "twice: " + twice);

        //super.onBackPressed();
        Toast.makeText(MainActivity.this, "Tap again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
                Log.d(TAG, "twice: " + twice);
            }
        }, 3000);

    }


}
