package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Represents the instruction push. This instruction requires <b>a single</b>
 * argument: a register which <b>does not</b> support <b>indirect addressing</b>
 * . It pushes the value from the specified register to the top of the stack.
 * Top of stack pointer is decreased by one.
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class InstrPush implements Instruction {
	/**
	 * Index of the register represented by the argument of this instruction.
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
	public InstrPush(List<InstructionArgument> arguments) {
		InstructionsUtil.validateNonIndirectRegister(arguments);

		registerIndex = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(0).getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		Registers registers = computer.getRegisters();

		Object value = registers.getRegisterValue(registerIndex);

		int topOfStack = (Integer) registers
				.getRegisterValue(Registers.STACK_REGISTER_INDEX);

		computer.getMemory().setLocation(topOfStack, value);

		registers.setRegisterValue(Registers.STACK_REGISTER_INDEX,
				topOfStack - 1);

		return false;
	}
}