package uk.ac.dundee;

/**
 * Dundee university Quackathon
 *
 **/
import java.util.*;
import java.util.Random;

public class Snake4 {
    // Global variables
    public int map[][];
    public int head;
    public char direction;
    public Pair[] portal_location;
    public List<Pair> snake;
    public boolean dead;
    public int height;
    public int width;
    public final char printChars[] = new char[] { ' ', '*', '.', '@', '~' };
    public Map mapClass;
    public Pair foodPair;

    /*
     * Constructor
     *
     */
    public Snake4() {
        mapClass = new Map();
        map = mapClass.initialiseBoard();
        snake = new ArrayList<Pair>();
        // Get portal locations
        List<Pair> portalList = mapClass.getPortals(map);
        portal_location = new Pair[portalList.size()];
        for (int i = 0; i < portalList.size(); i++) {
            Pair portal = portalList.get(i);
            portal_location[i] = new Pair(portal.row, portal.column);
        }
        foodPair = mapClass.getNewFoodLocation(map);
        map[foodPair.row][foodPair.column] = 4;
        height = map.length;
        width = map[0].length;
        head = 0;
        Pair mapHead = mapClass.getDefaultStartPlace(map);
        snake.add(0, new Pair(mapHead.row, mapHead.column));
        snake.add(new Pair(mapHead.row - 1, mapHead.column));
        dead = false;
        direction = 'u';
    }

    public void move() throws CloneNotSupportedException {
        //System.out.println("location: " + snake.get(0));
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
    }

    public void changeDirection(int code) {
        switch (code) {
            case 39:
            case 68:
                if (direction != 'r') {
                    direction = 'r';
                }
                break;
            case 37:
            case 65:
                if (direction != 'l') {
                    direction = 'l';
                }
                break;
            case 40:
            case 83:
                if (direction != 'd') {
                    direction = 'd';
                }
                break;
            case 38:
            case 87:
                if (direction != 'u') {
                    direction = 'u';
                }
                break;
            default:
                break;
        }
    }

    public void moveHere(Pair coordinate) throws CloneNotSupportedException {

        int type = map[coordinate.row][coordinate.column];
        Pair newLocation = coordinate;

        if (map[coordinate.row][coordinate.column] == Map.FOOD) {
            System.out.println("Were in the food place");
            grow1(newLocation);
            move1(newLocation);
            map = mapClass.setNewFoodLocation(map, coordinate);
        } else if (validPortal(coordinate)) {
            newLocation = portal(coordinate);

            if (map[newLocation.row][newLocation.column] == 1) {
            newLocation = throughWall(newLocation);
            }
            move1(newLocation);
        } else {
            switch (type) {
                case 1:
                    newLocation = throughWall(coordinate);
                    move1(newLocation);
                    break;
                case 2:
                    dead = true;
                    break;
                case 0:
                case 5:
                case 7:
                    move1(newLocation);
                    break;
                default:
                    System.out.println("ERROR - Snake does not know where to move");
                    break;
            }
        }
    }

    public void move1(Pair newLocation) {
        if (head == 0) {
            head = snake.size() - 1;
        } else {
            head = head - 1;
        }
        snake.set(head, newLocation);
    }

    public void grow1(Pair newLocation) {
        snake.add(head, newLocation);
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
    public Pair portal(Pair coordinate) throws CloneNotSupportedException {
        // WE need to know the direction and which portal it hits, then its the other
        // portal becomes the new head + direction of portal
        Pair loci = new Pair();
        for (int i = 0; i < portal_location.length; i++) {
            if (!portal_location[i].equals(coordinate)) { // get the portal which does not have the snake on it.
                loci = (Pair) portal_location[i].clone();
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
                        break;
                }
                if (map[loci.row][loci.column] == Map.SNAKE || map[loci.row][loci.column] == Map.HARDWALL) { // r
                    dead = true;
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

    public int[][] getMap() {
        return map;
    }

    public List<Pair> getSnake() {
        return snake;
    }

    public Pair getFoodLocation() {
        return foodPair;
    }

    public void setMap(int[][] map) {
        this.map = map;
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
        if (dead)
            return true;
        return false;
    }
}
