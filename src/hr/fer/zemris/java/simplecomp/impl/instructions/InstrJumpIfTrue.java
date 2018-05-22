package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Represents the instruction jump if true. This instruction requires <b>a
 * single</b> argument: an address which represents the location of the next
 * instruction which will be done. Value of the <b>program counter</b> register
 * is set to the address represented by the argument if the flag register
 * contains value <code>true</code>.
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class InstrJumpIfTrue implements Instruction {
	/**
	 * Address of the next instruction which will be done.
	 */
	private int address;

	/**
	 * Initializes class variable of this class: address of the next instruction
	 * which will be done, represented by the argument. This method expects a
	 * list which contains a single argument, an integer which represents the
	 * address. If invalid argument is given, an
	 * {@link IllegalArgumentException} is thrown.
	 * 
	 * @param arguments
	 *            list of {@link InstructionArgument} objects
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {
		InstructionsUtil.validateAddress(arguments);
		address = (Integer) arguments.get(0).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		Registers registers = computer.getRegisters();

		if (registers.getFlag() == true) {
			registers.setProgramCounter(address);
		}

		return false;
	}
}