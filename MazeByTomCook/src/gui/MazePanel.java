package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

/**
 * Add functionality for double buffering to an AWT Panel class.
 * Used for drawing a maze.
 * 
 * @author Peter Kemper
 *
 */
public class MazePanel extends Panel implements P5PanelF21  {
	private static final long serialVersionUID = 2787329533730973905L;
	/* Panel operates a double buffer see
	 * http://www.codeproject.com/Articles/2136/Double-buffer-in-standard-Java-AWT
	 * for details
	 */
	// bufferImage can only be initialized if the container is displayable,
	// uses a delayed initialization and relies on client class to call initBufferImage()
	// before first use
	private Image bufferImage;  
	private Graphics2D graphics; // obtained from bufferImage, 
	// graphics is stored to allow clients to draw on the same graphics object repeatedly
	// has benefits if color settings should be remembered for subsequent drawing operations
	private Graphics gc;
	private Font markerFont;
	
	/**
	 * Constructor. Object is not focusable.
	 */
	public MazePanel() {
		setFocusable(false);
		bufferImage = null; // bufferImage initialized separately and later
		graphics = null;	// same for graphics
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	/**
	 * Method to draw the buffer image on a graphics object that is
	 * obtained from the superclass. 
	 * Warning: do not override getGraphics() or drawing might fail. 
	 */
	public void update() {
		paint(getGraphics());
	}
	
	/**
	 * Draws the buffer image to the given graphics object.
	 * This method is called when this panel should redraw itself.
	 * The given graphics object is the one that actually shows 
	 * on the screen.
	 */
	@Override
	public void paint(Graphics g) {
		if (null == g) {
			System.out.println("MazePanel.paint: no graphics object, skipping drawImage operation");
		}
		else {
			g.drawImage(bufferImage,0,0,null);	
		}
	}

	/**
	 * Obtains a graphics object that can be used for drawing.
	 * This MazePanel object internally stores the graphics object 
	 * and will return the same graphics object over multiple method calls. 
	 * The graphics object acts like a notepad where all clients draw 
	 * on to store their contribution to the overall image that is to be
	 * delivered later.
	 * To make the drawing visible on screen, one needs to trigger 
	 * a call of the paint method, which happens 
	 * when calling the update method. 
	 * @return graphics object to draw on, null if impossible to obtain image
	 */
	public Graphics getBufferGraphics() {
		// if necessary instantiate and store a graphics object for later use
		if (null == graphics) { 
			if (null == bufferImage) {
				bufferImage = createImage(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
				if (null == bufferImage)
				{
					System.out.println("Error: creation of buffered image failed, presumedly container not displayable");
					return null; // still no buffer image, give up
				}		
			}
			graphics = (Graphics2D) bufferImage.getGraphics();
			if (null == graphics) {
				System.out.println("Error: creation of graphics for buffered image failed, presumedly container not displayable");
			}
			else {
				// System.out.println("MazePanel: Using Rendering Hint");
				// For drawing in FirstPersonDrawer, setting rendering hint
				// became necessary when lines of polygons 
				// that were not horizontal or vertical looked ragged
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			}
		}
		return graphics;
	}
	
    public void addLine(int startX, int startY, int endX, int endY) {
    	Graphics g = this.getBufferGraphics();
    	g.drawLine(startX, startY, endX, endY);
		g.drawLine(startX, startY, endX, endY);
    }

	@Override
	public void commit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isOperational() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setColor(int rgb) {
		Graphics g = this.getBufferGraphics();
		Color c = new Color(rgb);
		g.setColor(c);
		
	}
	
	/**
	 * Provides an alternate way to set the color used for drawing
	 * @param r the red value 0-255
	 * @param g the green value 0-255
	 * @param b the blue value 0-255
	 */
	public void setColor(int r, int g, int b) {
		Graphics gc = this.getBufferGraphics();
		Color c = new Color(r, g, b);
		gc.setColor(c);
	}
	
	public void setColor(float r, float g, float b, float a) {
		Graphics gc = this.getBufferGraphics();
		Color c = new Color(r, g, b, a);
		gc.setColor(c);
	}

	@Override
	public int getColor() {
		Graphics g = this.getBufferGraphics();
		Color col = g.getColor();
		return col.getRGB();
	}

	@Override
	public void addBackground(float percentToExit) {

	}

	@Override
	public void addFilledRectangle(int x, int y, int width, int height) {
		Graphics g = this.getBufferGraphics();
		g.fillRect(x, y, width, height);
		
	}

	@Override
	public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		Graphics g = this.getBufferGraphics();
		g.fillPolygon(xPoints, yPoints, nPoints);
		
	}

	@Override
	public void addPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		Graphics g = this.getBufferGraphics();
		g.drawPolygon(xPoints, yPoints, nPoints);
		
	}

	@Override
	public void addFilledOval(int x, int y, int width, int height) {
		Graphics g = this.getBufferGraphics();
		g.fillOval(x, y, width, height);
		
	}

	@Override
	public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		Graphics g = this.getBufferGraphics();
		g.drawArc(x, y, width, height, startAngle, arcAngle);
		
	}

	@Override
	public void addMarker(float x, float y, String str) {
		Graphics2D g = (Graphics2D) this.getBufferGraphics();
		GlyphVector gv = markerFont.createGlyphVector(g.getFontRenderContext(), str);
		 Rectangle2D rect = gv.getVisualBounds();
	        // need to update x, y by half of rectangle width, height
	        // to serve as x, y coordinates for drawing a GlyphVector
	        x -= rect.getWidth() / 2;
	        y += rect.getHeight() / 2;
	        
	        g.drawGlyphVector(gv, x, y);
	}

	@Override
	public void setRenderingHint(P5RenderingHints hintKey, P5RenderingHints hintValue) {
		Graphics2D g = (Graphics2D) this.getBufferGraphics();
		g.setRenderingHint(convertP5toGraphicHint(hintKey), hintValue);
		
	}
	
	private RenderingHints.Key convertP5toGraphicHint(P5RenderingHints hintKey) {
		RenderingHints.Key result = null;
		switch (hintKey) {
		case KEY_ANTIALIASING:
			result = RenderingHints.KEY_ANTIALIASING;
			break;
		case KEY_INTERPOLATION:
			result = RenderingHints.KEY_INTERPOLATION;
			break;
		case KEY_RENDERING:
			result = RenderingHints.KEY_RENDERING;
			break;
		case VALUE_ANTIALIAS_ON:
			result = (Key) RenderingHints.VALUE_ANTIALIAS_ON;
			break;
		case VALUE_INTERPOLATION_BILINEAR:
			result = (Key) RenderingHints.VALUE_INTERPOLATION_BILINEAR;
			break;
		case VALUE_RENDER_QUALITY:
			result = (Key) RenderingHints.VALUE_RENDER_QUALITY;
			break;		
		}
		return result;
	}

}
