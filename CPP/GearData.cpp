#include<iostream>
#include<string>
#include<fstream>
// #include<vector>
#include<string>
#include <iomanip>
using namespace std;

int main()
{
    float data[4];
    string hz[10]={"0","10","20","30","40","50","60","70","80","90"};
    string file;
    ofstream out("/Users/zhangzhaobo/Documents/Graduation-Design/Data/BrokenTooth Data/New_b30hz.txt");
    for (int i = 0; i < 10; ++i)
    {
        file="/Users/zhangzhaobo/Documents/Graduation-Design/Data/BrokenTooth Data/b30hz"+hz[i]+".txt";
        ifstream in(file);
        while(in>>data[0])
        {
            out<<setprecision(2)<<data[0]<<"\t\t";
            for (int i = 1; i < 4; ++i)
            {
                in>>data[i];
                out<<setprecision(2)<<data[i]<<"\t\t";
            }
            out<<endl;
        }
        cout<<file<<" is done!"<<endl;
        in.close();
    }
    out.close();
    ofstream out1("/Users/zhangzhaobo/Documents/Graduation-Design/Data/Healthy Data/New_h30hz.txt");
    for (int i = 0; i < 10; ++i)
    {
        file="/Users/zhangzhaobo/Documents/Graduation-Design/Data/Healthy Data/h30hz"+hz[i]+".txt";
        ifstream in(file);
        while(in>>data[0])
        {
            out1<<setprecision(2)<<data[0]<<"\t\t";
            for (int i = 1; i < 4; ++i)
            {
                in>>data[i];
                out1<<setprecision(2)<<data[i]<<"\t\t";
            }
            out1<<endl;
        }
        cout<<file<<" is done!"<<endl;
        in.close();
    }
    out1.close();
    return 0;
}