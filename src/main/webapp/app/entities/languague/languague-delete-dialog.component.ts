import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILanguague } from 'app/shared/model/languague.model';
import { LanguagueService } from './languague.service';

@Component({
    selector: 'jhi-languague-delete-dialog',
    templateUrl: './languague-delete-dialog.component.html'
})
export class LanguagueDeleteDialogComponent {
    languague: ILanguague;

    constructor(
        protected languagueService: LanguagueService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.languagueService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'languagueListModification',
                content: 'Deleted an languague'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-languague-delete-popup',
    template: ''
})
export class LanguagueDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ languague }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LanguagueDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.languague = languague;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/languague', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/languague', { outlets: { popup: null } }]);
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
