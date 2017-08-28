package com.maxibi.kamusku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.maxibi.kamusku.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

/**
 * Created by User on 8/14/2017.
 */

public class CustomAdapter extends BaseAdapter implements Filterable, SectionIndexer {

    String[] sections;
    int layout;
    private Context context;
    private ArrayList<Word> wordArrayList;
    private ArrayList<Word> arrayList;
    private HashMap<String,Integer> mapIndex;

    //Constructor
    public CustomAdapter(Context context, ArrayList<Word> wordArrayList, int layout ) {
        this.context = context;
        this.wordArrayList = wordArrayList;
        this.arrayList = wordArrayList;
        this.layout = layout;

       /////////////////////////////////////////////////////////
       ///// THUMB INDEXER /////
       ///////////////////////

        mapIndex = new HashMap<String ,Integer>();
        for( int i = 0; i < wordArrayList.size(); i++)
        {
            String wordDex = wordArrayList.get(i).getBm();
            String ch = wordDex.substring(0,1);
            ch = ch.toUpperCase(Locale.UK);

            //HashMap will prevent duplicate
            if (!mapIndex.containsKey(ch))
            {
                mapIndex.put(ch, i);
            }
            //Hashmap will not prevent duplicate when
            //declare only mapIndex.put(ch, i)
        }

        Set<String> sectionLetters = mapIndex.keySet();

        //create a list from the set to sort
        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
       // Log.d("SectionList"," "+sectionList.toString());

        //Collection.sort ni untuk susun huruf dari A-Z secara menaik
        Collections.sort(sectionList);

        sections = new String[sectionList.size()];
        sectionList.toArray(sections); //masukkan ke dalam "sections" array

        ///////////////////////////////////////Debug process
                //    for( int i = 0; i< sections.length;i++){
                     //   Log.d("sections"," "+sections[i]);
                   // }
        ///////////////////////////////////////////
    }

    @Override
    public int getCount() {
       return wordArrayList.size();
    }

    @Override
    public Object getItem(int position) {

        return wordArrayList.get(position);}

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, null);

            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

                viewHolder.itemName.setText(wordArrayList.get(i).bm);




        return view;


    }

    @Override
    public Filter getFilter() {

        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                ArrayList<Word> tempArrayList = tempArrayList = new ArrayList<Word>();

                if( charSequence.length() == 0)
                {
                    //set the original result to return
                    filterResults.count = arrayList.size();
                    filterResults.values = arrayList;

                   // Log.d("abc", "running 00000000000000000000000000000000: ");
                }
                else
                {
                    charSequence = charSequence.toString().toLowerCase();
                    for( int i = 0; i < wordArrayList.size(); i++ )
                    {
                        Word data = wordArrayList.get(i);
                        if( data.getBm().toLowerCase().startsWith(charSequence.toString()))
                        {
                            tempArrayList.add(data);

                        }
                    }
                    //set the filtered result to return
                    filterResults.count = tempArrayList.size();
                    filterResults.values = tempArrayList;

                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                wordArrayList = (ArrayList<Word>) filterResults.values; //filter dalam arraylist ni

                notifyDataSetChanged();
               // Log.d("abc", "running: ade masalah bro..." + charSequence.length());

            }
        };
    }

    /////////////////////////////////////////////////////

    public String getItemIndex(int position){
        return wordArrayList.get(position).getBm();
    }

    ///////////////////////////////////////////////////////
    //SectionIndexer override method
    @Override
    public Object[] getSections()
    {
        return sections;
    }

    @Override
    public int getPositionForSection(int i)
    {
       // Log.d("getPositonForSection"," "+i);
        return mapIndex.get(sections[i]);
    }

    @Override
    public int getSectionForPosition(int i)
    {
       // Log.d("getSectionForPosition"," "+ i);
        return 0;
    }

   public void refresh(ArrayList<Word> items)
    {
        this.arrayList = items;
        notifyDataSetChanged();
    }

    /////////////////////////////////////////////////////
    private class ViewHolder {
        TextView itemName;

        public ViewHolder(View view) {
            itemName = (TextView) view.findViewById(R.id.test);
        }
    }


}