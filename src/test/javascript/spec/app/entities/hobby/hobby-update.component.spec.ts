/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PortafolioTestModule } from '../../../test.module';
import { HobbyUpdateComponent } from 'app/entities/hobby/hobby-update.component';
import { HobbyService } from 'app/entities/hobby/hobby.service';
import { Hobby } from 'app/shared/model/hobby.model';

describe('Component Tests', () => {
    describe('Hobby Management Update Component', () => {
        let comp: HobbyUpdateComponent;
        let fixture: ComponentFixture<HobbyUpdateComponent>;
        let service: HobbyService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PortafolioTestModule],
                declarations: [HobbyUpdateComponent]
            })
                .overrideTemplate(HobbyUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HobbyUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HobbyService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Hobby(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.hobby = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Hobby();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.hobby = entity;
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
