package net.callisto.jhmm;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.*;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;

import java.io.*;
import java.util.*;

public class UI {
	public static final Theme THEME = SimpleTheme.makeTheme(
		true,
		TextColor.ANSI.DEFAULT,
		TextColor.ANSI.DEFAULT,
		TextColor.ANSI.MAGENTA,
		TextColor.ANSI.GREEN,
		new TextColor.RGB(255, 69, 0),
		TextColor.ANSI.DEFAULT,
		TextColor.ANSI.DEFAULT
	);
	
	public static void showUI(final Manifest manifest) throws IOException {
		try (
			final Terminal terminal = new DefaultTerminalFactory().createTerminal()
		) {
			final TerminalScreen     screen = new TerminalScreen(terminal);
			final MultiWindowTextGUI gui    = createGui(screen, terminal);
			
			final BasicWindow mainWindow = new BasicWindow("JHMM");
			mainWindow.setHints(List.of(Window.Hint.FULL_SCREEN));
			mainWindow.setTheme(THEME);
			
			final Panel mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));
			mainWindow.setComponent(mainPanel);
			
			// --- init done ---
			
			mainPanel.addComponent(
				1,
				new Label(String.format("%s, version %d", manifest.name, manifest.version))
			);
			mainPanel.addComponent(2, new Label(manifest.description));
			
			mainPanel.addComponent(3, new EmptySpace());
			
			mainPanel.addComponent(4, new Label("Options:"));
			
			final CheckBoxList<Option> optionCheckBoxList = new CheckBoxList<>();
			manifest.options.forEach(optionCheckBoxList::addItem);
			
			mainPanel.addComponent(5, optionCheckBoxList);
			mainPanel.addComponent(6, new EmptySpace());
			mainPanel.addComponent(7, new Label("SubOptions:"));
			
			final Panel subOptionsPanel = new Panel(new LinearLayout(Direction.VERTICAL));
			
			final HashMap<Option, LabeledComponent> subOptionMap = new HashMap<>();
			
			optionCheckBoxList.addListener((itemIndex, checked) -> {
				final Option option        = manifest.options.get(itemIndex);
				final int    relativeIndex = itemIndex * 2;
				
				if (option.subOptions.isEmpty()) {
					return;
				}
				
				if (checked) {
					final RadioBoxList<SubOption> subOptionRadioBoxList = new RadioBoxList<>();
					option.subOptions.forEach(subOptionRadioBoxList::addItem);
					subOptionRadioBoxList.setCheckedItemIndex(0);
					
					final Label label = new Label(option.name);
					subOptionsPanel.addComponent(relativeIndex, label);
					subOptionsPanel.addComponent(relativeIndex + 1, subOptionRadioBoxList);
					subOptionMap.put(option, new LabeledComponent(label, subOptionRadioBoxList));
				} else {
					final LabeledComponent labeledComponent = subOptionMap.get(option);
					subOptionsPanel.removeComponent(labeledComponent.label());
					subOptionsPanel.removeComponent(labeledComponent.component());
				}
			});
			
			mainPanel.addComponent(8, subOptionsPanel);
			
			// --- final steps ---
			
			mainPanel.addComponent(9, new EmptySpace());
			final Button closeButton = new Button("close");
			closeButton.addListener(button -> mainWindow.close());
			mainPanel.addComponent(10, closeButton);
			
			gui.addWindow(mainWindow);
			screen.startScreen();
			
			gui.waitForWindowToClose(mainWindow);
		}
	}
	
	private static MultiWindowTextGUI createGui(
		final TerminalScreen screen,
		final Terminal terminal
	) {
		final MultiWindowTextGUI gui = new MultiWindowTextGUI(
			screen,
			new DefaultWindowManager(),
			new EmptySpace()
		);
		
		terminal.addResizeListener((terminal1, newSize) -> {
			try {
				gui.updateScreen();
			} catch (IOException e) {
				// this should never happen
			}
		});
		return gui;
	}
}
