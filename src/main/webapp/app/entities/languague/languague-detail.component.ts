import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILanguague } from 'app/shared/model/languague.model';

@Component({
    selector: 'jhi-languague-detail',
    templateUrl: './languague-detail.component.html'
})
export class LanguagueDetailComponent implements OnInit {
    languague: ILanguague;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ languague }) => {
            this.languague = languague;
        });
    }

    previousState() {
        window.history.back();
    }
}
