package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Represents the instruction halt. This instruction does not require any
 * arguments. It terminates the work of the {@link ExecutionUnit}.
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class InstrHalt implements Instruction {

	/**
	 * Constructor method. Has a single argument: a list of
	 * {@link InstructionArgument} objects. It has to be empty or an
	 * {@link IllegalArgumentException} will be thrown.
	 * 
	 * @param arguments
	 *            list of {@link InstructionArgument} objects
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		InstructionsUtil.validateNoArguments(arguments);
	}

	@Override
	public boolean execute(Computer computer) {
		return true;
	}
}