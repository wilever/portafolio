import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'personal',
                loadChildren: './personal/personal.module#PortafolioPersonalModule'
            },
            {
                path: 'contact',
                loadChildren: './contact/contact.module#PortafolioContactModule'
            },
            {
                path: 'social-account',
                loadChildren: './social-account/social-account.module#PortafolioSocialAccountModule'
            },
            {
                path: 'about-me',
                loadChildren: './about-me/about-me.module#PortafolioAboutMeModule'
            },
            {
                path: 'formation',
                loadChildren: './formation/formation.module#PortafolioFormationModule'
            },
            {
                path: 'address',
                loadChildren: './address/address.module#PortafolioAddressModule'
            },
            {
                path: 'experience',
                loadChildren: './experience/experience.module#PortafolioExperienceModule'
            },
            {
                path: 'hability',
                loadChildren: './hability/hability.module#PortafolioHabilityModule'
            },
            {
                path: 'languague',
                loadChildren: './languague/languague.module#PortafolioLanguagueModule'
            },
            {
                path: 'hobby',
                loadChildren: './hobby/hobby.module#PortafolioHobbyModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PortafolioEntityModule {}
