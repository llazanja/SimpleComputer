package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Represents the instruction load. This instruction requires <b>two</b>
 * arguments: a register which <b>does not</b> support <b>indirect
 * addressing</b> and an address which which represents a memory location. It
 * stores the value located at the given address to the specified register.
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class InstrLoad implements Instruction {
	/**
	 * Index of the register which represents the first argument of this
	 * instruction.
	 */
	private int registerIndex;

	/**
	 * Location in the memory, represented by the second argument of this
	 * instruction.
	 */
	private int memoryLocation;

	/**
	 * Initializes class variables of this class: index of the register and
	 * location in the memory, provided by the arguments of this constructor.
	 * This method expects a list of two arguments: register which does not
	 * support indirect addressing and number which represents an address. If
	 * invalid arguments were given, an {@link IllegalArgumentException} is
	 * thrown.
	 * 
	 * @param arguments
	 *            list of {@link InstructionArgument} objects
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		areValidArguments(arguments);

		registerIndex = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(0).getValue());
		memoryLocation = (Integer) arguments.get(1).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getMemory().getLocation(memoryLocation);

		computer.getRegisters().setRegisterValue(registerIndex, value);

		return false;
	}

	/**
	 * Help method which determines if valid arguments were given to the
	 * constructor of this class.
	 * 
	 * @param arguments
	 *            a list of {@link InstructionArgument} objects
	 */
	private void areValidArguments(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		InstructionArgument firstArg = arguments.get(0);
		if (!firstArg.isRegister()
				|| RegisterUtil.isIndirect((Integer) firstArg.getValue())) {
			throw new IllegalArgumentException(
					"Type missmatch for argument 0, must be a register that"
							+ "does not indicate indirect addressing!");

		} else if (!arguments.get(1).isNumber()) {
			throw new IllegalArgumentException(
					"Type missmatch for argument 1, must be a memory location!");
		}
	}
}