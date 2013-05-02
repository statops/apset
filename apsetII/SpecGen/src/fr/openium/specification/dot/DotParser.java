package fr.openium.specification.dot;

import java.io.File;

/**
 * Class that will be used to read our IOSTS dot file The dot file contains
 * information about the structure of the IOSTS, but it does not provide
 * sufficient information about its data.
 */
public class DotParser {

	/**
	 * 
	 * */

	public DotParser() {

	}

	public boolean parse(File file) {

		return false;
	}

	/**
	 * label of the transition indicating the action
	 * */
	public class DotAction {
		public String mValue;

		public DotAction(String value) {
			mValue = value;

		}

	}

	/**
	 * label of the transition indicating the guard
	 * */
	public class DotGuard {
		public String mValue;

		public DotGuard(String value) {
			mValue = value;

		}

	}

	/**
	 * label of the transition indicating function that updated the internal
	 * variables
	 * */
	public class DotFunction {
		public String mValue;

		public DotFunction(String value) {
			mValue = value;

		}

	}

	/**
	 * SCAN and ANALYZE the DOTFILE before performing operations.
	 **/

	public class DotScanner {

	}

}
