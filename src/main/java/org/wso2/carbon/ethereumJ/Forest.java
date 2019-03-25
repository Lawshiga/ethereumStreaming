package org.wso2.carbon.ethereumJ;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class Forest extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060405160208061033c8339810180604052602081101561003057600080fd5b505160005560038054600160a060020a031916331790556102e6806100566000396000f3fe60806040526004361061005b577c01000000000000000000000000000000000000000000000000000000006000350463a47f87958114610060578063f04961ba146100b6578063f3f58561146100e2578063fc7c082c146100ea575b600080fd5b34801561006c57600080fd5b506100a06004803603602081101561008357600080fd5b503573ffffffffffffffffffffffffffffffffffffffff16610111565b6040805160ff9092168252519081900360200190f35b3480156100c257600080fd5b506100e0600480360360208110156100d957600080fd5b50356101c2565b005b6100e0610225565b3480156100f657600080fd5b506100ff6102b4565b60408051918252519081900360200190f35b600080805b600254811015610172576001600060028381548110151561013357fe5b600091825260208083209091015473ffffffffffffffffffffffffffffffffffffffff1683528201929092526040019020549190910190600101610116565b508015156101845760009150506101bd565b73ffffffffffffffffffffffffffffffffffffffff831660009081526001602052604090205481906064028115156101b857fe5b049150505b919050565b60035473ffffffffffffffffffffffffffffffffffffffff1633146101e657600080fd5b30318111156101f457600080fd5b604051339082156108fc029083906000818181858888f19350505050158015610221573d6000803e3d6000fd5b5050565b600054341461023357600080fd5b33600090815260016020526040902054151561029957600280546001810182556000919091527f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace01805473ffffffffffffffffffffffffffffffffffffffff1916331790555b33600090815260016020819052604090912080549091019055565b6000548156fea165627a7a72305820258f676800406ccbb0cc673dfc69c1167e61f90e2c854a324546e95142c208480029";

    public static final String FUNC_GETSPONSORCONTRIBUTION = "getSponsorContribution";

    public static final String FUNC_GETETHER = "getEther";

    public static final String FUNC_ADDTREE = "addTree";

    public static final String FUNC_TREECOST = "treeCost";

    protected Forest(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Forest(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<BigInteger> getSponsorContribution(String sponsor) {
        final Function function = new Function(FUNC_GETSPONSORCONTRIBUTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(sponsor)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> getEther(BigInteger amount) {
        final Function function = new Function(
                FUNC_GETETHER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addTree(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_ADDTREE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<BigInteger> treeCost() {
        final Function function = new Function(FUNC_TREECOST, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<Forest> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _treeCost) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_treeCost)));
        return deployRemoteCall(Forest.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Forest> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _treeCost) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_treeCost)));
        return deployRemoteCall(Forest.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static Forest load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Forest(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Forest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Forest(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
