import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
        JOptionPane.showMessageDialog(null," Welcome for Your Next Time!","MESSAGE FROM ZZB",JOptionPane.WARNING_MESSAGE);
        System.exit(0);
    }
    @Override
    public void windowActivated(WindowEvent e) {
        //每次获得焦点 就会触发
        System.out.println("");
   }
   @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub
        System.out.println("Now It is Working!");
        JOptionPane.showMessageDialog(null,"Welcome To Here!","MESSAGE FROM ZZB",JOptionPane.WARNING_MESSAGE);
  }
}

public class MouseAndKeyEvent{
    private Frame f;
    private Button but,but1,but2;
    private TextField ta;
    private MenuBar mb;
    private Menu m,subm,Run;
    private MenuItem closeItem,openItem,saveItem,subItem1,subItem;
    private FileDialog openDialog,saveDialog;
    private File file;
    private JPanel jp1,jp2,jp3,jp4,jp5,jp6,jp7,jp8,jp9,jp10,jp11,jp12,jp13;
    private JLabel jl1,jl2,jl3,jl4,jl5,jl6,jl7,jl8,jl9,jl10,jl11;
    private String[] TEXT =  new String[12];
    public MouseAndKeyEvent() {
        init();
    }

    public static void UpdateTEXT(MouseAndKeyEvent obj,String[] txt){
        for (int i=0;i<10;++i){
            obj.TEXT[i]=txt[i];
        }
    }

    private void init(){

        f=new Frame("The Graduation Design Windwos form Zhang Zhaobo for Teachers!");
        f.setBounds(300, 100, 800, 600);
        f.setLayout(new GridLayout(13,1));
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
        jl1 = new JLabel();
        jl1 = new JLabel("This is the Code Line for Command!");
        jp1.add(jl1);
        f.add(jp1);


        jp2 = new JPanel();
        jp2.add(ta);
        jp2.add(but);
        f.add(jp2);

        jp3 = new JPanel();
        jl2 = new JLabel();
        jl2 = new JLabel("Line 1",JLabel.CENTER);
        jp3.add(jl2);
        f.add(jp3);

        jp4 = new JPanel();
        jl3 = new JLabel();
        jl3 = new JLabel("Line 2",JLabel.CENTER);
        jp4.add(jl3);
        f.add(jp4);

        jp5 = new JPanel();
        jl4 = new JLabel();
        jl4 = new JLabel("Line 3",JLabel.CENTER);
        jp5.add(jl4);
        f.add(jp5);

        jp6 = new JPanel();
        jl5 = new JLabel();
        jl5 = new JLabel("Line 4",JLabel.CENTER);
        jp6.add(jl5);
        f.add(jp6);

        jp7 = new JPanel();
        jl6 = new JLabel();
        jl6 = new JLabel("Line 5",JLabel.CENTER);
        jp7.add(jl6);
        f.add(jp7);

        jp8 = new JPanel();
        jl7 = new JLabel();
        jl7 = new JLabel("Line 6",JLabel.CENTER);
        jp8.add(jl7);
        f.add(jp8);

        jp9 = new JPanel();
        jl8 = new JLabel();
        jl8 = new JLabel("Line 7",JLabel.CENTER);
        jp9.add(jl8);
        f.add(jp9);

        jp10 = new JPanel();
        jl9 = new JLabel();
        jl9 = new JLabel("Line 8",JLabel.CENTER);
        jp10.add(jl9);
        f.add(jp10);

        jp11 = new JPanel();
        jl10 = new JLabel();
        jl10 = new JLabel("Line 9",JLabel.CENTER);
        jp11.add(jl10);
        f.add(jp11);

        jp12 = new JPanel();
        jl11 = new JLabel();
        jl11 = new JLabel("Line 10",JLabel.CENTER);
        jp12.add(jl11);
        f.add(jp12);

        jp13 = new JPanel();
        but1 = new Button("CLEAR");
        but2 = new Button("NEXT");
        jp13.add(but1);
        jp13.add(but2);
        f.add(jp13);
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

                    BufferedWriter bufw=new BufferedWriter(new FileWriter(file));

                    String text=ta.getText();

                    bufw.write(text);
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
                openDialog.setVisible(true);
                String dirPath=openDialog.getDirectory();
                String fileName=openDialog.getFile();
                System.out.println(dirPath+"...."+fileName);
                if(dirPath==null || fileName==null)
                    return;
                ta.setText("");
                file=new File(dirPath,fileName);
                try {
                    BufferedReader bufr=new BufferedReader(new FileReader(file));
                    String line=null;
                    while((line=bufr.readLine())!=null){
                        ta.setText(line+"\r\n");
                    }
                    bufr.close();
                } catch (IOException e2) {
                    throw new RuntimeException("open the Exception");
                }
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
        ta.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e){
                int code=e.getKeyCode();
                if(code<0){
                    System.out.println(code+"  ....Illegal Input");
                    e.consume();  //不执行加入文本框.
                }
            }
        });
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                System.out.println("Put Down the Button!");
            }
        });
        but.addMouseListener(new MouseAdapter() {
            private int count=0;
            private int clickCount=1;
            public void mouseEntered(MouseEvent e){
                System.out.println("Entered!"+count++);
            }
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount()==2){
                    System.out.println("Double Click!");
                }else
                    System.out.println("Click : "+clickCount++);
            }
        });
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                System.out.println("Put Down the Button1 to Clear the Data!");
                jl2.setText("Line 1");
                jl3.setText("Line 2");
                jl4.setText("Line 3");
                jl5.setText("Line 4");
                jl6.setText("Line 5");
                jl7.setText("Line 6");
                jl8.setText("Line 7");
                jl9.setText("Line 8");
                jl10.setText("Line 9");
                jl11.setText("Line 10");
            }
        });
        but2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                System.out.println("Put Down the Button1 to Clear the Data!");
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
        });
    }
}  