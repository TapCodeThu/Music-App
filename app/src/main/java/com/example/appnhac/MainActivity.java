package com.example.appnhac;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.Annotation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView tenbai, time_start, time_end;
    ImageButton buttonpre, buttonplay, buttonstop, buttonnext;
    SeekBar seekBar;
    ArrayList<Song> arraySong;
    int position = 0;
    MediaPlayer mediaPlayer;
    Animation animation;
    ImageView imageViewdia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        AddSong();
        animation = AnimationUtils.loadAnimation(this,R.anim.disk_rotale);
        KhoitaoMediaPlayer();



        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if(position > arraySong.size() - 1)
                {
                    position = 0;
                }
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                    KhoitaoMediaPlayer();
                    mediaPlayer.start();
                buttonplay.setImageResource(R.drawable.ic_pause);
                SetTimeTotal();
                UpdateTimeSong();

            }
        });
        buttonpre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if(position <0)
                {
                    position = arraySong.size() - 1;
                }
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                KhoitaoMediaPlayer();
                mediaPlayer.start();
                buttonplay.setImageResource(R.drawable.ic_pause);
                SetTimeTotal();
                UpdateTimeSong();

            }
        });

        buttonstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                buttonplay.setImageResource(R.drawable.ic_play);
                KhoitaoMediaPlayer();
            }
        });
        buttonplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying())
                {
                    //neu dang phat thi pause va doi hinh
                    mediaPlayer.pause();
                    buttonplay.setImageResource(R.drawable.ic_play);
                }
                else
                {
                    //neu dang dung thi phat va doi hinh
                    mediaPlayer.start();
                    buttonplay.setImageResource(R.drawable.ic_pause);
                }
                SetTimeTotal();
                UpdateTimeSong();
                imageViewdia.startAnimation(animation);

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });
    }
    private void UpdateTimeSong()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                time_start.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                //updat progress
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if(position > arraySong.size() - 1)
                        {
                            position = 0;
                        }
                        if(mediaPlayer.isPlaying())
                        {
                            mediaPlayer.stop();
                        }
                        KhoitaoMediaPlayer();
                        mediaPlayer.start();
                        buttonplay.setImageResource(R.drawable.ic_pause);
                        SetTimeTotal();
                        UpdateTimeSong();
                    }
                });
                handler.postDelayed(this,500);

            }
        },100);
    }
    private void SetTimeTotal()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        time_end.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
    }
    private void KhoitaoMediaPlayer()
    {
        mediaPlayer = MediaPlayer.create(MainActivity.this,arraySong.get(position).getFile());

        tenbai.setText(arraySong.get(position).getTitle());
    }
    private void AddSong()
    {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Con Bướm Xuân", R.raw.con_buom_xuan));
        arraySong.add(new Song("Vợ Người ta", R.raw.vo_nguoi_ta));
        arraySong.add(new Song("Vọng Kim Lang", R.raw.vong_kim_lang));
        arraySong.add(new Song("Điều Anh Biết", R.raw.dieu_anh_biet));
        arraySong.add(new Song("Người Yêu Cũ", R.raw.nguoi_yeu_cu));
    }
    private void AnhXa()
    {
        tenbai = (TextView) findViewById(R.id.textviewtenbai);
        time_start = (TextView) findViewById(R.id.id_timestart);
        time_end = (TextView) findViewById(R.id.id_timesend);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        buttonpre = (ImageButton) findViewById(R.id.id_buttonpre);
        buttonplay = (ImageButton) findViewById(R.id.id_buttonplay);
        buttonstop = (ImageButton) findViewById(R.id.id_buttonstop);
        buttonnext= (ImageButton) findViewById(R.id.id_buttonnext);
        imageViewdia = (ImageView) findViewById(R.id.id_dvd);

    }
}