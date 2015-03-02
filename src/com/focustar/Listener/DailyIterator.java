package com.focustar.Listener;

import java.util.Calendar;
import java.util.Date;

import org.tiling.scheduling.ScheduleIterator;

public class DailyIterator implements ScheduleIterator {
	private int hourOfDay = 0, minute = 0, second = 0;

	private int offset = 1;

	private final Calendar calendar = Calendar.getInstance();

	private int changeDateKind = Calendar.DATE;

	public DailyIterator(int hourOfDay, int minute, int second) {
		this(hourOfDay, minute, second, new Date(), 1);
	}

	public DailyIterator(int date, int noset) {
		this(date, 0, 0, 0, noset);
	}

	public DailyIterator(int hourOfDay, int minute, int second, int offset) {
		this(hourOfDay, minute, second, new Date(), offset);
	}

	public DailyIterator(int offset) {
		this(0, 0, 0, new Date(), offset);
	}

	public DailyIterator(int date, int hourOfDay, int minute, int second, int noset) {
		this.changeDateKind = Calendar.MONTH;
		this.hourOfDay = hourOfDay;
		this.minute = minute;
		this.second = second;
		Date now = calendar.getTime();
		calendar.set(Calendar.DATE, date);
		calendar.set(Calendar.HOUR_OF_DAY, this.hourOfDay);
		calendar.set(Calendar.MINUTE, this.minute);
		calendar.set(Calendar.SECOND, this.second);
		calendar.set(Calendar.MILLISECOND, 0);
		if (!calendar.getTime().before(now)) {
			calendar.add(changeDateKind, -1);
		}
	}

	public DailyIterator(int hourOfDay, int minute, int second, Date date, int offset) {
		this.changeDateKind = Calendar.DATE;
		this.offset = offset;
		this.hourOfDay = hourOfDay;
		this.minute = minute;
		this.second = second;
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, this.hourOfDay);
		calendar.set(Calendar.MINUTE, this.minute);
		calendar.set(Calendar.SECOND, this.second);
		calendar.set(Calendar.MILLISECOND, 0);
		if (!calendar.getTime().before(date)) {
			calendar.add(changeDateKind, -offset);
		}
	}

	public Date next() {
		calendar.add(changeDateKind, offset);
		Date date = calendar.getTime();
		return date;
	}

	public void setChangeDateKind(int changeDateKind) {
		this.changeDateKind = changeDateKind;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
