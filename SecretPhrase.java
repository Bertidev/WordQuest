// subclasse de Challenge para desafios do tipo frase

import java.util.Set;
import java.util.HashSet;

public class SecretPhrase extends Challenge {
    
    // usa o construtor da superclasse para inicializar texto, dica e nível
    public SecretPhrase(String challengeText, String hint, int level) {
        super(challengeText, hint, level);
    }

    // determina o numero maximo de tentativas permitido para a frase
    @Override
    public int getMaxAttempts() {
        // junta todas as letras unicas presentes na frase (ignora espaços e pontuação)
        Set<Character> uniqueLetters = new HashSet<>();
        for (char c : this.challengeText.toCharArray()) {
            if (Character.isLetter(c)) {
                uniqueLetters.add(c);
            }
        }

        // fator fixo de tentativas extras dependendo do nível de dificuldade
        int allowedMisses;
        if (level == 1) {
            allowedMisses = 9;  // facil -> mais tentativas
        } else if (level == 2) {
            allowedMisses = 7;  // medio -> tentativas moderadas
        } else {
            allowedMisses = 4;  // dificil -> menos tentativas
        }
        
        // total = letras unicas + tentativas extras definidas pelo nivel
        return uniqueLetters.size() + allowedMisses;
    }

    // tempo maximo em segundos para resolver a frase, conforme dificuldade
    @Override
    public int getMaxTimeInSeconds() {
        if (level == 1) {
            return 100; // facil
        } else if (level == 2) {
            return 80;  // medio
        } else {
            return 60;  // dificil
        }
    }

    // numero de caracteres visiveis inicialmente 
    @Override
    public int getInitialVisibleChars() {
        return 2; // sempre comeca mostrando duas letras
    }
}