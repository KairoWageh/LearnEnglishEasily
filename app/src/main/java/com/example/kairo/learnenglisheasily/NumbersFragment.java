package com.example.kairo.learnenglisheasily;


import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {
    // Handles playback of all the sound files
    private MediaPlayer mMediaPlayer;

    // Handles audio focus when playing a sound file
    private AudioManager mAudioManager;

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        /** The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus
                         * short amount of time.
                         * The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means our app is allowed
                         * to continue playing sound but at a lower volume .
                         * We put both cases the same way because our app is playing short sound files.
                         * Pause playback and reset player to the start of the file.
                         * That way we play the word from the beginning when we resume playback
                         *
                         * */
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo( 0 );
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        /**
                         * The AUDIOFOCUS_GAIN case means we have regained focus and
                         * can resume playback
                         * */
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        /**
                         *  The AUDIOFOCUS_LOSS case means we've lost audio focus and stop
                         *  playback and cleanup resources
                         * */
                        releaseMediaPlayer();
                    }
                }
            };

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file
     */


    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };


    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.word_list, container, false );

        /** create and setup the {@link AudioManager} to request audio focus */

        mAudioManager = (AudioManager) getActivity().getSystemService( Context.AUDIO_SERVICE );

        // Find the root view of the whole layout
        //LinearLayout rootView = findViewById(R.id.rootView);

        // Create a variable to keep track of index position
        int index = 0;

        // Create an array list of words

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add( new Word( "واحد", "One", R.drawable.number_one, R.raw.one ) );
        words.add( new Word( "اثنان", "Two", R.drawable.number_two, R.raw.two ) );
        words.add( new Word( "ثﻻثة", "Three", R.drawable.number_three, R.raw.three ) );
        words.add( new Word( "أربعة", "Four", R.drawable.number_four, R.raw.four ) );
        words.add( new Word( "خمسة", "Five", R.drawable.number_five, R.raw.five ) );
        words.add( new Word( "ستة", "Six", R.drawable.number_six, R.raw.six ) );
        words.add( new Word( "سبعة", "Seven", R.drawable.number_seven, R.raw.seven ) );
        words.add( new Word( "ثمانية", "Eight", R.drawable.number_eight, R.raw.eight ) );
        words.add( new Word( "تسعة", "Nine", R.drawable.number_nine, R.raw.nine ) );
        words.add( new Word( "عشرة", "Ten", R.drawable.number_ten, R.raw.ten ) );

        /**while (index < words.size()){
         TextView wordView = new TextView(this);
         rootView.addView(wordView);
         wordView.setText(words.get(index));
         index ++;
         }*/

        /**for(index = 0; index < words.size(); index ++){
         TextView wordView = new TextView(this);
         rootView.addView(wordView);
         wordView.setText(words.get(index));
         }*/


        // Create Recycler View using array adapter

        WordAdapter itemsAdapter = new WordAdapter( getActivity(), words, R.color.category_numbers );
        final ListView listView = (ListView) rootView.findViewById( R.id.list );
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = words.get( i );

                /**
                 *
                 * Release the media player if it currently exists because we are about to
                 * play a diiferent sound file
                 *
                 */

                releaseMediaPlayer();

                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus( mOnAudioFocusChangeListener,

                        // Use the music stream
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT );

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now
                    /*
                    *  Create and setup the {@link MediaPlayer} for the audio resource associated
                    *  with the current word
                    * */
                    mMediaPlayer = MediaPlayer.create( getActivity(), word.getAudioResourceID() );

                    // Start the audio file
                    mMediaPlayer.start();
                    /*
                    *
                    * Setup a listenr on the media player, so that  we can stop and release
                    * the media player once the sounds have finished playing
                    *
                    */
                    mMediaPlayer.setOnCompletionListener( mCompletionListener );
                }

            }
        } );
        listView.setAdapter( itemsAdapter );

        return rootView;
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            /**
             * Regardless of whether or not we were granted audio focus, abandon it.
             * This also unregisters the AudioFocusChangeListener so we don't get any more callbacks.
             *
             */
            mAudioManager.abandonAudioFocus( mOnAudioFocusChangeListener );
        }
    }

    @Override
    public void onStop() {
        /**
         *  When the activity is stopped, release the media player resources because we won't
         *  be playing any more sounds.
         * */
        super.onStop();
        releaseMediaPlayer();
    }
}
