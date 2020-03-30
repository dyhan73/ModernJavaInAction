import io.reactivex.Observable;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Observable<TempInfo> observable = getNegativeTemperature("New York");
        observable.blockingSubscribe(new TempObserver());

        Observable<TempInfo> observable2 = getCelsiusTemperatures(
                "New York", "Chicago", "San Francisco"
        );
        observable2.blockingSubscribe(new TempObserver());
    }

    public static Observable<TempInfo> getTemperature(String town) {
        return Observable.create(emitter ->
                Observable.interval(1, TimeUnit.SECONDS)
                        .subscribe(i -> {
                            if (!emitter.isDisposed()) {
                                if ( i >= 10 ) {
                                    emitter.onComplete();
                                } else {
                                    try {
                                        emitter.onNext(TempInfo.fetch(town));
                                    } catch (Exception e) {
                                        emitter.onError(e);
                                    }
                                }
                            }
                        }));
    }

    public static Observable<TempInfo> getCelsiusTemperature(String town) {
        return getTemperature(town)
                .map(temp -> new TempInfo(temp.getTown(), (temp.getTemp() - 32) * 5 / 9));
    }

    public static Observable<TempInfo> getNegativeTemperature(String town) {
        return getCelsiusTemperature(town)
                .filter(temp -> temp.getTemp() < 0);
    }

    public static Observable<TempInfo> getCelsiusTemperatures(String... towns) {
        return Observable.merge(Arrays.stream(towns)
                .map(Main::getCelsiusTemperature)
                .collect(Collectors.toList()));
    }
}
