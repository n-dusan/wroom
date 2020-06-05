# Agentski model 

![Image](./model.png?raw=true)

# Arhitektura: 

[![](https://mermaid.ink/img/eyJjb2RlIjoiZ3JhcGggTFJcblBbUEtJXVxuTU9OT1tNb25vbGl0aF1cblZbVmVoaWNsZV1cbk9bQWRzXVxuU1tTZWFyY2hdXG5SW1JlbnRzXVxuV1tDbGllbnRdXG5ae1p1dWx9XG5cblogLS0-IFZcblogLS0-IFNcblogLS0-IFJcblogLS0-IE9cblogLS0-IEFbQXV0aF1cblogLS0-IEdbR1BTXVxuVyAtLT4gWlxuTyAtLSByZXBsaWNhdGVzIGRhdGEgLS0-IFExKChBTVFQKSlcblYgLS0gcmVwbGljYXRlcyBkYXRhLS0-IFExXG5BIC0tIHJlcGxpY2F0ZXMgZGF0YS0tPiBRMVxuTyAtLSBzeW5jLS0-IFZcblMgLS0gc3luYyAtLT4gUlxuRyAtLT4gUTIoKEFNUVApKVxuUTIgLS0gZ3BzIGNvb3JkLS0-IE9cblExIC0tIGZvcndhcmRzLS0-IFMiLCJtZXJtYWlkIjp7InRoZW1lIjoiZGVmYXVsdCJ9LCJ1cGRhdGVFZGl0b3IiOmZhbHNlfQ)](https://mermaid-js.github.io/mermaid-live-editor/#/edit/eyJjb2RlIjoiZ3JhcGggTFJcblBbUEtJXVxuTU9OT1tNb25vbGl0aF1cblZbVmVoaWNsZV1cbk9bQWRzXVxuU1tTZWFyY2hdXG5SW1JlbnRzXVxuV1tDbGllbnRdXG5ae1p1dWx9XG5cblogLS0-IFZcblogLS0-IFNcblogLS0-IFJcblogLS0-IE9cblogLS0-IEFbQXV0aF1cblogLS0-IEdbR1BTXVxuVyAtLT4gWlxuTyAtLSByZXBsaWNhdGVzIGRhdGEgLS0-IFExKChBTVFQKSlcblYgLS0gcmVwbGljYXRlcyBkYXRhLS0-IFExXG5BIC0tIHJlcGxpY2F0ZXMgZGF0YS0tPiBRMVxuTyAtLSBzeW5jLS0-IFZcblMgLS0gc3luYyAtLT4gUlxuRyAtLT4gUTIoKEFNUVApKVxuUTIgLS0gZ3BzIGNvb3JkLS0-IE9cblExIC0tIGZvcndhcmRzLS0-IFMiLCJtZXJtYWlkIjp7InRoZW1lIjoiZGVmYXVsdCJ9LCJ1cGRhdGVFZGl0b3IiOmZhbHNlfQ)

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
	* replicirani podaci iz svih ostalih baza

## Ads
* funkcionalnosti
	 * oglas CRUD
	 * cenovnik CRUD
	 * statistika pređenosti vozila
	 * komentari i ocene

* entiteti nad kojima rukuje: 
	* Ad
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
	 * RentReport

## GPS
> šalje jwt koordinate u amqp na osnovu prosledjenog vehicle_id-a koji poseduje tracking uređaj.
## PKI
> tls
## PINF podsistem
> najverovatnije prodaja