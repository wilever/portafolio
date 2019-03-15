/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PortafolioTestModule } from '../../../test.module';
import { PersonalDetailComponent } from 'app/entities/personal/personal-detail.component';
import { Personal } from 'app/shared/model/personal.model';

describe('Component Tests', () => {
    describe('Personal Management Detail Component', () => {
        let comp: PersonalDetailComponent;
        let fixture: ComponentFixture<PersonalDetailComponent>;
        const route = ({ data: of({ personal: new Personal(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PortafolioTestModule],
                declarations: [PersonalDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PersonalDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PersonalDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.personal).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
