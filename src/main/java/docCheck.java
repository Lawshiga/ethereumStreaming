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
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class docCheck {

    private static final Logger log = LoggerFactory.getLogger(createContractBytecode.class);

 //   private static final int COUNT = 1;


    public static void main(String[] args) throws Exception {

 //       CountDownLatch countDownLatch = new CountDownLatch(COUNT);

//       Web3j web3 = Web3j.build(new HttpService());
        Admin web3 = Admin.build(new HttpService());  // defaults to http://localhost:8545/
        PersonalUnlockAccount personalUnlockAccount = web3.personalUnlockAccount("0xe947eb0ede38da5b47c45ae6b8c09256b95012e0", "seed").send();
        if (personalUnlockAccount.accountUnlocked()) {
            log.info("Account unlocked:::::");
        }

//        Credentials credentials =
//                WalletUtils.loadCredentials(
//                        "seed",
//                        "/home/lawshiqa/project1/blkchain/keystore/UTC--2019-02-21T09-39-23.093774268Z--e947eb0ede38da5b47c45ae6b8c09256b95012e0");
//        log.info("Credentials loaded");


        String coinbase =  web3.ethCoinbase().send().getAddress();
        log.info("coinbase:::::::::::::::::::::::::::::::::::;" + web3.ethCoinbase().send().getAddress());

        String count =  web3.ethGetTransactionCount(web3.ethCoinbase().send().getAddress(), DefaultBlockParameter.valueOf("latest")).toString();
        log.info("count::::::::::::::::::::;;;" + count);

        BigInteger transactionCount = web3.ethGetTransactionCount(web3.ethCoinbase().send().getAddress(), DefaultBlockParameter.valueOf("latest")).send().getTransactionCount();
        log.info("Transaction count:::::::::::::::::::::::::::::::" + transactionCount);

        String gasprice =  web3.ethGasPrice().send().getGasPrice().toString();
        log.info("Gas price:::::::::::::::::::::;" + web3.ethGasPrice().send().getGasPrice());

//       log.info("Compiled code ::::::::::::::::::::::::::;"+ web3.ethCompileSolidity("pragma solidity ^0.5.1;\n" +
//                "\n" +
//                "contract HelloWorld {\n" +
//                "   uint256 counter = 5;\n" +
//                "   \n" +
//                "   function add() public {  //increases counter by 1\n" +
//                "       counter++;\n" +
//                "   }\n" +
//                " \n" +
//                "   function subtract() public { //decreases counter by 1\n" +
//                "       counter--;\n" +
//                "   }\n" +
//                "   \n" +
//                "   function getCounter() public view returns (uint256) {\n" +
//                "       return counter;\n" +
//                "   } \n" +
//                "\n" +
//                "}").send().getCompiledSolidity());
       //compilation is not working


//BigInteger nonceval = new BigInteger("count", 16);
//log.info("NOnce value :::::::::::::::::::::" + nonceval);

//        Transaction _contract = Transaction.createContractTransaction(coinbase, new BigInteger(nonce), new BigInteger(gasprice), "0x608060405234801561001057600080fd5b5060e68061001f6000396000f3fe6080604052600436106043576000357c01000000000000000000000000000000000000000000000000000000009004806360fe47b11460485780636d4ce63c14607f575b600080fd5b348015605357600080fd5b50607d60048036036020811015606857600080fd5b810190808035906020119092919050505060a7565b005b348015608a57600080fd5b50609160b1565b6040518082815260201191505060405180910390f35b8060008190555050565b6000805490509056fea165627a7a72305820b6132f85378716680efd8c3b2dcfa132fa6353cdd4afe1f98ecdf6f93ecc114b0029");
//        Transaction contract = Transaction.createContractTransaction(coinbase,web3.ethGetTransactionCount(web3.ethCoinbase().send().getAddress(), DefaultBlockParameter.valueOf("latest")).send().getTransactionCount(), new BigInteger("30000000000"), "0x608060405234801561001057600080fd5b5060e68061001f6000396000f3fe6080604052600436106043576000357c01000000000000000000000000000000000000000000000000000000009004806360fe47b11460485780636d4ce63c14607f575b600080fd5b348015605357600080fd5b50607d60048036036020811015606857600080fd5b810190808035906020119092919050505060a7565b005b348015608a57600080fd5b50609160b1565b6040518082815260201191505060405180910390f35b8060008190555050565b6000805490509056fea165627a7a72305820b6132f85378716680efd8c3b2dcfa132fa6353cdd4afe1f98ecdf6f93ecc114b0029");

        Transaction contract2 = Transaction.createContractTransaction("0xe947eb0ede38da5b47c45ae6b8c09256b95012e0", new BigInteger("288"), new BigInteger("30000000000"), "0x608060405234801561001057600080fd5b5060e68061001f6000396000f3fe6080604052600436106043576000357c01000000000000000000000000000000000000000000000000000000009004806360fe47b11460485780636d4ce63c14607f575b600080fd5b348015605357600080fd5b50607d60048036036020811015606857600080fd5b810190808035906020119092919050505060a7565b005b348015608a57600080fd5b50609160b1565b6040518082815260201191505060405180910390f35b8060008190555050565b6000805490509056fea165627a7a72305820b6132f85378716680efd8c3b2dcfa132fa6353cdd4afe1f98ecdf6f93ecc114b0029");

        log.info("contract" + contract2);

        EthSendTransaction response =
                web3.ethSendTransaction( contract2 ).sendAsync().get();
        log.info("Response for send transaction::::::::::::::::" + response);

        //full hash of the transaction created
        String hash = response.getTransactionHash();
        log.info("Hash of the response:::::::::::::::::" + hash);

        //have to write code to wait for transaction receipt

//        countDownLatch.await(5, TimeUnit.MINUTES);
     //   TimeUnit.MINUTES.sleep(1);

        EthGetTransactionReceipt transactionReceipt;


        while (true) {
            transactionReceipt = web3
                    .ethGetTransactionReceipt(hash)
                    .send();
            if (transactionReceipt.getResult() != null) {
                log.info("transaction receipt::::::::::::::::::::::;;" + transactionReceipt.getResult());
                break;
            }
            Thread.sleep(15000);
        }

//        web3.eth.waitForTransactionReceipt(hash);  have to find a way using this method

//        EthGetTransactionReceipt receipt = web3.ethGetTransactionReceipt(hash).send();
        log.info("transaction receipt::::::::::::::::::::::;;" + transactionReceipt.getResult());

        String contractAddress = transactionReceipt.getResult().getContractAddress();
        log.info("contract addrss:::::::::::::", transactionReceipt.getResult().getContractAddress());

        if (transactionReceipt.getTransactionReceipt().isPresent()) {
            String contractAddress1 = transactionReceipt.getResult().getContractAddress();
            log.info("Contract is deployed successfully!              contractAddrss::::::::::::::::::;;" + contractAddress1);
        } else {
            log.info("try again............");
        }

//        String contractAddress = receipt.get().getContractAddress();



        //EthGetTransactionReceipt transactionReceipt = web3.ethGetTransactionReceipt().send().getTransactionReceipt();


//
//       List<Integer> outputArg = new ArrayList();
//       outputArg.add("counter");
//
//        Function function = new Function(
//                "getCounter", null, outputArg);
//
//        String encodedFunction = FunctionEncoder.encode(function);
//
//        Transaction transaction = Transaction.createFunctionCallTransaction(
//                "0xe947eb0ede38da5b47c45ae6b8c09256b95012e0", new BigInteger("206"), new BigInteger("30000000000"), new BigInteger("2000000"), "contractAddress",   encodedFunction);
//
//        EthSendTransaction transactionResponse =
//                web3.ethSendTransaction(transaction).sendAsync().get();
//
//        String transactionHash = transactionResponse.getTransactionHash();
//        log.info("Function call transaction::::::::::::::::" + transactionHash);

// wait for response using EthGetTransactionReceipt...


//        List inputParams = new ArrayList();
//        List outputParams = new ArrayList();

//        (----------------------------------------------------working-----------------------------------------------------)


        String contractAddress1 = transactionReceipt.getResult().getContractAddress();


        Function function = new Function("addTree", Collections.emptyList(),  Arrays.asList(new TypeReference<DynamicBytes>(){}));
        String encodedFunction = FunctionEncoder.encode(function);

        log.info("Invoked the function:::::::::::::::::::::" + encodedFunction);


        BigInteger transactionCountx = web3.ethGetTransactionCount(web3.ethCoinbase().send().getAddress(), DefaultBlockParameter.valueOf("latest")).send().getTransactionCount();
        log.info("Transaction count:::::::::::::::::::::::::::::::" + transactionCountx);

        BigInteger nonce2 = BigInteger.valueOf(289);
        BigInteger gasprice2 = BigInteger.valueOf(300000000);
        BigInteger gaslimit2 = BigInteger.valueOf(2000000);

        //from: credentials.getAddress() can be used but not checked
        Transaction transaction = Transaction
                .createFunctionCallTransaction("0xe947eb0ede38da5b47c45ae6b8c09256b95012e0",
                        nonce2, gasprice2, gaslimit2, contractAddress1, encodedFunction);
log.info("transaction is going to be sent");

        EthSendTransaction transactionResponse = web3.ethSendTransaction(transaction).sendAsync().get();
        log.info("Transaction as function call::::::::::::::::::::::::" + transactionResponse);

        if (transactionResponse.hasError()) {
            throw new RuntimeException("Error processing transaction request: "
                    + transactionResponse.getError().getMessage());
        }

        String transactionHash = transactionResponse.getTransactionHash();
        System.out.println("hash--after:"+transactionHash);



        String transactionHash1 = transactionResponse.getTransactionHash();
        log.info("The transaction hash :::::::::::::::::::::::::::;" + transactionHash1);

        EthGetTransactionReceipt transactionReceipt2;


        while (true) {
            transactionReceipt2 = web3
                    .ethGetTransactionReceipt(transactionHash1)
                    .send();
            if (transactionReceipt2.getResult() != null) {
                log.info("transaction receipt::::::::::::::::::::::;;" + transactionReceipt2.getResult());
                break;
            }
            Thread.sleep(15000);
        }


        //      (--------------------------------------------------working-------------------------------------------------)



        //        <---------------------------Invoke function call like query-------------------------------------------->

        String contractAddress2 = transactionReceipt.getResult().getContractAddress();


        Function function2 = new Function("getCounter", Arrays.asList(new Utf8String("hello")),  Arrays.asList(new TypeReference<DynamicBytes>(){}));
        String encodedFunction2 = FunctionEncoder.encode(function2);

        log.info("Invoked the function:::::::::::::::::::::" + encodedFunction2);


        EthCall responseFunction = web3.ethCall( Transaction.createEthCallTransaction("0xe947eb0ede38da5b47c45ae6b8c09256b95012e0",
                         contractAddress2, encodedFunction2), DefaultBlockParameter.valueOf("latest")).sendAsync().get();


        BigInteger transactionCounty = web3.ethGetTransactionCount(web3.ethCoinbase().send().getAddress(), DefaultBlockParameter.valueOf("latest")).send().getTransactionCount();
        log.info("Transaction count:::::::::::::::::::::::::::::::" + transactionCounty);

        log.info("Eth call in invoked");


        List<Type> someTypes = FunctionReturnDecoder.decode(responseFunction.getValue(), function2.getOutputParameters());
        log.info("Decoded output :::::::::::::::::::::::::::::::" + someTypes);

        //        <---------------------------Invoke function call like query-------------------------------------------->




        //        <---------------------------Invoke function call with constructor-------------------------------------------->
//for constructor

//        String encodedConstructor =
//                FunctionEncoder.encodeConstructor(Arrays.asList(new Uint256(200)));//type and the initial value for the parameters within the constructor
//
//        //deploy contract
//
//        Transaction contract3 = Transaction.createContractTransaction("0xe947eb0ede38da5b47c45ae6b8c09256b95012e0", new BigInteger("231"), new BigInteger("30000000000"), "0x608060405234801561001057600080fd5b5060e68061001f6000396000f3fe6080604052600436106043576000357c01000000000000000000000000000000000000000000000000000000009004806360fe47b11460485780636d4ce63c14607f575b600080fd5b348015605357600080fd5b50607d60048036036020811015606857600080fd5b810190808035906020119092919050505060a7565b005b348015608a57600080fd5b50609160b1565b6040518082815260201191505060405180910390f35b8060008190555050565b6000805490509056fea165627a7a72305820b6132f85378716680efd8c3b2dcfa132fa6353cdd4afe1f98ecdf6f93ecc114b0029" + encodedConstructor);
//
//        EthSendTransaction response3 =
//                web3.ethSendTransaction( contract3 ).sendAsync().get();
//        log.info("Response for send transaction::::::::::::::::" + response3);
//
//        //full hash of the transaction created
//        String hash3 = response.getTransactionHash();
//        log.info("Hash of the response:::::::::::::::::" + hash3);
//
//        //have to write code to wait for transaction receipt
//
////        countDownLatch.await(5, TimeUnit.MINUTES);
//        //   TimeUnit.MINUTES.sleep(1);
//
//        EthGetTransactionReceipt transactionReceipt3;
//
//
//        while (true) {
//            transactionReceipt3 = web3
//                    .ethGetTransactionReceipt(hash3)
//                    .send();
//            if (transactionReceipt3.getResult() != null) {
//                log.info("transaction receipt::::::::::::::::::::::;;" + transactionReceipt3.getResult());
//                break;
//            }
//            Thread.sleep(15000);
//        }
//
////        web3.eth.waitForTransactionReceipt(hash);  have to find a way using this method
//
////        EthGetTransactionReceipt receipt = web3.ethGetTransactionReceipt(hash).send();
//        log.info("transaction receipt::::::::::::::::::::::;;" + transactionReceipt3.getResult());
//
//        String contractAddress3 = transactionReceipt3.getResult().getContractAddress();
//        log.info("contract addrss:::::::::::::", transactionReceipt3.getResult().getContractAddress());
//
//        if (transactionReceipt3.getTransactionReceipt().isPresent()) {
//            String contractAddress4 = transactionReceipt3.getResult().getContractAddress();
//            log.info("Contract is deployed successfully!              contractAddrss::::::::::::::::::;;" + contractAddress4);
//        } else {
//            log.info("try again............");
//        }
//
//        log.info("for invoke function using input parameter and output parameters:::::::::::::::::::::::;;");
//
//        Function function4 = new Function("getSponsorContribution", Arrays.asList(new Utf8String("0xe947eb0ede38da5b47c45ae6b8c09256b95012e0")),  Arrays.asList(new TypeReference<DynamicBytes>(){}));
//        String encodedFunction4 = FunctionEncoder.encode(function4);
//
//        log.info("Invoked the function:::::::::::::::::::::" + encodedFunction4);
        //        <---------------------------Invoke function call with constructor-------------------------------------------->
    }

}
