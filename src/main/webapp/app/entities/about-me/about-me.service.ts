import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAboutMe } from 'app/shared/model/about-me.model';

type EntityResponseType = HttpResponse<IAboutMe>;
type EntityArrayResponseType = HttpResponse<IAboutMe[]>;

@Injectable({ providedIn: 'root' })
export class AboutMeService {
    public resourceUrl = SERVER_API_URL + 'api/about-mes';

    constructor(protected http: HttpClient) {}

    create(aboutMe: IAboutMe): Observable<EntityResponseType> {
        return this.http.post<IAboutMe>(this.resourceUrl, aboutMe, { observe: 'response' });
    }

    update(aboutMe: IAboutMe): Observable<EntityResponseType> {
        return this.http.put<IAboutMe>(this.resourceUrl, aboutMe, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAboutMe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAboutMe[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
