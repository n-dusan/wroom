export class Message {
    public constructor(
        public toUserId?: number,
        public rentRequestId?: number,
        public title?: string,
        public content?: string,
        public fromUserId?: number,
        public date?: Date,
        public toUser?: string,
        public fromUser?: string,
        public fromUserNameSurname?: string,
        public toUserNameSurname?: string
    ) {}
} 