import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PortafolioSharedModule } from 'app/shared';
import {
    PersonalComponent,
    PersonalDetailComponent,
    PersonalUpdateComponent,
    PersonalDeletePopupComponent,
    PersonalDeleteDialogComponent,
    personalRoute,
    personalPopupRoute
} from './';

const ENTITY_STATES = [...personalRoute, ...personalPopupRoute];

@NgModule({
    imports: [PortafolioSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PersonalComponent,
        PersonalDetailComponent,
        PersonalUpdateComponent,
        PersonalDeleteDialogComponent,
        PersonalDeletePopupComponent
    ],
    entryComponents: [PersonalComponent, PersonalUpdateComponent, PersonalDeleteDialogComponent, PersonalDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PortafolioPersonalModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
