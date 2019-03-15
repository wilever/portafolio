import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPersonal } from 'app/shared/model/personal.model';

@Component({
    selector: 'jhi-personal-detail',
    templateUrl: './personal-detail.component.html'
})
export class PersonalDetailComponent implements OnInit {
    personal: IPersonal;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ personal }) => {
            this.personal = personal;
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
