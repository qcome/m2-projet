import { Moment } from 'moment';

export interface IUserEvent {
    id?: number;
    userId?: string;
    eventId?: string;
    avis?: string;
    created?: Moment;
}

export class UserEvent implements IUserEvent {
    constructor(public id?: number, public userId?: string, public eventId?: string, public avis?: string, public created?: Moment) {}
}
