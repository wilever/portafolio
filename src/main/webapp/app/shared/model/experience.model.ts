import { Moment } from 'moment';
import { IPersonal } from 'app/shared/model/personal.model';

export interface IExperience {
    id?: number;
    title?: string;
    startDate?: Moment;
    endDate?: Moment;
    isActive?: boolean;
    description?: string;
    personal?: IPersonal;
}

export class Experience implements IExperience {
    constructor(
        public id?: number,
        public title?: string,
        public startDate?: Moment,
        public endDate?: Moment,
        public isActive?: boolean,
        public description?: string,
        public personal?: IPersonal
    ) {
        this.isActive = this.isActive || false;
    }
}
