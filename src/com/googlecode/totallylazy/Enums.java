package com.googlecode.totallylazy;

import com.googlecode.totallylazy.functions.Function1;

public class Enums {
    public static <T extends Enum<T>> Function1<T, String> name() {
        return anEnum -> anEnum.name();
    }

    public static <T extends Enum<T>> Function1<T, String> name(Class<T> aClass) {
        return name();
    }
}
