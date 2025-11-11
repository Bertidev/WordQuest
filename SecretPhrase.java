//subclasse que herda de Challenge, serve para desafio de frase

import java.util.Set;
import java.util.HashSet;

public class SecretPhrase extends Challenge{
    
    //usa o construtor da superclasse
    public SecretPhrase(String challengeText, String hint, int level) {
        super(challengeText, hint, level);
    }

    //especificando o numero maximo de tentativas, fazendo sobrescrita da classe pai, ou seja polimorfismo
    @Override
    public int getMaxAttempts() {
        Set<Character> uniqueLetters = new HashSet<>();
        for (char c : this.challengeText.toCharArray()) {
            if (Character.isLetter(c)) {
                uniqueLetters.add(c);
            }
        }
        //o numero maximo de tentativas depende da quantidade de letras unicas 
        //esse valor é somado com um fator fixo de acordo coma  dificuldade
        int allowedMisses;
        if (level == 1) {
            allowedMisses = 9; 
        } else if (level == 2) {
            allowedMisses = 7; 
        } else {
            allowedMisses = 4; 
        }
        
        return uniqueLetters.size() + allowedMisses;
    }

    @Override
    public int getMaxTimeInSeconds() {
        //o tempo limite é definido de acordo com a dificuldade
        if (level == 1) {
            return 100;
        } else if (level == 2) {
            return 80;
        } else {
            return 60;
        }
    }

    @Override
    public int getInitialVisibleChars() {
        //o numero de letras unicas disponiveis no começo do jogo é fixo
        return 2;
    }
}
