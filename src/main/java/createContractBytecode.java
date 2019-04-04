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
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

public class createContractBytecode {
    private static final Logger log = LoggerFactory.getLogger(createContractBytecode.class);

    public static void main(String[] args) throws Exception {
        Web3j web3 = Web3j.build(new HttpService());

        Credentials credentials =
                WalletUtils.loadCredentials(
                        "seed",
                        "/home/lawshiqa/project1/blkchain/keystore/UTC--2019-02-21T09-39-23.093774268Z--e947eb0ede38da5b47c45ae6b8c09256b95012e0");
        log.info("Credentials loaded");


       String bytecode = "0x608060405234801561001057600080fd5b5060e68061001f6000396000f3fe6080604052600436106043576000357c01000000000000000000000000000000000000000000000000000000009004806360fe47b11460485780636d4ce63c14607f575b600080fd5b348015605357600080fd5b50607d60048036036020811015606857600080fd5b810190808035906020019092919050505060a7565b005b348015608a57600080fd5b50609160b1565b6040518082815260200191505060405180910390f35b8060008190555050565b6000805490509056fea165627a7a72305820b6132f85378716680efd8c3b2dcfa132fa6353cdd4afe1f98ecdf6f93ecc114b0029";

        RawTransaction rawTransaction = RawTransaction.createContractTransaction(new BigInteger("0"), new BigInteger("30000000000"),new BigInteger("2000000") , new BigInteger("100"), "bytecode");

        System.out.println("rawTrasaction ::::::::::::::;;;" + rawTransaction);

        String transactionHash = web3.ethSendRawTransaction("rawTransaction").send().getTransactionHash();
        System.out.println("Transaction hash::::::::::::::::::::" + transactionHash);
        EthGetTransactionReceipt transactionReceipt =
                web3.ethGetTransactionReceipt(transactionHash).send();
       log.info("Transaction receipt is :::::::::::::::::::::" + transactionReceipt);

        if (transactionReceipt.getTransactionReceipt().isPresent()) {
            String contractAddress = transactionReceipt.getResult().getContractAddress();
            System.out.println("Contract address ::::::::::::::::" + contractAddress);
        } else {
            System.out.println("Try again");
        }


    }

}
