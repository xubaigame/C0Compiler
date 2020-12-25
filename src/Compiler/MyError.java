package Compiler;
public class MyError {
    public static boolean errFlag=false;
    public static void ShowErrMsg(int errCode,String errMsg){
        errFlag=true;
        switch (errCode){
            case 1:{showMessage(ErrorCode.ArithmeticException,errMsg);break;}
            case 2:{showMessage(ErrorCode.InputParamErrException,errMsg);break;}
            case 3:{showMessage(ErrorCode.VariableException,errMsg);break;}
            case 4:{showMessage(ErrorCode.FunctionException,errMsg);break;}
            default:{showMessage(ErrorCode.VariableException,errMsg);break;}
        }
    }
    private static void showMessage(ErrorCode errCode, String errMsg){
        System.err.println("Error-"+errCode.toString()+": "+errMsg);
        errFlag=true;
    }
}
