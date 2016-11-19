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
=======
        int height = 60;
        int width = 80;
        map = new int[height][width];
        snake = new ArrayList<Integer>(3);
>>>>>>> f074a174fa2b753191b1aa6ae3bdca237d8462ad
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
                direction = 'r';
                moveHere(coordinate);
            }
         break;
         case 37:
            if (direction != 'l') {
                cordinate = head - 100;
                direction = 'l';
                moveHere(coordinate);
            }
         break;
         case 40:
            if (direction != 'd') {
                cordinate = head + 1;
                direction = 'd';
                moveHere(coordinate);
            }
        break;
        case 38:
            if (direction != 'u') {
                cordinate = head - 1;
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
        int type = map[part[0]][parts[1]];
        int newLocation = coordinate;
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
                move1(newLocation);
            break;
            case 4:
                grow1(newLocation);
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
            break;
            case 'r':
                for (int i = parts[0]; i<=0; i--) {
                    if (map[i][parts[1]] == 1) {
                        return convertToInt(i,parts[1]);
                    }
                }
            break;
            case 'u':
                for (int i = parts[1]; i<=0; i--) {
                    if (map[parts[0]][i] {
                        return convertToInt(parts[0],i);
                    }
                }
            break;
            case 'd':
                for (int i = parts[1]; i>height; i++) {
                    if (mapts[parts[0]][i] == 1) {
                        return convertToInt(parts[0],i);
                    }
                }
            break;
            default:
                System.out.println("ERROR - Snake is confused about the Wall");
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


}
