export class CommentModel{
    id:number;
    title: string;
    content: string;

    constructor(title?: string, content?: string){
        this.title = title;
        this.content = content;
    }
}