/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PortafolioTestModule } from '../../../test.module';
import { AboutMeDeleteDialogComponent } from 'app/entities/about-me/about-me-delete-dialog.component';
import { AboutMeService } from 'app/entities/about-me/about-me.service';

describe('Component Tests', () => {
    describe('AboutMe Management Delete Component', () => {
        let comp: AboutMeDeleteDialogComponent;
        let fixture: ComponentFixture<AboutMeDeleteDialogComponent>;
        let service: AboutMeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PortafolioTestModule],
                declarations: [AboutMeDeleteDialogComponent]
            })
                .overrideTemplate(AboutMeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AboutMeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AboutMeService);
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
