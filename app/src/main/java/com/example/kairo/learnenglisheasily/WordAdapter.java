package com.example.kairo.learnenglisheasily;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by kairo on 16/04/18.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    //  Resource ID for the background color for this list of words
    private int mColorResourceID;


    public WordAdapter(Context context, ArrayList<Word> words, int colorResourceID) {
        super( context, 0, words );
        mColorResourceID = colorResourceID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from( getContext() ).inflate(
                    R.layout.list_item, parent, false );
        }

        // Get the {@link Word} object located at this position in the list
        Word currentWord = getItem( position );

        // Find the TextView in the list_item.xml layout with the ID english_text_view
        TextView englishTextView = (TextView) listItemView.findViewById( R.id.english_text_view );
        // Get the english translation from the current word object and
        // set this text on the english TextView
        englishTextView.setText( currentWord.getEnglishTranslation() );

        // Find the TextView in the list_item.xml layout with the ID default_text_view
        TextView defaultTextView = (TextView) listItemView.findViewById( R.id.default_text_view );
        // Get the default translation from the current Word object and
        // set this text on the default TextView
        defaultTextView.setText( currentWord.getDefaultTranslation() );

        // Find the ImageView in the list_item.xml layout with the ID image
        ImageView iconView = (ImageView) listItemView.findViewById( R.id.image );

        if (currentWord.hasImage()) {

            // Get the image resource ID from the current AndroidFlavor object and
            // set the image to iconView
            iconView.setImageResource( currentWord.getImageResourceId() );

            //Make sure that the image resource specified in the current word
            iconView.setVisibility( View.VISIBLE );

        } else {

            // Otherwise hide the image view
            iconView.setVisibility( View.GONE );
        }

        // Set the theme color for the list item
        View textContainer = listItemView.findViewById( R.id.text_container );
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor( getContext(), mColorResourceID );
        // Set the background color of the text container view
        textContainer.setBackgroundColor( color );

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
































