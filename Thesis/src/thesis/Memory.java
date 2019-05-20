package thesis;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Memory {

	private int blockSize = 4;
	private BufferedWriter writer;
	private List<String[]> L1_Caches;
	private String[] L2_Cache;
	private float L1_Access = 0;
	private float L1_Misses = 0;
	private float L1_Hits = 0;
	private float L2_Access = 0;
	private float L2_Misses = 0;
	private float L2_Hits = 0;

	public Memory(BufferedWriter wr) throws IOException {
		this.writer = wr;
		L1_Caches = new ArrayList<String[]>();
		L1_Caches.add(new String[512]);
		L1_Caches.add(new String[512]);
		L1_Caches.add(new String[512]);
		L1_Caches.add(new String[512]);
		L2_Cache = new String[1024];

		for (int i = 0; i < 512; i++) {
			for (int j = 0; j < 4; j++) {
				L1_Caches.get(j)[i] = "";
			}
		}
		for (int i = 0; i < 1024; i++) {
			L2_Cache[i] = "";
		}

	}

	public void process(Operation operation) throws IOException {
		if (operation.getOperationType() == 'R') {
			read_L1(operation);
		} else {
			write_L1(operation);
		}
	}

	public void read_L1(Operation o) throws IOException {
		L1_Access++;
		if (L1_Caches.get(o.getThreadId())[Integer.parseInt(o.getIndex_L1(), 2)].equals(o.getTag_L1())) {
			L1_Hits++;
			int temp = (Integer.parseInt(o.getIndex_L1(), 2) / blockSize) * blockSize;
			for (int i = 0; i < blockSize; i++) {
				L1_Caches.get(o.getThreadId())[temp + i] = o.getTag_L1();
				writer.write("L1 " + (temp + i) + " " + o.getOperationType() + " " + o.getThreadId() + " Hit "
						+ (((Long.parseLong(o.getMemoryAddress(), 16) / 255) / 255) % 255) + " "
						+ ((Long.parseLong(o.getMemoryAddress(), 16) / 255) % 255) + " "
						+ (Long.parseLong(o.getMemoryAddress(), 16) % 255) + "\n");
			}
		} else {
			L1_Misses++;
			int temp = (Integer.parseInt(o.getIndex_L1(), 2) / blockSize) * blockSize;
			for (int i = 0; i < blockSize; i++) {
				writer.write("L1 " + (temp + i) + " " + o.getOperationType() + " " + o.getThreadId() + " Miss "
						+ (((Long.parseLong(o.getMemoryAddress(), 16) / 255) / 255) % 255) + " "
						+ ((Long.parseLong(o.getMemoryAddress(), 16) / 255) % 255) + " "
						+ (Long.parseLong(o.getMemoryAddress(), 16) % 255) + "\n");
			}
			read_L2(o);
			for (int i = 0; i < blockSize; i++) {
				L1_Caches.get(o.getThreadId())[temp + i] = o.getTag_L1();
				writer.write("L1 " + (temp + i) + " " + o.getOperationType() + " " + o.getThreadId() + " Hit "
						+ (((Long.parseLong(o.getMemoryAddress(), 16) / 255) / 255) % 255) + " "
						+ ((Long.parseLong(o.getMemoryAddress(), 16) / 255) % 255) + " "
						+ (Long.parseLong(o.getMemoryAddress(), 16) % 255) + "\n");
			}
		}
	}

	public void read_L2(Operation o) throws NumberFormatException, IOException {
		L2_Access++;
		if (L2_Cache[Integer.parseInt(o.getIndex_L2(), 2)].equals(o.getTag_L2())) {
			L2_Hits++;
			int temp = (Integer.parseInt(o.getIndex_L2(), 2) / blockSize) * blockSize;
			for (int i = 0; i < blockSize; i++) {
				L2_Cache[temp + i] = o.getTag_L2();
				writer.write("L2 " + (temp + i) + " " + o.getOperationType() + " " + o.getThreadId() + " Hit "
						+ (((Long.parseLong(o.getMemoryAddress(), 16) / 255) / 255) % 255) + " "
						+ ((Long.parseLong(o.getMemoryAddress(), 16) / 255) % 255) + " "
						+ (Long.parseLong(o.getMemoryAddress(), 16) % 255) + "\n");
			}
		} else {
			L2_Misses++;
			int temp = (Integer.parseInt(o.getIndex_L2(), 2) / blockSize) * blockSize;
			for (int i = 0; i < blockSize; i++) {
				writer.write("L2 " + (temp + i) + " " + o.getOperationType() + " " + o.getThreadId() + " Miss "
						+ (((Long.parseLong(o.getMemoryAddress(), 16) / 255) / 255) % 255) + " "
						+ ((Long.parseLong(o.getMemoryAddress(), 16) / 255) % 255) + " "
						+ (Long.parseLong(o.getMemoryAddress(), 16) % 255) + "\n");
			}
			read_main(o);
			for (int i = 0; i < blockSize; i++) {
				L2_Cache[temp + i] = o.getTag_L2();
				writer.write("L2 " + (temp + i) + " " + o.getOperationType() + " " + o.getThreadId() + " Hit "
						+ (((Long.parseLong(o.getMemoryAddress(), 16) / 255) / 255) % 255) + " "
						+ ((Long.parseLong(o.getMemoryAddress(), 16) / 255) % 255) + " "
						+ (Long.parseLong(o.getMemoryAddress(), 16) % 255) + "\n");
			}
		}
	}

	public void read_main(Operation o) throws NumberFormatException, IOException {
		long temp = (Long.parseLong(o.getMemoryAddress(), 16) / blockSize) * blockSize;
		for (int i = 0; i < blockSize; i++) {
			writer.write("Main " + (temp + i) + " " + o.getOperationType() + " " + o.getThreadId() + " Hit "
					+ (((Long.parseLong(o.getMemoryAddress(), 16) / 255) / 255) % 255) + " "
					+ ((Long.parseLong(o.getMemoryAddress(), 16) / 255) % 255) + " "
					+ (Long.parseLong(o.getMemoryAddress(), 16) % 255) + "\n");
		}
	}

	public void write_L1(Operation o) throws NumberFormatException, IOException {
		int temp = (Integer.parseInt(o.getIndex_L1(), 2) / blockSize) * blockSize;
		for (int i = 0; i < blockSize; i++) {
			L1_Caches.get(o.getThreadId())[temp + i] = o.getTag_L1();
			writer.write("L1 " + (temp + i) + " " + o.getOperationType() + " " + o.getThreadId() + " Hit "
					+ (((Long.parseLong(o.getMemoryAddress(), 16) / 255) / 255) % 255) + " "
					+ ((Long.parseLong(o.getMemoryAddress(), 16) / 255) % 255) + " "
					+ (Long.parseLong(o.getMemoryAddress(), 16) % 255) + "\n");
		}
		write_L2(o);
	}

	public void write_L2(Operation o) throws NumberFormatException, IOException {
		int temp = (Integer.parseInt(o.getIndex_L2(), 2) / blockSize) * blockSize;
		for (int i = 0; i < blockSize; i++) {
			L2_Cache[temp + i] = o.getTag_L2();
			writer.write("L2 " + (temp + i) + " " + o.getOperationType() + " " + o.getThreadId() + " Hit "
					+ (((Long.parseLong(o.getMemoryAddress(), 16) / 255) / 255) % 255) + " "
					+ ((Long.parseLong(o.getMemoryAddress(), 16) / 255) % 255) + " "
					+ (Long.parseLong(o.getMemoryAddress(), 16) % 255) + "\n");
		}
		write_main(o);
	}

	public void write_main(Operation o) throws NumberFormatException, IOException {
		long temp = (Long.parseLong(o.getMemoryAddress(), 16) / blockSize) * blockSize;
		for (int i = 0; i < blockSize; i++) {
			writer.write("Main " + (temp + i) + " " + o.getOperationType() + " " + o.getThreadId() + " Hit "
					+ (((Long.parseLong(o.getMemoryAddress(), 16) / 255) / 255) % 255) + " "
					+ ((Long.parseLong(o.getMemoryAddress(), 16) / 255) % 255) + " "
					+ (Long.parseLong(o.getMemoryAddress(), 16) % 255) + "\n");
		}
	}

	public float getL1_Access() {
		return L1_Access;
	}

	public float getL1_Misses() {
		return L1_Misses;
	}

	public float getL1_Hits() {
		return L1_Hits;
	}

	public float getL2_Access() {
		return L2_Access;
	}

	public float getL2_Misses() {
		return L2_Misses;
	}

	public float getL2_Hits() {
		return L2_Hits;
	}

}
