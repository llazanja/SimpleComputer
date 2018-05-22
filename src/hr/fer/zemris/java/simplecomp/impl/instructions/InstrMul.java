package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Represents the instruction add. This instruction requires <b>three</b>
 * arguments: three registers which <b>do not</b> support <b>indirect
 * addressing</b>. It multiplies the value located in the second register, by
 * the value located in the third register and stores it in into the first
 * register.
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class InstrMul implements Instruction {
	/**
	 * Index of the first register which is an argument of this instruction.
	 */
	private int indexRegistra1;

	/**
	 * Index of the second register which is an argument of this instruction.
	 */
	private int indexRegistra2;

	/**
	 * Index of the third register which is an argument of this instruction.
	 */
	private int indexRegistra3;

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
	public InstrMul(List<InstructionArgument> arguments) {
		InstructionsUtil.validateThreeNonIndirectRegisters(arguments);

		this.indexRegistra1 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.indexRegistra2 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(1).getValue());
		this.indexRegistra3 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(2).getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters()
				.getRegisterValue(indexRegistra2);
		Object value2 = computer.getRegisters()
				.getRegisterValue(indexRegistra3);

		computer.getRegisters().setRegisterValue(indexRegistra1,
				Integer.valueOf((Integer) value1 * (Integer) value2));

		return false;
	}
}