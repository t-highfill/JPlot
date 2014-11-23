package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import main.args.*;

/**
 * Main class for the program
 * 
 * @author Tobias Highfill
 *
 */
public class JPlot extends ArgsProcessor{
	public static final DebugStream DEBUG = new DebugStream(System.out);
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
	public static final Flag Y_STDIN_FLAG = new Flag("--y-stdin", false);	//true if y should be taken from stdin
	public static final CharFlag DEBUG_MODE = new CharFlag('d', false);	//controls debug mode
	public static final CharFlag FULLSCREEN = new CharFlag('f', false);	//make window fullscreen?
	public static final MultiFlag ALL_FLAGS = new MultiFlag(DEBUG_MODE, FULLSCREEN);
	public static final IntOption FPSCAP = new IntOption("--fps-cap", -1);	//FPS cap
	public static final ActionArg HELP = new ActionArg("--help"){	//Display help
		@Override
		public void action() {
			JPLOT.showHelp();
			System.exit(0);
		}
	};
	public static final ActionArg VERSION = new ActionArg("--version"){	//Display version info
		@Override
		public void action() {
			JPlot.showVersion();
			System.exit(0);
		}
	};
	{
		ALL_FLAGS.description = "Flags";
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
		FULLSCREEN.description = "Run in fullscreen mode";
		FPSCAP.description = "Limits the framerate. If negative or zero framerate is unlimited";
		HELP.description = "Display this help message";
		VERSION.description = "Display version information";
	}
	
	public static final ArgMatcher[] VAR_OPTIONS = {
		//Since this is the order in which they appear in the help message I have sorted them alphabetically
		ALL_FLAGS, BG_COLOR, FPSCAP, HEIGHT, HELP,
		LINECOLOR, TITLE, VERSION, WIDTH, X_FILE, X_STDIN_FLAG,
		Y_FILE, Y_STDIN_FLAG
	};
	public static final String[][] ALIASES = {
		{"-w", WIDTH.getName()},
		{"-h", HEIGHT.getName()},
		{"-c", LINECOLOR.getName()},
		{"--colour", LINECOLOR.getName()},	//brit-proofing
		{"--bg-colour", BG_COLOR.getName()},	//More brit-proofing
		{"--fullscreen", FULLSCREEN.getName()},
		{"--debug", DEBUG_MODE.getName()}
	};
	public static final JPlot JPLOT = new JPlot();
	
	public static boolean isDebugOn(){
		return DEBUG_MODE.getVal();
	}
	
	private static Reader[] getReaders() throws FileNotFoundException{
		boolean[] stdin = {X_STDIN_FLAG.getVal(), Y_STDIN_FLAG.getVal()};
		File[] files = {X_FILE.getVal(), Y_FILE.getVal()}, otherFiles = JPLOT.FILES.getFiles();
		Reader[] readers = {null, null};
		assert stdin.length == files.length && files.length == readers.length;
		if(X_STDIN_FLAG.isDefined() && Y_STDIN_FLAG.isDefined()){
			throw new FlagCollisionException(X_STDIN_FLAG, Y_STDIN_FLAG);
		}
		for(int i=0;i<readers.length;i++){
			if(files[i]!=null){
				DEBUG.println("Set reader "+i+" to FileReader for "+files[i]);
				readers[i] = new FileReader(files[i]);
			}else if(stdin[i]){
				DEBUG.println("Set reader "+i+" to InputStreamReader");
				readers[i]=new InputStreamReader(System.in);
			}
		}
		if(readers[1]==null){
			if(otherFiles.length>0){
				DEBUG.println("Set reader "+1+" to FileReader for "+otherFiles[0]);
				readers[1]=new FileReader(otherFiles[0]);
			}else{
				DEBUG.println("Set reader "+1+" to InputStreamReader");
				readers[1]=new InputStreamReader(System.in);
			}
		}
		return readers;
	}
	private static Dataset data = null;
	public static Dataset getData(){
		if(data!=null){
			return data;
		}
		Reader[] readers = {null, null};
		try {
			readers = getReaders();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(e.getMessage().hashCode());
		}
		boolean xnull=readers[0]==null, ynull=readers[1]==null;
		if(xnull && ynull){
			throw new RuntimeException("Unable to find data");
		}else if(!xnull && !ynull){
			data = new DualStreamDataset(readers[0], readers[1]);
		}else{
			for(Reader r : readers){
				if(r!=null){
					data = new SingleStreamDataset(r);
				}
			}
		}
		return data;
	}
	
	public final FileLoop FILES = new FileLoop("files", true);
	
	private JPlot(){
		super(ALIASES);
		this.knownArgs.add(
				new ArgList<AbstractArgMatcher>("mainSeq",false,
						new ArgSet<ArgMatcher>("options", true, VAR_OPTIONS),
						FILES
				)
		);
	}
	
	public void showHelp(){
		System.out.println("JPlot: the java based data plotter");
		System.out.println("USAGE:\tJPlot [OPTIONS] [FILES]\nOPTIONS:");
		int maxsize = 0;
		for(ArgMatcher am : VAR_OPTIONS){
			maxsize = Math.max(maxsize, am.getName().length());
		}
		for(ArgMatcher am : VAR_OPTIONS){
			System.out.println("\t"+am.getHelp(maxsize));
		}
	}
	public static void showVersion(){
		System.out.println("JPlot 0.0.1\nThis program is a work in progress. Features are liable to change or vanish.");
	}
	
	private void initLWJGL(){
		if(Display.isCreated()) return;
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH.getVal(), HEIGHT.getVal()));
			Display.setFullscreen(FULLSCREEN.getVal());
			Display.setTitle(TITLE.getVal());
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(e.getMessage().hashCode());
		}
	}
	
	private void mainloop(){
		this.initLWJGL();
		getData().begin();
		while(!Display.isCloseRequested()){
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			BG_COLOR.getVal().clearScreen();
			getData().render();
			Display.update();
			int cap = FPSCAP.getVal();
			if(cap > 0){
				Display.sync(cap);
			}
		}
		Display.destroy();
		System.exit(0);
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
		JPLOT.mainloop();
	}
}
