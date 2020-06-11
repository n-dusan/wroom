package com.wroom.adsservice.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;


@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

	private Long id;

    private String title;

    private String content;

    private Date commentDate;

    private boolean deleted;

    private boolean approved;

    private String clientUsername;

    private Long adId;

}

