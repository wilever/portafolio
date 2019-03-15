/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PortafolioTestModule } from '../../../test.module';
import { PersonalUpdateComponent } from 'app/entities/personal/personal-update.component';
import { PersonalService } from 'app/entities/personal/personal.service';
import { Personal } from 'app/shared/model/personal.model';

describe('Component Tests', () => {
    describe('Personal Management Update Component', () => {
        let comp: PersonalUpdateComponent;
        let fixture: ComponentFixture<PersonalUpdateComponent>;
        let service: PersonalService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PortafolioTestModule],
                declarations: [PersonalUpdateComponent]
            })
                .overrideTemplate(PersonalUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PersonalUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonalService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Personal(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.personal = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Personal();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.personal = entity;
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
