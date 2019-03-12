import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserEvent } from 'app/shared/model/userEvent/user-event.model';
import { UserEventService } from './user-event.service';

@Component({
    selector: 'jhi-user-event-delete-dialog',
    templateUrl: './user-event-delete-dialog.component.html'
})
export class UserEventDeleteDialogComponent {
    userEvent: IUserEvent;

    constructor(
        protected userEventService: UserEventService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userEventService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'userEventListModification',
                content: 'Deleted an userEvent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-event-delete-popup',
    template: ''
})
export class UserEventDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userEvent }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UserEventDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.userEvent = userEvent;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/user-event', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/user-event', { outlets: { popup: null } }]);
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
