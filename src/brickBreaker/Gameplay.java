package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer time;//time of ball how fast it should move 
	private int delay = 10;//speed for a ball
	
	private int playerX = 310;
	
	private int ballposX = 120;
	private int ballposY = 350;
	private double ballXdir = -1;
	private double ballYdir = -3;
	
	private MapGen map;
	
	public Gameplay() {
		
		map = new MapGen(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false); 
		time = new Timer(delay, this);	
		time.start();
	}
	
	public void paint(Graphics g) {
		//background 
		g.setColor(Color.black);
		g.fillRect(1,1, 692, 592);
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(692, 0, 3, 592);
		
		//score
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+score+"", 650, 30);
		
		//game over
		if(ballposY>570) {
			play = false;
			
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 40));
			g.drawString("GAME OVER", 238, 250);
			
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Press enter to RESTART", 200, 300);
			
		}
		
		//win
		if(totalBricks<=0) {
			play = false;
			
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 40));
			g.drawString("YOU WON", 238, 250);
		
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Press enter to RESTART", 200, 300);
			
			
		}
		
		//drawing map
		map.draw((Graphics2D)g);
		
		//panel
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		//ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		time.start();
		//ball movement
		if(play) {
			int x = setX(ballposX, ballposY, playerX);
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				//ball strike back
				
				ballYdir = -ballYdir;
				ballXdir = setAngle(ballposX-playerX+10);
			}
				
		 	
			A:for(int i = 0; i<map.map.length;i++) {
				for(int j = 0; j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int brickX = j*map.brickWidth+80;
						int brickY = i*map.brickHeight+50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if (ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score+=5;
							
							if(ballposX+19 <= brickRect.x || ballposX +1 >= brickRect.x + brickWidth) {
								ballXdir = -ballXdir;
							}
							
							else {
								ballYdir = -ballYdir;
							}
							break A;	
						}
					}
				}
			}
			
			ballposX+=ballXdir;
			ballposY+=ballYdir;
			
			if(ballposX<0) {
				ballXdir = - ballXdir; //left
			}
			if(ballposY<0) {
				ballYdir = - ballYdir; //top
			}
			if(ballposX>670) {
				ballXdir = - ballXdir; //right
			}
			
		}
		
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(playerX>=600) {
				playerX=600;//don't let to leave the panel
			}
			else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(playerX<10) {
				playerX=10;//don't let to leave the panel
			}
			else {
				moveLeft();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGen(3, 7);
			}
		}
	}

	public void moveRight() {
		play = true;
		playerX += 20;
	}
	
	public void moveLeft() {
		play = true;
		playerX -= 20;
	}
	
	public double setAngle(int x) {
		int ballX;
		
			ballX = -4;
		if(x>20) {
			ballX = -2;
		}
		if(x>40) {
			ballX= -1;
		}
		if (x>50) {
			ballX= 1;
		}
		if (x>60) {
			ballX= 2;
		}
		if (x>80){
			ballX= 4;
		}
		System.out.println(ballX+"  "+x);
		
		
		return ballX;
	}
	
	public int setX(int ballX, int ballY, int PlayerX) {
		int x = 100;
		
		return x;
	}
}
