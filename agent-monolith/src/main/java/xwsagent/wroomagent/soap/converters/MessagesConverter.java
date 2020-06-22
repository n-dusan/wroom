package xwsagent.wroomagent.soap.converters;

public class MessagesConverter {

	public static xwsagent.wroomagent.domain.Message fromSoapMessage(xwsagent.wroomagent.soap.xsd.Message soap) {
		return new xwsagent.wroomagent.domain.Message(
				null,
				null,
				null,
				soap.getRentRequestId(),
				soap.getTitle(),
				soap.getContent(),
				soap.getDate() == null ? null : soap.getDate(),
				soap.getFromUser() == null ? null : soap.getFromUser(),
				soap.getToUser() == null ? null : soap.getToUser()
		);
	}
	
	public static xwsagent.wroomagent.soap.xsd.Message toSoapMessage(xwsagent.wroomagent.domain.Message entity) {
		xwsagent.wroomagent.soap.xsd.Message ret = new xwsagent.wroomagent.soap.xsd.Message();
		ret.setContent(entity.getContent());
		ret.setTitle(entity.getTitle());
		ret.setRentRequestId(entity.getRentRequestId());
		ret.setFromUser(entity.getFromUser());
		ret.setToUser(entity.getToUser());
		ret.setDate(entity.getDate());
//		ret.setLocalId(entity.getId());
		ret.setId(entity.getId());
		return ret;
	}
	
}
