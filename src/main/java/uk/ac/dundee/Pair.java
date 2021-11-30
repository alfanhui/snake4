package uk.ac.dundee;

import java.util.Objects;

public class Pair implements Cloneable{
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
        if (obj == this) return true;
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair other = (Pair) obj;
        return this.row == other.row &&
                this.column == other.column;
    }

    protected Object clone() throws CloneNotSupportedException{
        Pair dolly = (Pair) super.clone();
        return dolly;
     }

    @Override
    public int hashCode() {
        return Objects.hash(this.row, this.column);
     }

    @Override
    public String toString(){
        return this.row + ":" + this.column;
    }

}
