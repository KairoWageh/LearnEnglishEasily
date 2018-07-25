package com.example.kairo.learnenglisheasily;


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
public class ColorsFragment extends Fragment {

    // Handles playback of all the sound files
    private MediaPlayer mMediaPlayer;

    // Handles audio focus when playing a sound file
    private AudioManager mAudioManager;

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

    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.word_list, container, false );

        /** create and setup the {@link AudioManager} to request audio focus */

        mAudioManager = (AudioManager) getActivity().getSystemService( Context.AUDIO_SERVICE );

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add( new Word( "أحمر", "Red", R.drawable.color_red, R.raw.red ) );
        words.add( new Word( "أخضر", "Green", R.drawable.color_green, R.raw.green ) );
        words.add( new Word( "أصفر", "Yellow", R.drawable.color_mustard_yellow, R.raw.yellow ) );
        words.add( new Word( "بنى", "Brown", R.drawable.color_brown, R.raw.brown ) );
        words.add( new Word( "أسود", "Black", R.drawable.color_black, R.raw.black ) );
        words.add( new Word( "رصاصى", "Gray", R.drawable.color_gray, R.raw.gray ) );
        words.add( new Word( "أبيض", "White", R.drawable.color_white, R.raw.white ) );
        WordAdapter colors = new WordAdapter( getActivity(), words, R.color.category_colors );
        ListView color = (ListView) rootView.findViewById( R.id.list );
        color.setOnItemClickListener( new AdapterView.OnItemClickListener() {
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

                    mMediaPlayer.setOnCompletionListener( onCompletionListener );
                }
            }
        } );
        color.setAdapter( colors );
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
