import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IUserEvent } from 'app/shared/model/userEvent/user-event.model';
import { UserEventService } from './user-event.service';

@Component({
    selector: 'jhi-user-event-update',
    templateUrl: './user-event-update.component.html'
})
export class UserEventUpdateComponent implements OnInit {
    userEvent: IUserEvent;
    isSaving: boolean;
    createdDp: any;

    constructor(protected userEventService: UserEventService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userEvent }) => {
            this.userEvent = userEvent;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userEvent.id !== undefined) {
            this.subscribeToSaveResponse(this.userEventService.update(this.userEvent));
        } else {
            this.subscribeToSaveResponse(this.userEventService.create(this.userEvent));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserEvent>>) {
        result.subscribe((res: HttpResponse<IUserEvent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
