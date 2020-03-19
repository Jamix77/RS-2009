package org.crandor.tools;

import java.io.PrintWriter;

import org.crandor.cache.def.impl.ObjectDefinition;
import org.crandor.game.world.GameWorld;

/**
 * 
 * @author jamix77
 *
 */
public class ListGenerator {

	
	public static void main(String...strings ) throws Throwable {
		GameWorld.prompt(true);
		PrintWriter writer = new PrintWriter("obj_ids.txt", "UTF-8");
		for (int i = 0; i < 40000; i++) {
				ObjectDefinition def = ObjectDefinition.forId(i);
				writer.println(def.getId() + ":" + def.getName());
				 
		}
		writer.close();
		System.exit(0);
	}

}
