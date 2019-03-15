/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PortafolioTestModule } from '../../../test.module';
import { LanguagueUpdateComponent } from 'app/entities/languague/languague-update.component';
import { LanguagueService } from 'app/entities/languague/languague.service';
import { Languague } from 'app/shared/model/languague.model';

describe('Component Tests', () => {
    describe('Languague Management Update Component', () => {
        let comp: LanguagueUpdateComponent;
        let fixture: ComponentFixture<LanguagueUpdateComponent>;
        let service: LanguagueService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PortafolioTestModule],
                declarations: [LanguagueUpdateComponent]
            })
                .overrideTemplate(LanguagueUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LanguagueUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LanguagueService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Languague(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.languague = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Languague();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.languague = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
