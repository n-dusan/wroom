package xwsagent.wroomagent.soap.clients;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.repository.AdRepository;
import xwsagent.wroomagent.repository.rbac.UserRepository;
import xwsagent.wroomagent.soap.converters.AdSoapConverter;
import xwsagent.wroomagent.soap.xsd.*;

import java.util.List;

@Log4j2
public class AdsClient extends WebServiceGatewaySupport{

	public static final String MONOLITH_USER_EMAIL = "zika@maildrop.cc";

	@Autowired
	private AdRepository adRepository;

	@Autowired
	private UserRepository userRepository;

	public SendAdResponse send(Ad entity, Operation operation) {

		SendAdRequest request = new SendAdRequest();

		request.setAd(AdSoapConverter.toAdSoap(entity));
		request.setOperation(operation);

		log.info("action=send-ad status=started");
		SendAdResponse response = (SendAdResponse) getWebServiceTemplate().marshalSendAndReceive(request);

		log.info("action=send-ad status=ended");
		return response;
	}

	public void sync() {
		log.info("action=sync-ads status=started");
		SendAdListRequestResponse request = new SendAdListRequestResponse();

		User user = userRepository.findByEmail(MONOLITH_USER_EMAIL).get();
		List<Ad> adList = this.adRepository.findAllActiveUser(user.getId());

		List<AdSoap> soaps = AdSoapConverter.toEntityList(adList, AdSoapConverter::toAdSoap);

		request.setAd(soaps);
		request.setCompanyEmail(MONOLITH_USER_EMAIL);

		SendAdListRequestResponse response = (SendAdListRequestResponse) getWebServiceTemplate().marshalSendAndReceive(request);

		log.info("action=sync-ads status=ended");
	}
	
}
