package com.icucse.android.kannada;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 3/1/17.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    // Resource ID for the background color
    private int mcolorResourceId;

    /*
    Check out the arrayadapter ctor ... it contains three parameters activity,resource ID, list<T>
    we can any value to resource ID since we are inflating the view later on using getView method..
     */
    public WordAdapter(Activity context, ArrayList<Word> numbersList,int colorResourceId){
        super(context,0,numbersList); //calling ArrayAdapter constructor
        mcolorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        //checking if there any recycled views to put up
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Word numberPosition = getItem(position);

        TextView kannadaNumbers = (TextView) listItemView.findViewById(R.id.kannada_text);
        kannadaNumbers.setText(numberPosition.getKannadaTranslation());

        TextView englishNumbers = (TextView) listItemView.findViewById(R.id.english_text);
        englishNumbers.setText(numberPosition.getEnglishTranslation());

        ImageView image = (ImageView) listItemView.findViewById(R.id.image);

        if(numberPosition.hasImageId()){
            image.setImageResource(numberPosition.getImageResourceId());
            image.setVisibility(View.VISIBLE);
        }
        else{
            image.setVisibility(View.GONE);
        }

        //find the linear layout whose background color needs to be set
        View textContainer = listItemView.findViewById(R.id.text_container);
        //find the color resource id maps to
        int color = ContextCompat.getColor(getContext(),mcolorResourceId);

        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
