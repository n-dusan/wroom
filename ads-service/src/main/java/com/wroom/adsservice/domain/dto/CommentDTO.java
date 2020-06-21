package com.wroom.adsservice.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;


@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

	private Long id;
	private String title;
	private String content;
	private Date date;
	private Integer rate;
	private String username;
	private Long clientId;
	private Long adId;
	private Long replyId;
	private boolean reply;
	private boolean approved;
	private boolean deleted;

}

