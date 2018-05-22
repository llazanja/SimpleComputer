package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Represents the instruction decrement. This instruction requires <b>a
 * single</b> argument: a register which <b>does not</b> support <b>indirect
 * addressing</b>. It decreases the value located in the register by one.
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class InstrDecrement implements Instruction {
	/**
	 * Index of the register which represents the argument of this instruction.
	 */
	private int registerIndex;

	/**
	 * Initializes class variable of this class: index of the register,
	 * represented by the argument. This method expects a list which contains a
	 * single argument, a register which does not support indirect addressing.
	 * If invalid argument is given, an {@link IllegalArgumentException} is
	 * thrown.
	 * 
	 * @param arguments
	 *            list of {@link InstructionArgument} objects
	 */
	public InstrDecrement(List<InstructionArgument> arguments) {
		InstructionsUtil.validateNonIndirectRegister(arguments);

		registerIndex = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(0).getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getRegisters().getRegisterValue(registerIndex);

		computer.getRegisters().setRegisterValue(registerIndex,
				Integer.valueOf((Integer) value - 1));

		return false;
	}
}