import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        
        DataAccess dataAccess = new DataAccess();
        GameManager gameManager = new GameManager();
        Random random = new Random();
        Scanner scanner = new Scanner(System.in); 
        
        System.out.println("Carregando desafios...");
        
        List<Challenge> allChallenges = dataAccess.loadChallengesFromFile("palavras.txt");
        
        boolean wantsToPlay = true; 
        
        while (wantsToPlay) { 
            
            int chosenLevel = 0;
            List<Challenge> filteredChallenges = new ArrayList<>(); 

            while (filteredChallenges.isEmpty()) {
                System.out.println("\nEscolha o nível de dificuldade:");
                System.out.println("1 - Fácil");
                System.out.println("2 - Médio");
                System.out.println("3 - Difícil");
                System.out.print("Digite o número (1, 2 ou 3): ");
                
                String levelInput = scanner.nextLine();
                
                try {
                    chosenLevel = Integer.parseInt(levelInput);
                    if (chosenLevel >= 1 && chosenLevel <= 3) {
                        for (Challenge c : allChallenges) {
                            if (c.getLevel() == chosenLevel) {
                                filteredChallenges.add(c);
                            }
                        }
                        if (filteredChallenges.isEmpty()) {
                            System.out.println("Desculpe, não há palavras cadastradas para o nível " + chosenLevel);
                        }
                    } else {
                        System.out.println("Input inválido. Por favor, digite 1, 2 ou 3.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input inválido. Por favor, digite um NÚMERO.");
                }
            }
            
            int randomIndex = random.nextInt(filteredChallenges.size());
            Challenge chosenChallenge = filteredChallenges.get(randomIndex);
            
            gameManager.startGame(chosenChallenge);
            
            System.out.println("\n--- Jogo Iniciado! Nível " + chosenLevel + " ---");
            System.out.println("A qualquer momento, digite 'DICA' para revelar a dica.");

            boolean showHint = false;
            String gameState = "PLAYING"; 

            while (gameState.equals("PLAYING")) {
                
                System.out.println("\n------------------------------------");
                System.out.println("Palavra: " + gameManager.getDisplayedWord());
                
                if (showHint) {
                    System.out.println("Dica: " + gameManager.getHint());
                } else {
                    System.out.println("Dica: (Digite 'DICA' para revelar)");
                }
                
                System.out.println("Tentativas Restantes: " + gameManager.getRemainingAttempts());
                System.out.println("Tempo Restante: " + gameManager.getRemainingTimeInSeconds() + "s");
                System.out.println("Pontos: " + gameManager.getScore());
                System.out.println("Letras Tentadas: " + gameManager.getGuessedLetters());
                
                System.out.print("\nDigite uma letra (ou 'DICA'): ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("DICA")) {
                    if (showHint) {
                        System.out.println(">>> A dica já está sendo mostrada. <<<");
                    } else {
                        showHint = true;
                        System.out.println(">>> DICA REVELADA! <<<");
                    }
                    continue; 
                }

                if (input.isEmpty() || !Character.isLetter(input.charAt(0))) {
                    System.out.println("Input inválido. Por favor, digite apenas uma letra.");
                    continue; 
                }

                char guess = Character.toUpperCase(input.charAt(0));
                
                boolean isValidGuess = gameManager.guessLetter(guess);
                
                if (!isValidGuess) {
                    System.out.println(">>> VOCÊ JÁ TENTOU A LETRA '" + guess + "' <<<");
                }

                gameState = gameManager.checkGameState();
            }

            System.out.println("\n====================================");
            System.out.println("           FIM DE JOGO!");
            System.out.println("====================================");

            switch (gameState) {
                case "WIN":
                    System.out.println("PARABÉNS! Você venceu!");
                    break;
                case "LOSE_ATTEMPTS":
                    System.out.println("VOCÊ PERDEU! Suas tentativas acabaram.");
                    break;
                case "LOSE_TIME":
                    System.out.println("VOCÊ PERDEU! O tempo esgotou.");
                    break;
            }

            System.out.println("A palavra era: " + chosenChallenge.getChallengeText());
            System.out.println("Pontuação Final: " + gameManager.getScore());
            System.out.println("Acertos: " + gameManager.getHits());
            System.out.println("Erros: " + gameManager.getErrors());

            System.out.println("\n------------------------------------");
            System.out.print("Deseja jogar novamente? (S/N): ");
            String playAgainInput = scanner.nextLine();
            
            if (!playAgainInput.equalsIgnoreCase("S")) {
                wantsToPlay = false;
            }
            
        } 

        System.out.println("\nObrigado por jogar WordQuest! Até a próxima!");
        scanner.close(); 
    }
}