import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SocialAccount } from 'app/shared/model/social-account.model';
import { SocialAccountService } from './social-account.service';
import { SocialAccountComponent } from './social-account.component';
import { SocialAccountDetailComponent } from './social-account-detail.component';
import { SocialAccountUpdateComponent } from './social-account-update.component';
import { SocialAccountDeletePopupComponent } from './social-account-delete-dialog.component';
import { ISocialAccount } from 'app/shared/model/social-account.model';

@Injectable({ providedIn: 'root' })
export class SocialAccountResolve implements Resolve<ISocialAccount> {
    constructor(private service: SocialAccountService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISocialAccount> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SocialAccount>) => response.ok),
                map((socialAccount: HttpResponse<SocialAccount>) => socialAccount.body)
            );
        }
        return of(new SocialAccount());
    }
}

export const socialAccountRoute: Routes = [
    {
        path: '',
        component: SocialAccountComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'portafolioApp.socialAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SocialAccountDetailComponent,
        resolve: {
            socialAccount: SocialAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.socialAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SocialAccountUpdateComponent,
        resolve: {
            socialAccount: SocialAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.socialAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SocialAccountUpdateComponent,
        resolve: {
            socialAccount: SocialAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.socialAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const socialAccountPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SocialAccountDeletePopupComponent,
        resolve: {
            socialAccount: SocialAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portafolioApp.socialAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
