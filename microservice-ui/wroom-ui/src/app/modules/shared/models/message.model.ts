export class Message {
    public constructor(
        public toUserId?: number,
        public rentRequestId?: number,
        public title?: string,
        public content?: string
    ) {}
} 