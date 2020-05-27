import { Privilege } from './privilege.model';

export class Role {

    constructor(public name: string,
                public privileges: Privilege[]) {}

}