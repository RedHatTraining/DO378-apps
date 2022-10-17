package com.redhat.training;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import java.util.Optional;

@ConfigMapping(prefix = "expense")
public interface ExpenseConfiguration {

    @WithDefault("false")
    boolean debugEnabled();

    Optional<String> debugMessage();

    int rangeHigh();

    int rangeLow();
}
