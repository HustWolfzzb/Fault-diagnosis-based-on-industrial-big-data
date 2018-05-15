/* *********************
 * Author   :   HustWolf --- 张照博

 * Time     :   2018.1-2018.5

 * Address  :   HUST

 * Version  :   2.0

 * 人机交互界面
 ********************* */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.NumberFormat;
import java.util.Vector;
import java.util.HashMap;
import java.util.Map;

/*
   8  * GUI(图形用户界面)
   9  *  Graphical User Interface(图形用户接口)
   10  *  用图形的方式,来显示计算机操作的界面,这样更方便更直观.
   11  *
   12  * CLI
   13  *  Command Line User Interface(命令行用户接口)
   14  *  就是常用的Dos命令行操作.
   15  *  需要记忆一些常用的命令.操作更直观.
   16  *
   17  * 举例:
   18  *   比如:创建文件夹,或者删除文件夹等
   19  *   md haha   del haha
   20  *
   21  *
   22  * Java的GUI提供的对象都存在 java.Awt 和 javax.Swing 两个包中.
   23  *
   24  * java.Awt:Abstract Window ToolKit(抽象 窗口工具包)
   25  *    需要调用本地系统方法实现功能.属重量级控件 (跨平台不够强)
   26  *
   27  * java.Swing:在AWT的基础上,建立的一套图形界面系统,其中提供了更多的组件,
   28  *   而且完全由java实现,增强了移植性,属于轻量级控件.(跨平台很好)
   29  *
   30  * java.swt: IBM 公司开发 Eclipse 用的组件工具 可以Eclipse网站下载后就可以使用了.
   31  *
   32  *
   33  * 布局管理器
   34  * 1)容器中的组件的排放方式,就是布局.
   35  * 2)常见的布局管理器
   36  *   FlowLayout(流式布局管理器)
   37  *     从左到右的顺序排列
   38  *     Panel默认的布局管理器
   39  *   BorderLayout(便捷布局管理器)
   40  *     东  南  西  北   中
   41  *     Frame 默认的布局管理器
   42  *     不指定布局方式,默认 满屏覆盖,在添加一个 也是 满屏覆盖
   43  *   GridLayout (网格布局管理器)
   44  *     规则的矩阵
   45  *   CardLayout  (卡片布局管理器)
   46  *     选项卡
   47  *   GridBagLayout(网格包布局管理器)
   48  *    非规则的矩阵
   49  *
   50  * 事件监听机制组成
   51  *  事件源:
   52  *  事件:Event
   53  *  监听器:Listener
   54  *  时间处理:(引发事件后处理方式)
   55  *
   56  *  事件源:就是awt包或者swing包中的那些图像界面组件.
   57  *  事件:每个事件源都有自己特定的对应时间和共性时间.
   58  *  监听器:可以出发某一个事件的动作都已经封装到监听器中.
   59  
*/

class MyWin extends WindowAdapter{
    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Bye Bye!");
        JOptionPane.showInternalMessageDialog(null, "非法输入字符", "Error", JOptionPane.WARNING_MESSAGE);
        JOptionPane.showMessageDialog(null," Welcome for Your Next Time!","MESSAGE FROM ZZB",JOptionPane.WARNING_MESSAGE);
        System.exit(0);
    }
    @Override
    public void windowActivated(WindowEvent e) {
        //每次获得焦点 就会触发
        System.out.println("Welcome Back!");
    }
    @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub
        System.out.println("Now It is Working!");
        JOptionPane.showMessageDialog(null,"Welcome To Here!\n【clear】: Clear the Screen!\n【next 】：The Next Line！\n【exit 】： Exit the System!\nFor More Option, input \"HELP\"","MESSAGE FROM ZZB",JOptionPane.WARNING_MESSAGE);
    }
}

public class GUI{
    public float RightCount = 0;
    public float FaultCount = 0;
    private Object tree;
    private Frame f;
    private int nextTimes=0;
    private String[] Test_Names = new String[] {"Sensor1","Sensor2","Sensor3","Sensor4", "Load"};

    private Button but,but1,but2;
    private TextField ta;
    private MenuBar mb;
    private Menu m,subm,Run;
    private MenuItem closeItem,openItem,saveItem,subItem1,subItem;
    private FileDialog openDialog,saveDialog;
    private File file;
    private JPanel jp1,jp2,jp3,jp4,jp5,jp6,jp7,jp8,jp9,jp10,jp11,jp12,jp13,jp14;
    private JLabel jl1,jl2,jl3,jl4,jl5,jl6,jl7,jl8,jl9,jl10,jl11,jl12;
    private String[] TEXT =  new String[11];
    private static  String[] LINES;
    public GUI() {
        init();
    }
    public static int line=0;
    public Vector<String > TData = new Vector<String>();
    public static String Space = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t";
    private void addLine(){
        if (line<=LINES.length/10)
            line++;
        else{
            line = 0;
        }
    }
    private void subLine(){
        if (line>0)
            line--;
        else{
            line = 0;
        }
    }
    private void autoTest(){
        for (int i=0;i<TData.size();++i) {
            Object[] test = TData.get(i).split(" ");
            String res="";
            res=TestData.TestData(tree, Test_Names,test,res);
            if (res.contains(":")){
                //三套方案！************
//                RightCount++;
                //三套方案！************
                //二套方案！************
//                        Fault = Fault.trim();
//                        if (Fault.equals("Other_Faults") && FaultMap.get(Fault).equals((String) test[test.length-1])){
//                            RightCount++;
//                        }
//                        else {
//                            RightCount++;
//                        }
                //二套方案！************
                //一套方案！************
                if((res.contains("1") && (test[test.length - 1]).equals("1.0")) || (res.contains("0") && ((String)test[test.length - 1]).equals("0.0"))){
                    RightCount++;
                }
                else {
                    FaultCount++;
                }
                //一套方案！************
            }
            else {
                FaultCount++;
            }
        }
    }
    public static void updateTEXT(GUI obj,String[] txt,Object tree1){
        LINES = new String[txt.length];
        for (int i=0;i<txt.length;++i){
            obj.LINES[i]=txt[i];
        }
        obj.tree=tree1;
    }

    public void updateDisplay(){
        if(line<LINES.length/10) {
            for (int i = line * 10, j = 1; i < line * 10 + 10; i++) {
                TEXT[j++] =Space+""+ LINES[i];
            }
        }else{
            for(int i=1;i<LINES.length-(LINES.length/10)*10;++i){
                TEXT[i] = Space+""+LINES[(LINES.length/10)*10+i];
            }
            for (int i=LINES.length-(LINES.length/10)*10;i<11;++i){
                TEXT[i]=Space+"|||||||||||||||||===========》》》》》》DONE!";
            }
        }
        System.out.println(line);
        jl2.setText(TEXT[1]);
        jl3.setText(TEXT[2]);
        jl4.setText(TEXT[3]);
        jl5.setText(TEXT[4]);
        jl6.setText(TEXT[5]);
        jl7.setText(TEXT[6]);
        jl8.setText(TEXT[7]);
        jl9.setText(TEXT[8]);
        jl10.setText(TEXT[9]);
        jl11.setText(TEXT[10]);
    }
    private void openFile(){
        openDialog.setVisible(true);
        String dirPath=openDialog.getDirectory();
        String fileName=openDialog.getFile();
        System.out.println(dirPath+"...."+fileName);
        if(dirPath==null || fileName==null)
            return;
        file=new File(dirPath,fileName);
        try {
            BufferedReader bufr=new BufferedReader(new FileReader(file));
            String line=null;
            while((line=bufr.readLine())!=null){
                TData.add(line);
            }
            bufr.close();
        } catch (IOException e2) {
            throw new RuntimeException("open the Exception");
        }
    }
    private void dealCommand(String command){
        if (command.isEmpty()){
            System.out.println("呵呵哒~~~");
        }
        if (command.toLowerCase().equals("exit")){
            System.exit(0);
        }
        if (command.toLowerCase().equals("help")){
            JOptionPane.showMessageDialog(null,"【clear】: Clear the Screen!\n【test】: Test your DATA!\n【load】:Load the File Choosed\n【autoload】：Load the default testdata\n【autotest】：test all Test_Data\n【last】：The Last Line\n【next】：The Next Line\n【help】：Show All Options\n【save】：Save the DecisionTree as '.txt' File\n【exit】： Exit the System","**** Command Option ****",JOptionPane.WARNING_MESSAGE);
        }
        if(command.toLowerCase().equals("clear")){
            System.out.println("Down, Clear ALL!");
            line=0;
            jl2.setText(Space+"Line 1");
            jl3.setText(Space+"Line 2");
            jl4.setText(Space+"Line 3");
            jl5.setText(Space+"Line 4");
            jl6.setText(Space+"Line 5");
            jl7.setText(Space+"Line 6");
            jl8.setText(Space+"Line 7");
            jl9.setText(Space+"Line 8");
            jl10.setText(Space+"Line 9");
            jl11.setText(Space+"Line 10");
            jl12.setText("");
        }
        else if(command.toLowerCase().equals("next")){
            addLine();
            jl12.setText("");
            updateDisplay();
        }
        else if(command.toLowerCase().equals("last")){
            subLine();
            jl12.setText("");
            updateDisplay();
        }
        else if(command.toLowerCase().equals("load")){
            openFile();
        }
        else if (command.toLowerCase().equals("autoload")){
            file=new File("DataToTest.txt");
            try {
                BufferedReader tdata=new BufferedReader(new FileReader(file));
                String line=null;
                while((line=tdata.readLine())!=null){
                    TData.add(line);
                }
                tdata.close();
                jl12.setText(Space+"Auto Load Done! Now you can test data to check the model!");
            } catch (IOException e2) {
                System.out.println(e2);
            }
        }
        else if(command.toLowerCase().equals("autotest")){
            if (TData.isEmpty()){
                jl12.setText(Space+"Please Open the Test File to load the Data! Or input autoload to load the data！");
                return;
            }
            else {
                autoTest();
                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setMaximumFractionDigits(1);
                System.out.println(RightCount+" "+FaultCount);
                try{
                    float acc1 = RightCount/(RightCount + FaultCount)*100;
                    jl12.setText(Space+Space+"Please Wait For Calculate!");
                    jl12.setText(Space+"决策树(Descision Tree) Model 准确率： "+Float.parseFloat(nf.format(acc1)) + "%     支持向量机(SVM) Model 准确率为：  " + Float.parseFloat(nf.format(ZZB_SVM.main()))+"%");
                }catch (IOException e){
                    System.out.println("这他么都能给报错？我不信！!");
                }
                RightCount = 0;
                FaultCount = 0;
            }
        }
        else{
            String[] comm = command.split(" ");
            if (comm[0].toLowerCase().equals("test") && comm.length<2) {
                System.out.println("Test Ready NOW!");
                Object[] test;
                if(TData.isEmpty()) {
                    test = new Object[]{"-3.0", "2.0", "2.0", "1.0", "0.0" };
                }
                else {
                    if (nextTimes<TData.size()) {
                        test = TData.get(nextTimes).split(" ");
                        nextTimes++;
                    }
                    else {
                        test = TData.get(TData.size()-1).split(" ");
                    }
                }
                String res="";
                res=TestData.TestData(tree, Test_Names,test,res);
                String tdata = "";
                for (int i=0;i<test.length;++i){
                    tdata =tdata + test[i] + " | ";
                }
                jl12.setText(Space+tdata+" "+res);
            }

            else if(comm[0].toLowerCase().equals("test") && comm.length-1==Test_Names.length){
                Object[] test = new Object[Test_Names.length];
                for (int i=0;i<test.length;++i){
                    test[i]=comm[i+1];
                }
                String res="";
                res=TestData.TestData(tree, Test_Names,test,res);
                jl12.setText(Space+res);
            }
//            else if(comm[0].toLowerCase().equals("setrate")){
//                Parameter.setRate(Integer.valueOf(comm[2]));
//                try{
//                    ZZB_JCS.main(Test_Names);
//                }catch (Exception e){
//                    System.out.println("HEHEHE！");
//                }
//                System.exit(0);
//            }
        }
    }
    private void init(){
        f=new Frame("The Graduation Design Windows form Zhang Zhaobo for Teachers!");
        f.setBounds(300, 100, 800, 600);
        f.setLayout(new GridLayout(14,1));
        ta=new TextField(50);
        mb=new MenuBar();
        m=new Menu("File");
        closeItem=new MenuItem("Exit");
        openItem=new MenuItem("Open");
        saveItem=new MenuItem("Save");
        subm=new Menu("New");
        subItem1=new MenuItem("Web Project");
        subItem=new MenuItem("Java Project");
        subm.add(subItem);
        subm.add(subItem1);
        m.add(subm);
        m.add(openItem);
        m.add(saveItem);
        m.add(closeItem);
        Run = new Menu("Run");
        mb.add(m);
        mb.add(Run);
        but = new Button("Execute !");
        openDialog=new FileDialog(f,"I wanna to open",FileDialog.LOAD);
        saveDialog=new FileDialog(f,"I wanna to save",FileDialog.SAVE);
        f.setMenuBar(mb);
        jp1 = new JPanel();
        jl1 = new JLabel("This is the Code Line for Command!");
        jl1.setSize(300,40);
        jp1.add(jl1);
        f.add(jp1);

        jp2 = new JPanel();
        jp2.add(ta);
        jp2.add(but);
        f.add(jp2);

        jp3 = new JPanel();
        jl2 = new JLabel(Space+"Line 1",SwingConstants.LEFT);
        jp3.add(jl2);
        jp3.setLayout(new FlowLayout(FlowLayout.LEFT));
        f.add(jp3);

        jp4 = new JPanel();
        jl3 = new JLabel(Space+"Line 2",SwingConstants.LEFT);
        jp4.add(jl3);
        jp4.setLayout(new FlowLayout(FlowLayout.LEFT));
        f.add(jp4);

        jp5 = new JPanel();
        jl4 = new JLabel(Space+"Line 3",SwingConstants.LEFT);
        jp5.add(jl4);
        jp5.setLayout(new FlowLayout(FlowLayout.LEFT));
        f.add(jp5);

        jp6 = new JPanel();
        jl5 = new JLabel(Space+"Line 4",SwingConstants.LEFT);
        jp6.add(jl5);
        jp6.setLayout(new FlowLayout(FlowLayout.LEFT));
        f.add(jp6);

        jp7 = new JPanel();
        jl6 = new JLabel(Space+"Line 5",SwingConstants.LEFT);
        jp7.add(jl6);
        jp7.setLayout(new FlowLayout(FlowLayout.LEFT));
        f.add(jp7);

        jp8 = new JPanel();
        jl7 = new JLabel(Space+"Line 6",SwingConstants.LEFT);
        jp8.add(jl7);
        jp8.setLayout(new FlowLayout(FlowLayout.LEFT));
        f.add(jp8);

        jp9 = new JPanel();
        jl8 = new JLabel(Space+"Line 7",SwingConstants.LEFT);
        jp9.add(jl8);
        jp9.setLayout(new FlowLayout(FlowLayout.LEFT));
        f.add(jp9);

        jp10 = new JPanel();
        jl9 = new JLabel(Space+"Line 8",SwingConstants.LEFT);
        jp10.add(jl9);
        jp10.setLayout(new FlowLayout(FlowLayout.LEFT));
        f.add(jp10);

        jp11 = new JPanel();
        jl10 = new JLabel(Space+"Line 9",SwingConstants.LEFT);
        jp11.add(jl10);
        jp11.setLayout(new FlowLayout(FlowLayout.LEFT));
        f.add(jp11);

        jp12 = new JPanel();
        jl11 = new JLabel(Space+"Line 10",SwingConstants.LEFT);
        jp12.add(jl11);
        jp12.setLayout(new FlowLayout(FlowLayout.LEFT));
        f.add(jp12);

        jp13 = new JPanel();
        jl12 = new JLabel("");
        jp13.add(jl12);
        jp13.setLayout(new FlowLayout(FlowLayout.LEFT));
        f.add(jp13);

        jp14 = new JPanel();
        but1 = new Button("CLEAR");
        but2 = new Button("NEXT");
        jp14.add(but1);
        jp14.add(but2);
        f.add(jp14);

        f.addWindowListener(new MyWin());
        event();
        f.setVisible(true);
        f.setResizable(true);
    }

    private void event(){
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // TODO Auto-generated method stub
                System.exit(0);

            }
        });
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(file==null){
                    saveDialog.setVisible(true);
                    String dirPath=saveDialog.getDirectory();
                    String fileName=saveDialog.getFile();
                    if(dirPath==null || fileName==null)
                        return;
                    file=new File(dirPath,fileName);
                }
                try {
                    BufferedWriter bufw = new BufferedWriter(new FileWriter(file));
                    int i = 0;
                    while (i < LINES.length){
                        if(LINES[i]!=null)
                            bufw.write(LINES[i]+"\n");
                        i++;
                    }
                    bufw.close();
                } catch (IOException e2) {
                    throw new RuntimeException("Failed to Save !");
                }
            }
        });
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                openFile();
            }
        });
        closeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                System.exit(0);
            }
        });

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }

        });

        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                System.out.println("Put Down the Button to Execute the Command!");
                dealCommand(ta.getText());
            }
        });
        but.addMouseListener(new MouseAdapter() {
            private int count=0;
            private int clickCount=1;
            public void mouseEntered(MouseEvent e){
                System.out.println("Entered The EXECUTE BUTTON!"+count++);
            }
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount()==2){
                    System.out.println("Double Click!");
                }else
                    System.out.println("Click : "+clickCount++);
            }
        });
        ta.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    System.out.println("Put Down the Enter to Execute the Command!");
                    dealCommand(ta.getText());
                }
            }
        });
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                System.out.println("Put Down the Button1 to Clear the Data!");
                line=0;
                jl2.setText(Space+"Line 1");
                jl3.setText(Space+"Line 2");
                jl4.setText(Space+"Line 3");
                jl5.setText(Space+"Line 4");
                jl6.setText(Space+"Line 5");
                jl7.setText(Space+"Line 6");
                jl8.setText(Space+"Line 7");
                jl9.setText(Space+"Line 8");
                jl10.setText(Space+"Line 9");
                jl11.setText(Space+"Line 10");
            }
        });
        but2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                System.out.println("Put Down the Button2 to Change the Data!");
                updateDisplay();
            }
        });
        but2.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e){
                System.out.println(KeyEvent.getKeyText(e.getKeyCode())+" ******>>>> "+e.getKeyCode());
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    System.out.println("Put Down the Enter to Change the Data!");
                    updateDisplay();
                }
            }
        });
    }

//    public static void main(String[] args){
//        GUI gui = new GUI();
//        ZZB_JCS jcs = new ZZB_JCS();
//        try {
//            jcs.main1(gui);
//        }catch (Exception e){
//            System.out.println("Failed to JCS");
//        }
//    }
}  