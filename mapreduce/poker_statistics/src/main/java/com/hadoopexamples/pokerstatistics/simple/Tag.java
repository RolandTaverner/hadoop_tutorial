package com.hadoopexamples.pokerstatistics.simple;

public enum Tag {
	TIMESTAMP(0), 
	GAME_TYPE(1),
	GAME_VARIANT(2),
	PLAYERS_COUNT(3), 
	BIG_BLIND(4),
	TABLE_TYPE(5);

	private final int m_index;

	Tag(int index) {
		m_index = index;
	}

	public int index() {
		return m_index;
	}
}
