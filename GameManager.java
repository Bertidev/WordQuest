// gerencia o estado do jogo: inicia rodada, processa palpites, pontuacao e verificacoes de vitoria/derrota

import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public class GameManager {

    // ATRIBUTOS

    private Challenge currentChallenge;      // desafio atual (palavra ou frase)

    // armazena versao parcialmente revelada da palavra/frase
    // é um string builder porque strings em java sao imutaveis
    // entao cada mudanca criaria um novo objeto
    // o jogo precisa modificar muitas posicoes 
    // o StringBuilder permite alterar caracteres diretamente sem recriar a string
    private StringBuilder displayedWord;     // versao "criptografada" mostrada ao jogador

    private Set<Character> guessedLetters;   // letras ja tentadas pelo jogador (sem duplicatas)
    private int score;                       // pontuacao atual
    private int hits;                        // acertos (letras corretas encontradas)
    private int errors;                      // erros (letras incorretas)
    private int remainingAttempts;           // tentativas restantes

    // controle de tempo
    private long startTimeMillis;            // quando a rodada começou (ms)
    private int maxTimeInSeconds;            // tempo maximo permitido (segundos)

    // PREPARA E INICIA UMA RODADA
    public void startGame(Challenge challenge) {

        // zera estado e guarda o desafio
        this.currentChallenge = challenge;
        this.score = 0;
        this.hits = 0;
        this.errors = 0;

        // usa polimorfismo para obter regras dependendo do tipo do desafio
        this.remainingAttempts = this.currentChallenge.getMaxAttempts();
        this.maxTimeInSeconds = this.currentChallenge.getMaxTimeInSeconds();

        // inicia cronometro e estruturas de apoio
        this.startTimeMillis = System.currentTimeMillis();
        this.guessedLetters = new HashSet<>();
        this.displayedWord = new StringBuilder();

        // constroi a versao inicial (substitui letras por '_', preserva espaços)
        String secretText = this.currentChallenge.getChallengeText();
        for (int i = 0; i < secretText.length(); i++) {
            if (secretText.charAt(i) == ' ') {
                displayedWord.append(' ');
            } else {
                displayedWord.append('_');
            }
        }

        // revela algumas letras iniciais conforme regra do desafio
        int lettersToReveal = this.currentChallenge.getInitialVisibleChars();
        Random random = new Random();
        int revealedCount = 0;

        // evita revelar a mesma letra mais de uma vez nesse sorteio inicial
        Set<Character> lettersAlreadyPicked = new HashSet<>();

        while (revealedCount < lettersToReveal) {
            int randomIndex = random.nextInt(secretText.length());
            char charToReveal = secretText.charAt(randomIndex);

            // so revela se for letra e ainda não foi escolhida
            if (Character.isLetter(charToReveal) && !lettersAlreadyPicked.contains(charToReveal)) {
                revealedCount++;
                lettersAlreadyPicked.add(charToReveal);

                // revela todas as ocorrencias da letra
                for (int i = 0; i < secretText.length(); i++) {
                    if (secretText.charAt(i) == charToReveal) {
                        displayedWord.setCharAt(i, charToReveal);
                    }
                }

                // marca como ja tentada para evitar duplicacao
                this.guessedLetters.add(charToReveal);
            }
        }
    }

    // PROCESSA UM PALPITE DO USUARIO (letra)
    // retorna false se a letra ja foi tentada (palpite invalido)
    public boolean guessLetter(char letter) {
        if (guessedLetters.contains(letter)) {
            return false;
        }

        // registra a tentativa
        this.guessedLetters.add(letter);
        String secretText = this.currentChallenge.getChallengeText();

        // verifica se a letra esta no texto secreto
        if (secretText.indexOf(letter) == -1) {
            // letra nao encontrada -> penaliza
            this.score -= 5;
            this.errors++;
        } else {
            // letra encontrada -> revela todas as ocorrencias e pontua
            this.hits++;
            for (int i = 0; i < secretText.length(); i++) {
                if (secretText.charAt(i) == letter) {
                    this.displayedWord.setCharAt(i, letter);
                    this.score += 10; // pontos por cada letra revelada
                }
            }
        }

        // cada palpite consome uma tentativa
        this.remainingAttempts--;
        return true;
    }

    // VERIFICA O ESTADO ATUAL DO JOGO
    // retorna "WIN", "LOSE_ATTEMPTS", "LOSE_TIME" ou "PLAYING"
    public String checkGameState() {
        long elapsedTimeMillis = System.currentTimeMillis() - this.startTimeMillis;
        long maxTimeMillis = this.maxTimeInSeconds * 1000L; // converte segundos para ms

        // ordem importa: primeiro verifica vitoria (nenhum '_'), depois derrotas
        if (!displayedWord.toString().contains("_")) {
            return "WIN";
        } else if (remainingAttempts == 0) {
            return "LOSE_ATTEMPTS";
        } else if (elapsedTimeMillis >= maxTimeMillis) {
            return "LOSE_TIME";
        }

        return "PLAYING";
    }

    // GETTERS simples para UI e lógica externa

    public String getDisplayedWord() {
        return this.displayedWord.toString();
    }

    public int getScore() {
        return this.score;
    }

    public int getRemainingAttempts() {
        return this.remainingAttempts;
    }

    public String getHint() {
        return this.currentChallenge.getHint();
    }

    public Set<Character> getGuessedLetters() {
        return this.guessedLetters;
    }

    public int getHits() {
        return this.hits;
    }

    public int getErrors() {
        return this.errors;
    }

    // calcula tempo restante em segundos (garante nao negativo)
    public long getRemainingTimeInSeconds() {
        long elapsedTimeMillis = System.currentTimeMillis() - this.startTimeMillis;
        long maxTimeMillis = this.maxTimeInSeconds * 1000L;
        long remainingMillis = maxTimeMillis - elapsedTimeMillis;
        return Math.max(0, remainingMillis / 1000);
    }
}