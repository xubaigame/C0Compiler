package Execute;

public class Instruction {

    public Instruction(InstructionName instructionName,int arg0,int arg1)
    {
        this.instructionName=instructionName;
        t=arg0;
        a=arg1;
    }
    public Instruction()
    {

    }
    //指令类型
    public InstructionName instructionName;
    //变量使用与定义之间的层差
    public int t;
    //变量相对地址或常数值
    public int a;

    public String toString(){
        return instructionName.toString()+" "+t+" "+a;
    }
    public String toOutString(){
        return instructionName.toString()+"\t"+t+"\t"+a;
    }
}
