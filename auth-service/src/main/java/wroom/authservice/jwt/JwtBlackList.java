package wroom.authservice.jwt;

import java.util.ArrayList;
import java.util.List;

public class JwtBlackList {

	public static List<String> lista = new ArrayList<String>();

	public void init() {
		lista.add("evilhacker");
	}
	
	public void add(String string) {
		lista.add(string);
	}
	
}