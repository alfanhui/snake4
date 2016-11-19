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
    public final char printChars[] = new char[] {' ', '*', '.','@','~'};

    /*Constructor
     *
     */
    public Snake4(){
        map = new int[80][60];
        snake = new ArrayList<Integer>(3);
        //Get portal locations

    }

    public void move(KeyEvent event) {
        int code =  event.getKeyCode();
        switch (code) {
            case 39:
            if (direction != 'r') {
                direction = 'r';
            }
         break;
         case 37:
            if (direction != 'l') {
                direction = 'l';
            }
         break;
         case 40:
            if (direction != 'd') {
                direction = 'd';
            }
        break;
        case 38:
            if (direction != 'u') {
                direction = 'u';
            }
        break;
        default:
        break;
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
        Random rn = new Random();
        int index = rn.nextInt() * portal_location.length;
        while(portal_location[index] == head){
            index = rn.nextInt() * portal_location.length;

        }

        return portal_location[index];
    }

    public int food(){

    }

    public static void main(String args[]) throws InterruptedException {
        System.out.println("Welcome to Snake4!");
        Snake4 game = new Snake4();
        //game loop
        while(true){
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
