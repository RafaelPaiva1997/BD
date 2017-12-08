package rmi;

import database.DatabaseHandler;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.*;

public class RMIServer {

    private static Connection connection;

    public static void main(String[] args) {
        if (args.length == 1 || args.length == 2) {
            try {
                Registry registry = LocateRegistry.createRegistry(Integer.parseInt(args[0]));

                DatabaseHandler databaseHandler = new DatabaseHandler(
                        "jdbc:mysql://127.0.0.1:3306",
                        "BD",
                        "root",
                        "rafael");

                if (databaseHandler.register()) {
                    if (databaseHandler.connect()) {
                        if (args.length == 2 && args[1].equals("-r")) databaseHandler.reset();
                        RMI rmi = new RMI(databaseHandler);
                        if (rmi.put(registry))
                            System.out.println("RMI está disponível!");
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
