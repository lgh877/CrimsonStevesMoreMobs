/**
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.workspace as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.mcreator.crimson_steves_mobs;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.AnimationChannel;

@OnlyIn(Dist.CLIENT)
public class MonstrosityAnimations {
	public static final AnimationDefinition REDSTONE_MONSTROSITY_SLAM = AnimationDefinition.Builder.withLength(3f)
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(1.92f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.posVec(0f, -2f, -1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.8f, KeyframeAnimations.degreeVec(-20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.88f, KeyframeAnimations.degreeVec(-14.17f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.96f, KeyframeAnimations.degreeVec(-16.75f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.28f, KeyframeAnimations.degreeVec(-15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.52f, KeyframeAnimations.degreeVec(-15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.84f, KeyframeAnimations.degreeVec(-4.4f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.92f, KeyframeAnimations.degreeVec(38f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.44f, KeyframeAnimations.degreeVec(36f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(1.92f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.posVec(0f, -1f, 1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(1.28f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.52f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.84f, KeyframeAnimations.degreeVec(5.81f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.92f, KeyframeAnimations.degreeVec(38f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2f, KeyframeAnimations.degreeVec(43f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.08f, KeyframeAnimations.degreeVec(38f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.44f, KeyframeAnimations.degreeVec(36f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(1.92f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, -4f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.8f, KeyframeAnimations.degreeVec(-211.88212f, 14.47874f, 15.1398f), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.88f, KeyframeAnimations.degreeVec(-214.76f, 13.05f, 15.37f), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.96f, KeyframeAnimations.degreeVec(-206.64f, 11.62f, 15.6f), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.28f, KeyframeAnimations.degreeVec(-199.14314f, 5.90316f, 16.50371f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.52f, KeyframeAnimations.degreeVec(-199.14314f, 5.90316f, 16.50371f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.84f, KeyframeAnimations.degreeVec(-177.19f, 7.04f, 9.29f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.92f, KeyframeAnimations.degreeVec(-89.39678f, 11.59433f, -19.56335f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.degreeVec(-96.90157f, 9.09446f, -19.58999f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.44f, KeyframeAnimations.degreeVec(-89.39678f, 11.59433f, -19.56335f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.8f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.88f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.96f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.52f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.84f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.92f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.44f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftHand",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(1.52f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.84f, KeyframeAnimations.degreeVec(-8f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.92f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.degreeVec(-27.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.44f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(1.92f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, -4f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.8f, KeyframeAnimations.degreeVec(-211.88212f, -14.47874f, -15.1398f), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.88f, KeyframeAnimations.degreeVec(-214.76f, -13.05f, -15.37f), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.96f, KeyframeAnimations.degreeVec(-206.64f, -11.62f, -15.6f), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.28f, KeyframeAnimations.degreeVec(-199.14314f, -5.90316f, -16.50371f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.52f, KeyframeAnimations.degreeVec(-199.14314f, -5.90316f, -16.50371f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.84f, KeyframeAnimations.degreeVec(-177.19f, -7.04f, -9.29f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.92f, KeyframeAnimations.degreeVec(-89.39678f, -11.59433f, 19.56335f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.degreeVec(-96.90157f, -9.09446f, 19.58999f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.44f, KeyframeAnimations.degreeVec(-89.39678f, -11.59433f, 19.56335f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.8f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.88f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.96f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.52f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.84f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.92f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.44f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightHand",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(1.52f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.84f, KeyframeAnimations.degreeVec(-8f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.92f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.degreeVec(-27.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.44f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftLeg",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(1.84f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.92f, KeyframeAnimations.posVec(0f, -5.83f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.12f, KeyframeAnimations.posVec(0f, -7f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.48f, KeyframeAnimations.posVec(0f, -7f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.92f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftLeg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(1.84f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.92f, KeyframeAnimations.degreeVec(58.33f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.12f, KeyframeAnimations.degreeVec(70f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.48f, KeyframeAnimations.degreeVec(70f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightLeg",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(1.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.84f, KeyframeAnimations.posVec(0f, 6f, -2.4f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.92f, KeyframeAnimations.posVec(0f, 0f, -12f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.44f, KeyframeAnimations.posVec(0f, 0f, -12f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.92f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
	public static final AnimationDefinition REDSTONE_MONSTROSITY_LEFTSLAM = AnimationDefinition.Builder.withLength(2.08f)
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(1.32f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.4f, KeyframeAnimations.posVec(0f, -2f, -1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.48f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.52f, KeyframeAnimations.degreeVec(-20.6469f, -14.0761f, 5.23619f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(-10.50409f, -14.47751f, 3.96713f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.degreeVec(-15.50409f, -14.47751f, 3.96713f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(-12.93591f, -14.10497f, 3.49169f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.degreeVec(-3.95f, 2.38f, 0.78f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.32f, KeyframeAnimations.degreeVec(41.37384f, 21.33762f, 17.7703f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(39.9946f, 23.86033f, 18.74505f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(1.32f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.4f, KeyframeAnimations.posVec(0f, -1f, 1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.48f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.64f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.04f, KeyframeAnimations.degreeVec(0.62f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.degreeVec(7f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.32f, KeyframeAnimations.degreeVec(38f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(36f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(1.32f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.4f, KeyframeAnimations.posVec(0f, 0f, -4f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.48f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.52f, KeyframeAnimations.degreeVec(-211.88212f, 14.47874f, 15.1398f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.degreeVec(-199.14314f, 5.90316f, 16.50371f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(-186.95f, 6.54f, 12.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.degreeVec(-144.27f, 8.75f, -1.53f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.32f, KeyframeAnimations.degreeVec(-89.39678f, 11.59433f, -19.56335f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.4f, KeyframeAnimations.degreeVec(-96.90157f, 9.09446f, -19.58999f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(-89.39678f, 11.59433f, -19.56335f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.84f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.32f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.4f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftHand",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.84f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.32f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.4f, KeyframeAnimations.degreeVec(-27.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.degreeVec(-66.78912f, -13.83447f, -5.85463f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.degreeVec(-66.78912f, -13.83447f, -5.85463f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.32f, KeyframeAnimations.degreeVec(-26.59914f, 36.43129f, 17.80232f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.4f, KeyframeAnimations.degreeVec(-11.28509f, 50.97061f, 26.99824f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(-24.09914f, 36.43129f, 17.80232f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.84f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.32f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.4f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightHand",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.84f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.32f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.4f, KeyframeAnimations.degreeVec(-27.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightLeg",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.posVec(0f, 6f, -2.4f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.28f, KeyframeAnimations.posVec(0f, 0f, -12f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.posVec(0f, 0f, -12f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
	public static final AnimationDefinition REDSTONE_MONSTROSITY_RIGHTSLAM = AnimationDefinition.Builder.withLength(2.08f)
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(1.32f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.4f, KeyframeAnimations.posVec(0f, -2f, -1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.48f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.52f, KeyframeAnimations.degreeVec(-20.6469f, 14.0761f, -5.23619f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(-10.50409f, 14.47751f, -3.96713f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.degreeVec(-15.50409f, 14.47751f, -3.96713f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(-12.93591f, 14.10497f, -3.49169f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.degreeVec(-3.95f, -2.38f, -0.78f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.32f, KeyframeAnimations.degreeVec(41.37384f, -21.33762f, -17.7703f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(39.9946f, -23.86033f, -18.74505f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(1.32f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.4f, KeyframeAnimations.posVec(0f, -1f, 1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.48f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.64f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.04f, KeyframeAnimations.degreeVec(0.62f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.degreeVec(7f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.32f, KeyframeAnimations.degreeVec(38f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(36f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.degreeVec(-66.78912f, 13.83447f, 5.85463f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.degreeVec(-66.78912f, 13.83447f, 5.85463f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.32f, KeyframeAnimations.degreeVec(-26.59914f, -36.43129f, -17.80232f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.4f, KeyframeAnimations.degreeVec(-11.28509f, -50.97061f, -26.99824f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(-24.09914f, -36.43129f, -17.80232f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.84f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.32f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.4f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftHand",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.84f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.32f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.4f, KeyframeAnimations.degreeVec(-27.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(1.32f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.4f, KeyframeAnimations.posVec(0f, 0f, -4f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.48f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.52f, KeyframeAnimations.degreeVec(-211.88212f, -14.47874f, -15.1398f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.degreeVec(-199.14314f, -5.90316f, -16.50371f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(-186.95f, -6.54f, -12.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.degreeVec(-144.27f, -8.75f, 1.53f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.32f, KeyframeAnimations.degreeVec(-89.39678f, -11.59433f, 19.56335f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.4f, KeyframeAnimations.degreeVec(-96.90157f, -9.09446f, 19.58999f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(-89.39678f, -11.59433f, 19.56335f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.84f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.32f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.4f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightHand",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.84f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.32f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.4f, KeyframeAnimations.degreeVec(-27.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftLeg",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.posVec(0f, 6f, -2.4f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.28f, KeyframeAnimations.posVec(0f, 0f, -12f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.posVec(0f, 0f, -12f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
	public static final AnimationDefinition REDSTONE_MONSTROSITY_INSERTINGARMSSTART = AnimationDefinition.Builder.withLength(1.8f)
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.08f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.16f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68f, KeyframeAnimations.posVec(0f, -2f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.8f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.24f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.52f, KeyframeAnimations.degreeVec(7.25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.64f, KeyframeAnimations.degreeVec(40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.08f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.16f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68f, KeyframeAnimations.posVec(0f, -2f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.8f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.24f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.08f, KeyframeAnimations.posVec(2f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.16f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.52f, KeyframeAnimations.posVec(0f, 7f, 7f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68f, KeyframeAnimations.posVec(0f, 0f, 4f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.8f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(-62.72683f, 67.73126f, -64.49445f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.degreeVec(-62.72683f, 67.73126f, -64.49445f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.52f, KeyframeAnimations.degreeVec(-71.9f, 54.15f, -74.12f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.degreeVec(-87.22503f, 12.85989f, -80.62848f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(1.04f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.52f, KeyframeAnimations.degreeVec(0f, 0f, 2.25f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.degreeVec(0f, 0f, 7.5f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.08f, KeyframeAnimations.posVec(-2f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.16f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.52f, KeyframeAnimations.posVec(0f, 7f, 7f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68f, KeyframeAnimations.posVec(0f, 0f, 4f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.8f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(-62.72683f, -67.73126f, 64.49445f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.degreeVec(-62.72683f, -67.73126f, 64.49445f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.52f, KeyframeAnimations.degreeVec(-71.9f, -54.15f, 74.12f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.degreeVec(-87.22503f, -12.85989f, 80.62848f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(1.04f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.52f, KeyframeAnimations.degreeVec(0f, 0f, -2.25f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftHand",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 19f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.52f, KeyframeAnimations.posVec(0f, 19f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightHand",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 19f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.52f, KeyframeAnimations.posVec(0f, 19f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
	public static final AnimationDefinition REDSTONE_MONSTROSITY_INSERTINGARMSIDLE = AnimationDefinition.Builder.withLength(0.52f).looping()
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, -1f, -4f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.24f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lowerBody", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, -1f, -1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.24f, KeyframeAnimations.posVec(0f, -1f, -1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, -5f, -7f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12f, KeyframeAnimations.posVec(0f, -3f, -5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.24f, KeyframeAnimations.posVec(0f, 0f, -2f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(-87.22503f, 12.85989f, -80.62848f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.degreeVec(-87.80278f, 12.96835f, -83.19248f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.12f, KeyframeAnimations.degreeVec(-87.22503f, 12.85989f, -80.62848f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.16f, KeyframeAnimations.degreeVec(-87.80278f, 12.96835f, -83.19248f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.52f, KeyframeAnimations.degreeVec(-87.22715f, 12.86152f, -80.62751f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftWrist", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 7.5f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, -5f, -7f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12f, KeyframeAnimations.posVec(0f, -3f, -5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.24f, KeyframeAnimations.posVec(0f, 0f, -2f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(-87.22503f, -12.85989f, 80.62848f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.degreeVec(-87.80278f, -12.96835f, 83.19248f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.12f, KeyframeAnimations.degreeVec(-87.22503f, -12.85989f, 80.62848f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.16f, KeyframeAnimations.degreeVec(-87.80278f, -12.96835f, 83.19248f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.52f, KeyframeAnimations.degreeVec(-87.22715f, -12.86152f, 80.62751f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightWrist", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftHand",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 10f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12f, KeyframeAnimations.posVec(0f, 4f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.24f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightHand",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 10f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12f, KeyframeAnimations.posVec(0f, 4f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.24f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
	public static final AnimationDefinition REDSTONE_MONSTROSITY_INSERTINGARMSSTOP = AnimationDefinition.Builder.withLength(1.68f)
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 2f, 2f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.16f, KeyframeAnimations.posVec(-1f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.24f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.76f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.8f, KeyframeAnimations.posVec(0f, 3f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.88f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.92f, KeyframeAnimations.posVec(1f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lowerBody", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.12f, KeyframeAnimations.degreeVec(40.16f, -3.83f, -3.22f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.76f, KeyframeAnimations.degreeVec(40.24258f, -5.73853f, -4.83711f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.88f, KeyframeAnimations.degreeVec(40.24f, 3.97f, 3.35f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.56f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 2f, 2f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.16f, KeyframeAnimations.posVec(-1f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.24f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.76f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.8f, KeyframeAnimations.posVec(0f, 2f, 2f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.88f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.92f, KeyframeAnimations.posVec(1f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.56f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.12f, KeyframeAnimations.degreeVec(23.73f, -7.2f, -4.22f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.76f, KeyframeAnimations.degreeVec(20.59871f, -10.80355f, -6.32526f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.88f, KeyframeAnimations.degreeVec(20.57f, 7.05f, 0.33f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.56f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(4f, 0f, 4f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.16f, KeyframeAnimations.posVec(-1f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.24f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.6f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.68f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(-87.22503f, 12.85989f, -80.62848f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.12f, KeyframeAnimations.degreeVec(-8.19583f, -19.1673f, -35.19152f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.76f, KeyframeAnimations.degreeVec(-25.65f, -18.86f, -30.88f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.88f, KeyframeAnimations.degreeVec(-101.30193f, -17.54419f, -12.22335f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 7.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.12f, KeyframeAnimations.degreeVec(-82.5f, 0f, 6.35f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.56f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(3f, -2f, -7f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.76f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.8f, KeyframeAnimations.posVec(0f, 0f, 5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.88f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.92f, KeyframeAnimations.posVec(1f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.56f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(-87.22503f, -12.85989f, 80.62848f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.12f, KeyframeAnimations.degreeVec(-65.55f, -2.96f, 52.15f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.76f, KeyframeAnimations.degreeVec(-61.61246f, -1.15545f, 46.9772f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.88f, KeyframeAnimations.degreeVec(-24.45885f, 20.69338f, 59.38382f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.76f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.88f, KeyframeAnimations.degreeVec(-87.5f, 0f, -7.5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.56f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftHand",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.12f, KeyframeAnimations.posVec(0f, 6f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightHand", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.76f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.88f, KeyframeAnimations.posVec(0f, 6f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.28f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
	public static final AnimationDefinition REDSTONE_MONSTROSITY_INSERTARMSSTOP2 = AnimationDefinition.Builder.withLength(1.04f)
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 3f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.92f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0f, 0.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.36f, KeyframeAnimations.degreeVec(35.38f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 3f, 3f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.92f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0f, 0.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.36f, KeyframeAnimations.degreeVec(26.54f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0f, 10f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.92f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0f, 0.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(-87.22503f, 12.85989f, -80.62848f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.36f, KeyframeAnimations.degreeVec(-77.16f, 11.38f, -71.33f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 7.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.36f, KeyframeAnimations.degreeVec(0f, 0f, 6.63f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftHand",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 5f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0f, 10f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.92f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0f, 0.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(-87.22503f, -12.85989f, 80.62848f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.36f, KeyframeAnimations.degreeVec(-77.16f, -11.38f, 71.33f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.36f, KeyframeAnimations.degreeVec(0f, 0f, -6.63f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightHand", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 5f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
	public static final AnimationDefinition REDSTONE_MONSTROSITY_DEATH_TYPE1 = AnimationDefinition.Builder.withLength(2.6f)
			.addAnimation("whole",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.36f, KeyframeAnimations.posVec(0f, 2.6f, 9.2f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.48f, KeyframeAnimations.posVec(0f, -17f, 11f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.52f, KeyframeAnimations.posVec(0f, -16f, 11f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("whole",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.36f, KeyframeAnimations.degreeVec(12.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.48f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.12f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.36f, KeyframeAnimations.degreeVec(-25.5f, -4.5f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.48f, KeyframeAnimations.degreeVec(-90f, -22.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6f, KeyframeAnimations.degreeVec(-82.5f, -22.5f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.36f, KeyframeAnimations.degreeVec(-5.9f, 2.1f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.48f, KeyframeAnimations.degreeVec(-90f, 22.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6f, KeyframeAnimations.degreeVec(-74.91728f, 25.14297f, 17.03322f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(2.44f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.48f, KeyframeAnimations.posVec(0f, -2f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.56f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.2f, KeyframeAnimations.degreeVec(-5f, 5f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.48f, KeyframeAnimations.degreeVec(-5f, -5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.88f, KeyframeAnimations.degreeVec(-5f, 5f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.28f, KeyframeAnimations.degreeVec(-5f, -5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.6f, KeyframeAnimations.degreeVec(17.43745f, 1.50179f, -4.76968f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.2f, KeyframeAnimations.degreeVec(-5f, 5f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.48f, KeyframeAnimations.degreeVec(-5f, -5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.88f, KeyframeAnimations.degreeVec(-5f, 5f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.28f, KeyframeAnimations.degreeVec(-5f, -5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.6f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.92f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.24f, KeyframeAnimations.posVec(0f, -6f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.44f, KeyframeAnimations.posVec(0f, -6f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.52f, KeyframeAnimations.posVec(0f, -21f, -13f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.6f, KeyframeAnimations.posVec(0f, -18f, -9f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.2f, KeyframeAnimations.degreeVec(-42.24682f, 17.04679f, -14.9092f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.48f, KeyframeAnimations.degreeVec(-42.24682f, -17.0468f, 14.9092f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.88f, KeyframeAnimations.degreeVec(-42.24682f, 17.04679f, -14.9092f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.28f, KeyframeAnimations.degreeVec(-42.24682f, -17.0468f, 14.9092f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.08f, KeyframeAnimations.degreeVec(21.741f, 17.26588f, -36.65996f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.36f, KeyframeAnimations.degreeVec(21.741f, 17.26588f, -36.65996f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.52f, KeyframeAnimations.degreeVec(58.56663f, 9.08661f, -17.89244f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.6f, KeyframeAnimations.degreeVec(60.48016f, 15.70063f, -4.09357f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("jaw",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.2f, KeyframeAnimations.degreeVec(50f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.6f, KeyframeAnimations.degreeVec(25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(2.48f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.52f, KeyframeAnimations.posVec(0f, 2f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.2f, KeyframeAnimations.degreeVec(-22.01816f, -4.75113f, -11.57518f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.6f, KeyframeAnimations.degreeVec(-22.42287f, -1.91134f, -4.62111f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftWrist",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(2.48f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.52f, KeyframeAnimations.posVec(0f, 2f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.2f, KeyframeAnimations.degreeVec(-32.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.4f, KeyframeAnimations.degreeVec(-14.42f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.48f, KeyframeAnimations.degreeVec(-41.6784f, -13.27207f, -7.05309f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftHand",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.6f, KeyframeAnimations.degreeVec(-15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(2.48f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.52f, KeyframeAnimations.posVec(0f, -8f, -4f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.2f, KeyframeAnimations.degreeVec(-22.01816f, 4.75113f, 11.57518f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.6f, KeyframeAnimations.degreeVec(-22.42287f, 1.91134f, 4.62111f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightWrist",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(2.52f, KeyframeAnimations.posVec(0f, -2f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.56f, KeyframeAnimations.posVec(0f, 1f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.2f, KeyframeAnimations.degreeVec(-32.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.4f, KeyframeAnimations.degreeVec(-14.42f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.48f, KeyframeAnimations.degreeVec(-61.67841f, 13.27207f, 7.05309f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightHand", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6f, KeyframeAnimations.degreeVec(-15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
	public static final AnimationDefinition REDSTONE_MONSTROSITY_DEATH_TYPE2 = AnimationDefinition.Builder.withLength(1.36f)
			.addAnimation("whole",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.16f, KeyframeAnimations.posVec(0f, -3.92f, -2.31f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4f, KeyframeAnimations.posVec(0f, -17f, -10f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftLeg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.16f, KeyframeAnimations.degreeVec(20.77f, 4.62f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4f, KeyframeAnimations.degreeVec(90f, 20f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.16f, KeyframeAnimations.degreeVec(20.77f, -4.62f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4f, KeyframeAnimations.degreeVec(90f, -20f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.16f, KeyframeAnimations.degreeVec(-1.15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(90f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.16f, KeyframeAnimations.degreeVec(-1.15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.4f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.48f, KeyframeAnimations.posVec(0f, 52.29f, 30.13f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.56f, KeyframeAnimations.posVec(0f, 61f, 35.15f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.posVec(0f, 52f, 17f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.16f, KeyframeAnimations.degreeVec(-4.62f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4f, KeyframeAnimations.degreeVec(-20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(360f, 0f, -35f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.16f, KeyframeAnimations.degreeVec(9.23f, 0.1f, -0.57f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.4f, KeyframeAnimations.degreeVec(39.99067f, 0.43399f, -2.46207f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(12.5f, 0f, -20f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.68f, KeyframeAnimations.degreeVec(3.13f, 0f, -20f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.76f, KeyframeAnimations.degreeVec(0f, 0f, -20f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.16f, KeyframeAnimations.degreeVec(4.04f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4f, KeyframeAnimations.degreeVec(17.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.56f, KeyframeAnimations.degreeVec(25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.64f, KeyframeAnimations.degreeVec(75f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.68f, KeyframeAnimations.degreeVec(18.75f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.76f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftHand",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.4f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(27.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.68f, KeyframeAnimations.degreeVec(6.88f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.76f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.16f, KeyframeAnimations.degreeVec(9.23f, -0.1f, 0.57f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.4f, KeyframeAnimations.degreeVec(39.99067f, -0.43399f, 2.46207f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(12.5f, 0f, 20f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.68f, KeyframeAnimations.degreeVec(3.13f, 0f, 20f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.76f, KeyframeAnimations.degreeVec(0f, 0f, 20f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.16f, KeyframeAnimations.degreeVec(4.04f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4f, KeyframeAnimations.degreeVec(17.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.56f, KeyframeAnimations.degreeVec(25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.64f, KeyframeAnimations.degreeVec(75f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.68f, KeyframeAnimations.degreeVec(18.75f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.76f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightHand",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.4f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(27.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.68f, KeyframeAnimations.degreeVec(6.88f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.76f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
	public static final AnimationDefinition REDSTONE_MONSTROSITY_SHOOTSTART = AnimationDefinition.Builder.withLength(1.04f)
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.92f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.92f, KeyframeAnimations.degreeVec(-20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.92f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("jaw",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.92f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.96f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("jaw",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.92f, KeyframeAnimations.degreeVec(22.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.92f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.92f, KeyframeAnimations.degreeVec(66.22614f, -65.41299f, -65.5576f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.92f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.92f, KeyframeAnimations.degreeVec(66.22614f, 65.41299f, 65.5576f), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
	public static final AnimationDefinition REDSTONE_MONSTROSITY_SHOOTIDLE = AnimationDefinition.Builder.withLength(0.52f)
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0f, 4f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0f, 4f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(-20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.degreeVec(-20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0f, 2f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("jaw",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(22.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.degreeVec(45f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.degreeVec(22.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(2f, 0f, 2f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(66.22614f, -65.41299f, -65.5576f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.degreeVec(71.32567f, -58.41041f, -71.32419f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.52f, KeyframeAnimations.degreeVec(66.22614f, -65.41299f, -65.5576f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(-2f, 0f, 2f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(66.22614f, 65.41299f, 65.5576f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.degreeVec(71.32567f, 58.41041f, 71.32419f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.52f, KeyframeAnimations.degreeVec(66.22614f, 65.41299f, 65.5576f), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
	public static final AnimationDefinition REDSTONE_MONSTROSITY_SHOOTSTOP = AnimationDefinition.Builder.withLength(1.2f)
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0f, 4f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0f, 4f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.08f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.2f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(-20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.degreeVec(-20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0f, 2f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("jaw",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(22.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.degreeVec(45f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.degreeVec(22.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(2f, 0f, 2f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.08f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.2f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(66.22614f, -65.41299f, -65.5576f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.degreeVec(71.32567f, -58.41041f, -71.32419f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.52f, KeyframeAnimations.degreeVec(66.22614f, -65.41299f, -65.5576f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.posVec(-2f, 0f, 2f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.08f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.2f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(66.22614f, 65.41299f, 65.5576f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.04f, KeyframeAnimations.degreeVec(71.32567f, 58.41041f, 71.32419f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.52f, KeyframeAnimations.degreeVec(66.22614f, 65.41299f, 65.5576f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
	public static final AnimationDefinition REDSTONE_MONSTROSITY_PUSH = AnimationDefinition.Builder.withLength(2.72f)
			.addAnimation("leftLeg",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(1.6f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.posVec(0f, -5.83f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.84f, KeyframeAnimations.posVec(0f, -7f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.2f, KeyframeAnimations.posVec(0f, -7f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftLeg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(1.6f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.degreeVec(58.33f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.84f, KeyframeAnimations.degreeVec(70f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.2f, KeyframeAnimations.degreeVec(70f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.6f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightLeg",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.84f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.28f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.6f, KeyframeAnimations.posVec(0f, 6f, -3.2f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.68f, KeyframeAnimations.posVec(0f, 0f, -16f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.84f, KeyframeAnimations.posVec(0f, 0f, -16f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.2f, KeyframeAnimations.posVec(0f, 0f, -16f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.84f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.88f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.28f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.6f, KeyframeAnimations.posVec(0f, 0f, -0.6f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.68f, KeyframeAnimations.posVec(0f, 0f, -3f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.72f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.84f, KeyframeAnimations.posVec(0f, 0f, -3f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2f, KeyframeAnimations.posVec(0f, 0f, -3f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.64f, KeyframeAnimations.posVec(0f, 0f, 0.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.72f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.28f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.degreeVec(-3f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.72f, KeyframeAnimations.degreeVec(16f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.84f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.2f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.6f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.84f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.88f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.28f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.6f, KeyframeAnimations.posVec(0f, 0f, -0.6f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.68f, KeyframeAnimations.posVec(0f, 0f, -3f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.72f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.84f, KeyframeAnimations.posVec(0f, 0f, -3f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2f, KeyframeAnimations.posVec(0f, 0f, -3f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.64f, KeyframeAnimations.posVec(0f, 0f, 0.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.72f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.28f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.degreeVec(-3f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.72f, KeyframeAnimations.degreeVec(16f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.84f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.2f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.6f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.28f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.degreeVec(6f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.84f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.6f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("jaw",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.84f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.28f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.6f, KeyframeAnimations.degreeVec(8f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.68f, KeyframeAnimations.degreeVec(40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.84f, KeyframeAnimations.degreeVec(40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.2f, KeyframeAnimations.degreeVec(40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.6f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.posVec(0f, 0f, 2f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.88f, KeyframeAnimations.posVec(0f, 0f, 3f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 2f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.28f, KeyframeAnimations.posVec(0f, 0f, 2f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.posVec(0f, 0.6f, 1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68f, KeyframeAnimations.posVec(0f, 3f, -3f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.72f, KeyframeAnimations.posVec(0f, 1f, -1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.84f, KeyframeAnimations.posVec(0f, 3f, -3f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.2f, KeyframeAnimations.posVec(0f, 3f, -3f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.64f, KeyframeAnimations.posVec(0f, 0f, 0.5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.72f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.degreeVec(35.86854f, -12.49548f, -33.5357f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.28f, KeyframeAnimations.degreeVec(35.86854f, -12.49548f, -33.5357f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.degreeVec(2.5f, -7.71f, -28.78f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.68f, KeyframeAnimations.degreeVec(-120.98084f, 11.43563f, -9.77243f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.2f, KeyframeAnimations.degreeVec(-120.98084f, 11.43563f, -9.77243f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.6f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.degreeVec(-90f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.28f, KeyframeAnimations.degreeVec(-90f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.degreeVec(-54f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.84f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftHand",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.posVec(0f, 19f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.88f, KeyframeAnimations.posVec(0f, 18f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 19f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.6f, KeyframeAnimations.posVec(0f, 19f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.68f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.72f, KeyframeAnimations.posVec(0f, 2f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.84f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftHand",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.degreeVec(0f, 360f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.88f, KeyframeAnimations.degreeVec(0f, 350f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(0f, 360f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.posVec(0f, 0f, 2f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.88f, KeyframeAnimations.posVec(0f, 0f, 3f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 2f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.28f, KeyframeAnimations.posVec(0f, 0f, 2f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.posVec(0f, 0.6f, 1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68f, KeyframeAnimations.posVec(0f, 3f, -3f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.72f, KeyframeAnimations.posVec(0f, 1f, -1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.84f, KeyframeAnimations.posVec(0f, 3f, -3f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.2f, KeyframeAnimations.posVec(0f, 3f, -3f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.64f, KeyframeAnimations.posVec(0f, 0f, 0.5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.72f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.degreeVec(35.86854f, 12.49548f, 33.5357f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.28f, KeyframeAnimations.degreeVec(35.86854f, 12.49548f, 33.5357f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.degreeVec(2.5f, 7.71f, 28.78f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.68f, KeyframeAnimations.degreeVec(-120.98084f, -11.43563f, 9.77243f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.2f, KeyframeAnimations.degreeVec(-120.98084f, -11.43563f, 9.77243f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.6f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.degreeVec(-90f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.28f, KeyframeAnimations.degreeVec(-90f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.degreeVec(-54f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.84f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightHand",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.posVec(0f, 19f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.88f, KeyframeAnimations.posVec(0f, 18f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 19f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.6f, KeyframeAnimations.posVec(0f, 19f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.68f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.72f, KeyframeAnimations.posVec(0f, 2f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.84f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightHand",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.degreeVec(0f, -360f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.88f, KeyframeAnimations.degreeVec(0f, -350f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(0f, -360f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
	public static final AnimationDefinition REDSTONE_MONSTROSITY_FASTSHOOT = AnimationDefinition.Builder.withLength(1.04f)
			.addAnimation("whole",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.28f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.48f, KeyframeAnimations.posVec(0f, -2f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, -6f, -11f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftLeg",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.28f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.48f, KeyframeAnimations.posVec(0f, -2.67f, 3.67f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, -8f, 11f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftLeg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.28f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.48f, KeyframeAnimations.degreeVec(21.67f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.52f, KeyframeAnimations.degreeVec(62.2687883123549f, -24.73906213792452f, 12.40748093976299f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightLeg",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.36f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.48f, KeyframeAnimations.posVec(0f, 7f, -5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, -3f, -7f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightLeg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.36f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.48f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.56f, KeyframeAnimations.posVec(0f, -5f, -3f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.08f, KeyframeAnimations.degreeVec(-3.57f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.28f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.52f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.56f, KeyframeAnimations.degreeVec(-2.5f, 0f, 2.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.6f, KeyframeAnimations.degreeVec(2.5f, 0f, -2.5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.68f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.56f, KeyframeAnimations.posVec(0f, -5f, -3f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.08f, KeyframeAnimations.degreeVec(-3.57f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.28f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.52f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.56f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.68f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.08f, KeyframeAnimations.posVec(0f, 3.57f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.28f, KeyframeAnimations.posVec(0f, 5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.48f, KeyframeAnimations.posVec(0f, 2.33f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, -3f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.56f, KeyframeAnimations.posVec(0f, -6f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.48f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.52f, KeyframeAnimations.degreeVec(0f, -11.313354172283653f, 16.60178977406713f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.6f, KeyframeAnimations.degreeVec(0f, 4.295099691046689f, -6.158156627440256f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.68f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("jaw",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.28f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.48f, KeyframeAnimations.degreeVec(11.11f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.56f, KeyframeAnimations.degreeVec(33.344110982567145f, -11.313354172283653f, 16.60178977406713f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(44.76401615451218f, 4.295099691046689f, -6.158156627440256f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.72f, KeyframeAnimations.degreeVec(40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.04f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.08f, KeyframeAnimations.posVec(0f, 17.14f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.28f, KeyframeAnimations.posVec(0f, 20f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.48f, KeyframeAnimations.posVec(0f, 4.67f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, -26f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.56f, KeyframeAnimations.posVec(0f, -31f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.64f, KeyframeAnimations.posVec(0f, -26f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.08f, KeyframeAnimations.degreeVec(-17.08f, -1.46f, -4.03f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.28f, KeyframeAnimations.degreeVec(-19.929896064562854f, -1.7081840554196788f, -4.699856911810457f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.48f, KeyframeAnimations.degreeVec(-6.67f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftHand",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.28f, KeyframeAnimations.posVec(0f, 19f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.48f, KeyframeAnimations.posVec(0f, 12.67f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.56f, KeyframeAnimations.posVec(0f, 6f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.08f, KeyframeAnimations.posVec(0f, 17.14f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.28f, KeyframeAnimations.posVec(0f, 20f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.48f, KeyframeAnimations.posVec(0f, 4.67f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.posVec(0f, -26f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.56f, KeyframeAnimations.posVec(0f, -31f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.64f, KeyframeAnimations.posVec(0f, -26f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.08f, KeyframeAnimations.degreeVec(-17.08f, 1.46f, 4.03f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.28f, KeyframeAnimations.degreeVec(-19.929896064562854f, 1.7081840554196788f, 4.699856911810457f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.48f, KeyframeAnimations.degreeVec(-6.67f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightHand",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.28f, KeyframeAnimations.posVec(0f, 19f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.48f, KeyframeAnimations.posVec(0f, 12.67f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.56f, KeyframeAnimations.posVec(0f, 6f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
	public static final AnimationDefinition REDSTONE_MONSTROSITY_LEAP = AnimationDefinition.Builder.withLength(0.76f)
			.addAnimation("leftLeg",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.32f, KeyframeAnimations.posVec(0f, -4f, -7f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.36f, KeyframeAnimations.posVec(0f, -3f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftLeg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.32f, KeyframeAnimations.degreeVec(-50f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.36f, KeyframeAnimations.degreeVec(35f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightLeg",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.28f, KeyframeAnimations.posVec(0f, -8f, -11f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.32f, KeyframeAnimations.posVec(0f, -8f, -11f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.4f, KeyframeAnimations.posVec(0f, 0f, 17f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightLeg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.28f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.36f, KeyframeAnimations.degreeVec(77.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.28f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.68f, KeyframeAnimations.posVec(0f, 0f, 0.5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.76f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lowerBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.28f, KeyframeAnimations.degreeVec(40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.36f, KeyframeAnimations.degreeVec(-20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.68f, KeyframeAnimations.posVec(0f, 0f, 0.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.76f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upperBody",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.28f, KeyframeAnimations.degreeVec(42.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4f, KeyframeAnimations.degreeVec(-17.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.64f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.64f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.28f, KeyframeAnimations.posVec(0f, -15f, -12f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.36f, KeyframeAnimations.posVec(0f, -29f, -12f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.4f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.68f, KeyframeAnimations.posVec(0f, 0f, 0.5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.76f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(-87.22503f, 12.85989f, -80.62848f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.28f, KeyframeAnimations.degreeVec(-87.29462864074593f, 0.37406726550521796f, -81.22888243756904f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.36f, KeyframeAnimations.degreeVec(-19.01586593292636f, 30.246645157690295f, -12.341370114289916f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.4f, KeyframeAnimations.degreeVec(60.41334771258164f, 8.495169452912634f, -11.537265536695983f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftWrist",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.24f, KeyframeAnimations.posVec(6f, 17f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.36f, KeyframeAnimations.posVec(-3f, 4f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 7.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.36f, KeyframeAnimations.degreeVec(0f, 0f, 7.5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.44f, KeyframeAnimations.degreeVec(50f, 0f, -2.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("leftHand",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.28f, KeyframeAnimations.posVec(0f, 7f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.36f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.28f, KeyframeAnimations.posVec(0f, -15f, -12f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.36f, KeyframeAnimations.posVec(0f, -29f, -12f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.4f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.68f, KeyframeAnimations.posVec(0f, 0f, 0.5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.76f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightShoulder",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(-87.22503f, -12.85989f, 80.62848f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.28f, KeyframeAnimations.degreeVec(-87.29462864074593f, -0.37406726550521796f, 81.22888243756904f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.36f, KeyframeAnimations.degreeVec(-19.01586593292636f, -30.246645157690295f, 12.341370114289916f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.4f, KeyframeAnimations.degreeVec(60.41334771258164f, -8.495169452912634f, 11.537265536695983f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightWrist",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.24f, KeyframeAnimations.posVec(-6f, 17f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.36f, KeyframeAnimations.posVec(3f, 4f, 0f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightWrist",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.36f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.44f, KeyframeAnimations.degreeVec(50f, 0f, 2.5f), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.64f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rightHand", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.28f, KeyframeAnimations.posVec(0f, 7f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
}
