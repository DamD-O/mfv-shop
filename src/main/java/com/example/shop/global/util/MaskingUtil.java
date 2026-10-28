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

    public static String maskingEmail(String email)
    {
        String localPart = email.substring(0, email.indexOf("@"));
        int visibleLength = localPart.length() / 2;
        return email.substring(0, visibleLength) + "*".repeat(localPart.length() - visibleLength) + email.substring(email.indexOf("@"));
    }

    public static String maskingPhone(String phone)
    {
        return phone.substring(0, phone.indexOf('-') + 1) + "*".repeat(4) + phone.substring(phone.lastIndexOf('-'));
    }

    public static String maskingName(String name)
    {
        if (name.length() == 1) return "*";
        else if (name.length() == 2) return name.substring(0, 1) + "*";
        else return name.substring(0, 1) + "*".repeat(name.length() - 2) + name.substring(name.length() - 1);
    }
}
