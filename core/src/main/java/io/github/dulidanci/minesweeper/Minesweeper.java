package io.github.dulidanci.minesweeper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.dulidanci.minesweeper.board.Board;
import io.github.dulidanci.minesweeper.screen.GameScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Minesweeper extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;
    public static final int height = 20;
    public static final int width = 25;
    public static final int mines = 100;
    public Board board;
    public boolean won;
    public boolean lost;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FitViewport(width + 2, height + 2);

        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());

        this.setScreen(new GameScreen(this));
        board = new Board(height, width, this);

        won = false;
        lost = false;
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }


}
