import { IContact } from 'app/shared/model/contact.model';

export const enum Network {
    GITHUB = 'GITHUB',
    TWITTER = 'TWITTER',
    LINKEDIN = 'LINKEDIN',
    INSTAGRAM = 'INSTAGRAM',
    OTHER = 'OTHER'
}

export interface ISocialAccount {
    id?: number;
    network?: Network;
    user?: string;
    otherNetwork?: string;
    iconContentType?: string;
    icon?: any;
    contact?: IContact;
}

export class SocialAccount implements ISocialAccount {
    constructor(
        public id?: number,
        public network?: Network,
        public user?: string,
        public otherNetwork?: string,
        public iconContentType?: string,
        public icon?: any,
        public contact?: IContact
    ) {}
}
