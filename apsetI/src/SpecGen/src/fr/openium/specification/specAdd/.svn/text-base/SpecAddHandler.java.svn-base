package fr.openium.specification.specAdd;

import java.io.File;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.openium.components.ContentProviderComponent;
import fr.openium.specification.config.Extra;
import fr.openium.specification.specAdd.SpecAddData.SpecAddIntent;

/** @author Stassia */

public class SpecAddHandler extends DefaultHandler {
	private final SpecAddData mSpecAddData;
	private int mElementLevel = 0;
	private int mValidLevel = 0;
	private String mPackageNAme;
	private final static int LEVEL_SPEC = 0;
	private SpecAddComponent mComponent;
	private SpecAddIntent mIntent;

	private final static int LEVEL_INSIDE_SPEC = 1;
	// inside Spec: There are just activity and service component

	private final static int LEVEL_INSIDE_COMPONENT = 2;
	// inside comp: there are the intent and Content provider

	private final static int LEVEL_INSIDE_INTENT = 3;

	// inside intent: there are the targetComponent and exta information

	public SpecAddHandler(File specAddXmlFile, SpecAddData specAddData) {
		mSpecAddData = specAddData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (mValidLevel == mElementLevel) {
			mValidLevel--;
		}
		mElementLevel--;

		if (mValidLevel == mElementLevel) {
			switch (mValidLevel) {
			case LEVEL_INSIDE_SPEC:
				mSpecAddData.setComponents(mComponent);
				mComponent = null;
				break;
			case LEVEL_INSIDE_COMPONENT:
				mSpecAddData.setSpecAddIntent(mIntent);
				mIntent = null;
			default:
				break;
			}

		}

		// super.endElement(uri, localName, qName);
		/*
		 * System.out.println("End Element " + k + "  :");
		 * System.out.println("qName " + ":" + qName);
		 * System.out.println("localName :" + localName); k++;
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (mValidLevel == mElementLevel) {
			String value;
			switch (mValidLevel) {
			case LEVEL_SPEC:
				if (SpecAdd.SPECIFICATION.equals(qName)) {
					mPackageNAme = getAttributeValue(attributes,
							SpecAdd.PACKAGE);
					mSpecAddData.setPackageName(mPackageNAme);
					mValidLevel++;
				}
				break;
			case LEVEL_INSIDE_SPEC:

				if (SpecAdd.ACTIVITY.equals(qName)
						|| SpecAdd.SERVICE.equals(qName)) {
					value = getAttributeValue(attributes, SpecAdd.NAME);
					if (value != null) {
						mComponent = new SpecAddComponent(mPackageNAme + value);
					}
					mValidLevel++;
				}
				break;
			case LEVEL_INSIDE_COMPONENT:
				if ((mComponent != null) && SpecAdd.EXTRA.equals(qName)) {
					String extraKey = getAttributeValue(attributes,
							SpecAdd.NAME);
					String extraType = getAttributeValue(attributes,
							SpecAdd.TYPE);
					mComponent.setExtra(new Extra(extraKey, extraType));
				}
				if ((mComponent != null) && SpecAdd.INTENT.equals(qName)) {
					mComponent.setHasInternalIntent(true);
				}
				if ((mComponent != null)
						&& SpecAdd.CONTENT_PROVIDER.equals(qName)) {
					mComponent.setAccessToCP(true);
					String name = getAttributeValue(attributes, SpecAdd.NAME);
					String auth = getAttributeValue(attributes,
							SpecAdd.AUTHORITY);
					ContentProviderComponent cp = new ContentProviderComponent(
							name);
					cp.setAuthorities(auth);
					mComponent.setContentProviders(cp);
				}
				mValidLevel++;
				break;
			case LEVEL_INSIDE_INTENT:
				if (mComponent != null && SpecAdd.TARGETCOMPONENT.equals(qName)) {
					mIntent = new SpecAddIntent();
					mIntent.setComponentSource(mComponent);
					value = getAttributeValue(attributes, SpecAdd.NAME);
					mIntent.setTargetComponent(new SpecAddComponent(
							mPackageNAme + value));
					mValidLevel++;
				}
				if (mComponent != null && SpecAdd.EXTRA.equals(qName)) {
					String extraKey = getAttributeValue(attributes,
							SpecAdd.NAME);
					String extraType = getAttributeValue(attributes,
							SpecAdd.TYPE);
					mIntent.setExtra(new Extra(extraKey, extraType));
					mValidLevel++;
				}

				break;
			}

		}
		mElementLevel++;

	}

	public SpecAddData getSpecAddData() {
		return mSpecAddData;
	}

	private String getAttributeValue(Attributes attributes, String attributeName) {
		int count = attributes.getLength();
		for (int i = 0; i < count; i++) {
			if (attributeName.equals(attributes.getLocalName(i))
					&& attributes.getURI(i).length() == 0) {
				return attributes.getValue(i);
			}
		}

		return null;
	}
}
