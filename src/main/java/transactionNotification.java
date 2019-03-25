/*
 *  Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.http.HttpService;
import rx.Subscription;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

//observe block copy
public class transactionNotification {

    private static final int COUNT = 10;

    private Logger log = LoggerFactory.getLogger(transactionNotification.class);

    private CountDownLatch countDownLatch = new CountDownLatch(COUNT);

    private Web3j web3j = Web3j.build(new HttpService());  // defaults to http://localhost:8545/

    public transactionNotification() {

        web3j = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
    }

    private void run() throws Exception {
        blockInfo();
        System.exit(0);  // we explicitly call the exit to clean up our ScheduledThreadPoolExecutor used by web3j
    }

    public static void main(String[] args) throws Exception {
        new transactionNotification().run();
    }
    void blockInfo() throws InterruptedException {
        log.info("Waiting for "+COUNT +" transactions...");
        //call json-rpc method by web3j.<method>(<parameters>)
        Subscription subscription = web3j.blockObservable(true)
                .take(COUNT)
                .subscribe(ethBlock -> {
                    EthBlock.Block block = ethBlock.getBlock();
                    LocalDateTime timestamp = Instant.ofEpochSecond(//instant represents current time, An Instant counts the time beginning from the first second of January 1, 1970 (1970-01-01 00:00:00) also called the EPOCH.
                            block.getTimestamp()
                                    .longValueExact()).atZone(ZoneId.of("UTC")).toLocalDateTime();//Coordinated Universal Time (UTC)
                    int transactionCount = block.getTransactions().size();
                    log.info("block.getTransaction() :::" + block.getTransactions());
                    log.info("transaction count for block.getTransaction().size():::::::::::::::::" + transactionCount);
                    String hash = block.getHash();
                    log.info("getting timestamp::::::::::::::::::" + timestamp);
                    String parentHash = block.getParentHash();

                    log.info(
                            timestamp + " " +
                                    "Tx count: " + transactionCount + ", " +
                                    "Hash: " + hash + ", " +
                                    "Parent hash: " + parentHash
                    );
                    countDownLatch.countDown();//countDownLatch is initialized with 10 countdowns and it gets decremented every time we call countDown()
                }, Throwable::printStackTrace);


        countDownLatch.await(10,TimeUnit.MINUTES);//calling awaits block the thread until countDown becomes zero
        subscription.unsubscribe();
    }

}
