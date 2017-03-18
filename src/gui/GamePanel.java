package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import gameLogic.GameConfig;
import gameLogic.GameLogic;
import gameLogic.Level;

public class GamePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean showBackground = true;
	
	private Image background;
	private Image hero;
	private GameLogic game;
	
	public GamePanel() {
		loadImages();
	}
	
	
	private void loadImages(){
		ImageIcon temp;
		temp = new ImageIcon(this.getClass().getResource("res/background.png"));
		background = temp.getImage();

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.clearRect(0, 0, getWidth(), getHeight());
		Graphics2D g2d = (Graphics2D) g;
		
		if(showBackground){
		g2d.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), 0, 0, 
				background.getWidth(null), background.getHeight(null), null);
		}
		else{
			drawGame(g2d);
		}
			
			
		
	}
	
	
	public void drawGame(Graphics g2d){
		g2d.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), 0, 0, 
				background.getWidth(null), background.getHeight(null), null);
		
	}
	
	public void startNewGame(GameConfig gameConfig) {
		this.game = new GameLogic(new Level(1), gameConfig);
		showBackground = false;
		repaint();
		requestFocus();
	}


	public boolean isShowBackground() {
		return showBackground;
	}


	public void setShowBackground(boolean showBackground) {
		this.showBackground = showBackground;
	}
	
	
	
	
	
}
