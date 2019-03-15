import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonal } from 'app/shared/model/personal.model';
import { PersonalService } from './personal.service';

@Component({
    selector: 'jhi-personal-delete-dialog',
    templateUrl: './personal-delete-dialog.component.html'
})
export class PersonalDeleteDialogComponent {
    personal: IPersonal;

    constructor(protected personalService: PersonalService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.personalService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'personalListModification',
                content: 'Deleted an personal'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-personal-delete-popup',
    template: ''
})
export class PersonalDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ personal }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PersonalDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.personal = personal;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/personal', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/personal', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
