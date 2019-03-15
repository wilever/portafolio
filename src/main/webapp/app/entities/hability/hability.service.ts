import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHability } from 'app/shared/model/hability.model';

type EntityResponseType = HttpResponse<IHability>;
type EntityArrayResponseType = HttpResponse<IHability[]>;

@Injectable({ providedIn: 'root' })
export class HabilityService {
    public resourceUrl = SERVER_API_URL + 'api/habilities';

    constructor(protected http: HttpClient) {}

    create(hability: IHability): Observable<EntityResponseType> {
        return this.http.post<IHability>(this.resourceUrl, hability, { observe: 'response' });
    }

    update(hability: IHability): Observable<EntityResponseType> {
        return this.http.put<IHability>(this.resourceUrl, hability, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IHability>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IHability[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
