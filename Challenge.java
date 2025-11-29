// superclasse base para SecretPhrase e SecretWord

public abstract class Challenge {
    protected String challengeText; // texto do desafio (palavra ou frase)
    protected String hint;          // dica curta para ajudar o jogador
    protected int level;            // nível de dificuldade (ex: 1=facil,2=medio,3=dificil)

    // CONSTRUTOR
    // inicializa o desafio com texto, dica e nível
    public Challenge(String challengeText, String hint, int level) {
        this.challengeText = challengeText;
        this.hint = hint;
        this.level = level;
    }

    // METODOS DE ACESSO
    public String getChallengeText() {
        return this.challengeText; 
    }

    public String getHint() {
        return this.hint;        
    }

    public int getLevel() {
        return this.level;         
    }

    // REGRAS ESPECIFICAS — implementadas nas subclasses

    // quantas tentativas maximas são permitidas
    public abstract int getMaxAttempts();

    // tempo maximo em segundos para resolver o desafio
    public abstract int getMaxTimeInSeconds();

    // quantos caracteres ficam visiveis no inicio do jogo
    public abstract int getInitialVisibleChars();
}