package io.github.dulidanci.minesweeper.board;

import io.github.dulidanci.minesweeper.Minesweeper;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Minesweeper minesweeper;
    private final Tile[][] grid;
    public final int height;
    public final int width;
    public boolean firstClick;
    public int flagCount;

    public Board(int height, int width, Minesweeper minesweeper) {
        this.minesweeper = minesweeper;
        this.height = height;
        this.width = width;
        this.grid = new Tile[height][width];
        firstClick = true;
        flagCount = 0;
        prepare();
    }

    public void prepare() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                setTile(j, i, new Tile());
            }
        }
    }

    public void reset() {
        firstClick = true;
        flagCount = 0;
        prepare();
        minesweeper.won = false;
        minesweeper.lost = false;
    }

    public boolean validateInput(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Tile getTile(int x, int y) {
        if (!validateInput(x, y)) return null;
        return grid[y][x];
    }

    public void setTile(int x, int y, Tile tile) {
        if (!validateInput(x, y)) return;
        grid[y][x] = tile;
    }

    public void clicked(int x, int y, boolean rightClick) {
        if (validateInput(x, y)) {
            if (rightClick) {
                rightClicked(x, y);
            } else {
                Tile tile = getTile(x, y);
                if (tile.flag) {
                    rightClicked(x, y);
                } else {
                    leftClicked(x, y);
                }
            }
        }
        checkWin();
        countFlag();
    }

    private void leftClicked(int x, int y) {
        if (!validateInput(x, y)) return;

        if (firstClick) {
            calculate(x, y);
            firstClick = false;
        }

        Tile clickedTile = getTile(x, y);
        if (!clickedTile.discovered) {
            clickedTile.setDiscovered(true);

            if (clickedTile.mine) {
                exploded();
            } else if (clickedTile.number == 0) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        leftClicked(x + i, y + j);
                    }
                }
            }
        }
    }

    private void rightClicked(int x, int y) {
        if (!validateInput(x, y)) return;
        Tile tile = getTile(x, y);
        tile.setFlag(!tile.flag);
    }


    private void calculate(int excludeX, int excludeY) {
        List<Position> indexes = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                indexes.add(new Position(j, i));
            }
        }
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (validateInput(excludeX + i, excludeY + j)) {
                    indexes.remove(new Position(excludeX + i, excludeY + j));
                }
            }
        }

        List<Position> mines = new ArrayList<>();
        for (int i = 0; i < Minesweeper.mines; i++) {
            int chosen = (int) (Math.random() * indexes.size());
            mines.add(indexes.get(chosen));
            indexes.remove(chosen);
        }

        for (Position position : mines) {
            Tile tile = getTile(position.x(), position.y());
            tile.setMine(true);
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int count = 0;
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if (validateInput(j + l, i + k)) {
                            if (getTile(j + l, i + k).mine) {
                                count++;
                            }
                        }
                    }
                }
                getTile(j, i).setNumber(count);
            }
        }
    }

    public void exploded() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (getTile(j, i).mine) getTile(j, i).setDiscovered(true);
            }
        }
        minesweeper.lost = true;
    }

    public void checkWin() {
        int undiscovered = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile tile = getTile(j, i);
                if (!tile.discovered) {
                    undiscovered++;
                }
            }
        }
        if (undiscovered == Minesweeper.mines) {
            minesweeper.won = true;
        }
    }

    public void countFlag() {
        int count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile tile = getTile(j, i);
                if (!tile.discovered && tile.flag) {
                    count++;
                }
            }
        }
        flagCount = count;
    }
}
