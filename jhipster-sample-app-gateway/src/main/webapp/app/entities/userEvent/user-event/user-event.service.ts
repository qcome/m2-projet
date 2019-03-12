import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserEvent } from 'app/shared/model/userEvent/user-event.model';

type EntityResponseType = HttpResponse<IUserEvent>;
type EntityArrayResponseType = HttpResponse<IUserEvent[]>;

@Injectable({ providedIn: 'root' })
export class UserEventService {
    public resourceUrl = SERVER_API_URL + 'userevent/api/user-events';

    constructor(protected http: HttpClient) {}

    create(userEvent: IUserEvent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(userEvent);
        return this.http
            .post<IUserEvent>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(userEvent: IUserEvent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(userEvent);
        return this.http
            .put<IUserEvent>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IUserEvent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IUserEvent[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(userEvent: IUserEvent): IUserEvent {
        const copy: IUserEvent = Object.assign({}, userEvent, {
            created: userEvent.created != null && userEvent.created.isValid() ? userEvent.created.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.created = res.body.created != null ? moment(res.body.created) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((userEvent: IUserEvent) => {
                userEvent.created = userEvent.created != null ? moment(userEvent.created) : null;
            });
        }
        return res;
    }
}
