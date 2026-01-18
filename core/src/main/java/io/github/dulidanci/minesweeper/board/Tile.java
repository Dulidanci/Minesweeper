package io.github.dulidanci.minesweeper.board;

public class Tile {
    public int x;
    public int y;
    public boolean discovered;
    public boolean mine;
    public int number;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        discovered = false;
        number = 0;
        mine = false;
    }

    public Tile(int x, int y, boolean discovered, boolean mine, int number) {
        this.x = x;
        this.y = y;
        this.discovered = discovered;
        this.mine = mine;
        this.number = number;
    }

    public Tile setDiscovered(boolean discovered) {
        this.discovered = discovered;
        return this;
    }

    public Tile setMine(boolean mine) {
        this.mine = mine;
        return this;
    }

    public Tile setNumber(int number) {
        this.number = number;
        return this;
    }
}
