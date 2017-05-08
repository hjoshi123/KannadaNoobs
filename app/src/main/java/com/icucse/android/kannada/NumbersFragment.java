package com.icucse.android.kannada;


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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {


    public NumbersFragment() {
        // Required empty public constructor
    }

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mAudioChangeListner =
            new AudioManager.OnAudioFocusChangeListener(){
                @Override
                public void onAudioFocusChange(int focusChange){
                    if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_GAIN)
                        //we gained audiofocus so we have to start playing the song
                        mMediaPlayer.start();
                    else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                        //we lost the audiofocus so release the mediaplayer resources
                        releaseMediaPlayer();
                    }
                }
            };

    //releasing media resources
    private MediaPlayer.OnCompletionListener mMediaOnCompletionListner = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.words_list, container, false);

        //create and setup the audiomanager to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // String [] numbersList = new String[10];
        //Using ArrayList is better than using array because it can grow or shrink by any amount
        //where as an array has restrictions on the size of the array
        //String [] numbersList = {"One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten"};
        // Set final to access it inside onItemClick method
        final ArrayList<Word> numbersList = new ArrayList<Word>();

        numbersList.add(new Word("One","ooNdu (೧)",R.drawable.number_one,R.raw.kannada_one));
        numbersList.add(new Word("Two","eradu (೨)",R.drawable.number_two,R.raw.kannada_two));
        numbersList.add(new Word("Three","mooru (೩)",R.drawable.number_three,R.raw.kannada_three));
        numbersList.add(new Word("Four","nalaku (೪)",R.drawable.number_four,R.raw.kannada_four));
        numbersList.add(new Word("Five","aaidu (೫)",R.drawable.number_five,R.raw.kannada_five));
        numbersList.add(new Word("Six","aaru (೬)",R.drawable.number_six,R.raw.kannada_six));
        numbersList.add(new Word("Seven","Yolu (೭)",R.drawable.number_seven,R.raw.kannada_seven));
        numbersList.add(new Word("Eight","entu (೮)",R.drawable.number_eight,R.raw.kannada_eight));
        numbersList.add(new Word("Nine","ombatu (೯)",R.drawable.number_nine,R.raw.kannada_nine));
        numbersList.add(new Word("Ten","hathu (೧೦)",R.drawable.number_ten,R.raw.kannada_ten));  

        //simple_list_item_1 is a resource xml file defined by android ... thats why we use android.R.layout and not
        //R.layout

        WordAdapter itemsAdapter = new WordAdapter(getActivity(),numbersList,R.color.category_numbers);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = numbersList.get(i);
                //release the MediaPlayer resources so that if user clicks on some other view while one is playing it directly jumps to other
                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mAudioChangeListner,
                        AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer = MediaPlayer.create(getActivity(),word.getAudioResourceId());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mMediaOnCompletionListner);
                }

                //Toast.makeText(NumbersActivity.this,"Item Clicked",Toast.LENGTH_SHORT);
                //Toast.LENGTH_SHORT is for time duration
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
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

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mAudioChangeListner);
        }
    }

}
