package org.example;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

// questa e una singelton l'ho presa da https://www.baeldung.com/java-singleton
//con un po di modifike
public final class WareHouse {
    Gson gson = new Gson();
    private static WareHouse INSTANCE;

    public List<Car> getCarList() {
        return carsList;
    }

    List<Car> carsList = new ArrayList<>();

    private WareHouse() {
        //query if db
        this.carsList.add(new Car(11,"bmw", 3594.9 , 2));
        this.carsList.add(new Car(12,"audi", 38346.9,4));
        this.carsList.add(new Car(13,"ferrari", 130000.0,3));
    }

    public static WareHouse getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new org.example.WareHouse();
        }

        return INSTANCE;
    }

    void addCar(Car car) {
        carsList.add(car);
    }

    void removeCar(Car product) {
        carsList.remove(product);
    }

    int numberCar() {
        return carsList.size();
    }


    List<Car> allSorted() {
        carsList.sort((o1, o2) -> {
            return o1.getBrand().compareTo(o2.getBrand());
        });

        return carsList;
    }

    List<Car> mostExpensiveCar(){
        carsList.sort((o1, o2) -> {
            if (o1.getCost()>o2.getCost())
                return 1;
            if (o1.getCost()<o2.getCost())
                return -1;
            return 0;
        });
        return carsList ;
    }


    //questo metodo premde carlist e la trasforma in un jsonformat
    String ToJSon() {

        String jsonStr = gson.toJson(carsList);
        return jsonStr;
    }
    String moreExptoJson (){
        String jsonExpensive = gson.toJson(mostExpensiveCar());
        return jsonExpensive;
    }
    String allSortedJson(){
        String jsonSorted = gson.toJson(allSorted());
        return jsonSorted;
    }



}
