package Execute;

public class MyError {
    public static enum ErrCode{
        ArithmeticException,//算数异常
        InputParamErrException,//输入参数错误
        AskMemoryTooBigException,//申请内存空间太大
        CrossBroderException,//越界
        StackOverflowException,//栈溢出
        InstructionExcrption, //指令格式错误
        UnknowException         //未知错误
    }
    public static boolean errFlag=false;

    public static void ShowErrMsg(int errCode,String errMsg){
        switch (errCode){
            case 1:{System.err.println("错误信息:"+ ErrCode.ArithmeticException.toString()+" "+errMsg);errFlag=true;break;}
            case 2:{System.err.println("错误信息:"+ ErrCode.InputParamErrException.toString()+" "+errMsg);errFlag=true;break;}
            case 3:{System.err.println("错误信息:"+ ErrCode.AskMemoryTooBigException.toString()+" "+errMsg);errFlag=true;break;}
            case 4:{System.err.println("错误信息:"+ ErrCode.CrossBroderException.toString()+" "+errMsg);errFlag=true;break;}
            case 5:{System.err.println("错误信息:"+ ErrCode.StackOverflowException.toString()+" "+errMsg);errFlag=true;break;}
            case 6:{System.err.println("错误信息:"+ ErrCode.InstructionExcrption.toString()+" "+errMsg);errFlag=true;break;}
            case 7:{System.err.println("错误信息:"+ ErrCode.UnknowException.toString()+" "+errMsg);errFlag=true;break;}
            default:{}
        }
    }
}
