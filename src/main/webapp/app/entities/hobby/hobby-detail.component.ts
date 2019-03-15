import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IHobby } from 'app/shared/model/hobby.model';

@Component({
    selector: 'jhi-hobby-detail',
    templateUrl: './hobby-detail.component.html'
})
export class HobbyDetailComponent implements OnInit {
    hobby: IHobby;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hobby }) => {
            this.hobby = hobby;
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
