package fr.openium.specification.xml;

/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import fr.openium.components.ActivityComponent;
import fr.openium.components.ContentProviderComponent;
import fr.openium.components.ReceiverComponent;
import fr.openium.components.ServiceComponent;
import fr.openium.iosts.IostsParser;
import fr.openium.specification.IAbstractComponent;
import fr.openium.specification.IAbstractIntent;
import fr.openium.specification.config.Actions;

/**
 * Class containing the manifest info obtained during the parsing.
 */
public final class ManifestData {

	/**
	 * Value returned by {@link #getMinSdkVersion()} when the value of the
	 * minSdkVersion attribute in the manifest is a codename and not an integer
	 * value.
	 */
	public final static int MIN_SDK_CODENAME = 0;

	/**
	 * Value returned by {@link #getGlEsVersion()} when there are no
	 * <uses-feature> node with the attribute glEsVersion set.
	 */
	public final static int GL_ES_VERSION_NOT_SET = -1;

	/** Application package */
	String mPackage;
	/** Application version Code, null if the attribute is not present. */
	Integer mVersionCode = null;
	/** List of all activities */
	final ArrayList<ActivityComponent> mActivities = new ArrayList<ActivityComponent>();
	/** List of all services */
	final ArrayList<ServiceComponent> mServices = new ArrayList<ServiceComponent>();
	/** List of all providers */
	final ArrayList<ContentProviderComponent> mProviders = new ArrayList<ContentProviderComponent>();
	/** List of all receivers */
	final ArrayList<ReceiverComponent> mReceivers = new ArrayList<ReceiverComponent>();
	/** List of all components */
	private final ArrayList<IAbstractComponent> mComponents = new ArrayList<IAbstractComponent>();

	/** list of process names declared by the manifest */
	Set<String> mProcesses = null;
	/** debuggable attribute value. If null, the attribute is not present. */
	Boolean mDebuggable = null;
	/** API level requirement. if null the attribute was not present. */
	private String mMinSdkVersionString = null;
	/**
	 * API level requirement. Default is 1 even if missing. If value is a
	 * codename, then it'll be 0 instead.
	 */
	private int mMinSdkVersion = 1;
	private int mTargetSdkVersion = 0;
	/** List of all instrumentations declared by the manifest */
	final ArrayList<Instrumentation> mInstrumentations = new ArrayList<Instrumentation>();

	/** enabled attribute value of Application level */
	Boolean mEnabled = null;

	/** permission attribute value of Application level */
	String mPermission = null;

	/**
	 * Instrumentation info obtained from manifest
	 */
	public final static class Instrumentation {
		private final String mName;
		private final String mTargetPackage;

		Instrumentation(String name, String targetPackage) {
			mName = name;
			mTargetPackage = targetPackage;
		}

		/**
		 * Returns the fully qualified instrumentation class name
		 */
		public String getName() {
			return mName;
		}

		/**
		 * Returns the Android app package that is the target of this
		 * instrumentation
		 */
		public String getTargetPackage() {
			return mTargetPackage;
		}
	}

	/**
	 * The class representing an Intent from ManifestFile
	 */
	public static final class Intent extends IAbstractIntent {

		public Intent(String action) {
			super(action);
			mIntentType = MANIFESTINTENT;
		}

		public String toString() {

			String temp = "action :" + mAction;

			if (mCategories.isEmpty())
				temp = temp + "\n  categories: " + "null";
			else {
				for (int i = 0; i < mCategories.size(); i++)
					temp = temp + "\n  categories: " + mCategories.get(i);
			}

			if (mData.isEmpty())
				temp = temp + "\n  data: " + "null";
			else {
				for (int i = 0; i < mData.size(); i++)
					temp = temp + "\n  data: " + mData.get(i).mHost;
			}

			return temp;

		}
	}

	/**
	 * the Class that represent the data field of the intent
	 */
	public static final class IntentData {
		// private String mIntentType;
		private final String mHost;
		private final String mMimeType;
		private String mPath;
		private final String mScheme;

		public IntentData(String host, String scheme, String mimetype,
				String path) {
			mHost = host;
			mMimeType = mimetype;
			mScheme = scheme;
			setPath(path);

		}

		public String getHost() {
			return mHost;
		}

		public String getMimeType() {
			return mMimeType;
		}

		public String getPath() {
			return mPath;
		}

		public void setPath(String mPath) {
			this.mPath = mPath;
		}

		public String getScheme() {
			return mScheme;
		}

	}

	/**
	 * Returns the package defined in the manifest, if found.
	 * 
	 * @return The package name or null if not found.
	 */
	public String getPackage() {
		return mPackage;
	}

	/**
	 * Returns the versionCode value defined in the manifest, if found, null
	 * otherwise.
	 * 
	 * @return the versionCode or null if not found.
	 */
	public Integer getVersionCode() {
		return mVersionCode;
	}

	/**
	 * Returns the list of activities found in the manifest.
	 * 
	 * @return An array list of activity , or empty if no activity were found.
	 */
	public ArrayList<ActivityComponent> getActivities() {
		return mActivities;
	}

	/**
	 * Returns the list of services found in the manifest.
	 * 
	 * @return An array list of service, or empty if no services were found.
	 */
	public ArrayList<ServiceComponent> getServices() {
		return mServices;
	}

	/**
	 * Returns the list of providers found in the manifest.
	 * 
	 * @return An array list of provider, or empty if no providers were found.
	 */
	public ArrayList<ContentProviderComponent> getProviders() {
		return mProviders;
	}

	/**
	 * Returns the list of receivers found in the manifest.
	 * 
	 * @return An array list of receiver, or empty if no receivers were found.
	 */
	public ArrayList<ReceiverComponent> getReceivers() {
		return mReceivers;
	}

	/**
	 * Returns the list of process names declared by the manifest.
	 */
	public String[] getProcesses() {
		if (mProcesses != null) {
			return mProcesses.toArray(new String[mProcesses.size()]);
		}

		return new String[0];
	}

	/**
	 * Returns the <code>debuggable</code> attribute value or null if it is not
	 * set.
	 */
	public Boolean getDebuggable() {
		return mDebuggable;
	}

	/**
	 * Returns the <code>minSdkVersion</code> attribute, or null if it's not
	 * set.
	 */
	public String getMinSdkVersionString() {
		return mMinSdkVersionString;
	}

	/**
	 * Sets the value of the <code>minSdkVersion</code> attribute.
	 * 
	 * @param minSdkVersion
	 *            the string value of the attribute in the manifest.
	 */
	public void setMinSdkVersionString(String minSdkVersion) {
		mMinSdkVersionString = minSdkVersion;
		if (mMinSdkVersionString != null) {
			try {
				mMinSdkVersion = Integer.parseInt(mMinSdkVersionString);
			} catch (NumberFormatException e) {
				mMinSdkVersion = MIN_SDK_CODENAME;
			}
		}
	}

	/**
	 * Returns the <code>minSdkVersion</code> attribute, or 0 if it's not set or
	 * is a codename.
	 * 
	 * @see #getMinSdkVersionString()
	 */
	public int getMinSdkVersion() {
		return mMinSdkVersion;
	}

	/**
	 * Sets the value of the <code>minSdkVersion</code> attribute.
	 * 
	 * @param targetSdkVersion
	 *            the string value of the attribute in the manifest.
	 */
	public void setTargetSdkVersionString(String targetSdkVersion) {
		if (targetSdkVersion != null) {
			try {
				mTargetSdkVersion = Integer.parseInt(targetSdkVersion);
			} catch (NumberFormatException e) {
				// keep the value at 0.
			}
		}
	}

	/**
	 * Returns the <code>targetSdkVersion</code> attribute, or the same value as
	 * {@link #getMinSdkVersion()} if it was not set in the manifest.
	 */
	public int getTargetSdkVersion() {
		if (mTargetSdkVersion == 0) {
			return getMinSdkVersion();
		}

		return mTargetSdkVersion;
	}

	void addProcessName(String processName) {
		if (mProcesses == null) {
			mProcesses = new TreeSet<String>();
		}

		if (processName.startsWith(":")) {
			mProcesses.add(mPackage + processName);
		} else {
			mProcesses.add(processName);
		}
	}

	void setApplicationPermissionName(String permissionName) {
		mPermission = permissionName;
	}

	public ArrayList<IAbstractComponent> getComponents() {
		mComponents.clear();
		mComponents.addAll(mActivities);
		mComponents.addAll(mServices);
		mComponents.addAll(mProviders);
		mComponents.addAll(mReceivers);
		return mComponents;
	}

	/**
	 * return the component object from its name
	 * */
	public IAbstractComponent getComponents(String name) {
		IAbstractComponent component = null;
		for (IAbstractComponent comp : mComponents) {
			if (comp.getName().equals(name)) {
				component = comp;
			}
		}
		return component;

	}

	/**
	 * @return the component object from its name and type
	 * */
	public IAbstractComponent getComponents(String name, String type) {
		IAbstractComponent component = null;
		if (type.equals(IostsParser.ACTIVITY_TYPE)) {

			for (IAbstractComponent comp : mActivities) {
				if (comp.getName().equals(name)) {
					component = comp;
				}
			}
		} else if (type.equals(IostsParser.SERVICE_TYPE)) {

			for (IAbstractComponent comp : mServices) {
				if (comp.getName().equals(name)) {
					component = comp;
				}
			}
		} else {
			for (IAbstractComponent comp : mComponents) {
				if (comp.getName().equals(name)) {
					component = comp;
				}
			}

		}
		return component;
	}

	public void setNull() {

	}

	public ArrayList<String> getCPauthorities() {

		ArrayList<String> auth = new ArrayList<String>();

		for (ContentProviderComponent provider : mProviders) {
			auth.addAll(provider.getAuthorities());
		}
		return auth;

	}

	public void addActionSet(String action) {
		if (!Actions.A_c.contains(action)) {
			Actions.A_c.add(action);
		}

	}
}