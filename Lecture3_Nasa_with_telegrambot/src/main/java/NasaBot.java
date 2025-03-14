import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class NasaBot extends TelegramLongPollingBot {
    String key = "afaJissfL0p9NYeZ6Og1KcNCx9Q1jpMtJKNtf46l";
    String url = "https://api.nasa.gov/planetary/apod?api_key=" + key;
    String botUsername;
    String botToken;

    public NasaBot(String botUsername, String botToken) throws TelegramApiException {
        this.botUsername = botUsername;
        this.botToken = botToken;

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);

    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String[] command_words = update.getMessage().getText().split(" ");

            switch (command_words[0]) {
                case "/start":
                    sendMessage(chatId, "Nasa bot. I am sending astronomy picture.");
                    break;
                case "/image":
                    sendMessage(chatId, Utils.getImageUrl(url));
                    break;
                case "/date":
                    sendMessage(chatId, Utils.getImageUrl(url + "&date=" + command_words[1]));
                    break;
                case "/help":
                    sendMessage(chatId, "Enter \n" +
                            "/image to receive today's Nasa picture. \n" +
                            "/date YYYY-MM-DD to receive Nasa picture for that day.");
                    break;
                default:
                    sendMessage(chatId, "Unknown command!");
            }


        }
    }

    void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}