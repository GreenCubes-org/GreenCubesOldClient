// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Map;
import java.util.Random;
import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            World, ItemStack, KeyBinding, EntityPlayer, 
//            GuiScreen, RenderBlocks, Block, IBlockAccess, 
//            IInventory

public abstract class BaseMod {

	public BaseMod() {
	}

	public int AddFuel(int i, int j) {
		return 0;
	}

	public void AddRenderer(Map map) {
	}

	public boolean DispenseEntity(World world, double d, double d1, double d2, int i, int j, ItemStack itemstack) {
		return false;
	}

	public void GenerateNether(World world, Random random, int i, int j) {
	}

	public void GenerateSurface(World world, Random random, int i, int j) {
	}

	public String getName() {
		return getClass().getSimpleName();
	}

	public String getPriorities() {
		return "";
	}

	public abstract String getVersion();

	public void KeyboardEvent(KeyBinding keybinding) {
	}

	public abstract void load();

	public void ModsLoaded() {
	}

	public void OnItemPickup(EntityPlayer entityplayer, ItemStack itemstack) {
	}

	public boolean OnTickInGame(float f, Minecraft minecraft) {
		return false;
	}

	public boolean OnTickInGUI(float f, Minecraft minecraft, GuiScreen guiscreen) {
		return false;
	}

	public void RegisterAnimation(Minecraft minecraft) {
	}

	public void RenderInvBlock(RenderBlocks renderblocks, Block block, int i, int j) {
	}

	public boolean RenderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l) {
		return false;
	}

	public void TakenFromCrafting(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory) {
	}

	public void TakenFromFurnace(EntityPlayer entityplayer, ItemStack itemstack) {
	}

	public boolean onMessageSent(String message, GChannel channel) {
		return true;
	}

	public boolean onMessageRecieved(String message, GChannel channel) {
		return true;
	}

	@Override
	public String toString() {
		return (new StringBuilder(String.valueOf(getName()))).append(' ').append(getVersion()).toString();
	}
}
