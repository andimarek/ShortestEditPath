import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SesFinder {

    private final List<?> listOne;
    private final List<?> listTwo;

    private PathElement[] v;
    private int listTwoSize;
    private int listOneSize;
    private int maxSesLength;
    private final PathElement dummyPathElement;


    public SesFinder(List<?> listOne, List<?> listTwo) {
        this.listOne = listOne;
        this.listTwo = listTwo;
        listOneSize = listOne.size();
        listTwoSize = listTwo.size();

        maxSesLength = listTwoSize + listOneSize;
        v = new PathElement[2 * maxSesLength + 1];

        dummyPathElement = new PathElement(0, -1, null);
        v[1] = dummyPathElement;
    }


    public PathElement getEditPath() {
        outer:
        for (int sesLength = 0; sesLength <= maxSesLength; sesLength++) {
            for (int diagonalIndex = -sesLength; diagonalIndex <= sesLength; diagonalIndex += 2) {

                int startFromX;
                int startedFromDiagonalIndex;
                PathElement startFromPathElement;
                if (diagonalIndex == -sesLength || (diagonalIndex < sesLength && getPointOnDiagonal(diagonalIndex - 1) < getPointOnDiagonal(diagonalIndex + 1))) {
                    startFromX = getPointOnDiagonal(diagonalIndex + 1);
                    startedFromDiagonalIndex = diagonalIndex + 1;
                    startFromPathElement = getPathElement(startedFromDiagonalIndex);
                } else {
                    startFromX = getPointOnDiagonal(diagonalIndex - 1) + 1;
                    startedFromDiagonalIndex = diagonalIndex - 1;
                    startFromPathElement = getPathElement(startedFromDiagonalIndex);
                }

                int startFromY = startFromX - diagonalIndex;

                PathElement nextElement = new PathElement(startFromX, startFromY, startFromPathElement);

                while (moveOnDiagonal(startFromX, startFromY)) {
                    startFromX++;
                    startFromY++;
                    nextElement = new PathElement(startFromX, startFromY, nextElement);
                }
                int realIndex = convertIndex(diagonalIndex);
                v[realIndex] = nextElement;

                if (startFromX >= listOneSize && startFromY >= listTwoSize) {
                    return nextElement;
                }
            }
        }
        throw new RuntimeException("shit!");
    }

    public List<EditCommand> findSes() {
        List<EditCommand> editCommands = new ArrayList<EditCommand>();
        PathElement editPath = getEditPath();
        while (editPath != null && editPath.predecessor != null && editPath.predecessor != dummyPathElement) {
            if (editPath.isDiagonal()) {
                editPath = editPath.predecessor;
                continue;
            }

            if (editPath.isHorizontal()) {
                EditCommand.DeleteCommand deleteCommand = new EditCommand.DeleteCommand(editPath.x);
                editCommands.add(deleteCommand);
            } else {
                EditCommand.InsertCommand insertCommand = new EditCommand.InsertCommand();
                insertCommand.insertPosition = editPath.x;
                insertCommand.insertObject = fromListTwo(editPath.y);
                editCommands.add(insertCommand);
            }
            editPath = editPath.predecessor;
        }
        Collections.reverse(editCommands);
        return editCommands;
    }


    private boolean moveOnDiagonal(int startFromX, int startFromY) {
        return startFromX < listOneSize && startFromY < listTwoSize && fromListOne(startFromX + 1).equals(fromListTwo(startFromY + 1));
    }


    private PathElement getPathElement(int diagonalIndex) {
        return v[convertIndex(diagonalIndex)];
    }

    private int getPointOnDiagonal(int diagonalIndex) {
        if (v[convertIndex(diagonalIndex)] == null) return 0;
        return v[convertIndex(diagonalIndex)].x;
    }

    private int convertIndex(int index) {
        if (index >= 0) return index;
        return -index + maxSesLength;
    }

    private Object fromListOne(int index) {
        return listOne.get(index - 1);
    }

    private Object fromListTwo(int index) {
        return listTwo.get(index - 1);
    }

}


