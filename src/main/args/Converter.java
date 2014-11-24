package main.args;

/**
 * Utility interface for converting types
 * @author Tobias.Highfill
 *
 * @param <F> Type to convert FROM (the before type)
 * @param <T> Type to convert TO (the after type)
 */
public interface Converter<F, T> {
	/**
	 * Convert object from type F to type T
	 * @param f The object to convert
	 * @return The newly created converted object
	 */
	T convert(F f);
}
