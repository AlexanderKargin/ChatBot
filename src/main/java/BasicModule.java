import com.google.inject.AbstractModule;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;


public class BasicModule extends AbstractModule {
    @Override
    public void configure(){
        bind(BotInterface.class).to(Bot.class);
        bind(DefaultBotOptions.class).toInstance(defBotOpt());
    }

    public DefaultBotOptions defBotOpt(){
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        botOptions.setProxyHost(System.getenv("PROXY_HOST"));
        botOptions.setProxyPort(Integer.parseInt(System.getenv("PROXY_PORT")));
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        return botOptions;
    }
}
