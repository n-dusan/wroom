export class ValidationError {
    
    public constructor(public status?: string, 
                       public message?: string,
                       public errors?: string[]) {}

}