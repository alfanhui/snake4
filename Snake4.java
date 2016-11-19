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
    public char direction;

    /*Constructor
     *
     */
    public Snake4(){
        map = new int[60][60];

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

    public static void main(String args[]){
        System.out.println("Welcome to Snake4!");
        //game loop
        while(True){

        }
    }

}
