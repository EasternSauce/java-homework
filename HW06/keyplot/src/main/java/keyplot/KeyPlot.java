package keyplot;
import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.awt.geom.Line2D;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

class Coord{
	public Coord(int x, int y){
		this.x = x;
		this.y = y;
	}
	public int x;
	public int y;
};

public class KeyPlot{
	public static void main(String[] args){
		Map<String, Coord> key_coords = new HashMap<String, Coord>();

		key_coords.put("Q", new Coord(48+93*0,48));
		key_coords.put("W", new Coord(48+93*1,48));
		key_coords.put("E", new Coord(48+93*2,48));
		key_coords.put("R", new Coord(48+93*3,48));
		key_coords.put("T", new Coord(48+93*4,48));
		key_coords.put("Y", new Coord(48+93*5,48));
		key_coords.put("U", new Coord(48+93*6,48));
		key_coords.put("I", new Coord(48+93*7,48));
		key_coords.put("O", new Coord(48+93*8,48));
		key_coords.put("P", new Coord(48+93*9,48));
		key_coords.put("A", new Coord(86+93*0,134));
		key_coords.put("S", new Coord(86+93*1,134));
		key_coords.put("D", new Coord(86+93*2,134));
		key_coords.put("F", new Coord(86+93*3,134));
		key_coords.put("G", new Coord(86+93*4,134));
		key_coords.put("H", new Coord(86+93*5,134));
		key_coords.put("J", new Coord(86+93*6,134));
		key_coords.put("K", new Coord(86+93*7,134));
		key_coords.put("L", new Coord(86+93*8,134));
		key_coords.put("Z", new Coord(138+93*0,219));
		key_coords.put("X", new Coord(138+93*1,219));
		key_coords.put("C", new Coord(138+93*2,219));
		key_coords.put("V", new Coord(138+93*3,219));
		key_coords.put("B", new Coord(138+93*4,219));
		key_coords.put("N", new Coord(138+93*5,219));
		key_coords.put("M", new Coord(138+93*6,219));


		int image_w = 1029;
		int image_h = 353;
		String filename = "keystrokes.csv";
		Vector<Coord> points = new Vector<Coord>();
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String s;
			while((s = br.readLine()) != null){
				Coord pos = key_coords.get(s);
				points.add(pos);
			}
			br.close();
		}catch(FileNotFoundException e){
				System.out.println("Positions file not found");
		}catch(IOException e){
				System.out.println("I/O Problem with positions file");
		}

		try{
			BufferedImage image = new BufferedImage(image_w, image_h, BufferedImage.TYPE_INT_ARGB);
			image = ImageIO.read(new File("./keyboard.png"));
			Graphics2D gr = image.createGraphics();

			gr.setPaint(Color.black);

			for(int i = 0; i < points.size()-1; i++){
				gr.draw(new Line2D.Double(points.elementAt(i).x,points.elementAt(i).y,points.elementAt(i+1).x,points.elementAt(i+1).y));
			}

			if(ImageIO.write(image, "PNG", new File("./keyplot.png"))){
				System.out.println("Successfully created image!");
			}
			else{
				System.out.println("Problem occured");
			}
		}
		catch(IOException e){
				System.out.println("I/O Problem with image file");
		}


	}
}
