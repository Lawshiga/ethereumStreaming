import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.http.HttpService;
import org.wso2.carbon.ethereumJ.Forest;
import rx.functions.Action1;

import java.math.BigInteger;
import java.util.Arrays;

//for sink ---> deploy contract

public class deployContract {
    private static final Logger log = LoggerFactory.getLogger(deployContract.class);

    //public static String contractAddr = "0x19b2002fcda5c7ecab174075e8d7c82b17d83c85";

    public static void main(String[] args) throws Exception {

        Web3j web3 = Web3j.build(new HttpService());

//        newly added
        SolidityFunctionWrapperGenerator.main(Arrays.asList(
                "src/main/solidity/forest/Forest.bin", // binary file location
                "src/main/solidity/forest/Forest.abi", // abi file location
                "-o", "src/main/java", // place to store generated wrapper
                "-p", "org.wso2.carbon.ethereumJ") // package name for generated class
                .toArray(new String[]{}));

        Credentials credentials = Credentials.create("516AA44178D4E2EDA879C80F7FC1D843C788D74988E36AC47F5FE7F119CDADA6");

        Forest forest = Forest.deploy(
                web3, // configuration object
                credentials, // account credentials
                new BigInteger("30000000000"), // gas price
                new BigInteger("2000000"), // gas limit
                new BigInteger("2000")) // tree cost - parameter of our contract
                .send();
        String contractAddr = forest.getContractAddress();
        //new added till this

        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, contractAddr);
        log.info("getting filter   :");
        web3.ethLogObservable(filter).subscribe(new Action1<Log>() {
            @Override
            public void call(Log log) {
                System.out.println("log.toString(): " + log.toString());
            }
        });
    }

//    private void listenForMessages() {
//        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, Chat.CHAT_CONTRACT);
//        filter.addSingleTopic(Chat.TOPIC_SEND_MESSAGE);
//
//        web3j.ethLogObservable(filter).subscribeOn(Schedulers.newThread()).subscribe(log -> {
//
//
//            YourContract.MessageSentEventEventResponse messageSentEventEventResponse = YourContract.getEventValuesMessageSent(log);
//
//
//            Log("From: " + messageSentEventEventResponse.from);
//            Log("To: " + messageSentEventEventResponse.to);
//            Log("Message: " + messageSentEventEventResponse.message);
//            Log("Timestamp: " + messageSentEventEventResponse.timestamp);
//
//        }
//another example
//    web3 = Web3j.build(new HttpService("http://localhost:9545/"));
//    Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
//    MyContract myContract = MyContract.load(CONTRACT_ADDRESS, web3, credentials, GAS_PRICE, GAS_LIMIT);
//    EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, greetingsContract.getContractAddress().substring(2));
//    String encodedEventSignature = EventEncoder.encode(MyContract.MYEVENT_EVENT);
//filter.addSingleTopic(encodedEventSignature);
//log.info("subscribing to event with filter");
//web3.ethLogObservable(filter).subscribe(eventString -> log.info("event string={}", eventString.toString()));
}