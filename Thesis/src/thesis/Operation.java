package thesis;

public class Operation {

	private String memoryType;
	private String memoryAddress;
	private char operationType;
	private int threadId;
	private String index_L1;
	private String tag_L1;
	private String index_L2;
	private String tag_L2;
	private String binaryAddress;
	private int L1_index_bit = 9;
	private int L2_index_bit = 10;

	public Operation(String memoryType, String memoryAddress, char operationType, int threadId) {
		this.memoryType = memoryType;
		this.memoryAddress = memoryAddress;
		this.operationType = operationType;
		this.threadId = threadId;
		this.binaryAddress = hexToBinary(memoryAddress);
		this.index_L1 = getIndex(binaryAddress, L1_index_bit);
		this.tag_L1 = getTag(binaryAddress, L1_index_bit);
		this.index_L2 = getIndex(binaryAddress, L2_index_bit);
		this.tag_L2 = getTag(binaryAddress, L2_index_bit);
	}

	public String getIndex(String binaryAddress, int indexBit) {

		return binaryAddress.substring(binaryAddress.length() - indexBit);

	}

	public String getTag(String binaryAddress, int indexBit) {

		return binaryAddress.substring(0, binaryAddress.length() - indexBit);
	}

	public String hexToBinary(String hexDecimal) {

		String binaryAddress = "";
		char[] c = hexDecimal.toCharArray();

		int i = 0;

		while (i < c.length) {

			switch (c[i]) {
			case '0':
				binaryAddress = binaryAddress.concat("0000");
				break;
			case '1':
				binaryAddress = binaryAddress.concat("0001");
				break;
			case '2':
				binaryAddress = binaryAddress.concat("0010");
				break;
			case '3':
				binaryAddress = binaryAddress.concat("0011");
				break;
			case '4':
				binaryAddress = binaryAddress.concat("0100");
				break;
			case '5':
				binaryAddress = binaryAddress.concat("0101");
				break;
			case '6':
				binaryAddress = binaryAddress.concat("0110");
				break;
			case '7':
				binaryAddress = binaryAddress.concat("0111");
				break;
			case '8':
				binaryAddress = binaryAddress.concat("1000");
				break;
			case '9':
				binaryAddress = binaryAddress.concat("1001");
				break;
			case 'A':
			case 'a':
				binaryAddress = binaryAddress.concat("1010");
				break;
			case 'B':
			case 'b':
				binaryAddress = binaryAddress.concat("1011");
				break;
			case 'C':
			case 'c':
				binaryAddress = binaryAddress.concat("1100");
				break;
			case 'D':
			case 'd':
				binaryAddress = binaryAddress.concat("1101");
				break;
			case 'E':
			case 'e':
				binaryAddress = binaryAddress.concat("1110");
				break;
			case 'F':
			case 'f':
				binaryAddress = binaryAddress.concat("1111");
				break;
			default:
				System.out.print("\nInvalid hexadecimal digit " + c[i]);
			}
			i++;
		}
		return binaryAddress;
	}

	public String getMemoryType() {
		return memoryType;
	}

	public String getMemoryAddress() {
		return memoryAddress;
	}

	public char getOperationType() {
		return operationType;
	}

	public int getThreadId() {
		return threadId;
	}

	public String getIndex_L1() {
		return index_L1;
	}

	public String getTag_L1() {
		return tag_L1;
	}

	public String getIndex_L2() {
		return index_L2;
	}

	public String getTag_L2() {
		return tag_L2;
	}

	public String getBinaryAddress() {
		return binaryAddress;
	}

	public String toString() {
		return memoryType + " " + memoryAddress + " " + operationType + " " + threadId;
	}

}
