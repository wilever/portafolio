import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Personal } from 'app/shared/model/personal.model';
import { PersonalService } from './personal.service';
import { PersonalComponent } from './personal.component';
import { PersonalDetailComponent } from './personal-detail.component';
import { PersonalUpdateComponent } from './personal-update.component';
import { PersonalDeletePopupComponent } from './personal-delete-dialog.component';
import { IPersonal } from 'app/shared/model/personal.model';

@Injectable({ providedIn: 'root' })
export class PersonalResolve implements Resolve<IPersonal> {
    constructor(private service: PersonalService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPersonal> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Personal>) => response.ok),
                map((personal: HttpResponse<Personal>) => personal.body)
            );
        }
        return of(new Personal());
    }
}

export const personalRoute: Routes = [
    {
        path: '',
        component: PersonalComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'portafolioApp.personal.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PersonalDetailComponent,
        resolve: {
            personal: PersonalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.personal.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PersonalUpdateComponent,
        resolve: {
            personal: PersonalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.personal.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PersonalUpdateComponent,
        resolve: {
            personal: PersonalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.personal.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personalPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PersonalDeletePopupComponent,
        resolve: {
            personal: PersonalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.personal.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
