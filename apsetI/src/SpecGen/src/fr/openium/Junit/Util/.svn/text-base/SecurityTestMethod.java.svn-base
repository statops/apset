package fr.openium.Junit.Util;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

/**
 * RŽutilisation des methodes pour le test
 * */
public class SecurityTestMethod {

	public static void RetrieveView(ViewGroup Vg, ArrayList<View> views) {

		for (int i = 0; i < Vg.getChildCount(); i++) {
			try {
				ViewGroup child = (ViewGroup) Vg.getChildAt(i);
				RetrieveView(child, views);
			} catch (ClassCastException cs) {
				views.add(Vg.getChildAt(i));
			}
		}

	}

	public static void getTextView(ArrayList<View> listView) {
		ArrayList<View> listViewTemp = new ArrayList<View>();

		if (!(listView.size() > 0))
			return;
		for (int i = 0; i < listView.size(); i++) {
			if ((listView.get(i) instanceof TextView)) {
				listViewTemp.add(listView.get(i));
			}
		}

		listView.clear();
		listView.addAll(listViewTemp);
	}

	public static boolean activeViewExist(Activity activity,
			ArrayList<View> views) {
		Window windowTemp = activity.getWindow();
		View viewTemp = windowTemp.getDecorView();
		viewTemp = viewTemp.getRootView();
		ViewGroup Vg = (ViewGroup) viewTemp;
		RetrieveView(Vg, views);
		getTextView(views);
		/**
		 * A rajouter impŽrativement quand le package de Android TestCase sera
		 * ajoutŽ
		 */
		// assertTrue(views.size() > 0);
		boolean verdict = false;
		for (int i = 0; i < views.size(); i++) {
			boolean isEnable = views.get(i).isEnabled();
			boolean isNull = ((TextView) views.get(i)).getText().equals(null);
			boolean isEmpty = ((TextView) views.get(i)).getText().equals("");
			boolean isVisible = (views.get(i)).isShown();
			if (isEnable && !isNull && !isEmpty && isVisible) {
				verdict = true;
			}
		}
		return verdict;
	}

	public static boolean hasErrorMessage(Activity activity,
			ArrayList<View> views) {
		Window windowTemp = activity.getWindow();
		View viewTemp = windowTemp.getDecorView();
		viewTemp = viewTemp.getRootView();
		ViewGroup Vg = (ViewGroup) viewTemp;
		RetrieveView(Vg, views);
		getTextView(views);

		/**
		 * A rajouter impŽrativement quand le package de Android TestCase sera
		 * ajoutŽ
		 */
		// assertTrue(views.size() > 0);

		for (int i = 0; i < views.size(); i++) {
			boolean isEnable = views.get(i).isEnabled();
			boolean isNull = ((TextView) views.get(i)).getText().equals(null);
			boolean isEmpty = ((TextView) views.get(i)).getText().equals("");
			boolean isVisible = (views.get(i)).isShown();
			boolean hasErrorMessage = ((TextView) views.get(i)).getText()
					.equals("error");
			if (isEnable && !isNull && !isEmpty && isVisible && hasErrorMessage) {
				return true;
			}
		}
		return false;
	}

}
