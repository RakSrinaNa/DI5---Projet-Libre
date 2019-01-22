package fr.mrcraftcod.shcheduler.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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
	
	public void addAllGroupStages(final Collection<GroupStage> groupStages){
		this.groupStages.addAll(groupStages);
	}
	
	public boolean isGymnasiumFull(final Gymnasium gymnasium, final LocalDate date){
		int found = 0;
	    for(GroupStage group : groupStages)
			for(Match match : group.getMatches()) {
				if (Objects.equals(gymnasium, match.getGymnasium()) && Objects.equals(date, match.getDate()))
					found++;
				if(found >= gymnasium.getCapacity())
					return true;
			}
		return false;
	}
	
	public Collection<GroupStage> getGroupStages(){
		return groupStages;
	}
}
