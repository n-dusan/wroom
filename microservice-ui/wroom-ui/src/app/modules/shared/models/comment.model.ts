export class CommentModel{
    id:number;
    title: string;
    content: string;
    clientUsername: string;
    approved: boolean;
    adId: number;

    constructor(title?: string, content?: string, clientUsername?: string, approved?: boolean, adId?: number){
        this.title = title;
        this.content = content;
        this.clientUsername = clientUsername;
        this.approved = approved;
        this.adId = adId;
    }
}