package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Represents the instruction add. This instruction requires <b>two</b>
 * arguments: two registers which <b>do not</b> support <b>indirect
 * addressing</b>. If value stored in the first register is equal to the value
 * stored in the second register, sets the flag register value to
 * <code>true</code>. Otherwise, sets it to <code>false</code>.
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class InstrTestEquals implements Instruction {
	/**
	 * Index of the first register which is an argument of this instruction.
	 */
	private int registerIndex1;

	/**
	 * Index of the second register which is an argument of this instruction.
	 */
	private int registerIndex2;

	/**
	 * Initializes class variables of this class: indexes of the registers,
	 * provided by the arguments of this constructor. This method expects a list
	 * of two arguments, which represent registers that do not support indirect
	 * addressing. If invalid arguments were given, an
	 * {@link IllegalArgumentException} is thrown.
	 * 
	 * @param arguments
	 *            list of {@link InstructionArgument} objects
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
		areValidArguments(arguments);

		registerIndex1 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(0).getValue());
		registerIndex2 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(1).getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters()
				.getRegisterValue(registerIndex1);
		Object value2 = computer.getRegisters()
				.getRegisterValue(registerIndex2);

		if (value1.equals(value2)) {
			computer.getRegisters().setFlag(true);

		} else {
			computer.getRegisters().setFlag(false);
		}

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
		for (int i = 0; i < 2; i++) {
			if (!arguments.get(i).isRegister() || RegisterUtil
					.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException(
						"Type mismatch for argument " + i + "!");
			}
		}
	}
}
