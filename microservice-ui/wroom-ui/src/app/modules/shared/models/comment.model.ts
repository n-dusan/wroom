export class CommentModel{
    id:number;
    title: string;
    content: string;
    rating: number;
    adId: number;

    constructor(title?: string, content?: string, rating?: number, adId?: number){
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.adId = adId;
    }
}