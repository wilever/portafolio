import { IContact } from 'app/shared/model/contact.model';
import { IFormation } from 'app/shared/model/formation.model';

export interface IAddress {
    id?: number;
    country?: string;
    city?: string;
    detail?: string;
    contact?: IContact;
    formation?: IFormation;
}

export class Address implements IAddress {
    constructor(
        public id?: number,
        public country?: string,
        public city?: string,
        public detail?: string,
        public contact?: IContact,
        public formation?: IFormation
    ) {}
}
