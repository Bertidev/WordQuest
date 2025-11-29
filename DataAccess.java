// classe responsavel por carregar desafios (palavras/frases) de um arquivo .txt

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;

// classe responsavel por acessar os dados do txt
public class DataAccess {

    // carrega a lista de desafios a partir do caminho do arquivo
    // retorna uma lista de Challenge (SecretWord ou SecretPhrase)
    public List<Challenge> loadChallengesFromFile (String filePath) throws IOException {
        
        // lista onde vamos guardar as palavras/frases carregadas
        List<Challenge> challenges = new ArrayList<>();

        // try-catch para garantir fechamento do reader
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            
            String line;
            // loop que le o arquivo linha por linha até chegar no final (null)
            while ((line = reader.readLine()) != null) {
                
                // pula a linha se ela estiver vazia ou for um comentario (começa com #)
                if (line.isBlank() || line.startsWith("#")) {
                    continue; 
                }   
                
                // faz o parsing da linha:
                // formato esperado: TIPO|NIVEL|TEXTO|DICA
                // usa "\\|" porque '|' e metacaractere em expressões regulares
                String[] parts = line.split("\\|");
                
                // converte a coluna de nivel para inteiro
                int level = Integer.parseInt(parts[1]);
                
                // decide qual tipo de objeto criar baseado na primeira coluna
                if(parts[0].equals("WORD")) {
                    // cria SecretWord com texto (parts[2]) e dica (parts[3])
                    challenges.add(new SecretWord(parts[2], parts[3], level));
                } else if (parts[0].equals("PHRASE")) {
                    // cria SecretPhrase com texto (parts[2]) e dica (parts[3])
                    challenges.add(new SecretPhrase(parts[2], parts[3], level));
                }
            }
        } catch (IOException e) {
            // imprime erro de I/O — método também declara throws IOException caso queira tratar acima
            e.printStackTrace(); 
        }   
        
        // retorna a lista carregada (pode estar vazia se ocorreram erros)
        return challenges;
    }
}