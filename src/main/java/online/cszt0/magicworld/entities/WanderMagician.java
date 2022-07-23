package online.cszt0.magicworld.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion.DestructionType;
import online.cszt0.magicworld.MagicWorld;
import online.cszt0.magicworld.events.EnchantantCallback;

public class WanderMagician extends HostileEntity {
    private int magicCooldown;
    private float mana;
    private float manaMax;

    public WanderMagician(EntityType<? extends WanderMagician> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = SMALL_MONSTER_XP;
        magicCooldown = 0;
        mana = 50;
        manaMax = 100;
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.5)
                .add(EntityAttributes.GENERIC_ARMOR, 10.0);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(1, new SwimGoal(this));
        goalSelector.add(2, new MeleeAttackGoal(this, 1.0, false));
        goalSelector.add(3, new MagicExplodeGoal());
        goalSelector.add(3, new FleeEntityGoal<ZombieEntity>(this, ZombieEntity.class, 6.0f, 1.0, 1.2));
        goalSelector.add(3, new WanderAroundFarGoal(this, 0.8));
        goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 18.0F));
        goalSelector.add(4, new LookAroundGoal(this));
        targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        targetSelector.add(1, new ActiveTargetGoal<>(this, CreeperEntity.class, true));
        targetSelector.add(2, new RevengeGoal(this));
    }

    @Override
    public void tick() {
        if (isAlive()) {
            if (magicCooldown > 0) {
                magicCooldown--;
            }
            if (mana < manaMax) {
                mana += 0.2;
            }
        }
        super.tick();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("MagicCooldown", magicCooldown);
        nbt.putFloat("Mana", mana);
        nbt.putFloat("ManaMax", manaMax);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("MagicCooldown")) {
            magicCooldown = nbt.getInt("MagicCooldown");
        }
        if (nbt.contains("Mana")) {
            mana = nbt.getFloat("Mana");
        }
        if (nbt.contains("ManaMax")) {
            mana = nbt.getFloat("ManaMax");
        }
    }

    public static void init() {
        EnchantantCallback.EVENT.register((player) -> {
            World world = player.getWorld();
            if (player.isSpectator() || world.getDifficulty() == Difficulty.PEACEFUL
                    || !world.getDimensionKey().getRegistry().toString().equals("minecraft:overworld")) {
                return ActionResult.PASS;
            }

            if (Math.random() < 0.1) {
                Vec3d pos = player.getPos();
                WanderMagician magician = new WanderMagician(MagicWorld.WANDER_MAGICIAN, world);
                if (!player.isCreative()) {
                    magician.setTarget(player);
                }
                magician.setPos(pos.getX(), pos.getY() + 0.1, pos.getZ());
                world.spawnEntity(magician);
            }
            return ActionResult.PASS;
        });
    }

    private class MagicExplodeGoal extends Goal {
        private LivingEntity target;
        private int progress;

        @Override
        public boolean canStart() {
            LivingEntity target = getTarget();
            return target != null && mana > 50 && magicCooldown <= 0
                    && squaredDistanceTo(target) < 91;
        }

        @Override
        public void start() {
            getNavigation().stop();
            target = getTarget();
            progress = 0;
        }

        @Override
        public void stop() {
            target = null;
            progress = 0;
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (target == null) {
                return;
            }
            if (!getVisibilityCache().canSee(target)) {
                return;
            }
            progress++;
            getNavigation().stop();
            if (progress > 50) {
                magicCooldown = 100;
                mana -= 50;
                target.getWorld().createExplosion(WanderMagician.this,
                        target.getX(), target.getY() + 1.15, target.getZ(), 1, true, DestructionType.DESTROY);
                stop();
            }
        }
    }
}
