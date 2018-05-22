package hr.fer.zemris.java.simplecomp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * Class which simulates the work of the class {@link ExecutionUnitImpl}. A
 * single argument has to be provided: path to the text file which contains
 * assembly code. This argument can be provided trough command line or trough
 * standard input (if no arguments have been provided trough command line).
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class Simulator {
	/**
	 * Method which starts the program.
	 * 
	 * @param args
	 *            command line arguments
	 * @throws Exception
	 *             if there has been an error while parsing
	 */
	public static void main(String[] args) throws Exception {
		String fileName = getFileName(args);

		// Stvori računalo s 256 memorijskih lokacija i 16 registara
		Computer comp = new ComputerImpl(256, 16);

		// Stvori objekt koji zna stvarati primjerke instrukcija
		InstructionCreator creator = new InstructionCreatorImpl(
				"hr.fer.zemris.java.simplecomp.impl.instructions");

		// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
		// uporabom predanog objekta za stvaranje instrukcija
		ProgramParser.parse(fileName, comp, creator);

		try {
			// Stvori izvršnu jedinicu
			ExecutionUnit exec = new ExecutionUnitImpl();

			// Izvedi program
			exec.go(comp);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Help method which checks if user has entered valid arguments. If args
	 * array contains a single argument, that argument is returned as the name
	 * of the file. If args array is empty, appropriate message is written and
	 * user has to enter name of the file. Otherwise, appropriate message is
	 * written to user and program will be terminated.
	 * 
	 * @param args
	 *            command line arguments
	 * @return name of the file with assembly code
	 * @throws IOException
	 *             if there has been an input error
	 */
	private static String getFileName(String[] args) throws IOException {
		if (args.length == 1) {
			return args[0];

		} else if (args.length == 0) {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(System.in)));

			System.out.println(
					"Please provide path to the file with assemly code: ");
			System.out.print(">");

			return reader.readLine().trim();

		} else {
			System.err.println("Expected none or a single argument!");
			System.exit(-1);
		}

		return null;
	}
}