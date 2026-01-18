package io.github.dulidanci.minesweeper.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.dulidanci.minesweeper.Minesweeper;
import io.github.dulidanci.minesweeper.board.Board;
import io.github.dulidanci.minesweeper.board.Tile;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    public final Minesweeper game;
    public SpriteBatch batch;
    public FitViewport viewport;
    public Texture mineTexture;
    public Texture flagTexture;
    public Texture tileOpenTexture;
    public Texture tileCloseTexture;
    public List<Texture> numberTextures;


    Vector2 mousePos;


    public GameScreen(Minesweeper game) {
        this.game = game;
        this.batch = this.game.batch;
        this.viewport = this.game.viewport;

        mineTexture = new Texture("mine.png");
        flagTexture = new Texture("flag.png");
        tileOpenTexture = new Texture("tile_open.png");
        tileCloseTexture = new Texture("tile_close.png");

        numberTextures = List.of(
            new Texture("1.png"),
            new Texture("2.png"),
            new Texture("3.png"),
            new Texture("4.png"),
            new Texture("5.png"),
            new Texture("6.png"),
            new Texture("7.png"),
            new Texture("8.png")
        );

        mousePos = new Vector2();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        input();
        draw();
    }

    public void input() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            mousePos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(mousePos);

            game.board.clicked((int) mousePos.x - 1, (int) mousePos.y - 1);
        }
    }

    public void draw() {
        ScreenUtils.clear(Color.GRAY);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        Board board = game.board;

        for (int i = 0; i < board.height; i++) {
            for (int j = 0; j < board.width; j++) {
                Tile tile = board.getTile(j, i);
                if (tile.discovered) {
                    batch.draw(tileOpenTexture, j + 1, i + 1, 1, 1);
                    if (tile.mine) {
                        batch.draw(mineTexture, j + 1, i + 1, 1, 1);
                    } else if (tile.number != 0 && tile.number < 9) {
                        batch.draw(numberTextures.get(tile.number - 1), j + 1, i + 1, 1, 1);
                    }
                } else {
                    batch.draw(tileCloseTexture, j + 1, i + 1, 1, 1);
                }
            }
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mineTexture.dispose();
        flagTexture.dispose();
        tileOpenTexture.dispose();
        tileCloseTexture.dispose();
        for (Texture texture : numberTextures) {
            texture.dispose();
        }
    }
}
