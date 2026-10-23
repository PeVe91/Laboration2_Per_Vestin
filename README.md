# Budgethanteraren.

I det här programmet får användaren kontroll över en egen budgetplanerare, med möjlighet att lägga till
sina vardagliga utgifter och inkomster. Ytterligare funktioner: visa mina transaktioner, visa totalt saldo
vid en given punkt, summering av en särskild angiven kategori eller transaktionstyp, samt möjlighet att
spara och ladda transaktioner till/från en CSV-fil.

## Klasser och tester och dess ansvarsområden:

### Menu:
Hanterar det mesta användaren ser. Här finns main-metoden, handler-metoder för samtliga menyval och
metoder för att validera input. Menu har det yttersta ansvaret för att validera data innan den skickas vidare
till dem andra klassernas metoder.

### Repository:
Ansvarar för att lagra transaktioner och på begäran lägga till transaktioner i dess ArrayList,
returnera en kopia på listan med transaktioner och att tömma sin original-lista.

### BudgetService:
Ansvarar för beräkningar och filtrering genom streams. Hit skickar Menu en lista med transaktioner
som den hämtar från Repository, och får tillbaka nuvarande totalt saldo och anpassade listor som antingen summerar
alla kategorier och dess saldon eller en lista filtrerad på kategori eller typ, beroende på vad användaren väljer.

### CsvRepository:
Ansvarar för att spara och ladda transaktioner från/till en CSV-fil och felhantering för dessa.
Där har jag valt att låta Menu sköta loggning då eventuellt fel skickas vidare till Menu.

### InvalidTransactionException:
Extendar RuntimeException eftersom det är en unchecked exception.
Hanterar ogiltiga transaktionsvärden.

### FileFormatException:
Extendar Exception eftersom det är en checked exception som måste fångas eller deklareras med
throws. Hanterar felaktigt format på CSV-filen vid inläsning. 

### TransactionType (enum):
Skiljer på inkomst och utgift. Här valde jag väldigt tidigt att gardera mig mot tankevurpor i
framtida metoder. Istället för att behöva komma ihåg att på alla olika ställen berätta för metoder att EXPENSE ska behandlas
som något negativt så lade jag till en egen konstruktor med en multiplier som int-parameter som är 1 för INCOME och -1 för EXPENSE.
Då behöver jag bara komma ihåg att lägga till gettern för multipliern när beräkningar görs, oavsett vilken transaktionstyp jag får in.
(T ex: transaction.amount() * transaction.type().getMultiplier())

### Transaction (record):
Innehåller fyra parametrar: en  LocalDateTime, en TransactionType för transaktionstyp,
en String för kategori och en double för belopp. Eftersom LocalDateTime alltid kommer anges som .now() så bestämde
jag mig för att använda mig av en egen konstruktor som bara tar dem tre andra parametrarna och automatiskt sätter
.now() som värde för LocalDateTime.

### RepositoryTest:
Innehåller tester som kontrollerar att add lägger till transaktioner i listan, att alla transaktioner
returneras korrekt, att en kopia av listan returneras istället för originalet för att användaren inte ska råka kunna göra
oavsiktliga ändringar och att clear-metoden rensar original-listan.

### BudgetServiceTest:
Innehåller tester som kontrollerar att flera transaktioner med blandade typer returnerar rätt summa
samt att den hanterar decimaltal korrekt (tar hjälp av felmarginal i asserten), att alla transaktioner av samma kategori
kombineras vid gruppering och filtrering, att enbart transaktioner av vald kategori/typ returneras, att en angiven kategori
som inte existerar returnerar en tom lista och att en tom lista returneras om inga transaktioner av angiven typ existerar.

## Ytterligare reflektion:

En bugg som jag särskilt slitit mig i håret över genom denna laboration var att inläsningen av t ex åäö inte fungerade.
Här ringade jag in att problemet uppstod vid inläsningen via IO.readln() genom att både testa en egen utskrift som inte
genererats av användarinput och bekräftade att inläsningen var problemet genom att debugga inläsning av kategori
i min kategori-input-handler.

Jag kontrollerade i inställningarna att global encoding, project encoding och properties encoding var inställda på UTF-8,
vilket de var. När jag fick slut på idéer bad jag chatgpt att peka mig i rätt riktning och fick tips om att kolla VM-options.
Det här var lättare sagt än gjort, så jag lade det åt sidan för att fokusera på annat. Jag fick senare vägledning av en klasskamrat
som lärde mig att det inte är i current file jag ska vara när jag väljer edit configurations, utan i main-metoden.
Då kunde jag lägga till "-Dstdin.encoding=UTF-8" i VM-options för main och testköra programmet igen. Det gjorde susen!

 Den här laborationen var en betydligt större utmaning för mig än laboration 1.
 Jag kände inte att jag hade lika mycket tid att stanna upp och reflektera över allt jag ville,
 så jag försökte lägga tiden för reflektion på det viktigaste enligt beskrivningen av labben.
 Jag hade dock stor användning av kunskapen jag fått med mig från laboration 1,
 och har haft många aha-moments och polletter som trillat ned under arbetet med denna labb.
 
 # Kul!
