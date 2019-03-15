import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISocialAccount } from 'app/shared/model/social-account.model';
import { SocialAccountService } from './social-account.service';

@Component({
    selector: 'jhi-social-account-delete-dialog',
    templateUrl: './social-account-delete-dialog.component.html'
})
export class SocialAccountDeleteDialogComponent {
    socialAccount: ISocialAccount;

    constructor(
        protected socialAccountService: SocialAccountService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.socialAccountService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'socialAccountListModification',
                content: 'Deleted an socialAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-social-account-delete-popup',
    template: ''
})
export class SocialAccountDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ socialAccount }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SocialAccountDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.socialAccount = socialAccount;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/social-account', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/social-account', { outlets: { popup: null } }]);
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
