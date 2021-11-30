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
import java.net.URL;

import java.util.Random;

public class SnakeGui implements ActionListener, KeyListener {

    /**
     * Enumerated type to allow us to refer to RED, YELLOW or BLANK
     */
    private final int GRID_SIZE_X = 60, GRID_SIZE_Y = 80;
    // private ImageIcon brownSquare = convertPicutres("tiles/brownSquare.jpg");
    // private ImageIcon blueSquare = convertPicutres("tiles/blueSquare.jpg");
    private ImageIcon blankSpace = convertPicutres("tiles/blankSpace.jpg");
    private ImageIcon softWall = convertPicutres("tiles/softWall.jpg");
    private ImageIcon snake = convertPicutres("tiles/snake.jpg");
    private ImageIcon food = convertPicutres("tiles/food.jpg");
    private ImageIcon portal = convertPicutres(getPortalColour());
    // private ImageIcon defaultStart = convertPicutres("defaultStart.jpg");
    private JLabel[][] labels;
    private static JFrame frame = new JFrame("SnakeGui");
    private final int DELAY_IN_MILISEC = 200;
    private int[][] array = new int[GRID_SIZE_X][GRID_SIZE_Y];
    private Snake4 game;
    public java.util.List<Integer> gameSnake = new java.util.ArrayList<Integer>();
    public int[] portalArray;
    public Pair foodLoc;

    public SnakeGui() {
        game = new Snake4();
        gameSnake = game.getSnake();
        portalArray = game.getPortals();
        foodLoc = game.getFoodLocation();
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

    public int[] convertToMDA(int location) {
        String intString = Integer.toString(location);
        int mid = intString.length() / 2;
        int[] parts = { Integer.parseInt(intString.substring(0, mid)), Integer.parseInt(intString.substring(mid)) };
        return parts;
    }

    /*
     * A timer that makes changes to the game after an set interval of time
     */
    private Timer timer = new Timer(DELAY_IN_MILISEC, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            game.move();

            for (int row = 0; row < GRID_SIZE_X; row++) {
                for (int col = 0; col < GRID_SIZE_Y; col++) {
                    try {
                        labels[row][col].setIcon(getImageIcon(array[row][col]));
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }

            labels[foodLoc.row][foodLoc.column].setIcon(food);

            gameSnake = game.getSnake();
            int[] parts = new int[2];
            for (int i = 0; i < gameSnake.size(); i++) {
                parts = convertToMDA(gameSnake.get(i));
                labels[parts[0]][parts[1]].setIcon(snake);
            }

            portalArray = game.getPortals();
            // System.out.println("PORTAL COORDINATES: "+portalArray[0]+" AND
            // "+portalArray[1]);
            for (int i = 0; i < portalArray.length; i++) {
                parts = convertToMDA(portalArray[i]);
                labels[parts[0]][parts[1]].setIcon(portal);
            }

            /**
             *
             * System.out.println("HERE and counter is: " + counter);
             * int row = counter/GRID_SIZE_Y;
             * int col = counter%GRID_SIZE_Y;
             * if(counter <= snakeSize)
             * {
             * labels[row][col].setIcon(snake);
             * counter++;
             * }
             * else
             * {
             * labels[row][col].setIcon(snake);
             * if(col - snakeSize - 1 < 0)
             * {
             * labels[row-1][GRID_SIZE_Y + (col - snakeSize) -
             * 1].setIcon(getImageIcon(array[row-1][GRID_SIZE_Y + (col - snakeSize) - 1]));
             * counter++;
             * }
             * else
             * {
             * labels[row][col-snakeSize - 1].setIcon(getImageIcon(array[row][col-snakeSize
             * - 1]));
             * counter++;
             * }
             * 
             * }
             */
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
        URL url = getClass().getClassLoader().getResource(name);
        ImageIcon imageTemporary = new ImageIcon(url.getPath());
        Image image2 = imageTemporary.getImage(); // transform it
        Image newimg = image2.getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH);// scale it the smooth way
        return new ImageIcon(newimg);// transform it back
    }

    private void printArrayArg(int[][] arr) {
        for (int i = 0; i < GRID_SIZE_X; i++) {
            for (int j = 0; j < GRID_SIZE_Y; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void printArray() {
        for (int i = 0; i < GRID_SIZE_X; i++) {
            for (int j = 0; j < GRID_SIZE_Y; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void readArrayFromFile() {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try {
            URL url = getClass().getClassLoader().getResource("boards/board2.txt");
            fileReader = new FileReader(url.getPath());
            bufferedReader = new BufferedReader(fileReader);
            String[] tempStringArray = new String[GRID_SIZE_Y];
            String nextLine = bufferedReader.readLine();

            for (int i = 0; i < array.length; i++) {
                tempStringArray = nextLine.split(" ");
                for (int j = 0; j < tempStringArray.length; j++) {
                    array[i][j] = Integer.parseInt(tempStringArray[j]);
                }
                nextLine = bufferedReader.readLine();
            }
        }

        catch (IOException e) {
            System.out.println("Cannot read file!");
        }
        try {
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Could not close file");
        }

    }

    public JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        ;
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
        // int numButtons = GRID_SIZE_X * GRID_SIZE_Y;
        JPanel grid = new JPanel(new GridLayout(GRID_SIZE_X, GRID_SIZE_Y, 0, 0));

        // buttonArray = new JButton[numButtons];
        labels = new JLabel[GRID_SIZE_X][GRID_SIZE_Y];
        readArrayFromFile();
        game.setMap(array);
        // array = game.getMap();
        for (int row = 0; row < GRID_SIZE_X; row++) {
            for (int col = 0; col < GRID_SIZE_Y; col++) {
                labels[row][col] = new JLabel(getImageIcon(array[row][col]));
                grid.add(labels[row][col]);

            }
            // buttonArray[i] = new JButton(" ");

            // This label is used to identify which button was clicked in the action
            // listener
            // buttonArray[i].setActionCommand("" + i); // String "0", "1" etc.
            // buttonArray[i].addActionListener(this);
            // grid.add(buttonArray[i]);

        }
        grid.addKeyListener(this);
        grid.setFocusable(true);
        return grid;
    }

    private ImageIcon getImageIcon(int x) throws Exception {
        switch (x) {
            case 0:
                // labels[row][col] = new JLabel(brownSquare);//For now this will be our squares
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
                try {
                    frame.setContentPane(snakeGui.createContentPane());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // Display the window, setting the size
                frame.setSize(1000, 750);
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
        timer.stop();
        System.out.println("Timer stopped");
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
