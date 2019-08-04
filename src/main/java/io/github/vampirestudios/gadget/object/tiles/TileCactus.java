package io.github.vampirestudios.gadget.object.tiles;

import io.github.vampirestudios.gadget.api.utils.RenderUtil;
import io.github.vampirestudios.gadget.object.Game;
import org.lwjgl.opengl.GL11;

public class TileCactus extends Tile {
    public TileCactus(int id) {
        super(id, 3, 2);
    }

    @Override
    public void render(Game game, int x, int y, Game.Layer layer) {
        if (game.getTile(layer.up(), x, y - 1) != this || layer == Game.Layer.FOREGROUND) {
            RenderUtil.drawRectWithTexture(game.xPosition + x * Tile.WIDTH, game.yPosition + y * Tile.HEIGHT - 5.5, layer.zLevel, this.x * 16, this.y * 16, WIDTH, HEIGHT, 16, 16);
            RenderUtil.drawRectWithTexture(game.xPosition + x * Tile.WIDTH + 0.5, game.yPosition + y * Tile.HEIGHT - 5.5, layer.zLevel, (this.x + 1) * 16 + 1, this.y * 16 + 1, WIDTH - 1, HEIGHT - 1, 14, 14);
        }

        GL11.glColor4f(0.6F, 0.6F, 0.6F, 1F);
        RenderUtil.drawRectWithTexture(game.xPosition + x * Tile.WIDTH, game.yPosition + y * Tile.HEIGHT - 0.5, layer.zLevel, this.x * 16, this.y * 16, WIDTH, HEIGHT, 16, 16);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean isFullTile() {
        return false;
    }
}
