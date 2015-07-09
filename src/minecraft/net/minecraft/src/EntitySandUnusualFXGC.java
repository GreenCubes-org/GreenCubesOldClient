package net.minecraft.src;

import static net.minecraft.src.GreenTextures.*;

public class EntitySandUnusualFXGC extends EntitySandFXGC {

	public EntitySandUnusualFXGC(World world, double x, double y, double z, Entity source) {
		super(world, x, y, z, source);
		textures = new int[][] {{P_S_U_1_1, P_S_U_1_2, P_S_U_1_3, P_S_U_1_4}, {P_S_U_2_1, P_S_U_2_2, P_S_U_2_3, P_S_U_2_4}, {P_S_U_3_1, P_S_U_3_2, P_S_U_3_3, P_S_U_3_4}, {P_S_U_4_1, P_S_U_4_2, P_S_U_4_3, P_S_U_4_4}};
		pickInitialTexture();
	}
	
	public EntitySandUnusualFXGC(World world, double x, double y, double z, Vec3D source) {
		super(world, x, y, z, source);
		textures = new int[][] {{P_S_U_1_1, P_S_U_1_2, P_S_U_1_3, P_S_U_1_4}, {P_S_U_2_1, P_S_U_2_2, P_S_U_2_3, P_S_U_2_4}, {P_S_U_3_1, P_S_U_3_2, P_S_U_3_3, P_S_U_3_4}, {P_S_U_4_1, P_S_U_4_2, P_S_U_4_3, P_S_U_4_4}};
		pickInitialTexture();
	}
}
