package mastef_chief.gitwebbuilder.hgm.app.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * GWBLogoModel - Mastef_Chief
 * Created using Tabula 7.0.0
 */
public class GWBLogoModel extends ModelBase {
    private ModelRenderer baseblock;

    public GWBLogoModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.baseblock = new ModelRenderer(this, 0, 0);
        this.baseblock.setRotationPoint(-6.0F, 12.0F, -6.0F);
        this.baseblock.addBox(0.0F, 0.0F, 0.0F, 12, 12, 12, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.baseblock.render(f5);
    }

}
