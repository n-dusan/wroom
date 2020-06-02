import { User } from './user.model';
import { Ad } from './ad.model';

export class RentRequest {

    public constructor(
        public id?: number,
        public status?: string,
        public fromDate?: Date,
        public toDate?: Date,
        public requestedUser?: User,
        public ads?: Ad[],
        public requestedUserUsername?: string,
    ) { }

}