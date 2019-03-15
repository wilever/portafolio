/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PortafolioTestModule } from '../../../test.module';
import { HabilityDetailComponent } from 'app/entities/hability/hability-detail.component';
import { Hability } from 'app/shared/model/hability.model';

describe('Component Tests', () => {
    describe('Hability Management Detail Component', () => {
        let comp: HabilityDetailComponent;
        let fixture: ComponentFixture<HabilityDetailComponent>;
        const route = ({ data: of({ hability: new Hability(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PortafolioTestModule],
                declarations: [HabilityDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HabilityDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HabilityDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.hability).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
