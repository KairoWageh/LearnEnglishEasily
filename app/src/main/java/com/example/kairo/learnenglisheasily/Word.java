package com.example.kairo.learnenglisheasily;

/**
 * Created by kairo on 16/04/18.
 */


/*
* {@link Word} represents a vocabulary word that the user wants to learn.
* It contains a default translation and English translation for that word.
* */
public class Word {

    //Default translation for the word
    private String mDefaultTranslation;

    // English translation for the word
    private String mEnglishTranslation;

    // The id of the image
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    // Resource ID for the audio for each item

    private int mAudioResourceID;

    /**
    *  Create a new word object
    *
    *  @param defaultTranslation is the word in a languege that the user speakes (Arabic)
    *
    *  @param englishTranslation is the word in English language
    *
    *  @param audioResourceID is the resource id for the audio associated with this word
    *
    *
    * */


    public Word(String defaultTranslation, String englishTranslation, int audioResourceID) {
        mDefaultTranslation = defaultTranslation;
        mEnglishTranslation = englishTranslation;
        mAudioResourceID = audioResourceID;
    }


    /**
    *  Create a new word object
    *
    *  @param defaultTranslation is the word in a languege that the user speakes (Arabic)
    *
    *  @param englishTranslation is the word in English language
    *
    *  @param imageResourceId is the drawable resource ID for the image assest
    *
    *  @param audioResourceID is the resource id for the audio associated with this word
    *
    * */

    public Word(String defaultTranslation, String englishTranslation, int imageResourceId, int audioResourceID) {
        mDefaultTranslation = defaultTranslation;
        mEnglishTranslation = englishTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceID = audioResourceID;
    }

    // Get the default translation of the word

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }


    // Get the english translation of the word

    public String getEnglishTranslation() {
        return mEnglishTranslation;
    }

    // Get the id of the image

    public int getImageResourceId() {
        return mImageResourceId;
    }

    /*
    *  Returns whether or not there is an image for this word
    */
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    /*
    * Returns the resource id of the word
    */
    public int getAudioResourceID() {
        return mAudioResourceID;
    }
}