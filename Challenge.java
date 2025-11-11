//superclasse base para as classes SecretPhrase e SecretWord

public abstract class Challenge {
    protected String challengeText;
    protected String hint;
    protected int level;

    //CONSTRUTOR
    public Challenge(String challengeText, String hint, int level) {
        this.challengeText = challengeText;
        this.hint = hint;
        this.level = level;
    }

    //METODOS
    public String getChallengeText() {
        return this.challengeText;
    }

    public String getHint() {
        return this.hint;
    }

    public int getLevel() {
        return this.level;
    }

    //GETTERS
    public abstract int getMaxAttempts();

    public abstract int getMaxTimeInSeconds();

    public abstract int getInitialVisibleChars();
}