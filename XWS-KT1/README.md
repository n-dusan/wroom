# Agentski model 

![Image](./model.png?raw=true)

# Mikroservisi: 

![Image](./mermaidjs-microservices.png?raw=true)

>Veze izmedju Zuula i mikroservisa je direktno obraćanje, dok veze između servisa pokazuju sadržavanje id-a, odnosno potrebu da se tom servisu direktno obrati, ili da uz pomoć aggregate šablona se dobave neophodni podaci.

## Auth 
* funkcionalnosti
	 * login, 
	 * registracija, 
	* upravljanje permisijama, 
	*  zahtevi za registraciju

* entiteti (nad kojima rukuje)
	* User 
	* SignupRequest

## Vehicle
* funkcionalnosti
	 *  vehicle CRUD,
	 * model-type CRUD,
	 * gearbox-type CRUD,
	 * brand-type CRUD,
	 * body-type CRUD,
	 * fuel-type CRUD

* entiteti nad kojima rukuje: 
	* Vehicle
	* BrandType
	* ModelType
	* GearboxType
	* FuelType
	* BodyType
	* Image
## Search
* funkcionalnosti
	 * napredna pretraga

* entiteti nad kojima rukuje: 
	* Ad
	* Vehicle
	* PriceList

## Ads
* funkcionalnosti
	 * oglas CRUD
	 * cenovnik CRUD
	 * statistika pređenosti vozila
	 * komentari i ocene

* entiteti nad kojima rukuje: 
	* User
	* Ad
	* Vehicle
	* PriceList
	* Comment
## Rents
* funkcionalnosti
	* ručni unos zauzeća vozila, 
	 * prihvatanje i odbijanje zahteva, 
	 * pregled istorije zahteva, 
	 * otkazivanje zahteva, 
	* izveštaj o iznajmljivanju (rent report)

* entiteti nad kojima rukuje: 
	* RentRequest
	*  User
	 * RentReport
	* Ad (id)

## Messaging
* funkcionalnosti
	 * dopisivanje

* entiteti nad kojima rukuje: 
	* User
	* Message

## Admin
* funkcionalnosti
	* banovanje,  
	* brisanje korisnika, 
	* odbijanje i prihvatanje komentara, 
	* registrovanje firme

* entiteti nad kojima rukuje: 
	*  User
	* Comments
## GPS
> šalje jwt koordinate u amqp na osnovu prosledjenog vehicle_id-a koji poseduje tracking uređaj.
## PKI
> tls
## PINF podsistem
> najverovatnije prodaja