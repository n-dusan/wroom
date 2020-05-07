package xwsagent.wroomagent.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column
    private Boolean deleted;

    @Column
    private Boolean approved;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private User client;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Ad ad;


}
