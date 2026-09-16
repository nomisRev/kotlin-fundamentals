package coroutines;

/** A handle on an in-flight SDK request. */
public interface Call {
  void cancel();
}
