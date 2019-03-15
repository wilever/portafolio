import { IPersonal } from 'app/shared/model/personal.model';

export const enum LanguagueLevel {
    WRITE = 'WRITE',
    WRITE_LISTENING = 'WRITE_LISTENING',
    WRITE_LISTENING_TALK = 'WRITE_LISTENING_TALK'
}

export interface ILanguague {
    id?: number;
    name?: string;
    isActive?: boolean;
    level?: LanguagueLevel;
    personal?: IPersonal;
}

export class Languague implements ILanguague {
    constructor(
        public id?: number,
        public name?: string,
        public isActive?: boolean,
        public level?: LanguagueLevel,
        public personal?: IPersonal
    ) {
        this.isActive = this.isActive || false;
    }
}
