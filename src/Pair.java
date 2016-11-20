public class Pair {
	private int row;
	private int column;

	Pair (int r, int c){
		row = r;
		column = c;
	}

    public int[] getValues(){
        int[] values = new int[] {row, column};
        return values;
    }
}
