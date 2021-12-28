package uk.ac.dundee;

/**
 * SnakeGui:
 *
 * @author Dancho Atanasov
 * @version 1.0
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URISyntaxException;
import java.net.URL;

public class SnakeGui implements ActionListener, KeyListener {

    /**
     * Enumerated type to allow us to refer to RED, YELLOW or BLANK
     */
    private ImageIcon blankSpace = convertPicutres("tiles/blankSpace.jpg");
    private ImageIcon food = convertPicutres("tiles/food.jpg");
    private ImageIcon hardWall = convertPicutres("tiles/hardWall.jpg");
    private ImageIcon portalBlue = convertPicutres("tiles/portalBlue.jpg");
    private ImageIcon portalOrange = convertPicutres("tiles/portalOrange.jpg");
    private ImageIcon snake = convertPicutres("tiles/snake.jpg");
    private ImageIcon snakeHead = convertPicutres("tiles/snakeHead.jpg");
    private ImageIcon softWall = convertPicutres("tiles/softWall.jpg");

    private JLabel[][] labels;
    private JToolTip levelToolTip = new JToolTip();
    private JToolTip snakizeToolTip = new JToolTip();
    private static JFrame frame = new JFrame("Snake4");

    private final int DELAY_IN_MILISEC = 125; // 200
    private int[][] array;
    private Snake4 game;
    private java.util.List<Pair> gameSnake = new java.util.ArrayList<Pair>();
    private Pair[] portalArray;

    private boolean portalColourToggle = false;

    private static final int windowDimentionsX = 460;
    private static final int windowDimentionsY = 500;

    private int level = 4;
    private final int numOfLevels = 5;
    private int winTarget = 20;

    public SnakeGui() {
        startNewGame();
    }

    private void startNewGame(){
        game = new Snake4("boards/board" + level + ".txt", true, false, true);
        array = game.getMap();
        gameSnake = game.getSnake();
        portalArray = game.getPortals();
    }

    private ImageIcon getPortalColour() {
        portalColourToggle = !portalColourToggle;
        if (portalColourToggle) {
            return portalBlue;
        } else {
            return portalOrange;
        }
    }

    /*
     * A timer that makes changes to the game after an set interval of time
     */
    private Timer timer = new Timer(DELAY_IN_MILISEC, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            gameSnake = game.move();
            if (game.isDead()) {
                timer.stop();
                deathScreen();
            }

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
                if (game.getHead() == i) {
                    labels[parts.row][parts.column].setIcon(snakeHead);
                } else {
                    labels[parts.row][parts.column].setIcon(snake);
                }
            }

            portalArray = game.getPortals();

            for (int i = 0; i < portalArray.length; i++) {
                Pair parts = portalArray[i];
                labels[parts.row][parts.column].setIcon(getPortalColour());
            }

            //Update tooltips
            levelToolTip.setTipText("Level " + level + "/" + numOfLevels);
            snakizeToolTip.setTipText("Snakize " + game.getSnake().size() + "/" + winTarget);
            
            if(hasWonLevel()){
                level++;
                timer.stop();
                if(hasWonGame()){
                    wonGameScreen();
                    level = 1; //return to starting level
                }else{
                    wonLevelScreen();
                    startNewGame();
                    timer.start();
                }
            }
        }
    });

    private boolean hasWonGame(){
        return (level) > numOfLevels;
    }

    private boolean hasWonLevel(){
        return game.getSnake().size() > winTarget;
    }

    /**
     * a method that resizes the pictures
     * 
     * @param name
     * @return
     * @throws URISyntaxException
     */
    private ImageIcon convertPicutres(String name) {
        URL url = getClass().getClassLoader().getResource(name);
        Image image = new ImageIcon(url).getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(image);
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

        menuItem = new JMenuItem("Start");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Pause");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("End Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        levelToolTip.setTipText("Level " + level + "/" + numOfLevels);
        snakizeToolTip.setTipText("Snakize " + game.getSnake().size() + "/" + winTarget);
        menuBar.add(levelToolTip);
        menuBar.add(snakizeToolTip);

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
            case 1:
                return softWall;
            case 2:
                return snake;
            case 3:
                return getPortalColour();
            case 4:
                return food;
            case 6:
                return hardWall;
            case 0:
            case 5:
            case 7:
            case 9:
                return blankSpace;
            case 8:
                return snakeHead;
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
            if (menutext.equals("Pause")) {
                /* ConnectGUI Add your code here to handle Load Game **********/
                pauseGame();
            } else if (menutext.equals("End Game")) {
                /* ConnectGUI Add your code here to handle Save Game **********/
                endGame();
            } else if (menutext.equals("Start")) {
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
                    // frame.setLocationRelativeTo(null);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // Display the window, setting the size
                centreWindow();
                frame.setSize(windowDimentionsX, windowDimentionsY);
                frame.setVisible(true);
            }
        });
    }

    public static void centreWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2 - (windowDimentionsX / 2));
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2 - (windowDimentionsY / 2));
        frame.setLocation(x, y);
    }

    // ************************************************************************
    // *** ConnectGUI: Modify the methods below to respond to Menu and Mouse click
    // events

    private static void deathScreen() {
        infoBox("You have died! Sorry to hear that...", "Death Screen");
    }

    private static void wonLevelScreen() {
        infoBox("Success!", "Level complete");
    }

    private static void wonGameScreen() {
        infoBox("Congratuations! You have completed this Game.", "Game complete");
    }


    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * This method is called from the Menu event: New Game.
     * ConnectGUI
     */
    public void NewGame() {
        System.out.println("New game selected");
        // Initialise your game
        startNewGame();
        timer.start();
        System.out.println("Timer started");

    }

    /**
     * This method is called from the Menu event: Load Game.
     * ConnectGUI
     */
    public void pauseGame() {
        System.out.println("Pause game selected");
        if (timer.isRunning()) {
            timer.stop();
            System.out.println("Timer stopped");
        } else {
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
        if (exitWarning()) {
            System.exit(0);
        }
    }

    private static boolean exitWarning() {
        String[] options = { "EXIT", "PLAY" };
        int end = JOptionPane.showOptionDialog(null, "Do you want to exit the game?", "Warning",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        if (end == 0)
            return true;// the function gives 0 when ok is clicked
        else
            return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!timer.isRunning() && e.getKeyCode() == 10){
            startNewGame();
            timer.start();
        }
        game.changeDirection(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}
