package com.example.android.miwok;

/**
 * Created by Brenda on 2017/07/11.
 */
/**
 * {@link word } represents a vocabulary word that the user wants to learn.
 * it contains a default translation and Miwork translation for that word
 */
public class word {
    /** default translation for the word*/
    private  String mDefaultTranslation;

    /**Miwork translation for the word*/
    private String mMiworkTranslation;

    /** Image resource ID for the word*/
    private int mImageResourseId = NO_IMAGE_PROVIDED;


    private static final int NO_IMAGE_PROVIDED= -1;

    private int mAudioResourceId;


    /**
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with (such as English)
     * @param miworkTranslation is the word in the Miwork language
     * @param audioResourceId
     */
    public word(String defaultTranslation,String miworkTranslation, int audioResourceId) {
        mDefaultTranslation=defaultTranslation;
        mMiworkTranslation= miworkTranslation;
        mAudioResourceId = audioResourceId;

    }

    public word(String defaultTranslation,String miworkTranslation, int imageResourceId,int audioResourceId) {
        mDefaultTranslation=defaultTranslation;
        mMiworkTranslation= miworkTranslation;
        mImageResourseId=imageResourceId;
        mAudioResourceId = audioResourceId;
    }


    /**
  *Get the default translation of the word.
     */
    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }
    /**
     *Get the Miwork translation of the word.
     */
    public String getmMiworkTranslation() {
        return mMiworkTranslation;
    }

    /**
     *Return the image resource ID of the word
     * @return
     */
    public int getmImageResourseId() {
        return mImageResourseId;
    }

    public int getmAudioResourceId() {
        return mAudioResourceId;
    }

    public  boolean hasImage(){
        return mImageResourseId != NO_IMAGE_PROVIDED;
    }

}
