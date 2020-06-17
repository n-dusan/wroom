package xwsagent.wroomagent.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(nullable = false)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date commentDate;
    
    @Column
    private Integer rating;

    @Column
    private boolean deleted;

    @Column
    private Boolean approved;
    
    @Column
    private Integer rate;

    @Column
    private String clientUsername;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Ad ad;

    @Column
    private Long replyId;
    
    @Column(nullable = false)
    private boolean reply;		// Marks if a comment is a reply to another comment
}
