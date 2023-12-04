package task2;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Main {
    public static void main(String[] args) {
        Create creator = new Create("CREATOR", "exp", .5);
        PriorityProcess priorityProcess = new PriorityProcess("PRIORITY_PROCESS", "exp", .0, 1, 1000);
        ConditionProcess windowProcess1 = new ConditionProcess("WINDOW_PROCESS1", "exp", .3, 1, 3);
        ConditionProcess windowProcess2 = new ConditionProcess("WINDOW_PROCESS2", "exp", .3, 1, 3);
        Process desposeProcess = new Process("DESPOSER", "exp", .0, 1, 1000);
        creator.setTnext(.1);
        windowProcess1.setStateOfProcesses(1);
        windowProcess2.setStateOfProcesses(1);
        windowProcess1.setTnextOfProcesses(FunRand.Norm(1, .3));
        windowProcess2.setTnextOfProcesses(FunRand.Norm(1, .3));
        windowProcess1.setQueue(2);
        windowProcess2.setQueue(2);
        creator.setNextElement(priorityProcess);
        priorityProcess.setNextElements(List.of(windowProcess1, windowProcess2));
        priorityProcess.setNextElementsPriority(List.of(1, 2));
        windowProcess1.setNextElements(List.of(desposeProcess, windowProcess2));
        windowProcess1.setNextElementsConditions(List.of(0, 2));
        windowProcess2.setNextElements(List.of(desposeProcess, windowProcess1));
        windowProcess2.setNextElementsConditions(List.of(0, 2));
        ArrayList<Element> list = new ArrayList<>(asList(creator, priorityProcess, windowProcess1, windowProcess2, desposeProcess));
        Model model = new Model(list);
        model.simulate(1000.0);
    }
}