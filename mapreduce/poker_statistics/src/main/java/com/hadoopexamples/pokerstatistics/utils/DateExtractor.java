package com.hadoopexamples.pokerstatistics.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateExtractor {

	public static Date getDatePart(final Date date) {
		Calendar c1 = new GregorianCalendar(m_timeZone);
		c1.setTime(date);

		Calendar c2 = new GregorianCalendar(m_timeZone);
		c2.clear();
		c2.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH));

		return c2.getTime();
	}

	private static final TimeZone m_timeZone = TimeZone.getTimeZone("UTC");
}
