package uk.ac.dundee;

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
import java.net.URISyntaxException;

import java.util.Random;

public class SnakeGui implements ActionListener, KeyListener {

    /**
     * Enumerated type to allow us to refer to RED, YELLOW or BLANK
     */
    private ImageIcon blankSpace = convertPicutres("tiles/blankSpace.jpg");
    private ImageIcon food = convertPicutres("tiles/food.jpg");
    private ImageIcon hardWall = convertPicutres("tiles/hardWall.jpg");
    private ImageIcon portal = convertPicutres(getPortalColour());
    private ImageIcon snake = convertPicutres("tiles/snake.jpg");
    private ImageIcon softWall = convertPicutres("tiles/softWall.jpg");
    
    private JLabel[][] labels;
    private static JFrame frame = new JFrame("SnakeGui");
    private final int DELAY_IN_MILISEC = 100; //200
    private int[][] array;
    private Snake4 game;
    private java.util.List<Pair> gameSnake = new java.util.ArrayList<Pair>();
    private Pair[] portalArray;

    public SnakeGui() {
        game = new Snake4("boards/board1.txt", false, false, true);
        array = game.getMap();
        gameSnake = game.getSnake();
        portalArray = game.getPortals();
    }

    private String getPortalColour() {
        String name;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            name = "tiles/portalBlue.jpg";
        } else {
            name = "tiles/portalOrange.jpg";
        }
        return name;
    }

    /*
     * A timer that makes changes to the game after an set interval of time
     */
    private Timer timer = new Timer(DELAY_IN_MILISEC, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            gameSnake = game.move();            

            for (int row = 0; row < array.length; row++) {
                for (int col = 0; col < array[row].length; col++) {
                    try {
                        labels[row][col].setIcon(getImageIcon(array[row][col]));
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }

            for (int i = 0; i < gameSnake.size(); i++) {
                Pair parts = gameSnake.get(i);
                labels[parts.row][parts.column].setIcon(snake);
            }

            portalArray = game.getPortals();
            // System.out.println("PORTAL COORDINATES: "+portalArray[0]+" AND
            // "+portalArray[1]);
            for (int i = 0; i < portalArray.length; i++) {
                Pair parts = portalArray[i];
                labels[parts.row][parts.column].setIcon(portal);
            }
        }
    });

    /**
     * a method that resizes the pictures
     * 
     * @param name
     * @return
     * @throws URISyntaxException
     */
    private ImageIcon convertPicutres(String name) {
        File imageFile = new File(getClass().getClassLoader().getResource(name).getFile());
        //URL url = getClass().getClassLoader().getResource(name);
        ImageIcon imageTemporary = new ImageIcon(imageFile.getPath());
        Image image2 = imageTemporary.getImage(); // transform it
        Image newimg = image2.getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH);// scale it the smooth way
        return new ImageIcon(newimg);// transform it back
    }

    private void printArrayArg(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void printArray() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    public JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
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

        menuItem = new JMenuItem("End Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        // a submenu
        menu.addSeparator();

        return menuBar;
    }

    public Container createContentPane() throws Exception {
        game.setMap(array);
        int GRID_SIZE_X = array.length;
        int GRID_SIZE_Y = array[0].length;
        JPanel grid = new JPanel(new GridLayout(GRID_SIZE_X, GRID_SIZE_Y, 0, 0));
        labels = new JLabel[GRID_SIZE_X][GRID_SIZE_Y];
        for (int row = 0; row < GRID_SIZE_X; row++) {
            for (int col = 0; col < GRID_SIZE_Y; col++) {
                labels[row][col] = new JLabel(getImageIcon(array[row][col]));
                grid.add(labels[row][col]);

            }
        }
        grid.addKeyListener(this);
        grid.setFocusable(true);
        return grid;
    }

    private ImageIcon getImageIcon(int x) throws Exception {
        switch (x) {
            case 0:
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
                return blankSpace;
            case 6:
                return hardWall;
            case 7:
                return blankSpace;
            default:
                throw new Exception("Default switch found");
        }
    }

    /**
     * This method handles events from the Menu and the board.
     *
     */
    public void actionPerformed(ActionEvent e) {
        String classname = getClassName(e.getSource());

        if (classname.equals("JMenuItem")) {
            JMenuItem menusource = (JMenuItem) (e.getSource());
            String menutext = menusource.getText();

            // Determine which menu option was chosen
            if (menutext.equals("Pause Game")) {
                /* ConnectGUI Add your code here to handle Load Game **********/
                pauseGame();
            } else if (menutext.equals("End Game")) {
                /* ConnectGUI Add your code here to handle Save Game **********/
                endGame();
            } else if (menutext.equals("New Game")) {
                /* ConnectGUI Add your code here to handle Save Game **********/
                NewGame();
            }
        }
    }

    /**
     * Returns the class name
     */
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex + 1);
    }

    /**
     * This is a standard main function for a Java GUI
     */
    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create and set up the window.
                // JFrame frame = new JFrame("SnakeGui");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Create and set up the content pane.
                SnakeGui snakeGui = new SnakeGui();
                frame.setJMenuBar(snakeGui.createMenu());
                Container snakeFrame;
                try {
                    snakeFrame = snakeGui.createContentPane();
                    frame.setContentPane(snakeFrame);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // Display the window, setting the size
                frame.setSize(300, 300);
                frame.setVisible(true);
            }
        });
    }

    // ************************************************************************
    // *** ConnectGUI: Modify the methods below to respond to Menu and Mouse click
    // events

    /**
     * This method is called from the Menu event: New Game.
     * ConnectGUI
     */
    public void NewGame() {
        System.out.println("New game selected");
        // Initialise your game
        timer.start();
        System.out.println("Timer started");

    }

    /**
     * This method is called from the Menu event: Load Game.
     * ConnectGUI
     */
    public void pauseGame() {
        System.out.println("Pause game selected");
        if(timer.isRunning()){
            timer.stop();
            System.out.println("Timer stopped");
        }else {
            timer.start();
            System.out.println("Timer started");
        }
    }

    /**
     * This method is called from the Menu event: Save Game.
     * ConnectGUI
     */
    public void endGame() {
        System.out.println("End game selected");
        System.exit(0);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        game.changeDirection(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}
