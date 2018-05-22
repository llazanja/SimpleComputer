package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * <p>
 * Class which represents an implementation of computer registers.
 * </p>
 * 
 * <p>
 * There are three types of registers in this class, registers used for general
 * purpose. Program counter is a register which represents an address of the
 * next instruction which has to be done. Flag register can only have two
 * different states: true or false. All of the registers can be get and set
 * using the methods inherited from interface {@link Registers}.
 * </p>
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class RegistersImpl implements Registers {
	/**
	 * Registers for general purpose.
	 */
	private Object[] registers;
	/**
	 * Program counter - represents an address of a next instruction which will
	 * be done.
	 */
	private int programCounter;
	/**
	 * Represents a flag register which can be <code>true</code> or
	 * <code>false</code>.
	 */
	private boolean flag;

	/**
	 * Allocates a new array of registers whose size is determined by the
	 * argument. Value of the program counter is initially set to zero, and
	 * value of the flag register is set to <code>false</code>. If user has
	 * entered an invalid number of registers, an
	 * {@link IllegalArgumentException} is thrown. Valid number of registers is
	 * larger than {@link Registers#STACK_REGISTER_INDEX}.
	 * 
	 * @param regsLen
	 *            number of registers
	 */
	public RegistersImpl(int regsLen) {
		isValidRegsLen(regsLen);
		registers = new Object[regsLen];
	}

	@Override
	public Object getRegisterValue(int index) {
		isValidIndex(index);
		return registers[index];
	}

	@Override
	public void setRegisterValue(int index, Object value) {
		isValidIndex(index);
		registers[index] = value;
	}

	@Override
	public int getProgramCounter() {
		return programCounter;
	}

	@Override
	public void setProgramCounter(int value) {
		if (value < 0) {
			throw new IllegalArgumentException(
					"Value of the program counter can not be negative!");
		}
		programCounter = value;
	}

	@Override
	public void incrementProgramCounter() {
		programCounter++;
	}

	@Override
	public boolean getFlag() {
		return flag;
	}

	@Override
	public void setFlag(boolean value) {
		flag = value;
	}

	/**
	 * Helper method which checks if user has entered a valid number of
	 * registers in the constructor of this class. If the number is not larger
	 * than {@link Registers#STACK_REGISTER_INDEX}, an
	 * {@link IllegalArgumentException} is thrown.
	 * 
	 * @param regsLen
	 *            number of registers
	 */
	private void isValidRegsLen(int regsLen) {
		if (regsLen < Registers.STACK_REGISTER_INDEX) {
			throw new IllegalArgumentException(
					"Number of registers must be larger than "
							+ Registers.STACK_REGISTER_INDEX + "!");
		}
	}

	/**
	 * Helper method which checks if user has entered a valid index of a
	 * register in {@link #getRegisterValue(int)} or
	 * {@link #setRegisterValue(int, Object)}. Valid index is in interval [1,
	 * number of registers - 1]. If invalid index was provided, an
	 * {@link IllegalArgumentException} is thrown.
	 * 
	 * @param index
	 *            index of the register
	 */
	private void isValidIndex(int index) {
		if (index < 0 || index > registers.length) {
			throw new IllegalArgumentException(
					"Index of the register must be in the interval [0, number of registers - 1]!");
		}
	}
}
