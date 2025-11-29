// subclasse de Challenge para desafio de palavra única

import java.util.Set;
import java.util.HashSet;

public class SecretWord extends Challenge {

    // usa o construtor da superclasse para inicializar texto, dica e nivel
    public SecretWord(String challengeText, String hint, int level) {
        super(challengeText, hint, level);
    }
    
    // numero maximo de tentativas permitido para a palavra
    // calcula letras unicas + fator fixo conforme dificuldade
    @Override
    public int getMaxAttempts() {
        // o uso de HashSet garante que nao haja repeticao de caracteres
        Set<Character> uniqueLetters = new HashSet<>();
        // percorre cada caractere da palavra ou frase do desafio
        for (char c : this.challengeText.toCharArray()) {
            // verifica se o caractere e uma letra (ignora espacos, acentos)
            if (Character.isLetter(c)) { 
                uniqueLetters.add(c);
            }
        }

        // fator de tentativas extras por nivel
        int allowedMisses;
        if (level == 1) {
            allowedMisses = 7; // facil -> mais tentativas
        } else if (level == 2) {
            allowedMisses = 5; // medio -> tentativas moderadas
        } else {
            allowedMisses = 3; // dificil -> menos tentativas
        }
        
        return uniqueLetters.size() + allowedMisses;
    }

    // tempo maximo em segundos para resolver a palavra, por nivel
    @Override
    public int getMaxTimeInSeconds() {
        if (level == 1) {
            return 90;  // facil
        } else if (level == 2) {
            return 70;  // medio
        } else {
            return 45;  // dificil
        }
    }

    // quantos caracteres ficam visíveis no inicio do jogo
    @Override
    public int getInitialVisibleChars() {
        return 1; // começa mostrando uma letra
    }
}