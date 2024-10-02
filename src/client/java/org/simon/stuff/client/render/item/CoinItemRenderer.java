package org.simon.stuff.client.render.item;

import net.minecraft.util.Identifier;
import org.simon.stuff.Stuff;
import org.simon.stuff.item.CoinItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class CoinItemRenderer extends GeoItemRenderer<CoinItem> {

	public CoinItemRenderer() {
		super(new CoinItemModel());
	}
}

class CoinItemModel extends GeoModel<CoinItem> {

	@Override
	public Identifier getModelResource(CoinItem coinItem) {
		return new Identifier(Stuff.MOD_ID, "geo/item/coin.geo.json");
	}

	@Override
	public Identifier getTextureResource(CoinItem coinItem) {
		return new Identifier(Stuff.MOD_ID, "textures/item/coin.png");
	}

	@Override
	public Identifier getAnimationResource(CoinItem coinItem) {
		return new Identifier(Stuff.MOD_ID, "animations/item/coin.animation.json");
	}
}