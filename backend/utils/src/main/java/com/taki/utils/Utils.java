package com.taki.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static String randomCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(ThreadLocalRandom.current().nextInt(0, 10));
        }
        return String.valueOf(code);
    }
}
