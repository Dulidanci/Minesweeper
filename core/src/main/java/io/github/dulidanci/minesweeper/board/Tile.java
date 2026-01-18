package io.github.dulidanci.minesweeper.board;

public class Tile {
    public boolean discovered;
    public boolean mine;
    public boolean flag;
    public int number;

    public Tile() {
        discovered = false;
        number = 0;
        mine = false;
        flag = false;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
