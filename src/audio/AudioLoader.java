package audio;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioLoader {
    private static float sfxVolume = 1.0f;
    private static float bgmVolume = 1.0f;
    private static Clip currentBgmClip = null;

    //sfx
    
    public static void playClick() {
        playSFX("audio/click.wav");
    }

    public static void playCorrectAnswer() {
        playSFX("audio/CorrectAnswer.wav");
    }

    public static void playWrongAnswer() {
        playSFX("audio/WrongAnswer.wav");
    }

    public static void playSFX(String path) {
        playSound(path, false, sfxVolume);
    }

    //bgm

    public static void playMainGameBG() {
        playBGM("audio/GameBG.wav");
    }

    public static void playProgrammingMusic() {
        playBGM("audio/ProgrammingQuestionsMusic.wav");
    }

    public static void playGameOver() {
        playBGM("audio/GameOver.wav");
    }

    public static void playBGM(String path) {
        if (currentBgmClip != null && currentBgmClip.isRunning()) {
            currentBgmClip.stop();
            currentBgmClip.close();
        }
        currentBgmClip = playSound(path, true, bgmVolume);
    }

    public static void stopBGM() {
        if (currentBgmClip != null) {
            currentBgmClip.stop();
            currentBgmClip.close();
            currentBgmClip = null;
        }
    }

    //Volume controls

    public static void setSFXVolume(float volume) {
        sfxVolume = Math.max(0.0f, Math.min(1.0f, volume));
    }

    public static void setBGMVolume(float volume) {
        bgmVolume = Math.max(0.0f, Math.min(1.0f, volume));
        if (currentBgmClip != null) {
            setClipVolume(currentBgmClip, bgmVolume);
        }
    }

    //helpers

    private static Clip playSound(String path, boolean loop, float volume) {
        try {
            URL url = AudioLoader.class.getClassLoader().getResource(path);
            if (url != null) {
                AudioInputStream audio = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                
                setClipVolume(clip, volume);

                if (loop) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    clip.start();
                }
                return clip;
            } else {
                System.out.println("MISSING SOUND ASSET: " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void setClipVolume(Clip clip, float volume) {
        try {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if (volume <= 0.0f) {
                gainControl.setValue(gainControl.getMinimum());
            } else {
                float dB = (float) (20.0 * Math.log10(volume));
                gainControl.setValue(Math.max(gainControl.getMinimum(), Math.min(gainControl.getMaximum(), dB)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}