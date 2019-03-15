import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHobby } from 'app/shared/model/hobby.model';

type EntityResponseType = HttpResponse<IHobby>;
type EntityArrayResponseType = HttpResponse<IHobby[]>;

@Injectable({ providedIn: 'root' })
export class HobbyService {
    public resourceUrl = SERVER_API_URL + 'api/hobbies';

    constructor(protected http: HttpClient) {}

    create(hobby: IHobby): Observable<EntityResponseType> {
        return this.http.post<IHobby>(this.resourceUrl, hobby, { observe: 'response' });
    }

    update(hobby: IHobby): Observable<EntityResponseType> {
        return this.http.put<IHobby>(this.resourceUrl, hobby, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IHobby>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IHobby[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
