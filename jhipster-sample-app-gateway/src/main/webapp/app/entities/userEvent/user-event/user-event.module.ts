import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleGatewaySharedModule } from 'app/shared';
import {
    UserEventComponent,
    UserEventDetailComponent,
    UserEventUpdateComponent,
    UserEventDeletePopupComponent,
    UserEventDeleteDialogComponent,
    userEventRoute,
    userEventPopupRoute
} from './';

const ENTITY_STATES = [...userEventRoute, ...userEventPopupRoute];

@NgModule({
    imports: [JhipsterSampleGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserEventComponent,
        UserEventDetailComponent,
        UserEventUpdateComponent,
        UserEventDeleteDialogComponent,
        UserEventDeletePopupComponent
    ],
    entryComponents: [UserEventComponent, UserEventUpdateComponent, UserEventDeleteDialogComponent, UserEventDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UserEventUserEventModule {}
