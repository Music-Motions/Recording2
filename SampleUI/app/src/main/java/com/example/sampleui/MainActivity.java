package com.example.sampleui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ImageButton keyC = null;
    private ImageButton keyD = null;
    private ImageButton keyE = null;
    private ImageButton keyF = null;
    private ImageButton keyG = null;

    private Button rec = null;
    private MediaRecorder record;
    private MediaPlayer mp;
    public static final int RECORD_AUDIO = 0;
    private String FILE; //File path
    public static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 0;

    FileOutputStream outputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        FILE = Environment.getExternalStorageDirectory() + "/tempRecord.3gpp";
        Log.d("Suhani", "The place is " + Environment.getExternalStorageDirectory().getAbsolutePath());
        buttonSound();


        rec = (Button)findViewById(R.id.rec);//rec button
        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rec.getText().toString().equals("Record")) {
                    try {
                        Log.d("Suhani", "1 Start Record Method");
                        startRecord();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    rec.setText("End");

                }
                else if (rec.getText().toString().equals("End")) { //Stop Recording
                    Log.d("Suhani", "2 Stop Record Method");
                    stopRecord();
                    rec.setText("Play");

                }
                else if (rec.getText().toString().equals("Play")) {//Playback
                    Log.d("Suhani", "3 Before try catch");
                    try {
                        Log.d("Suhani", "4 Start Playback Method");
                        startPlayback();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("Suhani", e.getMessage());

                    }
                    Log.d("Suhani", "5 End in method");
                    rec.setText("Stop");

                }
                else {//Stop Playback
                    stopPlayback();
                    Log.d("Suhani", "6 Stop Playback Method");
                    rec.setText("Record");
                }

            }

        });


    }

    public void buttonSound(){//assigns sounds for all buttons

        keyC = (ImageButton) this.findViewById(R.id.keyC);
        keyC.setSoundEffectsEnabled(false);

        keyD = (ImageButton) this.findViewById(R.id.keyD);
        keyD.setSoundEffectsEnabled(false);

        keyE = (ImageButton) this.findViewById(R.id.keyE);
        keyE.setSoundEffectsEnabled(false);

        keyF = (ImageButton) this.findViewById(R.id.keyF);
        keyF.setSoundEffectsEnabled(false);

        keyG = (ImageButton) this.findViewById(R.id.keyG);
        keyG.setSoundEffectsEnabled(false);




        keyC.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                mp = MediaPlayer.create(MainActivity.this, R.raw.pianoc);
                mp.start();
                mp.setOnCompletionListener(new OnCompletionListener() {//When sound ends
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();//Releases system resources

                    }
                });
                return true;
            }


        });

        keyD.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                mp = MediaPlayer.create(MainActivity.this, R.raw.pianod);
                mp.start();
                mp.setOnCompletionListener(new OnCompletionListener() {//When sound ends
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();//Releases system resources

                    }
                });
                return true;


            }

        });
        keyE.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                mp = MediaPlayer.create(MainActivity.this, R.raw.pianoe);
                mp.start();
                mp.setOnCompletionListener(new OnCompletionListener() {//When sound ends
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();//Releases system resources

                    }
                });
                return true;


            }

        });
        keyF.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                mp = MediaPlayer.create(MainActivity.this, R.raw.pianof);
                mp.start();
                mp.setOnCompletionListener(new OnCompletionListener() {//When sound ends
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Log.d("Suhani", "Player: MediaPlayer released");
                        mp.release();//Releases system resources

                    }
                });
                return true;


            }

        });
        keyG.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                mp = MediaPlayer.create(MainActivity.this, R.raw.pianog);
                mp.start();
                mp.setOnCompletionListener(new OnCompletionListener() {//When sound ends
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();//Releases system resources

                    }
                });
                return true;


            }

        });


    }

    public void startRecord() throws Exception{//Throws exceptions
        if (record!=null) {
            Log.d("Suhani", "1a Record Stop");
            record.stop();
            Log.d("Suhani", "1b Record Reset");
            record.reset();
            Log.d("Suhani", "1c Record Release");
            record.release();

        }
        File fileOut = new File(FILE);
        if (fileOut!=null) {
            fileOut.delete();//Overwrites existing file before recording
            Log.d("Suhani", "If file is not null");
        }
        record = new MediaRecorder();
        record.setAudioSource(MediaRecorder.AudioSource.MIC);
        record.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        record.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        record.setOutputFile(FILE);//File path

            try{
                record.prepare();
            } catch (IOException e){
                Log.d("Suhani", " prepare() failed");
            }
            record.start();
    }


    public void stopRecord() {
        Log.d("Suhani", "2a Record is stopped");
        record.stop();
        Log.d("Suhani", "2b Record is reset");
        Log.d("Suhani", "2c Record is released");
        record.release();

        record = null;
    }
    public void startPlayback() {

        mp = new MediaPlayer();
        Log.d("Suhani", "4d Before try catch");
        try {
            Log.d("Suhani", "4e MediaPlayer setting data source");
            mp.setDataSource(FILE);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            Log.d("Suhani", "4f In the catch exception  " + e.getMessage());
            e.printStackTrace();
            Log.d("Suhani", "4g setDataSource " + e.getMessage());
        }
        Log.d("Suhani", "4h After try catch to prepare");

        Log.d("Suhani", "4l MediaPlayer is starting");


    }
    public void stopPlayback() {

        mp.release();//Releases system resources
        mp = null;
        Log.d("Suhani", "6c MediaPlayer is released");


    }




}
