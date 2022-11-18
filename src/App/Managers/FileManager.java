package App.Managers;

import java.io.*;
import java.util.Optional;
import java.util.Scanner;
/**
 *  < MonoStatic Class for handling Files
 */
public class FileManager {

    /**
     *  < Retorna uma instância de FileReader, opcionalmente.
     * 
     * @param path
     * @return Optional<FileReader>
     */
    public static Optional<FileReader> read(final String path) 
    {
        File file = new File(path);
        if (file.exists()) {
            try 
            {
                FileReader reader = new FileReader(new File(path));
                return Optional.of(reader);
            }
            catch (FileNotFoundException exp) {
                System.out.println(exp.getMessage());
            }
        }
        else {
            System.out.println("FileHandler: File Dont Exists");
        }

        return Optional.empty();
    }

    /**
     *   < Escreve o conteúdo no arquivo especificado.
     * 
     * @param file
     * @param content
     * @return boolean indicando se a escrita foi bem sucedida ou não
     */
    public static boolean write(final String path, boolean append, final String content)
    {
        try (FileWriter writter = new FileWriter(new File(path), append))
        {
            writter.write(content);
            return true;
        }
        catch (IOException exp) {
            System.out.println(exp.getMessage());
            return false;
        }
    }

    private FileManager() 
    {
        //nothing
    }

}
