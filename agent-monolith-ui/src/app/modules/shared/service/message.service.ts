import { Message } from '../../shared/models/message.model';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class MessageService {

    private baseUrl = environment.protocol
        + '://' + environment.domain
        + ':'
        + environment.port
        + environment.api
        + '/messages';

    constructor(private http: HttpClient) { }


    public send(message: Message): Observable<any> {
        return this.http.post<any>(this.baseUrl, message);
    }

    public received(): Observable<Message[]> {
        return this.http.get<Message[]>(this.baseUrl + '/received');
    }

    public sent(): Observable<Message[]> {
        return this.http.get<Message[]>(this.baseUrl + '/sent');
    }
}