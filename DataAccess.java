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
                String[] parts = line.split("\\|");
                int level = Integer.parseInt(parts[1]);
                if(parts[0].equals("WORD")) {
                    challenges.add(new SecretWord(parts[2], parts[3], level));
                } else if (parts[0].equals("PHRASE")) {
                    challenges.add(new SecretPhrase(parts[2], parts[3], level));
                }
            }
        } catch (IOException e) {
        e.printStackTrace(); 
        }   
        return challenges;
    }
}
