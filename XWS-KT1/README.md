# Agentski model 

![Image](./model.png?raw=true)

# Arhitektura: 

[![](https://mermaid.ink/img/eyJjb2RlIjoiXG5ncmFwaCBMUlxuUFtQS0ldXG5NT05PW01vbm9saXRoXVxuVltWZWhpY2xlXVxuT1tBZHNdXG5TW1NlYXJjaF1cblJbUmVudHNdXG5XW0NsaWVudF1cblp7WnV1bH1cblxuWiAtLT4gVlxuWiAtLT4gU1xuWiAtLT4gUlxuWiAtLT4gT1xuWiAtLT4gQVtBdXRoXVxuWiAtLT4gR1tHUFNdXG5XIC0tPiBaXG5PIC0tIHJlcGxpY2F0ZXMgZGF0YSAtLT4gUTEoKEFNUVApKVxuViAtLSByZXBsaWNhdGVzIGRhdGEtLT4gUTFcbkEgLS0gcmVwbGljYXRlcyBkYXRhLS0-IFExXG5SIC0tIHJlcGxpY2F0ZXMgZGF0YSAtLT4gUTFcbk8gLS0gc3luYy0tPiBWXG5SIC0tIHN5bmMgLS0-IE9cbkcgLS0-IFEyKChBTVFQKSlcblEyIC0tIGdwcyBjb29yZC0tPiBPXG5RMSAtLSBmb3J3YXJkcy0tPiBTXG5cbiIsIm1lcm1haWQiOnsidGhlbWUiOiJkZWZhdWx0In0sInVwZGF0ZUVkaXRvciI6ZmFsc2V9)](https://mermaid-js.github.io/mermaid-live-editor/#/edit/eyJjb2RlIjoiXG5ncmFwaCBMUlxuUFtQS0ldXG5NT05PW01vbm9saXRoXVxuVltWZWhpY2xlXVxuT1tBZHNdXG5TW1NlYXJjaF1cblJbUmVudHNdXG5XW0NsaWVudF1cblp7WnV1bH1cblxuWiAtLT4gVlxuWiAtLT4gU1xuWiAtLT4gUlxuWiAtLT4gT1xuWiAtLT4gQVtBdXRoXVxuWiAtLT4gR1tHUFNdXG5XIC0tPiBaXG5PIC0tIHJlcGxpY2F0ZXMgZGF0YSAtLT4gUTEoKEFNUVApKVxuViAtLSByZXBsaWNhdGVzIGRhdGEtLT4gUTFcbkEgLS0gcmVwbGljYXRlcyBkYXRhLS0-IFExXG5SIC0tIHJlcGxpY2F0ZXMgZGF0YSAtLT4gUTFcbk8gLS0gc3luYy0tPiBWXG5SIC0tIHN5bmMgLS0-IE9cbkcgLS0-IFEyKChBTVFQKSlcblEyIC0tIGdwcyBjb29yZC0tPiBPXG5RMSAtLSBmb3J3YXJkcy0tPiBTXG5cbiIsIm1lcm1haWQiOnsidGhlbWUiOiJkZWZhdWx0In0sInVwZGF0ZUVkaXRvciI6ZmFsc2V9)

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