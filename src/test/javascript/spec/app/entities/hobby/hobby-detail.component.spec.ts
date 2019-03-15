/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PortafolioTestModule } from '../../../test.module';
import { HobbyDetailComponent } from 'app/entities/hobby/hobby-detail.component';
import { Hobby } from 'app/shared/model/hobby.model';

describe('Component Tests', () => {
    describe('Hobby Management Detail Component', () => {
        let comp: HobbyDetailComponent;
        let fixture: ComponentFixture<HobbyDetailComponent>;
        const route = ({ data: of({ hobby: new Hobby(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PortafolioTestModule],
                declarations: [HobbyDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HobbyDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HobbyDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.hobby).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
