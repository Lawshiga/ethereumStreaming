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
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCoinbase;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.websocket.WebSocketService;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.util.concurrent.Executors;

//Source ---> listen for transaction

public class listenForTransaction {

    private static final Logger LOGGER = LoggerFactory.getLogger(listenForTransaction.class);


    public static void main(String[] args) throws ConnectException {
        new listenForTransaction().run();
    }

    private void run() throws ConnectException {

        //        Web3j web3j = Web3j.build(new HttpService());

        String url1 = "ws://localhost:8546/";
        long pollingInterval = 30000;
        WebSocketService web3jService = new WebSocketService(url1, true);
        web3jService.connect();
        Web3j web3j = Web3j.build(web3jService, pollingInterval, Executors.newSingleThreadScheduledExecutor());

        web3j.transactionObservable().subscribe(tx -> {


            LOGGER.info("New tx: transactionHash={}, blockHash={}, from={}, blckN={} to={}, value={}, nonce={}, String={}, V={}, gasprice={}, gas={}, publickey={}, inputString={}, raw={}", tx.getHash(), tx.getBlockHash(), tx.getFrom(), tx.getBlockNumber(), tx.getTo(), tx.getValue().intValue(), tx.getNonce(), tx.getS(), tx.getV(), tx.getGasPrice(), tx.getGas(), tx.getPublicKey(), tx.getInput());

            try {

                EthCoinbase coinbase = web3j.ethCoinbase().send();
                EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(tx.getFrom(), DefaultBlockParameterName.LATEST).send();
                LOGGER.info("Tx count: {}", transactionCount.getTransactionCount().intValue());

                if (transactionCount.getTransactionCount().intValue() % 10 == 0) {

                    EthGetTransactionCount tc = web3j.ethGetTransactionCount(coinbase.getAddress(), DefaultBlockParameterName.LATEST).send();
                    Transaction transaction = Transaction.createEtherTransaction(coinbase.getAddress(), tc.getTransactionCount(), tx.getValue(), BigInteger.valueOf(21_000), tx.getFrom(), tx.getValue());
                    web3j.ethSendTransaction(transaction).send();

                }

            } catch (IOException e) {
                LOGGER.error("Error getting transactions", e);
            }

        });

        LOGGER.info("Subscribed");

    }

}
