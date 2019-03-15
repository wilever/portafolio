import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IHability } from 'app/shared/model/hability.model';

@Component({
    selector: 'jhi-hability-detail',
    templateUrl: './hability-detail.component.html'
})
export class HabilityDetailComponent implements OnInit {
    hability: IHability;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hability }) => {
            this.hability = hability;
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
