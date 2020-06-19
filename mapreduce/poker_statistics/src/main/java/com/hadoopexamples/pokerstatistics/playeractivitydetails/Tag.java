package com.hadoopexamples.pokerstatistics.playeractivitydetails;

public enum Tag {
	TIMESTAMP(0),
	PLAYER_ID(1), 
	GAME_TYPE(2), 
	GAME_VARIANT(3),
	PLAYERS_COUNT(4), 
	BIG_BLIND(5),
	TABLE_TYPE(6);
	
	private final int m_index;

	Tag(int index) {
		m_index = index;
	}

	public int index() {
		return m_index;
	}
}