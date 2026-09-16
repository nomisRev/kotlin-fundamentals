package coroutines;

/** The handle an SDK returns for a {@link Listener}. */
public interface Subscription {
  void unsubscribe();
}
