package c24w.x;

/**
 * Created by Chris on 08/03/14.
 */
public class CrosswordParser {

    public void parse(byte[] data, Callback callback) {

       // callback.success(new int[]{0, 1, 0, 0, 1});
    }

    interface Callback {
        void success(Crossword crossword);
    }
}
