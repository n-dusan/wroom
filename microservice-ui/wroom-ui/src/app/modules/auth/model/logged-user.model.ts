import { UserTokenState } from './user-token-state.model';

export class LoggedUser {
    constructor(public id: number,
                public email: number,
                public privileges: string[],
                public token: UserTokenState) {}
}
