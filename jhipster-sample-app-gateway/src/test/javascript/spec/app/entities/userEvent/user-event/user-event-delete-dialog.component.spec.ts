/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleGatewayTestModule } from '../../../../test.module';
import { UserEventDeleteDialogComponent } from 'app/entities/userEvent/user-event/user-event-delete-dialog.component';
import { UserEventService } from 'app/entities/userEvent/user-event/user-event.service';

describe('Component Tests', () => {
    describe('UserEvent Management Delete Component', () => {
        let comp: UserEventDeleteDialogComponent;
        let fixture: ComponentFixture<UserEventDeleteDialogComponent>;
        let service: UserEventService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleGatewayTestModule],
                declarations: [UserEventDeleteDialogComponent]
            })
                .overrideTemplate(UserEventDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserEventDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserEventService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
