/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PortafolioTestModule } from '../../../test.module';
import { PersonalDeleteDialogComponent } from 'app/entities/personal/personal-delete-dialog.component';
import { PersonalService } from 'app/entities/personal/personal.service';

describe('Component Tests', () => {
    describe('Personal Management Delete Component', () => {
        let comp: PersonalDeleteDialogComponent;
        let fixture: ComponentFixture<PersonalDeleteDialogComponent>;
        let service: PersonalService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PortafolioTestModule],
                declarations: [PersonalDeleteDialogComponent]
            })
                .overrideTemplate(PersonalDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PersonalDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonalService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
