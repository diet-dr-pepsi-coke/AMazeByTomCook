package edu.wm.cs.cs301.tomcook.generation;

import edu.wm.cs.cs301.tomcook.gui.MazePanel;

public class GlobalValues {
    public static int skillLevel = 0; //default
    public static boolean perfect = false; //default
    public static String filename = null;
    public static Order.Builder builder = Order.Builder.DFS;
    public static int seed = 13; //default
    public static Maze mazeConfig;

    public static MazePanel panel;

    public static boolean showSolution = false;
    public static boolean showMaze = false;
    public static boolean mapMode = false;
}
