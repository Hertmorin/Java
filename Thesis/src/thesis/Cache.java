package thesis;

public class Cache {
	
	@SuppressWarnings("unused")
	private int blockSize;
	private String[] address;
	private String[] blocks;
	
	public Cache(int blockSize) {
		
		this.blockSize = blockSize;
		address = new String[blockSize];
		blocks = new String[blockSize];
		
		for(int i=0; i < blockSize; i++){
			address[i] = "";
			blocks[i] = "";
		}
	}
	
	public boolean inCache(int index, String tag){
		
		if(blocks[index].equals(tag)){
			return true;
		}
		// burda istersen counter tut miss rate bulunur.
		return false;
	}
	
	public void load(int index, String tag){
		blocks[index] = tag;		
	}
}
