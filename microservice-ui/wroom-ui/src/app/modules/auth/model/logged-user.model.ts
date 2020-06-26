import { UserTokenState } from './user-token-state.model';

export class LoggedUser {
    constructor(public id: number,
                public email: string,
                public privileges: string[],
                public token: UserTokenState) {}
}
