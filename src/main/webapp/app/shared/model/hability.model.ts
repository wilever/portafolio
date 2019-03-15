import { IPersonal } from 'app/shared/model/personal.model';

export const enum HabilityType {
    FRONTEND = 'FRONTEND',
    BACKEND = 'BACKEND',
    CI_CD = 'CI_CD',
    QA = 'QA'
}

export interface IHability {
    id?: number;
    type?: HabilityType;
    name?: string;
    iconContentType?: string;
    icon?: any;
    personal?: IPersonal;
}

export class Hability implements IHability {
    constructor(
        public id?: number,
        public type?: HabilityType,
        public name?: string,
        public iconContentType?: string,
        public icon?: any,
        public personal?: IPersonal
    ) {}
}
