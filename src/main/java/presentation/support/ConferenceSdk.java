package presentation.support;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

/**
 * A stand-in for a Java SDK the slides did not write: one operation per
 * asynchronous style found in the wild. Every call answers after 100 ms on the
 * SDK's own thread, never on the caller's. The two counters let a test see
 * whether a wrapper forwarded cancellation.
 */
public final class ConferenceSdk {
  private static final Duration LATENCY = Duration.ofMillis(100);

  private final ScheduledExecutorService executor =
      Executors.newSingleThreadScheduledExecutor(runnable -> {
        Thread thread = new Thread(runnable, "conference-sdk");
        thread.setDaemon(true);
        return thread;
      });
  private final AtomicInteger cancelledCalls = new AtomicInteger();
  private final AtomicInteger activeSubscriptions = new AtomicInteger();

  /** Callback style: the result arrives on the SDK thread, the {@link Call} cancels it. */
  public Call speaker(long id, Callback<Speaker> callback) {
    ScheduledFuture<?> pending = executor.schedule(
        () -> callback.onSuccess(new Speaker(id, "Ada")),
        LATENCY.toMillis(), TimeUnit.MILLISECONDS);
    return () -> {
      if (pending.cancel(false)) cancelledCalls.incrementAndGet();
    };
  }

  /** {@link CompletableFuture} style. */
  public CompletableFuture<List<Talk>> talks(long speakerId) {
    CompletableFuture<List<Talk>> future = new CompletableFuture<>();
    executor.schedule(
        () -> future.complete(List.of(
            new Talk(10, "Structured concurrency"),
            new Talk(11, "Flow"))),
        LATENCY.toMillis(), TimeUnit.MILLISECONDS);
    return future;
  }

  /** Reactive Streams style: the live attendance of a talk, one reading per 100 ms. */
  public Publisher<Integer> attendance(long talkId) {
    return Flux.interval(LATENCY).map(tick -> (int) (tick + 1) * 10);
  }

  /** Listener style: schedule changes pushed until {@link Subscription#unsubscribe()}. */
  public Subscription onScheduleChange(Listener<Talk> listener) {
    activeSubscriptions.incrementAndGet();
    ScheduledFuture<?> pending = executor.scheduleAtFixedRate(
        () -> listener.onEvent(new Talk(12, "Room change")),
        LATENCY.toMillis(), LATENCY.toMillis(), TimeUnit.MILLISECONDS);
    return () -> {
      if (pending.cancel(false)) activeSubscriptions.decrementAndGet();
    };
  }

  /** How many in-flight {@link #speaker} calls were cancelled through their {@link Call}. */
  public int getCancelledCalls() {
    return cancelledCalls.get();
  }

  /** How many {@link #onScheduleChange} listeners have not been unsubscribed. */
  public int getActiveSubscriptions() {
    return activeSubscriptions.get();
  }
}
