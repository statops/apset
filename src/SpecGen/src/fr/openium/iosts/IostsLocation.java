package fr.openium.iosts;

public class IostsLocation {
	private String mLabel;
	private final String mID;
	private String mComponent;

	public IostsLocation(String name) {
		mID = name;

	}

	public String getName() {
		return mID;
	}

	public String getLabel() {
		return mLabel;
	}

	public void setLabel(String mLabel) {
		this.mLabel = mLabel;
	}

	public String getComponentName() {
		return mComponent;
	}

	public String setComponentName(String name) {
		mComponent = name;
		return mComponent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mID == null) ? 0 : mID.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof IostsLocation)) {
			return false;
		}
		IostsLocation other = (IostsLocation) obj;
		if (mID == null) {
			if (other.mID != null) {
				return false;
			}
		} else if (!mID.equals(other.mID)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */

}