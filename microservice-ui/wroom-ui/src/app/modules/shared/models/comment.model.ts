export class CommentModel{
    id:number;
    title: string;
    content: string;
    clientUsername: string;
    approved: boolean;
    adId: number;
    commentDate: Date;

    constructor(title?: string, content?: string, clientUsername?: string, approved?: boolean, adId?: number, commentDate?: Date){
        this.title = title;
        this.content = content;
        this.clientUsername = clientUsername;
        this.approved = approved;
        this.adId = adId;
        this.commentDate = commentDate;
    }
}