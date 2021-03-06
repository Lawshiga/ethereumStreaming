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
import org.web3j.protocol.websocket.WebSocketService;

import java.net.ConnectException;
import java.util.concurrent.Executors;

public class listenPendingTransaction {

    private static final Logger LOGGER = LoggerFactory.getLogger(listenPendingTransaction.class);

    public static void main(String[] args) throws ConnectException {
        new listenPendingTransaction().run();
    }

    private void run() throws ConnectException {

        //        Web3j web3j = Web3j.build(new HttpService());

        String url1 = "ws://localhost:8546/";
        long pollingInterval = 30000;
        WebSocketService web3jService = new WebSocketService(url1, true);
        web3jService.connect();
        Web3j web3j = Web3j.build(web3jService, pollingInterval, Executors.newSingleThreadScheduledExecutor());

         web3j.pendingTransactionObservable().subscribe(tx -> {
 //           tx.notifyAll();
            LOGGER.info("New tx: id={}, block={}, from={}, to={}, value={}", tx.getHash(), tx.getBlockHash(), tx.getFrom(), tx.getTo(), tx.getValue().intValue());

        });

    }

}
