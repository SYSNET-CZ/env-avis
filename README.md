# env-avis

__env-avis__ je knihovna Java pro čtení a ukládání dat do DBF pro výměnu dat s AVIS.


# Licence

__env-anis__ je AGPL.

# Úvod

Mezi evidenčním systémem sanace ekologických zátěží a účetnictvím vedeným v AVIS probíhá výměna dat. 
V rámci této výměny se do AVIS posílají soubory:

* SUP.DBF - číselník supervizorů
* CISPOD.DBF - ekologické smlouvy
* CISPOD3.DBF - realizační smlouvy
 
Z AVIS do evidenčího systému sanace ekologických zátěží se posílají aktuální data ekologických faktur.

* faktLN.dbf  

Knihovna používá knihovnu [JavaDBF](https://github.com/albfernandez/javadbf). 

Architektura knihovny je prostá. Knihovna obsahuje jedinou třídu **cz.sysnet.env.Utils**, která pracuje s modelem pomocí statických metod.
Soubory DBF se ukládají v kódování **Windoze 1250**.   

# Model

Datové objekty vyměňovaných dat jsou popsány v modelu jako 4 třídy, jejich názvy respektují historické pojmenování:

* __cz.sysnet.env.model.Sup__	supervizor
* __cz.sysnet.env.model.Cisdod__	ekologická smlouva
* __cz.sysnet.env.model.Cisdod3__	realizační smlouva
* __cz.sysnet.env.model.Faktura__	ekologická faktura

# Metody

Knihovna umožňuje uložit seznam objektů do souboru DBF, načist seznam objektů z DBF a zkonvertovat soubor DBF do standardního CSV v kódování UTF-8.

## Konverze do CSV

		String dbfFilename = "FAKTURY.DBF";
		String csvFilePath = cz.sysnet.env.Utils.dbfToCsv(dbfFilename);
			   
## Uložení seznamu datových objektů do DBF

**Supervizor**

		List<Sup> supList = new ArrayList<Sup>();
		.
		. naplnit seznam
		.
		String dbfFilename = "SUP.DBF";
		cz.sysnet.env.Utils.storeSupToDbf(supList, dbfFilename);
		
**Ekologické smlouvy**

		List<Cisdod> cisdodList = new ArrayList<Cisdod>();
		.
		. naplnit seznam
		.
		String dbfFilename = "CISDOD.DBF";
		cz.sysnet.env.Utils.storeCisdodToDbf(cisdodList, dbfFilename);
		
**Realizační smlouva**

		List<Cisdod3> cisdod3List = new ArrayList<Cisdod3>();
		.
		. naplnit seznam
		.
		String dbfFilename = "CISDOD3.DBF";
		cz.sysnet.env.Utils.storeCisdod3ToDbf(cisdod3List, dbfFilename);
		
## Načtení seznamu datových objektů z DBF

Tento příklad načte do proměnné **fakturaList** 1000 datových objektů ze souboru, počínaje 51. objektem (řádkem).  

		List<Faktura> fakturaList = null;
		int fromItem = 50;
		int itemCount = 1000;		
		String dbfFilename = "fakturyLN.dbf";
		fakturaList = cz.sysnet.env.Utils.loadFakturaList(dbfFilename, fromItem, itemCount);		

# Vytvoření ze zdroje

		git clone https://github.com/SYSNET-CZ/env-avis.git
		cd env-avis
		mvn clean package

