package com.barcicki.gorcalculator.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barcicki.gorcalculator.core.Player;
import com.barcicki.gorcalculator.libs.MathUtils;

public class DummyContent {

	public static List<Player> ITEMS = new ArrayList<Player>();
	public static Map<String, Player> ITEM_MAP = new HashMap<String, Player>();

	static {
		for (int i = 0; i < 10; i++) {
			int rank = MathUtils.random(Player.MIN_GOR, Player.MAX_GOR);
			addItem(new Player("Player", "P " + (i + 1), "POZ", "PL", 1400 + i, Player.calculateRanking(rank), rank));
		}
	}

	private static void addItem(Player item) {
		ITEMS.add(item);
		ITEM_MAP.put(Integer.toString(item.getPin()), item);
	}
}
