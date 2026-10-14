package com.example.shop.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-10-14
 * @description
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MaskingUtil {
    public static String maskingCustomerId(String customerId)
    {
        int visibleLength = customerId.length() / 2;
        return customerId.substring(0, visibleLength) + "*".repeat(customerId.length() - visibleLength);
    }
}
