import { IPersonal } from 'app/shared/model/personal.model';

export interface IFormation {
    id?: number;
    career?: string;
    university?: string;
    description?: string;
    personal?: IPersonal;
}

export class Formation implements IFormation {
    constructor(
        public id?: number,
        public career?: string,
        public university?: string,
        public description?: string,
        public personal?: IPersonal
    ) {}
}
