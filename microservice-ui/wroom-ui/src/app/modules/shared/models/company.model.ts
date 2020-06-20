export class Company {
    id:number;
    name:string;
    website:string;
    email:string;
    enabled:boolean;
    nonLocked:boolean;
    businessNumber:string;
    address:string;

    constructor(name?: string, website?: string, email?: string, enabled?: boolean, nonLocked?: boolean, businessNumber?: string,  address?: string){
      
        this.name = name;
        this.website = website;
        this.email= email;
        this.enabled = enabled;
        this.nonLocked = nonLocked;
        this.businessNumber = businessNumber;
        this.address = address;
    }

}