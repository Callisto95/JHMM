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
			mainPanel.addComponent(new Label(manifest.description));
			
			mainPanel.addComponent(new EmptySpace());
			
			mainPanel.addComponent(new Label("Options:"));
			
			manifest.options.forEach(option -> {
				mainPanel.addComponent(new Label(option.toString()));
				final ComboBox<SubOption> comboBox = new ComboBox<>();
				comboBox.addItem(SubOption.DISABLED);
				option.subOptions.forEach(comboBox::addItem);
				mainPanel.addComponent(comboBox);
				mainPanel.addComponent(new EmptySpace());
			});
			
			// --- final steps ---
			
			mainPanel.addComponent(new EmptySpace());
			final Button closeButton = new Button("close");
			closeButton.addListener(button -> mainWindow.close());
			mainPanel.addComponent(closeButton);
			
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
