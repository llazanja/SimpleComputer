package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Represents the instruction move. This instruction requires <b>two</b>
 * arguments: first argument must be a register which <b>can</b> support
 * <b>indirect addressing</b>, second argument must be either a number or a
 * register which <b>can</b> support <b>indirect addressing</b>. It moves the
 * value from the location specified by the second argument to the location
 * specified by the first argument.
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class InstrMove implements Instruction {
	/**
	 * First argument of this instruction.
	 */
	private InstructionArgument firstArg;

	/**
	 * Second argument of this instruction.
	 */
	private InstructionArgument secondArg;

	/**
	 * Initializes class variables of this class: first and second
	 * {@link InstructionArgument}, provided by the arguments of this
	 * constructor. This method expects a list of two arguments. If invalid
	 * arguments were given, an {@link IllegalArgumentException} is thrown.
	 * 
	 * @param arguments
	 *            list of {@link InstructionArgument} objects
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		validateArguments(arguments);

		firstArg = arguments.get(0);
		secondArg = arguments.get(1);
	}

	@Override
	public boolean execute(Computer computer) {
		Object value;

		if (secondArg.isNumber()) {
			value = secondArg.getValue();
		} else {
			value = InstructionsUtil.getValueFromLocation(computer, secondArg);
		}

		InstructionsUtil.setValueToLocation(computer, firstArg, value);

		return false;
	}

	/**
	 * Help method which determines if valid arguments were given to the
	 * constructor of this class.
	 * 
	 * @param arguments
	 *            a list of {@link InstructionArgument} objects
	 */
	private void validateArguments(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		} else if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException(
					"Type missmatch for argument 0, must be a register or an"
							+ "indirect memory address.");
		}

		if (arguments.get(1).isString()) {
			throw new IllegalArgumentException(
					"Type missmatch for argument 1, must be a register, an"
							+ "indirect memory address or a number.");
		}
	}
}
