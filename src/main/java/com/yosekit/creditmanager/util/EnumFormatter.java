package com.yosekit.creditmanager.util;

import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.ResourceBundle;

@Service
public class EnumFormatter {
    private static final ResourceBundle bundle = ResourceBundle.getBundle(
            "bundles.messages", new Locale("ru", "RU"));

    public static String format(Enum<?> enumValue) {
        String key = enumValue.getClass().getSimpleName() + "." + enumValue.name();
        return bundle.getString(key);
    }
}
