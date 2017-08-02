import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class TitleScreen extends JApplet
{
	private Sound sound;
	private boolean easy = true;
	private boolean med = false;
	private boolean hard = false;
	public static TitleScreen theApp;
	
	private MainCode board;

	private ImageIcon titleScreenImage;//image


	//All buttons

	//Image button
	public JButton 	titleScButton;

	//Main Buttons
	private JFrame help;

	public void init () 
	{		
		sound = new Sound();
		theApp = this;
		playMusic();


		//Adds the image and creates a button out of it
		titleScreenImage = new ImageIcon(this.getClass().getResource("TSImage.jpg"));//image	
		titleScButton = new JButton (titleScreenImage);//image button
		getContentPane().add(titleScButton);
		setSize(2304,1296);
		centerWindow();//centers the window


		titleScButton.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent arg0) 
			{
				
				int c = arg0.getKeyCode();

				//Pressing the keys 1 2 3 on the num pad on the right side of the keyboard
				if (c == KeyEvent.VK_E || c == KeyEvent.VK_ENTER)
				{
					//System.out.println("Easy");

					easy = true;
					sound.stop();
					addMainBoard();					
				}
				
				
			}
			
			public void keyReleased(KeyEvent arg0)
			{
				int c = arg0.getKeyCode();
				
				if (c == KeyEvent.VK_S) 
				{
					sound.toggle();
				}
			}
		});


		//Based on where they click in easy medium or hard something happens
		titleScButton.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
			}

			public void mouseEntered(MouseEvent arg0) {

			}

			public void mouseExited(MouseEvent arg0) {

			}

			public void mousePressed(MouseEvent arg0) {

			}

			public void mouseReleased(MouseEvent arg0) {

			}
		});
	}


	public void addMainBoard()
	{
		hideWindow();
		
		int level = 1;
		if (med)
			level = 2;
		else
			if (hard)
				level = 3;
		
		board = new MainCode();
		board.init(level);
	}


	public void playMusic()
	{
		sound.play("TitleScreenMusic.wav");
	}

	public void hideWindow()
	{
		Container c = getParent();
		while (c.getParent()!=null) 
			c = c.getParent();
		c.setVisible(false);		
	}
	
	//Centers the window
	public void centerWindow()
	{
		//gets top level window
		Window window;
		Container c = getParent();
		while (c.getParent()!=null) 
			c = c.getParent();

		// center window
		if (c instanceof Window)//if it is the top window...
		{
			//centers it
			window = (Window)c;
			window.pack();
			window.setLocationRelativeTo(null);					
		}
	}

}

