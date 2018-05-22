package hr.fer.zemris.java.simplecomp;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * <p>
 * Implementation of the computer execution unit. This class starts the work of
 * the computer.
 * </p>
 * 
 * <p>
 * It implements interface {@link ExecutionUnit} and provides a single method
 * {@link #go(Computer)} which starts the work of the computer. Method generates
 * instructions and executes them. The work of the computer ends when halt
 * instruction is executed.
 * </p>
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	@Override
	public boolean go(Computer computer) {
		Registers registers = computer.getRegisters();
		registers.setProgramCounter(0);

		while (true) {
			Instruction instruction = (Instruction) computer.getMemory()
					.getLocation(registers.getProgramCounter());

			registers.incrementProgramCounter();

			if (instruction.execute(computer) == true) {
				break;
			}
		}

		return true;
	}
}