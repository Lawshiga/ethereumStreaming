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
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

//for sink----> fund transaction

public class sendTransaction {
    private static final Logger log = LoggerFactory.getLogger(sendTransaction.class);

    public static void main(String[] args) throws Exception {
        new sendTransaction().run();
    }


    private void run() throws Exception {
        // We start by creating a new web3j instance to connect to remote nodes on the network.

        Web3j web3j = Web3j.build(new HttpService());
       // Web3j web3j = new JsonRpc2_0Web3j(httpServiceProvider.getService(url));
        //check connection by getting the node version
        log.info("Connected to Ethereum client version: "
                + web3j.web3ClientVersion().send().getWeb3ClientVersion());//This will make a synchronous request, by changing send() to sendAsync() or observable() you can perform async requests.
        Credentials credentials =
                WalletUtils.loadCredentials(
                        "seed",
                        "/home/lawshiqa/project1/blkchain/keystore/UTC--2019-02-21T09-39-23.093774268Z--e947eb0ede38da5b47c45ae6b8c09256b95012e0");
        log.info("Credentials loaded with the address::::" + credentials.getAddress());
       log.info("Sending Ether");

        BigInteger gas = BigInteger.valueOf(21000);
        log.info("gas:::" + gas);

        //Both version and account balance are read-only operations, that don’t produce new transactions to the Ethereum network.
        BigInteger amount = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.fromString("latest")).send().getBalance();
        log.info("balance:::" + amount);

        String publicAddress = credentials.getAddress();
        String privateKey = String.valueOf(credentials.getEcKeyPair().getPrivateKey());
        BigInteger privateKey2 = credentials.getEcKeyPair().getPrivateKey();

        log.info("public key::::::::::::" + publicAddress);
        log.info("private key::::::::::::  " + privateKey);
        log.info("private key::::::::::::  " + privateKey2);

        BigInteger xth = new BigInteger("1000000000000000000");
        log.info(":::Transferred amount is::::" + amount);

        BigInteger gaslimit = BigInteger.valueOf(4800000);
        log.info("gaslimit:::" + gaslimit);

        BigInteger gasprice = web3j.ethGasPrice().send().getGasPrice();
        log.info("gasprice:::" + gasprice);

        // Calculate transaction fees for transaction
        BigInteger txnFee = gas.multiply(gasprice);
        log.info("transaction fee:::" + txnFee);

        BigInteger hashrate = web3j.ethHashrate().send().getHashrate();
        log.info("hashrate:::" + hashrate);

        BigInteger balance = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.fromString("latest")).send().getBalance();
        log.info("balance:::" + balance);

//To perform transaction use transactionReceipt
        //On successful transaction it will produce a hash
        TransactionReceipt transferReceipt = Transfer.sendFunds(//use the Transfer class for sending Ether, which takes care of the nonce management and polling(like recording) for a response for you
                web3j, credentials,
                "0x6e5b85cd00f9e92dec7f3d05e40f93b353ec2be7",  // you can put any address here
                BigDecimal.valueOf(100), Convert.Unit.ETHER)  // 1 wei = 10^-18 Ether  -the amount of Ether you wish to send to the destination address
                .sendAsync().get();
        log.info("Transaction complete : "
                + transferReceipt.getTransactionHash());
    }
}
