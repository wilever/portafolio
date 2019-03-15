import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AboutMe } from 'app/shared/model/about-me.model';
import { AboutMeService } from './about-me.service';
import { AboutMeComponent } from './about-me.component';
import { AboutMeDetailComponent } from './about-me-detail.component';
import { AboutMeUpdateComponent } from './about-me-update.component';
import { AboutMeDeletePopupComponent } from './about-me-delete-dialog.component';
import { IAboutMe } from 'app/shared/model/about-me.model';

@Injectable({ providedIn: 'root' })
export class AboutMeResolve implements Resolve<IAboutMe> {
    constructor(private service: AboutMeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAboutMe> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AboutMe>) => response.ok),
                map((aboutMe: HttpResponse<AboutMe>) => aboutMe.body)
            );
        }
        return of(new AboutMe());
    }
}

export const aboutMeRoute: Routes = [
    {
        path: '',
        component: AboutMeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'portafolioApp.aboutMe.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AboutMeDetailComponent,
        resolve: {
            aboutMe: AboutMeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.aboutMe.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AboutMeUpdateComponent,
        resolve: {
            aboutMe: AboutMeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.aboutMe.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AboutMeUpdateComponent,
        resolve: {
            aboutMe: AboutMeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.aboutMe.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const aboutMePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AboutMeDeletePopupComponent,
        resolve: {
            aboutMe: AboutMeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.aboutMe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
