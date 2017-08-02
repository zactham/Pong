import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import javax.swing.*;

//PONG
public class MainCode extends JPanel implements KeyListener
{
	Sound sound;
	private int score = 0;
	private boolean end;

	//	private JFrame restart;
	private JFrame gameOver;
	private JFrame start;

	private final int gameSize = 1000;
	private final int scorex = gameSize/5;
	private final int scorey = gameSize-120;

	public final int paddleWidth = 30;
	public final int paddleHeight = 80;



	// Constructor
	public MainCode()
	{
		setFocusable(true);
		// Register for mouse events on the panel
		addKeyListener(this);
	}


	public void init(int level)
	{
		sound = new Sound();

		// launch game
		JFrame frame = new JFrame("Sample Frame");
		frame.add(this);
		frame.setTitle("Game Title");
		setBackground(Color.black);

		JOptionPane.showMessageDialog(start, "Game Instructions");

		//Sets the speed of the game for each mode
		if (level == 1)		// easy
		{

		}

		if (level == 2)		// medium
		{

		}

		if (level == 3)		// hard
		{

		}


		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		centerWindow();
		frame.setSize(gameSize, gameSize);
		frame.setLocationRelativeTo(TitleScreen.theApp);

		// runs the mainLoop
		ActionListener timerAction = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MainLoop();

			}

		};

		// Frame rate, updates the frame every 15ms --- 60fps
		Timer timer = new Timer(15, timerAction);
		timer.setRepeats(true);
		timer.start();


	}

	public void MainLoop()
	{
		// updateGame();
		repaint();
	}




	public void playSoundEffect()
	{
		//sound.play("SMACK Sound Effect.wav");
	}

	// Centers the window
	public void centerWindow()
	{
		// gets top level window
		Window window;
		Container c = getParent();
		while (c.getParent() != null)
			c = c.getParent();

		// center window
		if (c instanceof Window)// if it is the top window...
		{
			// centers it
			window = (Window) c;
			window.pack();
			window.setLocationRelativeTo(null);
		}
	}

	//
	//When the game ends
	//
	public void gameEnding()
	{

		sound.stop();

		int result = JOptionPane.showConfirmDialog(this, 
				"Your Score: " + score + " - Play Again?", 
				"Game Over", JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.NO_OPTION)
		{
			// no
			System.exit(0);
		}
		else
		{
			// yes, play again
			resetGame();
		}
	}

	private void resetGame()
	{

	}

	public void displayScore(Graphics page)
	{
		//Displays the Score
		page.setColor(Color.white);
		page.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
		page.drawString("SCORE: ", scorex+100, scorey);
		page.drawString(Integer.toString(score), scorex+330, scorey);
	}

	@Override
	protected void paintComponent(Graphics page)
	{
		super.paintComponent(page);		// paint baseclass members too

		// drawGame(page);
		displayScore(page);

		//Line down the middle
		page.setColor(Color.white);
		page.fillRect(gameSize/2, 0, 10, gameSize);

		//Paddle1
		page.fillRect(Paddle1.getPaddleX(), Paddle1.getPaddleY(), paddleWidth, paddleHeight);

		//Paddle2
		page.fillRect(Paddle2.getPaddleX(), Paddle2.getPaddleY(), paddleWidth, paddleHeight);


	}

	public int getScore()
	{
		return score;
	}




	public void keyPressed(KeyEvent arg0) 
	{
		int c = arg0.getKeyCode();

		//Pressing the keys
		if (c == KeyEvent.VK_UP)
		{
			
			Paddle2.setPaddleY(Paddle2.getPaddleY()-10);
			
			
		}

		if (c == KeyEvent.VK_DOWN)
		{
			Paddle2.setPaddleY(Paddle2.getPaddleY()+10);
			
		}

		if (c == KeyEvent.VK_Q)
		{
			Paddle1.setPaddleY(Paddle1.getPaddleY()-10);
			
		}

		if (c == KeyEvent.VK_A)
		{
			Paddle1.setPaddleY(Paddle1.getPaddleY()+10);
			
		}
	}



	public void keyReleased(KeyEvent arg0) 
	{
		int c = arg0.getKeyCode();

		//When S is pressed the music stops
		if (c == KeyEvent.VK_S) 
		{
			sound.toggle();
		}

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}

