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
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.utils.Convert;
import rx.Observable;
import rx.Subscription;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.ConnectException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrations of working with RxJava's Observables in web3j.
 */
//for source----> listen to new blocks


    //When we create an object of CountDownLatch, we specify the number of threads it should wait for,
    //all such thread are required to do count down by calling CountDownLatch.countDown()
    //once they are completed or ready to the job. As soon as count reaches zero, the waiting task starts running.
    //
public class listenForBlocks {

        private static final int COUNT = 10;

        private static Logger log = LoggerFactory.getLogger(listenForBlocks.class);

        private final Web3j web3j;
    private  ScheduledExecutorService scheduledExecutorService;

    public listenForBlocks() throws ConnectException {
            String url1 = "ws://localhost:8546/";
 //           HttpService httpService = new HttpService(url1, true);
  //          WebSocketService webSocketService = new WebSocketService();
  //         web3j = Web3j.build(httpService, 36000, scheduledExecutorService);  // defaults to http://localhost:8545/

            // Connection to the node
            WebSocketService web3jService = new WebSocketService(url1, true);
            web3jService.connect();
            web3j = Web3j.build(web3jService);
        }

        private void run() throws Exception {
            simpleFilterExample();
            blockInfoExample();
            countingEtherExample();
            clientVersionExample();
            System.exit(0);  // we explicitly call the exit to clean up our ScheduledThreadPoolExecutor used by web3j
        }

        public static void main(String[] args) throws Exception {
            new listenForBlocks().run();
        }

        void simpleFilterExample() throws Exception {

            //operate upon the emissions and notifications from an Observable when subscribe is called
            //find out what is the difference between true & false
            //the false parameter specifies that we only want the blocks, not the embedded transactions too
            Subscription subscription = web3j.blockObservable(false).subscribe(block -> { //This blockObservable will now emit a block object to the subscriber each time a new block is appended to the Ethereum blockchain.
               log.info("Block size ={} blockNo ={} block# ={} txnCount ={}", block.getResult().getSize(), block.getBlock().getNumber(), block.getBlock().getHash());
                log.info("Sweet, block number " + block.getBlock().getNumber()
                        + " has just been created");
            }, Throwable::printStackTrace);
//We need to include the sleep statement, because the subscription takes place asynchronously in a different thread of execution to the rest of our program.
            TimeUnit.MINUTES.sleep(2);//sleep after 2 minutes
            subscription.unsubscribe();
        }

        void blockInfoExample() throws Exception { //By running the code, we can see details for the last 10 blocks added to the Ethereum blockchain:
            CountDownLatch countDownLatch = new CountDownLatch(COUNT);

            log.info("Waiting for " + COUNT + " transactions...");
            //call json-rpc method by web3j.<method>(<parameters>)
            Subscription subscription = web3j.blockObservable(true)
                    .take(COUNT)
                    .subscribe(ethBlock -> {

                       log.info("Block result:", ethBlock.getResult());
                        EthBlock.Block block = ethBlock.getBlock();

                        log.info("Using observables true--------------------------------------");
                        log.info("Block size ={} blockNo ={} block# ={} txnCount ={} nonce ={} extraData ={} gasLimit ={} difficulty ={} parentHash ={} gasUsed ={}", block.getSize(), block.getNumber(), block.getHash(), block.getTransactions().size(), block.getNonce(), block.getExtraData(), block.getGasLimit(),  block.getDifficulty(), block.getParentHash(), block.getGasUsed());
                        log.info("Transaction list ={}", block.getTransactions());
                        LocalDateTime timestamp = Instant.ofEpochSecond(//instant represents current time, An Instant counts the time beginning from the first second of January 1, 1970 (1970-01-01 00:00:00) also called the EPOCH.
                                block.getTimestamp()
                                        .longValueExact()).atZone(ZoneId.of("UTC")).toLocalDateTime();//Coordinated Universal Time (UTC)
                       log.info("miner",block.getMiner());
                        block.getAuthor();
                        log.info("author", block.getMiner());
                        int transactionCount = block.getTransactions().size();
//                        String author = block.getAuthor();
//                        String miner = block.getMiner();
//                        BigInteger blockNumber = block.getNumber();
//                        List trasactions = block.getTransactions();
//                        BigInteger size = block.getSize();
//                        log.info("Author of the block :::::::::::::;", author);
//                        log.info("Miner of the block::::::::::::;;;;", miner);
//                        log.info("Block number ::::::::::;", blockNumber);
//                        log.info("List of transactions:::::::::::::", trasactions);
//                        log.info("Block size::::::::::::::", size);
//                        log.info("block.getTransaction() :::" + block.getTransactions());
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

            countDownLatch.await(10, TimeUnit.MINUTES);//calling awaits block the thread until countDown becomes zero
            subscription.unsubscribe();
        }

    void TransactionInfo() throws Exception { //By running the code, we can see details for the last 10 blocks added to the Ethereum blockchain:
        CountDownLatch countDownLatch = new CountDownLatch(COUNT);

        log.info("Waiting for " + COUNT + " transactions...");
        web3j.transactionObservable().subscribe(tx -> {
                    web3j.transactionObservable();
                },  Throwable::printStackTrace, () -> System.out.println("tx done"));
        //call json-rpc method by web3j.<method>(<parameters>)
//        Subscription subscription = web3j.transactionObservable().subscribe(tx -> {
//    ...
//        });

//        Subscription subscription = web3j.transactionObservable()
//                .take(COUNT)
//                .subscribe(ethBlock -> {
//                    EthBlock.Block block = ethBlock.getTransactionIndex();
//                    LocalDateTime timestamp = Instant.ofEpochSecond(//instant represents current time, An Instant counts the time beginning from the first second of January 1, 1970 (1970-01-01 00:00:00) also called the EPOCH.
//                            block.getTimestamp()
//                                    .longValueExact()).atZone(ZoneId.of("UTC")).toLocalDateTime();//Coordinated Universal Time (UTC)
//                    int transactionCount = block.getTransactions().size();
//                    log.info("block.getTransaction() :::" + block.getTransactions());
//                    log.info("transaction count for block.getTransaction().size():::::::::::::::::" + transactionCount);
//                    String hash = block.getHash();
//                    log.info("getting timestamp::::::::::::::::::" + timestamp);
//                    String parentHash = block.getParentHash();
//
//                    log.info(
//                            timestamp + " " +
//                                    "Tx count: " + transactionCount + ", " +
//                                    "Hash: " + hash + ", " +
//                                    "Parent hash: " + parentHash
//                    );
//                    countDownLatch.countDown();//countDownLatch is initialized with 10 countdowns and it gets decremented every time we call countDown()
//                }, Throwable::printStackTrace);
//
//        countDownLatch.await(10, TimeUnit.MINUTES);//calling awaits block the thread until countDown becomes zero
//        subscription.unsubscribe();
    }

        void countingEtherExample() throws Exception {
            CountDownLatch countDownLatch = new CountDownLatch(1);//wait for one thread before it starts
//to obtain the total value of transactions taking place during a given number of blocks
            log.info("Eth Waiting for " + COUNT + " transactions...");
            Observable<BigInteger> transactionValue = web3j.transactionObservable()//Using the transaction Observable, we can easily start pulling information out of the blockchain in real time.
                    .take(COUNT)
                    .map(Transaction::getValue)
                    .reduce(BigInteger.ZERO, BigInteger::add);
//we obtain the value of each emitted transaction and sum them together via the reduce() method.
            log.info("transaction value with obervable object is ::::::::::::::::::::::::::" + transactionValue);
            log.info("--------------getting into transaction value subscribe part--------------");
            Subscription subscription = transactionValue.subscribe(total -> {
                BigDecimal value = new BigDecimal(total);
                log.info("Transaction value: " +
                        Convert.fromWei(value, Convert.Unit.ETHER) + " Ether (" +  value + " Wei)");// Convert method converts the value in Wei that we get back from our transactions into Ether.
                countDownLatch.countDown();
            }, Throwable::printStackTrace);

            countDownLatch.await(10, TimeUnit.MINUTES); //// The  task waits for one threads
            subscription.unsubscribe();
        }

        void clientVersionExample() throws Exception {
            CountDownLatch countDownLatch = new CountDownLatch(1);

            Subscription subscription = web3j.web3ClientVersion().observable().subscribe(x -> {
                log.info("Client is running version: {}", x.getWeb3ClientVersion());
                countDownLatch.countDown();
            });

            countDownLatch.await();
            subscription.unsubscribe();
        }
    }


