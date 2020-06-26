package xwsagent.wroomagent.domain.auth;

public enum RoleName {

	ROLE_ADMIN ("ROLE_ADMIN"),
    ROLE_AGENT ("ROLE_AGENT"),
	ROLE_USER ("ROLE_USER"),
	ROLE_RENTING_USER ("ROLE_RENTING_USER"),
	ROLE_CHATTING_USER ("ROLE_CHATTING_USER"),
	ROLE_RATING_COMMENTING_USER ("ROLE_RATING_COMMENTING_USER"),
	ROLE_CRUD_VEHICLE ("ROLE_CRUD_VEHICLE"),
	ROLE_CRUD_AD ("ROLE_CRUD_AD"),
	ROLE_PHYSICALLY_RESERVE ("ROLE_PHYSICALLY_RESERVE");
	
	private final String name;       

    private RoleName(String s) {
        name = s;
    }
    
    public String toString() {
        return this.name;
     }
}
