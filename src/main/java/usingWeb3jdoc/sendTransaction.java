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
package usingWeb3jdoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

public class sendTransaction {

    private static final Logger log = LoggerFactory.getLogger(sendTransaction.class);

    public static void main(String[] args) throws IOException {

        Web3j web3 = Web3j.build(new HttpService());

        Request<?, EthGetTransactionCount> count = web3.ethGetTransactionCount("0xe947eb0ede38da5b47c45ae6b8c09256b95012e0", DefaultBlockParameter.valueOf("lates"));

        BigInteger nonce = count.send().getTransactionCount();

        RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(nonce, new BigInteger(""), new BigInteger(""), "0x6e5b85cd00f9e92dec7f3d05e40f93b353ec2be7", new BigInteger("100"));
    }


}
