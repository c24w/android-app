package c24w.x;

/**
 * Created by Chris on 08/03/14.
 */
public class CrosswordParser {

    public void parse(byte[] data, Callback callback) {
        callback.success(new Crossword());
    }

    interface Callback {
        void success(Crossword crossword);
    }
}