/*
 * Author   :   HustWolf --- 张照博

 * Time     :   2018.1-2018.5

 * Address  :   HUST

 * Version  :   1.5

 * 输入测试数据得到决策树的预测结果

 * @param decisionTree 决策树

 * @param Attr_Name 特征列表

 * @param TestData 测试数据
 */

public class TestData{
    public static String getFault(String number){
        String[] Fault = new String[]{"Pastry","Z_Scratch","K_Scatch","Stains","Dirtiness","Bumps","Other_Faults"};
        return Fault[Integer.valueOf(number)];
    }

    public static void TestData(Object obj, Object[] Attr_Name, Object[] TestData) {
        if (obj instanceof ZZB_JCS.Tree && Attr_Name.length>0){
            ZZB_JCS.Tree tree = (ZZB_JCS.Tree) obj;
            String attribute_Name = tree.getAttribute();
            Object[] Attr_Not_Used = new Object[Attr_Name.length-1];
            Object[] Data_Not_Used = new Object[Attr_Name.length-1];
            Object testvalue = TestData[0];
            for (int i=0,j=0;i<Attr_Name.length ;++i ) {
                if ( attribute_Name != Attr_Name[i]  ) {
                    Data_Not_Used[j] = TestData[i];
                    Attr_Not_Used[j++] = Attr_Name[i];
                }
                else {
                    testvalue = TestData[i];
                }
            }
            boolean flag=false;
            for (Object attrValue : tree.getAttributeValues()){
                if (((String)testvalue).equals((String) attrValue)) {
                    Object child = tree.getChild(attrValue);
                    flag=true;
                    TestData(child,Attr_Not_Used,Data_Not_Used);
                }
            }
            if (!flag){
                System.out.println("Sorry, We Don't Find the Same Data, Maybe it is Good! Congratulation!");
                return;
            }
        }else {
            System.out.println("\n\nThe Fault of this Data is: "+getFault((String )obj));
        }
    }
}