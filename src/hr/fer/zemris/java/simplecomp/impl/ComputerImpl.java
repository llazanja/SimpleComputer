package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * <p>
 * An implementation of a computer.
 * </p>
 * 
 * <p>
 * Has a memory which is represented by the class {@link MemoryImpl} and
 * registers represented by the class {@link RegistersImpl}. Size of the memory
 * and the number of registers are specified by the user. Class provides methods
 * inherited from interface {@link Computer} which can be used for getting the
 * registers or memory.
 * </p>
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class ComputerImpl implements Computer {
	/**
	 * Registers of this computer.
	 */
	private RegistersImpl registers;
	/**
	 * Memory of this computer.
	 */
	private MemoryImpl memory;

	/**
	 * Initializes the memory of this computer, whose size is determined by the
	 * first argument and registers of this computer whose size is determined by
	 * the second argument. If user has entered an invalid size of the memory,
	 * or invalid number of registers an {@link IllegalArgumentException} is
	 * thrown. Both numbers are valid if they are larger than 0.
	 * 
	 * @param size
	 *            size of the memory
	 * @param regsLen
	 *            number of registers
	 */
	public ComputerImpl(int size, int regsLen) {
		registers = new RegistersImpl(regsLen);
		memory = new MemoryImpl(size);
	}
	@Override
	public Registers getRegisters() {
		return registers;
	}

	@Override
	public Memory getMemory() {
		return memory;
	}
}