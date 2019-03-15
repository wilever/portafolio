import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILanguague } from 'app/shared/model/languague.model';

type EntityResponseType = HttpResponse<ILanguague>;
type EntityArrayResponseType = HttpResponse<ILanguague[]>;

@Injectable({ providedIn: 'root' })
export class LanguagueService {
    public resourceUrl = SERVER_API_URL + 'api/languagues';

    constructor(protected http: HttpClient) {}

    create(languague: ILanguague): Observable<EntityResponseType> {
        return this.http.post<ILanguague>(this.resourceUrl, languague, { observe: 'response' });
    }

    update(languague: ILanguague): Observable<EntityResponseType> {
        return this.http.put<ILanguague>(this.resourceUrl, languague, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILanguague>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILanguague[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
