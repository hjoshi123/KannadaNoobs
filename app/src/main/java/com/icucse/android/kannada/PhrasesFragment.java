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
public class PhrasesFragment extends Fragment {


    public PhrasesFragment() {
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
        View rootView = inflater.inflate(R.layout.words_list,container,false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> phrasesList = new ArrayList<>();

        phrasesList.add(new Word("How are you","Nivu hege idira",R.raw.how_are_you));
        //phrasesList.add(new Word("How much is this","idu estu",R.raw.number_three));
        phrasesList.add(new Word("What is your name?","Nimma hesaru enu?",R.raw.what_is_your_name));
        phrasesList.add(new Word("I'm good,Thank You",
                "Nanu chenagi iddane,dhanyavadagallu",R.raw.im_good_thank_you));
        phrasesList.add(new Word("Turn Left","Eddake tirigi",R.raw.turn_left));
        phrasesList.add(new Word("Turn Right","Balekka tirigi",R.raw.turn_right));
        phrasesList.add(new Word("Help","Sahaya",R.raw.help));
        phrasesList.add(new Word("Call the police","Policerannu kareyiri",R.raw.call_the_police));
        phrasesList.add(new Word("I don't know","Nannage gotilla",R.raw.i_dont_know));
        phrasesList.add(new Word("Go straight","Nerakke hogi",R.raw.go_straight));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(),phrasesList,R.color.category_phrases);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = phrasesList.get(i);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mAudioChangeListner,
                        AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer = MediaPlayer.create(getActivity(),word.getAudioResourceId());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mMediaOnCompletionListner);

                }
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
