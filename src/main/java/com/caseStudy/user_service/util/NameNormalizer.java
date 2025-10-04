package com.caseStudy.user_service.util;

import java.text.Normalizer;

public final class NameNormalizer {

    private NameNormalizer() {}


    public static String normalize(String input) {
        if (input == null) return null;
        String n = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "");
        n = n.toLowerCase().replaceAll("[^a-z0-9]", "");
        return n;
    }
}
