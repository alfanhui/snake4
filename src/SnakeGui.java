/**
 * SnakeGui:
 *
 * @author Dancho Atanasov
 * @version 1.0
 *
 *
 * Notes:
 *  Event handlers have been set up for Menu Options
 *  NewGame, Pause game, End game
 *
 *  To add functionality to this GUI add you code to these functions
 *  which are at the end of this file.
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Random;

public class SnakeGui implements ActionListener, KeyListener {

    /**
     * Enumerated type to allow us to refer to RED, YELLOW or BLANK
     */

    private final static String DEFAULT_FILENAME = "Snakegui.txt";
    private final int GRID_SIZE_X = 60, GRID_SIZE_Y = 80;
    //private JButton [] buttonArray;
    private ImageIcon brownSquare = convertPicutres("brownSquare.jpg");
    private ImageIcon blueSquare = convertPicutres("blueSquare.jpg");
    private ImageIcon blankSpace = convertPicutres("blankSpace.jpg");
    private ImageIcon softWall = convertPicutres("softWall.jpg");
    private ImageIcon snake = convertPicutres("snake.jpg");
    private ImageIcon portal = convertPicutres(getPortalColour());
    private ImageIcon food = convertPicutres("food.jpg");
    private ImageIcon defaultStart = convertPicutres("defaultStart.jpg");
    private int counter = 0;
    private JLabel [][] labels;
    private static JFrame frame = new JFrame("SnakeGui");
    private int snakeSize = 2;
    private final int DELAY_IN_MILISEC = 200;
    private int [][] array = new int[GRID_SIZE_X][GRID_SIZE_Y];
    private TextField textField;
    private Snake4 game;
    public java.util.List<Integer> gameSnake = new java.util.ArrayList<Integer>();
    public java.util.List<Integer> portalArray = new java.util.ArrayList<Integer>();


    public SnakeGui() {
        game = new Snake4();
        gameSnake = game.getSnake();
        portalArray = game.getPortals();
    }


    private String getPortalColour()
    {
    	String name;
    	Random rand = new Random();
    	boolean portalColour = rand.nextBoolean();
    	if(portalColour)
    	{
    		name = "portalBlue.jpg";
    		return name;
    	}
    	else
    	{
    		name = "portalOrange.jpg";
    		return name;
    	}

    }

    public int[] convertToMDA(int location){
        String intString = Integer.toString(location);
        int mid = intString.length()/2;
        int[] parts = {Integer.parseInt(intString.substring(0, mid)),Integer.parseInt(intString.substring(mid))};
        return parts;
    }

    /*
     * A timer that makes changes to the game after an set interval of time
     */
    private Timer timer = new Timer(DELAY_IN_MILISEC, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
        game.move();

        for (int row=0; row<GRID_SIZE_X; row++) {
        	for(int col=0;col<GRID_SIZE_Y;col++) {
        		//labels[row][col] = new JLabel(getImageIcon(array[row][col]));
        		labels[row][col].setIcon(getImageIcon(array[row][col]));
            }
        }
        gameSnake = game.getSnake();
        int[] parts = new int[2];
        for (int i=0;i<gameSnake.size();i++) {
            parts = convertToMDA(gameSnake.get(i));
            labels[parts[0]][parts[1]].setIcon(snake);
        }

        portalArray = game.getPortals();
        int[] parts = new int[2];
        for (int i=0;i<portalArray.size();i++) {
            parts = convertToMDA(portalArray.get(i));
            labels[parts[0]][parts[1]].setIcon(getPortalColour());
        }


/**
 *
			System.out.println("HERE and counter is: " + counter);
			int row = counter/GRID_SIZE_Y;
			int col = counter%GRID_SIZE_Y;
			if(counter <= snakeSize)
			{
				labels[row][col].setIcon(snake);
				counter++;
			}
			else
			{
				labels[row][col].setIcon(snake);
				if(col - snakeSize - 1 < 0)
				{
					labels[row-1][GRID_SIZE_Y + (col - snakeSize) - 1].setIcon(getImageIcon(array[row-1][GRID_SIZE_Y + (col - snakeSize) - 1]));
					counter++;
				}
				else
				{
					labels[row][col-snakeSize - 1].setIcon(getImageIcon(array[row][col-snakeSize - 1]));
					counter++;
				}

			}
            */
		}
	});

    /**
     * a method that resizes the pictures
     * @param name
     * @return
     */
    private ImageIcon convertPicutres(String name)
    {
    	ImageIcon imageTemporary = new ImageIcon(name);
    	Image image2 = imageTemporary.getImage(); // transform it
        Image newimg = image2.getScaledInstance(10, 10,  java.awt.Image.SCALE_SMOOTH);// scale it the smooth way
        return new ImageIcon(newimg);// transform it back
    }

    private void printArrayArg(int [][] arr)
    {
    	for(int i=0;i<GRID_SIZE_X;i++)
    	{
    		for(int j=0;j<GRID_SIZE_Y;j++)
    		{
    			System.out.print(arr[i][j] + " ");
    		}
    		System.out.println();
    	}
    }

    private void printArray()
    {
    	for(int i=0;i<GRID_SIZE_X;i++)
    	{
    		for(int j=0;j<GRID_SIZE_Y;j++)
    		{
    			System.out.print(array[i][j] + " ");
    		}
    		System.out.println();
    	}
    }

    private void readArrayFromFile()
    {
    	FileReader fileReader = null;
    	BufferedReader bufferedReader = null;

    	try
    	{
    		fileReader = new FileReader("board2.txt");
    		bufferedReader = new BufferedReader(fileReader);
    		String[] tempStringArray = new String[GRID_SIZE_Y];
    		String nextLine = bufferedReader.readLine();

    			for(int i=0;i<array.length;i++)
        		{
    				tempStringArray = nextLine.split(" ");
    				for(int j=0;j<tempStringArray.length;j++)
    				{
    					array[i][j] = Integer.parseInt(tempStringArray[j]);
    				}
        			nextLine = bufferedReader.readLine();
        		}
    	}

    	catch(IOException e)
    	{
    		System.out.println("Cannot read file!");
    	}
    	try
    	{
    		bufferedReader.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println("Could not close file");
    	}


    }

    public JMenuBar createMenu()
    {
        JMenuBar menuBar  = new JMenuBar();;
        JMenu menu = new JMenu("Snake Menu");
        JMenuItem menuItem;
//        JPanel jPanel = new JPanel();
//        KeyListener keyListener = new KeyListener() {
//
//			@Override
//			public void keyTyped(KeyEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void keyReleased(KeyEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//				// TODO Auto-generated method stub
//				int key = e.getKeyCode();
//				if(key == KeyEvent.VK_UP)
//			    	{
//			    		System.out.println("Working?");
//			    	}
//			}
//		};


        menuBar.add(menu);

        // A group of JMenuItems. You can create other menu items here if desired
        menuItem = new JMenuItem("New Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Pause Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("End Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //a submenu
        menu.addSeparator();

        return menuBar;
    }

    public Container createContentPane()
    {
        //int numButtons = GRID_SIZE_X * GRID_SIZE_Y;
        JPanel grid = new JPanel(new GridLayout(GRID_SIZE_X, GRID_SIZE_Y,0,0));

        //buttonArray = new JButton[numButtons];
        labels = new JLabel[GRID_SIZE_X][GRID_SIZE_Y];
        readArrayFromFile();
        game.setMap(array);
        // array = game.getMap();
        for (int row=0; row<GRID_SIZE_X; row++)
        {
        	for(int col=0;col<GRID_SIZE_Y;col++)
        	{
        		labels[row][col] = new JLabel(getImageIcon(array[row][col]));
        		grid.add(labels[row][col]);

        	}
            //buttonArray[i] = new JButton(" ");

            // This label is used to identify which button was clicked in the action listener
            //buttonArray[i].setActionCommand("" + i); // String "0", "1" etc.
            //buttonArray[i].addActionListener(this);
            //grid.add(buttonArray[i]);

        }
        grid.addKeyListener(this);
		grid.setFocusable(true);
        return grid;
    }

    private ImageIcon getImageIcon(int x)
    {
    	switch(x)
		{
		case 0:
			//labels[row][col] = new JLabel(brownSquare);//For now this will be our squares
			return blankSpace;

		case 1:
			return softWall;

		case 2:
			return snake;

		case 3:
			return portal;

		case 4:
			return food;

		case 5:
			return defaultStart;
		default: return null;
		}
    }

    //private String get

//    private void keyPressed(KeyEvent e)
//    {
//    	int key = e.getKeyCode();
//    	if(key == KeyEvent.VK_SPACE)
//    	{
//    		System.out.println("Working?");
//    	}
//    }




    /**
     * This method handles events from the Menu and the board.
     *
     */
    public void actionPerformed(ActionEvent e)
    {
        String classname = getClassName(e.getSource());
        JComponent component = (JComponent)(e.getSource());

//        KeyListener keyListener = new KeyListener() {
//
//			@Override
//			public void keyTyped(KeyEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void keyReleased(KeyEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//				// TODO Auto-generated method stub
//				int key = e.getKeyCode();
//				if(key == KeyEvent.VK_SPACE)
//			    	{
//			    		System.out.println("Working?");
//			    	}
//			}
//		};
		//component.addKeyListener(this);

        if (classname.equals("JMenuItem"))
        {
            JMenuItem menusource = (JMenuItem)(e.getSource());
            String menutext  = menusource.getText();

            // Determine which menu option was chosen
            if (menutext.equals("Pause Game"))
            {
                /* ConnectGUI    Add your code here to handle Load Game **********/
                pauseGame();
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
        }

        // Handle the event from the user clicking on a command button
//        else if (classname.equals("JButton"))
//        {
//            JButton button = (JButton)(e.getSource());
//            int bnum = Integer.parseInt(button.getActionCommand());
//            int row = bnum % GRID_SIZE_X;
//            int col = bnum / GRID_SIZE_X;
//
//            System.out.println("bnum=" + bnum);
//
//        }
    }

    /**
     *  Returns the class name
     */
    protected String getClassName(Object o)
    {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }



    /**
     * Create the GUI and show it.
     * For thread safety, this method should be invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI()
    {
        // Create and set up the window.
        //JFrame frame = new JFrame("SnakeGui");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        SnakeGui snakeGui = new SnakeGui();
        frame.setJMenuBar(snakeGui.createMenu());
        frame.setContentPane(snakeGui.createContentPane());

        // Display the window, setting the size
        frame.setSize(1000, 750);
        frame.setVisible(true);
    }

//

    /**
     * This is a standard main function for a Java GUI
     */
    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
//    	SnakeGui test = new SnakeGui();
//    	test.readArrayFromFile();
//    	test.printArray();
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {

                createAndShowGUI();
            }
        });
    }

    //************************************************************************
    //*** ConnectGUI: Modify the methods below to respond to Menu and Mouse click events

    /**
     * This method is called from the Menu event: New Game.
     * ConnectGUI
     */
    public void NewGame()
    {
         System.out.println("New game selected");

         // Initialise your game
         timer.start();
         System.out.println("Timer started");

    }


    /**
     * This method is called from the Menu event: Load Game.
     * ConnectGUI
     */
    public void pauseGame()
    {
          System.out.println("Pause game selected");
          timer.stop();
          System.out.println("Timer stopped");
    }


    /**
     * This method is called from the Menu event: Save Game.
     * ConnectGUI
     */
    public void endGame()
    {
          System.out.println("End game selected");
          System.exit(0);
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
        System.out.println("KEY HAS BEEN PRESSED");
        game.changeDirection(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}

