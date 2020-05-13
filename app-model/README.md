Ovde dolazi readme za model: aplikacije


MIKROSERVISI: 
	- auth ( logovanje, registracija, upravljanje permisijama, prihvatanje zahteva za registraciju)
		entiteti: {
			- User
			- SignupRequest
		}
	- search ( napredna pretraga )
			entiteti: {
			- User
			- Vehicle
			- Ad
			- Image
			- PriceList
			- BrandType
			- ModelType
			- GearboxType
			- FuelType
			- BodyType
			- Image
		}
	- ads ( crud oglasa, rucni unos zauzeca vozila, crud vozila?, crud price-list, statistika vozila (obraca se rents (za rent report) i  comments (za top komentare)?)
			entiteti: {
			- Ad
			- Image
			- Vehicle
			- PriceList
			- BrandType
			- ModelType
			- GearboxType
			- FuelType
			- BodyType
			- RentRequest* (pitati asistenta za hendlovanje rucnog unosa, tj da li se obracati drugom mikroservisu?)
			- User
		}
	- messaging ( dopisivanje )
			entiteti: {
			- User
			- RentRequest
			- Ad? (komunicira ka ad modulu)
		
		}
	- rents (prihvatanje i odbijanje zahteva, istorija zahteva, otkazivanje zahteva, izvestaj o iznajmljivanju (rent report))
			entiteti: {
			- RentRequest
			- User
			- RentReport
			- Ad? (komunicira ka ad modulu)
		
		}
	- comments and rating
			entiteti: {
			- User
			- Comment
			- Ad? (komunicira ka ad modulu)
		}
	- admin (crud vehicle-features, banovanje,  brisanje korisnika, odbijanje i prihvatanje komentara, registrovanje firme)
			entiteti: {
			- BrandType
			- ModelType
			- GearboxType
			- FuelType
			- BodyType
			- User
			- Comments
		}
	- tingle (pki):
			entiteti: {
			- // is ready to mingle
		
		}
	- likvidatura ( zatvaranje faktura (RESERVED -> PAID) )
			entiteti: {
			- // u sustini treba modelovati na mendixu 
		
		}
	- gps (vozilo salje koordinate)
			entiteti: {
			- nista
			- //salje koordinate ka message queue
		}
		
		
		
