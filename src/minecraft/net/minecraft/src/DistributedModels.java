package net.minecraft.src;

public class DistributedModels {

	public static final ModelBiped modelArmorChestplate = new ModelBiped(1.0F);
	public static final ModelBiped modelArmor = new ModelBiped(0.5F);

	private static final ModelBiped[] bipedModels = new ModelBiped[]{modelArmorChestplate, modelArmor};

	private DistributedModels() {
	}

	public static void updateForRender(EntityLiving entity) {
		ItemStack itemstack = entity.getEquipment(0);

		for(ModelBiped model : bipedModels) {
			model.field_1278_i = itemstack == null ? 0 : 1;
			if(itemstack != null && entity instanceof EntityPlayer && ((EntityPlayer) entity).func_35205_Y() > 0) {
				EnumAction enumaction = itemstack.getItemUseAction();
				if(enumaction == EnumAction.block) {
					model.field_1278_i = 3;
				} else if(enumaction == EnumAction.bow) {
					model.field_40333_u = true;
				}
			}
			model.isSneak = entity.isSneaking();
		}
	}

	public static void reset() {
		for(ModelBiped model : bipedModels) {
			model.field_40333_u = false;
			model.isSneak = false;
			model.field_1278_i = 0;
		}
	}

}
