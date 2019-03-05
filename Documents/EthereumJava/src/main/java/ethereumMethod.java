///*
// *  Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
// *
// *  WSO2 Inc. licenses this file to you under the Apache License,
// *  Version 2.0 (the "License"); you may not use this file except
// *  in compliance with the License.
// *  You may obtain a copy of the License at
// *
// *    http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing,
// * software distributed under the License is distributed on an
// * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// * KIND, either express or implied.  See the License for the
// * specific language governing permissions and limitations
// * under the License.
// */
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.web3j.crypto.Credentials;
//import org.web3j.crypto.WalletUtils;
//import org.web3j.protocol.Web3j;
//import org.web3j.protocol.core.DefaultBlockParameter;
//import org.web3j.protocol.core.methods.response.EthAccounts;
//import org.web3j.protocol.core.methods.response.EthGetBalance;
//import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
//import org.web3j.protocol.core.methods.response.TransactionReceipt;
//import org.web3j.protocol.http.HttpService;
//import org.web3j.tx.Transfer;
//import org.web3j.utils.Convert;
//
//import java.math.BigDecimal;
//import java.util.concurrent.ExecutionException;
//
//
//
//public class ethereumMethod {
//    private static final Logger log = LoggerFactory.getLogger(ethereumMethod.class);
//    private static final String DEFAULT_ADDRESS = "0x6e5b85cd00f9e92dec7f3d05e40f93b353ec2be7";
//    private final Web3j web3j;
//
//    public ethereumMethod(Web3j web3j) {
//
//        this.web3j = web3j;
//    }
//
//    public static void main(String[] args) throws Exception {
//        new ethereumMethod().run();
//    }
//
//    public EthAccounts getEthAccounts() throws ExecutionException, InterruptedException {
//
//        EthAccounts result = new EthAccounts();
//        result = this.web3j.ethAccounts()
//                .sendAsync()
//                .get();
//        return result;
//    }
//
//    public EthGetTransactionCount getTransactionCount() throws ExecutionException, InterruptedException {
//        EthGetTransactionCount result = new EthGetTransactionCount();
//        result = this.web3j.ethGetTransactionCount(DEFAULT_ADDRESS,
//                DefaultBlockParameter.valueOf("latest"))
//                .sendAsync()
//                .get();
//        return result;
//    }
//
//    public EthGetBalance getEthBalance() throws ExecutionException, InterruptedException {
//        EthGetBalance result = new EthGetBalance();
//        this.web3j.ethGetBalance(DEFAULT_ADDRESS,
//                DefaultBlockParameter.valueOf("latest"))
//                .sendAsync()
//                .get();
//        return result;
//    }
//
//
//
//    private void run() throws Exception {
//        // We start by creating a new web3j instance to connect to remote nodes on the network.
//
//        Web3j web3j = Web3j.build(new HttpService());
//        // Web3j web3j = new JsonRpc2_0Web3j(httpServiceProvider.getService(url));
//        log.info("Connected to Ethereum client version: "
//                + web3j.web3ClientVersion().send().getWeb3ClientVersion());
//        Credentials credentials =
//                WalletUtils.loadCredentials(
//                        "seed",
//                        "/home/lawshiqa/project1/blkchain/keystore/UTC--2019-02-21T09-39-23.093774268Z--e947eb0ede38da5b47c45ae6b8c09256b95012e0");
//        log.info("Credentials loaded");
//        log.info("Sending Ether");
//
//        TransactionReceipt transferReceipt = Transfer.sendFunds(//use the Transfer class for sending Ether, which takes care of the nonce management and polling(like recording) for a response for you
//                web3j, credentials,
//                "0x6e5b85cd00f9e92dec7f3d05e40f93b353ec2be7",  // you can put any address here
//                BigDecimal.valueOf(100), Convert.Unit.ETHER)  // 1 wei = 10^-18 Ether  -the amount of Ether you wish to send to the destination address
//                .sendAsync().get();
//        log.info("Transaction complete : "
//                + transferReceipt.getTransactionHash());
//    }
//}
//
//
//
