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
import org.web3j.protocol.websocket.WebSocketService;
import rx.Subscription;

import java.net.ConnectException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class listenNewBlock {

    private static final Logger log = LoggerFactory.getLogger(listenNewBlock.class);

    public static void main(String[] args) throws ConnectException {
        new listenNewBlock().run();
    }

    private void run() throws ConnectException {

        String url1 = "ws://localhost:8546/";
        long pollingInterval = 30000;

        ArrayList<String> transactionList = new ArrayList<>();
        WebSocketService web3jService = new WebSocketService(url1, true);
        web3jService.connect();
        Web3j web3j = Web3j.build(web3jService, pollingInterval, Executors.newSingleThreadScheduledExecutor());

        Subscription subscription = web3j.blockObservable(true)
                .subscribe(ethBlock -> {

                    log.info("Block result:", ethBlock.getResult());
                    EthBlock.Block block = ethBlock.getBlock();

                    log.info("Using observables true--------------------------------------");
                    log.info("Block size ={} blockNo ={} block# ={} txnCount ={} nonce ={} extraData ={} gasLimit ={} difficulty ={} parentHash ={} gasUsed ={}", block.getSize(), block.getNumber(), block.getHash(), block.getTransactions().size(), block.getNonce(), block.getExtraData(), block.getGasLimit(),  block.getDifficulty(), block.getParentHash(), block.getGasUsed());
                    log.info("Transaction list ={}", transactionList.add( block.getTransactions().toString()));
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



    });
    }


}
