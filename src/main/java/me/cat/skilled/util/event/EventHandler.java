package me.cat.skilled.util.event;

public class EventHandler<T, U> {
    private final Event<T, U> event;

    public EventHandler(Event<T,U> event) {
        this.event = event;
    }

    public void subscribe(Subscriber<T, U> subscriber) {
        event.subscribe(subscriber);
    }

    public void unsubscribe(Subscriber<T, U> subscriber) {
        event.subscribe(subscriber);
    }
}
