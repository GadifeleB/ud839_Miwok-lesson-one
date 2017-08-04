/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mMediaplayer;
    private AudioManager mAudioManager;



    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener= new AudioManager.OnAudioFocusChangeListener(){

        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||
                    focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaplayer.pause();
                mMediaplayer.seekTo(0);
            }else if (focusChange==AudioManager.AUDIOFOCUS_GAIN){
                mMediaplayer.start();
                //resume play back
            }else if (focusChange==AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }

    };

    private MediaPlayer.OnCompletionListener mCompletionListener =new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //create and setup the audio manager to request audio focus
        mAudioManager =(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //Create a Array of words
      final ArrayList<word> Words= new ArrayList<word>();
        //Create a list of words
        Words.add(new word("father","apa",R.drawable.family_father,R.raw.family_father));
        Words.add(new word("mother","ata",R.drawable.family_mother,R.raw.family_mother));
        Words.add(new word("son","angsi",R.drawable.family_son,R.raw.family_son));
        Words.add(new word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        Words.add(new word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        Words.add(new word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        Words.add(new word("older sister","tete",R.drawable.family_older_sister,R.raw.family_older_sister));
        Words.add(new word("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        Words.add(new word("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        Words.add(new word("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter adapter=new WordAdapter(this,Words,R.color.category_family);
        //Find the {@link ListView} object in the view hierarchy of the {@link Axtivity}.
        //There should be a {@link ListView } with the view ID called list, which is declared in the
        //word_list_xml layout file.
        ListView listView =(ListView)findViewById(R.id.List);

        //Make the {@Link listView} use the {@Link Word Adapter} we created above, so that the
        //{@link ListView } will display list items for each {@link word } in the list.
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word worrd =  Words.get(position);
                //release the media player if it currently exist because we are about to play a different sound
                releaseMediaPlayer();

                //Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //create and setup the mediaPlayer for the audio resource associated with the current word
                mMediaplayer= MediaPlayer.create(FamilyActivity.this,worrd.getmAudioResourceId());

                    //start the audio file
                mMediaplayer.start();



                    //setup the media on the media player,so that we can stop and release the media player once the sounds has finished playing
                mMediaplayer.setOnCompletionListener(mCompletionListener);


                }
            }
        });



    }
    @Override
    protected void onStop(){
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){
        if (mMediaplayer !=null){
            mMediaplayer.release();

            mMediaplayer=null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);

        }
    }

    }

