package xwsagent.wroomagent.soap.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "comment"
})
@XmlRootElement(name = "SendCommentRequest")
public class SendCommentRequest {

    @XmlElement(required = true)
    protected CommentSoap comment;

    public CommentSoap getComment() {
        return comment;
    }

    public void setComment(CommentSoap value) {
        this.comment = value;
    }

}
