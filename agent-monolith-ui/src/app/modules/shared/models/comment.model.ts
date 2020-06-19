export class Comment {

    public constructor(
        public id?: number,
        public title?: string,
        public content?: string,
        public date?: Date,
        public rate?: number,
        public username?: string,
        public adId?: number,
        public replyId?: number,
        public reply?: boolean,
        public approved?: boolean,
        public replyObj?: Comment
    ) {}

}