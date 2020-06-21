package com.wroom.adsservice.soap.converters;

import com.wroom.adsservice.converter.AbstractConverter;
import com.wroom.adsservice.domain.Comment;
import com.wroom.adsservice.soap.xsd.CommentSoap;

public class CommentSoapConverter extends AbstractConverter {

    public static CommentSoap toSoapRequest(Comment entity) {
		CommentSoap ret = new CommentSoap();
		ret.setId(entity.getId());
		ret.setTitle(entity.getTitle() == null ? "" : entity.getTitle());
		System.out.println("title");
		ret.setContent(entity.getContent());
		System.out.println("content");
		ret.setCommentDate(entity.getCommentDate());
		ret.setClientUsername(entity.getClientUsername());
		ret.setClientId(entity.getClientId());
		System.out.println("pre");
		ret.setAdId(entity.getAd().getId());
		System.out.println("posle");
		ret.setReplyId((entity.getReplyId() == null ? null : entity.getReplyId().intValue()));
		System.out.println("reply");

		ret.setApproved(entity.isApproved());
		System.out.println("approved");
		ret.setDeleted(entity.isDeleted());
        ret.setRate((entity.getRate() == null ? 0 : entity.getRate().intValue()));
		System.out.println("rate");
        ret.setReply(entity.isReply());
		System.out.println("wat is it" + ret.getId());
		return ret;
	}

}
