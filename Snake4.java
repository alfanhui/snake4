/**
 * Dundee university Quackathon
 *
 **/
import java.util.*;
import java.util.EventObject;


public class Snake4{
    //Global variables
    public int map[][];
    public int head;
    public char direction;
    public int portal_location;
    public List<Integer> snake;
    public final char printChars[] = new char[] {' ', '*', '.','@','~'};

    /*Constructor
     *
     */
    public Snake4(){
        map = new int[60][60];
        snake = new ArrayList<Integer>(3);
        head = 0;
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
            break;
            case 2:
                // die
            break;
            case 3:
                newLocation = portal();
            break;
            case 4:
                // grow 1
            break;
            case 5:
            case 0:
            break;
            default:
                System.out.println("ERROR - Snake does not know where to move");
            break;
        }
        if (head == (snake.size() - 1)) {
            head = 0;
            snake.add(coordinate);
        } else {
            head = snake.size();
            snake.add(
        }


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

    public void portal(){

    }

    public void food(){

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
