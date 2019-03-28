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

/* Variable pour le calcul de l'alternance */

var Reception {l in 1..NbPoules, j in 1..NbEquipesPoule[l], t in 1..NbJourneeMax};

var AltEquipeJournee {l in 1..NbPoules, j in 1..NbEquipesPoule[l], t in 1..NbJourneeMax-1};

var AltEquipe{l in 1..NbPoules, j in 1..NbEquipesPoule[l]};


/* Fonction objectif*/

maximize Alternance: sum{l in 1..NbPoules, j in 1..NbEquipesPoule[l]} AltEquipe[l,j];


/* Contraintes: */

/* Contraintes permettant un match par équipe par journée*/
s.t. UnMatchParEquipeParJour {l in 1..NbPoules, j in 1..NbEquipesPoule[l], t in 1..NbJourneeMax}:
sum{k in 1..NbEquipesPoule[l]} x[l,j,k,t] + sum{k in 1..NbEquipesPoule[l]} x[l,k,j,t] <= 1;

/* Contraintes obligeant chaque équipe à se rencontrer une fois (A reçoit B et B reçoit A ne sont pas les mêmes rencontres) */
s.t. RencontreEquipe {l in 1..NbPoules, j in 1..NbEquipesPoule[l], k in 1..NbEquipesPoule[l]: j<>k}:
sum{t in 1..2*NbEquipesPoule[l]-2} x[l,j,k,t] = 1;

/* Contraintes supprimant les rencontres entre même équipe (A reçoit A)*/
s.t. SuppRencontreMemeEquipe{l in 1..NbPoules, j in 1..NbEquipesPoule[l], t in 1..NbJourneeMax}:
x[l,j,j,t]=0;


/* Suppression des journées en trop */ 
s.t. JourneeNulle{l in 1..NbPoules, j in 1..NbEquipesPoule[l], k in 1..NbEquipesPoule[l], t in 2*NbEquipesPoule[l]-1..NbJourneeMax}:
x[l,j,k,t] = 0;

/* Contraintes sur la capacité des gymnases */
s.t. CapacitesParGymnase{p in 1..NbPoules, t in 1..2*NbEquipesPoule[p]-2, g in 1..NbGymnases}:
sum{l in 1..NbPoules, j in 1..NbEquipesPoule[l], k in 1..NbEquipesPoule[l]} x[l,j,k,t] * EquipeGymnase[l,j,g] <= CapacitesMax[g];


/* Calculs des variables d'alternances */


s.t. CalculReception {l in 1..NbPoules, j in 1..NbEquipesPoule[l], t in 1..NbJourneeMax}:
Reception[l,j,t] = sum{k in 1..NbEquipesPoule[l]} x[l,j,k,t];

s.t. CalculAltEquipe {l in 1..NbPoules, j in 1..NbEquipesPoule[l]}:
AltEquipe[l, j] = sum{t in 1..NbJourneeMax-1} AltEquipeJournee[l,j,t];

s.t. CalculAltEquipeJournee1 {l in 1..NbPoules, j in 1..NbEquipesPoule[l], t in 1..NbJourneeMax-1}:
AltEquipeJournee[l,j,t] >= Reception[l,j,t+1]-Reception[l,j,t];

s.t. CalculAltEquipeJournee2 {l in 1..NbPoules, j in 1..NbEquipesPoule[l], t in 1..NbJourneeMax-1}:
AltEquipeJournee[l,j,t] >=Reception[l,j,t]-Reception[l,j,t+1];

s.t. CalculAltEquipeJournee3 {l in 1..NbPoules, j in 1..NbEquipesPoule[l], t in 1..NbJourneeMax-1}:
AltEquipeJournee[l,j,t] <= Reception[l,j,t+1]+Reception[l,j,t];

s.t. CalculAltEquipeJournee4 {l in 1..NbPoules, j in 1..NbEquipesPoule[l], t in 1..NbJourneeMax-1}:
AltEquipeJournee[l,j,t] <= 2 - (Reception[l,j,t+1]+Reception[l,j,t]);


end;