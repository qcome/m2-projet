import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserEvent } from 'app/shared/model/userEvent/user-event.model';
import { UserEventService } from './user-event.service';
import { UserEventComponent } from './user-event.component';
import { UserEventDetailComponent } from './user-event-detail.component';
import { UserEventUpdateComponent } from './user-event-update.component';
import { UserEventDeletePopupComponent } from './user-event-delete-dialog.component';
import { IUserEvent } from 'app/shared/model/userEvent/user-event.model';

@Injectable({ providedIn: 'root' })
export class UserEventResolve implements Resolve<IUserEvent> {
    constructor(private service: UserEventService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUserEvent> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<UserEvent>) => response.ok),
                map((userEvent: HttpResponse<UserEvent>) => userEvent.body)
            );
        }
        return of(new UserEvent());
    }
}

export const userEventRoute: Routes = [
    {
        path: '',
        component: UserEventComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'UserEvents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: UserEventDetailComponent,
        resolve: {
            userEvent: UserEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserEvents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: UserEventUpdateComponent,
        resolve: {
            userEvent: UserEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserEvents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: UserEventUpdateComponent,
        resolve: {
            userEvent: UserEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserEvents'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userEventPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: UserEventDeletePopupComponent,
        resolve: {
            userEvent: UserEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserEvents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
