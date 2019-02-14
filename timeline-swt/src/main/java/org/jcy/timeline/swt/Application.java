package org.jcy.timeline.swt;

import java.io.File;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;
import org.jcy.timeline.swt.git.GitTimelineFactory;

public class Application {

	private static final File BASE_DIRECTORY = new File(System.getProperty("user.home"));
	private static final String URI = "https://github.com/Annoyer/jenkins-web-test.git";
	private static final String REPOSITORY_NAME = "jenkins-web-test";

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO - implement Application.main
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param display
	 */
	private static Shell createShell(Display display) {
		// TODO - implement Application.createShell
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param display
	 * @param shell
	 */
	private static void spinUiLoop(Display display, Shell shell) {
		// TODO - implement Application.spinUiLoop
		throw new UnsupportedOperationException();
	}

	private static GitTimelineFactory createTimelineFactory() {
		// TODO - implement Application.createTimelineFactory
		throw new UnsupportedOperationException();
	}

}