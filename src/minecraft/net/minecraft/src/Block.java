// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import org.greencubes.compatibility.BlockDurability;
import org.greencubes.compatibility.BlockDurabilityFactory;

import static net.minecraft.src.GreenTextures.*;

public class Block {

	public static final String dyeColorNames[] = {"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};

	public static final int blocksLength = 4096 + 256;// GreenCubes

	public static final StepSound soundPowderFootstep = new StepSound("stone", 1.0F, 1.0F);
	public static final StepSound soundWoodFootstep = new StepSound("wood", 1.0F, 1.0F);
	public static final StepSound soundGravelFootstep = new StepSound("gravel", 1.0F, 1.0F);
	public static final StepSound soundGrassFootstep = new StepSound("grass", 1.0F, 1.0F);
	public static final StepSound soundStoneFootstep = new StepSound("stone", 1.0F, 1.0F);
	public static final StepSound soundMetalFootstep = new StepSound("stone", 1.0F, 1.5F);
	public static final StepSound soundGlassFootstep = new StepSoundStone("stone", 1.0F, 1.0F);
	public static final StepSound soundClothFootstep = new StepSound("cloth", 1.0F, 1.0F);
	public static final StepSound soundSandFootstep = new StepSoundSand("sand", 1.0F, 1.0F);
	public static final Block[] blocksList = new Block[blocksLength];
	public static final boolean[] transparentMaterial = new boolean[blocksLength];
	public static boolean[] useNeighborBrightness = new boolean[blocksLength];
	public static final boolean tickOnLoad[] = new boolean[blocksLength];
	public static final boolean opaqueCubeLookup[] = new boolean[blocksLength];
	public static final boolean isBlockContainer[] = new boolean[blocksLength];
	public static final int lightOpacity[] = new int[blocksLength];
	public static final int lightValue[] = new int[blocksLength];
	public static final boolean requiresSelfNotify[] = new boolean[blocksLength];
	public static final boolean catchesLight[] = new boolean[blocksLength];
	public static final boolean[] multipassBlocks = new boolean[blocksLength];

	public static final Block stone = new BlockStone(1, 1).setHardness(1.5F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("stone");
	public static final Block grass = new BlockGrass(2).setHardness(0.6F).setStepSound(soundGrassFootstep).setBlockName("grass");
	public static final Block dirt = new BlockDirt(3, 2).setHardness(0.5F).setStepSound(soundGravelFootstep).setBlockName("dirt");
	public static final Block cobblestone = new Block(4, 16, Material.rock).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("stonebrick");
	public static final Block planks = new BlockPlanks(5).setHardness(2.0F).setResistance(5F).setStepSound(soundWoodFootstep).setBlockName("planks").setRequiresSelfNotify();
	public static final Block sapling = new BlockSapling(6, 15).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("sapling").setRequiresSelfNotify();
	public static final Block bedrock = new Block(7, 17, Material.rock).setBlockUnbreakable().setResistance(6000000F).setStepSound(soundStoneFootstep).setBlockName("bedrock").disableStats();
	public static final Block waterMoving = new BlockFlowing(8, Material.water).setHardness(100F).setLightOpacity(3).setBlockName("water").disableStats().setRequiresSelfNotify();
	public static final Block waterStill = new BlockStationary(9, Material.water).setHardness(100F).setLightOpacity(3).setBlockName("water").disableStats().setRequiresSelfNotify();
	public static final Block lavaMoving = new BlockFlowing(10, Material.lava).setHardness(0.0F).setLightValue(1.0F).setLightOpacity(255).setBlockName("lava").disableStats().setRequiresSelfNotify();
	public static final Block lavaStill = new BlockStationary(11, Material.lava).setHardness(100F).setLightValue(1.0F).setLightOpacity(255).setBlockName("lava").disableStats().setRequiresSelfNotify();
	public static final Block sand = new BlockSand(12, 18).setHardness(0.5F).setStepSound(soundSandFootstep).setBlockName("sand");
	public static final Block gravel = new BlockGravel(13, 19).setHardness(0.6F).setStepSound(soundGravelFootstep).setBlockName("gravel");
	public static final Block oreGold = new BlockOre(14, 32).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("oreGold");
	public static final Block oreIron = new BlockOre(15, 33).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("oreIron");
	public static final Block oreCoal = new BlockOre(16, 34).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("oreCoal");
	public static final Block wood = new BlockLog(17).setHardness(2.0F).setStepSound(soundWoodFootstep).setBlockName("log").setRequiresSelfNotify();
	public static final BlockLeaves leaves = (BlockLeaves) new BlockLeaves(18, 52).setHardness(0.2F).setLightOpacity(1).setStepSound(soundGrassFootstep).setBlockName("leaves").setRequiresSelfNotify();
	public static final Block sponge = new BlockSponge(19).setHardness(0.6F).setStepSound(soundGrassFootstep).setBlockName("sponge");
	public static final Block glass = new BlockGlassNew(20, 49, Material.glass, false).setHardness(0.3F).setStepSound(soundGlassFootstep).setBlockName("glass");
	public static final Block oreLapis = new BlockOre(21, 160).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("oreLapis");
	public static final Block blockLapis = new Block(22, 144, Material.rock).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("blockLapis");
	public static final Block dispenser = new BlockDispenser(23).setHardness(3.5F).setStepSound(soundStoneFootstep).setBlockName("dispenser").setRequiresSelfNotify();
	public static final Block sandStone = new BlockSandStone(24).setStepSound(soundStoneFootstep).setHardness(0.8F).setBlockName("sandStone");
	public static final Block music = new BlockNote(25).setHardness(0.8F).setBlockName("musicBlock").setRequiresSelfNotify();
	public static final Block bed = new BlockBed(26).setHardness(0.2F).setBlockName("bed").disableStats().setRequiresSelfNotify();
	public static final Block railPowered = new BlockRail(27, 179, true).setHardness(0.7F).setStepSound(soundMetalFootstep).setBlockName("goldenRail").setRequiresSelfNotify();
	public static final Block railDetector = new BlockDetectorRail(28, 195).setHardness(0.7F).setStepSound(soundMetalFootstep).setBlockName("detectorRail").setRequiresSelfNotify();
	public static final Block pistonStickyBase = new BlockPistonBase(29, 106, true).setBlockName("pistonStickyBase").setRequiresSelfNotify();
	public static final Block web = new BlockWeb(30, 11).setLightOpacity(1).setHardness(4F).setBlockName("web");
	public static final Block tallGrass = new BlockTallGrass(31, 39).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("tallgrass");
	public static final Block deadBush = new BlockDeadBush(32, 55).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("deadbush");
	public static final Block pistonBase = new BlockPistonBase(33, 107, false).setBlockName("pistonBase").setRequiresSelfNotify();
	public static final BlockPistonExtension pistonExtension = (BlockPistonExtension) new BlockPistonExtension(34, 107).setRequiresSelfNotify();
	public static final Block wool = new BlockWool().setHardness(0.8F).setStepSound(soundClothFootstep).setBlockName("cloth").setRequiresSelfNotify();
	public static final BlockPistonMoving pistonMoving = new BlockPistonMoving(36);
	public static final Block plantYellow = new BlockFlower(37, 13).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("flower");
	public static final Block plantRed = new BlockFlower(38, 12).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("rose");
	public static final Block mushroomBrown = new BlockMushroom(39, 29).setHardness(0.0F).setStepSound(soundGrassFootstep).setLightValue(0.125F).setBlockName("mushroom");
	public static final Block mushroomRed = new BlockMushroom(40, 28).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("mushroom");
	public static final Block blockGold = new BlockOreStorage(41, 23).setHardness(3F).setResistance(10F).setStepSound(soundMetalFootstep).setBlockName("blockGold");
	public static final Block blockSteel = new BlockOreStorage(42, 22).setHardness(5F).setResistance(10F).setStepSound(soundMetalFootstep).setBlockName("blockIron");
	public static final Block stairDouble = new BlockStep(43, true).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("stoneSlab");
	public static final Block stairSingle = new BlockStep(44, false).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("stoneSlab");
	public static final Block brick = new Block(45, 7, Material.rock).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("brick");
	public static final Block tnt = new BlockTNT(46, 8).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("tnt");
	public static final Block bookShelf = new BlockBookshelf(47, 35).setHardness(1.5F).setStepSound(soundWoodFootstep).setBlockName("bookshelf");
	public static final Block cobblestoneMossy = new Block(48, 36, Material.rock).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("stoneMoss");
	public static final Block obsidian = new BlockObsidian(49, 37).setHardness(50F).setResistance(2000F).setStepSound(soundStoneFootstep).setBlockName("obsidian");
	public static final Block torchWood = new BlockTorch(50, 80).setHardness(0.0F).setLightValue(0.9375F).setStepSound(soundWoodFootstep).setBlockName("torch").setRequiresSelfNotify();
	public static final BlockFire fire = (BlockFire) new BlockFire(51, 31).setHardness(0.0F).setLightValue(1.0F).setStepSound(soundWoodFootstep).setBlockName("fire").disableStats();
	public static final Block mobSpawner = new BlockMobSpawner(52, 65).setHardness(5F).setStepSound(soundMetalFootstep).setBlockName("mobSpawner").disableStats();
	public static final Block stairCompactPlanks = new BlockStairs(53, planks).setBlockName("stairsWood").setRequiresSelfNotify();
	public static final Block chest = new BlockChest(54).setHardness(2.5F).setStepSound(soundWoodFootstep).setBlockName("chest").setRequiresSelfNotify();
	public static final Block redstoneWire = new BlockRedstoneWire(55, 164).setHardness(0.0F).setStepSound(soundPowderFootstep).setBlockName("redstoneDust").disableStats().setRequiresSelfNotify();
	public static final Block oreDiamond = new BlockOre(56, 50).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("oreDiamond");
	public static final Block blockDiamond = new BlockOreStorage(57, 24).setHardness(5F).setResistance(10F).setStepSound(soundMetalFootstep).setBlockName("blockDiamond");
	public static final Block workbench = new BlockWorkbench(58).setHardness(2.5F).setStepSound(soundWoodFootstep).setBlockName("workbench");
	public static final Block crops = new BlockCrops(59, 88).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("crops").disableStats().setRequiresSelfNotify();
	public static final Block tilledField = new BlockFarmland(60).setHardness(0.6F).setStepSound(soundGravelFootstep).setBlockName("farmland").setRequiresSelfNotify();
	public static final Block stoneOvenIdle = new BlockFurnace(61, false).setHardness(3.5F).setStepSound(soundStoneFootstep).setBlockName("furnace").setRequiresSelfNotify();
	public static final Block stoneOvenActive = new BlockFurnace(62, true).setHardness(3.5F).setStepSound(soundStoneFootstep).setLightValue(0.875F).setBlockName("furnace").setRequiresSelfNotify();
	public static final Block signPost = new BlockSign(63, TileEntitySign.class, true).setHardness(1.0F).setStepSound(soundWoodFootstep).setBlockName("sign").disableStats().setRequiresSelfNotify();
	public static final Block doorWood = new BlockDoor(64, Material.wood).setHardness(3F).setStepSound(soundWoodFootstep).setBlockName("doorWood").disableStats().setRequiresSelfNotify();
	public static final Block ladder = new BlockLadder(65, 83).setHardness(0.4F).setStepSound(soundWoodFootstep).setBlockName("ladder").setRequiresSelfNotify();
	public static final Block rail = new BlockRail(66, 128, false).setHardness(0.7F).setStepSound(soundMetalFootstep).setBlockName("rail").setRequiresSelfNotify();
	public static final Block stairCompactCobblestone = new BlockStairs(67, cobblestone).setBlockName("stairsStone").setRequiresSelfNotify();
	public static final Block signWall = new BlockSign(68, TileEntitySign.class, false).setHardness(1.0F).setStepSound(soundWoodFootstep).setBlockName("sign").disableStats().setRequiresSelfNotify();
	public static final Block lever = new BlockLever(69, 96).setHardness(0.5F).setStepSound(soundWoodFootstep).setBlockName("lever").setRequiresSelfNotify();
	public static final Block pressurePlateStone = new BlockPressurePlate(70, stone.blockIndexInTexture, EnumMobType.mobs, Material.rock).setHardness(0.5F).setStepSound(soundStoneFootstep).setBlockName("pressurePlate").setRequiresSelfNotify();
	public static final Block doorSteel = new BlockDoor(71, Material.iron).setHardness(5F).setStepSound(soundMetalFootstep).setBlockName("doorIron").disableStats().setRequiresSelfNotify();
	public static final Block pressurePlatePlanks = new BlockPressurePlate(72, planks.blockIndexInTexture, EnumMobType.everything, Material.wood).setHardness(0.5F).setStepSound(soundWoodFootstep).setBlockName("pressurePlate").setRequiresSelfNotify();
	public static final Block oreRedstone = new BlockRedstoneOre(73, 51, false).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("oreRedstone").setRequiresSelfNotify();
	public static final Block oreRedstoneGlowing = new BlockRedstoneOre(74, 51, true).setLightValue(0.625F).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("oreRedstone").setRequiresSelfNotify();
	public static final Block torchRedstoneIdle = new BlockRedstoneTorch(75, 115, false).setHardness(0.0F).setStepSound(soundWoodFootstep).setBlockName("notGate").setRequiresSelfNotify();
	public static final Block torchRedstoneActive = new BlockRedstoneTorch(76, 99, true).setHardness(0.0F).setLightValue(0.5F).setStepSound(soundWoodFootstep).setBlockName("notGate").setRequiresSelfNotify();
	public static final Block button = new BlockButton(77, stone.blockIndexInTexture).setHardness(0.5F).setStepSound(soundStoneFootstep).setBlockName("button").setRequiresSelfNotify();
	public static final Block snow = new BlockSnow(78, 66).setHardness(0.1F).setStepSound(soundClothFootstep).setBlockName("snow").setLightOpacity(0);
	public static final Block ice = new BlockIce(79, 67).setHardness(0.5F).setLightOpacity(3).setStepSound(soundGlassFootstep).setBlockName("ice");
	public static final Block blockSnow = new BlockSnowBlock(80, 66).setHardness(0.2F).setStepSound(soundClothFootstep).setBlockName("snow");
	public static final Block cactus = new BlockCactus(81, 70).setHardness(0.4F).setStepSound(soundClothFootstep).setBlockName("cactus");
	public static final Block blockClay = new BlockClay(82, 72).setHardness(0.6F).setStepSound(soundGravelFootstep).setBlockName("clay");
	public static final Block reed = new BlockReed(83, 73).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("reeds").disableStats();
	public static final Block jukebox = new BlockJukeBox(84, 74).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("jukebox").setRequiresSelfNotify();
	public static final Block fence = new BlockFence(85, 4).setHardness(2.0F).setResistance(5F).setStepSound(soundWoodFootstep).setBlockName("fence");
	public static final Block pumpkin = new BlockPumpkin(86, 102, false).setHardness(1.0F).setStepSound(soundWoodFootstep).setBlockName("pumpkin").setRequiresSelfNotify();
	public static final Block netherrack = new BlockNetherrack(87, 103).setHardness(0.4F).setStepSound(soundStoneFootstep).setBlockName("hellrock");
	public static final Block slowSand = new BlockSoulSand(88, 104).setHardness(0.5F).setStepSound(soundSandFootstep).setBlockName("hellsand");
	public static final Block glowStone = new BlockGlowStone(89, 105, Material.glass).setHardness(0.3F).setStepSound(soundGlassFootstep).setLightValue(1.0F).setBlockName("lightgem");
	public static final BlockPortal portal = (BlockPortal) new BlockPortal(90, 14).setHardness(-1F).setStepSound(soundGlassFootstep).setLightValue(0.75F).setBlockName("portal");
	public static final Block pumpkinLantern = new BlockPumpkin(91, 102, true).setHardness(1.0F).setStepSound(soundWoodFootstep).setLightValue(1.0F).setBlockName("litpumpkin").setRequiresSelfNotify();
	public static final Block cake = new BlockCake(92, 121).setHardness(0.5F).setStepSound(soundClothFootstep).setBlockName("cake").disableStats().setRequiresSelfNotify();
	public static final Block redstoneRepeaterIdle = new BlockRedstoneRepeater(93, false).setHardness(0.0F).setStepSound(soundWoodFootstep).setBlockName("diode").disableStats().setRequiresSelfNotify();
	public static final Block redstoneRepeaterActive = new BlockRedstoneRepeater(94, true).setHardness(0.0F).setLightValue(0.625F).setStepSound(soundWoodFootstep).setBlockName("diode").disableStats().setRequiresSelfNotify();
	//public static final Block lockedChest = (new BlockLockedChest(95)).setHardness(0.0F).setLightValue(1.0F).setStepSound(soundWoodFootstep).setBlockName("lockedchest").setTickOnLoad(true).setRequiresSelfNotify();
	public static final Block trapdoor = new BlockTrapDoor(96, Material.wood).setHardness(3F).setStepSound(soundWoodFootstep).setBlockName("trapdoor").disableStats().setRequiresSelfNotify();
	public static final Block silverfish = new BlockSilverfish(97).setHardness(0.75F);
	public static final Block stoneBrick = new BlockStoneBrick(98).setHardness(1.5F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("stonebricksmooth");
	public static final Block mushroomCapBrown = new BlockMushroomCap(99, Material.wood, 142, 0).setHardness(0.2F).setStepSound(soundWoodFootstep).setBlockName("mushroom").setRequiresSelfNotify();
	public static final Block mushroomCapRed = new BlockMushroomCap(100, Material.wood, 142, 1).setHardness(0.2F).setStepSound(soundWoodFootstep).setBlockName("mushroom").setRequiresSelfNotify();
	public static final Block fenceIron = new BlockPane(101, 85, pane_iron, pane_iron, Material.iron, true).setHardness(5F).setResistance(10F).setStepSound(soundMetalFootstep).setBlockName("fenceIron");
	public static final Block thinGlass = new BlockPane(102, 49, glass_pane, glass_pane_cut, Material.glass, false).setHardness(0.3F).setStepSound(soundGlassFootstep).setBlockName("thinGlass");
	public static final Block melon = new BlockMelon(103).setHardness(1.0F).setStepSound(soundWoodFootstep).setBlockName("melon");
	public static final Block pumpkinStem = new BlockStem(104, pumpkin).setHardness(0.0F).setStepSound(soundWoodFootstep).setBlockName("pumpkinStem").setRequiresSelfNotify();
	public static final Block melonStem = new BlockStem(105, melon).setHardness(0.0F).setStepSound(soundWoodFootstep).setBlockName("pumpkinStem").setRequiresSelfNotify();
	public static final Block vine = new BlockVine(106).setHardness(0.2F).setStepSound(soundGrassFootstep).setBlockName("vine").setRequiresSelfNotify();
	public static final Block fenceGate = new BlockFenceGate(107, 4).setHardness(2.0F).setResistance(5F).setStepSound(soundWoodFootstep).setBlockName("fenceGate").setRequiresSelfNotify();
	public static final Block stairsBrick = new BlockStairs(108, brick).setBlockName("stairsBrick").setRequiresSelfNotify();
	public static final Block stairsStoneBrickSmooth = new BlockStairs(109, stoneBrick).setBlockName("stairsStoneBrickSmooth").setRequiresSelfNotify();
	public static final Block mycelium = new BlockMycelium(110).setHardness(0.6F).setStepSound(soundGrassFootstep).setBlockName("mycel");
	public static final Block waterlily = new BlockLilyPad(111, 76).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("waterlily");
	public static final Block netherBrick = new Block(112, 224, Material.rock).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("netherBrick");
	public static final Block netherFence = new BlockFence(113, 224, Material.rock).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("netherFence");
	public static final Block stairsNetherBrick = new BlockStairs(114, netherBrick).setBlockName("stairsNetherBrick").setRequiresSelfNotify();
	public static final Block netherStalk = new BlockNetherStalk(115).setBlockName("netherStalk").setRequiresSelfNotify();
	public static final Block enchantmentTable = new BlockEnchantmentTable(116).setHardness(5F).setResistance(2000F).setBlockName("enchantmentTable");
	public static final Block brewingStand = new BlockBrewingStand(117).setHardness(0.5F).setLightValue(0.125F).setBlockName("brewingStand").setRequiresSelfNotify();
	public static final Block cauldron = new BlockCauldron(118).setHardness(2.0F).setBlockName("cauldron").setRequiresSelfNotify();
	public static final Block endPortal = new BlockEndPortal(119, Material.portal).setHardness(-1F).setResistance(6000000F);
	public static final Block endPortalFrame = new BlockEndPortalFrame(120).setStepSound(soundGlassFootstep).setLightValue(0.125F).setHardness(-1F).setBlockName("endPortalFrame").setRequiresSelfNotify().setResistance(6000000F);
	public static final Block whiteStone = new Block(121, 175, Material.rock).setHardness(3F).setResistance(15F).setStepSound(soundStoneFootstep).setBlockName("whiteStone");
	public static final Block field_41050_bK = new BlockDragonEgg(122, 167).setHardness(3F).setResistance(15F).setStepSound(soundStoneFootstep).setLightValue(0.125F).setBlockName("dragonEgg");

	public static final Block netherFenceGate = new BlockFenceGate(210, 224, Material.rock).setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setBlockName("netherGate").setRequiresSelfNotify();
	public static final Block blockGreenFlowers = new BlockGreenFlowers(213).setHardness(0.0F).setLightOpacity(0).setStepSound(Block.soundGrassFootstep);
	public static final Block STAIRS_DIAMOND = new BlockStairs(214, blockDiamond).setBlockName("stairs.diamond").setRequiresSelfNotify();
	public static final Block STAIRS_GOLD = new BlockStairs(215, blockGold).setBlockName("stairs.gold").setRequiresSelfNotify();
	public static final Block STAIRS_IRON = new BlockStairs(216, blockSteel).setBlockName("stairs.iron").setRequiresSelfNotify();
	//public static final Block STAIRS_DIRT = new BlockStairs(217, dirt).setBlockName("stairs.dirt").setRequiresSelfNotify();
	//public static final Block STAIRS_GRASS = new BlockStairsGrass(218, grass).setBlockName("stairs.grass").setRequiresSelfNotify();
	
	public static final Block NY_CANDLE = new BlockCandle(400, nycandle).setHardness(0).setBlockName("nycandle").setLightValue(0.8f);
	public static final Block GARLAND_NYPINE = new BlockGarland(401, new int[] {garland_nypine_1, garland_nypine_2, garland_nypine_3, garland_nypine_4, garland_nypine_5, garland_nypine_6}).setBlockName("garland.nypine").setHardness(0.2F).setStepSound(Block.soundPowderFootstep).setRequiresSelfNotify();
	public static final Block LANTERN = new BlockLantern(402, Material.glass, new int[] {lantern_red_top, lantern_blue_top}, new int[] {lantern_red_side, lantern_blue_side}, new String[] {"nylantern.red", "nylantern.blue"}).setHardness(0.3F).setStepSound(soundGlassFootstep).setLightValue(0.9375F);
	
	public static final Block blockWhiteStone = new Block(1001, whiteStoneTexture, Material.rock).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setBlockName("whiteStoneGC");
	public static final Block blockWhiteStoneBrick = new Block(1002, whiteStoneBrickTexture, Material.rock).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setBlockName("whiteStoneBrick");
	public static final Block blockLimestone = new Block(1003, limestoneTexture, Material.rock).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundGravelFootstep).setBlockName("limestone");
	public static final Block blockWhiteStoneBrickCracked = new Block(1005, whiteStoneBrickCracked, Material.rock).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setBlockName("blockWhiteStoneBrickCracked");
	public static final Block blockWhiteStoneBrickMossy = new Block(1004, whiteStoneBrickMossy, Material.rock).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setBlockName("blockWhiteStoneBrickMossy");

	public static final Block blockStepWhiteStoneBrick = new BlockGreenStep(1010, whiteStoneBrickTexture, blockWhiteStoneBrick, false).setBlockName("whiteStoneBrickStep");
	public static final Block blockStepWhiteStone = new BlockGreenStep(1011, whiteStoneTexture, blockWhiteStone, false).setBlockName("whiteStoneStep");
	public static final Block blockStepLog = new BlockGreenStepLog(1012, Material.wood, Block.wood, false);
	public static final Block blockStepSnow = new BlockGreenStep(1013, Block.snow.blockIndexInTexture, Block.snow, false).setBlockName("snowStep");
	public static final Block blockStepNetherrack = new BlockGreenStep(1014, Block.netherrack.blockIndexInTexture, Block.netherrack, false).setBlockName("netherrackStep");
	public static final Block blockStepNetherBrick = new BlockGreenStep(1015, Block.netherBrick.blockIndexInTexture, Block.netherBrick, false).setBlockName("netherBrickStep");
	public static final Block blockStepDirt = new BlockGreenStep(1016, Block.dirt.blockIndexInTexture, Block.dirt, false).setBlockName("dirtStep");
	public static final Block blockStepGrass = new BlockGreenStepGrass(1017, Block.grass.blockIndexInTexture, Block.grass, false).setBlockName("grassStep");
	public static final Block blockStepGlass = new BlockGreenStepGlass(1018, Block.glass.blockIndexInTexture, glassStepTexture, Block.glass, false).setBlockName("glassStep");
	public static final Block blockStepObsidian = new BlockGreenStep(1019, Block.obsidian.blockIndexInTexture, Block.obsidian, false).setBlockName("obsidianStep");
	public static final Block blockStepLapis = new BlockGreenStep(1020, Block.blockLapis.blockIndexInTexture, Block.blockLapis, false).setBlockName("lapisStep");
	public static final Block blockStepMossyCobble = new BlockGreenStep(1021, Block.cobblestoneMossy.blockIndexInTexture, Block.cobblestoneMossy, false).setBlockName("mossyCobbleStep");
	public static final Block blockStepMossyBrick = new BlockGreenStep(1022, 100, Block.stoneBrick, false).setBlockName("mossyBrickStep");
	public static final Block blockStepCloth = new BlockStepWool(1023, Material.wool, false);
	public static final Block blockStepIron = new BlockGreenStepTextured(1024, Block.blockSteel.blockIndexInTexture, ironStepTexture, Block.blockSteel, false).setBlockName("ironStep");
	public static final Block blockStepGold = new BlockGreenStepTextured(1025, Block.blockGold.blockIndexInTexture, goldStepTexture, Block.blockGold, false).setBlockName("goldStep");
	public static final Block blockStepDiamond = new BlockGreenStepTextured(1026, Block.blockDiamond.blockIndexInTexture, diamondStepTexture, Block.blockDiamond, false).setBlockName("diamondStep");
	public static final Block blockStepStone = new BlockGreenStep(1027, Block.stone.blockIndexInTexture, Block.stone, false).setBlockName("stoneStep");
	public static final Block blockStepWhiteStoneBrickMossy = new BlockGreenStep(1029, whiteStoneBrickMossy, blockWhiteStone, false).setBlockName("whiteStoneBrickMossyStep");

	public static final Block blockStepWhiteStoneBrickUp = new BlockGreenStep(1030, whiteStoneBrickTexture, blockWhiteStoneBrick, true).setBlockName("whiteStoneBrickStep");
	public static final Block blockStepWhiteStoneUp = new BlockGreenStep(1031, whiteStoneTexture, blockWhiteStone, true).setBlockName("whiteStoneStep");
	public static final Block blockStepLogUp = new BlockGreenStepLog(1032, Material.wood, Block.wood, true);
	public static final Block blockStepSnowUp = new BlockGreenStep(1033, Block.snow.blockIndexInTexture, Block.netherrack, true).setBlockName("snowStep");
	public static final Block blockStepNetherrackUp = new BlockGreenStep(1034, Block.netherrack.blockIndexInTexture, Block.netherrack, true).setBlockName("netherrackStep");
	public static final Block blockStepNetherBrickUp = new BlockGreenStep(1035, Block.netherBrick.blockIndexInTexture, Block.netherBrick, true).setBlockName("netherBrickStep");
	public static final Block blockStepDirtUp = new BlockGreenStep(1036, Block.dirt.blockIndexInTexture, Block.dirt, true).setBlockName("dirtStep");
	public static final Block blockStepGrassUp = new BlockGreenStepGrass(1037, Block.grass.blockIndexInTexture, Block.grass, true).setBlockName("grassStep");
	public static final Block blockStepGlassUp = new BlockGreenStepGlass(1038, Block.glass.blockIndexInTexture, glassStepTexture, Block.glass, true).setBlockName("glassStep");
	public static final Block blockStepObsidianUp = new BlockGreenStep(1039, Block.obsidian.blockIndexInTexture, Block.obsidian, true).setBlockName("obsidianStep");
	public static final Block blockStepLapisUp = new BlockGreenStep(1040, Block.blockLapis.blockIndexInTexture, Block.blockLapis, true).setBlockName("lapisStep");
	public static final Block blockStepMossyCobbleUp = new BlockGreenStep(1041, Block.cobblestoneMossy.blockIndexInTexture, Block.cobblestoneMossy, true).setBlockName("mossyCobbleStep");
	public static final Block blockStepMossyBrickUp = new BlockGreenStep(1042, 100, Block.stoneBrick, true).setBlockName("mossyBrickStep");
	public static final Block blockStepClothUp = new BlockStepWool(1043, Material.wool, true).setBlockName("stepCloth");
	public static final Block blockStepIronUp = new BlockGreenStepTextured(1044, Block.blockSteel.blockIndexInTexture, ironStepTexture, Block.blockSteel, true).setBlockName("ironStep");
	public static final Block blockStepGoldUp = new BlockGreenStepTextured(1045, Block.blockGold.blockIndexInTexture, goldStepTexture, Block.blockGold, true).setBlockName("goldStep");
	public static final Block blockStepDiamondUp = new BlockGreenStepTextured(1046, Block.blockDiamond.blockIndexInTexture, diamondStepTexture, Block.blockDiamond, true).setBlockName("diamondStep");
	public static final Block blockStepStoneUp = new BlockGreenStep(1047, Block.stone.blockIndexInTexture, Block.stone, true).setBlockName("stoneStep");
	public static final Block blockStepUp = new BlockStepUp(1048).setBlockName("stoneSlab");
	public static final Block blockStepWhiteStoneBrickMossyUp = new BlockGreenStep(1049, whiteStoneBrickMossy, blockWhiteStone, true).setBlockName("whiteStoneBrickMossyStep");

	public static final Block blockStairsWhiteStoneBrick = new BlockStairs(1050, blockWhiteStoneBrick).setLightOpacity(0).setBlockName("whiteStoneBrickStairs");
	public static final Block blockStairsWhiteStone = new BlockStairs(1051, blockWhiteStone).setLightOpacity(0).setBlockName("whiteStoneStairs");
	public static final Block blockStairsMossyCobbleStone = new BlockStairs(1052, Block.cobblestoneMossy).setLightOpacity(0).setBlockName("mossyCobblestoneStairs");
	public static final Block blockStairsSnow = new BlockStairs(1053, Block.blockSnow).setLightOpacity(0).setBlockName("snowStairs");
	public static final Block blockStairsNetherRack = new BlockStairs(1054, Block.netherrack).setLightOpacity(0).setBlockName("netherRackStairs");
	public static final Block blockStairsLapisLazuli = new BlockStairs(1055, Block.blockLapis).setLightOpacity(0).setBlockName("lapisLazuliStairs");
	public static final Block blockStairsObsidian = new BlockStairs(1056, Block.obsidian).setLightOpacity(0).setBlockName("obsidianStairs");
	public static final Block blockStairsMossyStoneBrick = new BlockStairsTextured(1057, Block.stoneBrick, 100).setLightOpacity(0).setBlockName("mossyStoneBrickStairs");
	public static final Block blockStairsGlass = new BlockStairsGlass(1058, Block.glass).setLightOpacity(0).setBlockName("glassStairs");
	public static final Block blockStairsStone = new BlockStairs(1059, Block.stone).setLightOpacity(0).setBlockName("stoneStairs");
	public static final Block blockStairsEndStone = new BlockStairs(1060, Block.whiteStone).setLightOpacity(0).setBlockName("endStairs");
	public static final Block blockStairsMossyWhiteStoneBrick = new BlockStairs(1061, blockWhiteStoneBrickMossy).setLightOpacity(0).setBlockName("whiteStoneBrickMossyStairs");
	public static final Block blockStairsSandstoneStone = new BlockStairs(1062, Block.sandStone).setLightOpacity(0).setBlockName("sandstoneStairs");
	public static final Block blockStairsCloth0 = new BlockStairsCloth(1221, 0).setBlockName((new StringBuilder()).append("clothStairs").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(0)]).toString());
	public static final Block blockStairsCloth1 = new BlockStairsCloth(1222, 1).setBlockName((new StringBuilder()).append("clothStairs").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(1)]).toString());
	public static final Block blockStairsCloth2 = new BlockStairsCloth(1223, 2).setBlockName((new StringBuilder()).append("clothStairs").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(2)]).toString());
	public static final Block blockStairsCloth3 = new BlockStairsCloth(1224, 3).setBlockName((new StringBuilder()).append("clothStairs").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(3)]).toString());
	public static final Block blockStairsCloth4 = new BlockStairsCloth(1225, 4).setBlockName((new StringBuilder()).append("clothStairs").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(4)]).toString());
	public static final Block blockStairsCloth5 = new BlockStairsCloth(1226, 5).setBlockName((new StringBuilder()).append("clothStairs").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(5)]).toString());
	public static final Block blockStairsCloth6 = new BlockStairsCloth(1227, 6).setBlockName((new StringBuilder()).append("clothStairs").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(6)]).toString());
	public static final Block blockStairsCloth7 = new BlockStairsCloth(1228, 7).setBlockName((new StringBuilder()).append("clothStairs").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(7)]).toString());
	public static final Block blockStairsCloth8 = new BlockStairsCloth(1229, 8).setBlockName((new StringBuilder()).append("clothStairs").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(8)]).toString());
	public static final Block blockStairsCloth9 = new BlockStairsCloth(1230, 9).setBlockName((new StringBuilder()).append("clothStairs").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(9)]).toString());
	public static final Block blockStairsCloth10 = new BlockStairsCloth(1231, 10).setBlockName((new StringBuilder()).append("clothStairs").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(10)]).toString());
	public static final Block blockStairsCloth11 = new BlockStairsCloth(1232, 11).setBlockName((new StringBuilder()).append("clothStairs").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(11)]).toString());
	public static final Block blockStairsCloth12 = new BlockStairsCloth(1233, 12).setBlockName((new StringBuilder()).append("clothStairs").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(12)]).toString());
	public static final Block blockStairsCloth13 = new BlockStairsCloth(1234, 13).setBlockName((new StringBuilder()).append("clothStairs").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(13)]).toString());
	public static final Block blockStairsCloth14 = new BlockStairsCloth(1235, 14).setBlockName((new StringBuilder()).append("clothStairs").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(14)]).toString());
	public static final Block blockStairsCloth15 = new BlockStairsCloth(1236, 15).setBlockName((new StringBuilder()).append("clothStairs").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(15)]).toString());

	public static final Block blockHalfBlockWhiteStoneBrick = new BlockHalfBlock(1070, blockWhiteStoneBrick).setBlockName("whiteStoneBrickStep");
	public static final Block blockHalfBlockWhiteStoneBrickMossy = new BlockHalfBlock(1071, blockWhiteStoneBrickMossy).setBlockName("whiteStoneBrickMossyStep");
	public static final Block blockHalfBlockWhiteStone = new BlockHalfBlock(1072, blockWhiteStone).setBlockName("whiteStoneStep");
	public static final Block blockHalfBlockLogOak = new BlockHalfBlockLog(1073, Material.wood, 0).setBlockName("oakLogStep");
	public static final Block blockHalfBlockLogRedWood = new BlockHalfBlockLog(1074, Material.wood, 1).setBlockName("redwoodLogStep");
	public static final Block blockHalfBlockLogBirch = new BlockHalfBlockLog(1075, Material.wood, 2).setBlockName("birchLogStep");
	public static final Block blockHalfBlockLogSakura = new BlockHalfBlockLog(1076, Material.wood, 4).setBlockName("sakuraLogStep");
	public static final Block blockHalfBlockSnow = new BlockHalfBlock(1081, Block.snow).setBlockName("snowStep");
	public static final Block blockHalfBlockNetherrack = new BlockHalfBlock(1082, Block.netherrack).setBlockName("netherrackStep");
	public static final Block blockHalfBlockNetherBrick = new BlockHalfBlock(1083, Block.netherBrick).setBlockName("netherBrickStep");
	public static final Block blockHalfBlockDirt = new BlockHalfBlock(1084, Block.dirt).setBlockName("dirtStep");
	public static final Block blockHalfBlockGrass = new BlockHalfBlockGrass(1085, Block.grass).setBlockName("grassStep");
	public static final Block blockHalfBlockGlass = new BlockHalfBlockGlass(1086, glassStepTexture, glassHalfTexture, Block.glass).setBlockName("glassStep");
	public static final Block blockHalfBlockObsidian = new BlockHalfBlock(1087, Block.obsidian).setBlockName("obsidianStep");
	public static final Block blockHalfBlockLapis = new BlockHalfBlock(1088, Block.blockLapis).setBlockName("lapisStep");
	public static final Block blockHalfBlockMossyCobble = new BlockHalfBlock(1089, Block.cobblestoneMossy).setBlockName("mossyCobbleStep");
	public static final Block blockHalfBlockMossyBrick = new BlockHalfBlock(1090, blockStepMossyBrickUp).setBlockName("mossyBrickStep");;
	public static final Block blockHalfBlockCloth0 = new BlockHalfBlockCloth(1091, 0).setBlockName((new StringBuilder()).append("clothStep").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(0)]).toString());
	public static final Block blockHalfBlockCloth1 = new BlockHalfBlockCloth(1092, 1).setBlockName((new StringBuilder()).append("clothStep").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(1)]).toString());
	public static final Block blockHalfBlockCloth2 = new BlockHalfBlockCloth(1093, 2).setBlockName((new StringBuilder()).append("clothStep").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(2)]).toString());
	public static final Block blockHalfBlockCloth3 = new BlockHalfBlockCloth(1094, 3).setBlockName((new StringBuilder()).append("clothStep").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(3)]).toString());
	public static final Block blockHalfBlockCloth4 = new BlockHalfBlockCloth(1095, 4).setBlockName((new StringBuilder()).append("clothStep").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(4)]).toString());
	public static final Block blockHalfBlockCloth5 = new BlockHalfBlockCloth(1096, 5).setBlockName((new StringBuilder()).append("clothStep").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(5)]).toString());
	public static final Block blockHalfBlockCloth6 = new BlockHalfBlockCloth(1097, 6).setBlockName((new StringBuilder()).append("clothStep").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(6)]).toString());
	public static final Block blockHalfBlockCloth7 = new BlockHalfBlockCloth(1098, 7).setBlockName((new StringBuilder()).append("clothStep").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(7)]).toString());
	public static final Block blockHalfBlockCloth8 = new BlockHalfBlockCloth(1099, 8).setBlockName((new StringBuilder()).append("clothStep").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(8)]).toString());
	public static final Block blockHalfBlockCloth9 = new BlockHalfBlockCloth(1100, 9).setBlockName((new StringBuilder()).append("clothStep").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(9)]).toString());
	public static final Block blockHalfBlockCloth10 = new BlockHalfBlockCloth(1101, 10).setBlockName((new StringBuilder()).append("clothStep").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(10)]).toString());
	public static final Block blockHalfBlockCloth11 = new BlockHalfBlockCloth(1102, 11).setBlockName((new StringBuilder()).append("clothStep").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(11)]).toString());
	public static final Block blockHalfBlockCloth12 = new BlockHalfBlockCloth(1103, 12).setBlockName((new StringBuilder()).append("clothStep").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(12)]).toString());
	public static final Block blockHalfBlockCloth13 = new BlockHalfBlockCloth(1104, 13).setBlockName((new StringBuilder()).append("clothStep").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(13)]).toString());
	public static final Block blockHalfBlockCloth14 = new BlockHalfBlockCloth(1105, 14).setBlockName((new StringBuilder()).append("clothStep").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(14)]).toString());
	public static final Block blockHalfBlockCloth15 = new BlockHalfBlockCloth(1106, 15).setBlockName((new StringBuilder()).append("clothStep").append(".").append(dyeColorNames[BlockWool.getBlockFromDye(15)]).toString());
	public static final Block blockHalfBlockIron = new BlockHalfBlockTextured(1107, ironStepTexture, ironHalfTexture, Block.blockSteel).setBlockName("ironStep");
	public static final Block blockHalfBlockGold = new BlockHalfBlockTextured(1108, goldStepTexture, goldHalfTexture, Block.blockGold).setBlockName("goldStep");
	public static final Block blockHalfBlockDiamond = new BlockHalfBlockTextured(1109, diamondStepTexture, diamondHalfTexture, Block.blockDiamond).setBlockName("diamondStep");
	public static final Block blockHalfBlockStone = new BlockHalfBlock(1111, Block.stone).setBlockName("stoneStep");
	public static final Block blockHalfBlockSlabs = new BlockHalfBlockSlabs(1112, Block.stairSingle).setBlockName("stoneSlab.stone");
	public static final Block blockHalfBlockSlabsSandStone = new BlockHalfBlock(1113, Block.sandStone).setBlockName("stoneSlab.sand");
	public static final Block blockHalfBlockSlabsWood = new BlockHalfBlock(1114, Block.planks).setBlockName("stoneSlab.wood");
	public static final Block blockHalfBlockSlabsCobble = new BlockHalfBlock(1115, Block.cobblestone).setBlockName("stoneSlab.cobble");
	public static final Block blockHalfBlockSlabsBrick = new BlockHalfBlock(1116, Block.brick).setBlockName("stoneSlab.brick");
	public static final Block blockHalfBlockSlabsStoneBrick = new BlockHalfBlock(1117, Block.stoneBrick).setBlockName("stoneSlab.smoothStoneBrick");

	public static final Block blockOrangeColorstone = new Block(1300, orangecolorstone, Material.rock).setHardness(1.5F).setBlockName("colorStone.orange").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockMagentaColorstone = new Block(1301, magentacolorstone, Material.rock).setHardness(1.5F).setBlockName("colorStone.magenta").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockLightblueColorstone = new Block(1302, lightbluecolorstone, Material.rock).setHardness(1.5F).setBlockName("colorStone.lightblue").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockYellowColorstone = new Block(1303, yellowcolorstone, Material.rock).setHardness(1.5F).setBlockName("colorStone.yellow").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockLimeColorstone = new Block(1304, limecolorstone, Material.rock).setHardness(1.5F).setBlockName("colorStone.lime").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockPinkColorstone = new Block(1305, pinkcolorstone, Material.rock).setHardness(1.5F).setBlockName("colorStone.pink").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockGrayColorstone = new Block(1306, graycolorstone, Material.rock).setHardness(1.5F).setBlockName("colorStone.gray").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockSilverColorstone = new Block(1307, silvercolorstone, Material.rock).setHardness(1.5F).setBlockName("colorStone.silver").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockCyanColorstone = new Block(1308, cyancolorstone, Material.rock).setHardness(1.5F).setBlockName("colorStone.cyan").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockPurpleColorstone = new Block(1309, purplecolorstone, Material.rock).setHardness(1.5F).setBlockName("colorStone.purple").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockBlueColorstone = new Block(1310, bluecolorstone, Material.rock).setHardness(1.5F).setBlockName("colorStone.blue").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockBrownColorstone = new Block(1311, browncolorstone, Material.rock).setHardness(1.5F).setBlockName("colorStone.brown").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockGreenColorstone = new Block(1312, greencolorstone, Material.rock).setHardness(1.5F).setBlockName("colorStone.green").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockRedColorstone = new Block(1313, redcolorstone, Material.rock).setHardness(1.5F).setBlockName("colorStone.red").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockBlackColorstone = new Block(1314, blackcolorstone, Material.rock).setHardness(1.5F).setBlockName("colorStone.black").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);

	public static final Block blockOrangeColorbrick = new Block(1315, orangecolorbrick, Material.rock).setHardness(1.5F).setBlockName("colorBrick.orange").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockMagentaColorbrick = new Block(1316, magentacolorbrick, Material.rock).setHardness(1.5F).setBlockName("colorBrick.magenta").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockLightblueColorbrick = new Block(1317, lightbluecolorbrick, Material.rock).setHardness(1.5F).setBlockName("colorBrick.lightblue").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockYellowColorbrick = new Block(1318, yellowcolorbrick, Material.rock).setHardness(1.5F).setBlockName("colorBrick.yellow").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockLimeColorbrick = new Block(1319, limecolorbrick, Material.rock).setHardness(1.5F).setBlockName("colorBrick.lime").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockPinkColorbrick = new Block(1320, pinkcolorbrick, Material.rock).setHardness(1.5F).setBlockName("colorBrick.pink").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockGrayColorbrick = new Block(1321, graycolorbrick, Material.rock).setHardness(1.5F).setBlockName("colorBrick.gray").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockSilverColorbrick = new Block(1322, silvercolorbrick, Material.rock).setHardness(1.5F).setBlockName("colorBrick.silver").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockCyanColorbrick = new Block(1323, cyancolorbrick, Material.rock).setHardness(1.5F).setBlockName("colorBrick.cyan").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockPurpleColorbrick = new Block(1324, purplecolorbrick, Material.rock).setHardness(1.5F).setBlockName("colorBrick.purple").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockBlueColorbrick = new Block(1325, bluecolorbrick, Material.rock).setHardness(1.5F).setBlockName("colorBrick.blue").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockBrownColorbrick = new Block(1326, browncolorbrick, Material.rock).setHardness(1.5F).setBlockName("colorBrick.brown").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockGreenColorbrick = new Block(1327, greencolorbrick, Material.rock).setHardness(1.5F).setBlockName("colorBrick.green").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockRedColorbrick = new Block(1328, redcolorbrick, Material.rock).setHardness(1.5F).setBlockName("colorBrick.red").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockBlackColorbrick = new Block(1329, blackcolorbrick, Material.rock).setHardness(1.5F).setBlockName("colorBrick.black").setResistance(10.0F).setStepSound(Block.soundStoneFootstep);

	public static final Block blockOrangeColorstoneStep = new BlockGreenStep(1330, orangecolorstone, blockWhiteStone, false).setBlockName("colorStoneStep.orange");
	public static final Block blockMagentaColorstoneStep = new BlockGreenStep(1331, magentacolorstone, blockWhiteStone, false).setBlockName("colorStoneStep.magenta");
	public static final Block blockLightblueColorstoneStep = new BlockGreenStep(1332, lightbluecolorstone, blockWhiteStone, false).setBlockName("colorStoneStep.lightblue");
	public static final Block blockYellowColorstoneStep = new BlockGreenStep(1333, yellowcolorstone, blockWhiteStone, false).setBlockName("colorStoneStep.yellow");
	public static final Block blockLimeColorstoneStep = new BlockGreenStep(1334, limecolorstone, blockWhiteStone, false).setBlockName("colorStoneStep.lime");
	public static final Block blockPinkColorstoneStep = new BlockGreenStep(1335, pinkcolorstone, blockWhiteStone, false).setBlockName("colorStoneStep.pink");
	public static final Block blockGrayColorstoneStep = new BlockGreenStep(1336, graycolorstone, blockWhiteStone, false).setBlockName("colorStoneStep.gray");
	public static final Block blockSilverColorstoneStep = new BlockGreenStep(1337, silvercolorstone, blockWhiteStone, false).setBlockName("colorStoneStep.silver");
	public static final Block blockCyanColorstoneStep = new BlockGreenStep(1338, cyancolorstone, blockWhiteStone, false).setBlockName("colorStoneStep.cyan");
	public static final Block blockPurpleColorstoneStep = new BlockGreenStep(1339, purplecolorstone, blockWhiteStone, false).setBlockName("colorStoneStep.purple");
	public static final Block blockBlueColorstoneStep = new BlockGreenStep(1340, bluecolorstone, blockWhiteStone, false).setBlockName("colorStoneStep.blue");
	public static final Block blockBrownColorstoneStep = new BlockGreenStep(1341, browncolorstone, blockWhiteStone, false).setBlockName("colorStoneStep.brown");
	public static final Block blockGreenColorstoneStep = new BlockGreenStep(1342, greencolorstone, blockWhiteStone, false).setBlockName("colorStoneStep.green");
	public static final Block blockRedColorstoneStep = new BlockGreenStep(1343, redcolorstone, blockWhiteStone, false).setBlockName("colorStoneStep.red");
	public static final Block blockBlackColorstoneStep = new BlockGreenStep(1344, blackcolorstone, blockWhiteStone, false).setBlockName("colorStoneStep.black");

	public static final Block blockOrangeColorbrickStep = new BlockGreenStep(1345, orangecolorbrick, blockWhiteStone, false).setBlockName("colorBrickStep.orange");
	public static final Block blockMagentaColorbrickStep = new BlockGreenStep(1346, magentacolorbrick, blockWhiteStone, false).setBlockName("colorBrickStep.magenta");
	public static final Block blockLightblueColorbrickStep = new BlockGreenStep(1347, lightbluecolorbrick, blockWhiteStone, false).setBlockName("colorBrickStep.lightblue");
	public static final Block blockYellowColorbrickStep = new BlockGreenStep(1348, yellowcolorbrick, blockWhiteStone, false).setBlockName("colorBrickStep.yellow");
	public static final Block blockLimeColorbrickStep = new BlockGreenStep(1349, limecolorbrick, blockWhiteStone, false).setBlockName("colorBrickStep.lime");
	public static final Block blockPinkColorbrickStep = new BlockGreenStep(1350, pinkcolorbrick, blockWhiteStone, false).setBlockName("colorBrickStep.pink");
	public static final Block blockGrayColorbrickStep = new BlockGreenStep(1351, graycolorbrick, blockWhiteStone, false).setBlockName("colorBrickStep.gray");
	public static final Block blockSilverColorbrickStep = new BlockGreenStep(1352, silvercolorbrick, blockWhiteStone, false).setBlockName("colorBrickStep.silver");
	public static final Block blockCyanColorbrickStep = new BlockGreenStep(1353, cyancolorbrick, blockWhiteStone, false).setBlockName("colorBrickStep.cyan");
	public static final Block blockPurpleColorbrickStep = new BlockGreenStep(1354, purplecolorbrick, blockWhiteStone, false).setBlockName("colorBrickStep.purple");
	public static final Block blockBlueColorbrickStep = new BlockGreenStep(1355, bluecolorbrick, blockWhiteStone, false).setBlockName("colorBrickStep.blue");
	public static final Block blockBrownColorbrickStep = new BlockGreenStep(1356, browncolorbrick, blockWhiteStone, false).setBlockName("colorBrickStep.brown");
	public static final Block blockGreenColorbrickStep = new BlockGreenStep(1357, greencolorbrick, blockWhiteStone, false).setBlockName("colorBrickStep.green");
	public static final Block blockRedColorbrickStep = new BlockGreenStep(1358, redcolorbrick, blockWhiteStone, false).setBlockName("colorBrickStep.red");
	public static final Block blockBlackColorbrickStep = new BlockGreenStep(1359, blackcolorbrick, blockWhiteStone, false).setBlockName("colorBrickStep.black");

	public static final Block blockOrangeColorstoneStepUp = new BlockGreenStep(1360, orangecolorstone, blockWhiteStone, true).setBlockName("colorStoneStep.orange");
	public static final Block blockMagentaColorstoneStepUp = new BlockGreenStep(1361, magentacolorstone, blockWhiteStone, true).setBlockName("colorStoneStep.magenta");
	public static final Block blockLightblueColorstoneStepUp = new BlockGreenStep(1362, lightbluecolorstone, blockWhiteStone, true).setBlockName("colorStoneStep.lightblue");
	public static final Block blockYellowColorstoneStepUp = new BlockGreenStep(1363, yellowcolorstone, blockWhiteStone, true).setBlockName("colorStoneStep.yellow");
	public static final Block blockLimeColorstoneStepUp = new BlockGreenStep(1364, limecolorstone, blockWhiteStone, true).setBlockName("colorStoneStep.lime");
	public static final Block blockPinkColorstoneStepUp = new BlockGreenStep(1365, pinkcolorstone, blockWhiteStone, true).setBlockName("colorStoneStep.pink");
	public static final Block blockGrayColorstoneStepUp = new BlockGreenStep(1366, graycolorstone, blockWhiteStone, true).setBlockName("colorStoneStep.gray");
	public static final Block blockSilverColorstoneStepUp = new BlockGreenStep(1367, silvercolorstone, blockWhiteStone, true).setBlockName("colorStoneStep.silver");
	public static final Block blockCyanColorstoneStepUp = new BlockGreenStep(1368, cyancolorstone, blockWhiteStone, true).setBlockName("colorStoneStep.cyan");
	public static final Block blockPurpleColorstoneStepUp = new BlockGreenStep(1369, purplecolorstone, blockWhiteStone, true).setBlockName("colorStoneStep.purple");
	public static final Block blockBlueColorstoneStepUp = new BlockGreenStep(1370, bluecolorstone, blockWhiteStone, true).setBlockName("colorStoneStep.blue");
	public static final Block blockBrownColorstoneStepUp = new BlockGreenStep(1371, browncolorstone, blockWhiteStone, true).setBlockName("colorStoneStep.brown");
	public static final Block blockGreenColorstoneStepUp = new BlockGreenStep(1372, greencolorstone, blockWhiteStone, true).setBlockName("colorStoneStep.green");
	public static final Block blockRedColorstoneStepUp = new BlockGreenStep(1373, redcolorstone, blockWhiteStone, true).setBlockName("colorStoneStep.red");
	public static final Block blockBlackColorstoneStepUp = new BlockGreenStep(1374, blackcolorstone, blockWhiteStone, true).setBlockName("colorStoneStep.black");

	public static final Block blockOrangeColorbrickStepUp = new BlockGreenStep(1375, orangecolorbrick, blockWhiteStone, true).setBlockName("colorBrickStep.orange");
	public static final Block blockMagentaColorbrickStepUp = new BlockGreenStep(1376, magentacolorbrick, blockWhiteStone, true).setBlockName("colorBrickStep.magenta");
	public static final Block blockLightblueColorbrickStepUp = new BlockGreenStep(1377, lightbluecolorbrick, blockWhiteStone, true).setBlockName("colorBrickStep.lightblue");
	public static final Block blockYellowColorbrickStepUp = new BlockGreenStep(1378, yellowcolorbrick, blockWhiteStone, true).setBlockName("colorBrickStep.yellow");
	public static final Block blockLimeColorbrickStepUp = new BlockGreenStep(1379, limecolorbrick, blockWhiteStone, true).setBlockName("colorBrickStep.lime");
	public static final Block blockPinkColorbrickStepUp = new BlockGreenStep(1380, pinkcolorbrick, blockWhiteStone, true).setBlockName("colorBrickStep.pink");
	public static final Block blockGrayColorbrickStepUp = new BlockGreenStep(1381, graycolorbrick, blockWhiteStone, true).setBlockName("colorBrickStep.gray");
	public static final Block blockSilverColorbrickStepUp = new BlockGreenStep(1382, silvercolorbrick, blockWhiteStone, true).setBlockName("colorBrickStep.silver");
	public static final Block blockCyanColorbrickStepUp = new BlockGreenStep(1383, cyancolorbrick, blockWhiteStone, true).setBlockName("colorBrickStep.cyan");
	public static final Block blockPurpleColorbrickStepUp = new BlockGreenStep(1384, purplecolorbrick, blockWhiteStone, true).setBlockName("colorBrickStep.purple");
	public static final Block blockBlueColorbrickStepUp = new BlockGreenStep(1385, bluecolorbrick, blockWhiteStone, true).setBlockName("colorBrickStep.blue");
	public static final Block blockBrownColorbrickStepUp = new BlockGreenStep(1386, browncolorbrick, blockWhiteStone, true).setBlockName("colorBrickStep.brown");
	public static final Block blockGreenColorbrickStepUp = new BlockGreenStep(1387, greencolorbrick, blockWhiteStone, true).setBlockName("colorBrickStep.green");
	public static final Block blockRedColorbrickStepUp = new BlockGreenStep(1388, redcolorbrick, blockWhiteStone, true).setBlockName("colorBrickStep.red");
	public static final Block blockBlackColorbrickStepUp = new BlockGreenStep(1389, blackcolorbrick, blockWhiteStone, true).setBlockName("colorBrickStep.black");

	public static final Block blockOrangeColorstoneHalf = new BlockHalfBlock(1390, orangecolorstone, blockWhiteStone).setBlockName("colorStoneStep.orange");
	public static final Block blockMagentaColorstoneHalf = new BlockHalfBlock(1391, magentacolorstone, blockWhiteStone).setBlockName("colorStoneStep.magenta");
	public static final Block blockLightblueColorstoneHalf = new BlockHalfBlock(1392, lightbluecolorstone, blockWhiteStone).setBlockName("colorStoneStep.lightblue");
	public static final Block blockYellowColorstoneHalf = new BlockHalfBlock(1393, yellowcolorstone, blockWhiteStone).setBlockName("colorStoneStep.yellow");
	public static final Block blockLimeColorstoneHalf = new BlockHalfBlock(1394, limecolorstone, blockWhiteStone).setBlockName("colorStoneStep.lime");
	public static final Block blockPinkColorstoneHalf = new BlockHalfBlock(1395, pinkcolorstone, blockWhiteStone).setBlockName("colorStoneStep.pink");
	public static final Block blockGrayColorstoneHalf = new BlockHalfBlock(1396, graycolorstone, blockWhiteStone).setBlockName("colorStoneStep.gray");
	public static final Block blockSilverColorstoneHalf = new BlockHalfBlock(1397, silvercolorstone, blockWhiteStone).setBlockName("colorStoneStep.silver");
	public static final Block blockCyanColorstoneHalf = new BlockHalfBlock(1398, cyancolorstone, blockWhiteStone).setBlockName("colorStoneStep.cyan");
	public static final Block blockPurpleColorstoneHalf = new BlockHalfBlock(1399, purplecolorstone, blockWhiteStone).setBlockName("colorStoneStep.purple");
	public static final Block blockBlueColorstoneHalf = new BlockHalfBlock(1401, bluecolorstone, blockWhiteStone).setBlockName("colorStoneStep.blue");
	public static final Block blockBrownColorstoneHalf = new BlockHalfBlock(1403, browncolorstone, blockWhiteStone).setBlockName("colorStoneStep.brown");
	public static final Block blockGreenColorstoneHalf = new BlockHalfBlock(1404, greencolorstone, blockWhiteStone).setBlockName("colorStoneStep.green");
	public static final Block blockRedColorstoneHalf = new BlockHalfBlock(1405, redcolorstone, blockWhiteStone).setBlockName("colorStoneStep.red");
	public static final Block blockBlackColorstoneHalf = new BlockHalfBlock(1406, blackcolorstone, blockWhiteStone).setBlockName("colorStoneStep.black");

	public static final Block blockOrangeColorbrickHalf = new BlockHalfBlock(1407, orangecolorbrick, blockWhiteStone).setBlockName("colorBrickStep.orange");
	public static final Block blockMagentaColorbrickHalf = new BlockHalfBlock(1408, magentacolorbrick, blockWhiteStone).setBlockName("colorBrickStep.magenta");
	public static final Block blockLightblueColorbrickHalf = new BlockHalfBlock(1409, lightbluecolorbrick, blockWhiteStone).setBlockName("colorBrickStep.lightblue");
	public static final Block blockYellowColorbrickHalf = new BlockHalfBlock(1410, yellowcolorbrick, blockWhiteStone).setBlockName("colorBrickStep.yellow");
	public static final Block blockLimeColorbrickHalf = new BlockHalfBlock(1411, limecolorbrick, blockWhiteStone).setBlockName("colorBrickStep.lime");
	public static final Block blockPinkColorbrickHalf = new BlockHalfBlock(1412, pinkcolorbrick, blockWhiteStone).setBlockName("colorBrickStep.pink");
	public static final Block blockGrayColorbrickHalf = new BlockHalfBlock(1413, graycolorbrick, blockWhiteStone).setBlockName("colorBrickStep.gray");
	public static final Block blockSilverColorbrickHalf = new BlockHalfBlock(1414, silvercolorbrick, blockWhiteStone).setBlockName("colorBrickStep.silver");
	public static final Block blockCyanColorbrickHalf = new BlockHalfBlock(1415, cyancolorbrick, blockWhiteStone).setBlockName("colorBrickStep.cyan");
	public static final Block blockPurpleColorbrickHalf = new BlockHalfBlock(1416, purplecolorbrick, blockWhiteStone).setBlockName("colorBrickStep.purple");
	public static final Block blockBlueColorbrickHalf = new BlockHalfBlock(1417, bluecolorbrick, blockWhiteStone).setBlockName("colorBrickStep.blue");
	public static final Block blockBrownColorbrickHalf = new BlockHalfBlock(1418, browncolorbrick, blockWhiteStone).setBlockName("colorBrickStep.brown");
	public static final Block blockGreenColorbrickHalf = new BlockHalfBlock(1419, greencolorbrick, blockWhiteStone).setBlockName("colorBrickStep.green");
	public static final Block blockRedColorbrickHalf = new BlockHalfBlock(1420, redcolorbrick, blockWhiteStone).setBlockName("colorBrickStep.red");
	public static final Block blockBlackColorbrickHalf = new BlockHalfBlock(1421, blackcolorbrick, blockWhiteStone).setBlockName("colorBrickStep.black");

	public static final Block blockOrangeColorstoneStairs = new BlockStairs(1422, blockOrangeColorstone).setBlockName("colorStoneStairs.orange");
	public static final Block blockMagentaColorstoneStairs = new BlockStairs(1423, blockMagentaColorstone).setBlockName("colorStoneStairs.magenta");
	public static final Block blockLightblueColorstoneStairs = new BlockStairs(1424, blockLightblueColorstone).setBlockName("colorStoneStairs.lightblue");
	public static final Block blockYellowColorstoneStairs = new BlockStairs(1425, blockYellowColorstone).setBlockName("colorStoneStairs.yellow");
	public static final Block blockLimeColorstoneStairs = new BlockStairs(1426, blockLimeColorstone).setBlockName("colorStoneStairs.lime");
	public static final Block blockPinkColorstoneStairs = new BlockStairs(1427, blockPinkColorstone).setBlockName("colorStoneStairs.pink");
	public static final Block blockGrayColorstoneStairs = new BlockStairs(1428, blockGrayColorstone).setBlockName("colorStoneStairs.gray");
	public static final Block blockSilverColorstoneStairs = new BlockStairs(1429, blockSilverColorstone).setBlockName("colorStoneStairs.silver");
	public static final Block blockCyanColorstoneStairs = new BlockStairs(1430, blockCyanColorstone).setBlockName("colorStoneStairs.cyan");
	public static final Block blockPurpleColorstoneStairs = new BlockStairs(1431, blockPurpleColorstone).setBlockName("colorStoneStairs.purple");
	public static final Block blockBlueColorstoneStairs = new BlockStairs(1432, blockBlueColorstone).setBlockName("colorStoneStairs.blue");
	public static final Block blockBrownColorstoneStairs = new BlockStairs(1433, blockBrownColorstone).setBlockName("colorStoneStairs.brown");
	public static final Block blockGreenColorstoneStairs = new BlockStairs(1434, blockGreenColorstone).setBlockName("colorStoneStairs.green");
	public static final Block blockRedColorstoneStairs = new BlockStairs(1435, blockRedColorstone).setBlockName("colorStoneStairs.red");
	public static final Block blockBlackColorstoneStairs = new BlockStairs(1436, blockBlackColorstone).setBlockName("colorStoneStairs.black");

	public static final Block blockOrangeColorbrickStairs = new BlockStairs(1437, blockOrangeColorbrick).setBlockName("colorBrickStairs.ornage");
	public static final Block blockMagentaColorbrickStairs = new BlockStairs(1438, blockMagentaColorbrick).setBlockName("colorBrickStairs.magenta");
	public static final Block blockLightblueColorbrickStairs = new BlockStairs(1439, blockLightblueColorbrick).setBlockName("colorBrickStairs.lightblue");
	public static final Block blockYellowColorbrickStairs = new BlockStairs(1440, blockYellowColorbrick).setBlockName("colorBrickStairs.yellow");
	public static final Block blockLimeColorbrickStairs = new BlockStairs(1441, blockLimeColorbrick).setBlockName("colorBrickStairs.lime");
	public static final Block blockPinkColorbrickStairs = new BlockStairs(1442, blockPinkColorbrick).setBlockName("colorBrickStairs.pink");
	public static final Block blockGrayColorbrickStairs = new BlockStairs(1443, blockGrayColorbrick).setBlockName("colorBrickStairs.gray");
	public static final Block blockSilverColorbrickStairs = new BlockStairs(1444, blockSilverColorbrick).setBlockName("colorBrickStairs.silver");
	public static final Block blockCyanColorbrickStairs = new BlockStairs(1445, blockCyanColorbrick).setBlockName("colorBrickStairs.cyan");
	public static final Block blockPurpleColorbrickStairs = new BlockStairs(1446, blockPurpleColorbrick).setBlockName("colorBrickStairs.purple");
	public static final Block blockBlueColorbrickStairs = new BlockStairs(1447, blockBlueColorbrick).setBlockName("colorBrickStairs.blue");
	public static final Block blockBrownColorbrickStairs = new BlockStairs(1448, blockBrownColorbrick).setBlockName("colorBrickStairs.brown");
	public static final Block blockGreenColorbrickStairs = new BlockStairs(1449, blockGreenColorbrick).setBlockName("colorBrickStairs.green");
	public static final Block blockRedColorbrickStairs = new BlockStairs(1450, blockRedColorbrick).setBlockName("colorBrickStairs.red");
	public static final Block blockBlackColorbrickStairs = new BlockStairs(1451, blockBlackColorbrick).setBlockName("colorBrickStairs.black");

	public static final Block blockRedwoodPlanksStep = new BlockGreenStep(1452, redwoodplank, Block.planks, false).setBlockName("redwoodPlanksStep");
	public static final Block blockBrichPlanksStep = new BlockGreenStep(1453, birchplank, Block.planks, false).setBlockName("birchPlanksStep");
	public static final Block blockRedwoodPlanksStepUp = new BlockGreenStep(1454, redwoodplank, Block.planks, true).setBlockName("redwoodPlanksStep");
	public static final Block blockBrichPlanksStepUp = new BlockGreenStep(1455, birchplank, Block.planks, true).setBlockName("birchPlanksStep");
	public static final Block blockRedwoodPlanksHalf = new BlockHalfBlock(1456, redwoodplank, Block.planks).setBlockName("redwoodPlanksStep");
	public static final Block blockBrichPlanksHalf = new BlockHalfBlock(1457, birchplank, Block.planks).setBlockName("birchPlanksStep");
	public static final Block blockSakuraPlanksStep = new BlockGreenStep(1460, sakuraplank, Block.planks, false).setBlockName("sakuraPlanksStep");
	public static final Block blockSakuraPlanksStepUp = new BlockGreenStep(1461, sakuraplank, Block.planks, true).setBlockName("sakuraPlanksStep");
	public static final Block blockSakuraPlanksHalf = new BlockHalfBlock(1462, sakuraplank, Block.planks).setBlockName("sakuraPlanksStep");

	public static final Block blockSakuraFlowers = new BlockSakuraFlowers(1210, sakuraFlowers).setBlockName("sakuraFlowers").setStepSound(Block.soundGrassFootstep).setHardness(Block.leaves.blockHardness);
	public static final Block blockSakuraFlowersDense = new BlockSakuraFlowers(1211, sakuraFlowersDense).setBlockName("sakuraFlowersDense").setStepSound(Block.soundGrassFootstep).setHardness(Block.leaves.blockHardness);
	public static final Block blockSakuraLeaves = new BlockSakuraLeaves(1212, 52).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundGrassFootstep).setRequiresSelfNotify().setBlockName("sakuraLeaves").setHardness(Block.leaves.blockHardness);
	public static final Block blockSakuraLog = new BlockSakuraLog(1213).setBlockName("sakuraLog").setHardness(Block.wood.blockHardness);
	public static final Block blockSakuraSapling = new BlockSapling(1214, sakuraSapling).setStepSound(Block.soundGrassFootstep).setBlockName("sakuraSapling");

	public static final Block blockGift = new BlockGift(1110, Block.wool, giftfirstup, giftfirstdown, giftfirstside).setBlockName("blockGift");
	public static final Block blockGiftNY = new BlockGift(1120, Block.wool, giftnyup, giftnydown, giftnyside).setBlockName("blockGiftNY");
	public static final Block blockGift1402 = new BlockGift1402(1402, Block.wool).setBlockName("blockGift1402");
	public static final Block blockBadnick = new BlockBadnick(1201, Block.obsidian).setBlockName("badnick");
	public static final Block blockBlueTorch = new BlockBlueTorch(1202, bluetorchtexture).setHardness(0.0F).setLightValue(1.0F).setBlockName("bluetorch").setRequiresSelfNotify();
	public static final Block blockWhiteFence = new BlockWhiteFence(1203, whiteFenceTexture, Material.rock).setHardness(blockWhiteStone.blockHardness).setResistance(blockWhiteStone.blockResistance).setStepSound(blockWhiteStone.stepSound).setBlockName("whiteFence");
	public static final Block blockWhiteButton = new BlockButton(1204, whiteFenceTexture).setHardness(0.5F).setStepSound(Block.soundStoneFootstep).setBlockName("whiteButton").setRequiresSelfNotify();;
	public static final Block blockWhitePressurePlate = new BlockPressurePlate(1205, whiteStoneTexture, EnumMobType.mobs, Material.rock).setHardness(0.5F).setStepSound(Block.soundStoneFootstep).setBlockName("whitePressurePlate").setRequiresSelfNotify();
	//public static final Block blockRainbow = new BlockReed(1202, rainbow).setBlockName("rainbow"); 
	public static final Block blockWhiteSign = new BlockWhiteSign(1206, net.minecraft.src.TileEntitySign.class, true).setHardness(1.0F).setStepSound(Block.soundWoodFootstep).setBlockName("whitesign").disableStats().setRequiresSelfNotify();
	public static final Block blockWhiteSignWall = new BlockWhiteSign(1207, net.minecraft.src.TileEntitySign.class, false).setHardness(1.0F).setStepSound(Block.soundWoodFootstep).setBlockName("whitesign").disableStats().setRequiresSelfNotify();
	public static final Block blockRedwoodPlanks = new Block(1208, redwoodplank, Material.wood).setBlockName("redwoodPlanks").setHardness(Block.planks.blockHardness);
	public static final Block blockBirchPlanks = new Block(1209, birchplank, Material.wood).setBlockName("birchPlanks").setHardness(2.0F);
	public static final Block blockRedwoodPlanksStairs = new BlockStairs(1458, blockRedwoodPlanks).setBlockName("redwoodPlanksStairs");
	public static final Block blockBrichPlanksStairs = new BlockStairs(1459, blockBirchPlanks).setBlockName("birchPlanksStairs");
	public static final Block blockPalazzoDucalePattern1 = new Block(1215, PalazzoDucalePattern1, Material.rock).setHardness(1.5F).setBlockName("palazzoPattern");
	public static final Block blockPalazzoDucalePattern2 = new Block(1216, PalazzoDucalePattern2, Material.rock).setHardness(1.5F).setBlockName("palazzoPattern");
	public static final Block blockPalazzoDucalePattern1Half = new BlockHalfBlock(1217, PalazzoDucalePattern1, Block.stoneBrick).setHardness(1.0F).setBlockName("palazzoPattern");
	public static final Block blockPalazzoDucalePattern2Half = new BlockHalfBlock(1218, PalazzoDucalePattern2, Block.stoneBrick).setHardness(1.0F).setBlockName("palazzoPattern");
	public static final Block blockAppleTreeLeaves = new BlockAppleTreeLeaves(1219, BlockAppleTreeLeaves.leaves).setHardness(Block.leaves.blockHardness).setStepSound(Block.soundGrassFootstep).setBlockName("appleTreeLeaves");
	public static final Block blockAppleTreeSapling = new BlockSapling(1220, appletreesapling).setStepSound(Block.soundGrassFootstep).setBlockName("appleTreeSapling");
	public static final Block blockAppleTreeLog = new BlockLog(1237).setHardness(2.0F).setStepSound(Block.soundWoodFootstep).setBlockName("appleTreeLog").setRequiresSelfNotify();
	public static final Block blockSakuraPlanks = new Block(1238, sakuraplank, Material.wood).setStepSound(Block.soundWoodFootstep).setBlockName("sakuraPlanks").setHardness(2.0F);
	public static final Block blockSakuraPlanksStairs = new BlockStairs(1463, blockSakuraPlanks).setStepSound(Block.soundWoodFootstep).setBlockName("sakuraPlanksStairs");
	public static final Block blockAppleTreeLeavesPlayer = new BlockAppleTreeLeaves(1239, BlockAppleTreeLeaves.leaves).setHardness(Block.leaves.blockHardness).setStepSound(Block.soundGrassFootstep).setBlockName("appleTreeLeaves");
	public static final Block blockGoldFence = new BlockPane(1240, goldFenceTexture, pane_gold, pane_gold, Material.iron, true).setHardness(5F).setResistance(10F).setStepSound(Block.soundMetalFootstep).setBlockName("goldFence");
	public static final Block blockBriarShrub = new BlockBriar(1241, true).setHardness(0.2F).setStepSound(Block.soundGrassFootstep).setBlockName("briar");
	public static final Block blockCoalBlock = new BlockCoal(1242).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("coalBlock");
	public static final Block blockFacelessPumpkin = new BlockFacelessPumpkin(1243).setHardness(1.0F).setStepSound(Block.soundWoodFootstep).setBlockName("facelessPumpkin");
	public static final Block blockWhiteFenceGate = new BlockFenceGate(1244, whiteFenceTexture, Material.rock).setHardness(blockWhiteStone.blockHardness).setResistance(blockWhiteStone.blockResistance).setStepSound(blockWhiteStone.stepSound).setBlockName("whiteFenceGate").setRequiresSelfNotify();
	public static final Block blockBriarShrubBerries = new BlockBriar(1245, false).setHardness(0.2F).setStepSound(Block.soundGrassFootstep).setBlockName("briar").setBlockUnbreakable();
	public static final Block blockJungleLogHalf = new BlockHalfBlockLog(1246, Material.wood, 3).setBlockName("jungleLogStep");
	public static final Block blockLogBao = new BlockGreenLog(1247, baologtop, baologside).setHardness(2.0F).setStepSound(Block.soundWoodFootstep).setBlockName("baoLog").setRequiresSelfNotify();
	public static final Block blockLogPalm = new BlockGreenLog(1248, palmlogtop, palmlogside).setHardness(2.0F).setStepSound(Block.soundWoodFootstep).setBlockName("palmLog").setRequiresSelfNotify();
	public static final Block blockBaoLogHalf = new BlockHalfBlockLog(1249, Material.wood, 5).setBlockName("baoLogStep");
	public static final Block blockPalmLogHalf = new BlockHalfBlockLog(1250, Material.wood, 6).setBlockName("palmLogStep");
	public static final Block blockLeavesBao = new BlockLeaves(1251, baoleaves).setHardness(Block.leaves.blockHardness).setStepSound(Block.soundGrassFootstep).setBlockName("baoLeaves");
	public static final Block blockLeavesPalm = new BlockPalmLeaves(1252, palmleavestop, palmleavesside, false).setHardness(Block.leaves.blockHardness).setStepSound(Block.soundGrassFootstep).setBlockName("palmLeaves");
	public static final Block blockSaplingBao = new BlockSapling(1253, baosapling).setStepSound(Block.soundGrassFootstep).setBlockName("baoSapling");
	public static final Block blockSaplingPalm = new BlockSapling(1254, palmsapling).setStepSound(Block.soundGrassFootstep).setBlockName("palmSapling");
	public static final Block blockBananaLog = new BlockBananaLog(1255).setHardness(0.5F).setStepSound(Block.soundGrassFootstep).setBlockName("bananaLog");
	public static final Block blockBananaLogHalf = new BlockHalfBlockBananaLog(1256, Material.wood).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("bananaLogStep");
	public static final Block blockBananaLogStep = new BlockGreenStepBananaLog(1257, BlockBananaLog.side, blockBananaLog, false).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("bananaLogStep");
	public static final Block blockBananaLogStepUp = new BlockGreenStepBananaLog(1258, BlockBananaLog.side, blockBananaLog, true).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("bananaLogStep");
	public static final Block blockBananaLeaves = new BlockPalmLeaves(1259, bananaleavestop, bananaleavesside, false).setHardness(Block.leaves.blockHardness).setStepSound(Block.soundGrassFootstep).setBlockName("bananaLeaves");
	public static final Block blockBananaSapling = new BlockSapling(1260, bananasapling).setStepSound(Block.soundGrassFootstep).setBlockName("bananaSapling");
	public static final Block blockBananaCake = new BlockBananaCake(1261).setHardness(0.5F).setStepSound(Block.soundClothFootstep).setBlockName("bananaCake");
	public static final Block blockChocoCake = new BlockChocoCake(1262).setHardness(0.5F).setStepSound(Block.soundClothFootstep).setBlockName("bananaCake");
	public static final Block blockJunglePlanksStep = new BlockGreenStep(1263, jungleplanks, Block.planks, false).setBlockName("junglePlanksStep");
	public static final Block blockBaoPlanksStep = new BlockGreenStep(1264, baoplanks, Block.planks, false).setBlockName("baoPlanksStep");
	public static final Block blockPalmPlanksStep = new BlockGreenStep(1265, palmplanks, Block.planks, false).setBlockName("palmPlanksStep");
	public static final Block blockJunglePlanksStepUp = new BlockGreenStep(1266, jungleplanks, Block.planks, true).setBlockName("junglePlanksStep");
	public static final Block blockBaoPlanksStepUp = new BlockGreenStep(1267, baoplanks, Block.planks, true).setBlockName("baoPlanksStep");
	public static final Block blockPalmPlanksStepUp = new BlockGreenStep(1268, palmplanks, Block.planks, true).setBlockName("palmPlanksStep");
	public static final Block blockJunglePlanksHalf = new BlockHalfBlock(1269, jungleplanks, Block.planks).setBlockName("junglePlanksStep");
	public static final Block blockBaoPlanksHalf = new BlockHalfBlock(1270, baoplanks, Block.planks).setBlockName("baoPlanksStep");
	public static final Block blockPalmPlanksHalf = new BlockHalfBlock(1271, palmplanks, Block.planks).setBlockName("palmPlanksStep");
	public static final Block blockJunglePlanksStairs = new BlockStairs(1272, Block.planks, 1).setBlockName("junglePlanksStairs");
	public static final Block blockBaoPlanksStairs = new BlockStairs(1273, Block.planks, 2).setBlockName("baoPlanksStairs");
	public static final Block blockPalmPlanksStairs = new BlockStairs(1274, Block.planks, 3).setBlockName("palmPlanksStairs");
	public static final Block blockCocoaPlant = new BlockCocoa(1275).setHardness(0.2F).setResistance(5F).setStepSound(Block.soundWoodFootstep).setBlockName("cocoa").setRequiresSelfNotify();
	public static final Block blockBananas = new BlockBananas(1276).setBlockName("bananas").setStepSound(Block.soundGrassFootstep);
	public static final Block blockCoconuts = new BlockCoconuts(1277).setBlockName("coconuts").setStepSound(Block.soundGrassFootstep);
	public static final Block blockSandstoneBrick = new Block(1278, sandstonebrick, Material.rock).setHardness(1.5F).setStepSound(Block.soundStoneFootstep).setBlockName("sandstoneBrick");
	public static final Block blockSandstoneStairs = new BlockStairs(1279, Block.sandStone).setStepSound(Block.soundStoneFootstep).setBlockName("sandstoneStairs");
	public static final Block blockCocoaPlantStem = new BlockCoconutsStem(1280, cocoastem, cocoastem2).setHardness(0.2F).setResistance(5F).setStepSound(Block.soundWoodFootstep).setBlockName("cocoa").setRequiresSelfNotify();
	public static final Block blockBananaStem = new BlockBananasStem(1281).setBlockName("bananas").setStepSound(Block.soundGrassFootstep);
	public static final Block blockCoconutStem = new BlockCoconutsStem(1282, coconutstem, coconutstem2).setBlockName("coconuts").setStepSound(Block.soundGrassFootstep);
	public static final Block blockLiana = new BlockLiana(1283, liana).setBlockName("liana").setStepSound(Block.soundGrassFootstep);
	public static final Block blockSandstoneBrickStep = new BlockGreenStep(1284, sandstonebrick, blockSandstoneBrick, false).setBlockName("sandstoneBrickStep");
	public static final Block blockSandstoneBrickStepUp = new BlockGreenStep(1285, sandstonebrick, blockSandstoneBrick, true).setBlockName("sandstoneBrickStep");
	public static final Block blockSandstoneBrickHalf = new BlockHalfBlock(1286, sandstonebrick, blockSandstoneBrick).setBlockName("sandstoneBrickStep");
	public static final Block blockSandstoneBrickStairs = new BlockStairs(1287, blockSandstoneBrick).setStepSound(Block.soundStoneFootstep).setBlockName("sandstoneBrickStairs");
	public static final Block blockBananaLeavesUp = new BlockPalmLeaves(1288, bananaleavestop, bananaleavesside, true).setHardness(Block.leaves.blockHardness).setStepSound(Block.soundGrassFootstep).setBlockName("bananaLeaves");
	public static final Block blockLeavesPalmUp = new BlockPalmLeaves(1289, palmleavestop, palmleavesside, true).setHardness(Block.leaves.blockHardness).setStepSound(Block.soundGrassFootstep).setBlockName("palmLeaves");
	public static final Block blockPortalSpecial = new BlockPortalSpecial(1290, 14).setHardness(-1F).setStepSound(Block.soundGlassFootstep).setLightValue(0.75F).setBlockName("portal");
	public static final Block blockSpikesWood = new BlockSpikes(1291, spikeswood, Material.wood).setHardness(2.0F).setStepSound(Block.soundWoodFootstep).setBlockName("spikesWood");
	public static final Block blockSpikesStone = new BlockSpikes(1292, spikesstone, Material.rock).setHardness(1.5F).setStepSound(Block.soundStoneFootstep).setBlockName("spikesStone");
	public static final Block blockSpikesIron = new BlockSpikes(1293, spikesiron, Material.iron).setHardness(5F).setStepSound(Block.soundMetalFootstep).setBlockName("spikesIron");
	public static final Block blockSpikesDiamond = new BlockSpikes(1294, spikesdiamond, Material.iron).setHardness(5F).setStepSound(Block.soundMetalFootstep).setBlockName("spikesDiamond");
	public static final Block blockSpikesGold = new BlockSpikes(1295, spikesgold, Material.iron).setHardness(3F).setStepSound(Block.soundMetalFootstep).setBlockName("spikesGold");
	public static final Block blockTileRed_halfUp = new BlockTile(1296, redtile, redtileside, Material.rock, 0.0F, 0.0F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redTile");
	public static final Block blockTileRed_halfDown = new BlockTile(1297, redtile, redtileside, Material.rock, 0.5F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redTile");
	public static final Block blockTileRed_45degree = new BlockTile(1298, redtile, redtileside, Material.rock, 0.0F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redTile");
	public static final Block blockTileRed_floor = new BlockTile(1299, redtile, redtileside, Material.rock, 0.875F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redTile");
	public static final Block blockTileOrange_halfUp = new BlockTile(1464, orangetile, orangetileside, Material.rock, 0.0F, 0.0F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("orangeTile");
	public static final Block blockTileOrange_halfDown = new BlockTile(1465, orangetile, orangetileside, Material.rock, 0.5F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("orangeTile");
	public static final Block blockTileOrange_45degree = new BlockTile(1466, orangetile, orangetileside, Material.rock, 0.0F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("orangeTile");
	public static final Block blockTileOrange_floor = new BlockTile(1467, orangetile, orangetileside, Material.rock, 0.875F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("orangeTile");
	public static final Block blockTileWhite_halfUp = new BlockTile(1468, whitetile, whitetileside, Material.rock, 0.0F, 0.0F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("whiteTile");
	public static final Block blockTileWhite_halfDown = new BlockTile(1469, whitetile, whitetileside, Material.rock, 0.5F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("whiteTile");
	public static final Block blockTileWhite_45degree = new BlockTile(1470, whitetile, whitetileside, Material.rock, 0.0F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("whiteTile");
	public static final Block blockTileWhite_floor = new BlockTile(1471, whitetile, whitetileside, Material.rock, 0.875F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("whiteTile");
	public static final Block blockTileBlue_halfUp = new BlockTile(1472, bluetile, bluetileside, Material.rock, 0.0F, 0.0F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("blueTile");
	public static final Block blockTileBlue_halfDown = new BlockTile(1473, bluetile, bluetileside, Material.rock, 0.5F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("blueTile");
	public static final Block blockTileBlue_45degree = new BlockTile(1474, bluetile, bluetileside, Material.rock, 0.0F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("blueTile");
	public static final Block blockTileBlue_floor = new BlockTile(1475, bluetile, bluetileside, Material.rock, 0.875F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("blueTile");
	public static final Block blockGiftGC = new BlockGift(1476, Block.wool, giftgcup, giftgcdown, giftgcside).setBlockName("giftGC");
	public static final Block blockPPSandstone = new BlockPressurePlate(1477, Block.blockLapis.blockIndexInTexture + 32, EnumMobType.mobs, Material.rock).setHardness(0.5F).setStepSound(Block.soundStoneFootstep).setBlockName("sandstonePP").setRequiresSelfNotify();
	public static final Block blockPPlapislazuli = new BlockPressurePlate(1478, Block.blockLapis.blockIndexInTexture, EnumMobType.mobs, Material.rock).setHardness(0.5F).setStepSound(Block.soundStoneFootstep).setBlockName("lapislazuliPP").setRequiresSelfNotify();
	public static final Block blockPPPlanksJungle = new BlockPressurePlate(1479, jungleplanks, EnumMobType.everything, Material.wood).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("planksJunglePP").setRequiresSelfNotify();
	public static final Block blockPPPlanksBao = new BlockPressurePlate(1480, baoplanks, EnumMobType.everything, Material.wood).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("planksBaoPP").setRequiresSelfNotify();
	public static final Block blockPPPlanksPalm = new BlockPressurePlate(1481, palmplanks, EnumMobType.everything, Material.wood).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("planksPalmPP").setRequiresSelfNotify();
	public static final Block blockPPPlanksBirch = new BlockPressurePlate(1482, birchplank, EnumMobType.everything, Material.wood).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("planksBirchPP").setRequiresSelfNotify();
	public static final Block blockPPPlanksRedwood = new BlockPressurePlate(1483, redwoodplank, EnumMobType.everything, Material.wood).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("planksRedwoodPP").setRequiresSelfNotify();
	public static final Block blockPPPlanksSakura = new BlockPressurePlate(1484, sakuraplank, EnumMobType.everything, Material.wood).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("planksSakuraPP").setRequiresSelfNotify();
	public static final Block blockButtonSandstone = new BlockButton(1485, Block.blockLapis.blockIndexInTexture + 32).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("buttonSandstone").setRequiresSelfNotify();
	public static final Block blockButtonLapislazuli = new BlockButton(1486, Block.blockLapis.blockIndexInTexture).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("buttonLapislazuli").setRequiresSelfNotify();
	public static final Block blockButtonPlanksJungle = new BlockButton(1487, jungleplanks).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("buttonPlanksJungle").setRequiresSelfNotify();
	public static final Block blockButtonPlanksBao = new BlockButton(1488, baoplanks).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("buttonPlanksBao").setRequiresSelfNotify();
	public static final Block blockButtonPlanksPalm = new BlockButton(1489, palmplanks).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("buttonPlanksPalm").setRequiresSelfNotify();
	public static final Block blockButtonPlanksBirch = new BlockButton(1490, birchplank).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("buttonPlanksBirch").setRequiresSelfNotify();
	public static final Block blockButtonPlanksRedwood = new BlockButton(1491, redwoodplank).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("buttonPlanksRedwood").setRequiresSelfNotify();
	public static final Block blockButtonPlanksSakura = new BlockButton(1492, sakuraplank).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("buttonPlanksSakura").setRequiresSelfNotify();
	public static final Block blockLavaSponge = new Block(1493, lavasponge, Material.sponge).setHardness(0.6F).setStepSound(Block.soundGlassFootstep).setBlockName("lavasponge");
	public static final Block blockGarlandRed = new BlockGarland(1495, new int[] {garlandred}).setHardness(0.2F).setStepSound(Block.soundPowderFootstep).setRequiresSelfNotify();
	public static final Block blockGarlandGreen = new BlockGarland(1496, new int[] {garlandgreen}).setHardness(0.2F).setStepSound(Block.soundPowderFootstep).setRequiresSelfNotify();
	public static final Block blockGarlandBlue = new BlockGarland(1497, new int[] {garlandblue}).setHardness(0.2F).setStepSound(Block.soundPowderFootstep).setRequiresSelfNotify();
	public static final Block blockGarlandPurple = new BlockGarland(1498, new int[] {garlandpurple}).setHardness(0.2F).setStepSound(Block.soundPowderFootstep).setRequiresSelfNotify();
	public static final Block blockTileGreen_halfUp = new BlockTile(1499, greentile, greentileside, Material.rock, 0.0F, 0.0F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("greenTile");
	public static final Block blockTileGreen_halfDown = new BlockTile(1501, greentile, greentileside, Material.rock, 0.5F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("greenTile");
	public static final Block blockTileGreen_45degree = new BlockTile(1502, greentile, greentileside, Material.rock, 0.0F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("greenTile");
	public static final Block blockTileGreen_floor = new BlockTile(1503, greentile, greentileside, Material.rock, 0.875F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("greenTile");
	public static final Block blockWinterCobble = new BlockWinter(1504, wintercobble, Block.cobblestone).setBlockName("wintercobble");
	public static final Block blockWinterStoneBrick = new BlockWinter(1505, winterstonebrick, Block.stoneBrick).setBlockName("winterstonebrick");
	public static final Block columnStone = new BlockColumn(1506, stone).setBlockName("columnStone");
	public static final Block columnPlanks = new BlockColumn(1507, planks, 0).setBlockName("columnPlanks");
	public static final Block columnPlanksJungle = new BlockColumn(1508, planks, 1).setBlockName("columnPlanksJungle");
	public static final Block columnPlanksBao = new BlockColumn(1509, planks, 2).setBlockName("columnPlanksBao");
	public static final Block columnPlanksPalm = new BlockColumn(1510, planks, 3).setBlockName("columnPlanksPalm");
	public static final Block columnPlanksRedwood = new BlockColumn(1511, blockRedwoodPlanks).setBlockName("columnPlanksRedwood");
	public static final Block columnPlanksBirch = new BlockColumn(1512, blockBirchPlanks).setBlockName("columnPlanksBirch");
	public static final Block columnPlanksSakura = new BlockColumn(1513, blockSakuraPlanks).setBlockName("columnPlanksSakura");
	public static final Block columnWhiteStone = new BlockColumn(1514, blockWhiteStone).setBlockName("columnWhiteStone");
	public static final Block columnColorOrange = new BlockColumn(1515, blockOrangeColorstone).setBlockName("columnColorOrange");
	public static final Block columnColorMagenta = new BlockColumn(1516, blockMagentaColorstone).setBlockName("columnColorMagenta");
	public static final Block columnColorLightblue = new BlockColumn(1517, blockLightblueColorstone).setBlockName("columnColorLightblue");
	public static final Block columnColorYellow = new BlockColumn(1518, blockYellowColorstone).setBlockName("columnColorYellow");
	public static final Block columnColorLime = new BlockColumn(1519, blockLimeColorstone).setBlockName("columnColorLime");
	public static final Block columnColorPink = new BlockColumn(1520, blockPinkColorstone).setBlockName("columnColorPink");
	public static final Block columnColorGray = new BlockColumn(1521, blockGrayColorstone).setBlockName("columnColorGray");
	public static final Block columnColorSilver = new BlockColumn(1522, blockSilverColorstone).setBlockName("columnColorSilver");
	public static final Block columnColorCyan = new BlockColumn(1523, blockCyanColorstone).setBlockName("columnColorCyan");
	public static final Block columnColorPurple = new BlockColumn(1524, blockPurpleColorstone).setBlockName("columnColorPurple");
	public static final Block columnColorBlue = new BlockColumn(1525, blockBlueColorstone).setBlockName("columnColorBlue");
	public static final Block columnColorBrown = new BlockColumn(1526, blockBrownColorstone).setBlockName("columnColorBrown");
	public static final Block columnColorGreen = new BlockColumn(1527, blockGreenColorstone).setBlockName("columnColorGreen");
	public static final Block columnColorRed = new BlockColumn(1528, blockRedColorstone).setBlockName("columnColorRed");
	public static final Block columnColorBlack = new BlockColumn(1529, blockBlackColorstone).setBlockName("columnColorBlack");
	public static final Block columnMossyCobbleStone = new BlockColumn(1530, cobblestoneMossy).setBlockName("columnMossyCobbleStone");
	public static final Block columnCobblestone = new BlockColumn(1531, cobblestone).setBlockName("columnCobblestone");
	public static final Block columnLapis = new BlockColumn(1532, blockLapis).setBlockName("columnLapis");
	public static final Block columnObsidian = new BlockColumn(1533, obsidian).setBlockName("columnObsidian");
	public static final Block columnWhiteColumn = new BlockColumnTextured(1534, stone, whiteColumnTop, whiteColumnSide).setBlockName("columnWhiteColumn");
	public static final Block columnSandstone = new BlockColumn(1535, sandStone).setBlockName("columnSandsone");

	public static final Block wallStone = new BlockWall(1540, stone).setBlockName("wallStone");
	public static final Block wallWhiteStone = new BlockWall(1541, blockWhiteStone).setBlockName("wallWhiteStone");
	public static final Block wallColorOrange = new BlockWall(1542, blockOrangeColorstone).setBlockName("wallColorOrange");
	public static final Block wallColorMagenta = new BlockWall(1543, blockMagentaColorstone).setBlockName("wallColorMagenta");
	public static final Block wallColorLightblue = new BlockWall(1544, blockLightblueColorstone).setBlockName("wallColorLightblue");
	public static final Block wallColorYellow = new BlockWall(1545, blockYellowColorstone).setBlockName("wallColorYellow");
	public static final Block wallColorLime = new BlockWall(1546, blockLimeColorstone).setBlockName("wallColorLime");
	public static final Block wallColorPink = new BlockWall(1547, blockPinkColorstone).setBlockName("wallColorPink");
	public static final Block wallColorGray = new BlockWall(1548, blockGrayColorstone).setBlockName("wallColorGray");
	public static final Block wallColorSilver = new BlockWall(1549, blockSilverColorstone).setBlockName("wallColorSilver");
	public static final Block wallColorCyan = new BlockWall(1550, blockCyanColorstone).setBlockName("wallColorCyan");
	public static final Block wallColorPurple = new BlockWall(1551, blockPurpleColorstone).setBlockName("wallColorPurple");
	public static final Block wallColorBlue = new BlockWall(1552, blockBlueColorstone).setBlockName("wallColorBlue");
	public static final Block wallColorBrown = new BlockWall(1553, blockBrownColorstone).setBlockName("wallColorBrown");
	public static final Block wallColorGreen = new BlockWall(1554, blockGreenColorstone).setBlockName("wallColorGreen");
	public static final Block wallColorRed = new BlockWall(1555, blockRedColorstone).setBlockName("wallColorRed");
	public static final Block wallColorBlack = new BlockWall(1556, blockBlackColorstone).setBlockName("wallColorBlack");
	public static final Block wallMossyCobbleStone = new BlockWall(1557, cobblestoneMossy).setBlockName("wallMossyCobbleStone");
	public static final Block wallCobblestone = new BlockWall(1558, cobblestone).setBlockName("wallCobblestone");
	public static final Block wallLapis = new BlockWall(1559, blockLapis).setBlockName("wallLapis");
	public static final Block wallObsidian = new BlockWall(1560, obsidian).setBlockName("wallObsidian");

	public static final Block fenceJungle = new BlockFence(1570, jungleplanks).setBlockName("fencePlanksJungle").setHardness(fence.blockHardness).setResistance(fence.blockResistance);
	public static final Block fenceBao = new BlockFence(1571, baoplanks).setBlockName("fencePlanksBao").setHardness(fence.blockHardness).setResistance(fence.blockResistance);
	public static final Block fencePalm = new BlockFence(1572, palmplanks).setBlockName("fencePlanksPalm").setHardness(fence.blockHardness).setResistance(fence.blockResistance);
	public static final Block fenceRedwood = new BlockFence(1573, redwoodplank).setBlockName("fencePlanksRedwood").setHardness(fence.blockHardness).setResistance(fence.blockResistance);
	public static final Block fenceBirch = new BlockFence(1574, birchplank).setBlockName("fencePlanksBirch").setHardness(fence.blockHardness).setResistance(fence.blockResistance);
	public static final Block fenceSakura = new BlockFence(1575, sakuraplank).setBlockName("fencePlanksSakura").setHardness(fence.blockHardness).setResistance(fence.blockResistance);
	public static final Block blockTileGray_halfUp = new BlockTile(1576, graytile, graytileside, Material.rock, 0.0F, 0.0F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockTileGray_halfDown = new BlockTile(1577, graytile, graytileside, Material.rock, 0.5F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockTileGray_45degree = new BlockTile(1578, graytile, graytileside, Material.rock, 0.0F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockTileGray_floor = new BlockTile(1579, graytile, graytileside, Material.rock, 0.875F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockTileLightGray_halfUp = new BlockTile(1580, lightgraytile, lightgraytileside, Material.rock, 0.0F, 0.0F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockTileLightGray_halfDown = new BlockTile(1581, lightgraytile, lightgraytileside, Material.rock, 0.5F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockTileLightGray_45degree = new BlockTile(1582, lightgraytile, lightgraytileside, Material.rock, 0.0F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep);
	public static final Block blockTileLightGray_floor = new BlockTile(1583, lightgraytile, lightgraytileside, Material.rock, 0.875F, 0.5F).setHardness(3.0F).setStepSound(Block.soundStoneFootstep);

	public static final BlockParts octablockWhiteColorstone = (BlockParts) new BlockParts(1600, blockWhiteStone).setBlockName("octablock.whitestone");
	public static final BlockParts octablockOrangeColorstone = (BlockParts) new BlockParts(1601, blockOrangeColorstone).setBlockName("octablock.colorStone.orange");
	public static final BlockParts octablockMagentaColorstone = (BlockParts) new BlockParts(1602, blockMagentaColorstone).setBlockName("octablock.colorStone.magenta");
	public static final BlockParts octablockLightblueColorstone = (BlockParts) new BlockParts(1603, blockLightblueColorstone).setBlockName("octablock.colorStone.lightblue");
	public static final BlockParts octablockYellowColorstone = (BlockParts) new BlockParts(1604, blockYellowColorstone).setBlockName("octablock.colorStone.yellow");
	public static final BlockParts octablockLimeColorstone = (BlockParts) new BlockParts(1605, blockLimeColorstone).setBlockName("octablock.colorStone.lime");
	public static final BlockParts octablockPinkColorstone = (BlockParts) new BlockParts(1606, blockPinkColorstone).setBlockName("octablock.colorStone.pink");
	public static final BlockParts octablockGrayColorstone = (BlockParts) new BlockParts(1607, blockGrayColorstone).setBlockName("octablock.colorStone.gray");
	public static final BlockParts octablockSilverColorstone = (BlockParts) new BlockParts(1608, blockSilverColorstone).setBlockName("octablock.colorStone.silver");
	public static final BlockParts octablockCyanColorstone = (BlockParts) new BlockParts(1609, blockCyanColorstone).setBlockName("octablock.colorStone.cyan");
	public static final BlockParts octablockPurpleColorstone = (BlockParts) new BlockParts(1610, blockPurpleColorstone).setBlockName("octablock.colorStone.purple");
	public static final BlockParts octablockBlueColorstone = (BlockParts) new BlockParts(1611, blockBlueColorstone).setBlockName("octablock.colorStone.blue");
	public static final BlockParts octablockBrownColorstone = (BlockParts) new BlockParts(1612, blockBrownColorstone).setBlockName("octablock.colorStone.brown");
	public static final BlockParts octablockGreenColorstone = (BlockParts) new BlockParts(1613, blockGreenColorstone).setBlockName("octablock.colorStone.green");
	public static final BlockParts octablockRedColorstone = (BlockParts) new BlockParts(1614, blockRedColorstone).setBlockName("octablock.colorStone.red");
	public static final BlockParts octablockBlackColorstone = (BlockParts) new BlockParts(1615, blockBlackColorstone).setBlockName("octablock.colorStone.black");

	public static final BlockParts octablockWhiteStoneBrick = (BlockParts) new BlockParts(1616, blockWhiteStoneBrick).setBlockName("octablock.whiteStoneBrick");
	public static final BlockParts octablockOrangecolorBrick = (BlockParts) new BlockParts(1617, blockOrangeColorbrick).setBlockName("octablock.colorBrick.orange");
	public static final BlockParts octablockMagentacolorBrick = (BlockParts) new BlockParts(1618, blockMagentaColorbrick).setBlockName("octablock.colorBrick.magenta");
	public static final BlockParts octablockLightbluecolorBrick = (BlockParts) new BlockParts(1619, blockLightblueColorbrick).setBlockName("octablock.colorBrick.lightblue");
	public static final BlockParts octablockYellowcolorBrick = (BlockParts) new BlockParts(1620, blockYellowColorbrick).setBlockName("octablock.colorBrick.yellow");
	public static final BlockParts octablockLimecolorBrick = (BlockParts) new BlockParts(1621, blockLimeColorbrick).setBlockName("octablock.colorBrick.lime");
	public static final BlockParts octablockPinkcolorBrick = (BlockParts) new BlockParts(1622, blockPinkColorbrick).setBlockName("octablock.colorBrick.pink");
	public static final BlockParts octablockGraycolorBrick = (BlockParts) new BlockParts(1623, blockGrayColorbrick).setBlockName("octablock.colorBrick.gray");
	public static final BlockParts octablockSilvercolorBrick = (BlockParts) new BlockParts(1624, blockSilverColorbrick).setBlockName("octablock.colorBrick.silver");
	public static final BlockParts octablockCyancolorBrick = (BlockParts) new BlockParts(1625, blockCyanColorbrick).setBlockName("octablock.colorBrick.cyan");
	public static final BlockParts octablockPurplecolorBrick = (BlockParts) new BlockParts(1626, blockPurpleColorbrick).setBlockName("octablock.colorBrick.purple");
	public static final BlockParts octablockBluecolorBrick = (BlockParts) new BlockParts(1627, blockBlueColorbrick).setBlockName("octablock.colorBrick.blue");
	public static final BlockParts octablockBrowncolorBrick = (BlockParts) new BlockParts(1628, blockBrownColorbrick).setBlockName("octablock.colorBrick.brown");
	public static final BlockParts octablockGreencolorBrick = (BlockParts) new BlockParts(1629, blockGreenColorbrick).setBlockName("octablock.colorBrick.green");
	public static final BlockParts octablockRedcolorBrick = (BlockParts) new BlockParts(1630, blockRedColorbrick).setBlockName("octablock.colorBrick.red");
	public static final BlockParts octablockBlackcolorBrick = (BlockParts) new BlockParts(1631, blockBlackColorbrick).setBlockName("octablock.colorBrick.black");

	public static final BlockParts octablockSnow = (BlockParts) new BlockParts(1632, blockSnow).setBlockName("octablock.snow");
	public static final BlockParts octablockNetherRack = (BlockParts) new BlockParts(1633, netherrack).setBlockName("octablock.netherrack");
	public static final BlockParts octablockNetherBrick = (BlockParts) new BlockParts(1634, netherBrick).setBlockName("octablock.netherbrick");
	public static final BlockParts octablockDirt = (BlockParts) new BlockParts(1635, dirt).setBlockName("octablock.dirt");
	//public static final BlockParts octablockGrass = (BlockParts) new BlockPartsGrass(1636, grass).setBlockName("octablock.grass");
	//public static final BlockParts octablockGlass = (BlockParts) new BlockParts(1637, blockBlackColorstone).setBlockName("octablock.");
	public static final BlockParts octablockObsidian = (BlockParts) new BlockParts(1638, obsidian).setBlockName("octablock.obsidian");
	public static final BlockParts octablockLapis = (BlockParts) new BlockParts(1639, blockLapis).setBlockName("octablock.lapis");
	public static final BlockParts octablockMossyCobble = (BlockParts) new BlockParts(1640, cobblestoneMossy).setBlockName("octablock.mossycobble");
	public static final BlockParts octablockMossyStoneBrick = (BlockParts) new BlockParts(1641, stoneBrick, 1).setBlockName("octablock.mossystonebrick");
	public static final BlockParts octablockWhiteWool = (BlockParts) new BlockParts(1642, wool, 0).setBlockName("octablock.wool.white");
	public static final BlockParts octablockOrangeWool = (BlockParts) new BlockParts(1643, wool, 1).setBlockName("octablock.wool.orange");
	public static final BlockParts octablockMagentaWool = (BlockParts) new BlockParts(1644, wool, 2).setBlockName("octablock.wool.magenta");
	public static final BlockParts octablockLightblueWool = (BlockParts) new BlockParts(1645, wool, 3).setBlockName("octablock.wool.lightblue");
	public static final BlockParts octablockYellowWool = (BlockParts) new BlockParts(1646, wool, 4).setBlockName("octablock.wool.yellow");
	public static final BlockParts octablockLimeWool = (BlockParts) new BlockParts(1647, wool, 5).setBlockName("octablock.wool.lime");
	public static final BlockParts octablockPinkWool = (BlockParts) new BlockParts(1648, wool, 6).setBlockName("octablock.wool.pink");
	public static final BlockParts octablockGrayWool = (BlockParts) new BlockParts(1649, wool, 7).setBlockName("octablock.wool.gray");
	public static final BlockParts octablockSilverWool = (BlockParts) new BlockParts(1650, wool, 8).setBlockName("octablock.wool.silver");
	public static final BlockParts octablockCyanWool = (BlockParts) new BlockParts(1651, wool, 9).setBlockName("octablock.wool.cyan");
	public static final BlockParts octablockPurpleWool = (BlockParts) new BlockParts(1652, wool, 10).setBlockName("octablock.wool.purple");
	public static final BlockParts octablockBlueWool = (BlockParts) new BlockParts(1653, wool, 11).setBlockName("octablock.wool.blue");
	public static final BlockParts octablockBrownWool = (BlockParts) new BlockParts(1654, wool, 12).setBlockName("octablock.wool.brown");
	public static final BlockParts octablockGreenWool = (BlockParts) new BlockParts(1655, wool, 13).setBlockName("octablock.wool.green");
	public static final BlockParts octablockRedWool = (BlockParts) new BlockParts(1656, wool, 14).setBlockName("octablock.wool.red");
	public static final BlockParts octablockBlackWool = (BlockParts) new BlockParts(1657, wool, 15).setBlockName("octablock.wool.black");
	//public static final BlockParts octablockIron = (BlockParts) new BlockParts(1658, blockBlackColorstone).setBlockName("octablock.");
	//public static final BlockParts octablockGold = (BlockParts) new BlockParts(1659, blockBlackColorstone).setBlockName("octablock.");
	//public static final BlockParts octablockDiamond = (BlockParts) new BlockParts(1660, blockBlackColorstone).setBlockName("octablock.");
	public static final BlockParts octablockStone = (BlockParts) new BlockParts(1661, stone).setBlockName("octablock.stone");
	//public static final BlockParts octablockStoneSlab = (BlockParts) new BlockParts(1662, blockBlackColorstone).setBlockName("octablock.");
	public static final BlockParts octablockSandstone = (BlockParts) new BlockParts(1663, sandStone).setBlockName("octablock.sandstone");
	public static final BlockParts octablockPlanksOak = (BlockParts) new BlockParts(1664, planks, 0).setBlockName("octablock.planks.oak");
	public static final BlockParts octablockPlanksBirch = (BlockParts) new BlockParts(1665, blockBirchPlanks).setBlockName("octablock.planks.birch");
	public static final BlockParts octablockPlanksRedwood = (BlockParts) new BlockParts(1666, blockRedwoodPlanks).setBlockName("octablock.planks.redwood");
	public static final BlockParts octablockPlanksBao = (BlockParts) new BlockParts(1667, planks, 2).setBlockName("octablock.planks.bao");
	public static final BlockParts octablockPlanksPalm = (BlockParts) new BlockParts(1668, planks, 3).setBlockName("octablock.planks.palm");
	public static final BlockParts octablockPlanksJungle = (BlockParts) new BlockParts(1669, planks, 1).setBlockName("octablock.planks.jungle");
	public static final BlockParts octablockPlanksSakura = (BlockParts) new BlockParts(1670, blockSakuraPlanks).setBlockName("octablock.planks.sakura");
	public static final BlockParts octablockCobblestone = (BlockParts) new BlockParts(1671, cobblestone).setBlockName("octablock.cobblestone");
	public static final BlockParts octablockRedBrick = (BlockParts) new BlockParts(1672, brick).setBlockName("octablock.redbrick");
	public static final BlockParts octablockStoneBrick = (BlockParts) new BlockParts(1673, stoneBrick).setBlockName("octablock.stonebrick");
	public static final BlockParts octablockWhiteStoneMossy = (BlockParts) new BlockParts(1674, blockWhiteStoneBrickMossy).setBlockName("octablock.whitestonemossy");
	public static final BlockParts octablockSandStoneBrick = (BlockParts) new BlockParts(1675, blockSandstoneBrick).setBlockName("octablock.sandstonebrick");
	public static final BlockParts octablockEndStone = (BlockParts) new BlockParts(1676, whiteStone).setBlockName("octablock.endstone");
	public static final BlockParts OCTABLOCK_SANDSTONE_DECOR = (BlockParts) new BlockParts(1677, sandStone, 1).setBlockName("octablock.sandstone.creeper");
	public static final BlockParts OCTABLOCK_SANDSTONE_DECOR_SMOOTH = (BlockParts) new BlockParts(1678, sandStone, 2).setBlockName("octablock.sandstone.smooth");
	public static final BlockParts OCTABLOCK_SANDSTONE_SMOOTH = (BlockParts) new BlockParts(1679, sandStone, 3).setBlockName("octablock.sandstone.smooth2");
	
	public static final Block FRAME = new BlockFrame(1700, Material.wood, 0, frame_overlay_glass).setBlockName("frame");
	public static final Block blockEndStoneStep = new BlockGreenStep(1701, 175, whiteStone, false).setBlockName("endstonestep");
	public static final Block blockEndStoneStepUp = new BlockGreenStep(1702, 175, whiteStone, true).setBlockName("endstonestep");
	public static final Block blockEndStoneHalf = new BlockHalfBlock(1703, 175, whiteStone).setBlockName("endstonestep");
	public static final Block MYSTERY_CHEST = new BlockMysteryChest(1704, "/gc_images/mchest.png").setBlockName("mysteryChest").setHardness(2.5F).setStepSound(soundWoodFootstep).setRequiresSelfNotify();
	public static final Block DIAMOND_CHEST = new BlockNormalChest(1705, "/gc_images/diamondchest.png").setBlockName("sakuraChestDiamond").setHardness(2.5F).setStepSound(soundWoodFootstep).setRequiresSelfNotify();
	public static final Block GOLD_CHEST = new BlockNormalChest(1706, "/gc_images/goldchest.png").setBlockName("sakuraChestGold").setHardness(2.5F).setStepSound(soundWoodFootstep).setRequiresSelfNotify();
	public static final Block IRON_CHEST = new BlockNormalChest(1707, "/gc_images/ironchest.png").setBlockName("sakuraChestIron").setHardness(2.5F).setStepSound(soundWoodFootstep).setRequiresSelfNotify();
	public static final Block OBSIDIAN_CHEST = new BlockNormalChest(1708, "/gc_images/obsidianchest.png").setBlockName("sakuraChestObsidian").setHardness(2.5F).setStepSound(soundWoodFootstep).setRequiresSelfNotify();
	public static final Block ICICLE = new BlockIcicle(1709).setHardness(ice.getHardness()).setStepSound(ice.stepSound).setRequiresSelfNotify();
	public static final Block DECOR_ICE = new BlockDecorIce(1710, 67).setHardness(0.5F).setLightOpacity(3).setStepSound(soundGlassFootstep).setBlockName("decorIce");
	public static final Block SNOW_BRICK = new BlockWinter(1711, snowbrick, stoneBrick).setStepSound(soundClothFootstep).setBlockName("snowBrick");
	public static final Block OMELA_GARLAND = new BlockGarland(1712, new int[] {omelagarland}).setHardness(0.2F).setStepSound(Block.soundPowderFootstep).setRequiresSelfNotify().setBlockName("omelaGarland");
	public static final Block STONE_BRICK_CIRCLE_SNOWY = new BlockWinter(1713, wintercarvedstonebrick, stoneBrick).setBlockName("stoneBrickCircleSnowy");
	public static final Block STONE_BRICK_CRACKED_SNOWY = new BlockWinter(1714, wintercrackedstonebrick, stoneBrick).setBlockName("stoneBrickCrackedSnowy");
	public static final Block STEP_GRAVEL = new BlockGreenStep(1715, gravel.blockIndexInTexture, gravel, false).setBlockName("gravelStep");
	public static final Block STEP_SANDSTONE_DECOR = new BlockGreenStep(1716, BlockSandStone.creeper, sandStone, false).setBlockName("sandStoneStep.creeper");
	public static final Block STEP_SANDSTONE_DECOR_SMOOTH = new BlockGreenStep(1717, BlockSandStone.smooth, sandStone, false).setBlockName("sandStoneStep.smooth");
	public static final Block STEP_SANDSTONE_SMOOTH = new BlockGreenStep(1718, BlockSandStone.top, sandStone, false).setBlockName("sandStoneStep.smooth2");
	public static final Block STEP_SANDSTONE_DECOR_UP = new BlockGreenStep(1719, BlockSandStone.creeper, sandStone, true).setBlockName("sandStoneStep.creeper");
	public static final Block STEP_SANDSTONE_DECOR_SMOOTH_UP = new BlockGreenStep(1720, BlockSandStone.smooth, sandStone, true).setBlockName("sandStoneStep.smooth");
	public static final Block STEP_SANDSTONE_SMOOTH_UP = new BlockGreenStep(1721, BlockSandStone.top, sandStone, true).setBlockName("sandStoneStep.smooth2");
	public static final Block HALF_SANDSTONE_DECOR = new BlockHalfBlock(1722, BlockSandStone.creeper, sandStone).setBlockName("sandStoneStep.creeper");
	public static final Block HALF_SANDSTONE_DECOR_SMOOTH = new BlockHalfBlock(1723, BlockSandStone.smooth, sandStone).setBlockName("sandStoneStep.smooth");
	public static final Block HALF_SANDSTONE_SMOOTH = new BlockHalfBlock(1724, BlockSandStone.top, sandStone).setBlockName("sandStoneStep.smooth2");
	
	public static final Block WHEAT_STACK = new BlockWheat(1725, wheatstackside, wheatstacktop).setHardness(leaves.getHardness()).setStepSound(soundGrassFootstep).setBlockName("wheatstack");
	public static final Block STEP_WHEAT_STACK = new BlockGreenStepTextured(1726, wheatstacktop, wheatstackside, WHEAT_STACK, false).setBlockName("wheatstackstep");
	public static final Block STEP_WHEAT_STACK_UP= new BlockGreenStepTextured(1727, wheatstacktop, wheatstackside, WHEAT_STACK, true).setBlockName("wheatstackstep");
	public static final Block HALF_WHEAT_STACK = new BlockHalfBlockWheat(1728, wheatstackside, wheatstacktop, WHEAT_STACK).setBlockName("wheatstackstep");
	
	public static final Block FRAME_2 = new BlockFrame(1800, Material.wood, 0, frame_overlay_ice).setBlockName("frame").setStepSound(soundWoodFootstep);
	public static final Block BANNER_1 = new BlockBanner(1801, new int[][] {
			{banner_satyr_1_bottom, banner_satyr_1_top}, {banner_satyr_2_bottom, banner_satyr_2_top}, {banner_satyr_3_bottom, banner_satyr_3_top},
			{banner_slime_1_bottom, banner_slime_1_top}, {banner_slime_2_bottom, banner_slime_2_top}, {banner_slime_3_bottom, banner_slime_3_top}}).setBlockName("banner").setStepSound(soundClothFootstep);
	public static final Block CARPET_1 = new BlockCarpet(1802, new int[][]{
			{carpet_0, carpet_0}, {carpet_1, carpet_1}, {carpet_2, carpet_2}, {carpet_3, carpet_3}, {carpet_4, carpet_4}, {carpet_5, carpet_5},
		}).setBlockName("carpet").setHardness(0.8F).setStepSound(soundClothFootstep);
	public static final Block COLORED_GLASS_1 = new BlockColoredGlass(1803, new int[][] {
			{glass_color_white, 0xF2F2F2}, {glass_color_orange, 0xFFB51F}, {glass_color_magenta, 0xE079DB}, {glass_color_light_blue, 0x92BFF6}, {glass_color_yellow, 0xEEE615},
			{glass_color_light_green, 0x84DA18}, {glass_color_pink, 0xF8BADB}, {glass_color_gray, 0x373737}, {glass_color_light_gray, 0x909999}, {glass_color_cyan, 0x1BA9BB},
			{glass_color_purple, 0xB660DD}, {glass_color_blue, 0x1747A4}, {glass_color_brown, 0x6D3E20}, {glass_color_green, 0x52791D},
			{glass_color_red, 0xC42A2A}, {glass_color_black, 0x181818},
		}).setBlockName("glass.colored").setHardness(0.3F).setStepSound(soundGlassFootstep);
	public static final Block COLORED_GLASS_HALF_BLOCK_1 = new BlockColoredHalfBlockGlass(1804, Block.glass, new int[][] {
			{glass_color_white, 0xF2F2F2}, {glass_color_orange, 0xFFB51F}, {glass_color_magenta, 0xE079DB}, {glass_color_light_blue, 0x92BFF6}, {glass_color_yellow, 0xEEE615},
			{glass_color_light_green, 0x84DA18}, {glass_color_pink, 0xF8BADB}, {glass_color_gray, 0x373737}, {glass_color_light_gray, 0x909999}, {glass_color_cyan, 0x1BA9BB},
			{glass_color_purple, 0xB660DD}, {glass_color_blue, 0x1747A4}, {glass_color_brown, 0x6D3E20}, {glass_color_green, 0x52791D},
			{glass_color_red, 0xC42A2A}, {glass_color_black, 0x181818},
		}).setBlockName("glassStep.colored");
	public static final Block COLORED_GLASS_STEP_1 = new BlockColoredGreenStepGlass(1805, Block.glass, false, new int[][] {
			{glass_color_white, 0xF2F2F2}, {glass_color_orange, 0xFFB51F}, {glass_color_magenta, 0xE079DB}, {glass_color_light_blue, 0x92BFF6}, {glass_color_yellow, 0xEEE615},
			{glass_color_light_green, 0x84DA18}, {glass_color_pink, 0xF8BADB}, {glass_color_gray, 0x373737}, {glass_color_light_gray, 0x909999}, {glass_color_cyan, 0x1BA9BB},
			{glass_color_purple, 0xB660DD}, {glass_color_blue, 0x1747A4}, {glass_color_brown, 0x6D3E20}, {glass_color_green, 0x52791D},
			{glass_color_red, 0xC42A2A}, {glass_color_black, 0x181818},
		}).setBlockName("glassStep.colored");
	public static final Block COLORED_GLASS_STEP_UP_1 = new BlockColoredGreenStepGlass(1806, Block.glass, true, new int[][] {
			{glass_color_white, 0xF2F2F2}, {glass_color_orange, 0xFFB51F}, {glass_color_magenta, 0xE079DB}, {glass_color_light_blue, 0x92BFF6}, {glass_color_yellow, 0xEEE615},
			{glass_color_light_green, 0x84DA18}, {glass_color_pink, 0xF8BADB}, {glass_color_gray, 0x373737}, {glass_color_light_gray, 0x909999}, {glass_color_cyan, 0x1BA9BB},
			{glass_color_purple, 0xB660DD}, {glass_color_blue, 0x1747A4}, {glass_color_brown, 0x6D3E20}, {glass_color_green, 0x52791D},
			{glass_color_red, 0xC42A2A}, {glass_color_black, 0x181818},
		}).setBlockName("glassStep.colored");
	public static final Block COLORED_GLASS_STAIRS_1 = new BlockColoredStairsGlass(1807, Block.glass, new int[][] {
			{glass_color_white, 0xF2F2F2}, {glass_color_orange, 0xFFB51F}, {glass_color_magenta, 0xE079DB}, {glass_color_light_blue, 0x92BFF6}, {glass_color_yellow, 0xEEE615},
			{glass_color_light_green, 0x84DA18}, {glass_color_pink, 0xF8BADB}, {glass_color_gray, 0x373737}, {glass_color_light_gray, 0x909999}, {glass_color_cyan, 0x1BA9BB},
			{glass_color_purple, 0xB660DD}, {glass_color_blue, 0x1747A4}, {glass_color_brown, 0x6D3E20}, {glass_color_green, 0x52791D},
			{glass_color_red, 0xC42A2A}, {glass_color_black, 0x181818},
		}).setLightOpacity(0).setBlockName("glassStairs.colored");
	public static final Block COLORED_GLASS_PANE_1 = new BlockColoredPane(1808, 49, glass_pane, glass_pane_cut, Material.glass, true, new int[][] {
			{glass_color_white, 0xF2F2F2}, {glass_color_orange, 0xFFB51F}, {glass_color_magenta, 0xE079DB}, {glass_color_light_blue, 0x92BFF6}, {glass_color_yellow, 0xEEE615},
			{glass_color_light_green, 0x84DA18}, {glass_color_pink, 0xF8BADB}, {glass_color_gray, 0x373737}, {glass_color_light_gray, 0x909999}, {glass_color_cyan, 0x1BA9BB},
			{glass_color_purple, 0xB660DD}, {glass_color_blue, 0x1747A4}, {glass_color_brown, 0x6D3E20}, {glass_color_green, 0x52791D},
			{glass_color_red, 0xC42A2A}, {glass_color_black, 0x181818},
		}).setHardness(0.3F).setStepSound(soundGlassFootstep).setBlockName("thinGlass.colored");
	
	public static final Block PS_PORTAL = new BlockPSPortal(2441, psportal, Material.portal).setHardness(-1F).setStepSound(soundGlassFootstep).setLightValue(0.75F).setBlockName("portal");
	
	public final int blockID;
	public final Material blockMaterial;

	private String blockName;
	protected float blockHardness;
	protected float blockResistance;
	protected boolean blockConstructorCalled;
	protected boolean enableStats;
	public StepSound stepSound;
	public int blockIndexInTexture;
	public double minX;
	public double minY;
	public double minZ;
	public double maxX;
	public double maxY;
	public double maxZ;
	public float blockParticleGravity;
	public float slipperiness;
	protected int blockGlassType = 0;

	protected long baseBreakTime = 1;
	
	private BlockDurability blockDurability;

	protected Block(int i, Material material) {
		blockConstructorCalled = true;
		enableStats = true;
		stepSound = soundPowderFootstep;
		blockParticleGravity = 1.0F;
		slipperiness = 0.6F;
		if(i == 0)
			throw new IllegalArgumentException("0_o");
		if(blocksList[i] != null) {
			throw new IllegalArgumentException(new StringBuilder().append("Slot ").append(i).append(" is already occupied by ").append(blocksList[i]).append(" when adding ").append(this).toString());
		} else {
			blockMaterial = material;
			blocksList[i] = this;
			blockID = i;
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			opaqueCubeLookup[i] = isOpaqueCube();
			lightOpacity[i] = isOpaqueCube() ? 255 : 0;
			transparentMaterial[i] = !material.notTransparent();
			isBlockContainer[i] = false;
			catchesLight[i] = renderAsNormalBlock() && isOpaqueCube();
			return;
		}
	}

	protected Block(int i, int j, Material material) {
		this(i, material);
		blockIndexInTexture = j;
	}

	protected Block setRequiresSelfNotify() {
		requiresSelfNotify[blockID] = true;
		return this;
	}

	protected void initializeBlock() {
	}

	protected Block setStepSound(StepSound stepsound) {
		stepSound = stepsound;
		return this;
	}

	protected Block setLightOpacity(int i) {
		lightOpacity[blockID] = i;
		return this;
	}

	protected Block setLightValue(float f) {
		lightValue[blockID] = (int) (15F * f);
		return this;
	}

	protected Block setResistance(float f) {
		blockResistance = f * 3F;
		return this;
	}

	public boolean renderAsNormalBlock() {
		return true;
	}

	public int getRenderType() {
		return 0;
	}

	protected Block setHardness(float f) {
		blockHardness = f;
		if(blockResistance < f * 5F)
			blockResistance = f * 5F;
		return this;
	}
	
	public BlockDurability getDurability() {
		return this.blockDurability;
	}
	
	public void setDurability(BlockDurability bd) {
		this.blockDurability = bd;
	}

	protected Block setBlockUnbreakable() {
		setHardness(-1F);
		return this;
	}

	public float getHardness() {
		return blockHardness;
	}

	protected Block setTickOnLoad(boolean flag) {
		tickOnLoad[blockID] = flag;
		return this;
	}

	public void setBlockBounds(float f, float f1, float f2, float f3, float f4, float f5) {
		minX = f;
		minY = f1;
		minZ = f2;
		maxX = f3;
		maxY = f4;
		maxZ = f5;
	}

	public float getBlockBrightness(IBlockAccess iblockaccess, int i, int j, int k) {
		return iblockaccess.getBrightness(i, j, k, lightValue[blockID]);
	}

	public int getMixedBrightnessForBlock(IBlockAccess iblockaccess, int i, int j, int k) {
		return iblockaccess.getLightBrightnessForSkyBlocks(i, j, k, lightValue[blockID]);
	}

	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		int blockNearID = iblockaccess.getBlockId(i, j, k);
		Block blockNear = blocksList[blockNearID];
		if(blockNear == null)
			return true;
		if(l == 0) {
			if(minY > 0.0D)
				return true;
			if(blockNear instanceof BlockGreenStepBananaLog)
				return true;
			if(blockNear instanceof BlockStepUp)
				return !blockNear.blockMaterial.getIsOpaque();
			if(blockNear instanceof BlockGreenStep) {
				if(!((BlockGreenStep) blockNear).isUp)
					return true;
				if(((BlockGreenStep) blockNear).original.isOpaqueCube())
					return false;
				if(blockGlassType > 0 && blockNear.blockGlassType == blockGlassType)
					return false;
				return true;
			}
			if(blockNear instanceof BlockStairs) {
				if(!((BlockStairs) blockNear).modelBlock.isOpaqueCube())
					if(blockGlassType == 0 || blockNear.blockGlassType != blockGlassType)
						return true;
				return ((iblockaccess.getBlockMetadata(i, j, k) >> 2) & 1) != 1;
			}
		}
		if(l == 1) {
			if(maxY < 1.0D)
				return true;
			if(blockNear instanceof BlockGreenStepBananaLog)
				return true;
			if(blockNear instanceof BlockGreenStep) {
				if(((BlockGreenStep) blockNear).isUp)
					return true;
				if(((BlockGreenStep) blockNear).original.isOpaqueCube())
					return false;
				if(blockGlassType > 0 && blockNear.blockGlassType == blockGlassType)
					return false;
				return true;
			}
			if(blockNear instanceof BlockStep && !(blockNear instanceof BlockStepUp))
				return !blockNear.blockMaterial.getIsOpaque();
			if(blockNear instanceof BlockStairs) {
				if(!((BlockStairs) blockNear).modelBlock.isOpaqueCube())
					if(blockGlassType == 0 || blockNear.blockGlassType != blockGlassType)
						return true;
				return ((iblockaccess.getBlockMetadata(i, j, k) >> 2) & 1) == 1;
			}
		}
		if(l == 2 && minZ > 0.0D)
			return true;
		if(l == 3 && maxZ < 1.0D)
			return true;
		if(l == 4 && minX > 0.0D)
			return true;
		if(l == 5 && maxX < 1.0D)
			return true;
		if(l == 2 || l == 3 || l == 4 || l == 5) {
			if(blockNear instanceof BlockHalfBlockBananaLog)
				return true;
			if(blockNear instanceof BlockHalfBlock/* || blockNear instanceof BlockStairs */) { // Not working with new stairs, too hard to determine :C
				if(!((IBlockMadeOf) blockNear).getBlockMadeOf().isOpaqueCube())
					if(blockGlassType == 0 || blockNear.blockGlassType != blockGlassType)
						return true;
				int data = iblockaccess.getBlockMetadata(i, j, k) & 3;
				if(l == 5 && data == 1)
					return false;
				if(l == 4 && data == 0)
					return false;
				if(l == 2 && data == 2)
					return false;
				if(l == 3 && data == 3)
					return false;
				return true;
			}
		}
		if(blockGlassType != 0)
			if(blockNear.blockGlassType == blockGlassType)
				return blockNear != Block.glass && !blockNear.isOpaqueCube();
		return !blockNear.isOpaqueCube();
	}

	public boolean getIsBlockSolid(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return iblockaccess.getBlockMaterial(i, j, k).isSolid();
	}

	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return getBlockTextureFromSideAndMetadata(l, iblockaccess.getBlockMetadata(i, j, k));
	}

	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		return getBlockTextureFromSide(i);
	}

	public int getBlockTextureFromSide(int i) {
		return blockIndexInTexture;
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		return AxisAlignedBB.getBoundingBoxFromPool(i + minX, j + minY, k + minZ, i + maxX, j + maxY, k + maxZ);
	}

	public void getCollidingBoundingBoxes(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, ArrayList arraylist) {
		AxisAlignedBB axisalignedbb1 = getCollisionBoundingBoxFromPool(world, i, j, k);
		if(axisalignedbb1 != null && axisalignedbb.intersectsWith(axisalignedbb1))
			arraylist.add(axisalignedbb1);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return AxisAlignedBB.getBoundingBoxFromPool(i + minX, j + minY, k + minZ, i + maxX, j + maxY, k + maxZ);
	}

	public boolean isOpaqueCube() {
		return true;
	}

	public boolean canCollideCheck(int i, boolean flag) {
		return isCollidable();
	}

	public boolean isCollidable() {
		return true;
	}

	public void updateTick(World world, int i, int j, int k, Random random) {
	}

	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
	}

	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l) {
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
	}

	public int tickRate() {
		return 10;
	}

	public void onBlockAdded(World world, int i, int j, int k) {
	}

	public void onBlockRemoval(World world, int i, int j, int k) {
	}

	public int quantityDropped(Random random) {
		return 1;
	}

	public int idDropped(int i, Random random, int j) {
		return blockID;
	}

	public float getDiggDamage(EntityPlayer player, long timeDelay, int blockData) {
		if(baseBreakTime == 0)
			return 0;
		return timeDelay / getBreakTime(player, blockData) * getBreakMultipler(player, blockData);
	}

	protected float getBreakTime(EntityPlayer player, int blockData) {
		return baseBreakTime;
	}

	protected float getBreakMultipler(EntityPlayer player, int blockData) {
		ItemStack item = player.getCurrentEquippedItem();
		if(!this.blockMaterial.getIsHarvestable())
			if(item == null || !item.getItem().canHarvestBlock(this, blockData))
				return 0.33F;
		if(item == null)
			return 1.0F;
		return item.getBlockDamageMultipler(this, player, blockData);
	}

	public float blockStrength(EntityPlayer player, int blockData) {
		if(blockDurability != null)
			return getNewBlockStrength(player);
		if(blockHardness < 0.0F)
			return 0.0F;
		return getBreakMultipler(player, blockData) / blockHardness / 30F * player.getDigSpeedMultipler(this, blockData);
	}
	
	public float getNewBlockStrength(EntityPlayer player) {
		long durability = blockDurability.getBlockDurability(player, player.getCurrentEquippedItem(), null, 0, 0, 0);
		if(durability < 0)
			return 0.0F;
		return 1.0f / (durability / 50f);
	}

	public final void dropBlockAsItem(World world, int i, int j, int k, int l, int i1) {
		dropBlockAsItemWithChance(world, i, j, k, l, 1.0F, i1);
	}

	public void dropBlockAsItemWithChance(World world, int i, int j, int k, int l, float f, int i1) {
		if(world.multiplayerWorld)
			return;
		int j1 = func_40198_a(i1, world.rand);
		for(int k1 = 0; k1 < j1; k1++) {
			if(world.rand.nextFloat() > f)
				continue;
			int l1 = idDropped(l, world.rand, i1);
			if(l1 > 0)
				dropBlockAsItem_do(world, i, j, k, new ItemStack(l1, 1, damageDropped(l)));
		}

	}

	protected void dropBlockAsItem_do(World world, int i, int j, int k, ItemStack itemstack) {
		if(world.multiplayerWorld)
			return;
		float f = 0.7F;
		double d = (world.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
		double d1 = (world.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
		double d2 = (world.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
		EntityItem entityitem = new EntityItem(world, i + d, j + d1, k + d2, itemstack);
		entityitem.delayBeforeCanPickup = 10;
		world.entityJoinedWorld(entityitem);
	}

	protected int damageDropped(int i) {
		return 0;
	}

	public float getExplosionResistance(Entity entity) {
		return blockResistance / 5F;
	}

	public MovingObjectPosition collisionRayTrace(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
		setBlockBoundsBasedOnState(world, i, j, k);
		vec3d = vec3d.addVector(-i, -j, -k);
		vec3d1 = vec3d1.addVector(-i, -j, -k);
		Vec3D vec3d2 = vec3d.getIntermediateWithXValue(vec3d1, minX);
		Vec3D vec3d3 = vec3d.getIntermediateWithXValue(vec3d1, maxX);
		Vec3D vec3d4 = vec3d.getIntermediateWithYValue(vec3d1, minY);
		Vec3D vec3d5 = vec3d.getIntermediateWithYValue(vec3d1, maxY);
		Vec3D vec3d6 = vec3d.getIntermediateWithZValue(vec3d1, minZ);
		Vec3D vec3d7 = vec3d.getIntermediateWithZValue(vec3d1, maxZ);
		if(!isVecInsideYZBounds(vec3d2))
			vec3d2 = null;
		if(!isVecInsideYZBounds(vec3d3))
			vec3d3 = null;
		if(!isVecInsideXZBounds(vec3d4))
			vec3d4 = null;
		if(!isVecInsideXZBounds(vec3d5))
			vec3d5 = null;
		if(!isVecInsideXYBounds(vec3d6))
			vec3d6 = null;
		if(!isVecInsideXYBounds(vec3d7))
			vec3d7 = null;
		Vec3D vec3d8 = null;
		if(vec3d2 != null && (vec3d8 == null || vec3d.distanceTo(vec3d2) < vec3d.distanceTo(vec3d8)))
			vec3d8 = vec3d2;
		if(vec3d3 != null && (vec3d8 == null || vec3d.distanceTo(vec3d3) < vec3d.distanceTo(vec3d8)))
			vec3d8 = vec3d3;
		if(vec3d4 != null && (vec3d8 == null || vec3d.distanceTo(vec3d4) < vec3d.distanceTo(vec3d8)))
			vec3d8 = vec3d4;
		if(vec3d5 != null && (vec3d8 == null || vec3d.distanceTo(vec3d5) < vec3d.distanceTo(vec3d8)))
			vec3d8 = vec3d5;
		if(vec3d6 != null && (vec3d8 == null || vec3d.distanceTo(vec3d6) < vec3d.distanceTo(vec3d8)))
			vec3d8 = vec3d6;
		if(vec3d7 != null && (vec3d8 == null || vec3d.distanceTo(vec3d7) < vec3d.distanceTo(vec3d8)))
			vec3d8 = vec3d7;
		if(vec3d8 == null)
			return null;
		byte byte0 = -1;
		if(vec3d8 == vec3d2)
			byte0 = 4;
		if(vec3d8 == vec3d3)
			byte0 = 5;
		if(vec3d8 == vec3d4)
			byte0 = 0;
		if(vec3d8 == vec3d5)
			byte0 = 1;
		if(vec3d8 == vec3d6)
			byte0 = 2;
		if(vec3d8 == vec3d7)
			byte0 = 3;
		return new MovingObjectPosition(i, j, k, byte0, vec3d8.addVector(i, j, k));
	}

	private boolean isVecInsideYZBounds(Vec3D vec3d) {
		if(vec3d == null)
			return false;
		else
			return vec3d.yCoord >= minY && vec3d.yCoord <= maxY && vec3d.zCoord >= minZ && vec3d.zCoord <= maxZ;
	}

	private boolean isVecInsideXZBounds(Vec3D vec3d) {
		if(vec3d == null)
			return false;
		else
			return vec3d.xCoord >= minX && vec3d.xCoord <= maxX && vec3d.zCoord >= minZ && vec3d.zCoord <= maxZ;
	}

	private boolean isVecInsideXYBounds(Vec3D vec3d) {
		if(vec3d == null)
			return false;
		else
			return vec3d.xCoord >= minX && vec3d.xCoord <= maxX && vec3d.yCoord >= minY && vec3d.yCoord <= maxY;
	}

	public void onBlockDestroyedByExplosion(World world, int i, int j, int k) {
	}
	
	public boolean setUpPass(int pass) {
		return false;
	}

	public int getRenderBlockPass() {
		return 0;
	}

	public boolean canPlaceBlockOnSide(World world, int i, int j, int k, int l) {
		return canPlaceBlockAt(world, i, j, k);
	}

	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		int l = world.getBlockId(i, j, k);
		return l == 0 || blocksList[l].blockMaterial.getIsReplaceable();
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		return false;
	}

	public boolean blockActivated2(World world, int i, int j, int k, EntityPlayer entityplayer) {
		return false;
	}

	public void onEntityWalking(World world, int i, int j, int k, Entity entity) {
	}

	public void onBlockPlaced(World world, int i, int j, int k, int l) {
	}

	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer entityplayer, int face) {
	}

	public void velocityToAddToEntity(World world, int i, int j, int k, Entity entity, Vec3D vec3d) {
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
	}

	public int getBlockColor() {
		return 0xffffff;
	}

	public int getRenderColor(int i) {
		return 0xffffff;
	}

	public int colorMultiplier(IBlockAccess iblockaccess, int i, int j, int k) {
		return 0xffffff;
	}

	public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return false;
	}

	public boolean canProvidePower() {
		return false;
	}

	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
	}

	public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l) {
		return false;
	}

	public void setBlockBoundsForItemRender() {
	}

	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l) {
		entityplayer.addStat(StatList.mineBlockStatArray[blockID], 1);
		entityplayer.addExhaustion(0.025F);
		if(renderAsNormalBlock() && !isBlockContainer[blockID] && EnchantmentHelper.getSilkTouchModifier(entityplayer.inventory)) {
			ItemStack itemstack = func_41049_c_(l);
			if(itemstack != null)
				dropBlockAsItem_do(world, i, j, k, itemstack);
		} else {
			int i1 = EnchantmentHelper.getFortuneModifier(entityplayer.inventory);
			dropBlockAsItem(world, i, j, k, l, i1);
		}
	}

	protected ItemStack func_41049_c_(int i) {
		int j = 0;
		if(Item.itemsList[blockID].getHasSubtypes())
			j = i;
		return new ItemStack(blockID, 1, j);
	}

	public int func_40198_a(int i, Random random) {
		return quantityDropped(random);
	}

	public boolean canBlockStay(World world, int i, int j, int k) {
		return true;
	}

	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
	}

	public Block setBlockName(String s) {
		blockName = "tile." + s;
		return this;
	}

	public String translateBlockName() {
		return StatCollector.translateToLocal(new StringBuilder().append(getBlockName()).append(".name").toString());
	}

	public String getBlockName() {
		return blockName;
	}

	public void powerBlock(World world, int i, int j, int k, int l, int i1) {
	}

	public boolean getEnableStats() {
		return enableStats;
	}

	protected Block disableStats() {
		enableStats = false;
		return this;
	}

	public int getMobilityFlag() {
		return blockMaterial.getMaterialMobility();
	}

	public float getLightFade(IBlockAccess iblockaccess, int i, int j, int k) {
		return iblockaccess.isBlockNormalCube(i, j, k) ? 0.6F : 1.0F;
	}

	protected void init() {
		/*
		 * �� ����� ��������� ���� ��� ������ ����� ������ ������� ��������, �.�. ��� �����������
		 * ����� 1 ���
		 */
		try {
			Class c = Class.forName("net.minecraft.src.Item");
			Field f = c.getField("itemsList");
			if(((Object[]) f.get(null))[blockID] == null)
				Class.forName("net.minecraft.src.ItemBlock").getConstructor(Integer.TYPE).newInstance(blockID - 256);
		} catch (Exception e) {
			try {
				Class c = Class.forName("acy");
				Field f = c.getField("d");
				if(((Object[]) f.get(null))[blockID] == null)
					Class.forName("uw").getConstructor(Integer.TYPE).newInstance(blockID - 256);
			} catch (Exception e1) {
				throw new RuntimeException("Possible file corruption!");
			}
		}
		//if(Item.itemsList[blockID] == null)
		//	new ItemBlock(blockID - 256);
		boolean flag = false;
		if(getRenderType() == 10)
			flag = true;
		else if(this instanceof BlockStep)
			flag = true;
		else if(this == tilledField)
			flag = true;
		useNeighborBrightness[blockID] = flag;
		
		//System.out.println(blockID + ": " + (long) Math.ceil((1f / (1f / blockHardness / 30f) * 50)) + (!blockMaterial.getIsHarvestable() ? (" no harvest (" + ((long) Math.ceil((1f / (1f / blockHardness / 30f) * 50)) * 3)  + ")") : ""));
	}

	static {
		/*
		 * Override snow texture right here
		 */
		Block.snow.blockIndexInTexture = ModLoader.snowTexture;
		((BlockLeaves) blockLeavesBao).setGraphicsLevel(true);

		Item.itemsList[planks.blockID] = new ItemColored(planks.blockID - 256, true).func_41033_a(new String[]{"oak", "jungle", "bao", "palm"}).setItemName("planks");
		Item.itemsList[wood.blockID] = new ItemColored(wood.blockID - 256, true).func_41033_a(new String[]{"oak", "redwood", "birch", "jungle"}).setItemName("log");
		Item.itemsList[stoneBrick.blockID] = new ItemColored(stoneBrick.blockID - 256, true).func_41033_a(new String[]{"stone", "mossy", "cracked", "circle"}).setItemName("stonebricksmooth");
		Item.itemsList[sapling.blockID] = new ItemColored(sapling.blockID - 256, true).func_41033_a(new String[]{"oak", "redwood", "birch", "jungle"}).setItemName("sapling");
		Item.itemsList[sandStone.blockID] = new ItemColored(sandStone.blockID - 256, true).func_41033_a(new String[]{"stone", "creeper", "smooth", "smooth2"}).setItemName("sandStone");
		Item.itemsList[oreCoal.blockID] = new ItemColored(oreCoal.blockID - 256, true).func_41033_a(new String[]{"ore", "decor"}).setItemName("oreCoal");
		Item.itemsList[oreDiamond.blockID] = new ItemColored(oreDiamond.blockID - 256, true).func_41033_a(new String[]{"ore", "decor"}).setItemName("oreDiamond");
		Item.itemsList[oreRedstone.blockID] = new ItemColored(oreRedstone.blockID - 256, true).func_41033_a(new String[]{"ore", "decor"}).setItemName("oreRedstone");
		Item.itemsList[oreLapis.blockID] = new ItemColored(oreLapis.blockID - 256, true).func_41033_a(new String[]{"ore", "decor"}).setItemName("oreLapis");
		Item.itemsList[leaves.blockID] = new ItemLeaves(leaves.blockID - 256).setItemName("leaves");
		Item.itemsList[vine.blockID] = new ItemColored(vine.blockID - 256, false);
		Item.itemsList[tallGrass.blockID] = new ItemColored(tallGrass.blockID - 256, true).func_41033_a(new String[]{"shrub", "grass", "fern"});
		Item.itemsList[blockStepLog.blockID] = new ItemGreenStepLog(blockStepLog.blockID - 256);
		Item.itemsList[blockCoalBlock.blockID] = new ItemColored(blockCoalBlock.blockID - 256, true).func_41033_a(new String[]{"stone", "char"}).setItemName("coalBlock");
		for(int i = 0; i < blocksLength; i++)
			if(blocksList[i] != null)
				blocksList[i].init();
		Item.itemsList[blockGiftNY.blockID].setMaxStackSize(1);
		Item.itemsList[blockGift1402.blockID].setMaxStackSize(1);
		Item.itemsList[blockGiftGC.blockID].setMaxStackSize(1);
		Item.itemsList[blockDiamond.blockID].setUndropable();
		Item.itemsList[sponge.blockID].setUndropable();
		Item.itemsList[blockLavaSponge.blockID].setUndropable();
		catchesLight[ice.blockID] = true;
		transparentMaterial[0] = true;
		StatList.initBreakableStats();
		
		BlockDurabilityFactory.load();
	}
}
