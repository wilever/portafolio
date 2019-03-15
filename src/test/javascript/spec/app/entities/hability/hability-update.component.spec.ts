/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PortafolioTestModule } from '../../../test.module';
import { HabilityUpdateComponent } from 'app/entities/hability/hability-update.component';
import { HabilityService } from 'app/entities/hability/hability.service';
import { Hability } from 'app/shared/model/hability.model';

describe('Component Tests', () => {
    describe('Hability Management Update Component', () => {
        let comp: HabilityUpdateComponent;
        let fixture: ComponentFixture<HabilityUpdateComponent>;
        let service: HabilityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PortafolioTestModule],
                declarations: [HabilityUpdateComponent]
            })
                .overrideTemplate(HabilityUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HabilityUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HabilityService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Hability(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.hability = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Hability();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.hability = entity;
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
