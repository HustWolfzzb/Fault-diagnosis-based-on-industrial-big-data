#include<iostream>
#include<string>
#include<fstream>
using namespace std;

int main()
{
    int count=0;
    string attr="start";
    ifstream in("/Users/zhangzhaobo/IdeaProjects/Graduation_Design/src/steel+plate.txt");
    ofstream out("/Users/zhangzhaobo/IdeaProjects/Graduation_Design/src/ClearData.txt");
    while(attr!="exit")
    {
        in>>attr;
        if (count%34==0 && count!=0)
        {
            out<<endl;
        }
        count++;
        out<<attr<<"\t";
        cout<<count<<endl;
    }
    in.close();
    out.close();
    return 0;
}
