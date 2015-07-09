package net.minecraft.src;

import static net.minecraft.src.GreenTextures.*;

public class EntityLeavesFXGC extends EntitySandFXGC {
	
	public EntityLeavesFXGC(World world, double x, double y, double z, Entity source) {
		super(world, x, y, z, source);
		textures = new int[][] {{P_LEAVES_1_1, P_LEAVES_1_2, P_LEAVES_1_3, P_LEAVES_1_4}, {P_LEAVES_2_1, P_LEAVES_2_2, P_LEAVES_2_3, P_LEAVES_2_4}, {P_LEAVES_3_1, P_LEAVES_3_2, P_LEAVES_3_3, P_LEAVES_3_4}, {P_LEAVES_4_1, P_LEAVES_4_2, P_LEAVES_4_3, P_LEAVES_4_4}};
		pickInitialTexture();
	}
	
	public EntityLeavesFXGC(World world, double x, double y, double z, Vec3D source) {
		super(world, x, y, z, source);
		textures = new int[][] {{P_LEAVES_1_1, P_LEAVES_1_2, P_LEAVES_1_3, P_LEAVES_1_4}, {P_LEAVES_2_1, P_LEAVES_2_2, P_LEAVES_2_3, P_LEAVES_2_4}, {P_LEAVES_3_1, P_LEAVES_3_2, P_LEAVES_3_3, P_LEAVES_3_4}, {P_LEAVES_4_1, P_LEAVES_4_2, P_LEAVES_4_3, P_LEAVES_4_4}};
		pickInitialTexture();
	}
	
}
