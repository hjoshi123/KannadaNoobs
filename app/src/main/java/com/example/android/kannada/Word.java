package com.example.android.kannada;

/**
 * Created by root on 3/1/17.
 * Custom Word class to display two words in a single view
 */

public class Word {

    // variable named m due to convention reasons declated in TextView class
    private String mEnglishTranslation;

    private String mKannadaTranslation;

    private int mImageResourceId = NO_IMAGE;

    private static final int NO_IMAGE = -1;

    private int mAudioResourceId;

    public Word(String EnglishTranslation, String KannadaTranslation,int audioResourceId){
        mEnglishTranslation = EnglishTranslation;
        mKannadaTranslation = KannadaTranslation;
        mAudioResourceId = audioResourceId;
    }

    public Word(String EnglishTranslation, String KannadaTranslation, int ImageResourceId,int audioResourceId){
        mEnglishTranslation = EnglishTranslation;
        mKannadaTranslation = KannadaTranslation;
        mImageResourceId = ImageResourceId;
        mAudioResourceId = audioResourceId;
    }

    public String getEnglishTranslation(){
        return mEnglishTranslation;
    }

    public String getKannadaTranslation(){
        return mKannadaTranslation;
    }

    public int getImageResourceId(){
        return mImageResourceId;
    }

    public boolean hasImageId(){
        return mImageResourceId != NO_IMAGE;
    }

    public int getAudioResourceId(){
        return mAudioResourceId;
    }
}
