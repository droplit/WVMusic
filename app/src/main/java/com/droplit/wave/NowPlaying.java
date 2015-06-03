package com.droplit.wave;

import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.SeekBar;
import com.android.custom.widget.waveformseekbar.WaveformSeekBar;

import java.io.IOException;


public class NowPlaying extends ActionBarActivity {


    private static final int UPDATE_FREQUENCY_MS = 5;
    private WaveformSeekBar seekbar = null;
    private MediaPlayer player = null;
    private FloatingActionButton playButton = null;
    private boolean isStarted = false;
    private boolean isMoveingSeekBar = false;
    private final Handler handler = new Handler();
    private final Runnable updatePositionRunnable = new Runnable() {
        public void run() {
            updatePosition();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        toolbar.setTitle("Now Playing");

        playButton = (FloatingActionButton) findViewById(R.id.fab_play);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (player.isPlaying()) {
                    handler.removeCallbacks(updatePositionRunnable);
                    player.pause();
                    playButton.setImageResource(android.R.drawable.ic_media_play);
                } else {
                    if (isStarted) {
                        player.start();
                        playButton.setImageResource(android.R.drawable.ic_media_pause);

                        updatePosition();
                    } else {
                        startPlay();
                    }
                }
            }
        });

        player = new MediaPlayer();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlay();
            }
        });
        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
                // returning false will call the OnCompletionListener
            }
        });


        seekbar = (WaveformSeekBar) findViewById(R.id.seekbar);
        try {
            seekbar.setAudio(this.getAssets().open("razorsharp.wav"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isMoveingSeekBar = false;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isMoveingSeekBar = true;
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (isMoveingSeekBar) {
                    player.seekTo(progress);
                }
            }
        });

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        if (null != cursor) {
            cursor.moveToFirst();
        }

    }


    private void startPlay() {
        seekbar.setProgress(0);
        player.stop();
        player.reset();

        AssetFileDescriptor afd = null;
        try {
            afd = getAssets().openFd("razorsharp.wav");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            player.prepare();
            player.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        seekbar.setMax(player.getDuration());
        playButton.setImageResource(android.R.drawable.ic_media_pause);

        updatePosition();

        isStarted = true;
    }


    private void stopPlay() {
        player.stop();
        player.reset();
        playButton.setImageResource(android.R.drawable.ic_media_play);
        handler.removeCallbacks(updatePositionRunnable);
        seekbar.setProgress(0);

        isStarted = false;
    }


    private void updatePosition() {
        handler.removeCallbacks(updatePositionRunnable);

        seekbar.setProgress(player.getCurrentPosition());

        handler.postDelayed(updatePositionRunnable, UPDATE_FREQUENCY_MS);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_now_playing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
