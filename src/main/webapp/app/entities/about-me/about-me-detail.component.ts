import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAboutMe } from 'app/shared/model/about-me.model';

@Component({
    selector: 'jhi-about-me-detail',
    templateUrl: './about-me-detail.component.html'
})
export class AboutMeDetailComponent implements OnInit {
    aboutMe: IAboutMe;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ aboutMe }) => {
            this.aboutMe = aboutMe;
        });
    }

    previousState() {
        window.history.back();
    }
}
