package io.github.dulidanci.minesweeper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.dulidanci.minesweeper.board.Board;
import io.github.dulidanci.minesweeper.screen.GameScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Minesweeper extends Game {
    public SpriteBatch batch;
    public FitViewport viewport;
    public static final int height = 8;
    public static final int width = 10;
    public static final int mines = 10;
    public Board board;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(width + 2, height + 2);

        this.setScreen(new GameScreen(this));
        board = new Board(height, width);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
