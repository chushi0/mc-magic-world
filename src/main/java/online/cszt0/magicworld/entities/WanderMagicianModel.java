package online.cszt0.magicworld.entities;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class WanderMagicianModel extends EntityModel<WanderMagician> {
    private final ModelPart base;

    public WanderMagicianModel() {
        base = new ModelPart(List.of(
                new ModelPart.Cuboid(0, 0, -6, -6, -6, 12, 12, 12, 0, 0, 0, false, 64, 64)),
                Collections.emptyMap());
    }

    @Override
    public void setAngles(WanderMagician entity, float limbAngle, float limbDistance, float animationProgress,
            float headYaw, float headPitch) {
        base.yaw = headYaw * ((float) Math.PI / 180);
        base.pitch = headPitch * ((float) Math.PI / 180);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
            float blue, float alpha) {
        matrices.translate(0, 1.125, 0);
        base.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

}
