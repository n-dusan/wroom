package xwsagent.wroomagent.converter;

import java.util.ArrayList;
import java.util.HashSet;

import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.domain.Privilege;
import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.domain.SignupRequest;
import xwsagent.wroomagent.domain.User;

public class RequestToUserConverter extends AbstractConverter {

	public static User fromRequest(SignupRequest request) {
		User ret = new User(
        		null,
        		request.getName(),
        		request.getSurname(),
        		request.getEmail(),
        		request.getPassword(),
        		new HashSet<RentRequest>(),
        		null,
        		new HashSet<Comment>(),
        		null,
        		new ArrayList<Privilege>(),
        		true,
        		false,
        		null
        );
        return ret;
    }
	
}
