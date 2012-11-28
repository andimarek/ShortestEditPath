import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        List<String> listOne = Arrays.asList("a", "b", "c", "a", "b", "b", "a");
        List<String> listTwo = Arrays.asList("c", "b", "a", "b", "a", "c");
        SesFinder sesFinder = new SesFinder(listOne, listTwo);
        List<EditCommand> ses = sesFinder.findSes();
        System.out.println(ses);
    }
}
