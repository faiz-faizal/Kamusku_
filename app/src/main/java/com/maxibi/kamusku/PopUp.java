package com.maxibi.kamusku;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by User on 8/14/2017.
 */

public class PopUp extends Activity {

    TextView msTextView;
    TextView enTextView;
    ImageButton ibBookmark;
    int bookmarkSwith = 0;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translate);

        msTextView = (TextView)findViewById(R.id.bm_word);
        enTextView = (TextView)findViewById(R.id.bi_word);
        ibBookmark = (ImageButton)findViewById(R.id.ibBookmark);

        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();


        Log.d("DICTIONARY", "pop up activity Started");

        msTextView.setText(getIntent().getStringExtra("Word"));
        enTextView.setText(getIntent().getStringExtra("Definition"));


        if(getIntent().getStringExtra("Bookmark").equals("null"))
        {
            //tukar image bookmark unset
            ibBookmark.setImageResource(R.mipmap.ic_bookmark_unset);
            bookmarkSwith = 1; //odd
        }
        else if(getIntent().getStringExtra("Bookmark").equals("1"))
        {
            //tukar image bookmark set
            ibBookmark.setImageResource(R.mipmap.ic_bookmark_set);
            bookmarkSwith = 2; //even

        }

        ibBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if (number % 2 == 0) numberIsEven
               //  if (number % 2 != 0) numberIsOdd
                if( bookmarkSwith % 2 != 0 ) //1
                {
                    databaseAccess.open();
                    Log.d("ONCLICK ", "itemClickedSetClicked");
                    ibBookmark.setImageResource(R.mipmap.ic_bookmark_set);
                    databaseAccess.setBookmark(getIntent().getStringExtra("Word"));
                }
                else if( bookmarkSwith % 2 == 0) //2
                {
                    databaseAccess.open();
                    Log.d("ONCLICK ", "itemClickedUn-SetClicked");
                    ibBookmark.setImageResource(R.mipmap.ic_bookmark_unset);
                    Log.d(""," ONCLKDFJKLSDFksd" + getIntent().getStringExtra("Word"));
                    databaseAccess.unSetBookmark(getIntent().getStringExtra("Word"));
                }
               bookmarkSwith++;

            }
        });

        Log.d("DICTIONARY","Set text started");
    }


}
