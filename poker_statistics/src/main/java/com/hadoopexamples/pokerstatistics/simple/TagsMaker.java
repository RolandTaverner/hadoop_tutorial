package com.hadoopexamples.pokerstatistics.simple;

import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import com.hadoopexamples.pokerstatistics.utils.DateExtractor;
import com.hadoopexamples.pokerstatistics.handshistory.Hand;

public class TagsMaker {
	public static SortedMap<Integer, String> makeTags(final Hand hand) {
		SortedMap<Integer, String> tags = new TreeMap<Integer, String>();

		final Date day = DateExtractor.getDatePart(new java.util.Date(hand.getTimestamp()));

		tags.put(Tag.TIMESTAMP.index(), String.valueOf(day.getTime() / 1000));
		tags.put(Tag.GAME_TYPE.index(), hand.getMode());
		tags.put(Tag.GAME_VARIANT.index(), hand.getVariant());
		tags.put(Tag.PLAYERS_COUNT.index(), String.valueOf(hand.getPlayersCount()));
		tags.put(Tag.BIG_BLIND.index(), String.valueOf(hand.getTable().getBigBlind()));
		
		if (hand.getPlayersCount() <= 2) {
			tags.put(Tag.TABLE_TYPE.index(), "HU");
		} else if (hand.getPlayersCount() <= 6) {
			tags.put(Tag.TABLE_TYPE.index(), "6MAX");
		} else {
			tags.put(Tag.TABLE_TYPE.index(), "FR");
		}
		
		return tags;
	}

	public static String getGameType(final SortedMap<Integer, String> tags) {
		return tags.getOrDefault(Tag.GAME_TYPE.index(), "");
	}
}

