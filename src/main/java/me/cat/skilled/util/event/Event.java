package me.cat.skilled.util.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Event<T, U> {
    private final List<Subscriber<T, U>> subscribers = new ArrayList<>();

    private final Consumer<U> callback;

    public Event(Consumer<U> callback) {
        this.callback = callback;
    }

    public void subscribe(Subscriber<T, U> subscriber) {
        subscribers.add(subscriber);
    }

    public void notifySubscribers(T ctx) {
        for (var listener : subscribers) {
             callback.accept(listener.onNotified(ctx));
        }
    }
}
