package io.github.dulidanci.minesweeper.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.dulidanci.minesweeper.Minesweeper;
import io.github.dulidanci.minesweeper.board.Board;
import io.github.dulidanci.minesweeper.board.Tile;
import io.github.dulidanci.minesweeper.difficulty.Difficulty;

import java.util.List;

public class GameScreen implements Screen {
    public final Minesweeper game;
    public final SpriteBatch batch;
    public final FitViewport viewport;
    public final Texture mineTexture;
    public final Texture flagTexture;
    public final Texture tileOpenTexture;
    public final Texture tileCloseTexture;
    public final List<Texture> numberTextures;
    public final Texture difficultyBackgroundTexture;
    public final Texture difficultyArrowTexture;
    public final Sprite difficultyArrowSprite;
    public final Texture difficultyTickTexture;
    public final List<Texture> faceTextures;
    public final String winMessage;
    public final String loseMessage;
    public final String resetMessage;
    public final Vector2 mousePos;
    private boolean difficultyOpen;


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
        difficultyBackgroundTexture = new Texture("difficulty_background.png");
        difficultyArrowTexture = new Texture("difficulty_arrow.png");
        difficultyTickTexture = new Texture("difficulty_tick.png");
        faceTextures = List.of(
            new Texture("face.png"),
            new Texture("face_dead.png"),
            new Texture("face_cool.png")
        );

        difficultyArrowSprite = new Sprite(difficultyArrowTexture);
        difficultyArrowSprite.setSize(1, 0.75f);
        difficultyArrowSprite.setPosition(3, viewport.getWorldHeight() - 0.875f);
        difficultyArrowSprite.setOriginCenter();

        winMessage = "Great job! You've found all the mines!";
        loseMessage = "Oh no! You've exploded and lost!";
        resetMessage = "Press 'R' to restart!";

        mousePos = new Vector2();

        difficultyOpen = false;
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
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(mousePos);

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.reset();
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float buttonHeight = 0.75f;
            if (mousePos.x >= 1 && mousePos.x <= 4 && mousePos.y >= viewport.getWorldHeight() - 0.5f - buttonHeight / 2 &&
                mousePos.y <= viewport.getWorldHeight() - 0.5f + buttonHeight / 2) {

                difficultyOpen = !difficultyOpen;
                return;
            }
            if (difficultyOpen) {
                for (int i = 0; i < Difficulty.values().length; i++) {
                    if (mousePos.x >= 1 && mousePos.x <= 4 && mousePos.y >= viewport.getWorldHeight() - 0.5f - buttonHeight * (i + 1.5f) &&
                        mousePos.y <= viewport.getWorldHeight() - 0.5f - buttonHeight  * (i + 0.5f)) {

                        game.difficulty = Difficulty.values()[i];
                        difficultyOpen = false;
                        game.reset();
                        return;
                    }
                }
            }

        }

        if (game.won || game.lost) return;

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            game.board.clicked((int) mousePos.x - 1, (int) mousePos.y - 1, false);

        } else if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            game.board.clicked((int) mousePos.x - 1, (int) mousePos.y - 1, true);
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
                    if (tile.flag) {
                        batch.draw(flagTexture, j + 1, i + 1, 1, 1);
                    }
                }
            }
        }

        if (game.lost || game.won) {
            game.font.draw(game.batch, game.lost ? loseMessage : winMessage, 1, viewport.getWorldHeight() - 1.25f);
            game.font.draw(game.batch, resetMessage, 1, 0.75f);

            batch.draw(game.lost ? faceTextures.get(1) : faceTextures.get(2), viewport.getWorldWidth() / 2 - 0.5f,
                viewport.getWorldHeight() - 1.125f, 1, 1);
        } else {
            batch.draw(faceTextures.get(0), viewport.getWorldWidth() / 2 - 0.5f,
                viewport.getWorldHeight() - 1.125f, 1, 1);
        }

        float buttonHeight = 0.75f;
        batch.draw(difficultyBackgroundTexture, 1, viewport.getWorldHeight() - 0.5f - buttonHeight / 2, 3, buttonHeight);

        game.font.draw(game.batch, game.difficulty.getId(), 1.25f, viewport.getWorldHeight() - 0.25f);

        if (difficultyOpen) {
            for (int i = 0; i < Difficulty.values().length; i++) {
                batch.draw(difficultyBackgroundTexture, 1, viewport.getWorldHeight() - 0.5f - buttonHeight * (i + 1.5f), 3, buttonHeight);
                game.font.draw(game.batch, Difficulty.values()[i].getId(), 2, viewport.getWorldHeight() - 0.25f - buttonHeight * (i + 1));
                if (game.difficulty.equals(Difficulty.values()[i])) {
                    batch.draw(difficultyTickTexture, 1, viewport.getWorldHeight() - 0.5f - buttonHeight * (i + 1.5f), 1, buttonHeight);
                }
            }
        }


        difficultyArrowSprite.setRotation(difficultyOpen ? 180 : 0);
        difficultyArrowSprite.draw(batch);

        batch.draw(mineTexture, viewport.getWorldWidth() - 5, viewport.getWorldHeight() - 1, 1, 1);
        game.font.draw(game.batch, ": " + game.difficulty.getMines(), viewport.getWorldWidth() - 4, viewport.getWorldHeight() - 0.5f);

        batch.draw(flagTexture, viewport.getWorldWidth() - 3, viewport.getWorldHeight() - 1, 1, 1);
        game.font.draw(game.batch, ": " + (game.difficulty.getMines() - board.flagCount), viewport.getWorldWidth() - 2, viewport.getWorldHeight() - 0.5f);

        game.font.draw(game.batch, "Author: Dulidanci", viewport.getWorldWidth() - 4.75f, 0.75f);

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
        difficultyBackgroundTexture.dispose();
        difficultyArrowTexture.dispose();
        difficultyTickTexture.dispose();
        for (Texture texture : faceTextures) {
            texture.dispose();
        }
    }
}
