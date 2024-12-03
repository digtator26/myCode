import net.logicsquad.nanocaptcha.content.AbstractContentProducer;

public class MyContentProducer extends AbstractContentProducer {

    private static final char[] DEFAULT_CHARS = new char[]
            {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public MyContentProducer() {
        this(5);
    }

    public MyContentProducer(int length) {
        super(length, DEFAULT_CHARS);
    }

}
