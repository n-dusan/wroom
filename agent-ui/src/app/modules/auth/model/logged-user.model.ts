import { UserTokenState } from './user-token-state.model';
import { Role } from './role.model';

export class LoggedUser {
    constructor(public id: number,
                public email: number,
                public role: Role,
                public token: UserTokenState) {}
}