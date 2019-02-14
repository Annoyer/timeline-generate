package org.jcy.timeline.swing.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

public class Resources {

	public static final Color WHITE = new Color(255, 255, 255);

	/**
	 * Change the font size in the component.
	 *
	 * @param component
	 * @param increment
	 */
	public static void changeFontSize(Component component, int increment) {
		Font baseFont = component.getFont();
		Font font = createFrom(baseFont, increment);
		component.setFont(font);
	}

	/**
	 * Create new font.
	 *
	 * @param baseFont
	 * @param increment
	 */
	static Font createFrom(Font baseFont, int increment) {
		return new Font(baseFont.getName(), baseFont.getStyle(), baseFont.getSize() + increment);
	}

}