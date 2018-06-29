package demo.ooad;

/**
 * Stateful VS Stateless Design
 */
public final class Logger {
    private MessageFilter filter; //final 4 var = const
    private SaverFactory saverFactory;

    /**
     * DI:
     * - constructor
     * - setter
     * - field
     */
    public Logger(MessageFilter filter, SaverFactory saverFactory) {
        this.filter = filter;
        this.saverFactory = saverFactory;
    }

    public void setFilter(final MessageFilter filter) {
        this.filter = filter;
    }

    /**
     * Public API
     */
    public final void log(String message, SeverityLevel severity) {
        if (filter.filter(message, severity)) {
            MessageSaver saver = saverFactory.createSaverWithState();
            saver.setMessage(message);
            saver.save();
        }
    }
}

class App {
    final int varI;

    App() {
        varI = 0;
    }

    App(int p) {
        varI = 1;
    }

    /**
     * @param p - final
     */
    public void log(final int p) {

    }

    public void log(char p) {

    }

    public static void main(final String[] args) {
        new Logger(
            new MoonPhaseMessageFilter(),
            new StubSaverFactory()
        );
    }

}
