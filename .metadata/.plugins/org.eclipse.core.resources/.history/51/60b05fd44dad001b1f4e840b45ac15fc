package com.AMT.util;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PlaySound {
	

	public static synchronized Thread playSound(final File file) {
		Thread soundThread = new Thread() {
			@Override
			public void run() {
				try {
					Clip clip = null;
					AudioInputStream inputStream = null;
					clip = AudioSystem.getClip();
					inputStream = AudioSystem.getAudioInputStream(file);
					AudioFormat format = inputStream.getFormat();
					long audioFileLength = file.length();
					int frameSize = format.getFrameSize();
					float frameRate = format.getFrameRate();
					long durationInMiliSeconds = (long) (((float) audioFileLength / (frameSize * frameRate)) * 1000);

					clip.open(inputStream);
					clip.start();

					Thread.sleep(durationInMiliSeconds);

					clip.stop();

					clip.close();
					inputStream.close();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					System.out.println(
							"" + "Music has stopped");
				}
			}
		};
		soundThread.setDaemon(true);
		soundThread.start();
		return soundThread;

	}

}