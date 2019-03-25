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
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

//draft --> send transaction(copy)

public class Application2 {
    private static final Logger log = LoggerFactory.getLogger(Application2.class);

    public static void main(String[] args) throws Exception {
        new Application2().run();
    }

    private void run() throws Exception {

        // We start by creating a new web3j instance to connect to remote nodes on the network.
        // Note: if using web3j Android, use Web3jFactory.build(...
        Web3j web3j = Web3j.build(new HttpService(
                "https://rinkeby.infura.io/v3/fb4a722dd605469b8c6d60da7668e5ce"));  // FIXME: Enter your Infura token here;
        log.info("Connected to Ethereum client version: "
                + web3j.web3ClientVersion().send().getWeb3ClientVersion());

        // We then need to load our Ethereum wallet file
        // FIXME: Generate a new wallet file using the web3j command line tools https://docs.web3j.io/command_line.html
        Credentials credentials =
                WalletUtils.loadCredentials(
                        "seed",
                        "/home/lawshiqa/.ethereum/testnet/keystore/newWallSam/UTC--2019-03-19T07-10-48.506000000Z--4d48b787582bbfa1aa4153f90ff7248880d500d9");
        log.info("Credentials loaded");

        // FIXME: Request some Ether for the Rinkeby test network at https://www.rinkeby.io/#faucet
        log.info("Sending 1 Wei ("
                + Convert.fromWei("1", Convert.Unit.ETHER).toPlainString() + " Ether)");
        TransactionReceipt transferReceipt = Transfer.sendFunds(
                web3j, credentials,
                "0x12c4c8152e03d1c5bae4ed5046e141729d773958",  // you can put any address here
                BigDecimal.ONE, Convert.Unit.WEI)  // 1 wei = 10^-18 Ether
                .send();
        log.info("Transaction complete, view it at https://rinkeby.etherscan.io/tx/"
                + transferReceipt.getTransactionHash());

        // Now lets deploy a smart contract
        log.info("Deploying smart contract");
        ContractGasProvider contractGasProvider = new DefaultGasProvider();

//        Greeter contract = Greeter.deploy(
//                web3j,
//                credentials,
//                contractGasProvider,
//                "test"
//        ).send();
//
//        String contractAddress = contract.getContractAddress();
//        log.info("Smart contract deployed to address " + contractAddress);
//        log.info("View contract at https://rinkeby.etherscan.io/address/" + contractAddress);
//
//        log.info("Value stored in remote smart contract: " + contract.greet().send());
//
//        // Lets modify the value in our smart contract
//        TransactionReceipt transactionReceipt = contract.newGreeting("Well hello again").send();
//
//        log.info("New value stored in remote smart contract: " + contract.greet().send());
//
//        // Events enable us to log specific events happening during the execution of our smart
//        // contract to the blockchain. Index events cannot be logged in their entirety.
//        // For Strings and arrays, the hash of values is provided, not the original value.
//        // For further information, refer to https://docs.web3j.io/filters.html#filters-and-events
//        for (Greeter.ModifiedEventResponse event : contract.getModifiedEvents(transactionReceipt)) {
//            log.info("Modify event fired, previous value: " + event.oldGreeting
//                    + ", new value: " + event.newGreeting);
//            log.info("Indexed event previous value: " + Numeric.toHexString(event.oldGreetingIdx)
//                    + ", new value: " + Numeric.toHexString(event.newGreetingIdx));
//        }
    }

}
