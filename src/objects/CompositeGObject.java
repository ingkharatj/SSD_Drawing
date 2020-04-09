package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class CompositeGObject extends GObject {

	private List<GObject> gObjects;

	public CompositeGObject() {
		super(0, 0, 0, 0);
		gObjects = new ArrayList<GObject>();
	}

	public void add(GObject gObject) {

		gObjects.add(gObject);

	}

	public void remove(GObject gObject) {

		gObjects.remove(gObject);

	}

	@Override
	public void move(int dX, int dY) {


		this.x = this.x + dX;
		this.y = this.y + dY;

		for (GObject i : gObjects){
			i.move(dX, dY);
		}

	}
	
	public void recalculateRegion() {
		GObject all_GObject = gObjects.get(0);

		int x_Max = 0;
		int x_Min = all_GObject.x + all_GObject.width;

		int y_Max = 0;
		int y_Min = all_GObject.y + all_GObject.height;

		for (GObject gObject : gObjects){

			if (gObject.x < x_Min){
				x_Min = gObject.x;
			}
			if (gObject.x + gObject.width > x_Max){
				x_Max = gObject.x + gObject.width;
			}
			if (gObject.y < y_Min){
				y_Min = gObject.y;
			}
			if (gObject.y + gObject.height > y_Max){
				y_Max = gObject.y + gObject.height;
			}

		}
		this.x = x_Min;
		this.y = y_Min;
		this.width = x_Max - x_Min;
		this.height = y_Max - y_Min;
	}

	@Override
	public void paintObject(Graphics g) {
		
		for (GObject gObject : gObjects){
			gObject.paintObject(g);
		}
	}

	@Override
	public void paintLabel(Graphics g) {

		g.drawString("Group of paint", x, y+height+15);
		

	}
	
}
