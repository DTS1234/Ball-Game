package brickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame obj = new JFrame();
		
		Gameplay gameplay = new Gameplay();
		
		obj.setBounds(10, 10, 710, 600);
		obj.setTitle("BallGame");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		obj.add(gameplay);
	}

}
