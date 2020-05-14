package xwsagent.wroomagent.converter;

import xwsagent.wroomagent.domain.PriceList;
import xwsagent.wroomagent.domain.Role;
import xwsagent.wroomagent.domain.dto.PriceListDTO;
import xwsagent.wroomagent.domain.dto.RoleDTO;

public class RoleConverter extends AbstractConverter {

	public static RoleDTO fromEntity(Role entity) {
        return new RoleDTO(
        		entity.getName(),
//        		entity.getPrivileges()
        		null
        		);
    }

//    public static Role toEntity(RoleDTO dto) {
//        Role entity = new Role();
//        entity.setName(dto.getName());
//
//        return entity;
//    }
	
}
