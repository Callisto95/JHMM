package net.callisto.jhmm.ui;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.*;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.*;
import com.googlecode.lanterna.input.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;

import java.io.*;
import java.util.*;
import java.util.function.*;

public class MainMenu {
	public static final TextColor ORANGE = new TextColor.RGB(255, 69, 0);
	public static final Theme     THEME  = SimpleTheme.makeTheme(
		true,
		TextColor.ANSI.DEFAULT,
		TextColor.ANSI.DEFAULT,
		ORANGE,
		TextColor.ANSI.BLACK_BRIGHT,
		ORANGE,
		TextColor.ANSI.DEFAULT,
		TextColor.ANSI.DEFAULT
	);
	
	final Deque<Window>                windows         = new ArrayDeque<>();
	final List<Consumer<TerminalSize>> resizeListeners = new ArrayList<>();
	
	public void showUI() {
		try (
			final Terminal terminal = new DefaultTerminalFactory().createTerminal()
		) {
			final TerminalScreen     screen = new TerminalScreen(terminal);
			final MultiWindowTextGUI gui    = createGui(screen, terminal);
			
			final BasicWindow mainWindow = new BasicWindow("JHMM");
			windows.push(mainWindow);
			mainWindow.setHints(List.of(Window.Hint.FULL_SCREEN));
			mainWindow.setTheme(THEME);
			
			final Panel background = new Panel(new LinearLayout(Direction.HORIZONTAL));
			mainWindow.setComponent(background);
			
			final Button openFile = new Button(
				"choose mod pack", () -> {
				final FileDialog dialog = new FileDialogBuilder()
					.setTitle("Open mod pack")
					.setActionLabel("Open")
					.build();
				dialog.setTheme(THEME);
				windows.push(dialog);
				final File file = dialog.showDialog(gui);
				
				if (file == null) {
					return;
				}
				
				windows.pop();
			}
			);
			background.addComponent(openFile);
			
			gui.addWindow(mainWindow);
			screen.startScreen();
			
			openFile.takeFocus();
			
			gui.waitForWindowToClose(mainWindow);
		} catch (IOException exc) {
			throw new RuntimeException(exc);
		}
	}
	
	private MultiWindowTextGUI createGui(final TerminalScreen screen, final Terminal terminal) {
		final MultiWindowTextGUI gui = new MultiWindowTextGUI(
			screen,
			new DefaultWindowManager(),
			new EmptySpace()
		);
		
		terminal.addResizeListener((_, newSize) -> resizeListeners.forEach(listener -> listener.accept(
			newSize)));
		
		gui.addListener((_, keyStroke) -> {
			if (keyStroke.getKeyType() == KeyType.Escape) {
				windows.pop().close();
				return true;
			}
			
			if (keyStroke.getCharacter() == null) {
				return false;
			}
			
			if (keyStroke.getCharacter() == 'q') {
				windows.pop().close();
				return true;
			}
			
			return false;
		});
		
		return gui;
	}
}
