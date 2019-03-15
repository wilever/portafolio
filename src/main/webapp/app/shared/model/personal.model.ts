import { IUser } from 'app/core/user/user.model';

export interface IPersonal {
    id?: number;
    firstName?: string;
    lastName?: string;
    profession?: string;
    imageContentType?: string;
    image?: any;
    user?: IUser;
}

export class Personal implements IPersonal {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public profession?: string,
        public imageContentType?: string,
        public image?: any,
        public user?: IUser
    ) {}
}
