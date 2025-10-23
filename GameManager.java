import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public class GameManager {
    private Challenge currentChallenge;
    private StringBuilder displayedWord;
    private Set<Character> guessedLetters;
    
    private int score;
    private int hits;
    private int errors;
    private int remainingAttempts;
    
    private long startTimeMillis;
    private int maxTimeInSeconds;

    public void startGame(Challenge challenge) {

        this.currentChallenge = challenge;
        this.score = 0;
        this.hits = 0;
        this.errors = 0;

        this.remainingAttempts = this.currentChallenge.getMaxAttempts();
        this.maxTimeInSeconds = this.currentChallenge.getMaxTimeInSeconds();
        
        this.startTimeMillis = System.currentTimeMillis();
        this.guessedLetters = new HashSet<>(); 
        this.displayedWord = new StringBuilder();

        String secretText = this.currentChallenge.getChallengeText();
        for (int i = 0; i < secretText.length(); i++) {
            if (secretText.charAt(i) == ' ') {
                displayedWord.append(' ');
            } else {
                displayedWord.append('_');
            }
        }
        
        int lettersToReveal = this.currentChallenge.getInitialVisibleChars();
        Random random = new Random();
        int revealedCount = 0;

        Set<Character> lettersAlreadyPicked = new HashSet<>(); 

        while (revealedCount < lettersToReveal) {
            int randomIndex = random.nextInt(secretText.length());
            char charToReveal = secretText.charAt(randomIndex);

            if (Character.isLetter(charToReveal) && !lettersAlreadyPicked.contains(charToReveal)) {
                revealedCount++; 
                lettersAlreadyPicked.add(charToReveal); 
                
                for (int i = 0; i < secretText.length(); i++) {
                    if (secretText.charAt(i) == charToReveal) {
                        displayedWord.setCharAt(i, charToReveal);
                    }
                }
                
                this.guessedLetters.add(charToReveal);
            }
        }
    }

    public boolean guessLetter (char letter) {
        if(guessedLetters.contains(letter)) {
            return false;
        }

        this.guessedLetters.add(letter);
        String secretText = this.currentChallenge.getChallengeText();
        if (secretText.indexOf(letter) == -1) {  
            this.score-= 5;
            this.errors++;
        } else {
            this.hits++;
            for (int i = 0; i < secretText.length(); i++) {
                if (secretText.charAt(i) == letter) {
                    this.displayedWord.setCharAt(i, letter);
                    this.score += 10;
                }
            }
        }
        this.remainingAttempts--;
        return true;
    }

    public String checkGameState() {
        long elapsedTimeMillis = System.currentTimeMillis() - this.startTimeMillis;
        long maxTimeMillis = this.maxTimeInSeconds * 1000L;

        if(!displayedWord.toString().contains("_")) {
            return "WIN";
        } else if (remainingAttempts == 0) {
            return "LOSE_ATTEMPTS";
        } else if (elapsedTimeMillis >= maxTimeMillis) {
            return "LOSE_TIME";
        }
        return "PLAYING";
    }

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

    public long getRemainingTimeInSeconds() {
        long elapsedTimeMillis = System.currentTimeMillis() - this.startTimeMillis;
        long maxTimeMillis = this.maxTimeInSeconds * 1000L;
        long remainingMillis = maxTimeMillis - elapsedTimeMillis;
        
        return Math.max(0, remainingMillis / 1000); 
    }
}
