/**
 * 
 */
package gui;

import generation.Order;
import gui.Robot.Direction;

import static org.junit.Assert.assertEquals;

import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFrame;


/**
 * This class is a wrapper class to startup the Maze game as a Java application
 * 
 * This code is refactored code from Maze.java by Paul Falstad, www.falstad.com, Copyright (C) 1998, all rights reserved
 * Paul Falstad granted permission to modify and use code for teaching purposes.
 * Refactored by Peter Kemper
 * 
 * TODO: use logger for output instead of Sys.out
 */
public class MazeApplication extends JFrame {

	// not used, just to make the compiler, static code checker happy
	private static final long serialVersionUID = 1L;
	
	// developments vs production version
	// for development it is more convenient if we produce the same maze over an over again
	// by setting the following constant to false, the maze will only vary with skill level and algorithm
	// but not on its own
	// for production version it is desirable that we never play the same maze 
	// so even if the algorithm and skill level are the same, the generated maze should look different
	// which is achieved with some random initialization
	private static final boolean DEVELOPMENT_VERSION_WITH_DETERMINISTIC_MAZE_GENERATION = false;

	/**
	 * Constructor
	 */
	public MazeApplication() {
		init(null, null, null);
	}

	/**
	 * Constructor that loads a maze from a given file or uses a particular method to generate a maze
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
     * or a filename that stores an already generated maze that is then loaded, or can be null
	 */
	public MazeApplication(String generation, String driver, String sensors) {
		init(generation, driver, sensors);
	}

	/**
	 * Instantiates a controller with settings according to the given parameter.
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
	 * or a filename that contains a generated maze that is then loaded,
	 * or can be null
	 * @return the newly instantiated and configured controller
	 */
	 Controller createController(String generation, String driver, String sensors) {
		 //System.out.println(sensors);
		 //System.out.println(driver);
		 //System.out.println(generation);
	    // need to instantiate a controller to return as a result in any case
	    Controller result = new Controller() ;
	    Robot robot = null;
	    RobotDriver robotDriver = null;
	    // can decide if user repeatedly plays the same mazes or 
	    // if mazes are different each and every time
	    // set to true for testing purposes
	    // set to false for playing the game
	    if (DEVELOPMENT_VERSION_WITH_DETERMINISTIC_MAZE_GENERATION)
	    	result.setDeterministic(true);
	    else
	    	result.setDeterministic(false);
	    String msg = null; // message for feedback
	    
	    // Case 1: no input
	    if (generation == "") {
	        msg = "MazeApplication: maze will be generated with a randomized algorithm."; 
	    }
	    
	    // Case 2: Prim
	    else if ("Prim".equalsIgnoreCase(generation))
	    {
	        msg = "MazeApplication: generating random maze with Prim's algorithm.";
	        result.setBuilder(Order.Builder.Prim);
	    }
	    
	    // Case 3 a and b: Eller, Kruskal, Boruvka or some other generation algorithm
	    else if ("Kruskal".equalsIgnoreCase(generation))
	    {
	    	// TODO: for P2 assignment, please add code to set the builder accordingly
	        throw new RuntimeException("Don't know anybody named Kruskal ...");
	    }
	    else if ("Eller".equalsIgnoreCase(generation))
	    {
	    	// TODO: for P2 assignment, please add code to set the builder accordingly
	        throw new RuntimeException("Don't know anybody named Eller ...");
	    }
	    else if ("Boruvka".equalsIgnoreCase(generation))
	    {
	    	result.setBuilder(Order.Builder.Boruvka);
	    }
	 // Case 9: a file
	    else {
	        File f = new File(generation) ;
	        if (f.exists() && f.canRead())
	        {
	            msg = "MazeApplication: loading maze from file: " + generation;
	            result.setFileName(generation);
	            //return result;
	        }
	        else {
	            // None of the predefined strings and not a filename either: 
	            msg = "MazeApplication: unknown parameter value: " + generation + " ignored, operating in default mode.";
	        }
	    }
	    
	    // Case 4: No driver input
	    if (driver == "") {
	    	msg = "No driver selected: maze will be ran in Manual mode.";
	    }
	    
	    // Case 5: Wizard
	    else if ("Wizard".equalsIgnoreCase(driver))
	    { 
	    	msg = "MazeApplication: running maze with Wizard driver.";
	    	robot = new ReliableRobot();
	    	robotDriver = new Wizard();
	    	result.setRobotAndDriver(robot, robotDriver);
	    }
	    
	    // Case 6: WallFollower
	    else if ("WallFollower".equalsIgnoreCase(driver))
	    {
	    	msg = "MazeApplication: running maze with WallFollower driver.";
	    	robot = new UnreliableRobot();
	    	robotDriver = new WallFollower();
	    	result.setRobotAndDriver(robot, robotDriver);
	    }

	    // Case 7: No sensor input
	    if (sensors == "") {
	    	System.out.println("No Sensor Information: Initializing ReliableSensors");
	    	robot = new ReliableRobot();
	    	// initialize all sensors as reliable on default
	    	ReliableSensor Fsensor = new ReliableSensor();
    		robot.addDistanceSensor(Fsensor, Direction.FORWARD);
    		ReliableSensor Lsensor = new ReliableSensor();
    		robot.addDistanceSensor(Lsensor, Direction.LEFT);
    		ReliableSensor Rsensor = new ReliableSensor();
    		robot.addDistanceSensor(Rsensor, Direction.RIGHT);
    		ReliableSensor Bsensor = new ReliableSensor();
    		robot.addDistanceSensor(Bsensor, Direction.BACKWARD);
    		// since we have just altered the robot, we must make sure 
	    	// the controller has access to the robot with all four sensors.
    		result.setRobotAndDriver(robot, robotDriver);
	    }
	    
	    // Case 8: Sensor input
	    else if (!"".equalsIgnoreCase(sensors)) { //make sure it is not empty
	    	
	    	// the next four characters represent the possible sensors 
	    	// on the robot in the order of front, left, right, and back.
	    	// if the character is a 0, this means the sensor should be
	    	// unreliable so we construct an unreliable sensor on that side.
	    	// if the character is a 1, we construct a reliable sensor.
	    	// Either way, we add the sensor to the robot and give it its
	    	// supposed direction.
	    	if (sensors.charAt(0) == '0') {
	    		UnreliableSensor Fsensor = new UnreliableSensor();
	    		robot.addDistanceSensor(Fsensor, Direction.FORWARD);
	    	}
	    		else {
	    		ReliableSensor Fsensor = new ReliableSensor();
	    		robot.addDistanceSensor(Fsensor, Direction.FORWARD);
	    		}
	    	if (sensors.charAt(1) == '0') {
	    		UnreliableSensor Lsensor = new UnreliableSensor();
	    		robot.addDistanceSensor(Lsensor, Direction.LEFT);
	    	}
	    		else {
	    		ReliableSensor Lsensor = new ReliableSensor();
	    		robot.addDistanceSensor(Lsensor, Direction.LEFT);
	    		}
	    	if (sensors.charAt(2) == '0') {
	    		UnreliableSensor Rsensor = new UnreliableSensor();
	    		robot.addDistanceSensor(Rsensor, Direction.RIGHT);
	    	}
	    		else {
	    		ReliableSensor Rsensor = new ReliableSensor();
	    		robot.addDistanceSensor(Rsensor, Direction.RIGHT);
	    		}
	    	if (sensors.charAt(3) == '0') {
	    		UnreliableSensor Bsensor = new UnreliableSensor();
	    		robot.addDistanceSensor(Bsensor, Direction.BACKWARD);
	    	}
	    		else {
	    		ReliableSensor Bsensor = new ReliableSensor();
	    		robot.addDistanceSensor(Bsensor, Direction.BACKWARD);
	    		}
	    	// since we have just altered the robot, we must make sure 
	    	// the controller has access to the robot with all four sensors.
	    	result.setRobotAndDriver(robot, robotDriver);
	    }
	    // controller instantiated and attributes set according to given input parameter
	    // output message and return controller
	    System.out.println(msg);
	    return result;
	}

	/**
	 * Initializes some internals and puts the game on display.
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
     * or a filename that contains a generated maze that is then loaded, or can be null
	 */
	private void init(String generation, String driver, String sensors) {
	    // instantiate a game controller and add it to the JFrame
	    Controller controller = createController(generation, driver, sensors);
		add(controller.getPanel()) ;
		// instantiate a key listener that feeds keyboard input into the controller
		// and add it to the JFrame
		KeyListener kl = new SimpleKeyListener(this, controller) ;
		addKeyListener(kl) ;
		// set the frame to a fixed size for its width and height and put it on display
		setSize(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT+22) ;
		setVisible(true) ;
		// focus should be on the JFrame of the MazeApplication and not on the maze panel
		// such that the SimpleKeyListener kl is used
		setFocusable(true) ;
		// start the game, hand over control to the game controller
		controller.start();
	}
	
	/**
	 * Main method to launch Maze game as a java application.
	 * The application can be operated in three ways. 
	 * 1) The intended normal operation is to provide no parameters
	 * and the maze will be generated by a randomized DFS algorithm (default). 
	 * 2) If a filename is given that contains a maze stored in xml format. 
	 * The maze will be loaded from that file. 
	 * This option is useful during development to test with a particular maze.
	 * 3) A predefined constant string is given to select a maze
	 * generation algorithm, currently supported is "Prim".
	 * @param args is optional, first string can be a fixed constant like Prim or
	 * the name of a file that stores a maze in XML format
	 */
	public static void main(String[] args) {
		 int i = 0, j;
	     String arg;
	     char flag;
	     boolean vflag = false;
	     String outputfile = "";
	     String generationAlgorithm = "";
	     String driver = "";
	     String reliableSensors = "";

	    JFrame app ; 
	    if (args.length == 0) {
			app = new MazeApplication();
			app.repaint() ;
		}
	    else {
	        while (i < args.length && args[i].startsWith("-")) {
	            arg = args[i++];
	            for (j = 1; j<arg.length(); j++) {
	            	flag = arg.charAt(j);
	            	switch (flag) {
	            	case 'g':
	            		generationAlgorithm = args[i++];
	            		break;
	            	case 'd':
	            		driver = args[i++];
	            		break;
	            	case 'r':
	            		reliableSensors = args[i++];
	            		break;
	            	}
	            }

	    
	    }
	    app = new MazeApplication(generationAlgorithm, driver, reliableSensors);
		app.repaint() ;
	}

}
}
