import { IPersonal } from 'app/shared/model/personal.model';

export interface IContact {
    id?: number;
    email?: string;
    phone?: string;
    address?: string;
    personal?: IPersonal;
}

export class Contact implements IContact {
    constructor(public id?: number, public email?: string, public phone?: string, public address?: string, public personal?: IPersonal) {}
}
