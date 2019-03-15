/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PortafolioTestModule } from '../../../test.module';
import { SocialAccountDeleteDialogComponent } from 'app/entities/social-account/social-account-delete-dialog.component';
import { SocialAccountService } from 'app/entities/social-account/social-account.service';

describe('Component Tests', () => {
    describe('SocialAccount Management Delete Component', () => {
        let comp: SocialAccountDeleteDialogComponent;
        let fixture: ComponentFixture<SocialAccountDeleteDialogComponent>;
        let service: SocialAccountService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PortafolioTestModule],
                declarations: [SocialAccountDeleteDialogComponent]
            })
                .overrideTemplate(SocialAccountDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SocialAccountDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SocialAccountService);
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
