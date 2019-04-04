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
package org.wso2.carbon.ethereumJ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class rawTransaction {

    private static final Logger log = LoggerFactory.getLogger(rawTransaction.class);
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);


    public static void main(String[] args) throws Exception {
        new rawTransaction().run();
    }

    private void run() throws IOException, CipherException, ExecutionException, InterruptedException {
        Web3j web3j = Web3j.build(new HttpService());


        log.info("Connected to Ethereum client version: "
                + web3j.web3ClientVersion().send().getWeb3ClientVersion());//This will make a synchronous request, by changing send() to sendAsync() or observable() you can perform async requests.
        Credentials credentials =
                WalletUtils.loadCredentials(
                        "seed",
                        "/home/lawshiqa/project1/blkchain/keystore/UTC--2019-02-21T09-39-23.093774268Z--e947eb0ede38da5b47c45ae6b8c09256b95012e0");
        log.info("Credentials loaded with the address::::" + credentials.getAddress());
        log.info("Sending Ether");


        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                "0xe947eb0ede38da5b47c45ae6b8c09256b95012e0", DefaultBlockParameterName.LATEST).sendAsync().get();

        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        System.out.println(nonce);

        RawTransaction rawTransaction = RawTransaction.createEtherTransaction (
                nonce, GAS_PRICE, GAS_LIMIT, "0x6e5b85cd00f9e92dec7f3d05e40f93b353ec2be7",  new BigInteger("10000"));
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        log.info("Hexa value of signedMessage::::::::::::::::::::::" + hexValue);

        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        String transactionHash = ethSendTransaction.getTransactionHash();

        log.info("Transaction hash :::::::::::::::::::::::::::::::::" + transactionHash);


    }
}
