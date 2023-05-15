package auxiliaryClasses;

import org.example.Employee;
import org.example.Main;
import org.example.PizzaStore;

import javax.lang.model.type.MirroredTypeException;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class EmployeeGenerator {

    private Random _rnd = new Random();

    private HashCreator _hc = new HashCreator(HashingAlgorhitms.SHA256);
    private ArrayList<String> _names;
    private ArrayList<String> _addresses;

    private  String getPathFromResources(String fileName) throws URISyntaxException {
        URL fileUrl = Main.class.getResource("/" + fileName);
        return Paths.get(fileUrl.toURI()).toString();
    }

    public EmployeeGenerator() {
        try {
            _names = preload(getPathFromResources("names.txt"));
            _addresses = preload(getPathFromResources("addresses.txt"));

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }


    } ArrayList<String> preload(String absolutePath) {
        ArrayList<String> list = new ArrayList<>();
        String path = null;
        Scanner sc = null;
        try {
            File namesFile = new File(absolutePath);
            sc = new Scanner(namesFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (sc.hasNextLine()) {
            list.add(sc.nextLine());
        }
        return list;
    }

    private String generatePhoneNumber() {
        StringBuilder sb = new StringBuilder();
        int[] digits  = new int[]{0,1,2,3,4,5,6,7,8,9};
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                sb.append(digits[_rnd.nextInt(digits.length)]);
            }
            if(i < 2)
                sb.append('-');
        }
        return sb.toString();
    }
    private Function1<ArrayList<String>,String> getRandomStringOfList = (list) -> list.get(_rnd.nextInt(0, list.size()-1));
    public  Employee getRandomEmployee() throws Exception {
        String firstName = null;
        String lastName = null;
        String address = null;
        try {
            firstName = getRandomStringOfList.func(_names);
            lastName = getRandomStringOfList.func(_names);
            address = getRandomStringOfList.func(_addresses);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

        String phoneNumber = generatePhoneNumber();
        String id = _hc.createHash(firstName, lastName, address, phoneNumber);
        return new Employee(id, firstName, lastName, address, phoneNumber);
    }
    public <T extends PizzaStore> T getPizzaStrore(Class<T> cl) {
        String phoneNumber = generatePhoneNumber();
        String id = _hc.createHash(phoneNumber, cl.getName());

        T item = null;
        try {
            Constructor<T> ctor = cl.getDeclaredConstructor(String.class, String.class);
            item = ctor.newInstance(id, phoneNumber);
        } catch (Exception e)  {
            throw new RuntimeException(e);
        }
        return item;
    }

}
