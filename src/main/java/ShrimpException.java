public class ShrimpException extends Exception {

    //fields used by task, formatted
    private static final String description = AnsiCode.ITALIC + "description" + AnsiCode.RESET;
    private static final String by = AnsiCode.ITALIC + "by" + AnsiCode.RESET;
    private static final String from = AnsiCode.ITALIC + "from" + AnsiCode.RESET;
    private static final String to = AnsiCode.ITALIC + "to" + AnsiCode.RESET;

    ShrimpException() {};

    ShrimpException(String errorMessage) {
        super(errorMessage);
    }

    public static class InvalidCommandException extends ShrimpException {
        private static final String errorMessage = "Oh nyoo~ I don't recognise that, can you try again?";
        InvalidCommandException() {
            super(errorMessage);
        };
    }

    public static class MissingArgumentException extends ShrimpException {
        private static final String errorMessage_toDo = "Oh nyoo~ I need a " + description + "to make a TODO...";
        private static final String errorMessage_mark = "Oh nyoo~ You didn't indicate which task to mark...";
        private static final String errorMessage_unmark = "Oh nyoo~ You didn't indicate which task to unmark...";
        private static final String errorMessage_deadline = "Oh nyoo~ I need a " + description + " and a " + by +
                " to make a DEADLINE...";
        private static final String errorMessage_event = "Oh nyoo~ I need a " + description + ", a " + from +
                " and a " + to + " to make a DEADLINE...";
        private static final String errorMessage_default = "Oh nyoo~ There seems to an issue somewhere! :<";

        MissingArgumentException(CommandParser.CommandType command) {
            super(switchErrorMessage(command));
        }

        private static String switchErrorMessage(CommandParser.CommandType command) {
            return switch (command) {
                case ADD -> errorMessage_toDo;
                case MARK -> errorMessage_mark;
                case UNMARK -> errorMessage_unmark;
                case DEADLINE -> errorMessage_deadline;
                case EVENT -> errorMessage_event;
                default -> errorMessage_default;
            };
        }
    }

    public static class InvalidArgumentException extends ShrimpException {
        private static final String errorMessage = "Oh nyoo~ Your values seems wrong, maybe try again?";
        InvalidArgumentException() {
            super(errorMessage);
        };
    }

    public static class EmptyArrayException extends ShrimpException {
        private static final String errorMessage = "Oh nyoo~ There's no tasks for me to show...";
        EmptyArrayException() {
            super(errorMessage);
        };
    }
}
