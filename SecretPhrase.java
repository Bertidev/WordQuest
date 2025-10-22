public class SecretPhrase extends Challenge{
    
    public SecretPhrase(String challengeText, String hint, int level) {
        super(challengeText, hint, level);
    }

    @Override
    public int getMaxAttempts() {
        if (level == 1) {
            return 13;
        } else if (level == 2) {
            return 11;
        } else {
            return 9;
        }
    }

    @Override
    public int getMaxTimeInSeconds() {
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
        return 2;
    }
}
