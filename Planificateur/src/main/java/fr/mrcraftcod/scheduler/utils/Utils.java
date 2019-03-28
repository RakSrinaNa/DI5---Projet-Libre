package fr.mrcraftcod.scheduler.utils;

import java.time.DayOfWeek;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-03-05.
 *
 * @author Thomas Couchoud
 * @since 2019-03-05
 */
public class Utils{
	/**
	 * Get the difference between monday and the other days.
	 *
	 * @param dayOfWeek The day to get the difference with.
	 *
	 * @return The number of days between monday and the given day.
	 */
	public static long getDaysToRemove(final DayOfWeek dayOfWeek){
		switch(dayOfWeek){
			case MONDAY:
				return 0;
			case TUESDAY:
				return 1;
			case WEDNESDAY:
				return 2;
			case THURSDAY:
				return 3;
			case FRIDAY:
				return 4;
			case SATURDAY:
				return 5;
			case SUNDAY:
				return 6;
		}
		return 0;
	}
}
