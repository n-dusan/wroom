package xwsagent.wroomagent.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xwsagent.wroomagent.domain.auth.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor
public class RentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double traveledMiles;

    @Column
    private String note;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateReport;

    @OneToOne(mappedBy = "rentReport", cascade = CascadeType.ALL)
    private RentRequest rentRequest;
    
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private User user;
}
