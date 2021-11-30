package uk.ac.dundee;

public class Pair {
    public int row;
    public int column;

    public Pair (int row, int column){
        this.row = row;
        this.column = column;
    }

    public Pair() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Pair other = (Pair) obj;
        if (this.row != other.row && this.column != other.column) {
            return false;
        }

        return true;
    }

    @Override
    public String toString(){
        return this.row + ":" + this.column;
    }

}
