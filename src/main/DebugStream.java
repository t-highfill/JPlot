package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class DebugStream extends PrintStream {

	public static final boolean DEBUG_ON = true;
	
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
		if (DEBUG_ON)
			super.write(b);
	}

	@Override
	public void write(byte[] buf, int off, int len) {
		if (DEBUG_ON)
			super.write(buf, off, len);
	}

	@Override
	public void print(boolean b) {
		if (DEBUG_ON)
			super.print(b);
	}

	@Override
	public void print(char c) {
		if (DEBUG_ON)
			super.print(c);
	}

	@Override
	public void print(int i) {
		if (DEBUG_ON)
			super.print(i);
	}

	@Override
	public void print(long l) {
		if (DEBUG_ON)
			super.print(l);
	}

	@Override
	public void print(float f) {
		if (DEBUG_ON)
			super.print(f);
	}

	@Override
	public void print(double d) {
		if (DEBUG_ON)
			super.print(d);
	}

	@Override
	public void print(char[] s) {
		if (DEBUG_ON)
			super.print(s);
	}

	@Override
	public void print(String s) {
		if (DEBUG_ON)
			super.print(s);
	}

	@Override
	public void print(Object obj) {
		if (DEBUG_ON)
			super.print(obj);
	}

	@Override
	public void println() {
		if (DEBUG_ON)
			super.println();
	}

	@Override
	public void println(boolean x) {
		if (DEBUG_ON)
			super.println(x);
	}

	@Override
	public void println(char x) {
		if (DEBUG_ON)
			super.println(x);
	}

	@Override
	public void println(int x) {
		if (DEBUG_ON)
			super.println(x);
	}

	@Override
	public void println(long x) {
		if (DEBUG_ON)
			super.println(x);
	}

	@Override
	public void println(float x) {
		if (DEBUG_ON)
			super.println(x);
	}

	@Override
	public void println(double x) {
		if (DEBUG_ON)
			super.println(x);
	}

	@Override
	public void println(char[] x) {
		if (DEBUG_ON)
			super.println(x);
	}

	@Override
	public void println(String x) {
		if (DEBUG_ON)
			super.println(x);
	}

	@Override
	public void println(Object x) {
		if (DEBUG_ON)
			super.println(x);
	}

	@SuppressWarnings("unused")
	@Override
	public PrintStream printf(String format, Object... args) {
		if (DEBUG_ON)
			return super.printf(format, args);
		return null;
	}

	@SuppressWarnings("unused")
	@Override
	public PrintStream printf(Locale l, String format, Object... args) {
		if (DEBUG_ON)
			return super.printf(l, format, args);
		return null;
	}

}
