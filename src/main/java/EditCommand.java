public class EditCommand {


    public static class DeleteCommand extends EditCommand {
        public int toDeletePos;

        public DeleteCommand() {

        }

        public DeleteCommand(int toDeletePos) {
            this.toDeletePos = toDeletePos;
        }

        public String toString() {
            return toDeletePos + "D";
        }
    }

    public static class InsertCommand extends EditCommand {
        public int insertPosition;
        public Object insertObject;

        public InsertCommand() {

        }

        public InsertCommand(int insertPosition, Object insertObject) {
            this.insertPosition = insertPosition;
            this.insertObject = insertObject;
        }

        public String toString() {
            return insertPosition + "I" + insertObject.toString();
        }
    }
}
