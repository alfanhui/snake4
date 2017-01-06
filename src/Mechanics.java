/**
 * Dundee university Quackathon
 *
 **/
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Random;


public class Mechanics{
    //Global variables
    public int map[][];
    public int head;
    public char direction;
    public List<Pair> portal_location;
    public List<Pair> snake;
    public boolean dead;
    public int height;
    public int width;
    //public final char printChars[] = new char[] {' ', '*', '.','@','~'};
    public Map newMap;
    public Pair foodPair;
    
    public boolean leftDirection = false;
    public boolean rightDirection = true;
    public boolean upDirection = false;
    public boolean downDirection = false;

    /*Constructor
     *
     */
    public Mechanics(int ROW, int COLUMN){
    	newMap = new Map();
        map = newMap.initialiseBoard(ROW, COLUMN);//new int[80][60];
        snake = new ArrayList<Pair>(2);
        //Get portal locations
        portal_location = newMap.getPortals(map);
        foodPair = newMap.getFoodLocation(map);
        height = newMap.COLUMN;
        width = newMap.ROW;
        snake = new ArrayList<Pair>(1);
        head = 0;
        Pair mapHead = newMap.getDefaultStartPlace(map);
        Pair snakeSeg = new Pair(mapHead.row, mapHead.column);
        Pair snakeSeg2 = new Pair(mapHead.row-1, mapHead.column);

        snake.add(0,snakeSeg);
        snake.add(1,snakeSeg2);
        System.out.println(snake.get(0).row + " : " + snake.get(0).column);
        System.out.println(snake.get(1).row + " : " + snake.get(1).column);

        dead = false;
        System.out.println("End of constructor");
        direction = 'u';
    }

    public void move() {
        if (downDirection == true){
            snake.get(head).column++;
	}
        else if (upDirection == true){
            snake.get(head).column--;
	}
        else if (rightDirection == true){
            snake.get(head).row++;
        }
	else if(leftDirection == true){
            snake.get(head).row--;
        }
    }

    public void checkCollision() {
        Pair coordinate = snake.get(head);
	int type = map[coordinate.row][coordinate.column];
        //System.out.println("TYPE: "+type);
        Pair newLocation = coordinate;
        if(coordinate.row == foodPair.row && coordinate.column == foodPair.column){
            System.out.println("Were in the food place");
            grow1(newLocation);
            move1(newLocation);
            foodPair = newMap.getFoodLocation(map);
        }else if (validPortal(coordinate)) {
            newLocation = portal();
            Pair newLocation2 = new Pair();
            //Pair coords = convertToMDA(newLocation);
            if (map[newLocation.row][newLocation.column] == 1) {
                newLocation2 = throughWall(newLocation);
            }
            move1(newLocation2);
        } else {
            switch (type) {
                case 1:
                    Pair newLocation2 = throughWall(coordinate);
                    move1(newLocation2);
                break;
                case 2:
                    dead = true;
                break;
                case 5:
                case 0:
                    //System.out.println("Type was 0, moving 1");
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
            head = (snake.size() - 1);
        } else {
            head -= 1;
        }
        snake.set(head, newLocation);

    }


    public void grow1(Pair newLocation) {
        snake.add(head,newLocation);

    }
    
    
    public Pair throughWall(Pair parts) {
        switch(direction) {
            case 'l':
                for (int i=(parts.column+1); i<width; i++) {
                    if (map[parts.row][i] == 1) {
                        return new Pair(parts.row,i);
                    }
                }break;
            case 'r':
                for (int i = (parts.column-1); i>=0; i--) {
                    if (map[parts.row][i] == 1) {
                        return new Pair(parts.row,i);
                    }
                }break;
            case 'u':
                for (int i = parts.row; i>=0; i--) {
                    if (map[i][parts.column] == 1) {
                        return new Pair(i,parts.column);
                    }
                }break;
            case 'd':
                for (int i = parts.row; i<height; i++) {
                    if (map[i][parts.column] == 1) {
                        return new Pair(i,parts.column);
                    }
                }break;
            default:
                System.out.println("ERROR - Snake is confused about the Wall");
                return null;
        }
        return null;
    }

    public Pair portal(){
        //WE need to know the direction and which portal it hits, then its the other portal becomes the new head + direction of portal
        boolean success = false;
        Pair loci = new Pair();
        //System.out.println("portal array: " + portal_location[0]);
        //shuffleArray(portal_location);
        //System.out.println("portal array shuffled: " + portal_location[0]);
        int index=0; //rn.nextInt() * portal_location.length;
        //System.out.println("Portal array size:" + portal_location.length);
        for(int i =0; i<portal_location.size();i++){
            index = i;
            if(portal_location.get(i) != snake.get(head)){
                loci = portal_location.get(i);
                if(map[loci.row][loci.column++] != 2 || map[loci.row][loci.column++]!=6){ //l
                    success = true;
                    break;
                }else if (map[loci.row][loci.column--] != 2 || map[loci.row][loci.column--]!=6){ //r
                    success = true;
                    break;
                }else if (map[loci.row++][loci.column] != 2 || map[loci.row++][loci.column]!=6){ //u
                    success = true;
                    break;
                }else if (map[loci.row--][loci.column] != 2 || map[loci.row--][loci.column]!=6){ //d
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

        }
        */
        return loci;
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

    public List<Pair> getSnake(){
         return snake;
    }

    public Pair getFoodLocation(){
        return foodPair;
    }

    public void setMap(int[][] map){
        this.map = map;
    }

    public List<Pair> getPortals(){
         return portal_location;
    }

    public boolean validPortal(Pair location){
        for(int i = 0; i< portal_location.size();i++){
            if((portal_location.get(i).row == location.row) && (portal_location.get(i).column == location.column)){
                return true;
            }
        }
        return false;
    }

    public boolean isDead(){
        if(dead)
            return true;
        return false;
    }
}
