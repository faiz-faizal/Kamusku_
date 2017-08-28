package com.maxibi.kamusku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {


    public ListView listView;
    TextView textView;
    DatabaseAccess databaseAccess;
    ArrayList<Word> quotes;
    CustomAdapter customAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);


        listView = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.test);

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        quotes = databaseAccess.getQuotesForBookmark(); // dapatkan semua qoutes

        customAdapter = new CustomAdapter(this, quotes);

        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(BookmarkActivity.this, PopUp.class);

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
        setTitle("My new title");
        return true;
    }
}
