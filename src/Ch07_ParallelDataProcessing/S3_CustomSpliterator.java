package Ch07_ParallelDataProcessing;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class S3_CustomSpliterator {
    static int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) lastSpace = true;
            else {
                if (lastSpace) counter++;
                lastSpace = false;
            }
        }
        return counter;
    }

    public static void main(String[] args) {
        String text = "abcd    \tefg han Dae young!! abcd    \tefg han Dae young!! abcd    \tefg han Dae young!! abcd    \tefg han Dae young!!";

        System.out.println(countWordsIteratively(text));

        // word count with functional way
        S3_CustomSpliterator proc = new S3_CustomSpliterator();
        Stream<Character> stream = IntStream.range(0, text.length())
                .mapToObj(text::charAt);

        WordCounter wc = stream.reduce(proc.new WordCounter(0, true),
                WordCounter::accumulate, WordCounter::combine);

        System.out.println(wc.getCounter());

        // word count in parallel
        // first try with parallel()
        // => very wrong result because of separate position
        stream = IntStream.range(0, text.length()).mapToObj(text::charAt);
        WordCounter wc2 = stream.parallel()
                .reduce(proc.new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);

        System.out.println(wc2.getCounter());

        // word count in parallel with Spliterator
        Spliterator<Character> spliterator = proc.new WordCounterSpliterator(text);
        stream = StreamSupport.stream(spliterator, true);
        WordCounter wc3 = stream.parallel()
                .reduce(proc.new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        System.out.println(wc3.getCounter());
    }

    class WordCounter {
        private final int counter;
        private final boolean lastSpace;

        public WordCounter(int counter, boolean lastSpace) {
            this.counter = counter;
            this.lastSpace = lastSpace;
        }

        public WordCounter accumulate(Character c) {
            if (Character.isWhitespace(c)) {
                return lastSpace ? this : new WordCounter(counter, true);
            } else {
                return lastSpace ? new WordCounter(counter + 1, false) : this;
            }
        }

        public WordCounter combine(WordCounter wordCounter) {
            return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
        }

        public int getCounter() {
            return counter;
        }
    }

    class WordCounterSpliterator implements Spliterator<Character> {
        private final String string;
        private int currentChar = 0;

        public WordCounterSpliterator(String string) {
            this.string = string;
        }

        @Override
        public boolean tryAdvance(Consumer<? super Character> action) {
            action.accept(string.charAt(currentChar++));
            return currentChar < string.length();
        }

        @Override
        public Spliterator<Character> trySplit() {
            int currentSize = string.length() - currentChar;
            if (currentSize < 10) return null;

            for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
                if (Character.isWhitespace(string.charAt(splitPos))) {
                    Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
                    currentChar = splitPos;
                    return spliterator;
                }
            }
            return null;
        }

        @Override
        public long estimateSize() {
            return string.length() - currentChar;
        }

        @Override
        public int characteristics() {
            return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
        }
    }
}
