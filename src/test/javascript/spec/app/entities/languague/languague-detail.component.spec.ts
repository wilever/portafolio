/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PortafolioTestModule } from '../../../test.module';
import { LanguagueDetailComponent } from 'app/entities/languague/languague-detail.component';
import { Languague } from 'app/shared/model/languague.model';

describe('Component Tests', () => {
    describe('Languague Management Detail Component', () => {
        let comp: LanguagueDetailComponent;
        let fixture: ComponentFixture<LanguagueDetailComponent>;
        const route = ({ data: of({ languague: new Languague(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PortafolioTestModule],
                declarations: [LanguagueDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LanguagueDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LanguagueDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.languague).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
