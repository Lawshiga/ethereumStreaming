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
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class checkConstructor {
    private static final Logger log = LoggerFactory.getLogger(createContractBytecode.class);


    public static void main(String[] args) throws Exception {

        Admin web3 = Admin.build(new HttpService());  // defaults to http://localhost:8545/

        PersonalUnlockAccount personalUnlockAccount = web3.personalUnlockAccount("0xe947eb0ede38da5b47c45ae6b8c09256b95012e0", "seed").send();
        if (personalUnlockAccount.accountUnlocked()) {
            log.info("Account unlocked:::::");
        }
        //        <---------------------------Invoke function call with constructor-------------------------------------------->
//for constructor

        String encodedConstructor =
                FunctionEncoder.encodeConstructor(Arrays.asList(new Uint256(200)));//type and the initial value for the parameters within the constructor

        //deploy contract

        Transaction contract3 = Transaction.createContractTransaction("0xe947eb0ede38da5b47c45ae6b8c09256b95012e0", new BigInteger("292"), new BigInteger("30000000000"), "0x608060405234801561001057600080fd5b5060e68061001f6000396000f3fe6080604052600436106043576000357c01000000000000000000000000000000000000000000000000000000009004806360fe47b11460485780636d4ce63c14607f575b600080fd5b348015605357600080fd5b50607d60048036036020811015606857600080fd5b810190808035906020119092919050505060a7565b005b348015608a57600080fd5b50609160b1565b6040518082815260201191505060405180910390f35b8060008190555050565b6000805490509056fea165627a7a72305820b6132f85378716680efd8c3b2dcfa132fa6353cdd4afe1f98ecdf6f93ecc114b0029" + encodedConstructor);

        EthSendTransaction response3 =
                web3.ethSendTransaction(contract3).sendAsync().get();
        log.info("Response for send transaction::::::::::::::::" + response3);

        //full hash of the transaction created
        String hash3 = response3.getTransactionHash();
        log.info("Hash of the response:::::::::::::::::" + hash3);

        // code to wait for transaction receipt

        EthGetTransactionReceipt transactionReceipt3;

        while (true) {
            transactionReceipt3 = web3
                    .ethGetTransactionReceipt(hash3)
                    .send();
            if (transactionReceipt3.getResult() != null) {
                log.info("transaction receipt::::::::::::::::::::::;;" + transactionReceipt3.getResult());
                break;
            }
            Thread.sleep(15000);
        }

//        web3.eth.waitForTransactionReceipt(hash);  have to find a way using this method

//        EthGetTransactionReceipt receipt = web3.ethGetTransactionReceipt(hash).send();
        log.info("transaction receipt::::::::::::::::::::::;;" + transactionReceipt3.getResult());

        String contractAddress3 = transactionReceipt3.getResult().getContractAddress();
        log.info("contract addrss:::::::::::::", transactionReceipt3.getResult().getContractAddress());

        if (transactionReceipt3.getTransactionReceipt().isPresent()) {
            String contractAddress4 = transactionReceipt3.getResult().getContractAddress();
            log.info("Contract is deployed successfully!              contractAddrss::::::::::::::::::;;" + contractAddress4);
        } else {
            log.info("try again............");
        }

        log.info("for invoke function using input parameter and output parameters:::::::::::::::::::::::;;");
        ArrayList<Type> dataParams = new ArrayList<>();

        String para1 = "Name";
        String para2 = "0xe947eb0ede38da5b47c45ae6b8c09256b95012e0";
        String para3 = "12";


        //       dataParams.add("para1");
 //       Utf8String name = new Utf8String("Name");

        dataParams.add(new Utf8String(para1));
        dataParams.add(new Address(para2));
//        dataParams.add(new Uint256(Long.parseLong(para3)));
        Function function4 = new Function("getSponsorContribution", dataParams, Arrays.asList(new TypeReference<DynamicBytes>() {
        }));
        String encodedFunction4 = FunctionEncoder.encode(function4);

        log.info("Invoked the function:::::::::::::::::::::" + encodedFunction4);

        BigInteger nonce2 = BigInteger.valueOf(293);
        BigInteger gasprice2 = BigInteger.valueOf(300000000);
        BigInteger gaslimit2 = BigInteger.valueOf(2000000);


        Transaction transaction = Transaction
                .createFunctionCallTransaction("0xe947eb0ede38da5b47c45ae6b8c09256b95012e0",
                        nonce2, gasprice2, gaslimit2, contractAddress3, encodedFunction4);
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



 //       List<Type> someTypes = FunctionReturnDecoder.decode(transaction.getValue(), function4.getOutputParameters());
//        log.info("Decoded output :::::::::::::::::::::::::::::::" + someTypes);
        //        <---------------------------Invoke function call with constructor-------------------------------------------->
    }

}
