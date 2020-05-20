```
Predlog mikroservisa : 
	- auth:
		funkcionalnosti: {
			(   -logovanje, 
				-registracija, 
				-upravljanje permisijama, 
				-prihvatanje zahteva za registraciju
			)
		}
		entiteti: {
			- User
			- SignupRequest
		}
		
	- vehicle 
		-funkcionalnosti: {
			(crud vozila, crud vehicle-features(menjac, tip modela auta, tip marke auta, itd.)) 
		}
		-entiteti: {
			- Vehicle
			- BrandType
			- ModelType
			- GearboxType
			- FuelType
			- BodyType
			- Image
		}
		
	- search 
			funkcionalnosti: {
				( napredna pretraga )
			}
			entiteti: {
			- Ad
		}
		
	- ads 
			funkcionalnosti: {
				( crud oglasa, crud cenovnika, statistika za vozilo)
			}
			entiteti: {
			- User
			- Ad
			- Vehicle
			- PriceList
			- Comment
		}
		
	- messaging 
			-funckionalnosti: {
				( dopisivanje )
			}
			entiteti: {
			- Message
		}
		
	- rents 
			-funckionalnosti: {
				(
				rucni unos zauzeca vozila, 
				prihvatanje i odbijanje zahteva, 
				istorija zahteva, 
				otkazivanje zahteva, 
				izvestaj o iznajmljivanju (rent report)
				)
			}
			
			entiteti: {
			- RentRequest
			- User (id)
			- RentReport
			- Ad (komunicira ka ad modulu - id)
		}
		
	- admin 
			funckionalnosti: {
			(banovanje,  brisanje korisnika, odbijanje i prihvatanje komentara, registrovanje firme)
			}
			entiteti: {
			- User
			- Comments
		}
		
	- tingle (pki):
			entiteti: {
			- // is ready to mingle
		}
		
	- likvidatura ili prodaja ()
			entiteti: {
			- // treba modelovati na mendixu 
		}
		
	- gps (vozilo salje koordinate)
			entiteti: {
			- nista
			- //salje koordinate ka message queue
		}
		
```
