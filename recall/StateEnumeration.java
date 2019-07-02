import java.util.ArrayList;
import java.util.List;

public class StateEnumeration {
    public static void main(String[] args) {
        List<String> stateSolution = new ArrayList<>();
        for (int var : new int[10]) {
            System.out.print(var);
        }
        state("", stateSolution);
        System.out.println(stateSolution.size());
        for (String var : stateSolution) {
            System.out.println(var);
        }

    }


    public static void state(String s, List<String> stateSolution) {
        if(s.split(" ").length >= 6) {
            stateSolution.add(s);
            return;
        }
        state(s+"1 ", stateSolution);
        state(s+"0 ", stateSolution);
    }

}