
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.http.HttpService;

import rx.functions.Action1;

public class Subscribe {
    private static final Logger log = LoggerFactory.getLogger(Subscribe.class);

    public static String contractAddr = "0xe947eb0ede38da5b47c45ae6b8c09256b95012e0";

    public static void main(String[] args) {

        Web3j web3 = Web3j.build(new HttpService());

        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, contractAddr);
        log.info("getting filter   :");
        web3.ethLogObservable(filter).subscribe(new Action1<Log>() {
            @Override
            public void call(Log log) {
                System.out.println("log.toString(): " + log.toString());
            }
        });
    }
}