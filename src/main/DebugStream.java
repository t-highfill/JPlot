package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class DebugStream extends PrintStream {
	
	public DebugStream(File file, String csn) throws FileNotFoundException,
			UnsupportedEncodingException {
		super(file, csn);
	}

	public DebugStream(File file) throws FileNotFoundException {
		super(file);
	}

	public DebugStream(String fileName, String csn)
			throws FileNotFoundException, UnsupportedEncodingException {
		super(fileName, csn);
	}

	public DebugStream(String fileName) throws FileNotFoundException {
		super(fileName);
	}

	public DebugStream(OutputStream out, boolean autoFlush, String encoding)
			throws UnsupportedEncodingException {
		super(out, autoFlush, encoding);
	}

	public DebugStream(OutputStream out, boolean autoFlush) {
		super(out, autoFlush);
	}

	public DebugStream(OutputStream other) {
		super(other);
	}

	public DebugStream() {
		this(System.err, true);
	}

	@Override
	public void write(int b) {
		if (JPlot.isDebugOn())
			super.write(b);
	}

	@Override
	public void write(byte[] buf, int off, int len) {
		if (JPlot.isDebugOn())
			super.write(buf, off, len);
	}

	@Override
	public void print(boolean b) {
		if (JPlot.isDebugOn())
			super.print(b);
	}

	@Override
	public void print(char c) {
		if (JPlot.isDebugOn())
			super.print(c);
	}

	@Override
	public void print(int i) {
		if (JPlot.isDebugOn())
			super.print(i);
	}

	@Override
	public void print(long l) {
		if (JPlot.isDebugOn())
			super.print(l);
	}

	@Override
	public void print(float f) {
		if (JPlot.isDebugOn())
			super.print(f);
	}

	@Override
	public void print(double d) {
		if (JPlot.isDebugOn())
			super.print(d);
	}

	@Override
	public void print(char[] s) {
		if (JPlot.isDebugOn())
			super.print(s);
	}

	@Override
	public void print(String s) {
		if (JPlot.isDebugOn())
			super.print(s);
	}

	@Override
	public void print(Object obj) {
		if (JPlot.isDebugOn())
			super.print(obj);
	}

	@Override
	public void println() {
		if (JPlot.isDebugOn())
			super.println();
	}

	@Override
	public void println(boolean x) {
		if (JPlot.isDebugOn())
			super.println(x);
	}

	@Override
	public void println(char x) {
		if (JPlot.isDebugOn())
			super.println(x);
	}

	@Override
	public void println(int x) {
		if (JPlot.isDebugOn())
			super.println(x);
	}

	@Override
	public void println(long x) {
		if (JPlot.isDebugOn())
			super.println(x);
	}

	@Override
	public void println(float x) {
		if (JPlot.isDebugOn())
			super.println(x);
	}

	@Override
	public void println(double x) {
		if (JPlot.isDebugOn())
			super.println(x);
	}

	@Override
	public void println(char[] x) {
		if (JPlot.isDebugOn())
			super.println(x);
	}

	@Override
	public void println(String x) {
		if (JPlot.isDebugOn())
			super.println(x);
	}

	@Override
	public void println(Object x) {
		if (JPlot.isDebugOn())
			super.println(x);
	}
	
	@Override
	public PrintStream printf(String format, Object... args) {
		if (JPlot.isDebugOn())
			return super.printf(format, args);
		return null;
	}

	@Override
	public PrintStream printf(Locale l, String format, Object... args) {
		if (JPlot.isDebugOn())
			return super.printf(l, format, args);
		return null;
	}

}
