## Aiheen kuvaus ja rakenne
**Aihe:** Toteutetaan järjestelmä, joka ominaisuuksiltaan toimii aluksi kuin tavallinen laskin. Ominaisuuksina on siis tunnetusti esimerkiksi summaus-, erutos-, tulo- ja osamäärä operaatiot.
Lisäksi voisi olla kiinnostavaa saisiko laskimeen tehtyä jotain lisätoiminnallisuutta, ja nyt loppuvaiheen edit: Laskimeen lisätty tavallisimpia "funktio-operaatioita", kuten esimerkiksi neliöjuuri yms. 
Laskimeen on lisäksi tehty ns. muisti ja historia ominaisuudet, jossa muistiin voidaan tallentaa ja palauttaa jokin generoitu tulos. Historia ominaisuus taas näyttää listan generoituja tuloksia, 
jonka pystyy tallentamaan erilliseen tekstitiedostoon, sekä hakemaan sen sieltä (esimerkiksi eri sessiossa).

**Rakenne:** 
Kaikessa yksinkertaisuudessaan voidaan mieltää, että laskimessa on kolme (pää)luokkaa Operaatio, Historia ja Käyttöliittymä(kulkee nimellä Laskin). Käyttäjä näkee ja "koskee" vain käyttöliittymään
ja sen kautta välittyy jotain taikatemppuja, jotka käyttävät Operaatio-luokkaa ja historiaa. Operaatio ja historia suorittavat käyttöliittymään annettujen komentojen mukaisesti aina haluttuja asioita.

**Käyttöohje:**
Itsessään laskin on varmasti monelle tuttu, mutta selvennetään joidenkin tiettyjen nappien toimintaa. 
* MC = Memory Clear: tyhjentää muistikentän. 
* MR = Memory Read: lukee muistikentän tuloksen syötteeksi. 
* MS = Memory Set: asettaa syötteen tuloksen muistikenttään muistiin.
* CH = clear history: tyhjentää historian (ei tyhjennä ohjelman ulkopuolista tekstitiedostoa). 
* WriteHistory: kirjoittaa historian ohjelman ulkopuoliseen tekstitiedostoon (jos tekstitiedostoa ei ole olemassa, luodaan sellainen). 
* ReadHistory: lukee ohjelman ulkopuolisesta tekstitiedostosta aikaisemmin tallennetun historian (oletuksena tyhjä historia, jos tekstitiedostoa ei ole olemassa, luodaan sellainen).
* Lisäksi tan/cos/sin operaatioiden syöte oletetaan radiaaneiksi.

**Käyttäjät:** Laskimen käyttäjä

**Käyttäjän toiminnot:**
* Laskimelle syötteiden antaminen.
* Laskimen operaatioiden käyttäminen (summaus, erotus jne.).
* Tulosteiden tarkasteleminen.  
 
  
   
### Laskimen luokkakaavio  
![Laskimen luokkakaavio](/dokumentaatio/kaaviot/LuokkakaavioD6.png)  
### Laskimen sekvenssikaavio 1  
![Laskimen sekvenssikaavio 1](/dokumentaatio/kaaviot/Sekvenssikaavio2.png)  
### Laskimen sekvenssikaavio 2  
![Laskimen sekvenssikaavio 2](/dokumentaatio/kaaviot/Sekvenssikaavio3.png)  
