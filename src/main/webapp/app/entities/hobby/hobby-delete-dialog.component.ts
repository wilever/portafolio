import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHobby } from 'app/shared/model/hobby.model';
import { HobbyService } from './hobby.service';

@Component({
    selector: 'jhi-hobby-delete-dialog',
    templateUrl: './hobby-delete-dialog.component.html'
})
export class HobbyDeleteDialogComponent {
    hobby: IHobby;

    constructor(protected hobbyService: HobbyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hobbyService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'hobbyListModification',
                content: 'Deleted an hobby'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hobby-delete-popup',
    template: ''
})
export class HobbyDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hobby }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HobbyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.hobby = hobby;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/hobby', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/hobby', { outlets: { popup: null } }]);
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
