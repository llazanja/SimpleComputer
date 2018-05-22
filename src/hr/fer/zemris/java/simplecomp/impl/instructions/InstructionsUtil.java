package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Utility class which contains methods used to validate arguments of the
 * instructions and methods used to get value from a location or to set value to
 * a location.
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class InstructionsUtil {

	/**
	 * Help method used to validate arguments of the instructions which require
	 * three register that do not support indirect addressing.
	 * 
	 * @param arguments
	 *            list of {@link InstructionArgument} objects
	 */
	public static void validateThreeNonIndirectRegisters(
			List<InstructionArgument> arguments) {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		for (int i = 0; i < 3; i++) {
			if (!arguments.get(i).isRegister() || RegisterUtil
					.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException(
						"Type mismatch for argument " + i + "!");
			}
		}
	}

	/**
	 * Help method used to validate arguments of the instructions which require
	 * a single register that does not support indirect addressing.
	 * 
	 * @param arguments
	 *            list of {@link InstructionArgument} objects
	 */
	public static void validateNonIndirectRegister(
			List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected a single argument!");
		}

		InstructionArgument firstArg = arguments.get(0);
		if (!firstArg.isRegister()
				|| RegisterUtil.isIndirect((Integer) firstArg.getValue())) {
			throw new IllegalArgumentException(
					"Type missmatch for argument 0, must be a register that"
							+ "does not indicate indirect addressing!");
		}
	}

	/**
	 * Help method used to validate arguments of the instructions which require
	 * an address which represents memory location or location of the
	 * instruction.
	 * 
	 * @param arguments
	 *            list of {@link InstructionArgument} objects
	 */
	public static void validateAddress(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected a single argument!");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException(
					"Type missmatch for argument 0, must be a memory location!");
		}
	}

	/**
	 * Help method which gets the value from location specified by the
	 * {@link InstructionArgument} argument. Argument is a register which can
	 * support indirect addressing.
	 * 
	 * @param computer
	 *            {@link Computer} object
	 * @param argument
	 *            {@link InstructionArgument} object
	 * @return {@link Object} from the location specified by the argument
	 */
	public static Object getValueFromLocation(Computer computer,
			InstructionArgument argument) {
		Object value;

		int argValue = (Integer) argument.getValue();

		if (argument.isRegister() && RegisterUtil.isIndirect(argValue)) {
			int index = RegisterUtil.getRegisterIndex(argValue);

			int memoryLocation = (Integer) computer.getRegisters()
					.getRegisterValue(index)
					+ RegisterUtil.getRegisterOffset(argValue);

			value = computer.getMemory().getLocation(memoryLocation);
		} else {
			value = computer.getRegisters().getRegisterValue(argValue);
		}

		return value;
	}

	/**
	 * Help method which sets the value to location specified by the
	 * {@link InstructionArgument} argument. Argument is a register which can
	 * support indirect addressing.
	 * 
	 * @param computer
	 *            {@link Computer} object
	 * @param argument
	 *            {@link InstructionArgument} object
	 * @param value
	 *            {@link Object} which will be stored to the location specified
	 *            by the second argument
	 */
	public static void setValueToLocation(Computer computer,
			InstructionArgument argument, Object value) {
		int argValue = (Integer) argument.getValue();

		Registers registers = computer.getRegisters();

		if (argument.isRegister() && RegisterUtil.isIndirect(argValue)) {
			int index = RegisterUtil.getRegisterIndex(argValue);

			int memoryLocation = (Integer) registers.getRegisterValue(index)
					+ RegisterUtil.getRegisterOffset(argValue);

			computer.getMemory().setLocation(memoryLocation, value);

		} else {
			registers.setRegisterValue(RegisterUtil.getRegisterIndex(argValue),
					value);
		}
	}

	/**
	 * Help method used to validate arguments of the instructions which do not
	 * require any arguments.
	 * 
	 * @param arguments
	 *            list of {@link InstructionArgument} objects
	 */
	public static void validateNoArguments(
			List<InstructionArgument> arguments) {
		if (!arguments.isEmpty()) {
			throw new IllegalArgumentException(
					"No arguments are required for this instruction.");
		}
	}
}