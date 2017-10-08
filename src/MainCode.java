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
	//	private JFrame restart;
	private JFrame start;

	private InputManager inputManager;

	private int p1Score = 0;
	private int p2Score = 0;

	private Paddle paddle1 = new Paddle();
	private Paddle paddle2 = new Paddle();

	private int paddleSpeed = 10;

	private Ball ball = new Ball();

	public final int gameSize = 500;
	private final int scorex = gameSize/5;
	private final int scorey = gameSize-120;

	public boolean paddle2Collision = false;
	public boolean paddle1Collision = true;

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
		frame.setTitle("PONG");
		setBackground(Color.black);

		JOptionPane.showMessageDialog(start, "Player 1: Use Q and A\t Player 2: Use the Up and Down Keys");

		paddle1.setPaddleWidth(gameSize/33);
		paddle2.setPaddleWidth(gameSize/33);

		paddle1.setPaddleHeight(gameSize/13);
		paddle2.setPaddleHeight(gameSize/13);

		paddle1.setPaddleX(gameSize/17);
		paddle1.setPaddleY(gameSize-100);
		paddle2.setPaddleX(gameSize-100);
		paddle2.setPaddleY(gameSize-100);

		ball.setBallSize(gameSize/50);

		ball.setBallX(gameSize/2);
		ball.setBallY(gameSize/2);

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

		checkKeys();

		paddle1Hit();

		if(paddle2Collision == false && paddle1Collision == true)
		{
			//hits the top after paddle 1 hits it
			if(ball.getBallY() <= 0)
			{
				//System.out.println("hit top");
				playhitWall();
				randomY = -randomY;
				runRandomNums = 1;
			}	

			//hits the bottom after paddle 1 hits it
			if(ball.getBallY() + ball.getBallSize() >= gameSize)
			{

				//System.out.println("hit bottom");
				playhitWall();
				randomY = -randomY;
				runRandomNums = 1;
			}		

			ball.setBallX(ball.getBallX() + randomX);
			ball.setBallY(ball.getBallY() + randomY);
		}



		paddle2Hit();

		if(paddle2Collision == true && paddle1Collision == false)
		{

			//hits the top after paddle 2 hits it
			if(ball.getBallY() <= 0)
			{
				//System.out.println("hit top");
				playhitWall();
				randomY =-randomY;
				runRandomNums = 1;
			}	

			//hits the bottom after paddle 2 hits it
			if(ball.getBallY() + ball.getBallSize() >= gameSize)
			{
				//System.out.println("hit bottom");
				playhitWall();
				randomY = Math.abs(randomY);
				runRandomNums = 1;
			}		
			ball.setBallX(ball.getBallX()-randomX);
			ball.setBallY(ball.getBallY()-randomY);
		}

		increaseP1Score();
		increaseP2Score();

		check10Score();


		repaint();
	}
	public void randomNums()
	{
		randomX = 0;

		if (gameSize == 1000)
		{
			while(randomX < randomY || runRandomNums == 1)
			{
				randomX = (int) (Math.random() * 10) + 3 ; 
				randomY = (int) (Math.random() * 10) + 3 ;  
				runRandomNums = 2;
			}
		}
		//TODO change based on gamesize
		if (gameSize == 500)
		{
			while(randomX < randomY || runRandomNums == 1)
			{
				randomX = (int) (Math.random() * 6) + 3 ; 
				randomY = (int) (Math.random() * 6) + 3 ;  
				runRandomNums = 2;
			}
		}

		//System.out.println(randomX);
		//System.out.println(randomY);


	}
	public void paddle2Hit()
	{

		if ((ball.getBallY() >= paddle2.getPaddleY() && ball.getBallY() <= paddle2.getPaddleY() + paddle2.getPaddleHeight())  
				&& (ball.getBallX() + ball.getBallSize() >= paddle2.getPaddleX() &&
				ball.getBallX() + ball.getBallSize() <= paddle2.getPaddleX() + paddle2.getPaddleWidth()))
		{
			playhitPaddle();
			randomNums();
			sendBalltoPaddle1 ();

		}

	}

	public void paddle1Hit()
	{

		if ((ball.getBallY() >= paddle1.getPaddleY() && ball.getBallY() <= paddle1.getPaddleY() + paddle1.getPaddleHeight())  
				&& (ball.getBallX() + ball.getBallSize() >= paddle1.getPaddleX() &&
				ball.getBallX() + ball.getBallSize()<= paddle1.getPaddleX() + paddle1.getPaddleWidth()))
		{
			playhitPaddle();
			randomNums();
			sendBalltoPaddle2 ();

		}

	}


	public void playhitWall()
	{
		sound.play("wallhit.wav");
	}

	public void playhitPaddle()
	{
		sound.play("paddlehit.wav");
	}

	public void playscoreincrease()
	{
		sound.play("scoreIncreased.wav");
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

		//sound.stop();

		int result = JOptionPane.showConfirmDialog(this, 
				"P1 Score: " + p1Score + " & " + "P2 Score: " + p2Score + " - Play Again?", 
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
		p1Score = 0;
		p2Score = 0;

		paddle2Collision = false;
		paddle1Collision = true;

		randomX = 5;
		randomY = 5;

		runRandomNums = 1;

		MainLoop();
	}

	public void displayScore(Graphics page)
	{
		//Displays the Score
		page.setColor(Color.white);
		page.setFont(new Font("Comic Sans MS", Font.PLAIN, gameSize/20));
		page.drawString("P1: ", scorex+gameSize/20, scorey + gameSize/17);
		page.drawString(Integer.toString(p1Score), scorex+gameSize/9, scorey + gameSize/17);

		//TODO
		//That scorex+gameSize/9 sometimes needs to be changed

		page.drawString("P2: ", scorex+gameSize/2, scorey + gameSize/17);
		page.drawString(Integer.toString(p2Score), scorex+gameSize/2 + 40, scorey + gameSize/17);
		//Do not change that 70

	}

	public void check10Score()
	{
		if (p1Score >9 || p2Score > 9)
			gameEnding();
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

		paddle1.draw(page);
		paddle2.draw(page);

		ball.draw(page);





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

		if(ball.getBallX()>gameSize - ball.getBallSize() && (ball.getBallY() < gameSize - ball.getBallSize() && ball.getBallY() > 0 + ball.getBallSize())
				|| ball.getBallX() == gameSize - ball.getBallSize() && ball.getBallY() == 0 
				|| ball.getBallX() == gameSize - ball.getBallSize() && ball.getBallY() == gameSize - ball.getBallSize())
		{
			p1Score++;
			playscoreincrease();
			//System.out.println("P1 Score Increased");
			randomX = 5;
			sendBalltoPaddle1();
			ball.setBallX(gameSize/2);
			ball.setBallY(gameSize/2);

		}
	}

	public void increaseP2Score()
	{

		if(ball.getBallX()< 0 && (ball.getBallY() < gameSize - ball.getBallSize() && ball.getBallY() > 0 + ball.getBallSize())
				|| ball.getBallX() == 0 && ball.getBallY() == 0
				|| ball.getBallX() == 0 && ball.getBallY() == gameSize - ball.getBallSize())
		{
			p2Score++;
			playscoreincrease();
			//	System.out.println("P2 Score Increased");
			randomX = 5;
			sendBalltoPaddle2 ();
			ball.setBallX(gameSize/2);
			ball.setBallY(gameSize/2);


		}

	}





	public void checkKeys()
	{
		//Pressing the keys
		if (inputManager.getKeyPressed(KeyEvent.VK_UP)==true 
				&& paddle2.getPaddleY() > 0)
		{
			paddle2.setPaddleY(paddle2.getPaddleY()-paddleSpeed);
		}
		else if (inputManager.getKeyPressed(KeyEvent.VK_DOWN)==true 
				&& paddle2.getPaddleY() + paddle2.getPaddleHeight() < gameSize)
		{
			paddle2.setPaddleY(paddle2.getPaddleY()+paddleSpeed);
		}


		if (inputManager.getKeyPressed(KeyEvent.VK_Q)==true
				&& paddle1.getPaddleY() > 0)
		{
			paddle1.setPaddleY(paddle1.getPaddleY()-paddleSpeed);
		}

		else if(inputManager.getKeyPressed(KeyEvent.VK_A)
				&& paddle1.getPaddleY() + paddle1.getPaddleHeight() < gameSize)
		{
			paddle1.setPaddleY(paddle1.getPaddleY()+paddleSpeed);

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

