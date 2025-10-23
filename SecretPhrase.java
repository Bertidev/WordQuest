import java.util.Set;
import java.util.HashSet;

public class SecretPhrase extends Challenge{
    
    public SecretPhrase(String challengeText, String hint, int level) {
        super(challengeText, hint, level);
    }

    @Override
    public int getMaxAttempts() {
        Set<Character> uniqueLetters = new HashSet<>();
        for (char c : this.challengeText.toCharArray()) {
            if (Character.isLetter(c)) {
                uniqueLetters.add(c);
            }
        }
        
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
