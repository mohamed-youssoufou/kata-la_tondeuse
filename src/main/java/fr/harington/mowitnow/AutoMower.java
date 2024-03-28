package fr.harington.mowitnow;

import fr.harington.mowitnow.exceptions.InvalidFileFormatException;
import fr.harington.mowitnow.services.impl.FileParserServiceImpl;
import fr.harington.mowitnow.services.impl.PilotMowerServiceImpl;
import fr.harington.mowitnow.services.impl.ProcessorServiceImpl;
import fr.harington.mowitnow.utils.FileValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.Scanner;

public class AutoMower {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoMower.class);

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        boolean continueProcessing;

        do {
            System.out.println("Veuillez entrer le chemin complet du fichier à injecter (fichier.txt):");
            LOGGER.info("--- Begin execute main program. ---");
            var filePath = scanner.nextLine();
            try {
                if (!FileValidator.veritfyFileExtension(filePath, "txt")) {
                    throw new InvalidFileFormatException("Mauvais format de fichier: format accepté .txt");
                }
                var fileParserService = new FileParserServiceImpl();
                var lawnMowerFileModel = fileParserService.parse(Paths.get(filePath));
                var pilotService = new PilotMowerServiceImpl();
                var processorService = new ProcessorServiceImpl(pilotService);
                var positions = processorService.execute(lawnMowerFileModel);
                for (int numberCommand = 0; numberCommand < positions.size(); numberCommand++) {
                    System.out.printf("position finale de la tondeuse (%s) : %s%n", numberCommand, positions.get(numberCommand));
                }
            } catch (Exception e) {
                LOGGER.error("Exception while processing file with path {}", filePath, e);
                System.out.println("Une erreur est survenue lors du traitement de votre fichier: " + e.getMessage());
            }

            System.out.println("Voulez-vous continuer et traiter un autre fichier ? (O/N)");
            continueProcessing = scanner.nextLine().trim().equalsIgnoreCase("O");

        } while (continueProcessing);

        LOGGER.info("--- End program execution. ---");
        System.out.println("Merci d'avoir utilisé le programme et à bientôt!");
        scanner.close();
    }
}