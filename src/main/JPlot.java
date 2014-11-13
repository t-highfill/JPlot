package main;

import main.args.*;

public class JPlot extends ArgsProcessor{
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 600;
	public static final GLColor DEFAULT_BACKGROUND = GLColor.WHITE;
	public static final GLColor DEFAULT_LINECOLOR = GLColor.BLACK;
	public static final String DEFAULT_TITLE = "JPlot";
	public static final ArgMatcher[] VAR_OPTIONS = {
		new IntOption("--width", DEFAULT_WIDTH),	//Window width
		new IntOption("--height",DEFAULT_HEIGHT),	//Window height
		new ActionArg("--help"){	//Display help
			@Override
			public void action() {
				showHelp();
			}
		},
		new ColorOption("--color", DEFAULT_LINECOLOR),	//Line color
		new ColorOption("--bg-color", DEFAULT_BACKGROUND),	//Background color
		new StringOption("--title", DEFAULT_TITLE),
		new FileOption("--y-file", null),
		new FileOption("--x-file", null)
	};
	public static final String[][] ALIASES = {
		{"-w", "--width"},
		{"-h", "--height"},
		{"-c", "--color"},
		{"--colour", "--color"},	//brit-proofing
		{"--bg-colour","--bg-color"}	//More brit-proofing
	};
	public static void showHelp(){
		System.out.println("JPlot: the java based data plotter");
		System.out.println("USAGE:\tJPlot [OPTIONS] [FILE]");
	}
	public static void showVersion(){
		System.out.println("JPlot 0.0.1");
	}
	
	private JPlot(){
		super(ALIASES);
		for(ArgMatcher s : VAR_OPTIONS){
			this.knownArgs.add(s);
		}
	}
	
	public static void main(String[] args){
		JPlot jplot = new JPlot();
		try {
			jplot.process(args);
		} catch (AliasRecursionException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
