import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Hobby } from 'app/shared/model/hobby.model';
import { HobbyService } from './hobby.service';
import { HobbyComponent } from './hobby.component';
import { HobbyDetailComponent } from './hobby-detail.component';
import { HobbyUpdateComponent } from './hobby-update.component';
import { HobbyDeletePopupComponent } from './hobby-delete-dialog.component';
import { IHobby } from 'app/shared/model/hobby.model';

@Injectable({ providedIn: 'root' })
export class HobbyResolve implements Resolve<IHobby> {
    constructor(private service: HobbyService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHobby> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Hobby>) => response.ok),
                map((hobby: HttpResponse<Hobby>) => hobby.body)
            );
        }
        return of(new Hobby());
    }
}

export const hobbyRoute: Routes = [
    {
        path: '',
        component: HobbyComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'portafolioApp.hobby.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HobbyDetailComponent,
        resolve: {
            hobby: HobbyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.hobby.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HobbyUpdateComponent,
        resolve: {
            hobby: HobbyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.hobby.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HobbyUpdateComponent,
        resolve: {
            hobby: HobbyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.hobby.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hobbyPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: HobbyDeletePopupComponent,
        resolve: {
            hobby: HobbyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.hobby.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
