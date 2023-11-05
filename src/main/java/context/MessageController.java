package context;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.Scanner;

@Configuration
class MyConfigClass {
    private String firstProperty;
    private String secondProperty;
}
@Controller
class MessageController {

    private final Scanner scanner;
    private final MessagePrinter messagePrinter; // zależność do drukarki wiadomości

    public MessageController(Scanner scanner, MessagePrinter messagePrinter) {
        this.scanner = scanner;
        this.messagePrinter = messagePrinter;
    }


    //metoda z główną pętlą programu
    public void mainLoop() {
        Option option;
        do {
            option = chooseOption();
            executeOption(option);
        } while (option != Option.EXIT);
    }

    private void executeOption(Option option) {
        switch (option) {
            case NEXT_MESSAGE -> printMessage();
            case EXIT -> exit();
        }
    }

    private void exit() {
        System.out.println("Koniec programu, do zobaczenia.");
    }

    private void printMessage() {
        messagePrinter.printMessage();
    }

    private Option chooseOption() {
        int optionNumber;
        Optional<Option> option;
        do {
            printOptions();
            System.out.println("Wybierz nr opcji: ");
            optionNumber = scanner.nextInt();
            option = Option.fromInt(optionNumber);
        } while (option.isEmpty());
        return option.get();
    }

    private enum Option {
        EXIT(0, "Wyjście z programu"),
        NEXT_MESSAGE(1, "Wczytaj kolejny komunikat");

        private final int number;
        private final String descryption;

        Option(int number, String descryption) {
            this.number = number;
            this.descryption = descryption;
        }


        //konwersja numeru na odpowiadającą mu opcję
        //metoda fromInt pomaga w konwersji numeru na odpowiadającą mu opcję.
        static Optional<Option> fromInt(int number) {
            Option[] options = Option.values();
            if (number >= 0 && number < options.length)
                return Optional.of(options[number]);
            else
                return Optional.empty();

        }

        @Override
        public String toString() {
            return number + " - " + descryption;
        }
    }

    private void printOptions() {
        Option[] options = Option.values();
        System.out.println("Opcje: ");
        for (Option option : options) {
            System.out.println(option);
        }
    }

}
