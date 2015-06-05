package net.lahwran.wecui.obf;

import net.lahwran.WorldRenderEvent;
import net.lahwran.fevents.EventManager;
import net.minecraft.src.Entity;
import net.minecraft.src.Render;
import net.minecraft.src.RenderHelper;

public class RenderHooks extends Render {
	
	public RenderHooks() {
		System.out.println("Attaching worldeditcui renderer step 2");
	}

	private void render(float renderTick) {
		RenderHelper.disableStandardItemLighting();
		WorldRenderEvent renderEvent = WorldRenderEvent.update(renderTick);
		EventManager.callEvent(renderEvent);
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		render(f1);
	}
}