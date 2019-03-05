package fr.mrcraftcod.shcheduler.jfx;

import fr.mrcraftcod.shcheduler.Utils;
import fr.mrcraftcod.shcheduler.jfx.table.GymnasiumMatchTableCell;
import fr.mrcraftcod.shcheduler.jfx.table.MatchMenuButton;
import fr.mrcraftcod.shcheduler.model.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ListChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainController{
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
	private Championship championship;
	private Map<Gymnasium, Map<LocalDate, SimpleIntegerProperty>> remainingPlaces;
	
	public MainController(){
		this.remainingPlaces = new HashMap<>();
	}
	
	public Predicate<Match> getWeakConstraints(final GymnasiumMatchTableCell cell){
		return getWeakConstraints(cell.getGroupStage(), cell::getGymnasium, cell.getDate(), List.of());
	}
	
	public void assignMatch(Match match, Gymnasium gymnasium, LocalDate date){
		if(Objects.nonNull(match)){
			if(match.isAssigned()){
				final var prop = remainingPlaces.get(match.getGymnasium()).get(match.getDate());
				prop.set(prop.get() + 1);
			}
			match.setGymnasium(gymnasium);
			match.setDate(date);
			if(match.isAssigned()){
				final var prop = remainingPlaces.get(match.getGymnasium()).get(match.getDate());
				prop.set(prop.get() - 1);
			}
		}
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
	
	private Predicate<Match> getStrongConstraints(final GroupStage gs, final Supplier<Gymnasium> gym, final LocalDate date, final List<Match> selected){
		final Predicate<Match> predicateMatchGymnasium = m -> Objects.equals(gym.get(), m.getTeam1().getGymnasium()) || Objects.equals(gym.get(), m.getTeam2().getGymnasium());
		final Predicate<Match> predicateNotAssigned = m -> !m.isAssigned();
		final Predicate<Match> predicateFreeGymnasium = m -> isGymnasiumFree(gym.get(), date, selected);
		final Predicate<Match> predicateDontPlaySameDay = m -> gs.getMatches().stream().filter(m2 -> m2.isTeamPlaying(m.getTeam1()) || m2.isTeamPlaying(m.getTeam2())).noneMatch(m2 -> Objects.equals(date, m2.getDate()));
		final Predicate<Match> predicateDontPlaySameDaySelected = m -> selected.stream().noneMatch(m2 -> m2.isTeamPlaying(m.getTeam1()) || m2.isTeamPlaying(m.getTeam2()));
		
		return predicateMatchGymnasium.and(predicateNotAssigned).and(predicateFreeGymnasium).and(predicateDontPlaySameDay).and(predicateDontPlaySameDaySelected);
	}
	
	public boolean isGymnasiumFree(final Gymnasium gymnasium, final LocalDate date, List<Match> selected){
		return getRemainingPlace(gymnasium, date, selected) > 0;
	}
	
	public Predicate<Match> getStrongConstraintsSelection(final MatchMenuButton button){
		return getStrongConstraints(button.getParentCell().getGroupStage(), () -> button.getParentCell().getGymnasium(), button.getParentCell().getDate(), button.getCheckedItems());
	}
	
	private int getRemainingPlace(Gymnasium g, LocalDate d, List<Match> selected){
		return (int) (g.getCapacity() - Stream.concat(championship.getGroupStages().stream().flatMap(gs -> gs.getMatches().stream()).filter(m -> Objects.equals(g, m.getGymnasium())).filter(m -> Objects.equals(d, m.getDate())), selected.stream()).distinct().count());
	}
	
	public SimpleIntegerProperty remainingPlaceProperty(Gymnasium gymnasium, LocalDate date){
		return Optional.ofNullable(this.remainingPlaces.get(gymnasium)).map(m -> m.get(date)).orElse(new SimpleIntegerProperty(-1));
	}
	
	public Championship getChampionship(){
		return this.championship;
	}
	
	public void setChampionship(final Championship championship){
		this.championship = championship;
		this.remainingPlaces = championship.getGroupStages().stream().flatMap(gs -> gs.getTeams().stream()).map(Team::getGymnasium).distinct().collect(Collectors.toMap(g -> g, g -> championship.getDates().stream().collect(Collectors.toMap(d -> d, d -> {
			final var prop = new SimpleIntegerProperty(getRemainingPlace(g, d, List.of()));
			g.capacityProperty().addListener((obs, oldValue, newValue) -> {
				prop.set(prop.get() - oldValue.intValue() + newValue.intValue());
			});
			g.getBannedDates().addListener(new ListChangeListener<LocalDate>(){
				@Override
				public void onChanged(Change<? extends LocalDate> change){
					while(change.next()){
						for(var added : change.getAddedSubList()){
							if(Objects.equals(added.minusDays(Utils.getDaysToRemove(added.getDayOfWeek())), d)){
								prop.set(0);
							}
						}
						for(var removed : change.getRemoved()){
							if(Objects.equals(removed.minusDays(Utils.getDaysToRemove(removed.getDayOfWeek())), d)){
								prop.set(g.getCapacity());
							}
						}
					}
				}
			});
			return prop;
		}))));
	}
}
