import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHability } from 'app/shared/model/hability.model';
import { HabilityService } from './hability.service';

@Component({
    selector: 'jhi-hability-delete-dialog',
    templateUrl: './hability-delete-dialog.component.html'
})
export class HabilityDeleteDialogComponent {
    hability: IHability;

    constructor(protected habilityService: HabilityService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.habilityService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'habilityListModification',
                content: 'Deleted an hability'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hability-delete-popup',
    template: ''
})
export class HabilityDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hability }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HabilityDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.hability = hability;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/hability', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/hability', { outlets: { popup: null } }]);
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
