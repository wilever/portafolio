import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISocialAccount } from 'app/shared/model/social-account.model';

type EntityResponseType = HttpResponse<ISocialAccount>;
type EntityArrayResponseType = HttpResponse<ISocialAccount[]>;

@Injectable({ providedIn: 'root' })
export class SocialAccountService {
    public resourceUrl = SERVER_API_URL + 'api/social-accounts';

    constructor(protected http: HttpClient) {}

    create(socialAccount: ISocialAccount): Observable<EntityResponseType> {
        return this.http.post<ISocialAccount>(this.resourceUrl, socialAccount, { observe: 'response' });
    }

    update(socialAccount: ISocialAccount): Observable<EntityResponseType> {
        return this.http.put<ISocialAccount>(this.resourceUrl, socialAccount, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISocialAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISocialAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
