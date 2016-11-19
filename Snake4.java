/**
 * Dundee university Quackathon
 *
 **/
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
    public final char printChars[] = new char[] {' ', '*', '.','@','~'};

    /*Constructor
     *
     */
    public Snake4(){
        map = new int[80][60];
        snake = new ArrayList<Integer>();
        //Get portal locations
        head = 0;
        dead = false;
    }

    public void move(KeyEvent event) {
	    int code =  event.getKeyCode();
        int coordinate;
        switch (code) {
        case 39:
            if (direction != 'r') {
                cordinate = head + 100;
                moveHere(coordinate);
                direction = 'r';
            }
         break;
         case 37:
            if (direction != 'l') {
                cordinate = head - 100;
                moveHere(coordinate);
                direction = 'l';
            }
         break;
         case 40:
            if (direction != 'd') {
                cordinate = head + 1;
                moveHere(coordinate);
                direction = 'd';
            }
        break;
        case 38:
            if (direction != 'u') {
                cordinate = head - 1;
                moveHere(coordinate);
                direction = 'u';
            }
        break;
        default:
        break;
        }
    }

    public void moveHere(int coordinate) {
        int[] parts = convertToMDA(coordinate);
        int type = map[part[0]][parts[1]];
        int newLocation = coordinate;
        switch (type) {
            case 1:
                newLocation = throughWall(parts);
                move1(newLocation);
            break;
            case 2:
                // die
            break;
            case 3:
                newLocation = portal();
                move1(newLocation);
            break;
            case 4:
                grow1(newLocation);
            break;
            case 5:
            case 0:
            break;
            default:
                System.out.println("ERROR - Snake does not know where to move");
            break;
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
    public void grow1() {


    }
    public int throughWall(int[] parts) {

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
        int homePortal;
        int loci[];
        Random rn = new Random();
        shuffleArray(portal_location);
        int index ; //rn.nextInt() * portal_location.length;
        for(int i =0; i<portal_location.length;i++){
            index = i;
            if(portal_location[i] == head)
                homePortal = i;
                continue;
            loci = convertToInt(portal_location[i]);
            if(map[loci[0]][loci[1]+1] != 2 || map[loci[0]][loci[1]+1]!=6){
                success = true;
                break;
            }else if (map[loci[0]][loci[1]-1] != 2 || map[loci[0]][loci[1]-1]!=6){
                success = true;
                break;
            }else if (map[loci[0]+1][loci[1]] != 2 || map[loci[0]+1][loci[1]]!=6){
                success = true;
                break;
            }else if (map[loci[0]-1][loci[1]] != 2 || map[loci[0]-1][loci[1]]!=6){
                success = true;
                break;
            }
        }
        if(!success){
            index = homePortal
            loci = convertToInt(portal_location[homePortal]);
            if(map[loci[0]][loci[1]+1] != 2 || map[loci[0]][loci[1]+1]!=6){
                break;
            }else if (map[loci[0]][loci[1]-1] != 2 || map[loci[0]][loci[1]-1]!=6){
                break;
            }else if (map[loci[0]+1][loci[1]] != 2 || map[loci[0]+1][loci[1]]!=6){
                break;
            }else if (map[loci[0]-1][loci[1]] != 2 || map[loci[0]-1][loci[1]]!=6){
                break;
            }else
                dead = true;
        }

        return portal_location[index];
    }

    public void shuffleArray(int[] array){
        Random rn = Random();
        for (int i = array.length - 1; i > 0; i--){
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = array[index];
            array[index] = ar[i];
            array[i] = a;
        }
    }


    public int food(){

    }

    public static void main(String args[]) throws InterruptedException {
        System.out.println("Welcome to Snake4!");
        Snake4 game = new Snake4();
        //game loop
        while(!dead){
           game.move();
           Thread.sleep(40);
        }
    }

    public int[] covert2MDA(int location){
        String intString = Integer.toString(location);
        int mid = intString.length()/2;
        int[] parts = {Integer.parseInt(intString.substring(0, mid)),Integer.parseInt(intString.substring(mid))};
        return parts;
    }

    public int convert2Int(int row, int col){
         String stringInt = (Integer.toString(row) + Integer.toString(col));
         return Integer.parseInt(stringInt);
    }


}
