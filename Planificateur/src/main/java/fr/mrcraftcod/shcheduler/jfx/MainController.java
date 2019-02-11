package fr.mrcraftcod.shcheduler.jfx;

import fr.mrcraftcod.shcheduler.jfx.utils.MatchMenuButton;
import fr.mrcraftcod.shcheduler.model.Championship;
import fr.mrcraftcod.shcheduler.model.GroupStage;
import fr.mrcraftcod.shcheduler.model.Gymnasium;
import fr.mrcraftcod.shcheduler.model.Match;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MainController{
	private Championship championship;
	
	public MainController(){}
	
	public Predicate<Match> getWeakConstraints(final GymnasiumMatchTableCell cell){
		return getWeakConstraints(cell.getGroupStage(), cell::getGymnasium, cell.getDate(), List.of());
	}
	
	public void assignMatch(Match item, Gymnasium gymnasium, LocalDate date){
		item.setGymnasium(gymnasium);
		item.setDate(date);
	}
	
	private Predicate<Match> getWeakConstraints(final GroupStage gs, final Supplier<Gymnasium> gym, final LocalDate date, final List<Match> selected){
		final Predicate<Match> predicateCorrectGymnasium = m -> Objects.equals(gym.get(), m.getTeam1().getGymnasium());
		return predicateCorrectGymnasium;
	}
	
	public Predicate<Match> getWeakConstraintsSelection(final MatchMenuButton button){
		return getWeakConstraints(button.getParentCell().getGroupStage(), () -> button.getParentCell().getGymnasium(), button.getParentCell().getDate(), button.getCheckedItems());
	}
	
	public Predicate<Match> getStrongConstraints(final GymnasiumMatchTableCell cell){
		return getStrongConstraints(cell.getGroupStage(), cell::getGymnasium, cell.getDate(), List.of());
	}
	
	public int getRemainingSpace(Gymnasium gymnasium, LocalDate date){
		return Integer.MAX_VALUE;
	}
	
	private Predicate<Match> getStrongConstraints(final GroupStage gs, final Supplier<Gymnasium> gym, final LocalDate date, final List<Match> selected){
		final Predicate<Match> predicateMatchGymnasium = m -> Objects.equals(gym.get(), m.getTeam1().getGymnasium()) || Objects.equals(gym.get(), m.getTeam2().getGymnasium());
		final Predicate<Match> predicateNotAssigned = m -> Objects.isNull(m.getGymnasium());
		final Predicate<Match> predicateFreeGymnasium = m -> isGymnasiumFree(gym.get(), date, selected);
		final Predicate<Match> predicateDontPlaySameDay = m -> gs.getMatches().stream().filter(m2 -> m2.isTeamPlaying(m.getTeam1()) || m2.isTeamPlaying(m.getTeam2())).noneMatch(m2 -> Objects.equals(date, m2.getDate()));
		final Predicate<Match> predicateDontPlaySameDaySelected = m -> selected.stream().noneMatch(m2 -> m2.isTeamPlaying(m.getTeam1()) || m2.isTeamPlaying(m.getTeam2()));
		
		return predicateMatchGymnasium.and(predicateNotAssigned).and(predicateFreeGymnasium).and(predicateDontPlaySameDay).and(predicateDontPlaySameDaySelected);
	}
	
	public boolean isGymnasiumFree(final Gymnasium gymnasium, final LocalDate date, List<Match> selected){
		return gymnasium.getCapacity() > championship.getGroupStages().stream().flatMap(gs -> gs.getMatches().stream()).filter(m -> Objects.equals(gymnasium, m.getGymnasium()) && Objects.equals(date, m.getDate())).count();
	}
	
	public Predicate<Match> getStrongConstraintsSelection(final MatchMenuButton button){
		return getStrongConstraints(button.getParentCell().getGroupStage(), () -> button.getParentCell().getGymnasium(), button.getParentCell().getDate(), button.getCheckedItems());
	}
	
	public void setChampionship(final Championship championship){
		this.championship = championship;
	}
}
