package net.lahwran.wecui.rendering;

import net.lahwran.WorldRenderEvent;
import net.lahwran.fevents.Listener;
import net.lahwran.wecui.WorldEditCUI;
import net.lahwran.wecui.obf.ObfHub;
import org.lwjgl.opengl.GL11;

public class WorldRenderListener implements Listener<WorldRenderEvent> {
	private WorldEditCUI wecui;

	public WorldRenderListener(WorldEditCUI wecui) {
		this.wecui = wecui;
	}

	@Override
	public void onEvent(WorldRenderEvent event) {
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glDisable(3008);
		GL11.glDisable(3553);
		GL11.glDepthMask(false);
		GL11.glPushMatrix();
		ObfHub obf = this.wecui.getObfHub();
		GL11.glTranslated(-obf.getPlayerX(event.partialTick), -obf.getPlayerY(event.partialTick), -obf.getPlayerZ(event.partialTick));

		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		if(this.wecui.getSelection() != null)
			this.wecui.getSelection().render();
		GL11.glDepthFunc(515);
		GL11.glPopMatrix();

		GL11.glDepthMask(true);
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glEnable(3008);
	}
}