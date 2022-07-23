package online.cszt0.magicworld.entities;

import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class WanderMagicianRender extends MobEntityRenderer<WanderMagician, WanderMagicianModel> {

    public WanderMagicianRender(Context context) {
        super(context, new WanderMagicianModel(), 0.5f);
    }

    @Override
    public Identifier getTexture(WanderMagician entity) {
        return new Identifier("magicworld", "textures/entity/wander_magician/wander_magician.png");
    }

}
