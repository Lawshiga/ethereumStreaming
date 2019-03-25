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
import org.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.util.Arrays;

public class blockNotification {
    private static final Logger log = LoggerFactory.getLogger(deployContract.class);

//    public static String contractAddr = "0xe947eb0ede38da5b47c45ae6b8c09256b95012e0";

    public static void main(String[] args) throws Exception {

        Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/

        //it listens the blocks created at the time of running the application
        //while mining is started only it is possible to mine for new block even it is empty

        SolidityFunctionWrapperGenerator.main(Arrays.asList(
                "src/main/solidity/hello/HelloWorld.bin", // binary file location
                "src/main/solidity/hello/HelloWorld.abi", // abi file location
                "-o", "src/main/java", // place to store generated wrapper
                "-p", "org.wso2.carbon.ethereumJ") // package name for generated class
                .toArray(new String[]{}));

       Credentials credentials = Credentials.create("B1BD85DB9104699BE78DBBC0F94B0ED531B410FF8E1A25B5ADBB0B29BE918DB1");

//        Credentials credentials =
//                WalletUtils.loadCredentials(
//                        "seed",
//                        "/home/lawshiqa/project1/blkchain/keystore/UTC--2019-02-21T09-39-23.093774268Z--e947eb0ede38da5b47c45ae6b8c09256b95012e0");
//        log.info("Credentials loaded with the address::::" + credentials.getAddress());
//
//        HelloWorld hello = HelloWorld.deploy(
//                web3, // configuration object
//                credentials, // account credentials
//                new BigInteger("30000000000"), // gas price
//                new BigInteger("2000000"), // gas limit
//                new BigInteger("2000")) // tree cost - parameter of our contract
//                .send();
//        String contractAddress = hello.getContractAddress();
//        log.info("The contract address is :::::::::::::::::" + contractAddress);

//        Subscription subscription = web3.blockObservable(false).subscribe(block -> {
//            System.out.println("Sweet, block number " + block.getBlock().getNumber() + " has just been created");
//        }, Throwable::printStackTrace);
//        TimeUnit.MINUTES.sleep(2);
//        subscription.unsubscribe();
    }

}
