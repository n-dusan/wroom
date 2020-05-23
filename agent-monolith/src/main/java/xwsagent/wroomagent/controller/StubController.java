package xwsagent.wroomagent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wroomagent.domain.Stub;
import xwsagent.wroomagent.producer.MailProducer;
import xwsagent.wroomagent.repository.StubRepository;

@RestController
@RequestMapping(value="api/stub")

//neka stuba za sad, sluzi za proveru rada dokera
public class StubController {

    @Autowired
    private StubRepository repository;

    @Autowired
    private MailProducer mailProducer;


    @GetMapping(value="/test")
    public ResponseEntity<Stub> test() {

        //mailProducer.send();

        String message = "I'm being tested";
        Stub stub = new Stub(message);
        this.repository.save(stub);
        Stub s = this.repository.findOneById(1L);
        return new ResponseEntity<Stub>(s, HttpStatus.OK);
    }

    @PostMapping(value="/test", produces = "application/json")
    public ResponseEntity<Stub> testPost() {
        Stub s = new Stub("YES, MY SWEET CHILD");
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @GetMapping(value="/test-auth")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> testAuth() {
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
