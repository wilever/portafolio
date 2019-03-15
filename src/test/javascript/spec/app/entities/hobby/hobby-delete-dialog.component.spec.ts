/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PortafolioTestModule } from '../../../test.module';
import { HobbyDeleteDialogComponent } from 'app/entities/hobby/hobby-delete-dialog.component';
import { HobbyService } from 'app/entities/hobby/hobby.service';

describe('Component Tests', () => {
    describe('Hobby Management Delete Component', () => {
        let comp: HobbyDeleteDialogComponent;
        let fixture: ComponentFixture<HobbyDeleteDialogComponent>;
        let service: HobbyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PortafolioTestModule],
                declarations: [HobbyDeleteDialogComponent]
            })
                .overrideTemplate(HobbyDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HobbyDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HobbyService);
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
