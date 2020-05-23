package xwsagent.wroomagent.domain;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ErrorResponse{
   
	public ErrorResponse(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }
 
    //General error message about nature of error
    private String message;
 
    //Specific errors in API request processing
    private List<String> details;
 
    
}