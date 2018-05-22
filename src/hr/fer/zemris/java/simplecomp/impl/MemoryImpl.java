package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * <p>
 * Class which represents an implementation of computer memory.
 * </p>
 * 
 * <p>
 * Size of the memory is determined by user. This implementation of memory
 * stores objects, not bytes. It inherits methods from interface {@link Memory}
 * which are used either for getting an object from a certain location or
 * setting an object to one.
 * </p>
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class MemoryImpl implements Memory {
	/**
	 * Container of objects, limited by the size of this memory.
	 */
	private Object[] container;

	/**
	 * Initializes the container of this memory whose size is represented by the
	 * argument. Valid size of the memory is a number larger than zero. If user
	 * enters an invalid number, an {@link IllegalArgumentException} is thrown.
	 * 
	 * @param size
	 *            size of the memory
	 */
	public MemoryImpl(int size) {
		isValidMemorySize(size);
		container = new Object[size];
	}

	@Override
	public void setLocation(int location, Object value) {
		isValidLocation(location);
		container[location] = value;
	}

	@Override
	public Object getLocation(int location) {
		isValidLocation(location);
		return container[location];
	}

	/**
	 * Helper method which checks if user has entered a valid memory size in the
	 * constructor of this class. If the number is less or equal to zero, an
	 * {@link IllegalArgumentException} is thrown.
	 * 
	 * @param size
	 *            size of the memory
	 */
	private void isValidMemorySize(int size) {
		if (size < 1) {
			throw new IllegalArgumentException(
					"Size of the memory must be larger than zero!");
		}
	}

	/**
	 * Helper method which checks if user has entered a valid memory location in
	 * {@link #setLocation(int, Object)} or {@link #getLocation(int)}. Valid
	 * location is in interval [1, size - 1]. If invalid location was provided,
	 * an {@link IllegalArgumentException} is thrown.
	 * 
	 * @param location
	 *            location in the memory
	 */
	private void isValidLocation(int location) {
		if (location < 0 || location >= container.length) {
			throw new IllegalArgumentException(
					"Location in the memory has to be in the interval [1, size - 1]!");
		}
	}
}
