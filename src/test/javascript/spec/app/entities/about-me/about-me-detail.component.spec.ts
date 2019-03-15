/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PortafolioTestModule } from '../../../test.module';
import { AboutMeDetailComponent } from 'app/entities/about-me/about-me-detail.component';
import { AboutMe } from 'app/shared/model/about-me.model';

describe('Component Tests', () => {
    describe('AboutMe Management Detail Component', () => {
        let comp: AboutMeDetailComponent;
        let fixture: ComponentFixture<AboutMeDetailComponent>;
        const route = ({ data: of({ aboutMe: new AboutMe(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PortafolioTestModule],
                declarations: [AboutMeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AboutMeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AboutMeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.aboutMe).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
