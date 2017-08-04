package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Brenda on 2017/07/11.
 */

    public class WordAdapter extends ArrayAdapter<word> {


    private  int mColorResourceId;


        public WordAdapter(Activity context, ArrayList<word> words, int colorResourceId){
            super(context,0,words);
        mColorResourceId=colorResourceId;
        }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //check if the existing view is beingreused,otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        //Get the object located at this position in the list
        word currentWord = getItem(position);

        //find the Textview in the list_item.xml layout with the ID miwok_text_view
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwork_text_view);

        //get the Miwok translation from the current word object and set this text on the
        //on the miwok TextView
        miwokTextView.setText(currentWord.getmMiworkTranslation());

        //find the TextView in the list_item.xml layout with the ID default_text_view
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);

        //Get the default translation from the currentWord object and set this text on
        //the default TextView
        defaultTextView.setText(currentWord.getmDefaultTranslation());
//Find the ImageView in the list_item.xml layout with the ID image
        ImageView imageview = (ImageView) listItemView.findViewById(R.id.image);
        if (currentWord.hasImage()) {

            //set the Image to the Image resource specified in the current word
            imageview.setImageResource(currentWord.getmImageResourseId());
            imageview.setVisibility(View.VISIBLE);
        } else {
            imageview.setVisibility(View.GONE);
        }
         View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(),mColorResourceId);
        textContainer.setBackgroundColor(color);
        //Return the whole list item layout (containing two Textview so that it can be shown in
        //the listView
        return listItemView;
    }
}