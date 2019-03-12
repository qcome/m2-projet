import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserEvent } from 'app/shared/model/userEvent/user-event.model';

@Component({
    selector: 'jhi-user-event-detail',
    templateUrl: './user-event-detail.component.html'
})
export class UserEventDetailComponent implements OnInit {
    userEvent: IUserEvent;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userEvent }) => {
            this.userEvent = userEvent;
        });
    }

    previousState() {
        window.history.back();
    }
}
