/**
 * Dundee university Quackathon
 *
 **/
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Random;


public class Snake4{
    //Global variables
    public int map[][];
    public int head;
    public char direction;
    public int[] portal_location;
    public List<Integer> snake;
    public boolean dead;
    public int height;
    public int width;
    public final char printChars[] = new char[] {' ', '*', '.','@','~'};
    public Map newMap;
    public Pair foodPair;

    /*Constructor
     *
     */
    public Snake4(){
    	System.out.println("breakpoint");
    	newMap = new Map();
    	System.out.println("breakpoint");
        //map = newMap.initialiseBoard();//new int[80][60];
        System.out.println("breakpoint");
        snake = new ArrayList<Integer>();
        //Get portal locations
        List<Pair> portals = new ArrayList<Pair>();
        Pair portalPair = new Pair();
        portals = newMap.getPortals(map);
        portal_location = new int[portals.size()];
        for(int i = 0; i<portals.size();i++){
            portalPair = portals.get(i);
            portal_location[i] = convertToInt(portalPair.row,portalPair.column);
        }
        height = newMap.COLUMN;
        width = newMap.ROW;
        snake = new ArrayList<Integer>(1);
        head = 0;
        System.out.print(' ');
        Pair mapHead = newMap.getDefaultStartPlace(map);
        snake.add(0,convertToInt(mapHead.row, mapHead.column));
        dead = false;
        System.out.println("End of constructor");
    }

    public void move() {
        int location;
        switch(direction) {
            case 'r':
                location =snake.get(head) + 100;
                moveHere(location);
            break;
            case 'l':
                location = snake.get(head) - 100;
                moveHere(location);
            break;
            case 'd':
                location = snake.get(head) + 1;
                moveHere(location);
            break;
            case 'u':
                location = snake.get(head) - 1;
                moveHere(location);
            break;
            default:
                System.out.println("Snake doesn't know how to move");
            break;
        }
    }

    public void changeDirection(int code) {
        int coordinate;
        switch (code) {
        case KeyEvent.VK_RIGHT:
            if (direction != 'r') {
                coordinate = head + 100;
                direction = 'r';
                moveHere(coordinate);
            }
         break;
         case KeyEvent.VK_LEFT:
            if (direction != 'l') {
                coordinate = head - 100;
                direction = 'l';
                moveHere(coordinate);
            }
         break;
         case KeyEvent.VK_DOWN:
            if (direction != 'd') {
                coordinate = head + 1;
                direction = 'd';
                moveHere(coordinate);
            }
        break;
        case KeyEvent.VK_UP:
            if (direction != 'u') {
                coordinate = head - 1;
                direction = 'u';
                moveHere(coordinate);
            }
        break;
        default:
        break;
        }
    }

    public void moveHere(int coordinate) {
        int[] parts = convertToMDA(coordinate);
        int[] foodLoci = new int[] {foodPair.row,foodPair.column};
        int type = map[parts[0]][parts[1]];
        int newLocation = coordinate;
        //If food
        if(parts == foodLoci){
            grow1(newLocation);
            foodPair = newMap.getFoodLocation(map);
            move1(newLocation);
        }else{
            switch (type) {
                case 1:
                    newLocation = throughWall(parts);
                    move1(newLocation);
                break;
                case 2:
                    dead = true;
                break;
                case 3:
                    newLocation = portal();
                    int[] coords = convertToMDA(newLocation);
                    if (map[coords[0]][coords[1]] == 1) {
                        newLocation = throughWall(coords);
                    }
                    move1(newLocation);
                break;
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
    public void move1(int newLocation) {
        if (head == 0) {
            head = snake.size() - 1;
        } else {
            head = head - 1;
        }
        snake.set(head, newLocation);

    }
    public void grow1(int newLocation) {
        snake.add(head,newLocation);

    }
    public int throughWall(int[] parts) {
        switch(direction) {
            case 'l':
                for (int i=parts[0]; i>width; i++) {
                    if (map[i][parts[1]] == 1) {
                        return convertToInt(i,parts[1]);
                    }
                }
            case 'r':
                for (int i = parts[0]; i<=0; i--) {
                    if (map[i][parts[1]] == 1) {
                        return convertToInt(i,parts[1]);
                    }
                }
            case 'u':
                for (int i = parts[1]; i<=0; i--) {
                    if (map[parts[0]][i] == 1) {
                        return convertToInt(parts[0],i);
                    }
                }
            case 'd':
                for (int i = parts[1]; i>height; i++) {
                    if (map[parts[0]][i] == 1) {
                        return convertToInt(parts[0],i);
                    }
                }
            default:
                System.out.println("ERROR - Snake is confused about the Wall");
                return -1;
        }
    }

    public void print(){
        for(int row = 0; row <60; row++){
            for(int col =0; col <60; row++){
                System.out.print(printChars[map[row][col]]);
            }
            System.out.println();
        }
    }

    public int portal(){
        //WE need to know the direction and which portal it hits, then its the other portal becomes the new head + direction of portal
        boolean success = false;
        int homePortal = 0;
        int loci[] = new int[2];
        Random rn = new Random();
        shuffleArray(portal_location);
        int index ; //rn.nextInt() * portal_location.length;
        for(int i =0; i<portal_location.length;i++){
            index = i;
            if(portal_location[i] == head){
                homePortal = i;
                continue;
            }
            loci = convertToMDA(portal_location[i]);
            if(map[loci[0]][loci[1]+=1] != 2 || map[loci[0]][loci[1]+=1]!=6){ //d

                success = true;
                break;
            }else if (map[loci[0]][loci[1]-=1] != 2 || map[loci[0]][loci[1]-=1]!=6){ //u
                success = true;
                break;
            }else if (map[loci[0]+=1][loci[1]] != 2 || map[loci[0]+=1][loci[1]]!=6){ //r
                success = true;
                break;
            }else if (map[loci[0]-=1][loci[1]] != 2 || map[loci[0]-=1][loci[1]]!=6){ //l
                success = true;
                break;
            }
        }
        if(!success){
            index = homePortal;
            loci = convertToMDA(portal_location[homePortal]);
            if(map[loci[0]][loci[1]+=1] != 2 || map[loci[0]][loci[1]+=1]!=6){
            }else if (map[loci[0]][loci[1]-=1] != 2 || map[loci[0]][loci[1]-=1]!=6){
            }else if (map[loci[0]+=1][loci[1]] != 2 || map[loci[0]+=1][loci[1]]!=6){
            }else if (map[loci[0]-=1][loci[1]] != 2 || map[loci[0]-=1][loci[1]]!=6){
            }
            else
                dead = true;
        }
        return convertToInt(loci[0],loci[1]);
    }

    public void shuffleArray(int[] array){
        Random rn = new Random();
        for (int i = array.length - 1; i > 0; i--){
            int index = rn.nextInt(i + 1);
            // Simple swap
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }


    public int[] convertToMDA(int location){
        String intString = Integer.toString(location);
        int mid = intString.length()/2;
        int[] parts = {Integer.parseInt(intString.substring(0, mid)),Integer.parseInt(intString.substring(mid))};
        return parts;
    }

    public int convertToInt(int row, int col){
         String stringInt = (Integer.toString(row) + Integer.toString(col));
         return Integer.parseInt(stringInt);
    }

    public int[][] getMap(){
        return map;
    }

    public List<Integer> getSnake(){
         return snake;
    }

    public Pair getFoodLocation(){
        return foodPair;
    }

    public void setMap(int[][] map){
        this.map = map;
    }
}
