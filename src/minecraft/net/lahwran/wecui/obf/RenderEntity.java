package net.lahwran.wecui.obf;

import net.lahwran.wecui.WorldEditCUI;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class RenderEntity extends Entity {
	private Minecraft mc;

	public RenderEntity(Minecraft mc, World arg0) {
		super(arg0);
		this.ignoreFrustumCheck = true;
		this.mc = mc;
		WorldEditCUI.debug("Entity spawned");
	}

	@Override
	public void onUpdate() {
		setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
	}
	
	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub
		
	}
}