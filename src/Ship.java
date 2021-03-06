/**
 * @author Ariana Fairbanks
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Ship extends Polygon implements KeyListener
{
	static boolean forward;
	static boolean backward;
	static boolean turningRight;
	static boolean turningLeft;
	private boolean shoot;
	private boolean mustRelease;
	private ArrayList<Bullet> shots;
	@SuppressWarnings("unused")
	private Point front;
	private static Point[] shipShape = { new Point(0, 0), new Point(0, 20), new Point(30, 10) };

	public Ship(Point inPosition, double inRotation)
	{
		super(shipShape, inPosition, inRotation);
		forward = false;
		backward = false;
		turningRight = false;
		turningLeft = false;
		shoot = false;
		mustRelease = false;
		shots = new ArrayList<Bullet>();
		front = inPosition;
	}
	
	public void resetShip()
	{
		forward = false;
		backward = false;
		turningRight = false;
		turningLeft = false;
		shoot = false;
		mustRelease = false;
		shots = new ArrayList<Bullet>();
		rotation = 270;
	}

	public void paint(Graphics brush, Color color) 
	{
		Point[] points = this.getPoints();
		int npts = points.length;
		this.front = points[0];
		int[] xValues = new int[npts];
		int[] yValues = new int[npts];
		for(int i = 0; i < points.length; i++)
		{
			xValues[i] = (int) points[i].x;
			yValues[i] = (int) points[i].y;
		}
		brush.setColor(Color.black);
		brush.fillPolygon(xValues, yValues, npts);
		brush.setColor(color);
		brush.drawPolygon(xValues, yValues, npts);
	}

	public void move() 
	{	
        if(forward) 
        {
            position.x += 3 * Math.cos(Math.toRadians(rotation));
            position.y += 3 * Math.sin(Math.toRadians(rotation));
        }
        if(backward) 
        {
            position.x -= 3 * Math.cos(Math.toRadians(rotation));
            position.y -= 3 * Math.sin(Math.toRadians(rotation));
        }
        if(turningRight) 
        {
            rotate(2);
        }
        if(turningLeft) 
        {
            rotate(-2);
        }
        if(shoot) 
        {
            if(!mustRelease)
            {
            	Bullet start = new Bullet(getPoints()[2].clone(), rotation);
            	start.center.x -= 2;
            	shots.add(start);
            }
            mustRelease = true;
            shoot = false;
        }

		if(position.x > Asteroids.SCREEN_WIDTH) 
		{
			position.x -= Asteroids.SCREEN_WIDTH;
		} 
		else if(position.x < 0)
		{
			position.x += Asteroids.SCREEN_WIDTH;
		}
		if(position.y > Asteroids.SCREEN_HEIGHT) 
		{
			position.y -= Asteroids.SCREEN_HEIGHT;
		} 
		else if(position.y < 0) 
		{
			position.y += Asteroids.SCREEN_HEIGHT;
		}
	}
	
	public ArrayList<Bullet> getBullets()
	{
		return shots;
	}
	
	public double getRotation()
	{
		return rotation;
	}

	public void keyPressed(KeyEvent e) 
	{
		if(!Asteroids.limbo && !Asteroids.paused)
		{
			if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) 
			{
				forward = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) 
			{
				backward = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) 
			{
				turningLeft = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) 
			{
				turningRight = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE) 
			{
				shoot = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) 
		{
			if(Asteroids.limbo)
			{
				Asteroids.reset = true;
			}
			else
			{
				if(Asteroids.paused)
				{
					Asteroids.paused = false;
				}
				else
				{
					Asteroids.paused = true;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(!Asteroids.limbo && !Asteroids.paused)
		{
			if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) 
			{
				forward = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) 
			{
				backward = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) 
			{
				turningLeft = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) 
			{
				turningRight = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE) 
			{
				mustRelease = false;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{

	}

}


