import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;


public class Sound
{
	private Clip audioClip;

	public Clip getClip() 		
	{
		return audioClip; 
	}
	public boolean isPlaying() 
	{ 
		return audioClip.isRunning(); 
	}
	public void stop() 			
	{ 
		audioClip.stop(); 
	}
	public void resume() 		
	{
		audioClip.start(); 
	}

	public void play(String audioFilePath)
	{
		try
		{
			File audioFile = new File(audioFilePath);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			AudioFormat format = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.open(audioStream);
			audioClip.start();
		}

		catch (Exception err)
		{
			System.out.println("1. " + err);
		}
	}
	
	public void toggle()
	{
		if (isPlaying())
		{
			stop();
		}
		else
		{
			resume();
		}
	}

}

