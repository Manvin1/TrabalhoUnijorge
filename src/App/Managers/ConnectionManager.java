package App.Managers;

import App.Utils.*;

import java.sql.*;
import java.io.*;
import java.util.Optional;
import java.util.Scanner;

public class ConnectionManager {

    /**
     *  < Retorna uma nova instância de conexão. O caller é responsável pelo seu gerenciamento.
     * @return Connection
     */
    static public Optional<Connection> getConnection() 
    {
       Optional<Triple<String, String, String>> propOpt = getProperties("properties/properties.txt");
        if (!propOpt.isPresent()) 
        {
            return Optional.empty();
        }

        try {
            Triple<String, String, String> prop = propOpt.get();
            return Optional.of(DriverManager.getConnection(prop.first, prop.second, prop.third));
        } catch (Exception exp) {
            
            System.out.println("getConnection: Não foi possível obter uma conexão >>" + exp.getMessage());
            return Optional.empty();
        }
    }

    /**
     *  < Retorna as propriedades para obter uma conexão a partir do arquivo especificado.
     * 
     * @param path
     * @return Uma tripla que é composta da url, do login e da senha lida.
     */
    static public Optional<Triple<String, String, String>> getProperties(final String path) 
    {
        Optional<FileReader> readerOp = FileManager.read(path);

        if (!readerOp.isPresent()) {
            return Optional.empty();
        }

        try (Scanner fileReader = new Scanner(readerOp.get())) 
        {
            String line = null;
            String url;
            String user;
            String pwd;
            int i;

            if (fileReader.hasNext("path:")) {
                line = fileReader.nextLine();
                i = line.indexOf(":") + 1;
                url = line.substring(i).replace(" ", "");
            } else {
                System.out.println("Path dont found on file");
                return Optional.empty();
            }

            if (fileReader.hasNext("user:")) {
                line = fileReader.nextLine();
                i = line.indexOf(":") + 1;
                user = line.substring(i).replace(" ", "");
            } else {
                System.out.println("Pwd dont found on file");
                return Optional.empty();
            }

            if (fileReader.hasNext("pwd:")) {
                line = fileReader.nextLine();
                i = line.indexOf(":") + 1;
                pwd = line.substring(i).replace(" ", "");
            } else {
                System.out.println("User dont found on file");
                return Optional.empty();
            }

            return Optional.of(new Triple<String, String, String>(url, user, pwd));
        }
        
    }

    static public boolean ping()
    {
        Optional<Connection> connectionOpt  = getConnection();
        if (connectionOpt.isPresent())
        {  
            try (Connection connection = connectionOpt.get())
            {
                return connection.isValid(0);
            }
            catch (SQLException exp)
            {
                System.out.println("Ping Exception: SQLException");
            }

            catch (Exception e)
            {
                System.out.println("Ping Exception: Exception");
            }
        }
        System.out.println("PingValue: False");
        return false;
    }
}
