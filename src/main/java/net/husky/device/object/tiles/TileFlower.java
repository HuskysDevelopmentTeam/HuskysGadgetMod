package net.husky.device.object.tiles;

import net.husky.device.api.utils.RenderUtil;
import net.husky.device.object.Game;
import net.husky.device.object.Game.Layer;

public class TileFlower extends Tile
{
	public TileFlower(int id, int x, int y)
	{
		super(id, x, y);
	}

	@Override
	public void render(Game game, int x, int y, Layer layer)
	{
		RenderUtil.drawRectWithTexture(game.xPosition + x * Tile.WIDTH , game.yPosition + y * Tile.HEIGHT - 4, this.x * 16, this.y * 16, WIDTH, 8, 16, 16);	
	}
	
	@Override
	public boolean isFullTile()
	{
		return false;
	}
}
