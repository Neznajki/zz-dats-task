#UZDEVUMA APRAKSTS UN PRASĪBAS
##Nepieciešams izveidot vienkāršu REST API, kas izstrādāts JAVA programmēšanas valodā
###(ietvara lietošana pēc brīvas izvēles), kas atbilstu šādiem biznesa nosacījumiem:

* API sniegs informāciju par bērnudārziem un rindām uz bērnudārzu.
* API lietotājs varēs pieteikt bērnu rindā uz bērnudārzu.
* API lietotājs varēs izgūt informāciju par rindu uz bērnudārzu, kas sakārtota prioritārā
secībā.
* API lietotājs varēs izņemt bērnu no bērnudārza rindas.
Vienumi un minimālie lauki. Nepieciešamības gadījumā var pievienot papildus laukus.
###Bērnudārzs:
* Identifikators
* Nosaukums
* Adrese (teksta lauks, nav obligāti strukturētā veidā)
* Bērnu Rinda
Bērns:
* Vārds
* Uzvārds
* Personas kods (vienkārša validācija - lai satur 6 ciparus, defisi un vēl 5 ciparus)
* Atzīme "Brālis vai Māsa", kas norāda, ka šī bērna brālis vai māsa mācās tajā pašā
bērnudārzā.
###Jānodrošina darbības:
* Visu bērnudārzu saraksta izgūšana.
* Viena bērnudārza izgūšana pēc ID.
* Rindas uz bērnudārzu izgūšana, prioritārā secībā. Secība atkarīga no bērna pieteikšanas laika
* rindā. Bērniem, kuriem attiecīgajā bērnu dārzā mācās brālis vai māsa, ir priekšroka rindā.
* Viena bērna pievienošana rindā dotajā bērnudārzā.
* Viena bērna izņemšana no rindas dotajā bērnudārzā pēc personas koda.
* Bērnudārzu sarakstu jādefinē kā statiskus datus, vai nu lietojuma atmiņā, vai arī kā datu
* sagatavošanas skriptu in-memory vai konteinerizētā DB.
* Lietojumam jānodrošina vienībtesti.
* Lietojuma kodu jāievieto Github vai līdzvērtīgā publiskā git repozitorijā, un jāatsūta links uz
repozitoriju.
* Repozitorijam jāsatur apraksts par lietojuma palaišanu.