import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;
import java.io.*;
import java.util.Random;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener{

    //private final static String DEFAULT_FILENAME = "Snakegui.txt";
    private final int GRID_SIZE_X = 400, GRID_SIZE_Y = 300;
    private final int DOT_SIZE = 10;


    //private JButton [] buttonArray;
    private int counter = 0;
    //private JLabel [][] labels;
    private int snakeSize = 2;
    private final int DELAY_IN_MILISEC = 10;
    private int [][] array = new int[GRID_SIZE_X][GRID_SIZE_Y];
    //private TextField textField;
    private Mechanics game;
    
    public java.util.List<Pair> gameSnake = new java.util.ArrayList<Pair>();
    public java.util.List<Pair> portalArray = new java.util.ArrayList<Pair>();
    public Pair foodLoc;
    
    private Image snake;
    private Image food;
    private Image portalBlue;
    private Image portalOrange;
    private Image softWall;

    private Timer timer;
    private boolean inGame = false;
    private static String currentLevel = "Zero";
    private JToolTip toolTip = new JToolTip();


    public Board() {
        addKeyListener(new TAdapter());
	setBackground(Color.white);
	setFocusable(true);
	
	setPreferredSize(new Dimension(GRID_SIZE_X+9, GRID_SIZE_Y+9));
	game = new Mechanics(GRID_SIZE_X, GRID_SIZE_Y);
	gameSnake = game.getSnake();
	portalArray  = game.getPortals();
        loadImages();
	foodLoc = game.getFoodLocation();
	Board.selectLevel();
        toolTip.setTipText("Current level is: " +  currentLevel);
	timer = new Timer(DELAY_IN_MILISEC,this);
	inGame = true;
	timer.start();
    }

    private void loadImages(){
	ImageIcon iia = new ImageIcon("snake.png");
	snake = iia.getImage();
	ImageIcon iib = new ImageIcon("food.png");
	food = iib.getImage();
	ImageIcon iic = new ImageIcon("portalBlue.png");
	portalBlue = iic.getImage();
	ImageIcon iid = new ImageIcon("portalOrange.png");
	portalOrange = iid.getImage();
	ImageIcon iie = new ImageIcon("softWall.png");
	softWall = iie.getImage();
    }

    private String getPortalColour(){
    	String name;
    	Random rand = new Random();
    	boolean portalColour = rand.nextBoolean();
    	if(portalColour){
    		name = "portalBlue.jpg";
    	}else{
    		name = "portalOrange.jpg";
    	}
	return name;
    }

    /**
     *  Returns the class name
     */
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }

    @Override
    public void paintComponent(Graphics g){
	super.paintComponent(g);
	doDrawing(g);
    }
    
    private void doDrawing(Graphics g){
	if(inGame){
	    //draw food
		foodLoc = game.getFoodLocation();
	    g.drawImage(food,foodLoc.row,foodLoc.column,this);
	    //draw snake
	    for(Pair snakeUnit : gameSnake){
		g.drawImage(snake, snakeUnit.row, snakeUnit.column, this); 
	    }
	    //draw portals
	    boolean alternate = true;
	    for(Pair portal: game.portal_location){
		if(alternate){
		    g.drawImage(portalBlue,portal.row,portal.column,this);
		    alternate = false;
		    continue;
		}else
		    g.drawImage(portalOrange, portal.row,portal.column,this);
		    alternate = true;
		    continue;
	    }

	    //draw walls
	    for(int row = 0; row<game.map.length; row++){
		for(int column = 0; column<game.map[row].length;column++){
		    if(game.map[row][column] == 1)
			g.drawImage(softWall, row, column, this);
		}
	    }
	    Toolkit.getDefaultToolkit().sync();
	}else{
	    //game over
	    endGame(); 
	}
    }


    @Override
    public void actionPerformed(ActionEvent e){
        String classname = getClassName(e.getSource());
        //JComponent component = (JComponent)(e.getSource());
        if (classname.equals("JMenuItem")){
            JMenuItem menusource = (JMenuItem)(e.getSource());
            String menutext  = menusource.getText();

            // Determine which menu option was chosen
            if (menutext.equals("Pause Game"))
            {
                /* ConnectGUI    Add your code here to handle Load Game **********/
                pauseGame();
                menusource.setText("Resume Game");
            }
            else if (menutext.equals("End Game"))
            {
                /* ConnectGUI    Add your code here to handle Save Game **********/
                endGame();
            }
            else if (menutext.equals("New Game"))
            {
                /* ConnectGUI    Add your code here to handle Save Game **********/
                NewGame();
            }
            else if(menutext.equals("Resume Game"))
            {
            	timer.start();
            	menusource.setText("Pause Game");
            }
            else if(menutext.equals("Info"))
            {
            	infoPage();
            }
        }
	
	if(inGame){
	    //checkFood();
	    game.checkCollision();
	    game.move();
	}
	repaint();
    }

    private class TAdapter extends KeyAdapter {
	@Override
	public void keyPressed(KeyEvent e){
	    int key = e.getKeyCode();
	    //LEFT
	    if((key == KeyEvent.VK_LEFT) && (!game.rightDirection)){
		game.leftDirection = true;
		game.upDirection = false;
		game.downDirection = false;
	    }
	    //RIGHT
	    if((key == KeyEvent.VK_RIGHT) && (!game.leftDirection)){
		game.rightDirection = true;
		game.upDirection = false;
		game.downDirection = false;
	    }
	    //UP
	    if((key == KeyEvent.VK_UP) && (!game.downDirection)){
		game.leftDirection = false;
		game.upDirection = true;
		game.rightDirection = false;
	    }
	    //DOWN
	    if((key == KeyEvent.VK_DOWN) && (!game.upDirection)){
		game.leftDirection = false;
		game.rightDirection = false;
		game.downDirection = true;
	    }
	}
    }

   public void NewGame(){
         System.out.println("New game selected");
         Board snakeGui = new Board();
    }

    public void pauseGame(){
	System.out.println("Pause game selected");
	timer.stop();
	System.out.println("Timer stopped");
    }

    public void endGame(){
	System.out.println("End game selected");
        if(exitWarning()){
	    System.exit(0);
        }
    }

    private void infoPage(){
	Board.infoBox("OK so this is our 'modern' implementation of the classic game Snake,\nwhich we loved to play on our old Nokia phones.\nThe rules are simple: Eat the food, walls are friendly,\nPortals teleport you to a random portal and DO NOT EAT yourself!\nPretty simple stuff! Now show us what you can do!", "Information");
    }

    public static void infoBox(String infoMessage, String titleBar){
	JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void selectLevel(){
	String[] possibleValues = { "One", "Two", "Three", "Four", "Five" };
	String selectedValue = (String) JOptionPane.showInputDialog(null,
	"Choose a level", "Level",
	JOptionPane.INFORMATION_MESSAGE, null,
	possibleValues, possibleValues[0]);
	System.out.println("A game has been selected and it is: " +  selectedValue);
	currentLevel = selectedValue;
    }

    private static boolean exitWarning(){
	int end;
	String[] options = { "OK", "CANCEL" };
	end = JOptionPane.showOptionDialog(null, "Click OK to exit the game", "Warning",
	JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
	null, options, options[0]);
	if(end == 0) return true;//the function gives 0 when ok is clicked
	else return false;
    }

    private static void deathScreen(){
	infoBox("You have died! Sorry to hear that...", "Death Screen");
    }


    public JMenuBar createMenu(){
        JMenuBar menuBar  = new JMenuBar();;
        JMenu menu = new JMenu("Snake Menu");
        JMenuItem menuItem;
        menuBar.add(menu);

        // A group of JMenuItems. You can create other menu items here if desired
        menuItem = new JMenuItem("New Game");
        menuItem.addActionListener(this);
        menu.add(menuItem); 

        menuItem = new JMenuItem("Pause Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

	menuItem = new JMenuItem("Info");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("End Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

	toolTip.setTipText("Current level is: " +  currentLevel);
        menuBar.add(toolTip);

        //a submenu
        menu.addSeparator();

        return menuBar;
    }
}

