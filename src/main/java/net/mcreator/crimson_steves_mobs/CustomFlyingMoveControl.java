/**
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.crimson_steves_mobs as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.mcreator.crimson_steves_mobs;

import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.util.Mth;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CustomFlyingMoveControl extends MoveControl {
	private final int maxTurn;
	private boolean hoversInPlace;
	private float trueSpeed;
	private float landTrueSpeed;

	public CustomFlyingMoveControl(Mob p_24893_, int p_24894_) {
		super(p_24893_);
		this.maxTurn = p_24894_;
	}

	public void tick() {
		if (this.operation == MoveControl.Operation.WAIT && mob.getRandom().nextInt(100) == 0)
			hoversInPlace = !hoversInPlace;
		if (this.operation == MoveControl.Operation.MOVE_TO) {
			this.operation = MoveControl.Operation.WAIT;
			this.mob.setNoGravity(true);
			double d0 = this.wantedX - this.mob.getX();
			double d1 = this.wantedY - this.mob.getY();
			double d2 = this.wantedZ - this.mob.getZ();
			double d3 = d0 * d0 + d1 * d1 + d2 * d2;
			if (d3 < (double) 2.5000003E-7F) {
				trueSpeed = Mth.lerp(0.1f, trueSpeed, 0);
				landTrueSpeed = Mth.lerp(0.1f, landTrueSpeed, 0);
				this.mob.setYya(trueSpeed);
				this.mob.setZza(trueSpeed);
				return;
			}
			float f = (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
			this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f, 90.0F));
			float f1;
			if (this.mob.isOnGround()) {
				f1 = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
				BlockPos blockpos = this.mob.blockPosition();
				BlockState blockstate = this.mob.level.getBlockState(blockpos);
				VoxelShape voxelshape = blockstate.getCollisionShape(this.mob.level, blockpos);
				if (d2 > (double) this.mob.getStepHeight() && d0 * d0 + d1 * d1 < (double) Math.max(1.0F, this.mob.getBbWidth())
						|| !voxelshape.isEmpty() && this.mob.getY() < voxelshape.max(Direction.Axis.Y) + (double) blockpos.getY()
								&& !blockstate.is(BlockTags.DOORS) && !blockstate.is(BlockTags.FENCES)) {
					this.mob.getJumpControl().jump();
				}
				landTrueSpeed = Mth.lerp(0.1f, landTrueSpeed, f1);
				trueSpeed = Mth.lerp(0.1f, trueSpeed, 0);
				this.mob.setSpeed(landTrueSpeed);
			} else {
				f1 = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)) * 5;
				trueSpeed = Mth.lerp(0.1f, trueSpeed, f1);
				landTrueSpeed = Mth.lerp(0.1f, landTrueSpeed, 0);
				this.mob.setSpeed(trueSpeed);
			}
			double d4 = Math.sqrt(d0 * d0 + d2 * d2);
			if (Math.abs(d1) > (double) 1.0E-5F || Math.abs(d4) > (double) 1.0E-5F) {
				float f2 = (float) (-(Mth.atan2(d1, d4) * (double) (180F / (float) Math.PI)));
				this.mob.setXRot(this.rotlerp(this.mob.getXRot(), f2, (float) this.maxTurn));
				this.mob.setYya(d1 > 0.0D ? trueSpeed : -trueSpeed);
			}
		} else {
			if (!this.hoversInPlace) {
				this.mob.setNoGravity(false);
			}
			trueSpeed = Mth.lerp(0.1f, trueSpeed, 0);
			landTrueSpeed = Mth.lerp(0.1f, landTrueSpeed, 0);
			this.mob.setYya(trueSpeed);
			this.mob.setZza(trueSpeed);
		}
	}
}
