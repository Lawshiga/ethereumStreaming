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
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.http.HttpService;
import rx.functions.Action1;

//not working --> don't have the idea of how it is going to push events to source
public class listenForSmartContractEvent {

    private static final Logger LOGGER = LoggerFactory.getLogger(listenForSmartContractEvent.class);
    public static String contractAddr = "0x38c3084b90bf8fdceca502977b1591deb9b7984b";

    public static void main(String[] args) {
        new listenForSmartContractEvent().run();
    }


    public  void run() {

        Web3j web3 = Web3j.build(new HttpService());

        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, contractAddr);
        web3.ethLogObservable(filter).subscribe(new Action1<Log>() {
            @Override
            public void call(Log log) {
                System.out.println("log.toString(): " + log.toString());
            }
        });
    }

}
