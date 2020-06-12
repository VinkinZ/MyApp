package com.sinano.rfidrccs.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;


/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/6/1 16:01
 * Description: 语音播放
 */
public class VoicePlayer {
    public boolean notPlayed = true;

    public VoicePlayer() {
    }

    public static void playVoice(Context context, int res) {
        final MediaPlayer mediaPlayer = MediaPlayer.create(context, res);
        if (null != mediaPlayer) {
            try {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                });
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } finally {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }
    }

    public void playVoiceOnce (Handler handler, int msg) {
        if (notPlayed) {
            handler.sendEmptyMessageDelayed(msg, 2000);
            notPlayed = false;
        }
    }
}
