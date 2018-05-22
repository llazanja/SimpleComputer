package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Represents the instruction iinput. This instruction requires <b>a single</b>
 * argument: an address which represents the memory location. It reads a line
 * from the standard input. If the line represents an {@link Integer}, value of
 * the flag register is set to <code>true</code>. Otherwise, sets it to
 * <code>false</code>.
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class InstrIinput implements Instruction {
	/**
	 * Location in the memory, represented by the argument.
	 */
	private int memoryLocation;

	/**
	 * Initializes class variable of this class: memory location. This method
	 * expects a list which contains a single argument, an integer which
	 * represents the location. If invalid argument is given, an
	 * {@link IllegalArgumentException} is thrown.
	 * 
	 * @param arguments
	 *            list of {@link InstructionArgument} objects
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		InstructionsUtil.validateAddress(arguments);

		memoryLocation = (Integer) arguments.get(0).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in)));

		try {
			String line = reader.readLine();

			int number = Integer.parseInt(line);

			computer.getRegisters().setFlag(true);

			computer.getMemory().setLocation(memoryLocation, number);

		} catch (IOException e) {
			System.err.println(e.getMessage());
			computer.getRegisters().setFlag(false);

		} catch (NumberFormatException nfe) {
			computer.getRegisters().setFlag(false);
		}

		return false;
	}
}