
/**
 * Dundee university Quackathon
 *
 **/
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Random;

public class Mechanics {
    // Global variables
    public int map[][];
    public int head;
    public char direction;
    public List<Pair> portal_location;
    public List<Pair> snake;
    public boolean dead;
    public int height;
    public int width;
    // public final char printChars[] = new char[] {' ', '*', '.','@','~'};
    public Map newMap;
    public Pair foodPair;

    public boolean leftDirection = false;
    public boolean rightDirection = true;
    public boolean upDirection = false;
    public boolean downDirection = false;

    /*
     * Constructor
     *
     */
    public Mechanics(int ROW, int COLUMN) {
        newMap = new Map();
        map = newMap.initialiseBoard(ROW, COLUMN);// new int[80][60];
        snake = new ArrayList<Pair>(2);
        // Get portal locations
        portal_location = newMap.getPortals(map);
        foodPair = newMap.getFoodLocation(map);
        height = newMap.COLUMN;
        width = newMap.ROW;
        snake = new ArrayList<Pair>(1);
        head = 0;
        Pair mapHead = newMap.getDefaultStartPlace(map);
        Pair snakeSeg = new Pair(mapHead.row, mapHead.column);

        snake.add(0, snakeSeg);

        System.out.println(snake.get(0).row + " : " + snake.get(0).column);

        dead = false;
        System.out.println("End of constructor");
        direction = 'r';
    }

    public void move() {
        if (downDirection == true) {
            direction = 'd';
            snake.add(new Pair(snake.get(0).row, snake.get(0).column++));
            // snake.get(0).column++;
        } else if (upDirection == true) {
            direction = 'u';
            snake.add(new Pair(snake.get(0).row, snake.get(0).column--));
            // snake.get(0).column--;
        } else if (rightDirection == true) {
            direction = 'r';
            snake.add(new Pair(snake.get(0).row++, snake.get(0).column));
            // snake.get(0).row++;
        } else if (leftDirection == true) {
            direction = 'l';
            snake.add(new Pair(snake.get(0).row--, snake.get(0).column));
            // snake.get(0).row--;
        }
        // System.out.println("PRINTING SNAKE LIMBS");
        // for (Pair limb : snake) {
        // System.out.println(limb.toString());
        // }
    }

    public void checkCollision() {
        Pair coordinate = snake.get(head);
        int type = 0;
        System.out.println(coordinate.row + " : " + coordinate.column);
        try {
            type = map[coordinate.row][coordinate.column];
        } catch (Exception e) {
            System.out.println("MAP length: " + map.length + ": " + map[0].length);
            System.out.println(e);
        }
        // System.out.println("TYPE: "+type);
        Pair newLocation = coordinate;
        if (coordinate.row == foodPair.row && coordinate.column == foodPair.column) {
            System.out.println("HIT Food");
            grow1(newLocation);
            move1(newLocation);
            foodPair = newMap.getFoodLocation(map);
        } else if (validPortal(coordinate)) {
            System.out.println("HIT PORTAL");
            newLocation = portal();
            Pair newLocation2 = new Pair();
            // Pair coords = convertToMDA(newLocation);
            if (map[newLocation.row][newLocation.column] == 1) {
                newLocation2 = throughWall(newLocation);
            }
            move1(newLocation2);
        } else {
            switch (type) {
            case 1:
                System.out.println("CASE 1?");
                Pair newLocation2 = throughWall(coordinate);
                move1(newLocation2);
                break;
            case 2:
                System.out.println("CASE 2: DEAD");
                dead = true;
                break;
            // add case 4 .. food..
            case 5:
            case 0:
                move1(newLocation);
                break;
            default:
                System.out.println("ERROR - Snake does not know where to move");
                break;
            }
        }
    }

    public void move1(Pair newLocation) {
        // System.out.println("head: " + head);
        // if (head == 0) {
        // head = (snake.size() - 1);
        // } else {
        // head -= 1;
        // }
        // snake.add(head, newLocation);
        if (snake.size() > 1) {
            snake.remove(snake.size() - 1);
        }
    }

    public void grow1(Pair newLocation) {
        System.out.println("Snake length: " + snake.size() + " GROWING...\nHEAD: " + head + "\nNEW LOCATION: "
                + newLocation.row + ":" + newLocation.column);

        snake.add(newLocation);
    }

    public Pair throughWall(Pair parts) {
        switch (direction) {
        case 'l':
            for (int i = (parts.row + 1); i < map.length; i++) {
                if (map[i][parts.column] == 1) {
                    return new Pair(i, parts.column);
                }
            }
            break;
        case 'r':
            for (int i = (parts.row - 1); i >= 0; i--) {
                if (map[i][parts.column] == 1) {
                    return new Pair(i, parts.column);
                }
            }
            break;
        case 'u':
            for (int i = parts.column + 1; i < map[0].length; i++) {
                if (map[parts.row][i] == 1) {
                    return new Pair(parts.row, i);
                }
            }
            break;
        case 'd':
            for (int i = parts.column - 1; i >= 0; i--) {
                if (map[parts.row][i] == 1) {
                    return new Pair(parts.row, i);
                }
            }
            break;
        default:
            System.out.println("ERROR - Snake is confused about the Wall");
            return null;
        }
        return null;
    }

    public Pair portal() {
        // WE need to know the direction and which portal it hits, then its the other
        // portal becomes the new head + direction of portal
        boolean success = false;
        Pair loci = new Pair();
        // System.out.println("portal array: " + portal_location[0]);
        // shuffleArray(portal_location);
        // System.out.println("portal array shuffled: " + portal_location[0]);
        int index = 0; // rn.nextInt() * portal_location.length;
        // System.out.println("Portal array size:" + portal_location.length);
        for (int i = 0; i < portal_location.size(); i++) {
            index = i;
            if (portal_location.get(i) != snake.get(head)) {
                loci = portal_location.get(i);
                if (map[loci.row][loci.column++] != 2 || map[loci.row][loci.column++] != 6) { // l
                    success = true;
                    break;
                } else if (map[loci.row][loci.column--] != 2 || map[loci.row][loci.column--] != 6) { // r
                    success = true;
                    break;
                } else if (map[loci.row++][loci.column] != 2 || map[loci.row++][loci.column] != 6) { // u
                    success = true;
                    break;
                } else if (map[loci.row--][loci.column] != 2 || map[loci.row--][loci.column] != 6) { // d
                    success = true;
                    break;
                }
            }
        }
        /**
         * if(!success){ loci = convertToMDA(portal_location[index]);
         * if(map[loci[0]][loci[1]+=1] != 2 || map[loci[0]][loci[1]+=1]!=6){ }else if
         * (map[loci[0]][loci[1]-=1] != 2 || map[loci[0]][loci[1]-=1]!=6){ }else if
         * (map[loci[0]+=1][loci[1]] != 2 || map[loci[0]+=1][loci[1]]!=6){ }else if
         * (map[loci[0]-=1][loci[1]] != 2 || map[loci[0]-=1][loci[1]]!=6){ } else dead =
         * true;
         * 
         * }
         */
        return loci;
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

    public int[] convertToMDA(int location) {
        String intString = Integer.toString(location);
        int mid = intString.length() / 2;
        int[] parts = { Integer.parseInt(intString.substring(0, mid)), Integer.parseInt(intString.substring(mid)) };
        return parts;
    }

    public int convertToInt(int row, int col) {
        String stringInt = (Integer.toString(row) + Integer.toString(col));
        return Integer.parseInt(stringInt);
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

    public List<Pair> getPortals() {
        return portal_location;
    }

    public boolean validPortal(Pair location) {
        for (int i = 0; i < portal_location.size(); i++) {
            if ((portal_location.get(i).row == location.row) && (portal_location.get(i).column == location.column)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDead() {
        return dead;
    }
}
