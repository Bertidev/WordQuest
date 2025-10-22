import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;

public class DataAccess {
    public List<Challenge> loadChallengesFromFile (String filePath) throws IOException{
        List<Challenge> challenges = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank() || line.startsWith("#")) {
                    continue; 
                }   
            }
        } catch (IOException e) {
        e.printStackTrace(); 
        }   
    }
}
