package com.runemonk.differences.data;

import com.google.gson.JsonObject;
import net.runelite.api.Projectile;

public class ProjectileData extends Data
{
	int projectileId, floor, startHeight, endHeight, slope, startMovementCycle, remainingCycles;
	double scalar, x, y, z, x1, y1, xVelocity, yVelocity, zVelocity;

	public ProjectileData()
	{
	}

	public ProjectileData(Object obj)
	{
		super(obj);
		setData(obj);
	}

	@Override
	public void setData(Object obj)
	{
		super.setData(obj);

		Projectile p = (Projectile) obj;
		projectileId = p.getId();
		floor = p.getFloor();
		startHeight = p.getStartHeight();
		endHeight = p.getEndHeight();
		slope = p.getSlope();
		scalar = p.getScalar();
		startMovementCycle = p.getStartMovementCycle();
		remainingCycles = p.getRemainingCycles();
		x = p.getX();
		y = p.getY();
		z = p.getZ();

		x1 = p.getX1();
		y1 = p.getY1();

		xVelocity = p.getVelocityX();
		yVelocity = p.getVelocityY();
		zVelocity = p.getVelocityZ();
	}

	@Override
	public JsonObject getDifference(Data obj)
	{
		ProjectileData p = (ProjectileData) obj;

		JsonObject differences = new JsonObject();

		if (projectileId != p.projectileId)
			differences.addProperty("projectileId", p.projectileId);

		if (floor != p.floor)
			differences.addProperty("floor", p.floor);

		if (startHeight != p.startHeight)
			differences.addProperty("startHeight", p.startHeight);

		if (endHeight != p.endHeight)
			differences.addProperty("endHeight", p.endHeight);

		if (slope != p.slope)
			differences.addProperty("slope", p.slope);

		if (scalar != p.scalar)
			differences.addProperty("scalar", p.scalar);

		if (startMovementCycle != p.startMovementCycle)
			differences.addProperty("startMovementCycle", p.startMovementCycle);

		if (remainingCycles != p.remainingCycles)
			differences.addProperty("remainingCycles", p.remainingCycles);

		if (x != p.x)
			differences.addProperty("x", p.x);

		if (y != p.y)
			differences.addProperty("y", p.y);

		if (z != p.z)
			differences.addProperty("z", p.z);

		if (x1 != p.x1)
			differences.addProperty("x1", p.x1);

		if (y1 != p.y1)
			differences.addProperty("y1", p.y1);

		if (xVelocity != p.xVelocity)
			differences.addProperty("xVelocity", p.xVelocity);

		if (yVelocity != p.yVelocity)
			differences.addProperty("yVelocity", p.yVelocity);

		if (zVelocity != p.zVelocity)
			differences.addProperty("zVelocity", p.zVelocity);

		return differences;
	}
}
