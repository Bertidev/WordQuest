import java.util.Set;
import java.util.HashSet;

public class SecretWord extends Challenge {

    public SecretWord(String challengeText, String hint, int level) {
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
            allowedMisses = 7; 
        } else if (level == 2) {
            allowedMisses = 5; 
        } else {
            allowedMisses = 3; 
        }
        
        return uniqueLetters.size() + allowedMisses;
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
