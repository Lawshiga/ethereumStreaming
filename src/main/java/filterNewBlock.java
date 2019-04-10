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
import java.util.List;
import java.util.concurrent.Executors;

public class filterNewBlock {

    private static final Logger log = LoggerFactory.getLogger(filterNewBlock.class);

    public static void main(String[] args) throws ConnectException {
        new filterNewBlock().run();
    }

    private void run() throws ConnectException {

        String url1 = "ws://localhost:8546/";
        long pollingInterval = 30000;
        WebSocketService web3jService = new WebSocketService(url1, true);
        web3jService.connect();
        Web3j web3j = Web3j.build(web3jService, pollingInterval, Executors.newSingleThreadScheduledExecutor());



        Subscription subscription = web3j.blockObservable(true)
                .subscribe(ethBlock -> {
                    EthBlock.Block block = ethBlock.getBlock();
                    //sourceEventListener.onEvent(json message);
                    log.info("Block size ={} blockNo ={} block# ={} txnCount ={} nonce ={} extraData ={} gasLimit ={} difficulty ={} parentHash ={} gasUsed ={}", block.getSize(), block.getNumber(), block.getHash(), block.getTransactions().size(), block.getNonce(), block.getExtraData(), block.getGasLimit(),  block.getDifficulty(), block.getParentHash(), block.getGasUsed());
                });

        web3j.blockObservable(true)
                .flatMapIterable(ethBlock -> (List)
                        ethBlock.getBlock().getTransactions());

    }
}
