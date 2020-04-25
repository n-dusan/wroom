package xwsagent.wroomagent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xwsagent.wroomagent.domain.Stub;
import xwsagent.wroomagent.repository.StubRepository;

@RestController
@RequestMapping(value="api/stub")
public class StubController {

    @Autowired
    private StubRepository repository;


    @GetMapping(value="/test")
    public ResponseEntity<Stub> test() {
        String message = "I'm being tested";
        Stub stub = new Stub(message);
        this.repository.save(stub);
        Stub s = this.repository.findOneById(1L);
        return new ResponseEntity<Stub>(s, HttpStatus.OK);
    }
}
