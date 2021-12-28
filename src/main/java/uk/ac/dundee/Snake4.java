package uk.ac.dundee;

/**
 * Dundee university Quackathon
 *
 **/
import java.util.*;
import java.util.Random;

public class Snake4 {
    // Global variables
    private int map[][];
    private int head = 0;
    private char direction = 'u';
    private Pair[] portal_location;
    private List<Pair> snake;
    private boolean dead = false;
    private int height;
    private int width;
    private final char printChars[] = new char[] { ' ', '*', '.', '@', '~' };
    private Map mapClass;
    private Pair foodPair;

    /**
     * @param boardPath         String location in resources folder of the board to
     *                          use
     * @param useDefaultStart   use default snake start location from board (noted
     *                          with '5')
     * @param useDefaultPortals use default ports locations from board (noted with
     *                          '3')
     * @param setFoodDown       boolean whether the board should have food
     **/
    public Snake4(String boardPath, boolean useDefaultStart, boolean useDefaultPortals, boolean setFoodDown) {
        mapClass = new Map();
        map = mapClass.initialiseBoard(boardPath, useDefaultStart, useDefaultPortals);
        snake = new ArrayList<Pair>();
        // Get portal locations
        List<Pair> portalList = mapClass.getPortals(map);
        portal_location = new Pair[portalList.size()];
        for (int i = 0; i < portalList.size(); i++) {
            Pair portal = portalList.get(i);
            portal_location[i] = new Pair(portal.row, portal.column);
        }
        if (setFoodDown) {
            foodPair = mapClass.getNewFoodLocation(map);
            map[foodPair.row][foodPair.column] = 4;
        }
        height = map.length;
        width = map[0].length;
        Pair mapHead = mapClass.getDefaultStartPlace(map);
        snake.add(0, new Pair(mapHead.row, mapHead.column));
        snake.add(new Pair(mapHead.row + 1, mapHead.column));

    }

    public List<Pair> move() {
        // System.out.println("location: " + snake.get(0));
        switch (direction) {
            case 'd':
                moveHere(new Pair(snake.get(head).row + 1, snake.get(head).column));
                break;
            case 'u':
                moveHere(new Pair(snake.get(head).row - 1, snake.get(head).column));
                break;
            case 'r':
                moveHere(new Pair(snake.get(head).row, snake.get(head).column + 1));
                break;
            case 'l':
                moveHere(new Pair(snake.get(head).row, snake.get(head).column - 1));
                break;
            default:
                System.out.println("Snake doesn't know how to move");
                break;
        }
        return snake;
    }

    public void changeDirection(int code) {
        switch (code) {
            case 39:
            case 68:
                if (direction != 'l') {
                    direction = 'r';
                }
                break;
            case 37:
            case 65:
                if (direction != 'r') {
                    direction = 'l';
                }
                break;
            case 40:
            case 83:
                if (direction != 'u') {
                    direction = 'd';
                }
                break;
            case 38:
            case 87:
                if (direction != 'd') {
                    direction = 'u';
                }
                break;
            default:
                break;
        }
    }

    public void moveHere(Pair originalLocation) {

        int type = map[originalLocation.row][originalLocation.column];
        Pair newLocation = originalLocation;

        switch (type) {
            case Map.SOFTWALL:
                newLocation = throughWall(originalLocation);
                // TODO what happens if you go through a softwall straight into a portal?
                if (validPortal(originalLocation)) {
                    newLocation = portal(originalLocation);
                }
                move1(newLocation);
                if (map[newLocation.row][newLocation.column] == Map.FOOD) { // fix bug on not eating after softwall
                    grow1(newLocation, originalLocation);
                }
                break;
            case Map.PORTAL:
                newLocation = portal(originalLocation);
                if (map[newLocation.row][newLocation.column] == Map.SOFTWALL) {
                    newLocation = throughWall(newLocation);
                }
                move1(newLocation);
                if (map[newLocation.row][newLocation.column] == Map.FOOD) { // fix bug on not eating after portal
                    grow1(newLocation, originalLocation);
                }
                break;
            case Map.FOOD:
                move1(newLocation);
                grow1(newLocation, originalLocation);
                break;
            case Map.SPACE:
            case Map.SNAKE_START:
            case Map.SPACE_2:
                move1(newLocation);
                break;
            case Map.HARDWALL:
            case Map.SNAKE: //doesn't work, they are not on the board
            case Map.SNAKE_HEAD: // doesn't work, they are not on the board
                System.out.print("You are dead. ");
                dead = true;
                break;
            default:
                System.out.println("ERROR - Snake does not know where to move");
                break;
        }
    }

    public boolean isSnakeValidToMove(Pair newLocation){
        return !snake.contains(newLocation);
    }

    public void move1(Pair newLocation) {
        if (head == 0) {
            head = snake.size() - 1;
        } else {
            head = head - 1;
        }
        if(!isSnakeValidToMove(newLocation)){
            dead = true;
        }
        snake.set(head, newLocation);
    }

    public void grow1(Pair newLocation, Pair originalLocation) {
        System.out.println("Were in the food place");
        snake.add(head, newLocation);
        mapClass.setNewFoodLocation(map, originalLocation);
    }

    public Pair throughWall(Pair location) {
        switch (direction) {
            case 'l':
                for (int i = (location.column + 1); i < width; i++) {
                    if (map[location.row][i] == 1) {
                        location.column = --i;
                        return location;
                    }
                }
                break;
            case 'r':
                for (int i = (location.column - 1); i >= 0; i--) {
                    if (map[location.row][i] == 1) {
                        location.column = ++i;
                        return location;
                    }
                }
                break;
            case 'u':
                for (int i = (location.row + 1); i < height; i++) {
                    if (map[i][location.column] == 1) {
                        location.row = --i;
                        return location;
                    }
                }
                break;
            case 'd':
                for (int i = (location.row - 1); i >= 0; i--) {
                    if (map[i][location.column] == 1) {
                        location.row = ++i;
                        return location;
                    }
                }
                break;
            default:
                System.out.println("ERROR - Snake is confused about the Wall");
                return new Pair(0, 0);
        }
        return new Pair(0, 0);
    }

    // This method is called to move location to other portal
    public Pair portal(Pair originalLocation) {
        // WE need to know the direction and which portal it hits, then its the other
        // portal becomes the new head + direction of portal
        Pair loci = new Pair();
        for (int i = 0; i < portal_location.length; i++) {
            if (!portal_location[i].equals(originalLocation)) { // get the portal which does not have the snake on it.
                try {
                    loci = (Pair) portal_location[i].clone();
                } catch (CloneNotSupportedException e) {
                    System.out.println(e);
                }
                switch (direction) {
                    case 'l':
                        loci.column = loci.column - 1;
                        break;
                    case 'r':
                        loci.column = loci.column + 1;
                        break;
                    case 'u':
                        loci.row = loci.row - 1;
                        break;
                    case 'd':
                        loci.row = loci.row + 1;
                }
                if (map[loci.row][loci.column] == Map.SNAKE || map[loci.row][loci.column] == Map.HARDWALL) {
                    dead = true;
                }
                if (map[loci.row][loci.column] == Map.SOFTWALL) {
                    try {
                        loci = (Pair) portal_location[i == 0 ? 1 : 0].clone(); //Not good for more than 2 portals, but oh well.
                    } catch (CloneNotSupportedException e) {
                        System.out.println(e);
                    }
                    // Go opposite direction
                    switch (direction) {
                        case 'l':
                            loci.column = loci.column + 2;
                            direction = 'r';
                            break;
                        case 'r':
                            loci.column = loci.column - 2;
                            direction = 'l';
                            break;
                        case 'u':
                            loci.row = loci.row + 2;
                            direction = 'd';
                            break;
                        case 'd':
                            loci.row = loci.row - 2;
                            direction = 'u';
                            break;
                    }
                }
            }
        }
        return new Pair(loci.row, loci.column);
    }

    public void print() {
        for (int row = 0; row < 60; row++) {
            for (int col = 0; col < 60; row++) {
                System.out.print(printChars[map[row][col]]);
            }
            System.out.println();
        }
    }

    public void shuffleArray(int[] array) {
        Random rn = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rn.nextInt(i + 1);
            // Simple swap
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    public int getHead() {
        return head;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public char getDirection() {
        return this.direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public List<Pair> getSnake() {
        return snake;
    }

    public Pair getFoodLocation() {
        return foodPair;
    }

    public Pair[] getPortals() {
        return portal_location;
    }

    public boolean validPortal(Pair location) {
        for (int i = 0; i < portal_location.length; i++) {
            if (portal_location[i].equals(location))
                return true;
        }
        return false;
    }

    public boolean isDead() {
        return dead;
    }
}
