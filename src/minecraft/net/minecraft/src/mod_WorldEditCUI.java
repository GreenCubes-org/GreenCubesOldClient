package net.minecraft.src;

import java.util.Map;
import net.lahwran.wecui.WorldEditCUI;
import net.lahwran.wecui.obf.RenderEntity;
import net.lahwran.wecui.obf.RenderHooks;
import net.minecraft.client.Minecraft;

public class mod_WorldEditCUI extends BaseMod {

	public static World lastworld;
	public static EntityPlayerSP lastplayer;
	public static RenderEntity entity;

	@Override
	public String getVersion() {
		return "v1.0 for GreenCubes";
	}

	public mod_WorldEditCUI() {
		ModLoader.SetInGameHook(this, true, true);
	}

	public static void spawn(Minecraft mc) {
		entity = new RenderEntity(mc, mc.theWorld);
		entity.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
		mc.theWorld.entityJoinedWorld(entity);
		entity.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
		WorldEditCUI.debug("spawned render entity");
	}

	@Override
	public boolean OnTickInGame(float partialTicks, Minecraft mc) {
		if(mc.theWorld != lastworld || mc.thePlayer != lastplayer || (entity != null && entity.isDead)) {
			spawn(mc);
			lastworld = mc.theWorld;
			lastplayer = mc.thePlayer;
		}
		return true;
	}

	@Override
	public void load() {
	}

	@Override
	public void AddRenderer(Map map) {
		WorldEditCUI.debug("Attaching worldeditcui renderer");
		map.put(RenderEntity.class, new RenderHooks());
	}

	static {
		WorldEditCUI.initialize(ModLoader.getMinecraftInstance());
	}
}