package net.thegaminghuskymc.gadgetmod.object.tiles;

import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.object.Game;

public class TileWheat extends Tile {
    public TileWheat(int id, int x, int y) {
        super(id, x, y);
    }

    @Override
    public void render(Game game, int x, int y, Game.Layer layer) {
        RenderUtil.drawRectWithTexture(game.xPosition + x * WIDTH, game.yPosition + y * HEIGHT - 6, this.x * 16, this.y * 16, WIDTH, HEIGHT + 1, 16, 16);
        RenderUtil.drawRectWithTexture(game.xPosition + x * WIDTH, game.yPosition + y * HEIGHT - 2, this.x * 16, this.y * 16, WIDTH, HEIGHT + 1, 16, 16);
    }

    @Override
    public boolean isFullTile() {
        return false;
    }
}
