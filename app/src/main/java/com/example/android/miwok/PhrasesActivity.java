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

public class PhrasesActivity extends AppCompatActivity {

    private MediaPlayer mMediaplayeere;
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener= new AudioManager.OnAudioFocusChangeListener(){

        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||
                    focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaplayeere.pause();
                mMediaplayeere.seekTo(0);
            }else if (focusChange==AudioManager.AUDIOFOCUS_GAIN){
                mMediaplayeere.start();

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



        //create array of words

      final ArrayList<word> Words= new ArrayList<word>();

            //Create a list of words
        Words.add(new word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        Words.add(new word("What is your name?","tinne oyaase'ne",R.raw.phrase_what_is_your_name));
        Words.add(new word("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        Words.add(new word("How are you feeling?","michekses",R.raw.phrase_how_are_you_feeling));
        Words.add(new word("I'm feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        Words.add(new word("Are you coming?","eenes'aa",R.raw.phrase_are_you_coming));
        Words.add(new word("Yes, I'm coming.","hee'eenem",R.raw.phrase_yes_im_coming));
        Words.add(new word("I'm coming","eenem",R.raw.phrase_im_coming));
        Words.add(new word("Let's go.","yoowutis",R.raw.phrase_lets_go));
        Words.add(new word("Come here","enni'nem",R.raw.phrase_come_here));

        WordAdapter adapter=new WordAdapter(this,Words,R.color.category_phrases);
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
                mMediaplayeere = MediaPlayer.create(PhrasesActivity.this,worrd.getmAudioResourceId());

                    //start the audio file
                mMediaplayeere.start();


                    //setup the media on the media player,so that we can stop and release the media player once the sounds has finished playing
                mMediaplayeere.setOnCompletionListener(mCompletionListener);


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
        if (mMediaplayeere !=null){
            mMediaplayeere.release();

            mMediaplayeere=null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);


        }
    }
}
