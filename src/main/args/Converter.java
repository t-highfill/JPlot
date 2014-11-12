package main.args;

public interface Converter<F, T> {
	T convert(F t);
}
