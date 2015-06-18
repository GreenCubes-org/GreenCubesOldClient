package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.greencubes.gui.FancyGUI;
import org.greencubes.gui.GuiAdapter;
import org.greencubes.gui.InputField;
import org.greencubes.gui.Tooltip;
import org.greencubes.party.Party;
import org.lwjgl.input.Keyboard;

public class GuiParty extends GuiScreen {
	
	protected static final FancyGUI gui = FancyGUI.getInstance(); // Short-cut
	
	private GuiAdapter adapter = new GuiAdapter();
	private boolean isTyping = true;
	private Party party;
	private int updateCounter = 0;
	private Tooltip playerInviteTooltip = new Tooltip("Введите ник игрока, чтобы пригласить его в группу. Используйте o:<имя организации>, чтобы пригласить в группу всю организацию.", 0, 0, 150, 0);
	private InputField playerInputField = new InputField(45, 125);
	
	public GuiParty() {
		playerInputField.toolTip = playerInviteTooltip;
	}
	
	@Override
	public void onGuiClosed() {
		if(playerInputField.isTyping)
			Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	public void updateScreen() {
		this.updateCounter++;
		this.playerInputField.update();
		if(playerInputField.isTyping)
			Keyboard.enableRepeatEvents(true);
	}
	
	@Override
	public void setWorldAndResolution(Minecraft minecraft, int i, int j) {
		super.setWorldAndResolution(minecraft, i, j);
		this.party = minecraft.thePlayer.party;
	}
	
	@Override
	protected void keyTyped(char c, int i) {
		if(playerInputField.isTyping) {
			playerInputField.keyTyped(c, i);
			return;
		}
		if(i == mc.gameSettings.keyParty.keyCode) {
			mc.displayGuiScreen(null);
			mc.setIngameFocus();
		}
		super.keyTyped(c, i);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		drawDefaultBackground();
		ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		FontRenderer fr = mc.fontRenderer;
		
		float scale = 1f / scaledresolution.scaleFactor;
		int guiWidth = 150;
		int guiHeight = 250;
		int guiStartX = (width - guiWidth) / 2;
		int guiStartY = (height - guiHeight) / 2;
		
		adapter.startFrame(width, height, mouseX, mouseY, f, scaledresolution.scaleFactor);
		
		gui.enableMode();
		gui.setScale(scale);
		gui.renderInterfaceNinePart(guiStartX, guiStartY, guiWidth, guiHeight, 199, 213, gui.inventoryWindowNPI);
		gui.setScale(1f);
		
		int h = guiStartY + 5;
		
		fr.drawStringWithShadow("Ваша группа", guiStartX + guiWidth / 2 - fr.getStringWidth("Ваша группа") / 2, h, 0xFFFFFF);
		h += 10;
		fr.drawStringWithShadow("Лидер группы: " + (party.leader == null ? "Вы" : party.leader.coloredName), guiStartX + 5, h, 0xFFFFFF);
		h += 13;
		if(party.isPlayerLeader()) {
			fr.drawStringWithShadow("Пригласить игрока в группу: ", guiStartX + 5, h, 0xFFFFFF);
			h += 13;
			playerInputField.isTyping = true; // TODO
			playerInputField.render(adapter, guiStartX + 5, h, scale);
			gui.enableMode();
			gui.renderInterface(guiStartX + guiWidth - 15, h + 1.5f, 9.5f, 9.5f, 54, 289, 19, 19);
			
			h += 13;
			
		}
		
		fr.drawStringWithShadow("_NEXT_LINE_", guiStartX + 7, h, 0xFFFFFF);
		
		adapter.endFrame();
	}
	
}
