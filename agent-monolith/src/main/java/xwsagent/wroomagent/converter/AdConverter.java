package xwsagent.wroomagent.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.dto.AdDTO;

public class AdConverter extends AbstractConverter {

	public static AdDTO fromEntity(Ad entity) {
		return new AdDTO(
				entity.getId(),
				entity.getPublishDate().toString(),
				entity.getAvailableFrom().toString(),
				entity.getAvailableTo().toString(),
				entity.getMileLimit()
		);
	}
	
	/**
	 * 
	 * @param dateString - String representation of a date
	 * @return Date in 'dd.MM.yyyy 'at' HH:mm:ss' format
	 */
	private static Date parseDate(String dateString) {
		Date parsed;
		try {
		    SimpleDateFormat format =
		        new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss");
		    parsed = format.parse(dateString);
		    return parsed;
		}
		catch(ParseException pe) {
		    throw new IllegalArgumentException(pe);
		}
	}
}
