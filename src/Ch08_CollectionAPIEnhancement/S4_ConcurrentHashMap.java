package Ch08_CollectionAPIEnhancement;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class S4_ConcurrentHashMap {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
        map.put("a", 4L);
        map.put("b", 8L);
        map.put("c", 9L);

        long parallelismThreshold = 1;
        Optional<Long> maxValue = Optional.ofNullable(
                map.reduceValuesToLong(parallelismThreshold, (Long x) -> x.longValue(), 0L, Long::max)
        );
        System.out.println(maxValue.get());
    }
}
