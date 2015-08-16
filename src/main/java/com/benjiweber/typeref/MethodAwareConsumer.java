package com.benjiweber.typeref;

import java.util.function.Consumer;
import java.util.function.Function;

public interface MethodAwareConsumer<T> extends Consumer<T>, MethodFinder { }
