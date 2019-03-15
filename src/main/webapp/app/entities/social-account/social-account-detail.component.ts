import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISocialAccount } from 'app/shared/model/social-account.model';

@Component({
    selector: 'jhi-social-account-detail',
    templateUrl: './social-account-detail.component.html'
})
export class SocialAccountDetailComponent implements OnInit {
    socialAccount: ISocialAccount;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ socialAccount }) => {
            this.socialAccount = socialAccount;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
