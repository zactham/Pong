import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
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
	private int p1Score = 0;
	private int p2Score = 0;
	private boolean end;

	//	private JFrame restart;
	private JFrame gameOver;
	private JFrame start;

	private InputManager inputManager;

	private final int gameSize = 1000;
	private final int scorex = gameSize/5;
	private final int scorey = gameSize-120;

	public final int paddleWidth = 30;
	public final int paddleHeight = 80;

	public boolean paddle2Collision = false;
	public boolean paddle1Collision = true;

	public final int ballSize = 20;

	private int randomX = 5;
	private int randomY = 5;

	private int runRandomNums = 1;






	// Constructor
	public MainCode()
	{
		setFocusable(true);
		// Register for mouse events on the panel
		addKeyListener(this);
	}


	public void init(int level)
	{
		setPreferredSize(new Dimension(gameSize, gameSize));

		sound = new Sound();
		inputManager = new InputManager();

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

		//+ - moves northeast
		//+ + moves southeast
		//- + moves southwest
		//- - moves northwest


		checkKeys();
		

		if(paddle2Collision == false && paddle1Collision == true)
		{
			//hits the top after paddle 1 hits it
			if(Ball.getBallY() == 0)
			{
				//++
				System.out.println("hit top");
				randomX = randomX;
				randomY = randomY;
				runRandomNums = 1;
			}	

			//hits the bottom after paddle 1 hits it
			if(Ball.getBallY() + ballSize == gameSize)
			{
				//+ -
				System.out.println("hit bottom");
				randomX = randomX;
				randomY =  -randomY;
				runRandomNums = 1;


			}		

			Ball.setBallX(Ball.getBallX() + randomX);
			Ball.setBallY(Ball.getBallY() + randomY);
		}



		paddle2Hit();

		if(paddle2Collision == true && paddle1Collision == false)
		{

			//hits the top after paddle 2 hits it
			if(Ball.getBallY() == 0)
			{
				//-+
				System.out.println("hit top");
				randomX = randomX;
				randomY = -randomY;
				runRandomNums = 1;
			}	

			//hits the bottom after paddle 2 hits it
			if(Ball.getBallY() + ballSize == gameSize)
			{
				//- -
				System.out.println("hit bottom");
				randomX = randomX;
				randomY =  randomY;
				runRandomNums = 1;

			}		
			Ball.setBallX(Ball.getBallX()-randomX);
			Ball.setBallY(Ball.getBallY()-randomY);
		}

		paddle1Hit();

		increaseP1Score();
		increaseP2Score();


		repaint();
	}
	public void randomNums()
	{
		randomX = 0;
		while(randomX < randomY || runRandomNums == 1)
		{
			randomX = (int) (Math.random() * 10) + 3 ; 
			randomY = (int) (Math.random() * 10) + 3 ;  
			runRandomNums = 2;
		}

		System.out.println(randomX);
		System.out.println(randomY);


	}
	public void paddle2Hit()
	{

		if ((Ball.getBallY() >= Paddle2.getPaddleY() && Ball.getBallY() <= Paddle2.getPaddleY() + paddleHeight)  
				&& (Ball.getBallX() + ballSize >= Paddle2.getPaddleX() &&
				Ball.getBallX() + ballSize <= Paddle2.getPaddleX() + paddleWidth))
		{
			randomNums();
			sendBalltoPaddle1 ();

		}

	}

	public void paddle1Hit()
	{

		if ((Ball.getBallY() >= Paddle1.getPaddleY() && Ball.getBallY() <= Paddle1.getPaddleY() + paddleHeight)  
				&& (Ball.getBallX() + ballSize >= Paddle1.getPaddleX() &&
				Ball.getBallX() + ballSize <= Paddle1.getPaddleX() + paddleWidth))
		{
			randomNums();
			sendBalltoPaddle2 ();

		}

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
				"P1 Score: " + p1Score + "P2 Score: " + p2Score + " - Play Again?", 
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
		page.drawString("P1: ", scorex+50, scorey + 60);
		page.drawString(Integer.toString(p1Score), scorex+130, scorey + 60);

		page.drawString("P2: ", scorex+500, scorey + 60);
		page.drawString(Integer.toString(p2Score), scorex+580, scorey + 60);
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

		//Ball
		page.fillRect(Ball.getBallX(), Ball.getBallY(), ballSize, ballSize);



	}

	public void sendBalltoPaddle1 ()
	{
		paddle2Collision = true;
		paddle1Collision = false;
	}



	public void sendBalltoPaddle2 ()
	{
		paddle2Collision = false;
		paddle1Collision = true;
	}

	public void increaseP1Score()
	{

		if(Ball.getBallX()>gameSize - ballSize && (Ball.getBallY() < gameSize - ballSize && Ball.getBallY() > 0 + ballSize)
				|| Ball.getBallX() == 1000 - ballSize && Ball.getBallY() == 0 
				|| Ball.getBallX() == 1000 - ballSize && Ball.getBallY() == 1000 - ballSize)
		{
			p1Score++;
			System.out.println("P1 Score Increased");
			randomX = 5;
			sendBalltoPaddle1();
			Ball.setBallX(500);
			Ball.setBallY(500);

		}
	}

	public void increaseP2Score()
	{

		if(Ball.getBallX()< 0 && (Ball.getBallY() < gameSize - ballSize && Ball.getBallY() > 0 + ballSize)
				|| Ball.getBallX() == 0 && Ball.getBallY() == 0
				|| Ball.getBallX() == 0 && Ball.getBallY() == 1000 - ballSize)
		{
			p2Score++;
			System.out.println("P2 Score Increased");
			randomX = 5;
			sendBalltoPaddle2 ();
			Ball.setBallX(500);
			Ball.setBallY(500);


		}

	}





	public void checkKeys()
	{
		//Pressing the keys
		if (inputManager.getKeyPressed(KeyEvent.VK_UP)==true)
		{
			Paddle2.setPaddleY(Paddle2.getPaddleY()-10);
		}
		else if (inputManager.getKeyPressed(KeyEvent.VK_DOWN)==true) 
		{
			Paddle2.setPaddleY(Paddle2.getPaddleY()+10);
		}
		else if (inputManager.getKeyPressed(KeyEvent.VK_Q)==true) 
		{
			Paddle1.setPaddleY(Paddle1.getPaddleY()-10);
		}

		else if(inputManager.getKeyPressed(KeyEvent.VK_A))
		{
			Paddle1.setPaddleY(Paddle1.getPaddleY()+10);

		}


	}


	public void keyPressed(KeyEvent arg0) 
	{
		int c = arg0.getKeyCode();
		inputManager.setKeyPressed(c, true);
	}

	public void keyReleased(KeyEvent arg0) 
	{
		int c = arg0.getKeyCode();
		inputManager.setKeyPressed(c, false);
	}



	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}

