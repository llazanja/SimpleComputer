package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Represents the instruction ret. This instruction does not require any
 * arguments. It returns from the subroutine program called by instruction call.
 * Address which was stored on the top of the stack is set as a current program
 * counter register value.
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class InstrRet implements Instruction {
	/**
	 * Constructor method. Has a single argument: a list of
	 * {@link InstructionArgument} objects. It has to be empty or an
	 * {@link IllegalArgumentException} will be thrown.
	 * 
	 * @param arguments
	 *            list of {@link InstructionArgument} objects
	 */
	public InstrRet(List<InstructionArgument> arguments) {
		InstructionsUtil.validateNoArguments(arguments);
	}

	@Override
	public boolean execute(Computer computer) {
		Registers registers = computer.getRegisters();

		int topOfStack = (Integer) registers
				.getRegisterValue(Registers.STACK_REGISTER_INDEX);

		int adress = (Integer) computer.getMemory().getLocation(topOfStack + 1);

		registers.setRegisterValue(Registers.STACK_REGISTER_INDEX,
				topOfStack + 1);

		registers.setProgramCounter(adress);

		return false;
	}
}
