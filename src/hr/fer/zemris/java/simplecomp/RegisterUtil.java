package hr.fer.zemris.java.simplecomp;

/**
 * <p>
 * Utility class which provides methods used for getting information about the
 * certain register by his register descriptor.
 * </p>
 * 
 * <p>
 * Register descriptor is an integer whose bit on position 24, if 1, indicates
 * indirect addressing, where offset is determined by bits on positions 23 - 8
 * and it <b>can be a negative</b> number. Otherwise, if bit on position 24 is
 * 0, those bits are ignored. Bits on positions 7 - 0 represent an index of the
 * register and <b>can not be negative</b>.
 * </p>
 * 
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
public class RegisterUtil {
	/**
	 * Gets the index of the register, which is determined by the first 8 bits
	 * of the argument. This number is <b>positive</b>.
	 * 
	 * @param registerDescriptor
	 *            descriptor of the register
	 * @return index of the register
	 */
	public static int getRegisterIndex(int registerDescriptor) {
		return registerDescriptor & 0xFF;
	}

	/**
	 * Checks if the provided registerDescriptor indicates that indirect
	 * addressing has to be done. This is determined by checking the
	 * descriptor's 25th bit. If the bit is 1, <code>true</code> is returned,
	 * else <code>false</code>.
	 * 
	 * @param registerDescriptor
	 *            descriptor of the register
	 * @return <code>true</code> if the descriptor indicates that indirect
	 *         addressing has to be done, <code>false</code> otherwise
	 */
	public static boolean isIndirect(int registerDescriptor) {
		return ((registerDescriptor >> 24) & 1) == 1;
	}

	/**
	 * Gets the offset of the register specified by the descriptors bits on
	 * positions 23 - 8. This number can be <b>negative</b>.
	 * 
	 * @param registerDescriptor
	 *            descriptor of the register
	 * @return offset of the register
	 */
	public static int getRegisterOffset(int registerDescriptor) {
		int offset = (registerDescriptor & 0xFFFF00) >> 8;

		// negative number
		if ((offset & 0x8000) != 0) {
			offset = -((~offset + 1) & 0xFFFF);
		}

		return offset;
	}
}