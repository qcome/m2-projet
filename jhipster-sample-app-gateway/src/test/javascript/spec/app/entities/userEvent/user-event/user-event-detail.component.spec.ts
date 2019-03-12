/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleGatewayTestModule } from '../../../../test.module';
import { UserEventDetailComponent } from 'app/entities/userEvent/user-event/user-event-detail.component';
import { UserEvent } from 'app/shared/model/userEvent/user-event.model';

describe('Component Tests', () => {
    describe('UserEvent Management Detail Component', () => {
        let comp: UserEventDetailComponent;
        let fixture: ComponentFixture<UserEventDetailComponent>;
        const route = ({ data: of({ userEvent: new UserEvent(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleGatewayTestModule],
                declarations: [UserEventDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UserEventDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserEventDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.userEvent).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
