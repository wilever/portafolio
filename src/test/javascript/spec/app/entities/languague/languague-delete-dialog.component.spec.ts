/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PortafolioTestModule } from '../../../test.module';
import { LanguagueDeleteDialogComponent } from 'app/entities/languague/languague-delete-dialog.component';
import { LanguagueService } from 'app/entities/languague/languague.service';

describe('Component Tests', () => {
    describe('Languague Management Delete Component', () => {
        let comp: LanguagueDeleteDialogComponent;
        let fixture: ComponentFixture<LanguagueDeleteDialogComponent>;
        let service: LanguagueService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PortafolioTestModule],
                declarations: [LanguagueDeleteDialogComponent]
            })
                .overrideTemplate(LanguagueDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LanguagueDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LanguagueService);
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
