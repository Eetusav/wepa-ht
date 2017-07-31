## Aiheen kuvaus ja rakenne
**Aihe:** Toteutetaan j�rjestelm�, joka ominaisuuksiltaan toimii aluksi kuin tavallinen laskin. Ominaisuuksina on siis tunnetusti esimerkiksi summaus-, erutos-, tulo- ja osam��r� operaatiot.
Lis�ksi voisi olla kiinnostavaa saisiko laskimeen tehty� jotain lis�toiminnallisuutta, ja nyt loppuvaiheen edit: Laskimeen lis�tty tavallisimpia "funktio-operaatioita", kuten esimerkiksi neli�juuri yms. 
Laskimeen on lis�ksi tehty ns. muisti ja historia ominaisuudet, jossa muistiin voidaan tallentaa ja palauttaa jokin generoitu tulos. Historia ominaisuus taas n�ytt�� listan generoituja tuloksia, 
jonka pystyy tallentamaan erilliseen tekstitiedostoon, sek� hakemaan sen sielt� (esimerkiksi eri sessiossa).

**Rakenne:** 
Kaikessa yksinkertaisuudessaan voidaan mielt��, ett� laskimessa on kolme (p��)luokkaa Operaatio, Historia ja K�ytt�liittym�(kulkee nimell� Laskin). K�ytt�j� n�kee ja "koskee" vain k�ytt�liittym��n
ja sen kautta v�littyy jotain taikatemppuja, jotka k�ytt�v�t Operaatio-luokkaa ja historiaa. Operaatio ja historia suorittavat k�ytt�liittym��n annettujen komentojen mukaisesti aina haluttuja asioita.

**K�ytt�ohje:**
Itsess��n laskin on varmasti monelle tuttu, mutta selvennet��n joidenkin tiettyjen nappien toimintaa. 
* MC = Memory Clear: tyhjent�� muistikent�n. 
* MR = Memory Read: lukee muistikent�n tuloksen sy�tteeksi. 
* MS = Memory Set: asettaa sy�tteen tuloksen muistikentt��n muistiin.
* CH = clear history: tyhjent�� historian (ei tyhjenn� ohjelman ulkopuolista tekstitiedostoa). 
* WriteHistory: kirjoittaa historian ohjelman ulkopuoliseen tekstitiedostoon (jos tekstitiedostoa ei ole olemassa, luodaan sellainen). 
* ReadHistory: lukee ohjelman ulkopuolisesta tekstitiedostosta aikaisemmin tallennetun historian (oletuksena tyhj� historia, jos tekstitiedostoa ei ole olemassa, luodaan sellainen).
* Lis�ksi tan/cos/sin operaatioiden sy�te oletetaan radiaaneiksi.

**K�ytt�j�t:** Laskimen k�ytt�j�

**K�ytt�j�n toiminnot:**
* Laskimelle sy�tteiden antaminen.
* Laskimen operaatioiden k�ytt�minen (summaus, erotus jne.).
* Tulosteiden tarkasteleminen.  
 
  
   
### Laskimen luokkakaavio  
![Laskimen luokkakaavio](/dokumentaatio/kaaviot/LuokkakaavioD6.png)  
### Laskimen sekvenssikaavio 1  
![Laskimen sekvenssikaavio 1](/dokumentaatio/kaaviot/Sekvenssikaavio2.png)  
### Laskimen sekvenssikaavio 2  
![Laskimen sekvenssikaavio 2](/dokumentaatio/kaaviot/Sekvenssikaavio3.png)  
