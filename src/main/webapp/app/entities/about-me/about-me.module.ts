import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PortafolioSharedModule } from 'app/shared';
import {
    AboutMeComponent,
    AboutMeDetailComponent,
    AboutMeUpdateComponent,
    AboutMeDeletePopupComponent,
    AboutMeDeleteDialogComponent,
    aboutMeRoute,
    aboutMePopupRoute
} from './';

const ENTITY_STATES = [...aboutMeRoute, ...aboutMePopupRoute];

@NgModule({
    imports: [PortafolioSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AboutMeComponent,
        AboutMeDetailComponent,
        AboutMeUpdateComponent,
        AboutMeDeleteDialogComponent,
        AboutMeDeletePopupComponent
    ],
    entryComponents: [AboutMeComponent, AboutMeUpdateComponent, AboutMeDeleteDialogComponent, AboutMeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PortafolioAboutMeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
