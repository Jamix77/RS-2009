package plugin.activity.stealingcreations;

import java.util.EnumSet;

/**
 * @author Metorrite - Drake
 */
public enum StealingCreationPlot {
	/**
	 * Miscellaneous map data
	 */
	KILN(1925, 5715, 0, 0, 5,3),
	MIST(1923, 5724, 0, 0,3,4),
	RIFT(1922, 5731, 0, 0,2,3),
	ALTAR(1930, 5715, 0, 0,5,4),

	/**
	 * Default and base map data
	 */
	DEFAULT(1928, 5704, 0, 0,0,0),
	BASE_BLUE(1920, 5696, 0, 2,0,0),
	BASE_RED(1920, 5696, 0, 0,0,0),

	/**
	 * Fragment map data
	 */
	FRAGMENTS(1971, 5699, 0, 0,3,3),

	/**
	 * Tree plot data
	 */
	TREE_2(1964, 5698, 0, 0,4,2),
	TREE_3(1955, 5698, 0, 0,3,2),
	TREE_4(1947, 5698, 0, 0,3,2),
	TREE_5(1938, 5698, 0, 0,2,2),

	/**
	 * Swarm plot data
	 */
	SWARM_2(1963, 5707, 0, 0,3,3),
	SWARM_3(1955, 5707, 0, 0,3,3),
	SWARM_4(1946, 5707, 0, 0,2,3),
	SWARM_5(1941, 5707, 0, 0,5,3),

	/**
	 * Rock plot data
	 */
	ROCK_2(1963, 5723, 0, 0,3,3),
	ROCK_3(1955, 5723, 0, 0,3,3),
	ROCK_4(1947, 5723, 0, 0,3,3),
	ROCK_5(1939, 5723, 0, 0,3,3),
	
	/**
	 * Pool plot data
	 */
	POOL_2(1963, 5715, 0, 0,3,3),
	POOL_3(1955, 5715, 0, 0,3,3),
	POOL_4(1947, 5715, 0, 0,3,3),
	POOL_5(1939, 5715, 0, 0,3,3);

	private static final EnumSet<StealingCreationPlot> PLOTS = EnumSet.allOf(StealingCreationPlot.class);
	private int locationHash;
	private int chunkX;
	private int chunkY;
	
	private StealingCreationPlot(int x, int y, int z, int rotation, int chunkX,int chunkY) {
		locationHash = x / 8 << 14 | y / 8 << 3 | z % 4 << 24 | rotation % 4 << 1;
	}
	
	public static StealingCreationPlot getRandomResourcePlotByClass(int level) {
		return PLOTS.stream().filter(plot -> plot.toString().contains(" " + level)).findAny().orElse(FRAGMENTS);
	}
	
	public int getLocationHash() {
		return locationHash;
	}
	

	@Override
	public String toString() {
		return name().toLowerCase().replaceAll("_", " ");
	}

	public static EnumSet<StealingCreationPlot> getPlots() {
		return PLOTS;
	}

	public int getChunkY() {
		return chunkY;
	}

	public void setChunkY(int chunkY) {
		this.chunkY = chunkY;
	}

	public int getChunkX() {
		return chunkX;
	}

	public void setChunkX(int chunkX) {
		this.chunkX = chunkX;
	}
}