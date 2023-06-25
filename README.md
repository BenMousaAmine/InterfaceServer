# InterfaceServer
meglio implentare 

    void addCar(Car car) {
        synchronized (carsList) {
            carsList.add(car);
        }
    }

    void removeCar(Car car) {
        synchronized (carsList) {
            carsList.remove(car);
        }
    }


