package net.callisto.jhmm.ui;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.*;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import net.callisto.jhmm.*;

import java.io.*;
import java.util.*;

public class ComponentSelection implements ManifestUI {


	public void showUI(final Manifest manifest) {
		// ! TODO: fixme
			// final Panel mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));
			// mainWindow.setComponent(mainPanel);
			//
			// // --- init done ---
			//
			// mainPanel.addComponent(
			// 	1,
			// 	new Label(String.format("%s, version %d", manifest.name, manifest.version))
			// );
			// mainPanel.addComponent(new Label(manifest.description));
			//
			// mainPanel.addComponent(new EmptySpace());
			//
			// mainPanel.addComponent(new Label("Options:"));
			//
			// manifest.options.forEach(option -> {
			// 	mainPanel.addComponent(new Label(option.getDisplayName()));
			// 	final ComboBox<String> comboBox = new ComboBox<>();
			//
			// 	// if (option.subOptions.isEmpty()) {
			// 	// 	comboBox.addItem(SubOption.ENABLED);
			// 	// } else {
			// 	// 	option.subOptions.forEach(comboBox::addItem);
			// 	// }
			// 	option.subOptions.forEach(subOption -> comboBox.addItem(subOption.getDisplayName()));
			//
			// 	mainPanel.addComponent(comboBox);
			// 	mainPanel.addComponent(new EmptySpace());
			//
			// 	comboBox.addListener((selectedIndex, previousSelection, changedByUserInteraction) -> {
			// 		final SubOption selectedSubOption = option.subOptions.get(selectedIndex);
			// 		final SubOption previousSubOption = option.subOptions.get(previousSelection);
			//
			// 		if (SubOption.DISABLED.equals(selectedSubOption)) {
			// 			option.selected            = false;
			// 		} else {
			// 			option.selected            = true;
			// 			selectedSubOption.selected = true;
			// 		}
			// 		previousSubOption.selected = false;
			// 	});
			// });
			//
			// // --- final steps ---
			//
			// mainPanel.addComponent(new EmptySpace());
			// final Button closeButton = new Button("close");
			// closeButton.addListener(button -> mainWindow.close());
			// mainPanel.addComponent(closeButton);
	}
}
