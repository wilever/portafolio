import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Languague } from 'app/shared/model/languague.model';
import { LanguagueService } from './languague.service';
import { LanguagueComponent } from './languague.component';
import { LanguagueDetailComponent } from './languague-detail.component';
import { LanguagueUpdateComponent } from './languague-update.component';
import { LanguagueDeletePopupComponent } from './languague-delete-dialog.component';
import { ILanguague } from 'app/shared/model/languague.model';

@Injectable({ providedIn: 'root' })
export class LanguagueResolve implements Resolve<ILanguague> {
    constructor(private service: LanguagueService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILanguague> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Languague>) => response.ok),
                map((languague: HttpResponse<Languague>) => languague.body)
            );
        }
        return of(new Languague());
    }
}

export const languagueRoute: Routes = [
    {
        path: '',
        component: LanguagueComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'portafolioApp.languague.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LanguagueDetailComponent,
        resolve: {
            languague: LanguagueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.languague.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LanguagueUpdateComponent,
        resolve: {
            languague: LanguagueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.languague.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LanguagueUpdateComponent,
        resolve: {
            languague: LanguagueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.languague.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const languaguePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LanguagueDeletePopupComponent,
        resolve: {
            languague: LanguagueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.languague.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
