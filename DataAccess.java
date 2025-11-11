import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;

//classe responsável por acessar os dados do txt
public class DataAccess {

    //carrega a lista de desafios a partir do caminho do arquivo
    public List<Challenge> loadChallengesFromFile (String filePath) throws IOException {
        
        //lista onde vamos guardar as palavras/frases carregadas
        List<Challenge> challenges = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            
            String line;
            //loop que le o arquivo linha por linha ate chegar no final (null)
            while ((line = reader.readLine()) != null) {
                
                //pula a linha se ela estiver vazia ou for um comentario
                if (line.isBlank() || line.startsWith("#")) {
                    continue; 
                }   
                
                //faz a analise (parsing) da linha
                //usa "\\|" porque pipeline é um caracter especial
                String[] parts = line.split("\\|");
                
                int level = Integer.parseInt(parts[1]);
                
                //decide qual tipo de objeto criar baseado na primeira coluna do txt
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