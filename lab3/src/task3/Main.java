package task3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;
import static java.util.Arrays.asList;

public class Main {
    public static void main(String[] args) {
        TypeCreate typeCreate = new TypeCreate("Creating patients", "exp", 15.0);
        typeCreate.setTypeChances(asList(0.5, 0.1, 0.4));

        TypeProcess registrationProcess = new TypeProcess("Registration", "exp", 0, 2, Integer.MAX_VALUE);
        registrationProcess.setDelayForTypes(asList(15.0, 40.0, 30.0));

        Process patientWardProcess = new Process("Going to wards", "unif", 3, 3, Integer.MAX_VALUE);
        patientWardProcess.setDelayDev(8);

        Process patientLaboratoryProcess = new Process("Going to Laboratory", "unif", 2, 1000, Integer.MAX_VALUE);
        patientLaboratoryProcess.setDelayDev(5);

        Process laboratoryRegistrationProcess = new Process("Registration in laboratory", "erl", 4.5, 1, Integer.MAX_VALUE);
        laboratoryRegistrationProcess.setDelayDev(3);

        TypeProcess analyzeInLaboratoryProcess = new TypeProcess("Analyze in laboratory", "erl", 4.0, 2, Integer.MAX_VALUE);
        analyzeInLaboratoryProcess.setDelayDev(2);
        analyzeInLaboratoryProcess.setDelayForTypes(asList(4.0, 4.0, 4.0));

        ChangingProcess returningToRegistrationProcess = new ChangingProcess("Returning to registration from laboratory", "unif", 2, 1000, Integer.MAX_VALUE);
        returningToRegistrationProcess.setDelayDev(5);

        Process despose = new Process("Despose", "exp", 0.0, 1000, Integer.MAX_VALUE);

        typeCreate.setNextElement(registrationProcess);
        registrationProcess.setNextElementsForTypes(asList(patientWardProcess, patientLaboratoryProcess, patientLaboratoryProcess));
        patientWardProcess.setNextElement(despose);
        patientLaboratoryProcess.setNextElement(laboratoryRegistrationProcess);
        laboratoryRegistrationProcess.setNextElement(analyzeInLaboratoryProcess);
        analyzeInLaboratoryProcess.setNextElementsForTypes(asList(despose, returningToRegistrationProcess, despose));
        returningToRegistrationProcess.setNextElement(registrationProcess);

        ArrayList<Element> list = new ArrayList<>(asList(
                typeCreate,
                registrationProcess,
                patientWardProcess,
                patientLaboratoryProcess,
                laboratoryRegistrationProcess,
                analyzeInLaboratoryProcess,
                returningToRegistrationProcess,
                despose
        ));
        Model model = new Model(list);
        model.simulate(1000.0);
    }
}