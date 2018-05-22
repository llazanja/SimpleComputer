package hr.fer.zemris.java.simplecomp;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrCall;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrLoad;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrMove;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrPop;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrPush;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrRet;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class InstructionsTests {

	@Mock
	private Computer computer;

	@Mock
	private Memory memory;

	@Mock
	private Registers registers;

	@Mock
	private InstructionArgument arg1;

	@Mock
	private InstructionArgument arg2;

	@Mock
	private InstructionArgument arg3;

	@Test
	public void testValidLoadInstruction() {
		initialization();

		initArgAsRegister(arg1, 7, true);
		initArgAsMemoryLocation(arg2, 10, true);

		when(memory.getLocation(10)).thenReturn("Đava ftw!");

		List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(arg1);
		arguments.add(arg2);

		InstrLoad load = new InstrLoad(arguments);

		load.execute(computer);

		verify(arg1, atLeastOnce()).isRegister();
		verify(arg2, atLeastOnce()).isNumber();
		verify(registers, times(1)).setRegisterValue(7, "Đava ftw!");
	}

	// Invalid number of arguments
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidLoadInstructionConstructor1() {
		List<InstructionArgument> arguments = new ArrayList<>();

		initArgAsRegister(arg1, 7, true);

		arguments.add(arg1);

		new InstrLoad(arguments);
	}

	// Invalid first argument format
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidLoadInstructionConstructor2() {
		List<InstructionArgument> arguments = new ArrayList<>();

		initArgAsRegister(arg1, 7, false);

		arguments.add(arg1);
		arguments.add(arg2);

		new InstrLoad(arguments);
	}

	// Invalid second argument format
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidLoadInstructionConstructor3() {
		List<InstructionArgument> arguments = new ArrayList<>();

		initArgAsRegister(arg1, 7, true);

		initArgAsMemoryLocation(arg2, 10, false);

		arguments.add(arg1);
		arguments.add(arg2);

		new InstrLoad(arguments);
	}

	// Both arguments are registers
	@Test
	public void testValidMoveInstruction1() {
		initialization();

		initArgAsRegister(arg1, 7, true);
		initArgAsRegister(arg2, 8, true);

		when(registers.getRegisterValue(8)).thenReturn("kukumar");

		List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(arg1);
		arguments.add(arg2);

		InstrMove move = new InstrMove(arguments);

		move.execute(computer);

		verify(computer, atLeastOnce()).getRegisters();
		verify(registers, times(1)).getRegisterValue(8);
		verify(registers, times(1)).setRegisterValue(7, "kukumar");
	}

	// First argument is a register, second argument is a number
	@Test
	public void testValidMoveInstruction2() {
		initialization();

		initArgAsRegister(arg1, 7, true);
		initArgAsNumber(arg2, 42, true);

		List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(arg1);
		arguments.add(arg2);

		InstrMove move = new InstrMove(arguments);

		move.execute(computer);

		verify(computer, atLeastOnce()).getRegisters();
		verify(registers, times(1)).setRegisterValue(7, 42);
	}

	// First argument is a register, second argument is an indirect address (r7,
	// offset 1)
	@Test
	public void testValidMoveInstruction3() {
		initialization();

		initArgAsRegister(arg1, 10, true);
		initArgAsRegister(arg2, 0b1000000000000000100000111, true);

		when(registers.getRegisterValue(7)).thenReturn(9);
		when(memory.getLocation(10)).thenReturn("Đava");

		List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(arg1);
		arguments.add(arg2);

		InstrMove move = new InstrMove(arguments);

		move.execute(computer);

		verify(computer, atLeastOnce()).getRegisters();
		verify(registers, times(1)).setRegisterValue(10, "Đava");
	}

	// First argument is an indirect address(r0, offset 5), second argument is
	// an indirect address (r7, offset 1)
	@Test
	public void testValidMoveInstruction4() {
		initialization();

		initArgAsRegister(arg1, 0b1000000000000010100000000, true);
		initArgAsRegister(arg2, 0b1000000000000000100000111, true);

		when(registers.getRegisterValue(0)).thenReturn(0);
		when(registers.getRegisterValue(7)).thenReturn(9);
		when(memory.getLocation(10)).thenReturn("Đava");

		List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(arg1);
		arguments.add(arg2);

		InstrMove move = new InstrMove(arguments);

		move.execute(computer);

		verify(computer, atLeastOnce()).getRegisters();
		verify(memory, times(1)).setLocation(5, "Đava");
	}

	// First argument is an indirect address(r0, offset 5), second argument is
	// an number
	@Test
	public void testValidMoveInstruction5() {
		initialization();

		initArgAsRegister(arg1, 0b1000000000000010100000000, true);
		initArgAsNumber(arg2, 42, true);

		when(registers.getRegisterValue(0)).thenReturn(0);

		List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(arg1);
		arguments.add(arg2);

		InstrMove move = new InstrMove(arguments);

		move.execute(computer);

		verify(memory, times(1)).setLocation(5, 42);
	}

	// First argument is an indirect address(r0, offset 5), second argument is
	// a register
	@Test
	public void testValidMoveInstruction6() {
		initialization();

		initArgAsRegister(arg1, 0b1000000000000010100000000, true);
		initArgAsRegister(arg2, 8, true);

		when(registers.getRegisterValue(0)).thenReturn(0);
		when(registers.getRegisterValue(8)).thenReturn(42);

		List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(arg1);
		arguments.add(arg2);

		InstrMove move = new InstrMove(arguments);

		move.execute(computer);

		verify(registers, times(1)).getRegisterValue(8);
		verify(memory, times(1)).setLocation(5, 42);
	}

	// Invalid number of arguments
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidMoveInstructionConstructor1() {
		List<InstructionArgument> arguments = new ArrayList<>();

		initArgAsRegister(arg1, 7, true);

		arguments.add(arg1);

		new InstrMove(arguments);
	}

	// Invalid first argument format
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidMoveInstructionConstructor2() {
		List<InstructionArgument> arguments = new ArrayList<>();

		initArgAsMemoryLocation(arg1, 7, false);

		arguments.add(arg1);

		new InstrMove(arguments);
	}

	// Invalid second argument format
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidMoveInstructionConstructor3() {
		List<InstructionArgument> arguments = new ArrayList<>();

		initArgAsRegister(arg1, 7, true);
		initArgAsString(arg2, "tekst", true);

		arguments.add(arg1);
		arguments.add(arg2);

		new InstrMove(arguments);
	}

	@Test
	public void testValidPushInstruction() {
		initialization();

		initArgAsRegister(arg1, 0, true);

		List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(arg1);

		InstrPush push = new InstrPush(arguments);

		when(registers.getRegisterValue(0)).thenReturn("Đava");
		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX))
				.thenReturn(200);

		push.execute(computer);

		verify(memory, times(1)).setLocation(200, "Đava");
		verify(registers, times(1))
				.setRegisterValue(Registers.STACK_REGISTER_INDEX, 199);
	}

	// Invalid number of arguments
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidPushInstructionConstructor1() {
		List<InstructionArgument> arguments = new ArrayList<>();

		initArgAsRegister(arg1, 0, true);

		arguments.add(arg1);
		arguments.add(arg2);

		new InstrPush(arguments);
	}

	// Invalid argument format
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidPushInstructionConstructor2() {
		List<InstructionArgument> arguments = new ArrayList<>();

		initArgAsRegister(arg1, 0, false);

		arguments.add(arg1);

		new InstrPush(arguments);
	}

	@Test
	public void testValidPopInstruction() {
		initialization();

		initArgAsRegister(arg1, 0, true);

		List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(arg1);

		InstrPop pop = new InstrPop(arguments);

		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX))
				.thenReturn(200);
		when(memory.getLocation(201)).thenReturn("Đava");

		pop.execute(computer);

		verify(registers, times(1)).setRegisterValue(0, "Đava");
		verify(registers, times(1))
				.setRegisterValue(Registers.STACK_REGISTER_INDEX, 201);
	}

	// Invalid number of arguments
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidPopInstructionConstructor1() {
		List<InstructionArgument> arguments = new ArrayList<>();

		initArgAsRegister(arg1, 0, true);

		arguments.add(arg1);
		arguments.add(arg2);

		new InstrPop(arguments);
	}

	// Invalid argument format
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidPopInstructionConstructor2() {
		List<InstructionArgument> arguments = new ArrayList<>();

		initArgAsRegister(arg1, 0, false);

		arguments.add(arg1);

		new InstrPop(arguments);
	}

	@Test
	public void testValidCallInstruction() {
		initialization();

		initArgAsNumber(arg1, 15, true);

		List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(arg1);

		InstrCall call = new InstrCall(arguments);

		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX))
				.thenReturn(200);
		when(registers.getProgramCounter()).thenReturn(10);

		call.execute(computer);

		verify(memory, times(1)).setLocation(200, 10);
		verify(registers, times(1)).setProgramCounter(15);
		verify(registers, times(1))
				.setRegisterValue(Registers.STACK_REGISTER_INDEX, 199);
	}

	// Invalid number of arguments
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidCallInstructionConstructor1() {
		List<InstructionArgument> arguments = new ArrayList<>();

		initArgAsNumber(arg1, 0, true);

		arguments.add(arg1);
		arguments.add(arg2);

		new InstrCall(arguments);
	}

	// Invalid argument format
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidCallInstructionConstructor2() {
		List<InstructionArgument> arguments = new ArrayList<>();

		initArgAsNumber(arg1, 0, false);

		arguments.add(arg1);

		new InstrCall(arguments);
	}

	@Test
	public void testValidRetInstruction() {
		initialization();

		List<InstructionArgument> arguments = new ArrayList<>();

		InstrRet ret = new InstrRet(arguments);

		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX))
				.thenReturn(200);
		when(memory.getLocation(201)).thenReturn(15);

		ret.execute(computer);

		verify(registers, times(1)).setProgramCounter(15);
		verify(registers, times(1))
				.setRegisterValue(Registers.STACK_REGISTER_INDEX, 201);
	}

	// Invalid number of arguments
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidRetInstructionConstructor() {
		List<InstructionArgument> arguments = new ArrayList<>();

		initArgAsRegister(arg1, 0, false);

		arguments.add(arg1);

		new InstrRet(arguments);
	}

	private void initArgAsString(InstructionArgument arg, String text,
			boolean isString) {
		when(arg.isString()).thenReturn(isString);
		when(arg.getValue()).thenReturn(text);
	}

	private void initArgAsNumber(InstructionArgument arg, int num,
			boolean isNumber) {
		when(arg.isNumber()).thenReturn(isNumber);
		when(arg.getValue()).thenReturn(num);
	}

	private void initialization() {
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
	}

	private void initArgAsRegister(InstructionArgument arg, int regNum,
			boolean isRegister) {
		when(arg.isRegister()).thenReturn(isRegister);
		when(arg.getValue()).thenReturn(regNum);
	}

	private void initArgAsMemoryLocation(InstructionArgument arg, int location,
			boolean isMemoryLocation) {
		when(arg.isNumber()).thenReturn(isMemoryLocation);
		when(arg.getValue()).thenReturn(location);
	}
}