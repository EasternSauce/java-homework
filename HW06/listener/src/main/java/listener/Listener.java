package listener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class Listener implements NativeKeyListener, NativeMouseInputListener {

	String pressed = "";
	int distance = 0, clicks = 0;
	int lastx = 0, lasty = 0;
	Timer t = new Timer();
	List<String> positions = new LinkedList<String>();
	List<String> keystrokes = new LinkedList<String>();

	public void nativeKeyPressed(NativeKeyEvent e) {
		String key = NativeKeyEvent.getKeyText(e.getKeyCode());
		if (pressed.endsWith("[Left Control]") && key.toUpperCase().equals("Q")) {
			print();
			
		}

		if (key.length() < 3) {
			pressed = pressed + key;
		} else if (key.equals("Space")) {
			pressed = pressed + " ";
		} else {
			pressed = pressed + "[" + key + "]";
		}
		if(key.length() == 1 && key.charAt(0) >= 'A' && key.charAt(0) <= 'Z') keystrokes.add(key);
	}

	public void nativeMouseClicked(NativeMouseEvent e) {
		clicks++;
	}

	public void nativeMouseMoved(NativeMouseEvent e) {
		distance += Math.sqrt(Math.pow(e.getX() - lastx, 2) + Math.pow(e.getY() - lasty, 2));
		lastx = e.getX();
		lasty = e.getY();
		positions.add(e.getX() + "," + e.getY());
	}

	public void print() {
		try {
			FileWriter fstream = new FileWriter("summary.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(pressed + "\n\n");
			out.write("Clicks: " + clicks + ", distance: " + distance + " pixel\n");
			out.write("Test duration: " + t.timeElapsed() / 1000.0 + "s");
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		try {
			FileWriter fstream = new FileWriter("positions.csv");
			BufferedWriter out = new BufferedWriter(fstream);

			for (String pos : positions) {
				out.write(pos + "\n");
			}
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		try {
			FileWriter fstream = new FileWriter("keystrokes.csv");
			BufferedWriter out = new BufferedWriter(fstream);

			for (String key : keystrokes) {
				out.write(key + "\n");
			}
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		
		System.out.println("Saved logs and cleared history.");
		positions.clear();
	}

	public void nativeKeyTyped(NativeKeyEvent e) { }
	public void nativeKeyReleased(NativeKeyEvent e) { }
	public void nativeMousePressed(NativeMouseEvent e) { }
	public void nativeMouseReleased(NativeMouseEvent e) { }
	public void nativeMouseDragged(NativeMouseEvent e) { }
}
