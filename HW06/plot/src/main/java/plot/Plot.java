package plot;
import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.awt.geom.Line2D;
import java.util.Vector;

class Coord{
	public int x;
	public int y;
};

public class Plot{
	public static void main(String[] args){
		int screen_w = 1366;
		int screen_h = 768;
		String filename = "positions.csv";
		Vector<Coord> points = new Vector<Coord>();
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String s;
			while((s = br.readLine()) != null){
				Coord pos = new Coord();
				String fields[] = s.split(",");
				pos.x = Integer.parseInt(fields[0]);
				pos.y = Integer.parseInt(fields[1]);
				points.add(pos);
			}
			br.close();
		}catch(FileNotFoundException e){
				System.out.println("Positions file not found");
		}catch(IOException e){
				System.out.println("I/O Problem with positions file");
		}

		try{
			BufferedImage image = new BufferedImage(screen_w, screen_h, BufferedImage.TYPE_INT_ARGB);
			Graphics2D gr = image.createGraphics();

			gr.setPaint(Color.black);

			for(int i = 0; i < points.size()-1; i++){
				gr.draw(new Line2D.Double(points.elementAt(i).x,points.elementAt(i).y,points.elementAt(i+1).x,points.elementAt(i+1).y));
			}

			if(ImageIO.write(image, "PNG", new File("./plot.png"))){
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
