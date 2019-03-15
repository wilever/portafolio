import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Hability } from 'app/shared/model/hability.model';
import { HabilityService } from './hability.service';
import { HabilityComponent } from './hability.component';
import { HabilityDetailComponent } from './hability-detail.component';
import { HabilityUpdateComponent } from './hability-update.component';
import { HabilityDeletePopupComponent } from './hability-delete-dialog.component';
import { IHability } from 'app/shared/model/hability.model';

@Injectable({ providedIn: 'root' })
export class HabilityResolve implements Resolve<IHability> {
    constructor(private service: HabilityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHability> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Hability>) => response.ok),
                map((hability: HttpResponse<Hability>) => hability.body)
            );
        }
        return of(new Hability());
    }
}

export const habilityRoute: Routes = [
    {
        path: '',
        component: HabilityComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'portafolioApp.hability.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HabilityDetailComponent,
        resolve: {
            hability: HabilityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.hability.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HabilityUpdateComponent,
        resolve: {
            hability: HabilityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.hability.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HabilityUpdateComponent,
        resolve: {
            hability: HabilityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.hability.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const habilityPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: HabilityDeletePopupComponent,
        resolve: {
            hability: HabilityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.hability.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
