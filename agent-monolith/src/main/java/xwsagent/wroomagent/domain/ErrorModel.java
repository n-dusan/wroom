package xwsagent.wroomagent.domain;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ErrorModel extends RuntimeException 
{
    public ErrorModel(String exception) {
        super(exception);
    }
}
