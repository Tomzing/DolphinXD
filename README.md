# DolphinXD
Prosjektoppgave gruppe 17
readme.txt
Yesterday
Tue 3:23 PM

SoppenFedg uploaded an item
Text
readme.txt
Dette er readme filen til prosjektet DolphinXD, som er lagd av Gruppe 17 i Software Engineering, Høst 2019

Levering går utifra IntelliJ, andre Java baserte editorer burde fungere, men da må dependencies lastes ned manuelt.

Når du åpner IntelliJ må mappen med prosjektet i bli definert. Du velger da mappen som heter "DolphinXD"
Når dette er gjort må du definere hva som er "Main" for at programmet skal kjøres. Dette gjøre du oppe i høyre gjørne, hvor du burde se en knapp der det står "Edit Configurations"
Trykk her, og trykk igjen på "Edit configurations"
Etter dette må du trykke på pluss knappen i venstre hjørnet i det nye vinduet, og velge "Application"
Når det er gjort må du definere klassen. I define class velg "Dolphin.Main". Navnet på toppen er valgfritt, men det er lurt å kalle denne også "Main".
Her kan du også definere Java Runtime Enviroment (JRE). Under JRE kan du enten velge hvilken versjon du skal bruke, eller definere path til JRE. Vårt prosjekt er bygd I JRE 13.0
Når du er ferdig trykker du på "Ok". Da skal "Main" komme opp der det sto "Edit Configuration", og pilen vedsiden av blir grønn.
Etter dette må du laste ned Dependencies for Maven (En guide for dette med bilder finnes i Sensor delen til prosjektdukomentasjonen)


Gå til høyre side av Intellij så skal det være Maven langs høyre side.
Trykk på Maven og trykk på «Reimport All Maven Projects» som har symbolet til en typisk refresh knapp.
Kan hende Maven er litt seig og vil ikke dukke opp. Hvis dette skjer, start start IntelliJ på nytt.

Hvis dette ikke fungerer så må du laste ned JavaFX manuelt og peke til denne slik at Main kan kjøres.
 
1.    Last ned JavaFX 11.0.2 fra denne siden: https://gluonhq.com/products/javafx/
2.    Gå til “Project structure” under “File”, på toppen av venstre side av Intellij. Alternativt trykk CTRL+Alt+Shift+S hvis du bruker standard hotkeys i Intellij.
 
3.    Gå så deretter til libraries (rød pil) og trykk på pluss tegnet (spygrønn pil) for å legge til JavaFX i biblioteket av programmet. Her peker til du til hvor JavaFX befinner seg på PC-en din.

4.    Når disse er lagt til i biblioteket så må du peke Main filen i prototypen til å bruke JavaFX. Dette gjør du ved å trykke på Main i høyre topp hjørne. Hvis Main ikke dukker opp der med en gang, prøv å kjør Main fra prosjektet i Intellij. Trykk på Main, deretter på «Edit configurations» i nedtrekkslisten som dukker opp.
 
 
5.    Merk «Main» i nye vindu som dukker opp
 
Deretter i «configuration» så kopierer du inn
--module-path ${PATH_TO_FX} --add-modules=javafx.controls,javafx.fxml
inn i “VM Options”.

Etter alt dette, hvis du nå trykker på den grønne pilen burde selve programmet kjøre. Hver enkelt fil kan åpnes via å klikke på den på venstre side i IntelliJ (det kan hende du må åpne opp mappene først), i "Project"
