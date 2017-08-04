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
import android.widget.Toast;

import java.util.ArrayList;

import static android.support.v7.widget.AppCompatDrawableManager.get;
import static android.widget.AdapterView.*;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mmMediaPlayer;
    private AudioManager mAudioManager;

AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener= new AudioManager.OnAudioFocusChangeListener(){

    @Override
    public void onAudioFocusChange(int focusChange) {
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||
            focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
            mmMediaPlayer.pause();
            mmMediaPlayer.seekTo(0);
    }else if (focusChange==AudioManager.AUDIOFOCUS_GAIN){
            mmMediaPlayer.start();

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

      final ArrayList<word> Words = new ArrayList<word>();
        //Create a list of words
        Words.add(new word("one", "lutti", R.drawable.number_one,R.raw.number_one));
        Words.add(new word("two", "otiiko", R.drawable.number_two,R.raw.number_two));
        Words.add(new word("three", "tolookosu", R.drawable.number_three,R.raw.number_three));
        Words.add(new word("four", "oyyisa", R.drawable.number_four,R.raw.number_four));
        Words.add(new word("five", "massokka", R.drawable.number_five,R.raw.number_five));
        Words.add(new word("six", "temmokka", R.drawable.number_six,R.raw.number_six));
        Words.add(new word("seven", "kenekaku", R.drawable.number_seven,R.raw.number_seven));
        Words.add(new word("eight", "kawinta", R.drawable.number_eight,R.raw.number_eight));
        Words.add(new word("nine", "wo'e", R.drawable.number_nine,R.raw.number_nine));
        Words.add(new word("ten", "na'aacha", R.drawable.number_ten,R.raw.number_ten));

        WordAdapter adapter = new WordAdapter(this, Words, R.color.category_numbers);

        //Find the {@link ListView} object in the view hierarchy of the {@link Axtivity}.
        //There should be a {@link ListView } with the view ID called list, which is declared in the
        //word_list_xml layout file.
        ListView listView = (ListView) findViewById(R.id.List);

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
                mmMediaPlayer = MediaPlayer.create(NumbersActivity.this,worrd.getmAudioResourceId());
                    //start the audio file
                mmMediaPlayer.start();

                //setup the media on the media player,so that we can stop and release the media player once the sounds has finished playing
                 mmMediaPlayer.setOnCompletionListener(mCompletionListener);
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
            if (mmMediaPlayer !=null){
                mmMediaPlayer.release();

                mmMediaPlayer=null;

                mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
            }
        }
    }