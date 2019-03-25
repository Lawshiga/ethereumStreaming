


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import rx.Subscription;

import java.util.concurrent.TimeUnit;

public class blockNotification {
    private static final Logger log = LoggerFactory.getLogger(Subscribe.class);

//    public static String contractAddr = "0xe947eb0ede38da5b47c45ae6b8c09256b95012e0";

    public static void main(String[] args) throws InterruptedException {

        Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
        Subscription subscription = web3.blockObservable(false).subscribe(block -> {
            System.out.println("Sweet, block number " + block.getBlock().getNumber() + " has just been created");
        }, Throwable::printStackTrace);
        TimeUnit.MINUTES.sleep(2);
        subscription.unsubscribe();
    }

}
