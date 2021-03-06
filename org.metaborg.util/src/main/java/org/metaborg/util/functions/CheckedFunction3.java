package org.metaborg.util.functions;

@FunctionalInterface
public interface CheckedFunction3<T1, T2, T3, R, E extends Throwable> {

    R apply(T1 t1, T2 t2, T3 t3) throws E;

}