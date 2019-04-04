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
import org.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.Arrays;

public class deployWill {

    private static final Logger log = LoggerFactory.getLogger(deployWill.class);

    public static void main(String[] args) throws Exception {

        Web3j web3 = Web3j.build(new HttpService());

        SolidityFunctionWrapperGenerator.main(Arrays.asList(
                "src/main/solidity/will/Will.bin", // binary file location
                "src/main/solidity/will/Will.abi", // abi file location
                "-o", "src/main/java", // place to store generated wrapper
                "-p", "org.wso2.carbon.ethereumJ") // package name for generated class
                .toArray(new String[]{}));

//        Credentials credentials = Credentials.create("516AA44178D4E2EDA879C80F7FC1D843C788D74988E36AC47F5FE7F119CDADA6");
        Credentials credentials = WalletUtils.loadCredentials("seed", "/home/lawshiqa/project1/blkchain/keystore/UTC--2019-02-21T09-39-23.093774268Z--e947eb0ede38da5b47c45ae6b8c09256b95012e0");


        Will will = Will.deploy(
                web3, // configuration object
                credentials, // account credentials
                new BigInteger("30000000000"), // gas price
                new BigInteger("2000000"), // gas limit
                new BigInteger("2000")) // tree cost - parameter of our contract
                .send();
        String contractAddr = will.getContractAddress();
        //new added till this

        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, contractAddr);

        log.info("getting filter   :", filter.getTopics());
        log.info("Inheritance is set for an account: " + will.setInheritance("0x6e5b85cd00f9e92dec7f3d05e40f93b353ec2be7", new BigInteger("100")));
        log.info("Inheritance is set for an account: " + will.decesed());
        log.info("getting filter   :", filter.getTopics());
    }

}
