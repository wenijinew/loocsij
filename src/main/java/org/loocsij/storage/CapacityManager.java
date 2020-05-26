package org.loocsij.storage;
/**
 * CapacityManager.java
 *
 * <p>Title: kailiao</p>
 * <p>Description: kailiao</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: zctx</p>
 * @author wengm
 * @version 1.0
 */
public class CapacityManager {
	private static int fatherCapacity;
	private static int childCapacity;
	public int getFatherCapacity() {
		return fatherCapacity;
	}
	public int getChildCapacity() {
		return childCapacity;
	}
	public CapacityManager() {
	}
}
