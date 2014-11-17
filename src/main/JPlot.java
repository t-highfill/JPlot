package main;

import java.util.Arrays;

import main.args.*;

public class JPlot extends ArgsProcessor{
	public static final DebugStream DEBUG = new DebugStream(System.err);
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
				JPLOT.showHelp();
			}
		},
		new ColorOption("--color", DEFAULT_LINECOLOR),	//Line color
		new ColorOption("--bg-color", DEFAULT_BACKGROUND),	//Background color
		new StringOption("--title", DEFAULT_TITLE),
		new FileOption("--y-file", null),
		new FileOption("--x-file", null),
		new Flag("--x-stdin", false),
		new Flag("--y-stdin", true)
	};
	public static final String[][] ALIASES = {
		{"-w", "--width"},
		{"-h", "--height"},
		{"-c", "--color"},
		{"--colour", "--color"},	//brit-proofing
		{"--bg-colour","--bg-color"}	//More brit-proofing
	};
	public static final JPlot JPLOT = new JPlot();
	public void showHelp(){
		System.out.println("JPlot: the java based data plotter");
		System.out.println("USAGE:\tJPlot [OPTIONS] [FILE]");
	}
	public static void showVersion(){
		System.out.println("JPlot 0.0.1");
	}
	
	private JPlot(){
		super(ALIASES);
		this.knownArgs.add(
				new ArgList<AbstractArgMatcher>("mainSeq",false,
						new ArgSet<ArgMatcher>("options", true, VAR_OPTIONS),
						new FileLoop("files", true)
				)
		);
	}
	
	public static void main(String[] args){
		DEBUG.println("args="+Arrays.toString(args));
		try {
			JPLOT.process(args);
		} catch (AliasRecursionException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println(JPLOT.knownArgs.get(0));
	}
}
