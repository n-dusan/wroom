import { Ad } from './ad.model';

export class OwnerAds {

    public constructor(
        public ownerId?: number,
        public ads? : number[],
        public ownerUsername?: string,
        public adsObj?: Ad[]
    ) {}

}