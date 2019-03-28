/* Projet libre: modèle */

/* Attributs*/
	
param NbGymnases;
param NbPoules;
param NbEquipesPoule{j in 1..NbPoules};
param CapacitesMax{j in 1..NbGymnases};
param EquipeGymnase{l in 1..NbPoules, j in 1..NbEquipesPoule[l], g in 1..NbGymnases};

param NbJourneeMax:= max{l in 1..NbPoules}(2*NbEquipesPoule[l]-2);


/* Variables : poule l x=1 si j rencontre k instant t */

var x {l in 1..NbPoules, j in 1..NbEquipesPoule[l], k in 1..NbEquipesPoule[l], t in 1..NbJourneeMax}, binary;

maximize test: NbGymnases;

/* Contraintes: */

/* Contrainte permettant un match par équipe par journée*/
s.t. UnMatchParEquipeParJour {l in 1..NbPoules, j in 1..NbEquipesPoule[l], t in 1..NbJourneeMax}:
sum{k in 1..NbEquipesPoule[l]} x[l,j,k,t] + sum{k in 1..NbEquipesPoule[l]} x[l,k,j,t] <= 1;

/* Contrainte obligeant chaque équipe à se rencontrer une fois (A reçoit B et B reçoit A ne sont pas les mêmes rencontres) */
s.t. RencontreEquipe {l in 1..NbPoules, j in 1..NbEquipesPoule[l], k in 1..NbEquipesPoule[l]: j<>k}:
sum{t in 1..2*NbEquipesPoule[l]-2} x[l,j,k,t] = 1;

/* Contrainte supprimant les rencontres entre même équipe (A reçoit A)*/
s.t. SuppRencontreMemeEquipe{l in 1..NbPoules, j in 1..NbEquipesPoule[l], t in 1..NbJourneeMax}:
x[l,j,j,t]=0;

/* Suppression des journées en trop */ 
s.t. JourneeNulle{l in 1..NbPoules, j in 1..NbEquipesPoule[l], k in 1..NbEquipesPoule[l], t in 2*NbEquipesPoule[l]-1..NbJourneeMax}:
x[l,j,k,t] = 0;

/* Contrainte sur la capacité des gymnases
s.t. CapacitesParGymnase{p in 1..NbPoules, t in 1..2*NbEquipesPoule[p]-2, g in 1..NbGymnases}:
sum{l in 1..NbPoules, j in 1..NbEquipesPoule[l], k in 1..NbEquipesPoule[l]} x[l,j,k,t] * EquipeGymnase[l,j,g] <= CapacitesMax[g];
*/

end;