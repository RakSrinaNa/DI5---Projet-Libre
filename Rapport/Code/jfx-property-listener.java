final var prop = controller.remainingPlaceProperty(getGymnasium(), getDate());
prop.addListener((obs, oldValue, newValue) -> {
	if(Objects.nonNull(matches)){
		if(newValue.intValue() < oldValue.intValue()){
			if(newValue.intValue() < 0){
				if(!matches.isEmpty()){
					final var newMatches = FXCollections.observableArrayList(matches);
					for(var i = 0; i > newValue.intValue(); i--){
						newMatches.remove(newMatches.size() - 1);
					}
					this.updateItem(newMatches, false);
				}
			}
		}
	}
});