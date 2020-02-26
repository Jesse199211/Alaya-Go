package wasm.data_type;

import com.platon.rlp.datatypes.Uint8;
import network.platon.autotest.junit.annotations.DataSource;
import network.platon.autotest.junit.enums.DataSourceType;
import network.platon.contracts.wasm.BasicDataTypeContract;
import org.junit.Before;
import org.junit.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import wasm.beforetest.WASMContractPrepareTest;

/**
 * @title 测试基本类型
 * @description:
 * @author: qudong
 * @create: 2020/02/07
 */
public class BasicDataTypeTest extends WASMContractPrepareTest {

    private String uint8ByteValueStr;
    private String stringValueStr;
    private String stringValueStrLength;
    private String floatValueStr;


    @Before
    public void before() {
        uint8ByteValueStr = driverService.param.get("uint8ByteValueStr");
        stringValueStr = driverService.param.get("stringValueStr");
        stringValueStrLength = driverService.param.get("stringValueStrLength");
        floatValueStr = driverService.param.get("floatValueStr");

    }

    @Test
    @DataSource(type = DataSourceType.EXCEL, file = "test.xls", sheetName = "Sheet1",
            author = "qudong", showName = "wasm.basicDataTypeTest基本类型验证测试",sourcePrefix = "wasm")
    public void testBasicDataType() {

         //部署合约
        BasicDataTypeContract basicDataTypeContract = null;
        try {
            prepare();
            basicDataTypeContract = BasicDataTypeContract.deploy(web3j, transactionManager, provider).send();
            String contractAddress = basicDataTypeContract.getContractAddress();
            TransactionReceipt tx = basicDataTypeContract.getTransactionReceipt().get();
            collector.logStepPass("basicDataTypeContract issued successfully.contractAddress:" + contractAddress
                                  + ", hash:" + tx.getTransactionHash());
            collector.logStepPass("deployFinishCurrentBlockNumber:" + tx.getBlockNumber());
        } catch (Exception e) {
            collector.logStepFail("basicDataTypeContract deploy fail.", e.toString());
            e.printStackTrace();
        }
        //调用合约方法
        try {
            //1、验证:布尔值赋值
            Boolean boolValue = true;
            TransactionReceipt  transactionReceipt = basicDataTypeContract.set_bool(boolValue).send();
            collector.logStepPass("basicDataTypeContract 【验证布尔值赋值】 successfully hash:" + transactionReceipt.getTransactionHash());
            Boolean actualBoolValue = basicDataTypeContract.get_bool().send();
            collector.logStepPass("basicDataTypeContract 【验证布尔值取值】 执行getBool() successfully actualBoolValue:" + actualBoolValue);
            collector.assertEqual(actualBoolValue,boolValue, "checkout  execute success.");
            //2、验证：字节类型（byte）
            Uint8 uint8ByteValue = Uint8.of(uint8ByteValueStr);
            TransactionReceipt transactionReceipt1 = basicDataTypeContract.set_byte(uint8ByteValue).send();
            collector.logStepPass("basicDataTypeContract 【验证字节类型（byte）】 successfully hash:" + transactionReceipt1.getTransactionHash());
            Uint8 actualByteValue = basicDataTypeContract.get_byte().send();
            collector.logStepPass("basicDataTypeContract 【验证字节类型取值】 执行get_byte() successfully actualByteValue:" + actualByteValue);
            collector.assertEqual(actualByteValue,uint8ByteValue, "checkout  execute success.");
            //3、验证：字符串赋值
            TransactionReceipt transactionReceipt2 = basicDataTypeContract.set_string(stringValueStr).send();
            collector.logStepPass("basicDataTypeContract 【验证字符串赋值】 successfully hash:" + transactionReceipt2.getTransactionHash());
            String actualStringValue = basicDataTypeContract.get_string().send();
            collector.logStepPass("basicDataTypeContract 【验证字符串取值】 执行get_string() successfully actualStringValue:" + actualStringValue);
            collector.assertEqual(actualStringValue,stringValueStr, "checkout  execute success.");
            //4、验证：字符串长度
            Uint8 actualStringLength = basicDataTypeContract.get_string_length().send();
            collector.logStepPass("basicDataTypeContract 【验证字符串长度】 执行get_string_length() successfully actualStringLength:" + actualStringLength);
            collector.assertEqual(actualStringLength,Uint8.of(stringValueStrLength), "checkout  execute success.");
            //5、地址类型(Address)
             TransactionReceipt  transactionReceipt4 = basicDataTypeContract.set_address().send();
            collector.logStepPass("basicDataTypeContract 【验证地址类型(Address)】 successfully hash:" + transactionReceipt4.getTransactionHash());
            String actualAddreeValue = basicDataTypeContract.get_address().send();
            collector.logStepPass("basicDataTypeContract 【验证地址取值】 执行getString() successfully actualAddreeValue:" + actualAddreeValue);
            //collector.assertEqual(actualStringValue,expectStringValue, "checkout  execute success.")
            //6、浮点类型(float)
            //Float floatValue = 1.5f;
            Float floatValue = Float.parseFloat(floatValueStr);//-3.4E-38f
            TransactionReceipt  transactionReceipt5 = basicDataTypeContract.set_float(floatValue).send();
            collector.logStepPass("basicDataTypeContract 【验证浮点类型(float)】 successfully hash:" + transactionReceipt5.getTransactionHash());
            Float actualFloatValue = basicDataTypeContract.get_float().send();
            collector.logStepPass("basicDataTypeContract 【验证浮点类型(float)取值】 执行get_float() successfully actualFloatValue:" + actualFloatValue);
            collector.assertEqual(actualFloatValue,floatValue, "checkout  execute success.");
            //7、浮点类型(double)
           // Double doubleValue = 6.577;
            Double doubleValue = 2.4791E2;
            TransactionReceipt  transactionReceipt6 = basicDataTypeContract.set_double(doubleValue).send();
            collector.logStepPass("basicDataTypeContract 【验证浮点类型(double)】 successfully hash:" + transactionReceipt6.getTransactionHash());
            Double actualDoubleValue = basicDataTypeContract.get_double().send();
            collector.logStepPass("basicDataTypeContract 【验证浮点类型(double)取值】 执行get_double() successfully actualDoubleValue:" + actualDoubleValue);
            collector.assertEqual(actualDoubleValue,doubleValue, "checkout  execute success.");

        } catch (Exception e) {
            collector.logStepFail("basicDataTypeContract Calling Method fail.", e.toString());
            e.printStackTrace();
        }

    }
}
