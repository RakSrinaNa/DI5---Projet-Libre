package fr.mrcraftcod.shcheduler.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-21.
 *
 * @author Thomas Couchoud
 * @since 2019-01-21
 */
public class Championship{
	private final Collection<GroupStage> groupStages;
	
	public Championship(){
		this.groupStages = new ArrayList<>();
	}
	
	public void addGroupStage(final GroupStage groupStage){
		this.groupStages.add(groupStage);
	}
	
	public void addAllGroupStage(final Collection<GroupStage> groupStages){
		this.groupStages.addAll(groupStages);
	}
	
	public boolean isGymnasimFull(final Gymnasium gymnasium, final LocalDate date){
		return false;
	}
	
	public Collection<GroupStage> getGroupStages(){
		return groupStages;
	}
}
