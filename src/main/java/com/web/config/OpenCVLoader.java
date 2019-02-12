package com.web.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Helper to check if OpenCV is loaded and to load OpenCV
 */
public class OpenCVLoader {

	/**
	 * Load OpenCV library
	 */
	public static void load() {
		nu.pattern.OpenCV.loadShared();
		System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
	}

	/**
	 * Check if OpenCV has been loaded.
	 * 
	 * @return true if in library path else false
	 */
	@SuppressWarnings("unchecked")
	public static boolean isLoaded() {
		try {
			final java.lang.reflect.Field LIBRARIES;
			LIBRARIES = ClassLoader.class.getDeclaredField("loadedLibraryNames");
			LIBRARIES.setAccessible(true);
			Object libraries = LIBRARIES.get(ClassLoader.getSystemClassLoader());
			
			if(libraries instanceof Vector) {
				for (String libpath : ((Vector<String>) libraries).toArray(new String[] {}))
					if (libpath.contains(org.opencv.core.Core.NATIVE_LIBRARY_NAME))
						return true;
			}else if (libraries instanceof Map) {
				libraries = (HashMap<String,String>) LIBRARIES.get(ClassLoader.getSystemClassLoader());
				for(Map.Entry<String, String> entry : ((HashMap<String,String>) libraries).entrySet()) {
					if (entry.getKey().contains(org.opencv.core.Core.NATIVE_LIBRARY_NAME))
						return true;
				}
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
