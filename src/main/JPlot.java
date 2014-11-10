package main;

public class JPlot extends ArgsProcessor{
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 600;
	public static final GLColor DEFAULT_BACKGROUND = GLColor.WHITE;
	public static final GLColor DEFAULT_LINECOLOR = GLColor.BLACK;
	public static final String DEFAULT_TITLE = "JPlot";
	public static final String[] VAR_OPTIONS = {
		"--width",	//Window width
		"--height",	//Window height
		"--help",	//Display help
		"--color",	//Line color
		"--bg-color",	//Background color
		"--title"
	};
	public static final String[][] ALIASES = {
		{"-w", "--width"},
		{"-h", "--height"},
		{"-c", "--color"},
		{"--colour", "--color"},	//brit-proofing
		{"--bg-colour","--bg-color"}	//More brit-proofing
	};
	
	private JPlot(){
		super(ALIASES);
		for(String s : VAR_OPTIONS){
			this.knownVariables.add(s);
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
