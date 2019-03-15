import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PortafolioSharedModule } from 'app/shared';
import {
    LanguagueComponent,
    LanguagueDetailComponent,
    LanguagueUpdateComponent,
    LanguagueDeletePopupComponent,
    LanguagueDeleteDialogComponent,
    languagueRoute,
    languaguePopupRoute
} from './';

const ENTITY_STATES = [...languagueRoute, ...languaguePopupRoute];

@NgModule({
    imports: [PortafolioSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LanguagueComponent,
        LanguagueDetailComponent,
        LanguagueUpdateComponent,
        LanguagueDeleteDialogComponent,
        LanguagueDeletePopupComponent
    ],
    entryComponents: [LanguagueComponent, LanguagueUpdateComponent, LanguagueDeleteDialogComponent, LanguagueDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PortafolioLanguagueModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
