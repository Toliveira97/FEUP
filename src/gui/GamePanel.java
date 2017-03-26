package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sun.rowset.internal.Row;

import gameLogic.Coord;
import gameLogic.CrazyOgre;
import gameLogic.Direction;
import gameLogic.GameConfig;
import gameLogic.GameLogic;
import gameLogic.Guard;
import gameLogic.Hero;
import gameLogic.Level;
import gameLogic.Rookie;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int numberOfLevels = 5;
	private static final int customLevel = 6;

	private boolean showBackground = true;
	private boolean customMap = false;
	
	private Image background;
	private Image wall;
	private Image door;
	private Image openDoor;
	private Image hero;
	private Image heroWithWeapon;
	private Image heroWithKey;
	private Image guard;
	private Image guardSleeping;
	private Image ogre;
	private Image ogreSleeping;
	private Image club;
	private Image key;
	private Image lever;
	private Image leverActivated;
	
	
	private GameFrame gameFrame;
	private GameLogic game;
	private GameConfig gameConfig;
	
	private int charactersWidth;
	private int charactersHeight;
	private int level;
	
	private Coord mouseCell;
	private char charSelected = ' ';
	private boolean haveHero = false;
	private boolean haveGuard = false;
	private int levers = 0;
	private int keys = 0;
	private int ogres = 0;
	
	
	public GamePanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		this.mouseCell = new Coord(0,0);
		MyMouseAdapter mouseAdapter = new MyMouseAdapter();
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		addKeyListener(new MyKeyAdapter());
		setFocusable(true);
		setDoubleBuffered(true);
		loadImages();
	}
	
	
	private void loadImages(){
		ImageIcon temp;
		temp = new ImageIcon(this.getClass().getResource("res/background.png"));
		background = temp.getImage();
		
		temp = new ImageIcon(this.getClass().getResource("res/wall.png"));
		wall = temp.getImage();
		
		temp = new ImageIcon(this.getClass().getResource("res/door.png"));
		door = temp.getImage();
		
		temp = new ImageIcon(this.getClass().getResource("res/openDoor.png"));
		openDoor = temp.getImage();
		
		temp = new ImageIcon(this.getClass().getResource("res/hero.png"));
		hero = temp.getImage();
		
		temp = new ImageIcon(this.getClass().getResource("res/heroWithWeapon.png"));
		heroWithWeapon = temp.getImage();
		
		temp = new ImageIcon(this.getClass().getResource("res/heroWithKey.png"));
		heroWithKey = temp.getImage();
		
		temp = new ImageIcon(this.getClass().getResource("res/guard.png"));
		guard = temp.getImage();
		
		temp = new ImageIcon(this.getClass().getResource("res/guardSleeping.png"));
		guardSleeping = temp.getImage();
		
		temp = new ImageIcon(this.getClass().getResource("res/ogre.png"));
		ogre = temp.getImage();
		
		temp = new ImageIcon(this.getClass().getResource("res/ogreSleeping.png"));
		ogreSleeping = temp.getImage();
		
		temp = new ImageIcon(this.getClass().getResource("res/club.png"));
		club = temp.getImage();

		temp = new ImageIcon(this.getClass().getResource("res/key.png"));
		key = temp.getImage();
		
		temp = new ImageIcon(this.getClass().getResource("res/lever1.png"));
		lever = temp.getImage();
		
		temp = new ImageIcon(this.getClass().getResource("res/lever2.png"));
		leverActivated = temp.getImage();


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
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, getWidth(), getHeight());
			drawGame(g2d);
		}
		
	}
	
	public void drawGame(Graphics g2d){
		drawHero(g2d);
		drawGuard(g2d);
		drawOgres(g2d);
		drawMaze(g2d);
		
		if(customMap)
			drawPiecesToChoose(g2d);
	}
	
	
	private void drawCharacter(Image img, Graphics g2d, int i, int j){ 
		int distX = j * charactersWidth;
		int distY = i * charactersHeight;
		
		distX += (getWidth() - charactersWidth * game.getBoard().getColumns()) / 2.0;
		distY += (getHeight() - charactersHeight * game.getBoard().getRows()) / 2.0;

		g2d.drawImage(img, distX, distY, distX + charactersWidth, distY + charactersHeight, 0,
				0, img.getWidth(null), img.getHeight(null), null);
		
	}
	
	private void drawCharacter(Image img, Graphics g2d, int i, int j, Direction orientation){
		
		int distX = j * charactersWidth;
		int distY = i * charactersHeight;
		
		distX += (getWidth() - charactersWidth * game.getBoard().getColumns()) / 2.0;
		distY += (getHeight() - charactersHeight * game.getBoard().getRows()) / 2.0;
		
		double rotationRequired;

		switch (orientation) {
		case RIGHT:
			rotationRequired = Math.toRadians(0);
			break;
		case LEFT:
			rotationRequired = Math.toRadians(180);
			break;
		case UP:
			rotationRequired = Math.toRadians(270);
			break;
		case DOWN:
			rotationRequired = Math.toRadians(90);
			break;
		default:
			rotationRequired = 0;
			break;
		}

		BufferedImage bimage = toBufferedImage(img);
		double locationX = img.getWidth(null)/2;
		double locationY = img.getHeight(null)/2;
		
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

		g2d.drawImage(op.filter(bimage, null), distX, distY, distX + charactersWidth, distY + charactersHeight, 0,
				0, bimage.getWidth(null), bimage.getHeight(null), null);
		
	}
	
	
	private void drawHero(Graphics g2d){
		
		Hero herocp = game.getHero();
		
		if(herocp.isGotKey())
			drawCharacter(heroWithKey, g2d, herocp.getPosition().getX(), herocp.getPosition().getY(),  herocp.getOrientation());
		else if(herocp.isArmed())
			drawCharacter(heroWithWeapon, g2d, herocp.getPosition().getX(), herocp.getPosition().getY(),  herocp.getOrientation());
		else
			drawCharacter(hero, g2d, herocp.getPosition().getX(), herocp.getPosition().getY(),  herocp.getOrientation());

	}
	
	private void drawGuard(Graphics g2d){

		Guard guardcp = game.getGuard();

		if(guardcp.getPosition() != null){

			if(guardcp.isSleeping())
				drawCharacter(guardSleeping, g2d, guardcp.getPosition().getX(), guardcp.getPosition().getY(), guardcp.getOrientation());
			else
				drawCharacter(guard, g2d, guardcp.getPosition().getX(), guardcp.getPosition().getY(), guardcp.getOrientation());
		}

	}

	
	private void drawOgres(Graphics g2d){
		
		ArrayList<CrazyOgre> temp = game.getCrazyOgres();

		if(!temp.isEmpty()){

			for(int i = 0; i < temp.size(); i++){
				CrazyOgre temp_ogre = temp.get(i);
				if(temp_ogre.isStunned())
					drawCharacter(ogreSleeping, g2d, temp_ogre.getPosition().getX(),temp_ogre.getPosition().getY(), temp_ogre.getOrientation());
				else
					drawCharacter(ogre, g2d, temp_ogre.getPosition().getX(),temp_ogre.getPosition().getY(), temp_ogre.getOrientation());
				if(temp_ogre.isArmed())
					drawCharacter(club, g2d, temp_ogre.getWeaponLocation().getX(),temp_ogre.getWeaponLocation().getY(), temp_ogre.getWeaponOrientation());
			}
		}
	}
	
	
	private void drawMaze(Graphics g2d){

		for(int i = 0; i < game.getBoard().getRows(); i++){
			for(int j = 0; j < game.getBoard().getColumns(); j++){
				
				if(game.getBoard().getBoardAt(i, j) == 'X')
					drawCharacter(wall, g2d, i,j);	
				
				else if(game.getBoard().getBoardAt(i, j) == 'I')
					drawCharacter(door, g2d, i,j);	
								
				else if(game.getBoard().getBoardAt(i, j) == 'S')
					drawCharacter(openDoor, g2d, i,j);	
				
				else if (game.getBoard().getBoardAt(i, j) == 'k' && game.getLevel().isHaveLever() && game.isTriggeredLever())
					drawCharacter(leverActivated, g2d, i,j); 
				else if (game.getBoard().getBoardAt(i, j) == 'k' && game.getLevel().isHaveLever())
					drawCharacter(lever, g2d, i,j); 
				else if (game.getBoard().getBoardAt(i, j) == 'k') //In this case is key
					drawCharacter(key, g2d, i,j);
			}
		}
		
	}
	
	private void drawPiecesToChoose(Graphics g2d){
		
 		int lastColumn = game.getBoard().getColumns();
		
		drawCharacter(hero, g2d,0,lastColumn,  Direction.LEFT);
		drawCharacter(guard, g2d,1,lastColumn,  Direction.LEFT);
		drawCharacter(ogre, g2d,2,lastColumn,  Direction.LEFT);
		drawCharacter(wall, g2d,3,lastColumn,  Direction.LEFT);
		drawCharacter(key, g2d,4,lastColumn,  Direction.LEFT);
		drawCharacter(lever, g2d,5,lastColumn,  Direction.RIGHT);
		drawCharacter(door, g2d,6,lastColumn,  Direction.LEFT);
	}
	
	public void startNewGame(GameConfig gameConfig, int level) {
		this.game = new GameLogic(new Level(level), gameConfig);
		this.gameConfig = gameConfig;
		this.level = level;
		showBackground = false;
		charactersHeight = this.getHeight() / gameConfig.getrows();
		charactersWidth = this.getWidth() / gameConfig.getcolumns();
		repaint();
		requestFocus();
	}

	public void startGameCustomization(GameConfig gameConfig){
		this.game = new GameLogic();
		this.gameConfig = gameConfig;
		this.level = customLevel;
		customMap = true;
		showBackground = false;
		charactersHeight = this.getHeight() / gameConfig.getrows();
		charactersWidth = this.getWidth() / (gameConfig.getcolumns() +1);
		repaint();
		requestFocus();
	}

	public boolean isShowBackground() {
		return showBackground;
	}


	public void setShowBackground(boolean showBackground) {
		this.showBackground = showBackground;
	}
	
	private class MyKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (showBackground || customMap)
				return;
			
			int key = e.getKeyCode();
			
			
			final int downKey = gameConfig.getDownKey();
			final int upKey = gameConfig.getUpKey();
			final int rigthKey = gameConfig.getRightKey();
			final int leftKey = gameConfig.getLeftKey();
		
			Direction direction;

			if (key == downKey) {
				direction = Direction.DOWN;
				
			} else if (key == upKey) {
				direction = Direction.UP;
				
			} else if (key == rigthKey) {
				direction = Direction.RIGHT;
				
			} else if (key == leftKey) {
				direction = Direction.LEFT;

			} else if (key == KeyEvent.VK_ESCAPE){
				gameFrame.setOptionInGameVisible(true);
				return;
				
			} else {
				direction = Direction.INVALID;
			}

			game.updateGame(direction);

			repaint();

			
			if(!game.isGameOn() && game.isWon()){
				String msg = "You win!";
				JOptionPane.showMessageDialog(getRootPane(), msg);
				if(++level<=numberOfLevels)
					startNewGame(gameConfig, level);
				else{
					showBackground = true;
					repaint();
				}
					
			}
			else if(!game.isGameOn() && !game.isWon()){
				String msg = "Game Over!";
				JOptionPane.showMessageDialog(getRootPane(), msg);
				startNewGame(gameConfig, level);
			}

			
		}
	}

	
	private class MyMouseAdapter extends MouseAdapter{

		@Override
		public void mousePressed(MouseEvent e) {

			if(customMap){
				if(e.getButton()==MouseEvent.BUTTON1){
					if(mouseCell.getY() == game.getBoard().getColumns())
						switch (mouseCell.getX()) {
						case 0:
							charSelected = 'H';
							break;
						case 1:
							charSelected = 'G';
							break;
						case 2:
							charSelected = 'O';
							break;
						case 3:
							charSelected = 'X';
							break;
						case 4:
							charSelected = 'k';
							break;
						case 5:
							charSelected = 'L';
							break;
						case 6:
							charSelected = 'I';
							break;

						default:
							break;
						}
					
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
			super.mouseReleased(e);
			
			System.out.println(game.getHero().getPosition());
			
			if(customMap){
				if(game.getBoard().getBoardAt(mouseCell.getX(), mouseCell.getY()) == ' '){
					game.getBoard().setBoardAt(mouseCell, charSelected);
					switch (charSelected) {
					case 'H':
						game.getHero().setPosition(new Coord(mouseCell.getX(), mouseCell.getY()));
						haveHero = true;
						break;
					case 'O':
						game.getCrazyOgres().add(new CrazyOgre(new Coord(mouseCell.getX(), mouseCell.getY()),false, game.getBoard()));
						game.getLevel().setHaveOgre(true);
						break;
					case 'G':
						game.setGuard(new Rookie(new Coord(mouseCell.getX(), mouseCell.getY())));
						game.getLevel().setHaveGuard(true);
						break;
					case 'K':
						game.getLevel().setHaveKey(true);
						game.getHero().setKey(true);
						break;
					case 'L':
						game.getLevel().setHaveLever(true);
						game.getHero().setLever(true);
						game.getBoard().setBoardAt(new Coord(mouseCell.getX(), mouseCell.getY()), 'k');
						break;

					default:
						break;
					}
					charSelected = ' ';
					repaint();
				}
			}
		}

		
		
		@Override
		public void mouseDragged(MouseEvent e) {
			super.mouseDragged(e);
			
			if(game!=null){
				int columns = game.getBoard().getColumns();
				int rows = game.getBoard().getRows();

				if (!customMap)
					return;


				int x = (int) ((e.getX() - (getWidth() - charactersWidth * columns) / 2.0) / charactersWidth);

				int y = (int) ((e.getY() - (getHeight() - charactersHeight * rows) / 2.0) / charactersHeight);

				if(x<0)
					x=0;
				else if(x >columns)
					x = columns;

				if(y<0)
					y=0;
				else if (y > rows)
					y = rows;

				mouseCell.setX(y);
				mouseCell.setY(x);

			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
		//	super.mouseMoved(e);
			
			if(game!=null){
				int columns = game.getBoard().getColumns();
				int rows = game.getBoard().getRows();

				if (!customMap)
					return;


				int x = (int) ((e.getX() - (getWidth() - charactersWidth * columns) / 2.0) / charactersWidth);

				int y = (int) ((e.getY() - (getHeight() - charactersHeight * rows) / 2.0) / charactersHeight);

				if(x<0)
					x=0;
				else if(x >columns)
					x = columns;

				if(y<0)
					y=0;
				else if (y > rows)
					y = rows;

				mouseCell.setX(y);
				mouseCell.setY(x);

			}
		}

		
		
		
	}

	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	private static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}


	public void loadGame(GameLogic game) {
		this.game = game;
		this.gameConfig = game.getGameConfig();
		this.level = game.getLevel().getLevel();
		showBackground = false;
		charactersHeight = this.getHeight() / gameConfig.getrows();
		charactersWidth = this.getWidth() / gameConfig.getcolumns();
		repaint();
		requestFocus();
	}
	
	public GameLogic getGame() {
		return game;
	}


	
}
