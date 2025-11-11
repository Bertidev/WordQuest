import java.util.Set;
import java.util.HashSet;
import java.util.Random;

//calcula a pontuacao, tempoe verifica vitoria/derrota

public class GameManager {

    //ATRIBUTOS

    private Challenge currentChallenge;

    //uso stringbuilder para modificar a string eficientemente, sem alocar nova memoria a cada alteracao
    //ao tentar alterar uma string, o java cria uma string nova com a alteracao, e depois junta as duas numa terceira 
    //string nova e deleta as duas primeiras, o que é muito ineficiente
    private StringBuilder displayedWord;

    //usando o set porque ele nao permite duplicatas de maneira automatizada
    //armazena as tentativas de letras do usuario
    private Set<Character> guessedLetters;

    private int score;
    private int hits;
    private int errors;
    private int remainingAttempts;

    //controle de tempo
    private long startTimeMillis; //guarda o momento que o jogo começou
    private int maxTimeInSeconds;

    //prepara e inicia uma rodada
    public void startGame(Challenge challenge) {

        //zera o placar e salva o desafio.
        this.currentChallenge = challenge;
        this.score = 0;
        this.hits = 0;
        this.errors = 0;

        //polimorfismo, java decide o tempo entre Word ou Phrase dependendo do objeto
        this.remainingAttempts = this.currentChallenge.getMaxAttempts();
        this.maxTimeInSeconds = this.currentChallenge.getMaxTimeInSeconds();

        //inicia o cronometro
        this.startTimeMillis = System.currentTimeMillis();
        this.guessedLetters = new HashSet<>();
        this.displayedWord = new StringBuilder();

        //constroi a palavra "criptografada"
        String secretText = this.currentChallenge.getChallengeText();
        for (int i = 0; i < secretText.length(); i++) {
            //se for espaco deixa espaco, se for letra substitui por _ 
            if (secretText.charAt(i) == ' ') {
                displayedWord.append(' ');
            } else {
                displayedWord.append('_');
            }
        }

        //regra para revelar letras iniciais
        int lettersToReveal = this.currentChallenge.getInitialVisibleChars();
        Random random = new Random();
        int revealedCount = 0;

        //set temporario para ter certeza de que a mesma letra nao sera sorteada mais de uma vez
        Set<Character> lettersAlreadyPicked = new HashSet<>();

        while (revealedCount < lettersToReveal) {
            int randomIndex = random.nextInt(secretText.length());
            char charToReveal = secretText.charAt(randomIndex);

            //verifica se o input é uma letra e se nao foi utilizada
            if (Character.isLetter(charToReveal) && !lettersAlreadyPicked.contains(charToReveal)) {
                revealedCount++;
                lettersAlreadyPicked.add(charToReveal);

                //revela todas as ocorrencias da letra na palavra
                for (int i = 0; i < secretText.length(); i++) {
                    if (secretText.charAt(i) == charToReveal) {
                        displayedWord.setCharAt(i, charToReveal);
                    }
                }

                //adiciona o caracter aos ja tentados para nao ter repeticao
                this.guessedLetters.add(charToReveal);
            }
        }
    }

    //processa a jogada do usuario, retorna true se for valida e false se nao
    public boolean guessLetter(char letter) {
        //se a letra ja foi tentada retorna
        if (guessedLetters.contains(letter)) {
            return false;
        }

        //salva a nova tentativa
        this.guessedLetters.add(letter);
        String secretText = this.currentChallenge.getChallengeText();

        //verifica se é erro ou acerto
        if (secretText.indexOf(letter) == -1) {
            //erro perde pontos e aumenta contador de erros
            this.score -= 5;
            this.errors++;
        } else {
            //acerto conta o hit
            this.hits++;
            //percorre a palavra para revelar a letra em TODAS as posições que ela aparece
            for (int i = 0; i < secretText.length(); i++) {
                if (secretText.charAt(i) == letter) {
                    this.displayedWord.setCharAt(i, letter);
                    this.score += 10; //ganha pontos por cada letra revelada
                }
            }
        }

        //Toda jogada consome uma tentativa
        this.remainingAttempts--;
        return true;
    }

    //verifica o estado do jogo
    public String checkGameState() {
        //calcula quanto tempo passou
        long elapsedTimeMillis = System.currentTimeMillis() - this.startTimeMillis;
        long maxTimeMillis = this.maxTimeInSeconds * 1000L; //converte segundo para ms

        // Ordem de verificação é importante:
        //se nao tem mais _ o jogador ganhou e retorna vitoria
        if (!displayedWord.toString().contains("_")) {
            return "WIN";
        }
        //derrota por tentativas
        else if (remainingAttempts == 0) {
            return "LOSE_ATTEMPTS";
        }
        //derrota por tempo
        else if (elapsedTimeMillis >= maxTimeMillis) {
            return "LOSE_TIME";
        }

        //se nada disso aconteceu continua
        return "PLAYING";
    }

    //GETTERS

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

    //calcula o tempo restante para mostrar na tela
    public long getRemainingTimeInSeconds() {
        long elapsedTimeMillis = System.currentTimeMillis() - this.startTimeMillis;
        long maxTimeMillis = this.maxTimeInSeconds * 1000L;
        long remainingMillis = maxTimeMillis - elapsedTimeMillis;

        //usando o maximo garante que nao vai mostrar tempo negativo
        return Math.max(0, remainingMillis / 1000);
    }
}