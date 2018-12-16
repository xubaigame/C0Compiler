package Execute;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Execute {

    //1层的函数基地址
    private int baseAddress=0;
    //当前执行的指令数
    private int currentIndex=0;

    //键盘输入流
    private Scanner scanner=new Scanner(System.in);
    //指令列表
    private List<Instruction> instructions=new ArrayList<>();
    //模拟运行栈
    private Stack<Integer> runStack=new Stack<>();

    public void start()
    {
        System.out.println("请输入解释执行文件地址:");
        if(readInstructionsFile(scanner.next()))
            execute();
        else
            MyError.ShowErrMsg(6,"程序运行结束!");

    }
    public void start(String SourcePath)
    {
        System.out.println("解释执行开始!");
        if(readInstructionsFile(SourcePath))
            execute();
        else
            MyError.ShowErrMsg(6,"程序运行结束!");
    }

    public boolean readInstructionsFile(String fileName)
    {
        boolean result=true;
        File file=new File(fileName);
        BufferedReader br=null;
        try{
            System.out.println("开始读取指令");
            br=new BufferedReader(new FileReader(file));
            String tempString;
            int line=1;
            while((tempString=br.readLine())!=null)
            {
                if(!tempString.equals("")){
                    String[] str = tempString.split(" ");
                    Instruction instruction=new Instruction();
                    for(InstructionName e: InstructionName.values()){
                        if(e.toString().equals(str[0])){
                            instruction.instructionName = e;
                            break;
                        }
                    }
                    try{
                        instruction.t = Integer.parseInt(str[1]);
                        instruction.a = Integer.parseInt(str[2]);
                    }
                    catch (Exception e)
                    {
                        MyError.ShowErrMsg(6,"参数异常,请检查指令格式!");
                        result=false;
                        return result;
                    }
                    instructions.add(instruction);
                    System.out.println("line "+line+":"+tempString);
                    line++;
                }
            }
            System.out.println("指令读取完毕,请输入参数:");
        }
        catch (IOException e) {
            MyError.ShowErrMsg(2,"找不到目标文件!");
            result=false;
            return result;
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e1) {
                    System.out.print("文档关闭失败");
                }
            }
        }
        return result;
    }

    public void execute()
    {
        while(currentIndex<instructions.size()){
            if(!MyError.errFlag){
                execute_word(currentIndex);
                currentIndex++;
            }else {
                return;
            }
        }
        System.out.println("解释执行完毕！");
    }

    public void execute_word(int index)
    {
        int t = instructions.get(index).t;
        int a = instructions.get(index).a;
        switch (instructions.get(index).instructionName)
        {
            case LIT : LIT(t,a); break;
            case LOD : LOD(t,a); break;
            case STO : STO(t,a); break;
            case CAL : CAL(t,a); break;
            case INT : INT(t,a); break;
            case JMP : JMP(t,a); break;
            case JPC : JPC(t,a); break;
            case ADD : ADD(t,a); break;
            case SUB : SUB(t,a); break;
            case MUL : MUL(t,a); break;
            case DIV : DIV(t,a); break;
            case RED : RED(t,a); break;
            case WRT : WRT(t,a); break;
            case RET : RET(t,a); break;
        }
    }
    /**
     *
     * @param t 0
     * @param a 空间大小
     * 在栈中开辟a个int空间
     */
    private void INT(int t,int a)
    {
        for(int i=0;i<a;i++){
            runStack.push(0);
            if(runStack.size()>=500){
                //System.out.println("栈满500");
                MyError.ShowErrMsg(4,"所在行数为:第"+currentIndex+"行");
                return;
            }
        }
    }

    /**
     *
     * @param t 0
     * @param a 常数值
     * 将常数a提至栈顶
     */
    private void LIT(int t,int a)
    {
        runStack.push(a);
    }

    /**
     *
     * @param t 层差
     * @param a 相对地址
     */
    private void LOD(int t,int a)
    {
        //同层变量,位置为 基地址+相对地址
        if(t==0)
        {
            runStack.push(runStack.get(baseAddress+a));
        }
        //外层变量,位置为 0+相对地址
        else
        {
            runStack.push(runStack.get(a));
        }
    }

    /**
     *
     * @param t 层差
     * @param a 相对地址
     * 将栈顶的值存入相对地址为a的变量中
     */
    private void STO(int t,int a)
    {
        //同层变量,位置为 基地址+相对地址
        if(t==0)
        {
            runStack.set(baseAddress + a, runStack.pop());
        }
        //外层变量,位置为 0+相对地址
        else
        {
            runStack.set(a, runStack.pop());
        }
    }

    private void CAL(int t,int a){
        int base = runStack.size();
        runStack.push(baseAddress);
        runStack.push(currentIndex+1);
        //更新当前层数基地址
        //更新当前执行指令的标号
        baseAddress = base;
        currentIndex = a - 1;
    }

    private void JMP(int t, int a){
        currentIndex = a - 1;
    }

    private void JPC(int t, int a){
        if(runStack.peek()==0){
            currentIndex = a - 1;
        }
    }

    /**
     * ADD 0 0
     * 次栈顶与栈顶相加，退两个栈元素，结果值进栈
     * @param t 0
     * @param a 0
     */
    private void ADD(int t, int a){
        int val_a = runStack.pop();
        int val_b = runStack.pop();
        runStack.push(val_a+val_b);
    }
    /**
     * SUB 0 0
     * 次栈顶减去栈顶，退两个栈元素，结果值进栈
     * @param t 0
     * @param a 0
     */
    private void SUB(int t, int a){
        int val_a = runStack.pop();
        int val_b = runStack.pop();
        runStack.push( val_b-val_a);
    }

    /**
     * MUL 0 0
     * 次栈顶乘以栈顶，退两个栈元素，结果值进栈
     * @param t 0
     * @param a 0
     */
    private void MUL(int t, int a){
        int val_a = runStack.pop();
        int val_b = runStack.pop();
        runStack.push(val_b*val_a);
    }
    /**
     * DIV
     * 次栈顶除以栈顶，退两个栈元素，结果值进栈
     * @param t 0
     * @param a 0
     */
    private void DIV(int t, int a){
        int val_a = runStack.pop();
        if(val_a==0){
            MyError.ShowErrMsg(1,"所在行数为:第"+currentIndex+"行");
            return;
        }
        int val_b = runStack.pop();
        runStack.push( val_b/val_a);
    }

    /**
     * RED 0 0
     * 从命令行读入一个输入置于栈顶
     * @param t 0
     * @param a 0
     */
    private void RED(int t, int a){

        runStack.push(scanner.nextInt());
    }

    /**
     * wrt 0 0
     * 栈顶值输出至屏幕并换行
     * @param t 0
     * @param a 0
     */
    private void WRT(int t, int a){
        if(runStack.empty()){
            //System.out.println("读入栈失败，栈为空");
            MyError.ShowErrMsg(4,"所在行数为:第"+currentIndex+"行");
            return;
        }else{
            System.out.println(runStack.peek());
        }
    }

    /**
     * ret 0 0
     * 函数调用结束后,返回调用点并退栈
     * 如果当前为0层，则运行结束
     * @param t 0
     * @param a 0
     */
    private void RET(int t, int a){
        if(baseAddress==0){
            currentIndex = 999999;
            return;
        }
        //将要return的值存在一个临时变量中
        //把当前层所有都退栈
        //再把return的值放在栈顶
        int returnValue = runStack.peek();
        currentIndex = runStack.get(baseAddress+1)-1;
        int baseAddressTemp = baseAddress;
        baseAddress = runStack.get(baseAddress);
        while(runStack.size()>baseAddressTemp){
            runStack.pop();
        }
        runStack.push(returnValue);
    }
}
