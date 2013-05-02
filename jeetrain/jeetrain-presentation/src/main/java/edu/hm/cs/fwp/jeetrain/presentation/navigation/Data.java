package edu.hm.cs.fwp.jeetrain.presentation.navigation;

import java.io.Serializable;

/**
 * Simple {@code Domain Object}.
 * @author theism
 */
public class Data implements Serializable {

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
