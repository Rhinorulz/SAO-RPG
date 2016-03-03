/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.cardinal;

import com.shynixn.thegreatswordartonlinerpg.resources.enums.Priority;

public final class CardinalException
extends Exception {
    private static final long serialVersionUID = 1;
    private static long counter = 0;
    private String reason;
    private String solution;
    private String conclusion;
    private Priority priority;
    private long id;

    public CardinalException(String reason, String conclusion, String solution, Priority exceptionPriority) {
        this.reason = reason;
        this.solution = solution;
        this.conclusion = conclusion;
        this.priority = exceptionPriority;
        this.id = ++counter;
    }

    public String getSolution() {
        return this.solution;
    }

    public String getReason() {
        return this.reason;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public String getConclusion() {
        return this.conclusion;
    }

    public long getId() {
        return this.id;
    }

    public static long getExceptionAmount() {
        return counter;
    }

    public static void resetExceptions() {
        counter = 0;
    }
}

