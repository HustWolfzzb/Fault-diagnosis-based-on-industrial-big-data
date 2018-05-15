/*
 * Author   :   HustWolf --- 张照博

 * Time     :   2018.1-2018.5

 * Address  :   HUST

 * Version  :   2.5

 * 输入测试数据得到故障模型的预测结果

 * @param decisionTree 决策树

 * @param Attr_Name 特征列表

 * @param TestData 测试数据
 */

public class TestData{
    public static String getCategory(Object number){
        if ((Float)number == 0){
            return "0";
        }
        else return "1";
    }

    public static String  TestData(Object obj, Object[] Attr_Name, Object[] TestData,String line) {
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
                if ((testvalue.equals(attrValue))) {
                    Object child = tree.getChild(attrValue);
                    flag=true;
                    line=TestData(child,Attr_Not_Used,Data_Not_Used,line);
                }
            }
            if (!flag){
                line="Sorry, we don't find this data, maybe it's OK!";
//                System.out.println("Sorry, we don't find this data, maybe it's OK!");
            }
        }else {
            line="The Category of this Data is:"+getCategory(obj);
//            System.out.println("The Category of this Data is:"+getCategory(obj));
        }
        return line;
    }
}