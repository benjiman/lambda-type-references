package com.benjiweber.typeref;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface MethodAwarePredicate<T> extends Predicate<T>, MethodFinder { }