import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PortafolioSharedModule } from 'app/shared';
import {
    HobbyComponent,
    HobbyDetailComponent,
    HobbyUpdateComponent,
    HobbyDeletePopupComponent,
    HobbyDeleteDialogComponent,
    hobbyRoute,
    hobbyPopupRoute
} from './';

const ENTITY_STATES = [...hobbyRoute, ...hobbyPopupRoute];

@NgModule({
    imports: [PortafolioSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [HobbyComponent, HobbyDetailComponent, HobbyUpdateComponent, HobbyDeleteDialogComponent, HobbyDeletePopupComponent],
    entryComponents: [HobbyComponent, HobbyUpdateComponent, HobbyDeleteDialogComponent, HobbyDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PortafolioHobbyModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
