package com.kebaptycoon.utils;

/* IsometricHelper.java
 * 
 * Helper class with isometric functions.
 */
public class IsometricHelper {
	
	//Scale of the isometric grid.
	public static final float Scale = 0.5f;
	
	//Angle of the isometric grid
	public static final float Angle = (float) Math.atan(0.5f);
	
	//Scale of the z coordinate
	public static final float HeightScale = 1f;
	
	public static Pair<Float,Float> translate(int x, int y, int z) {
		float trueX = x + y;
		float trueY = (float) ((y * Math.tan(Angle)) - (x * Math.tan(Angle)) + 0.25f + (z * HeightScale));
		return new Pair<Float,Float>(trueX / Scale,trueY / Scale);
	}
}
