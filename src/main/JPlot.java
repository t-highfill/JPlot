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
	
	public static final IntOption WIDTH = new IntOption("--width", DEFAULT_WIDTH);	//Window width
	public static final IntOption HEIGHT = new IntOption("--height",DEFAULT_HEIGHT);	//Window height
	public static final ColorOption LINECOLOR = new ColorOption("--color", DEFAULT_LINECOLOR);	//Line color
	public static final ColorOption BG_COLOR = new ColorOption("--bg-color", DEFAULT_BACKGROUND);	//Background color
	public static final StringOption TITLE = new StringOption("--title", DEFAULT_TITLE);	//window title
	public static final FileOption Y_FILE = new FileOption("--y-file", null);	//y data file
	public static final FileOption X_FILE = new FileOption("--x-file", null);	//x data file
	public static final Flag X_STDIN_FLAG = new Flag("--x-stdin", false);	//true if x should be taken from stdin
	public static final Flag Y_STDIN_FLAG = new Flag("--y-stdin", true);	//true if y should be taken from stdin
	public static final Flag DEBUG_MODE = new Flag("--debug", false);	//controls debug mode
	public static final ActionArg HELP = new ActionArg("--help"){	//Display help
		@Override
		public void action() {
			JPLOT.showHelp();
		}
	};
	public static final ActionArg VERSION = new ActionArg("--version"){	//Display version info
		@Override
		public void action() {
			JPlot.showVersion();
		}
	};
	{
		WIDTH.description = "Window width in pixels";
		HEIGHT.description = "Window height in pixels";
		LINECOLOR.description = "Color of the line in the plot";
		BG_COLOR.description = "Color of the background in the plot";
		TITLE.description = "Title of the window";
		Y_FILE.description = "File to extract y values from";
		X_FILE.description = "File to extract x values from";
		X_STDIN_FLAG.description = "Extract x values from standard input";
		Y_STDIN_FLAG.description = "Extract y values from standard input";
		DEBUG_MODE.description = "Run program in debug mode";
		HELP.description = "Display this help message";
		VERSION.description = "Display version information";
	}
	
	public static final ArgMatcher[] VAR_OPTIONS = {
		//Since this is the order in which they appear in the help message I have sorted them alphabetically
		BG_COLOR, DEBUG_MODE, HEIGHT, HELP, LINECOLOR, TITLE, VERSION, WIDTH, X_FILE, X_STDIN_FLAG, Y_FILE, Y_STDIN_FLAG
	};
	public static final String[][] ALIASES = {
		{"-w", WIDTH.getName()},
		{"-h", HEIGHT.getName()},
		{"-c", LINECOLOR.getName()},
		{"--colour", LINECOLOR.getName()},	//brit-proofing
		{"--bg-colour", BG_COLOR.getName()}	//More brit-proofing
	};
	public static final JPlot JPLOT = new JPlot();
	
	public static boolean isDebugOn(){
		return DEBUG_MODE.getVal();
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
	
	public void showHelp(){
		System.out.println("JPlot: the java based data plotter");
		System.out.println("USAGE:\tJPlot [OPTIONS] [FILES]\nOPTIONS:");
		for(ArgMatcher am : VAR_OPTIONS){
			System.out.println("\t"+am.getHelp());
		}
	}
	public static void showVersion(){
		System.out.println("JPlot 0.0.1");
	}
	
	public static void main(String[] args){
		DEBUG.println("args="+Arrays.toString(args));
		try {
			JPLOT.process(args);
		} catch (AliasRecursionException e) {
			e.printStackTrace();
			System.exit(1);
		}
		DEBUG.println(JPLOT.knownArgs.get(0));
	}
}
