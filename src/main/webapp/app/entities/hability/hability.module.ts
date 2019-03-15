import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PortafolioSharedModule } from 'app/shared';
import {
    HabilityComponent,
    HabilityDetailComponent,
    HabilityUpdateComponent,
    HabilityDeletePopupComponent,
    HabilityDeleteDialogComponent,
    habilityRoute,
    habilityPopupRoute
} from './';

const ENTITY_STATES = [...habilityRoute, ...habilityPopupRoute];

@NgModule({
    imports: [PortafolioSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HabilityComponent,
        HabilityDetailComponent,
        HabilityUpdateComponent,
        HabilityDeleteDialogComponent,
        HabilityDeletePopupComponent
    ],
    entryComponents: [HabilityComponent, HabilityUpdateComponent, HabilityDeleteDialogComponent, HabilityDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PortafolioHabilityModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
