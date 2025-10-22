public class SecretWord extends Challenge {

    public SecretWord(String challengeText, String hint, int level) {
        super(challengeText, hint, level);
    }
    
    @Override
    public int getMaxAttempts() {
        if (level == 1) 
        {
            return 10;
        } else if (level == 2) {
            return 6;
        } else {
            return 3;
        }
    }

    @Override
    public int getMaxTimeInSeconds() {
        if (level == 1) 
        {
            return 90;
        } else if (level == 2) {
            return 70;
        } else {
            return 45;
        }
    }

    @Override
    public int getInitialVisibleChars() {
        return 1;
    }
}
