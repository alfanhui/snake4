/**
 * Dundee university Quackathon
 *
 **/




public class Snake4{
    //Global variables
    public int map[][];
    public int location[][];
    public int head[][];
    public int tail[][];
    public int current_position[][];
    public final char printChars[] = new char[] {' ', '*', '.','@','~'};

    /*Constructor
     *
     */
    public Snake4(){
        map = new int[60][60];

    }

    public void move() {

    }

    public void print(){
        for(int row = 0; row <60; row++){
            for(int col =0; col <60; row++){
                System.out.print(printChars[map[row][col]]);
            }
            System.out.println();
        }
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

}
