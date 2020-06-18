import { User } from './user.model';
import { Ad } from './ad.model';

export class RentRequest {

  public constructor(
    public id?: number,
    public status?: string,
    public fromDate?: Date,
    public toDate?: Date,
    public requestedUserId?: number,
    public ad?: Ad,
    public requestedUserUsername?: string,
    public checked?: boolean,
    public bundleId?: number,
    public requestId?: number
) { }
}
