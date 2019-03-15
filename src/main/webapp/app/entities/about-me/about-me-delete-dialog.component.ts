import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAboutMe } from 'app/shared/model/about-me.model';
import { AboutMeService } from './about-me.service';

@Component({
    selector: 'jhi-about-me-delete-dialog',
    templateUrl: './about-me-delete-dialog.component.html'
})
export class AboutMeDeleteDialogComponent {
    aboutMe: IAboutMe;

    constructor(protected aboutMeService: AboutMeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.aboutMeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'aboutMeListModification',
                content: 'Deleted an aboutMe'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-about-me-delete-popup',
    template: ''
})
export class AboutMeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ aboutMe }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AboutMeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.aboutMe = aboutMe;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/about-me', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/about-me', { outlets: { popup: null } }]);
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
