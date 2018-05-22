package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Represents the instruction echo. This instruction requires <b>a single</b>
 * argument: a register which <b>can</b> support <b>indirect addressing</b>. It
 * prints the value from the location specified by the argument to the standard
 * output.
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class InstrEcho implements Instruction {
	/**
	 * Argument of this instruction.
	 */
	private InstructionArgument argument;

	/**
	 * Initializes class variable of this class: {@link InstructionArgument}
	 * argument, provided by the arguments of this constructor. This method
	 * expects a list which contains a single {@link InstructionArgument}:
	 * register which can support indirect addressing. If invalid argument was
	 * given, an {@link IllegalArgumentException} is thrown.
	 * 
	 * @param arguments
	 *            list of {@link InstructionArgument} objects
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		areValidArguments(arguments);

		argument = arguments.get(0);
	}

	@Override
	public boolean execute(Computer computer) {
		Object value = InstructionsUtil.getValueFromLocation(computer,
				argument);

		System.out.print(value);

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
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected a single argument!");

		} else if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException(
					"Type missmatch for argument 0, must be a register!");
		}
	}
}
