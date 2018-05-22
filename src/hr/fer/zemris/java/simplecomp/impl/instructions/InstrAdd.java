package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Represents the instruction add. This instruction requires <b>three</b>
 * arguments: three registers which <b>do not</b> support <b>indirect
 * addressing</b>. It sums the value located in second and third register and
 * stores in into the first register.
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class InstrAdd implements Instruction {
	/**
	 * Index of the first register which is an argument of this instruction.
	 */
	private int registerIndex1;

	/**
	 * Index of the second register which is an argument of this instruction.
	 */
	private int registerIndex2;

	/**
	 * Index of the third register which is an argument of this instruction.
	 */
	private int registerIndex3;

	/**
	 * Initializes class variables of this class: indexes of the registers,
	 * provided by the arguments of this constructor. This method expects a list
	 * of three arguments, which represent registers that do not support
	 * indirect addressing. If invalid arguments were given, an
	 * {@link IllegalArgumentException} is thrown.
	 * 
	 * @param arguments
	 *            list of {@link InstructionArgument} objects
	 */
	public InstrAdd(List<InstructionArgument> arguments) {
		InstructionsUtil.validateThreeNonIndirectRegisters(arguments);
		registerIndex1 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(0).getValue());
		registerIndex2 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(1).getValue());
		registerIndex3 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(2).getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters()
				.getRegisterValue(registerIndex2);
		Object value2 = computer.getRegisters()
				.getRegisterValue(registerIndex3);

		computer.getRegisters().setRegisterValue(registerIndex1,
				Integer.valueOf((Integer) value1 + (Integer) value2));

		return false;
	}
}