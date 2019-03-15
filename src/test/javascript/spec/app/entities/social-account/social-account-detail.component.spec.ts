/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PortafolioTestModule } from '../../../test.module';
import { SocialAccountDetailComponent } from 'app/entities/social-account/social-account-detail.component';
import { SocialAccount } from 'app/shared/model/social-account.model';

describe('Component Tests', () => {
    describe('SocialAccount Management Detail Component', () => {
        let comp: SocialAccountDetailComponent;
        let fixture: ComponentFixture<SocialAccountDetailComponent>;
        const route = ({ data: of({ socialAccount: new SocialAccount(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PortafolioTestModule],
                declarations: [SocialAccountDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SocialAccountDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SocialAccountDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.socialAccount).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
