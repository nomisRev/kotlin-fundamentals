package coroutines;

/** A push-style SDK subscription: called for every event until it is unsubscribed. */
public interface Listener<T> {
  void onEvent(T event);
}
