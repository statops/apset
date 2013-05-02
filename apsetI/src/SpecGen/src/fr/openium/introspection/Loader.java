package fr.openium.introspection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Loader extends ClassLoader {

	public Loader(String s) {
		try {
			mClass = Class.forName(s);
			setClassFound(true);
		} catch (ClassNotFoundException e) {
			setClassFound(false);
		}
		loadAttribute();
		loadMethods();
	}

	public Loader(ClassLoader parent) {
		super(parent);
	}

	public Class loadClass(String url) throws ClassNotFoundException {
		try {

			URL myUrl = new URL(url);
			URLConnection connection = myUrl.openConnection();
			InputStream input = connection.getInputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int data = input.read();
			while (data != -1) {
				buffer.write(data);
				data = input.read();
			}

			input.close();

			byte[] classData = buffer.toByteArray();

			return defineClass("reflection.MyObject", classData, 0,
					classData.length);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private Class mClass;
	private Field[] mAttributes;
	private Method[] mMethods;
	private boolean mhasCP;
	private boolean mhasIntent;
	private boolean mWaitResult;
	private boolean mClassFound;

	/**
	 * load attribute of the Component class and check if it exists the
	 * ContentResolver, Uri type. if (true) add it to component that access to
	 * content Provider
	 * 
	 * load attribute of the Component class and check if it exists the Intent
	 * type. if (true) add it to component that handle intent
	 * 
	 * */
	private static final String CONTENTRESOLVER = "Contentresolver";
	private static final String INTENT = "Intent";

	public void loadAttribute() {
		mAttributes = mClass.getDeclaredFields();
		for (int i = 0; i < mAttributes.length; i++) {
			Field f = mAttributes[i];
			if (f.getType().getName().equalsIgnoreCase(CONTENTRESOLVER)) {
				sethasCP(true);
			}
			if (f.getType().getName().equalsIgnoreCase(INTENT)) {
				sethasIntent(true);
			}
		}
	}

	private static final String OnActivityResult = "onActivityResult()";

	/**
	 * load method of the Component class and check if it exists the
	 * "OnActivityResult()" type. if (true) add it to component that expect an
	 * result.
	 * 
	 * 
	 * 
	 * */

	public void loadMethods() {
		mMethods = mClass.getDeclaredMethods();
		for (int i = 0; i < mMethods.length; i++) {
			if (mMethods[i].getName().equals(OnActivityResult)) {
				setWaitResult(true);
			}

		}

	}

	public boolean hasCP() {
		return mhasCP;
	}

	public void sethasCP(boolean mhasCP) {
		this.mhasCP = mhasCP;
	}

	public boolean hasIntent() {
		return mhasIntent;
	}

	public void sethasIntent(boolean mhasIntent) {
		this.mhasIntent = mhasIntent;
	}

	public boolean waitResult() {
		return mWaitResult;
	}

	public void setWaitResult(boolean mWaitResult) {
		this.mWaitResult = mWaitResult;
	}

	public boolean classFound() {
		return mClassFound;
	}

	public void setClassFound(boolean mClassFound) {
		this.mClassFound = mClassFound;
	}
}
