package com.runemonk.differences.data;

import com.google.gson.JsonObject;

import net.runelite.api.HeadIcon;
import net.runelite.api.Player;
import net.runelite.api.PlayerComposition;
import net.runelite.api.SkullIcon;
import net.runelite.api.kit.KitType;

public class PlayerData extends ActorData
{
	HeadIcon overheadIcon;
	SkullIcon skullIcon;
	String username = "";

	//better to do it like this instead of a class
	//individual variables will be updated instead of the whole class then
	int equipHead, equipTorso, equipCape, equipAmulet, equipLegs, equipWeapon, equipShield, equipHands, equipBoots;
	int kitTorso, kitArms, kitLegs, kitHair, kitHands, kitBoots, kitJaw;
	int[] bodyPartColours = new int[5];

	boolean isFemale;

	public PlayerData()
	{
	}

	public PlayerData(Object obj)
	{
		super(obj);
		setData(obj);
	}

	@Override
	public void setData(Object obj)
	{
		super.setData(obj);

		Player p = (Player) obj;
		PlayerComposition pc = p.getPlayerComposition();

		equipHead = pc.getEquipmentId(KitType.HEAD);
		equipTorso = pc.getEquipmentId(KitType.TORSO);
		equipCape = pc.getEquipmentId(KitType.CAPE);
		equipAmulet = pc.getEquipmentId(KitType.AMULET);
		equipLegs = pc.getEquipmentId(KitType.LEGS);
		equipWeapon = pc.getEquipmentId(KitType.WEAPON);
		equipShield = pc.getEquipmentId(KitType.SHIELD);
		equipHands = pc.getEquipmentId(KitType.HANDS);
		equipBoots = pc.getEquipmentId(KitType.BOOTS);

		kitTorso = pc.getKitId(KitType.TORSO);
		kitArms = pc.getKitId(KitType.ARMS);
		kitLegs = pc.getKitId(KitType.LEGS);
		kitHair = pc.getKitId(KitType.HAIR);
		kitHands = pc.getKitId(KitType.HANDS);
		kitBoots = pc.getKitId(KitType.BOOTS);
		kitJaw = pc.getKitId(KitType.JAW);

		overheadIcon = p.getOverheadIcon();
		skullIcon = p.getSkullIcon();
		username = p.getName();

		isFemale = p.getPlayerComposition().isFemale();
		bodyPartColours = p.getPlayerComposition().getColors();
		//p.
	}

	@Override
	public JsonObject getDifference(Data obj)
	{
		PlayerData p = (PlayerData) obj;
		JsonObject differences = super.getDifference(obj);

		if (equipHead != p.equipHead)
			differences.addProperty("equipHead", p.equipHead);

		if (equipTorso != p.equipTorso)
			differences.addProperty("equipTorso", p.equipTorso);

		if (equipCape != p.equipCape)
			differences.addProperty("equipCape", p.equipCape);

		if (equipAmulet != p.equipAmulet)
			differences.addProperty("equipAmulet", p.equipAmulet);

		if (equipLegs != p.equipLegs)
			differences.addProperty("equipLegs", p.equipLegs);

		if (equipWeapon != p.equipWeapon)
			differences.addProperty("equipWeapon", p.equipWeapon);

		if (equipShield != p.equipShield)
			differences.addProperty("equipShield", p.equipShield);

		if (equipHands != p.equipHands)
			differences.addProperty("equipHands", p.equipHands);

		if (equipBoots != p.equipBoots)
			differences.addProperty("equipBoots", p.equipBoots);

		if (equipHands != p.equipHands)
			differences.addProperty("equipHands", p.equipHands);

		if (kitTorso != p.kitTorso)
			differences.addProperty("kitTorso", p.kitTorso);

		if (kitArms != p.kitArms)
			differences.addProperty("kitArms", p.kitArms);

		if (kitLegs != p.kitLegs)
			differences.addProperty("kitLegs", p.kitLegs);

		if (kitHair != p.kitHair)
			differences.addProperty("kitHair", p.kitHair);

		if (kitHands != p.kitHands)
			differences.addProperty("kitHands", p.kitHands);

		if (kitBoots != p.kitBoots)
			differences.addProperty("kitBoots", p.kitBoots);

		if (kitJaw != p.kitJaw)
			differences.addProperty("kitJaw", p.kitJaw);

		if (overheadIcon != p.overheadIcon)
			differences.addProperty("overheadIcon", p.overheadIcon.ordinal());

		if (skullIcon != p.skullIcon)
			differences.addProperty("skullIcon", p.skullIcon.ordinal());

		if (username.compareTo(p.username) != 0)
			differences.addProperty("username", p.username);

		if (isFemale != p.isFemale)
			differences.addProperty("isFemale", p.isFemale);

		for (int i = 0; i < 5; i++)
		{
			if (bodyPartColours[i] != p.bodyPartColours[i])
				differences.addProperty("bodyPartColour" + i, p.bodyPartColours[i]);
		}
		if (isFemale != p.isFemale)
			differences.addProperty("isFemale", p.isFemale);

		return differences;
	}

}
