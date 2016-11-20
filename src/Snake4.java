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
    	newMap = new Map();
        map = newMap.initialiseBoard();//new int[80][60];
        snake = new ArrayList<Integer>();
        //Get portal locations
        List<Pair> portalList = newMap.getPortals(map);
        System.out.println("hello");
        portal_location = new int[portalList.size()];
        for(int i = 0; i<portalList.size();i++){
            Pair portal = portalList.get(i);
            portal_location[i] = convertToInt(portal.row, portal.column);
            System.out.println("Portal:" +portal.row+":"+portal.column);
        }
        foodPair = newMap.getFoodLocation(map);
        height = newMap.COLUMN;
        width = newMap.ROW;
        snake = new ArrayList<Integer>(1);
        head = 0;
        Pair mapHead = newMap.getDefaultStartPlace(map);
        snake.add(0,convertToInt(mapHead.row, mapHead.column));
        snake.add(convertToInt(mapHead.row-1,mapHead.column));
        dead = false;
        System.out.println("End of constructor");
        direction = 'u';
    }

    public void move() {
        int location;
        System.out.println("Startlocation: "+snake.get(0));
        switch(direction) {
            case 'd':
                location =snake.get(head) + 100;
                moveHere(location);
            break;
            case 'u':
                location = snake.get(head) - 100;
                moveHere(location);
            break;
            case 'r':
                location = snake.get(head) + 1;
                moveHere(location);
            break;
            case 'l':
                location = snake.get(head) - 1;
                System.out.println("Location: "+location);
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
        case 39:
            if (direction != 'r') {
                coordinate = snake.get(head) + 1;
                direction = 'r';
            }
         break;
         case 37:
            if (direction != 'l') {
                coordinate = snake.get(head) - 1;
                direction = 'l';
            }
         break;
         case 40:
            if (direction != 'd') {
                coordinate = snake.get(head) + 100;
                direction = 'd';
            }
        break;
        case 38:
            if (direction != 'u') {
                coordinate = snake.get(head) - 100;
                direction = 'u';
            }
        break;
        default:
        break;
        }
    }

    public void moveHere(int coordinate) {
        int[] parts = convertToMDA(coordinate);
        System.out.println("Location in parts. Part 1: "+parts[0]+" Part 2: "+parts[1]);
        int[] foodLoci = new int[] {foodPair.row,foodPair.column};
        System.out.println("food location:" + foodPair.row + " : " + foodPair.column);
        int type = map[parts[0]][parts[1]];
        System.out.println("TYPE: "+type);
        int newLocation = coordinate;
        if(parts == foodLoci){
            System.out.println("Were in the food place");
            grow1(newLocation);
            move1(newLocation);
            foodPair = newMap.getFoodLocation(map);
        }else if (validPortal(coordinate)) {
            newLocation = portal();
            int[] coords = convertToMDA(newLocation);
            if (map[coords[0]][coords[1]] == 1) {
                newLocation = throughWall(coords);
            }
            move1(newLocation);
        } else {
            switch (type) {
                case 1:
                    newLocation = throughWall(parts);
                    move1(newLocation);
                break;
                case 2:
                    dead = true;
                break;
                case 5:
                case 0:
                    System.out.println("Type was 0, moving 1");
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
                for (int i=parts[1]; i>width; i++) {
                    if (map[parts[0]][i] == 1) {
                        return convertToInt(parts[0],i);
                    }
                }
            case 'r':
                for (int i = parts[1]; i<=0; i--) {
                    if (map[parts[0]][i] == 1) {
                        return convertToInt(parts[0],i);
                    }
                }
            case 'u':
                for (int i = parts[0]; i<=0; i--) {
                    if (map[i][parts[1]] == 1) {
                        return convertToInt(i,parts[1]);
                    }
                }
            case 'd':
                for (int i = parts[0]; i>height; i++) {
                    if (map[i][parts[1]] == 1) {
                        return convertToInt(i,parts[1]);
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
        int loci[] = new int[2];
        System.out.println("portal array: " + portal_location[0]);
        shuffleArray(portal_location);
        System.out.println("portal array shuffled: " + portal_location[0]);
        int index=0; //rn.nextInt() * portal_location.length;
        System.out.println("Portal array size:" + portal_location.length);
        for(int i =0; i<portal_location.length;i++){
            index = i;
            if(portal_location[i] != head){
                loci = convertToMDA(portal_location[i]);
                System.out.println("Should be the same as above: " + Integer.toString(loci[0]) + Integer.toString(loci[1]));
                System.out.println("map value:" + map[loci[0]][loci[1]++]);
                if(map[loci[0]][loci[1]+1] != 2 || map[loci[0]][loci[1]++]!=6){ //l
                    success = true;
                    break;
                }else if (map[loci[0]][loci[1]--] != 2 || map[loci[0]][loci[1]--]!=6){ //r
                    success = true;
                    break;
                }else if (map[loci[0]++][loci[1]] != 2 || map[loci[0]++][loci[1]]!=6){ //u
                    success = true;
                    break;
                }else if (map[loci[0]--][loci[1]] != 2 || map[loci[0]--][loci[1]]!=6){ //d
                    success = true;
                    break;
                }
            }
        }
        /**
        if(!success){
            loci = convertToMDA(portal_location[index]);
            if(map[loci[0]][loci[1]+=1] != 2 || map[loci[0]][loci[1]+=1]!=6){
            }else if (map[loci[0]][loci[1]-=1] != 2 || map[loci[0]][loci[1]-=1]!=6){
            }else if (map[loci[0]+=1][loci[1]] != 2 || map[loci[0]+=1][loci[1]]!=6){
            }else if (map[loci[0]-=1][loci[1]] != 2 || map[loci[0]-=1][loci[1]]!=6){
            }
            else
                dead = true;
        */
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

    public int[] getPortals(){
         return portal_location;
    }

    public boolean validPortal(int location){
        for(int i = 0; i< portal_location.length;i++){
            if (portal_location[i] == location)
                return true;
        }
        return false;
    }
}
