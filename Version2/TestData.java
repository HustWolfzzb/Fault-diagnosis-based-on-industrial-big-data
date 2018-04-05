/*
 * 输入测试数据得到决策树的预测结果
 * @param decisionTree 决策树
 * @param Attr_Name 特征列表
 * @param TestData 测试数据
 * @return
 */

public class TestData{
    public static String TestData(Object Input, Object[] Attr_Name, Object[] TestData) {
        if (Attr_Name.length - 1 != TestData.length) {
            System.out.println("输入数据不完整");
            return "ERROR";
        }
        if (!(Input instanceof ZZB_JCS.Tree)){
            return Input.toString();
        }
        String Result;
        ZZB_JCS.Tree decisionTree=(ZZB_JCS.Tree) Input;
        while (decisionTree instanceof ZZB_JCS.Tree) {

            // 孩子节点不为空,则判断特征值找到子节点
            for (int i = 0; i < Attr_Name.length - 1; i++) {
                // 找到当前特征下标
                if (decisionTree.getAttribute().equals(Attr_Name[i])) {
                    // 得到测试数据特征值
                    Object  AttrValue = TestData[i];
                    // 在子节点中找到含有此特征值的节点
                    ZZB_JCS.Tree childNode = null;
                    for (Object cn : decisionTree.getAttributeValues()) {
                        if (cn.equals(AttrValue)) {
//                            System.out.println(cn.toString());
                            if (!(decisionTree.getChild((String)cn) instanceof ZZB_JCS.Tree) ) {
                                Result = (String) decisionTree.getChild((String) cn);
                                return Result;
                            }
                            childNode = (ZZB_JCS.Tree)decisionTree.getChild((String)cn);
                            break;
                        }
                    }
                    // 如果没有找到此节点,则说明训练集中没有到这个节点的特征值
                    if (childNode == null) {
                        System.out.println("没有找到此特征值的数据");
                        return "ERROR";
                    }
                    decisionTree = childNode;
                    break;
                }
            }
        }
        return "ERROR";
    }
}