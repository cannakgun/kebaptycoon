package com.kebaptycoon.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/* IsometricHelper.java
 * 
 * Helper class with isometric functions.
 */
public class IsometricHelper {
	
	//Scale of the isometric grid.
	public static final float Scale = 0.5f;
	
	//Angle of the isometric grid
	public static final float Angle = (float) (Math.PI / 3);
	
	//Scale of the z coordinate
	public static final float HeightScale = 1f;

    public static Vector2 origin = new Vector2(0,0);
	
	public static Vector2 project(Vector3 point) {
        float deltaX = (point.x - point.y) * (float)Math.cos(Angle);
        float deltaY = ((point.x + point.y) * (float)Math.sin(Angle)) + (point.z * HeightScale);
        Vector2 delta = new Vector2(deltaX, deltaY);
		return delta.add(origin);
	}

    public static Vector2 project(float x, float y, float z)
    {
        return project(new Vector3(x, y, z));
    }

    public static Vector3 unproject(Vector2 point, float height)
    {
        Vector2 ground = point.sub(0f, height - HeightScale);
        float delta = ground.x / (float)Math.cos(Angle);
        float sum = ground.y / (float)Math.sin(Angle);
        float x = (sum + delta) / 2f;
        float y = (sum - delta) / 2f;
        return new Vector3(x, y, height);
    }

    public static Vector3 unproject(float x, float y, float height)
    {
        return unproject(new Vector2(x, y), height);
    }

    public static Vector3 unproject(Vector2 point)
    {
        return unproject(point, 0f);
    }

    public static Vector3 unproject(float x, float y)
    {
        return unproject(new Vector2(x, y), 0f);
    }
}
