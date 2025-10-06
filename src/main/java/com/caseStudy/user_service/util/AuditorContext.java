package com.caseStudy.user_service.util;

import java.util.UUID;

public class AuditorContext {
    private static final ThreadLocal<UUID> CURRENT = new ThreadLocal<>();
    private AuditorContext() {}
    public static void set(UUID id) { CURRENT.set(id); }
    public static UUID get() { return CURRENT.get(); }
    public static void clear() { CURRENT.remove(); }
}
