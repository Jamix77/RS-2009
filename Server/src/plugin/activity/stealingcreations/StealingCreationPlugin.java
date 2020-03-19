package plugin.activity.stealingcreations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.crandor.game.content.activity.ActivityPlugin;
import org.crandor.game.content.skill.member.construction.Room;
import org.crandor.game.node.entity.player.Player;
import org.crandor.game.world.map.BuildRegionChunk;
import org.crandor.game.world.map.Location;
import org.crandor.game.world.map.Region;
import org.crandor.game.world.map.RegionChunk;
import org.crandor.game.world.map.RegionManager;
import org.crandor.game.world.map.build.DynamicRegion;
import org.crandor.game.world.map.zone.ZoneBorders;
import org.crandor.game.world.map.zone.ZoneBuilder;
import org.crandor.game.world.map.zone.ZoneRestriction;
import org.crandor.tools.RandomFunction;

/**
 * The stealing creations minigame plugin.
 * @author jamix77
 *
 */
public class StealingCreationPlugin extends ActivityPlugin {
	
	private Player host;
	
	/**
	 * List of all available plots that can be used for random plots
	 */
	private final List<int[]> plotList = new ArrayList<>();
	
	/**
	 * The plots chunk data
	 */
	private final StealingCreationPlot[][][] plotChunks = new StealingCreationPlot[13][13][4];
	
	/**
	 * Players in the game currently.
	 */
	private final List<Player> players = new LinkedList<>();

	/**
	 * 
	 * Constructs a new @{Code StealingCreationPlugin} object.
	 * @param p Player
	 */
	public StealingCreationPlugin(List<Player> players) {
		super("stealing creations", true, true, true, ZoneRestriction.CANNON, ZoneRestriction.FOLLOWERS,ZoneRestriction.RANDOM_EVENTS);
		this.players.addAll(players);
		host = this.players.get(0);
	}

	@Override
	public ActivityPlugin newInstance(Player p) throws Throwable {
		return this;
	}

	/**
	 * temporarily in lumby
	 */
	@Override
	public Location getSpawnLocation() {
		return Location.create(3200, 3200, 0);
	}

	
	/**
	 * configure a new instance of stealing creations place thingy.
	 */
	@Override
	public void configure() {
		region = DynamicRegion.create(7769);
		setRegionBase();
		registerRegion(region.getId());
	}
	
	
	
	public Region construct() {
		
		StealingCreationPlot plot = StealingCreationPlot.DEFAULT;
		int[] currentPlot = new int[3];
		int[] plots = {
				2 + RandomFunction.random(2),//class 1 type
				2 + RandomFunction.random(1),//class 2 type
				2 + RandomFunction.random(1),//class 3 type
				1 + RandomFunction.random(2),//class 4 type
				1 + RandomFunction.random(1),//class 5 type
				
				2 + RandomFunction.random(2),//base min
				3 + RandomFunction.random(1),//kiln min
				1 + RandomFunction.random(1),//altar min
				1 + RandomFunction.random(1),//rift min
		};
		
		for(int x=3; x<8+3; x++) {
			for(int y=3; y<8+3; y++) {
				
				if((x == 3 && y == 3) || (x == 8+2 && y == 8+2))
					plotChunks[x][y][0] = x == 3 ? StealingCreationPlot.MIST : StealingCreationPlot.MIST;
				else if((x == 3 && y == 4) ||  (x == 4 && y == 3) 
							|| (x == 8+3 - 1 && y == 8+3) 
								|| (x == 8+3 && y == 8+3 - 1))
					plotChunks[x][y][0] = StealingCreationPlot.MIST;
				else {
					plotChunks[x][y][0] = StealingCreationPlot.MIST;
					plotList.add(new int[]{x, y, 0});
				}
			}
		}
		Collections.shuffle(plotList);
		for(int i=0; i<plots.length; i++) {
			if(i <= 4) {
				plot = StealingCreationPlot.MIST;
			}else {
				switch(i){
				case 5:
					plot = StealingCreationPlot.MIST;
					break;
				case 6:
					plot = StealingCreationPlot.MIST;
					break;
				case 7:
					plot = StealingCreationPlot.MIST;
					break;
				case 8:
					plot = StealingCreationPlot.KILN;
					break;
				}
			}
			for(int j=0; j<plots[i]; j++) {
				currentPlot = plotList.get(0);
				plotChunks[currentPlot[0]][currentPlot[1]][currentPlot[2]] = plot;
				plotList.remove(0);
			}
		}
		
		
		
		Region from = RegionManager.forId(7769);
		Region.load(from, true);
		RegionChunk defaultChunk = from.getPlanes()[0].getRegionChunk(1, 0);
		ZoneBorders borders = DynamicRegion.reserveArea(8, 8);
		region = new DynamicRegion(7769, borders.getSouthWestX() >> 6, borders.getSouthWestY() >> 6);
		region.setBorders(borders);
		region.setUpdateAllPlanes(true);
		RegionManager.getRegionCache().put(region.getId(), region);
		for (int z = 0; z < 1; z++) {
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					StealingCreationPlot plot1 = plotChunks[x][y][z];
					if (plot1 != null) {
						BuildRegionChunk copy = region.getPlanes()[z].getRegionChunk(plot1.getChunkX(),plot1.getChunkY()).copy(region.getPlanes()[z]);
						region.replaceChunk(z, x, y, copy, from);
					} else {
						region.replaceChunk(z, x, y,defaultChunk.copy(region.getPlanes()[0]), from);
					}
				}
			}
		}
		players.forEach(player -> player.getProperties().setTeleportLocation(new Location(borders.getRandomLoc().getX(),borders.getRandomLoc().getY(), host.getId() * 4)));
		players.forEach(player -> {
			System.out.println(player.getName());
			
		});
		return region;
	}

}
