export class ModelType{
    id:number;
    name:string;
    brandId: number;

    constructor(name?: string, brandId?: number){
        this.name = name;
        this.brandId = brandId
    }
}