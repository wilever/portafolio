import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PortafolioSharedModule } from 'app/shared';
import {
    SocialAccountComponent,
    SocialAccountDetailComponent,
    SocialAccountUpdateComponent,
    SocialAccountDeletePopupComponent,
    SocialAccountDeleteDialogComponent,
    socialAccountRoute,
    socialAccountPopupRoute
} from './';

const ENTITY_STATES = [...socialAccountRoute, ...socialAccountPopupRoute];

@NgModule({
    imports: [PortafolioSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SocialAccountComponent,
        SocialAccountDetailComponent,
        SocialAccountUpdateComponent,
        SocialAccountDeleteDialogComponent,
        SocialAccountDeletePopupComponent
    ],
    entryComponents: [
        SocialAccountComponent,
        SocialAccountUpdateComponent,
        SocialAccountDeleteDialogComponent,
        SocialAccountDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PortafolioSocialAccountModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
