package com.wroom.adsservice.soap.converters;

import com.wroom.adsservice.converter.AbstractConverter;
import com.wroom.adsservice.domain.Comment;
import com.wroom.adsservice.soap.xsd.CommentSoap;

public class CommentSoapConverter extends AbstractConverter {

    public static CommentSoap toSoapRequest(Comment entity) {
		CommentSoap ret = new CommentSoap();
		ret.setId(entity.getId());
		ret.setTitle(entity.getTitle() == null ? "" : entity.getTitle());
		ret.setContent(entity.getContent());
		ret.setCommentDate(entity.getCommentDate());
		ret.setClientUsername(entity.getClientUsername());
		ret.setClientId(entity.getClientId());
		System.out.println("ad" + entity.getAd());
		ret.setAdId(entity.getAd().getLocalId());
		ret.setReplyId((entity.getReplyId() == null ? null : entity.getReplyId().intValue()));

		ret.setApproved(entity.isApproved());
		ret.setDeleted(entity.isDeleted());
        ret.setRate((entity.getRate() == null ? 0 : entity.getRate().intValue()));
        ret.setReply(entity.isReply());
		return ret;
	}

}
