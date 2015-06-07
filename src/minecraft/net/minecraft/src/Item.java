// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;
import java.util.Random;

import org.greencubes.items.unique.DecorItemQuality;
import org.greencubes.items.unique.DecorItemStatus;
import org.greencubes.items.unique.DecorItemUnique;

import static net.minecraft.src.GreenTextures.*;

public class Item {

	protected static final Random itemRand = new Random();
	public static final Item itemsList[] = new Item[32000];

	public static Item shovelSteel = new ItemSpade(0, EnumToolMaterial.IRON).setIconCoord(2, 5).setItemName("shovelIron");
	public static Item pickaxeSteel = new ItemPickaxe(1, EnumToolMaterial.IRON).setIconCoord(2, 6).setItemName("pickaxeIron");
	public static Item axeSteel = new ItemAxe(2, EnumToolMaterial.IRON).setIconCoord(2, 7).setItemName("hatchetIron");
	public static Item flintAndSteel = new ItemFlintAndSteel(3).setIconCoord(5, 0).setItemName("flintAndSteel");
	public static Item appleRed = new ItemFood(4, 4, 0.3F, false).setIconCoord(10, 0).setItemName("apple");
	public static Item bow = new ItemBow(5, 1.0f).setIconCoord(5, 1).setItemName("bow");
	public static Item arrow = new ItemArrow(6, 1.0f, 1.0f).setIconCoord(5, 2).setItemName("arrow");
	public static Item coal = new ItemCoal(7).setIconCoord(7, 0).setItemName("coal");
	public static Item diamond = new Item(8).setIconCoord(7, 3).setItemName("emerald").setUndropable();
	public static Item ingotIron = new Item(9).setIconCoord(7, 1).setItemName("ingotIron");
	public static Item ingotGold = new Item(10).setIconCoord(7, 2).setItemName("ingotGold");
	public static Item swordSteel = new ItemSword(11, EnumToolMaterial.IRON).setIconCoord(2, 4).setItemName("swordIron");
	public static Item swordWood = new ItemSword(12, EnumToolMaterial.WOOD).setIconCoord(0, 4).setItemName("swordWood");
	public static Item shovelWood = new ItemSpade(13, EnumToolMaterial.WOOD).setIconCoord(0, 5).setItemName("shovelWood");
	public static Item pickaxeWood = new ItemPickaxe(14, EnumToolMaterial.WOOD).setIconCoord(0, 6).setItemName("pickaxeWood");
	public static Item axeWood = new ItemAxe(15, EnumToolMaterial.WOOD).setIconCoord(0, 7).setItemName("hatchetWood").setUsable();
	public static Item swordStone = new ItemSword(16, EnumToolMaterial.STONE).setIconCoord(1, 4).setItemName("swordStone");
	public static Item shovelStone = new ItemSpade(17, EnumToolMaterial.STONE).setIconCoord(1, 5).setItemName("shovelStone");
	public static Item pickaxeStone = new ItemPickaxe(18, EnumToolMaterial.STONE).setIconCoord(1, 6).setItemName("pickaxeStone");
	public static Item axeStone = new ItemAxe(19, EnumToolMaterial.STONE).setIconCoord(1, 7).setItemName("hatchetStone");
	public static Item swordDiamond = new ItemSword(20, EnumToolMaterial.DIAMOND).setIconCoord(3, 4).setItemName("swordDiamond");
	public static Item shovelDiamond = new ItemSpade(21, EnumToolMaterial.DIAMOND).setIconCoord(3, 5).setItemName("shovelDiamond");
	public static Item pickaxeDiamond = new ItemPickaxe(22, EnumToolMaterial.DIAMOND).setIconCoord(3, 6).setItemName("pickaxeDiamond");
	public static Item axeDiamond = new ItemAxe(23, EnumToolMaterial.DIAMOND).setIconCoord(3, 7).setItemName("hatchetDiamond");
	public static Item stick = new Item(24).setIconCoord(5, 3).setFull3D().setItemName("stick").setUsable();
	public static Item bowlEmpty = new Item(25).setIconCoord(7, 4).setItemName("bowl");
	public static Item bowlSoup = new ItemSoup(26, 8).setIconCoord(8, 4).setItemName("mushroomStew");
	public static Item swordGold = new ItemSword(27, EnumToolMaterial.GOLD).setIconCoord(4, 4).setItemName("swordGold");
	public static Item shovelGold = new ItemSpade(28, EnumToolMaterial.GOLD).setIconCoord(4, 5).setItemName("shovelGold");
	public static Item pickaxeGold = new ItemPickaxe(29, EnumToolMaterial.GOLD).setIconCoord(4, 6).setItemName("pickaxeGold");
	public static Item axeGold = new ItemAxe(30, EnumToolMaterial.GOLD).setIconCoord(4, 7).setItemName("hatchetGold");
	public static Item silk = new Item(31).setIconCoord(8, 0).setItemName("string");
	public static Item feather = new Item(32).setIconCoord(8, 1).setItemName("feather");
	public static Item gunpowder = new Item(33).setIconCoord(8, 2).setItemName("sulphur").func_40407_b(PotionHelper.field_40373_k);
	public static Item hoeWood = new ItemHoe(34, EnumToolMaterial.WOOD).setIconCoord(0, 8).setItemName("hoeWood");
	public static Item hoeStone = new ItemHoe(35, EnumToolMaterial.STONE).setIconCoord(1, 8).setItemName("hoeStone");
	public static Item hoeSteel = new ItemHoe(36, EnumToolMaterial.IRON).setIconCoord(2, 8).setItemName("hoeIron");
	public static Item hoeDiamond = new ItemHoe(37, EnumToolMaterial.DIAMOND).setIconCoord(3, 8).setItemName("hoeDiamond");
	public static Item hoeGold = new ItemHoe(38, EnumToolMaterial.GOLD).setIconCoord(4, 8).setItemName("hoeGold");
	public static Item seeds = new ItemSeeds(39, Block.crops.blockID, Block.tilledField.blockID).setIconCoord(9, 0).setItemName("seeds");
	public static Item wheat = new Item(40).setIconCoord(9, 1).setItemName("wheat");
	public static Item bread = new ItemFood(41, 5, 0.6F, false).setIconCoord(9, 2).setItemName("bread");
	public static Item helmetLeather = new ItemArmor(42, EnumArmorMaterial.CLOTH, 0, 0, -1).setIconCoord(0, 0).setItemName("helmetCloth");
	public static Item plateLeather = new ItemArmor(43, EnumArmorMaterial.CLOTH, 0, 1, -1).setIconCoord(0, 1).setItemName("chestplateCloth");
	public static Item legsLeather = new ItemArmor(44, EnumArmorMaterial.CLOTH, 0, 2, -1).setIconCoord(0, 2).setItemName("leggingsCloth");
	public static Item bootsLeather = new ItemArmor(45, EnumArmorMaterial.CLOTH, 0, 3, -1).setIconCoord(0, 3).setItemName("bootsCloth");
	public static Item helmetChain = new ItemDecorArmor(46, EnumArmorMaterial.CHAIN, 1, 0, 3046).setIconCoord(1, 0).setItemName("helmetChain");
	public static Item plateChain = new ItemDecorArmor(47, EnumArmorMaterial.CHAIN, 1, 1, 3046).setIconCoord(1, 1).setItemName("chestplateChain");
	public static Item legsChain = new ItemDecorArmor(48, EnumArmorMaterial.CHAIN, 1, 2, 3046).setIconCoord(1, 2).setItemName("leggingsChain");
	public static Item bootsChain = new ItemDecorArmor(49, EnumArmorMaterial.CHAIN, 1, 3, 3046).setIconCoord(1, 3).setItemName("bootsChain");
	public static Item helmetSteel = new ItemArmorRepairable(50, EnumArmorMaterial.IRON, 2, 0, ingotIron.shiftedIndex).setIconCoord(2, 0).setItemName("helmetIron");
	public static Item plateSteel = new ItemArmorRepairable(51, EnumArmorMaterial.IRON, 2, 1, ingotIron.shiftedIndex).setIconCoord(2, 1).setItemName("chestplateIron");
	public static Item legsSteel = new ItemArmorRepairable(52, EnumArmorMaterial.IRON, 2, 2, ingotIron.shiftedIndex).setIconCoord(2, 2).setItemName("leggingsIron");
	public static Item bootsSteel = new ItemArmorRepairable(53, EnumArmorMaterial.IRON, 2, 3, ingotIron.shiftedIndex).setIconCoord(2, 3).setItemName("bootsIron");
	public static Item helmetDiamond = new ItemArmorRepairable(54, EnumArmorMaterial.DIAMOND, 3, 0, 3046).setIconCoord(3, 0).setItemName("helmetDiamond");
	public static Item plateDiamond = new ItemArmorRepairable(55, EnumArmorMaterial.DIAMOND, 3, 1, 3046).setIconCoord(3, 1).setItemName("chestplateDiamond");
	public static Item legsDiamond = new ItemArmorRepairable(56, EnumArmorMaterial.DIAMOND, 3, 2, 3046).setIconCoord(3, 2).setItemName("leggingsDiamond");
	public static Item bootsDiamond = new ItemArmorRepairable(57, EnumArmorMaterial.DIAMOND, 3, 3, 3046).setIconCoord(3, 3).setItemName("bootsDiamond");
	public static Item helmetGold = new ItemArmorRepairable(58, EnumArmorMaterial.GOLD, 4, 0, ingotGold.shiftedIndex).setIconCoord(4, 0).setItemName("helmetGold");
	public static Item plateGold = new ItemArmorRepairable(59, EnumArmorMaterial.GOLD, 4, 1, ingotGold.shiftedIndex).setIconCoord(4, 1).setItemName("chestplateGold");
	public static Item legsGold = new ItemArmorRepairable(60, EnumArmorMaterial.GOLD, 4, 2, ingotGold.shiftedIndex).setIconCoord(4, 2).setItemName("leggingsGold");
	public static Item bootsGold = new ItemArmorRepairable(61, EnumArmorMaterial.GOLD, 4, 3, ingotGold.shiftedIndex).setIconCoord(4, 3).setItemName("bootsGold");
	public static Item flint = new Item(62).setIconCoord(6, 0).setItemName("flint");
	public static Item porkRaw = new ItemFood(63, 3, 0.3F, true).setIconCoord(7, 5).setItemName("porkchopRaw");
	public static Item porkCooked = new ItemFood(64, 8, 0.8F, true).setIconCoord(8, 5).setItemName("porkchopCooked");
	public static Item painting = new ItemPainting(65).setIconCoord(10, 1).setItemName("painting");
	public static Item appleGold = new ItemAppleGold(66, 10, 1.2F, false).func_35424_o().setPotionEffect(Potion.potionRegeneration.id, 30, 0, 1.0F).setIconCoord(11, 0).setItemName("appleGold");
	public static Item sign = new ItemSign(67).setIconCoord(10, 2).setItemName("sign");
	public static Item doorWood = new ItemDoor(68, Material.wood).setIconCoord(11, 2).setItemName("doorWood");
	public static Item bucketEmpty = new ItemBucket(69, 0).setIconCoord(10, 4).setItemName("bucket").setMaxStackSize(8);
	public static Item bucketWater = new ItemBucket(70, Block.waterMoving.blockID).setIconCoord(11, 4).setItemName("bucketWater").setContainerItem(bucketEmpty);
	public static Item bucketLava = new ItemBucket(71, Block.lavaMoving.blockID).setIconCoord(12, 4).setItemName("bucketLava").setContainerItem(bucketEmpty);
	public static Item minecartEmpty = new ItemMinecart(72, 0).setIconCoord(7, 8).setItemName("minecart");
	public static Item saddle = new ItemSaddle(73).setIconCoord(8, 6).setItemName("saddle");
	public static Item doorSteel = new ItemDoor(74, Material.iron).setIconCoord(12, 2).setItemName("doorIron");
	public static Item redstone = new ItemRedstone(75).setIconCoord(8, 3).setItemName("redstone").func_40407_b(PotionHelper.field_40375_i);
	public static Item snowball = new ItemSnowball(76).setIconCoord(14, 0).setItemName("snowball");
	public static Item boat = new ItemBoat(77).setIconCoord(8, 8).setItemName("boat");
	public static Item leather = new Item(78).setIconCoord(7, 6).setItemName("leather");
	public static Item bucketMilk = new ItemBucketMilk(79).setIconCoord(13, 4).setItemName("milk").setContainerItem(bucketEmpty);
	public static Item brick = new Item(80).setIconCoord(6, 1).setItemName("brick");
	public static Item clay = new Item(81).setIconCoord(9, 3).setItemName("clay");
	public static Item reed = new ItemReed(82, Block.reed).setIconCoord(11, 1).setItemName("reeds");
	public static Item paper = new Item(83).setIconCoord(10, 3).setItemName("paper");
	public static Item book = new Item(84).setIconCoord(11, 3).setItemName("book");
	public static Item slimeBall = new Item(85).setIconCoord(14, 1).setItemName("slimeball");
	public static Item minecartCrate = new ItemMinecart(86, 1).setIconCoord(7, 9).setItemName("minecartChest");
	public static Item minecartPowered = new ItemMinecart(87, 2).setIconCoord(7, 10).setItemName("minecartFurnace");
	public static Item egg = new ItemEgg(88).setIconCoord(12, 0).setItemName("egg");
	public static Item compass = new Item(89).setIconCoord(6, 3).setItemName("compass");
	public static Item fishingRod = new ItemFishingRod(90).setIconCoord(5, 4).setItemName("fishingRod");
	public static Item pocketSundial = new Item(91).setIconCoord(6, 4).setItemName("clock");
	public static Item lightStoneDust = new Item(92).setIconCoord(9, 4).setItemName("yellowDust").func_40407_b(PotionHelper.field_40372_j);
	public static Item fishRaw = new ItemFood(93, 2, 0.3F, false).setIconCoord(9, 5).setItemName("fishRaw");
	public static Item fishCooked = new ItemFood(94, 5, 0.6F, false).setIconCoord(10, 5).setItemName("fishCooked");
	public static Item dyePowder = new ItemDye(95).setIconCoord(14, 4).setItemName("dyePowder");
	public static Item bone = new Item(96).setIconCoord(12, 1).setItemName("bone").setFull3D();
	public static Item sugar = new Item(97).setIconCoord(13, 0).setItemName("sugar").func_40407_b(PotionHelper.field_40365_b);
	public static Item cake = new ItemReed(98, Block.cake).setMaxStackSize(1).setIconCoord(13, 1).setItemName("cake");
	public static Item bed = new ItemBed(99).setMaxStackSize(1).setIconCoord(13, 2).setItemName("bed");
	public static Item redstoneRepeater = new ItemReed(100, Block.redstoneRepeaterIdle).setIconCoord(6, 5).setItemName("diode");
	public static Item cookie = new ItemFood(101, 1, 0.1F, false).setIconCoord(12, 5).setItemName("cookie");
	public static ItemMap map = (ItemMap) new ItemMap(102).setIconCoord(12, 3);
	public static ItemShears shears = (ItemShears) new ItemShears(103).setIconCoord(13, 5).setItemName("shears");
	public static Item melon = new ItemFood(104, 2, 0.3F, false).setIconCoord(13, 6).setItemName("melon");
	public static Item pumpkinSeeds = new ItemSeeds(105, Block.pumpkinStem.blockID, Block.tilledField.blockID).setIconCoord(13, 3).setItemName("seeds_pumpkin");
	public static Item melonSeeds = new ItemSeeds(106, Block.melonStem.blockID, Block.tilledField.blockID).setIconCoord(14, 3).setItemName("seeds_melon");
	public static Item beefRaw = new ItemFood(107, 3, 0.3F, true).setIconCoord(9, 6).setItemName("beefRaw");
	public static Item beefCooked = new ItemFood(108, 8, 0.8F, true).setIconCoord(10, 6).setItemName("beefCooked");
	public static Item chickenRaw = new ItemFood(109, 2, 0.3F, true).setPotionEffect(Potion.potionHunger.id, 30, 0, 0.3F).setIconCoord(9, 7).setItemName("chickenRaw");
	public static Item chickenCooked = new ItemFood(110, 6, 0.6F, true).setIconCoord(10, 7).setItemName("chickenCooked");
	public static Item rottenFlesh = new ItemFood(111, 4, 0.1F, true).setPotionEffect(Potion.potionHunger.id, 30, 0, 0.8F).setIconCoord(11, 5);
	public static Item enderPearl = new ItemEnderPearl(112).setIconCoord(11, 6);
	public static Item blazeRod = new Item(113).setIconCoord(12, 6);
	public static Item ghastTear = new Item(114).setIconCoord(11, 7).func_40407_b(PotionHelper.field_40366_c);
	public static Item goldNugget = new Item(115).setIconCoord(12, 7).setItemName("goldNugget");
	public static Item netherStalkSeeds = new ItemSeeds(116, Block.netherStalk.blockID, Block.slowSand.blockID).setIconCoord(13, 7).func_40407_b("+4");
	public static ItemPotion potion = (ItemPotion) new ItemPotion(117).setIconCoord(13, 8);
	public static Item glassBottle = new ItemGlassBottle(118).setIconCoord(12, 8);
	public static Item spiderEye = new ItemFood(119, 2, 0.8F, false).setPotionEffect(Potion.potionPoison.id, 5, 0, 1.0F).setIconCoord(11, 8).func_40407_b(PotionHelper.field_40363_d);
	public static Item fermentedSpiderEye = new Item(120).setIconCoord(10, 8).func_40407_b(PotionHelper.field_40364_e);
	public static Item blazePowder = new Item(121).setIconCoord(13, 9).func_40407_b(PotionHelper.field_40362_g);
	public static Item magmaCream = new Item(122).setIconCoord(13, 10).func_40407_b(PotionHelper.field_40374_h);
	public static Item brewingStand = new ItemReed(123, Block.brewingStand).setIconCoord(12, 10);
	public static Item cauldron = new ItemReed(124, Block.cauldron).setIconCoord(12, 9);
	public static Item eyeOfEnder = new ItemEnderEye(125).setIconCoord(11, 9);
	public static Item speckledMelon = new Item(126).setIconCoord(9, 8).func_40407_b(PotionHelper.field_40361_f);
	public static Item record13 = new ItemRecord(2000, "13").setIconCoord(0, 15).setItemName("record");
	public static Item recordCat = new ItemRecord(2001, "cat").setIconCoord(1, 15).setItemName("record");
	public static Item recordBlocks = new ItemRecord(2002, "blocks").setIconCoord(2, 15);
	public static Item recordChirp = new ItemRecord(2003, "chirp").setIconCoord(3, 15);
	public static Item recordFar = new ItemRecord(2004, "far").setIconCoord(4, 15);
	public static Item recordMall = new ItemRecord(2005, "mall").setIconCoord(5, 15);
	public static Item recordMellohi = new ItemRecord(2006, "mellohi").setIconCoord(6, 15);
	public static Item recordStal = new ItemRecord(2007, "stal").setIconCoord(7, 15);
	public static Item recordStrad = new ItemRecord(2008, "strad").setIconCoord(8, 15);
	public static Item recordWard = new ItemRecord(2009, "ward").setIconCoord(9, 15);
	public static Item record11 = new ItemRecord(2010, "11").setIconCoord(10, 15);

	public static Item readableBook = new ItemReadableBook(3000 - 256).setIconIndex(readableBookIcon).setItemName("readableBook").setMaxStackSize(1);
	public static Item cocoaBean = new Item(3001 - 256).setIconIndex(cocoabean).setItemName("cocoabean");
	public static Item banana = new ItemFood((3002 - 256), 2, 0.1F, false).setIconIndex(bananaItemTexture).setItemName("banana");
	public static Item bananaGreen = new ItemFood((3003 - 256), 1, 0.1F, false).setIconIndex(bananaGreenItemTexture).setItemName("bananaGreen");
	public static Item bananaCakeItem = new ItemReed(3004 - 256, Block.blockBananaCake).setIconIndex(bananacakeitem).setItemName("bananaCake").setMaxStackSize(1);
	public static Item readableBookGolden = new ItemReadableBook(3005 - 256).setIconIndex(readableBookGoldenIcon).setItemName("readableBookGolden").setMaxStackSize(1);
	public static Item readableBookDiamond = new ItemReadableBook(3006 - 256).setIconIndex(readableBookDiamondIcon).setItemName("readableBookDiamond").setMaxStackSize(1);
	public static Item readableBookObsidian = new ItemReadableBook(3007 - 256).setIconIndex(readableBookObsidianIcon).setItemName("readableBookobs").setMaxStackSize(1);
	public static Item readableBookLocked = new ItemReadableBook(3008 - 256).setIconIndex(readableBookLockedIcon).setItemName("readableBookLocked").setMaxStackSize(1);
	public static Item readableBookGoldenLocked = new ItemReadableBook(3009 - 256).setIconIndex(readableBookGoldenLockedIcon).setItemName("readableBookGoldenLocked").setMaxStackSize(1);
	public static Item readableBookDiamondLocked = new ItemReadableBook(3010 - 256).setIconIndex(readableBookDiamondLockedIcon).setItemName("readableBookDiamondLocked").setMaxStackSize(1);
	public static Item limestoneItem = new Item(3020 - 256).setIconIndex(limestoneItemTexture).setItemName("limestoneItem").setMaxStackSize(64);
	public static Item slakedLime = new Item(3021 - 256).setIconIndex(slakedLimeTexture).setItemName("slakedlime").setMaxStackSize(64);
	public static Item whiteSign = new ItemWhiteSign(3022 - 256).setIconIndex(whiteSignIcon).setItemName("whitesign");
	public static Item sakuraPetal = new ItemSakuraPetal(3023 - 256).setIconIndex(sakurapetal1).setItemName("sakurapetal");
	public static Item briarBerry = new ItemBriar((3024 - 256), 2, 0.1F, Block.blockBriarShrub).setIconIndex(briarberry).setItemName("briarberry");
	public static Item pumpkinSeedsFried = new ItemFood((3025 - 256), 0, 0, false).setIconIndex(pumpkinSeedsFriedIcon).setItemName("pumpkinSeedsFried");
	public static Item pumpkinSlice = new Item(3026 - 256).setIconIndex(pumpkinSliceIcon).setItemName("pumpkinSlice");
	public static Item pumpkinSliceSugar = new Item(3027 - 256).setIconIndex(pumpkinSliceSugarIcon).setItemName("pumpkinSliceSugar");
	public static Item pumpkinSliceFried = new ItemFood((3028 - 256), 1, 0, false).setIconIndex(pumpkinSliceFriedIcon).setItemName("pumpkinSliceFried");
	public static Item chocoCakeItem = new ItemReed(3029 - 256, Block.blockChocoCake).setIconIndex(chococakeitem).setItemName("chocoCake").setMaxStackSize(1);
	public static Item lianaItem = new ItemReed(3031 - 256, Block.blockLiana).setIconIndex(lianaitem).setItemName("liana");
	public static Item coconut = new Item(3032 - 256).setIconIndex(coconutitem).setItemName("coconut");
	public static Item coconutShaving = new ItemFood((3033 - 256), 1, 0, false).setIconIndex(coconutshaving).setItemName("coconutShaving");
	public static Item coconutCookie = new ItemFood((3034 - 256), 1, 0, false).setIconIndex(coconutcookie).setItemName("coconutCookie");
	public static Item premiumCoupon = new ItemPremiumCoupon((3035 - 256)).setIconIndex(couponIcon).setItemName("ticketPremium");
	public static Item cherryBow = new ItemCherryBow(3036 - 256, 2.0f).setIconIndex(cherryBowIcon).setItemName("cherryBow");
	public static Item diamondArrow = new ItemArrow(3037 - 256, 2f, 2f).setIconIndex(diamondArrowIcon).setItemName("diamondArrow");
	public static Item cherryStick = new Item(3038 - 256).setIconIndex(cherryStickIcon).setItemName("cherryStick").setFull3D().setUsable();
	public static Item explosiveArrow = new ItemArrow(3039 - 256, 1f, 0.8f).setIconIndex(explosiveArrowIcon).setItemName("explosiveArrow");
	public static Item redTileItem = new ItemReed(3040 - 256, Block.blockTileRed_45degree).setIconIndex(redTileIcon).setItemName("redTile");
	public static Item orangeTileItem = new ItemReed(3041 - 256, Block.blockTileOrange_45degree).setIconIndex(orangeTileIcon).setItemName("orangeTile");
	public static Item whiteTileItem = new ItemReed(3042 - 256, Block.blockTileWhite_45degree).setIconIndex(whiteTileIcon).setItemName("whiteTile");
	public static Item blueTileItem = new ItemReed(3043 - 256, Block.blockTileBlue_45degree).setIconIndex(blueTileIcon).setItemName("blueTile");
	public static Item greenTileItem = new ItemReed(3044 - 256, Block.blockTileGreen_45degree).setIconIndex(greenTileIcon).setItemName("greenTile");
	public static Item garlandItem = new ItemReed(3045 - 256, Block.blockGarlandRed).setIconIndex(garlandIcon).setItemName("garland");
	public static Item diamondDustItem = new Item(3046 - 256).setIconIndex(diamonddust).setItemName("diamonddust");
	public static Item snowMan = new Item(3047 - 256).setIconIndex(snowmanIcon).setItemName("snowman").setUsable().setMaxStackSize(1);
	public static Item nyHat = new ItemClothImmortal(3048 - 256, 11, 0).setIconIndex(nyHatIcon).setItemName("nyhat");
	public static Item giftBagNy = new ItemGiftBagNy(3049 - 256).setIconIndex(giftBagIcon).setItemName("nybag");

	public static Item redHelmet = new ItemArmorColorLeather(3050 - 256, EnumArmorMaterial.CLOTH, 5, 0).setIconIndex(redhelmet).setItemName("helmetRed");
	public static Item redChestPlate = new ItemArmorColorLeather(3051 - 256, EnumArmorMaterial.CLOTH, 5, 1).setIconIndex(redchestplate).setItemName("chestplateRed");
	public static Item redLeggins = new ItemArmorColorLeather(3052 - 256, EnumArmorMaterial.CLOTH, 5, 2).setIconIndex(redleggins).setItemName("legginsRed");
	public static Item redBoots = new ItemArmorColorLeather(3053 - 256, EnumArmorMaterial.CLOTH, 5, 3).setIconIndex(redboots).setItemName("bootsRed");
	public static Item blueHelmet = new ItemArmorColorLeather(3054 - 256, EnumArmorMaterial.CLOTH, 6, 0).setIconIndex(bluehelmet).setItemName("helmetBlue");
	public static Item blueChestplate = new ItemArmorColorLeather(3055 - 256, EnumArmorMaterial.CLOTH, 6, 1).setIconIndex(bluechestplate).setItemName("chestplateBlue");
	public static Item blueLeggins = new ItemArmorColorLeather(3056 - 256, EnumArmorMaterial.CLOTH, 6, 2).setIconIndex(blueleggins).setItemName("legginsBlue");
	public static Item blueBoots = new ItemArmorColorLeather(3057 - 256, EnumArmorMaterial.CLOTH, 6, 3).setIconIndex(blueboots).setItemName("bootsBlue");
	public static Item greenHelmet = new ItemArmorColorLeather(3058 - 256, EnumArmorMaterial.CLOTH, 7, 0).setIconIndex(greenhelmet).setItemName("helmetGreen");
	public static Item greenChestplate = new ItemArmorColorLeather(3059 - 256, EnumArmorMaterial.CLOTH, 7, 1).setIconIndex(greenchestplate).setItemName("chestplateGreen");
	public static Item greenLeggins = new ItemArmorColorLeather(3060 - 256, EnumArmorMaterial.CLOTH, 7, 2).setIconIndex(greenleggins).setItemName("legginsGreen");
	public static Item greenBoots = new ItemArmorColorLeather(3061 - 256, EnumArmorMaterial.CLOTH, 7, 3).setIconIndex(greenboots).setItemName("bootsGreen");
	public static Item orangeHelmet = new ItemArmorColorLeather(3062 - 256, EnumArmorMaterial.CLOTH, 8, 0).setIconIndex(orangehelmet).setItemName("helmetOrange");
	public static Item orangeChestplate = new ItemArmorColorLeather(3063 - 256, EnumArmorMaterial.CLOTH, 8, 1).setIconIndex(orangechestplate).setItemName("chestplateOrange");
	public static Item orangeLeggins = new ItemArmorColorLeather(3064 - 256, EnumArmorMaterial.CLOTH, 8, 2).setIconIndex(orangeleggins).setItemName("legginsOrange");
	public static Item orangeBoots = new ItemArmorColorLeather(3065 - 256, EnumArmorMaterial.CLOTH, 8, 3).setIconIndex(orangeboots).setItemName("bootsOrange");
	public static Item velvetHelmet = new ItemArmorColorLeather(3066 - 256, EnumArmorMaterial.CLOTH, 9, 0).setIconIndex(velvethelmet).setItemName("helmetVelvet");
	public static Item velvetChestplate = new ItemArmorColorLeather(3067 - 256, EnumArmorMaterial.CLOTH, 9, 1).setIconIndex(velvetchestplate).setItemName("chestplateVelvet");
	public static Item velvetLeggins = new ItemArmorColorLeather(3068 - 256, EnumArmorMaterial.CLOTH, 9, 2).setIconIndex(velvetleggins).setItemName("legginsVelvet");
	public static Item velvetBoots = new ItemArmorColorLeather(3069 - 256, EnumArmorMaterial.CLOTH, 9, 3).setIconIndex(velvetboots).setItemName("bootsVelvet");
	public static Item cyanHelmet = new ItemArmorColorLeather(3070 - 256, EnumArmorMaterial.CLOTH, 10, 0).setIconIndex(cyanhelmet).setItemName("helmetCyan");
	public static Item cyanChestplate = new ItemArmorColorLeather(3071 - 256, EnumArmorMaterial.CLOTH, 10, 1).setIconIndex(cyanchestplate).setItemName("chestplateCyan");
	public static Item cyanLeggins = new ItemArmorColorLeather(3072 - 256, EnumArmorMaterial.CLOTH, 10, 2).setIconIndex(cyanleggins).setItemName("legginsCyan");
	public static Item cyanBoots = new ItemArmorColorLeather(3073 - 256, EnumArmorMaterial.CLOTH, 10, 3).setIconIndex(cyanboots).setItemName("bootsCyan");

	public static Item candy = new ItemCandy(3074 - 256, 5, false).setItemName("candy").setIconIndex(candyIcon);
	public static Item firework = new ItemFireworkAmmo(3075 - 256).setItemName("fireworks").setIconIndex(flareammo);
	public static Item grayTile = new ItemReed(3076 - 256, Block.blockTileGray_45degree).setIconIndex(blackTileIcon).setItemName("grayTile");
	public static Item lightGrayTile = new ItemReed(3077 - 256, Block.blockTileLightGray_45degree).setIconIndex(grayTileIcon).setItemName("lightgrayTile");
	public static Item trammel = new ItemTrammel(3078 - 256).setItemName("trammel").setFull3D();
	public static Item mysterious = new ItemMysterious(3079 - 256).setIconIndex(mysteriousItem).setItemName("mysterious");

	public static Item darkHelmet = new ItemRareCloth(3080 - 256, 12, 0, DecorItemQuality.RARE_WEAR, DecorItemStatus.IMMORTAL).setIconIndex(darkhelmet).setItemName("darkHelmet");
	public static Item darkChestPlate = new ItemRareCloth(3081 - 256, 12, 1, DecorItemQuality.RARE_WEAR, DecorItemStatus.IMMORTAL).setIconIndex(darkchestplate).setItemName("darkChestplate");
	public static Item darkLeggins = new ItemRareCloth(3082 - 256, 12, 2, DecorItemQuality.RARE_WEAR, DecorItemStatus.IMMORTAL).setIconIndex(darkleggins).setItemName("darkLeggins");
	public static Item darkBoots = new ItemRareCloth(3083 - 256, 12, 3, DecorItemQuality.RARE_WEAR, DecorItemStatus.IMMORTAL).setIconIndex(darkboots).setItemName("darkBoots");
	public static Item lightHelmet = new ItemRareCloth(3084 - 256, 13, 0, DecorItemQuality.RARE_WEAR, DecorItemStatus.IMMORTAL).setIconIndex(lighthelmet).setItemName("lightHelmet");
	public static Item lightChestPlate = new ItemRareCloth(3085 - 256, 13, 1, DecorItemQuality.RARE_WEAR, DecorItemStatus.IMMORTAL).setIconIndex(lightchestplate).setItemName("lightChestplate");
	public static Item lightLeggins = new ItemRareCloth(3086 - 256, 13, 2, DecorItemQuality.RARE_WEAR, DecorItemStatus.IMMORTAL).setIconIndex(lightleggins).setItemName("lightLeggins");
	public static Item lightBoots = new ItemRareCloth(3087 - 256, 13, 3, DecorItemQuality.RARE_WEAR, DecorItemStatus.IMMORTAL).setIconIndex(lightboots).setItemName("lightBoots");

	public static Item flareGun = new ItemFlareGun(3088 - 256).setIconIndex(flaregun).setItemName("flareGun");
	public static Item spawnScroll = new ItemCoupon(3089 - 256).setIconIndex(scroll).setItemName("spawnScroll");

	public static Item EARFLAPS_HAT = new ItemRareCloth(3090 - 256, 14, 0, DecorItemQuality.NORMAL_WEAR).setItemName("earflapsHat").setIconIndex(earflapshat);
	public static Item BOW_HAT = new ItemRareCloth(3091 - 256, 15, 0, DecorItemQuality.NORMAL_WEAR).setItemName("bowHat").setIconIndex(bowhat);
	public static Item KEFFIYEH_HAT = new ItemRareCloth(3092 - 256, 16, 0, DecorItemQuality.NORMAL_WEAR).setItemName("keffiyehName").setIconIndex(keffiyehhat);
	public static Item CROWN_HAT = new ItemRareCloth(3093 - 256, 17, 0, DecorItemQuality.HIGH_QUALITY_WEAR).setItemName("crownHat").setIconIndex(crownhat);
	public static Item CREEPER_MASK = new ItemRareCloth(3094 - 256, 18, 0, DecorItemQuality.NORMAL_WEAR).setItemName("creeperMask").setIconIndex(creeperMask);
	public static Item ENDERMAN_MASK = new ItemRareCloth(3095 - 256, 19, 0, DecorItemQuality.NORMAL_WEAR).setItemName("endermanMask").setIconIndex(endermanMask);
	public static Item SKELETON_MASK = new ItemRareCloth(3096 - 256, 20, 0, DecorItemQuality.NORMAL_WEAR).setItemName("skeletonMask").setIconIndex(skeletonMask);
	public static Item SPIDER_MASK = new ItemRareCloth(3097 - 256, 21, 0, DecorItemQuality.NORMAL_WEAR).setItemName("spiderMask").setIconIndex(spiderMask);
	public static Item STEVE_MASK = new ItemRareCloth(3098 - 256, 22, 0, DecorItemQuality.NORMAL_WEAR).setItemName("steveMask").setIconIndex(steveMask);
	public static Item ZOMBIE_MASK = new ItemRareCloth(3099 - 256, 23, 0, DecorItemQuality.NORMAL_WEAR).setItemName("zombieMask").setIconIndex(zombieMask);
	public static Item SPEED_BOOTS = new ItemSpeedBoots(3100 - 256, 24, 0.2f).setIconIndex(speedboots).setItemName("speedBoots");
	public static Item MELON_HAT = new ItemRareCloth(3101 - 256, 26, 0, DecorItemQuality.NORMAL_WEAR).setItemName("watermelon").setIconIndex(watermelonhat);
	public static Item JAPAN_PATH = new ItemRareCloth(3102 - 256, 25, 0, DecorItemQuality.NORMAL_WEAR).setItemName("japaneseBandage").setIconIndex(japanpath);
	public static Item LAUREATE_WREATH = new ItemRareCloth(3103 - 256, 27, 0, DecorItemQuality.RARE_WEAR).setItemName("laureateWreath").setIconIndex(laureatewreath);
	public static Item RED_DOTTED_BOW = new ItemRareCloth(3104 - 256, 28, 0, DecorItemQuality.NORMAL_WEAR).setItemName("redDottedBow").setIconIndex(reddottedbow);
	public static Item BLACK_BOW = new ItemRareCloth(3105 - 256, 29, 0, DecorItemQuality.NORMAL_WEAR).setItemName("blackBow").setIconIndex(blackbow);
	public static Item SAMURAI_HAT = new ItemRareCloth(3106 - 256, 30, 0, DecorItemQuality.RARE_WEAR, DecorItemStatus.IMMORTAL).setItemName("samuraiHat").setIconIndex(samuraihat);
	public static Item SAMURAI_CHESTPLATE = new ItemRareCloth(3107 - 256, 30, 1, DecorItemQuality.RARE_WEAR, DecorItemStatus.IMMORTAL).setItemName("samuraiChestplate").setIconIndex(samuraichestplate);
	public static Item SAMURAI_PANTS = new ItemRareCloth(3108 - 256, 30, 2, DecorItemQuality.RARE_WEAR, DecorItemStatus.IMMORTAL).setItemName("samuraiLeggins").setIconIndex(samuraileggins);
	public static Item SAMURAI_BOOTS = new ItemRareCloth(3109 - 256, 30, 3, DecorItemQuality.RARE_WEAR, DecorItemStatus.IMMORTAL).setItemName("samuraiBoots").setIconIndex(samuraiboots);
	public static Item BLUE_XMAS_HAT = new ItemRareCloth(3110 - 256, 31, 0, DecorItemQuality.NORMAL_WEAR).setItemName("bluenyHat").setIconIndex(bluexmashat);
	public static Item FELT_BOOTS = new ItemValenki(3111 - 256, 32, 2).setItemName("valenki").setIconIndex(valenki);
	public static Item MUSTACHE = new ItemRareCloth(3112 - 256, 37, 0, DecorItemQuality.NORMAL_WEAR).setIconIndex(gentsetmust).setItemName("gentSetMust");
	public static Item MONOCLE = new ItemRareCloth(3113 - 256, 36, 0, DecorItemQuality.NORMAL_WEAR).setIconIndex(gentsetmono).setItemName("gentSetMono");
	public static Item BOWLER_HAT = new ItemRareCloth(3114 - 256, 33, 0, DecorItemQuality.RARE_WEAR).setIconIndex(gentsetbowler).setItemName("gentSetBowler");
	public static Item GENT_SET = new ItemRareCloth(3115 - 256, 34, 0, DecorItemQuality.RARE_WEAR).setIconIndex(gentset).setItemName("gentSet");
	public static Item GUYFAWKES_MASK = new ItemRareCloth(3116 - 256, 35, 0, DecorItemQuality.NORMAL_WEAR).setIconIndex(guyfawkesmask).setItemName("guyFawkesMask");
	public static Item TIE = new ItemRareCloth(3117 - 256, 38, 1, DecorItemQuality.NORMAL_WEAR).setIconIndex(tie).setItemName("tie");
	
	public static Item SCROLL_COORD = new ItemScrollCoord(3183 - 256).setItemName("scrollCoord").setIconIndex(scrollcoord);
	
	public static Item IVY_MOD = new Item(3187 - 256).setItemName("modIvy").setIconIndex(modivy);
	public static Item IVY_STICK = new Item(3188 - 256).setItemName("ivyStick").setIconIndex(ivystick).setFull3D().setUsable();
	public static Item IVY_DIAMONDSWORD = new ItemSword(3189 - 256, EnumToolMaterial.DIAMOND).setDecor(DecorItemQuality.IMPROOVED).setItemName("ivyDiamondSword").setIconIndex(ivydiamondsword);
	public static Item IVY_DIAMONDSHOVEL = new ItemSpade(3190 - 256, EnumToolMaterial.DIAMOND).setDecor(DecorItemQuality.IMPROOVED).setItemName("ivyDiamondShovel").setIconIndex(ivydiamondshovel);
	public static Item IVY_DIAMONDPICKAXE = new ItemPickaxe(3191 - 256, EnumToolMaterial.DIAMOND).setDecor(DecorItemQuality.IMPROOVED).setItemName("ivyDiamondPickaxe").setIconIndex(ivydiamondpickaxe);
	public static Item IVY_DIAMONDAXE = new ItemAxe(3192 - 256, EnumToolMaterial.DIAMOND).setDecor(DecorItemQuality.IMPROOVED).setItemName("ivyDiamondAxe").setIconIndex(ivydiamondaxe);
	public static Item IVY_DIAMONDHOE = new ItemHoe(3193 - 256, EnumToolMaterial.DIAMOND).setDecor(DecorItemQuality.IMPROOVED).setItemName("ivyDiamondHoe").setIconIndex(ivydiamondhoe);
	
	public static Item ICECREAM = new ItemCandy(3194 - 256, 5, false).setItemName("iceCream").setIconIndex(icecream);
	public static Item ICICLE = new ItemIcicle(3195 - 256, Block.ICICLE).setIconIndex(icicleitem).setItemName("iceBucket").setMaxStackSize(1);
	public static Item FROZEN_FLARE_GUN = new ItemFlareGun(3196 - 256).setIconIndex(frozenflaregun).setItemName("icyFlareGun");
	public static Item MANDARIN = new ItemFood(3197 - 256, 5, false).setItemName("mandarin").setIconIndex(mandarin);
	public static Item HALLOWEEN_CANDY = new ItemCandy(3198 - 256, 5, false).setItemName("halloweenCandy").setIconIndex(halloweenCandy);
	public static Item ENERGY_BAR = new ItemFood(3199 - 256, 0, false).setItemName("energyBar").setIconIndex(energybar);
	public static Item DARK_CHEST = new ItemGiftBagNy(3200 - 256).setItemName("darkChest").setIconIndex(darkchest);
	public static Item LIGHT_CHEST = new ItemGiftBagNy(3201 - 256).setItemName("lightChest").setIconIndex(lightchest);
	public static Item FLARE_CHEST = new ItemGiftBagNy(3202 - 256).setItemName("flareChest").setIconIndex(flarechest);
	public static Item GM_CHEST = new ItemGiftBagNy(3203 - 256).setItemName("gmChest").setIconIndex(glowmodchest);
	public static Item HALLOWEEN_BAG = new ItemGiftBagNy(3204 - 256).setItemName("halloweenBag").setIconIndex(halloweenBag);
	public static Item SAMURAI_CHEST = new ItemGiftBagNy(3205 - 256).setItemName("samuraiChest").setIconIndex(samuraichest);
	public static Item RARE_CHEST = new ItemGiftBagNy(3206 - 256).setItemName("rareChest").setIconIndex(rarechest);
	public static Item GIFT_BAG_BLUE = new ItemGiftBagNy(3207 - 256).setItemName("ny2014Chest").setIconIndex(giftbagblue);
	public static Item GIFT_POST = new ItemGiftBagNy(3208 - 256).setItemName("nyPostage").setIconIndex(giftpost);
	public static Item IVY_CHEST = new ItemGiftBagNy(3209 - 256).setItemName("ivyChest").setIconIndex(ivychest);
	public static Item IVY_CHEST_RARE = new ItemGiftBagNy(3210 - 256).setItemName("ivyChestRare").setIconIndex(ivychestrare);

	public static Item GLOWING_MOD = new ItemGlowModifier(3300 - 256).setItemName("gmod.color").setIconIndex(glowmodcolor);
	public static Item PACKED_ITEM = new ItemPackedNamedItem(3301 - 256).setItemName("packedItem").setIconIndex(packeditem);
	public static Item COUNTER_MOD = new ItemCounter(3302 - 256).setItemName("counterMod").setIconIndex(countermod);
	public static Item COUNTER_MOD_REMOVE = new Item(3303 - 256).setItemName("counterModRemove").setIconIndex(counterremove);
	public static Item COUNTER_MOD_CLEAR = new Item(3304 - 256).setItemName("counterModClear").setIconIndex(counterclear);
	public static Item DESCRIPTION_MOD = new ItemDescriptionMod(3305 - 256).setItemName("modDesc").setIconIndex(moddescr);
	public static Item NAME_MOD = new ItemNameMod(3306 - 256).setItemName("modName").setIconIndex(modname);
	public static Item DURABILITY_MOD = new ItemDurabilityMod(3307 - 256).setItemName("moddurability").setIconIndex(moddurability);
	
	public static final Item COLLECTIBLES = new ItemCollectible(4100 - 256, new String[] {"collect.coin1","collect.coin2","collect.coin3","collect.coin4",
			"collect.coin5","collect.badge1", "collectible.rebirth.token1", "collectible.rebirth.token2", "collectible.rebirth.token3", "collectible.rebirth.token4",
			"collectible.rebirth.token5", "collectible.rebirth.badge", "collectible.ny2015.token1", "collectible.ny2015.token2", "collectible.ny2015.token3", "collectible.ny2015.token4",
			"collectible.ny2015.token5", "collectible.ny2015.token6", "collectible.ny2015.token7", "collectible.ny2015.token8", "collectible.ny2015.token9", "collectible.ny2015.badge",
			"collectible.ny2015.badge", "collectible.ny2015.stamp", "collectible.satyr.horn", "collectible.satyr.ear", "collectible.satyr.cloth", "collectible.satyr.badge",
			"collectible.slime.eye", "collectible.slime.goo", "collectible.slime.tentacle", "collectible.slime.badge"},
			new int[] {collect_coin_1,collect_coin_2,collect_coin_3,collect_coin_4,collect_coin_5,collect_badge_1,
			 collectible_rebirth_token1, collectible_rebirth_token2, collectible_rebirth_token3, collectible_rebirth_token4, collectible_rebirth_token5, collectible_rebirth_badge,
			 collectible_ny2015_token1, collectible_ny2015_token2, collectible_ny2015_token3, collectible_ny2015_token4, collectible_ny2015_token5, collectible_ny2015_token6,
			 collectible_ny2015_token7, collectible_ny2015_token8, collectible_ny2015_token9, collectible_ny2015_badge, collectible_ny2015_badge, collectible_ny2015_stamp, collectible_satyr_horn,
			 collectible_satyr_ear, collectible_satyr_cloth, collectible_satyr_badge, collectible_slime_eye, collectible_slime_goo, collectible_slime_tentacle, collectible_slime_badge}, DecorItemQuality.COLLECTIBLE);
	public static Item SET_BOX = new ItemSetBox(4101 - 256).setItemName("setbox").setIconIndex(set_box);
	public static final Item SUMMER_SALE_2014_CHEST = new ItemGiftBagNy(4102 - 256).setItemName("summerSale2014Chest").setIconIndex(summerSale2014Chest);
	public static final Item LUCK_BOX = new ItemGiftBagNy(4103 - 256).setItemName("treasure.luckbox").setIconIndex(set_box).setMaxStackSize(64);
	public static final Item REBIRTH_CHEST = new ItemGiftBagNy(4104 - 256).setItemName("treasure.rebirth").setIconIndex(treasure_rebirth);
	public static final Item HALLOWEEN_BAG_2014 = new ItemGiftBagNy(4105 - 256).setItemName("halloweenBag2014").setIconIndex(halloweenBag).setMaxStackSize(64);
	public static final Item GIFT_BAG_NY2015 = new ItemGiftBagNy(4106 - 256).setItemName("treasure.ny2015giftbag").setIconIndex(treasure_ny2015giftbag);
	public static final Item POST_NY2015 = new ItemGiftBagNy(4107 - 256).setItemName("treasure.ny2015postage").setIconIndex(giftpost);
	
	public static final Item COLLECTIBLES_RARE = new ItemCollectible(4199 - 256, new String[] {"collectible.goldensakura"}, new int[] {collectible_goldensakura}, DecorItemQuality.COLLECTIBLE_RARE);
	public static final Item GLASSES_DEAL_WITH_IT = new ItemRareCloth(4200 - 256, 39, 0, DecorItemQuality.RARE_WEAR).setItemName("dealwithit").setIconIndex(dealwithit);
	public static final Item GLASSES_DEAL_WITH_IT_GOLD = new ItemRareCloth(4201 - 256, 40, 0, DecorItemQuality.HIGH_QUALITY_WEAR).setItemName("dealwithitgold").setIconIndex(dealwithitgold);
	public static final Item BEARD_NY2015 = new ItemRareCloth(4202 - 256, 41, 0, DecorItemQuality.NORMAL_WEAR).setItemName("head.ny2015beard").setIconIndex(head_ny2015beard);
	public static final Item BLUE_NYHAT_2014_BEARD = new ItemRareCloth(4203 - 256, 42, 0, DecorItemQuality.RARE_WEAR).setItemName("head.bluenyhat2014beard").setIconIndex(head_bluenyhat2014beard);
	public static final Item RED_NYHAT_2013_BEARD = new ItemRareCloth(4204 - 256, 43, 0, DecorItemQuality.RARE_WEAR).setItemName("head.rednyhat2013beard").setIconIndex(head_rednyhat2013beard);
	
	public static final Item PICKAXE_DIAMOND_ICY = new ItemPickaxe(4300 - 256, EnumToolMaterial.DIAMOND).setDecor(DecorItemQuality.IMPROOVED).setIconIndex(pickaxe_diamond_icy).setItemName("pickaxe.diamond.icy");
	
	public static final Item MODIFIER = new Item(4302 - 256).setIconIndex(modifier_power).setItemName("modifier.toolpower");
	public static final Item GARLAND_NYPINE = new Item(4303 - 256).setItemName("garland.nypine").setIconIndex(garland_nypine_icon).setUsable();
	public static final Item COOKIE_CHRISTMAS = new ItemFood(4304 - 256, 5, false).setItemName("christmascookie").setIconIndex(christmascookie);
	public static final Item FRAME = new ItemFrame(4305 - 256).setItemName("frame");
	public static final Item FRAME_PARTS = new ItemMultidata(4306 - 256, new String[] {"frame.base.0","frame.base.1","frame.base.2","frame.base.3","frame.base.4","frame.base.5","frame.base.6","frame.base.7",
			"frame.base.8","frame.base.9","frame.base.10","frame.base.11","frame.base.12","frame.base.13","frame.base.14","frame.base.15","frame.overlay.glass","frame.overlay.ice",
			"frame.bg.leather", "frame.bg.velvet"}, new int[] {frame_base_0, frame_base_1, frame_base_2, frame_base_3, frame_base_4, frame_base_5, frame_base_6, frame_base_7, frame_base_8, frame_base_9,
			frame_base_10, frame_base_11, frame_base_12, frame_base_13, frame_base_14, frame_base_15, frame_panel_glass, frame_panel_ice, frame_bckg_leather, frame_bckg_velvet});
	public static final Item POTION_SATYR = new ItemPotionGC(4307 - 256).setIconIndex(potion_satyr).setItemName("potion.satyr");
	public static final Item BANNER = new ItemBanner(4308 - 256, new String[] {"banner.satyr", "banner.slime"}, new int[] {banner_satyr, banner_slime});
	
	public final int shiftedIndex;

	private int maxDamage;
	private Item containerItem;
	private String field_39010_c;
	private String itemName;
	protected int maxStackSize;
	protected int iconIndex;
	protected boolean bFull3D;
	protected boolean hasSubtypes;

	public boolean isUsable;
	public boolean rotate = false;
	protected boolean undropable = false;

	protected Item(int i) {
		maxStackSize = 64;
		maxDamage = 0;
		bFull3D = false;
		hasSubtypes = false;
		containerItem = null;
		field_39010_c = null;
		shiftedIndex = 256 + i;
		if(shiftedIndex == 0)
			throw new IllegalArgumentException("O_o");
		if(itemsList[256 + i] != null)
			throw new IllegalArgumentException(new StringBuilder().append("Items ID conflict at ").append(i).toString());
		itemsList[256 + i] = this;
	}

	public Item setUsable() {
		isUsable = true;
		return this;
	}

	public Item setIconIndex(int i) {
		iconIndex = i;
		return this;
	}

	public Item setMaxStackSize(int i) {
		maxStackSize = i;
		return this;
	}

	public Item setIconCoord(int i, int j) {
		iconIndex = i + j * 16;
		return this;
	}

	public boolean noDrop() {
		return undropable;
	}

	public int getIconFromDamage(int i) {
		return iconIndex;
	}
	
	public Item setUndropable() {
		this.undropable = true;
		return this;
	}

	public int getIconIndex(ItemStack itemstack) {
		return getIconFromDamage(itemstack.getItemDamage());
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		return isUsable;
	}

	public boolean onItemUse2(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		return false;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		return itemstack;
	}

	public ItemStack onFoodEaten(ItemStack itemstack, World world, EntityLiving entityLiving) {
		return itemstack;
	}

	public int getItemStackLimit() {
		return maxStackSize;
	}

	public int getPlacedBlockMetadata(int i) {
		return 0;
	}

	public boolean getHasSubtypes() {
		return hasSubtypes;
	}

	protected Item setHasSubtypes(boolean flag) {
		hasSubtypes = flag;
		return this;
	}

	public int getMaxDamage() {
		return maxDamage;
	}

	protected Item setMaxDamage(int i) {
		maxDamage = i;
		return this;
	}

	public boolean isDamageable() {
		return maxDamage > 0 && !hasSubtypes;
	}

	public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
		return false;
	}

	public boolean onBlockDestroyed(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving) {
		return false;
	}

	public int getDamageVsEntity(Entity entity) {
		return 1;
	}

	public boolean canHarvestBlock(Block block) {
		return false;
	}

	public boolean canHarvestBlock(Block block, int data) {
		return this.canHarvestBlock(block);
	}

	public void useItemOnEntity(ItemStack itemstack, EntityLiving entityliving) {
	}

	public Item setFull3D() {
		bFull3D = true;
		return this;
	}

	public boolean isFull3D() {
		return bFull3D;
	}
	
	public Item rotate() {
		this.rotate = true;
		return this;
	}

	public boolean shouldRotateAroundWhenRendering() {
		return rotate;
	}

	public Item setItemName(String s) {
		itemName = new StringBuilder(s.length() + 5).append("item.").append(s).toString();
		return this;
	}

	public String getUntranslatedName(ItemStack itemstack) {
		String s = getItemNameIS(itemstack);
		if(s == null)
			return "";
		else
			return s;
	}

	public String getItemName() {
		return itemName;
	}

	public String getItemNameIS(ItemStack itemstack) {
		return itemName;
	}

	public Item setContainerItem(Item item) {
		if(maxStackSize > 1)
			throw new IllegalArgumentException("Max stack size must be 1 for items with crafting results");
		else
			containerItem = item;
		return this;
	}

	public Item getContainerItem() {
		return containerItem;
	}

	public boolean hasContainerItem() {
		return containerItem != null;
	}

	public String getStatName() {
		return StatCollector.translateToLocal(new StringBuilder().append(getItemName()).append(".name").toString());
	}

	public int getColorFromIS(ItemStack item) {
		return getColorFromDamage(item.itemDamage);
	}

	public int getColorFromDamage(int i) {
		return 0xffffff;
	}

	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
	}

	public void onCreated(ItemStack itemstack, World world, EntityPlayer entityplayer) {
	}

	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.none;
	}

	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 0;
	}

	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
	}

	protected Item func_40407_b(String s) {
		field_39010_c = s;
		return this;
	}

	public String func_40405_m() {
		return field_39010_c;
	}

	public boolean func_40406_n() {
		return field_39010_c != null;
	}

	public void appendDescription(ItemStack itemstack, List<String> list) {
		String key = getUntranslatedName(itemstack) + ".desc";
		String descr = StringTranslate.getInstance().translateIfExists(key);
		if(descr != null) {
			String[] d = descr.split("\n");
			for(String s : d)
				list.add("\247rffd2d2d2" + s);
		}
	}

	public void appendAttributes(ItemStack itemstack, List<String> list) {

	}

	public String getTranslatedName(ItemStack itemstack) {
		return new StringBuilder().append(StringTranslate.getInstance().translateNamedKey(getUntranslatedName(itemstack))).toString().trim();
	}

	public boolean isEnchanted(ItemStack itemstack) {
		return itemstack.hasEnchantment();
	}

	public EnumRarity getRarity(ItemStack itemstack) {
		if(itemstack.hasEnchantment())
			return EnumRarity.rare;
		else
			return EnumRarity.common;
	}

	public boolean func_40401_i(ItemStack itemstack) {
		return getItemStackLimit() == 1 && isDamageable();
	}

	protected MovingObjectPosition func_40402_a(World world, EntityPlayer entityplayer, boolean flag) {
		float f = 1.0F;
		float f1 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
		float f2 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
		double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * f;
		double d1 = (entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * f + 1.6200000000000001D) - entityplayer.yOffset;
		double d2 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * f;
		Vec3D vec3d = Vec3D.createVector(d, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
		float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
		float f5 = -MathHelper.cos(-f1 * 0.01745329F);
		float f6 = MathHelper.sin(-f1 * 0.01745329F);
		float f7 = f4 * f5;
		float f8 = f6;
		float f9 = f3 * f5;
		double d3 = 5D;
		Vec3D vec3d1 = vec3d.addVector(f7 * d3, f8 * d3, f9 * d3);
		MovingObjectPosition movingobjectposition = world.rayTraceBlocks_do_do(vec3d, vec3d1, flag, !flag);
		return movingobjectposition;
	}

	public boolean traceLiquids(ItemStack stack) {
		return false;
	}

	public int getItemEnchantability() {
		return 0;
	}

	public boolean shouldDrawOnSelection(ItemStack stack) {
		return false;
	}

	public void drawOnSelection(World world, MovingObjectPosition selectedObject, ItemStack item, EntityPlayer player, float partialTick) {

	}

	public float getBlockDamageMultipler(ItemStack itemStack, Block block, EntityPlayer player, int data) {
		return 1.0F;
	}

	public boolean noPrerender() {
		return false;
	}

	public boolean useMultirender(ItemStack source, ItemRenderType renderType) {
		return false;
	}

	public List<ItemStack> getMultirender(ItemStack source, ItemRenderType renderType) {
		return null;
	}

	public ItemState getState(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int face) {
		return null;
	}

	public void updateInInventory(ItemStack item, EntityLiving entity, int slot) {

	}

	public DecorItemQuality getDecorQuality() {
		return null;
	}

	public DecorItemStatus getDecorStatus() {
		return null;
	}

	public DecorItemUnique getDecorUniques() {
		return null;
	}

	public boolean isUnbreakable() {
		return false;
	}

	public float getRenderShift() {
		return 0;
	}

	public void init() {
		
	}
	
	static {
		StatList.initStats();
		for(Item item : itemsList)
			if(item != null)
				item.init();
	}

}
