/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PortafolioTestModule } from '../../../test.module';
import { AboutMeUpdateComponent } from 'app/entities/about-me/about-me-update.component';
import { AboutMeService } from 'app/entities/about-me/about-me.service';
import { AboutMe } from 'app/shared/model/about-me.model';

describe('Component Tests', () => {
    describe('AboutMe Management Update Component', () => {
        let comp: AboutMeUpdateComponent;
        let fixture: ComponentFixture<AboutMeUpdateComponent>;
        let service: AboutMeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PortafolioTestModule],
                declarations: [AboutMeUpdateComponent]
            })
                .overrideTemplate(AboutMeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AboutMeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AboutMeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new AboutMe(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.aboutMe = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new AboutMe();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.aboutMe = entity;
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
