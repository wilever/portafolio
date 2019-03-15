import { IPersonal } from 'app/shared/model/personal.model';

export interface IHobby {
    id?: number;
    name?: string;
    iconContentType?: string;
    icon?: any;
    personal?: IPersonal;
}

export class Hobby implements IHobby {
    constructor(
        public id?: number,
        public name?: string,
        public iconContentType?: string,
        public icon?: any,
        public personal?: IPersonal
    ) {}
}
