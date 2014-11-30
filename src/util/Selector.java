package util;

public interface Selector<E> {
	public E choose(E e1, E e2);
}
