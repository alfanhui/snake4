package uk.ac.dundee;
public class Pair {
    public int row;
    public int column;

    public Pair(int r, int c) {
        row = r;
        column = c;
    }

    @Override
    public String toString() {
        return "(" + row + "," + column + ")";
    }

    public Pair() {
        // TODO Auto-generated constructor stub
    }
}
