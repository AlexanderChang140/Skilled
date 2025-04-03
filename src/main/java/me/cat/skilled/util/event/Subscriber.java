package me.cat.skilled.util.event;

public interface Subscriber<T, U> {
    U onNotified(T ctx);
}
