import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws TelegramApiException {

        new NasaBot("Nasa_34",
                "my_token");
    }
}