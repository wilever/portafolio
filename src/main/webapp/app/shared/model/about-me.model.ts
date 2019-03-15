import { IPersonal } from 'app/shared/model/personal.model';

export interface IAboutMe {
    id?: number;
    resume?: string;
    personal?: IPersonal;
}

export class AboutMe implements IAboutMe {
    constructor(public id?: number, public resume?: string, public personal?: IPersonal) {}
}
