package xwsagent.wroomagent.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.domain.Stub;
import xwsagent.wroomagent.producer.MailProducer;
import xwsagent.wroomagent.repository.StubRepository;
import xwsagent.wroomagent.util.RequestCounter;

@RestController
@RequestMapping(value = EndpointConfig.STUB_BASE_URL)
@Log4j2
//neka stuba za sad, sluzi za proveru rada dokera
public class StubController {

    private static final String LOG_TEST_STUB = "action=test user=%s times=%s";

    private final StubRepository repository;
    private final MailProducer mailProducer;
    private final RequestCounter requestCounter;

    public StubController(StubRepository stubRepository, MailProducer mailProducer, RequestCounter requestCounter) {
        this.repository = stubRepository;
        this.mailProducer = mailProducer;
        this.requestCounter = requestCounter;
    }


    @GetMapping(value="/test")
    public ResponseEntity<Stub> test() {
        String logContent = String.format(LOG_TEST_STUB, "public_user", requestCounter.get(EndpointConfig.STUB_BASE_URL));
        //mailProducer.send();

        log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info(logContent);
        log.warn("A WARN Message");
        log.error("An ERROR Message");

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
