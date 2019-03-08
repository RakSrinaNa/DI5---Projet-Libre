package fr.mrcraftcod.shcheduler.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a championship.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-21.
 *
 * @author Thomas Couchoud
 * @since 2019-01-21
 */
public class Championship{
	private final Collection<GroupStage> groupStages;
	
	private final Collection<LocalDate> dates;
	private int weeksCount;
	
	/**
	 * Constructor.
	 * @param weeksCount
	 */
	public Championship(final int weeksCount){
		this.weeksCount = weeksCount;
		this.groupStages = new ArrayList<>();
		this.dates = new ArrayList<>();
	}
	
	/**
	 * Add a collection group stage.
	 * If the group stage already exists, it won't be added again.
	 *
	 * @param groupStages The groups stages to add.
	 */
	public void addAllGroupStages(final Collection<GroupStage> groupStages){
		groupStages.stream().filter(Objects::nonNull).forEach(this::addGroupStage);
	}
	
	/**
	 * Add a group stage.
	 * If the group stage already exists, it won't be added again.
	 *
	 * @param groupStage The group stage to add.
	 *
	 * @throws IllegalArgumentException If the group stage is null.
	 */
	@SuppressWarnings("WeakerAccess")
	public void addGroupStage(final GroupStage groupStage) throws IllegalArgumentException{
		if(groupStage == null){
			throw new IllegalArgumentException("Championship GroupStage is null");
		}
		if(!this.groupStages.contains(groupStage)){
			this.groupStages.add(groupStage);
		}
	}
	
	/**
	 * Tells if a gymnasium is full at a given date.
	 *
	 * @param gymnasium The gymnasium to check for.
	 * @param date      The date to check at.
	 *
	 * @return True if full, false otherwise.
	 */
	@SuppressWarnings("WeakerAccess")
	public boolean isGymnasiumFull(final Gymnasium gymnasium, final LocalDate date){
		return this.groupStages.stream().flatMap(groupStage -> groupStage.getMatches().stream()).filter(match -> Objects.equals(gymnasium, match.getGymnasium()) && Objects.equals(date, match.getDate())).count() >= gymnasium.getCapacity();
	}
	
	public int getWeeksCount(){
		return this.weeksCount;
	}
	
	/**
	 * Get the group stages.
	 *
	 * @return The group stages.
	 */
	public Collection<GroupStage> getGroupStages(){
		return this.groupStages;
	}
	
	/**
	 * Add a date to the championship.
	 *
	 * @param date The date to add.
	 */
	public void addDate(final LocalDate date){
		if(!this.dates.contains(date)){
			this.dates.add(date);
		}
	}
	
	/**
	 * Get the dates (one per week) of the championship.
	 *
	 * @return The dates of the championship.
	 */
	public Collection<LocalDate> getDates(){
		return this.dates;
	}
}
