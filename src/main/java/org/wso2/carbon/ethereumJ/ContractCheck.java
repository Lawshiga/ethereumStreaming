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
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetCode;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

public class ContractCheck {

    private static final Logger log = LoggerFactory.getLogger(ContractCheck.class);

    public static void main(String[] args) throws Exception {

        Web3j web3 = Web3j.build(new HttpService());

        Credentials credentials =
                WalletUtils.loadCredentials(
                        "seed",
                        "/home/lawshiqa/project1/blkchain/keystore/UTC--2019-02-21T09-39-23.093774268Z--e947eb0ede38da5b47c45ae6b8c09256b95012e0");
        log.info("Credentials loaded");

        String bytecode = "0x608060405234801561001057600080fd5b5060e68061001f6000396000f3fe6080604052600436106043576000357c01000000000000000000000000000000000000000000000000000000009004806360fe47b11460485780636d4ce63c14607f575b600080fd5b348015605357600080fd5b50607d60048036036020811015606857600080fd5b810190808035906020019092919050505060a7565b005b348015608a57600080fd5b50609160b1565b6040518082815260200191505060405180910390f35b8060008190555050565b6000805490509056fea165627a7a72305820b6132f85378716680efd8c3b2dcfa132fa6353cdd4afe1f98ecdf6f93ecc114b0029";

        String privateKeyGenerated = credentials.getEcKeyPair().getPrivateKey().toString(16);
        String publicKeyGenerated = credentials.getEcKeyPair().getPublicKey().toString(16);

        log.info("privatekey {}", privateKeyGenerated);
        log.info("publickey {}", publicKeyGenerated);


        EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(
                "0x28cb94808b862585bbeb99d9fad98084f21542108537423dab8dc500dcd2d2d2", DefaultBlockParameterName.LATEST).sendAsync().get();

        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

        log.info("nonce:::::::::::::::::::::::::::::" + nonce);

        Transaction contractTransaction = Transaction.createContractTransaction("credentials", nonce, new BigInteger("2000000"), "bytecode");


        log.info(web3.ethSendTransaction(contractTransaction).send().getTransactionHash());

        log.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::"+web3.netVersion().send().getNetVersion());

        EthGetCode ethGetCode = web3.ethGetCode("0x608060405234801561001057600080fd5b5060e68061001f6000396000f3fe6080604052600436106043576000357c01000000000000000000000000000000000000000000000000000000009004806360fe47b11460485780636d4ce63c14607f575b600080fd5b348015605357600080fd5b50607d60048036036020811015606857600080fd5b810190808035906020019092919050505060a7565b005b348015608a57600080fd5b50609160b1565b6040518082815260200191505060405180910390f35b8060008190555050565b6000805490509056fea165627a7a72305820b6132f85378716680efd8c3b2dcfa132fa6353cdd4afe1f98ecdf6f93ecc114b0029", DefaultBlockParameterName.LATEST).send();
        String s=ethGetCode.getCode();

        log.info(s);
    }

}
