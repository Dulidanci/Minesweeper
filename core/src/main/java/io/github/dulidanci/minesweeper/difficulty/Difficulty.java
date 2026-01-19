package io.github.dulidanci.minesweeper.difficulty;

public enum Difficulty {
    EASY("Easy", 8, 10, 10),
    MEDIUM("Medium", 12, 15, 30),
    HARD("Hard", 20, 25, 100);

    private final String id;
    private final int height;
    private final int width;
    private final int mines;

    Difficulty(String id, int height, int width, int mines) {
        this.id = id;
        this.height = height;
        this.width = width;
        this.mines = mines;
    }

    public String getId() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getMines() {
        return mines;
    }
}
