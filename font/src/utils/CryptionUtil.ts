import * as crypto from "node:crypto";
import {publicKey} from "@/assets/enum/Constants.ts";

export class CryptionUtil {

    static encrypt(str: string): string {
        const hmac = crypto.createHmac('sha512', publicKey);
        hmac.update(str);


        return hmac.digest('hex')
    }

}