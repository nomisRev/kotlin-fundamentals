package coroutines;

/** The callback shape of a classic Java SDK: exactly one of the two methods is called. */
public interface Callback<T> {
  void onSuccess(T value);

  void onFailure(Throwable error);
}
