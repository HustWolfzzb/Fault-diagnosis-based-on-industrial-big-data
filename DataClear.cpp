#include<iostream>
#include<string>
#include<fstream>
using namespace std;

int main()
{
    int count=0;
    float attr[34];
    ifstream in("/Users/zhangzhaobo/Documents/Graduation-Design/Mydata.txt");
    ofstream out("/Users/zhangzhaobo/Documents/Graduation-Design/Data/New_Data.txt");
    string line[7]={"Diff_X","Diff_Y","Pixels_Areas","Diff_Luminosity","TypeOfSteel","Steel_Plate_Thickness","Fault"};
    for(int i=0;i<7;i++)
    {
        out<<line[i]<<"\t\t";
    }
    out<<endl;
    for (int i = 0; i < 34; ++i)
    {
        in>>line[1];
        /* code */
    }
    while(count<1941)
    {
        for (int i = 0; i < 34; ++i)
        {
            in>>attr[i];
        }
        int X_dis=attr[1]-attr[0];
        int Y_dis=attr[3]-attr[2];
        int Area_Range=attr[4];
        int Luminosity_dis=attr[9]-attr[8];
        int TypeOfSteel=attr[11];
        int Steel_Plate_Thickness=attr[13];
        if (X_dis<100)
        {
            X_dis=X_dis/10;
        }
        else{
            X_dis=10+X_dis/100;
        }
        if (Y_dis<100)
        {
            Y_dis=Y_dis/10;
        }
        else{
            if (Y_dis<1000)
            {
                Y_dis=10+Y_dis/100;
            }
            else
            {
                Y_dis=20+Y_dis/1000;
            }
        }
        Luminosity_dis=Luminosity_dis/5;
        if (Area_Range<100)
        {
            Area_Range=Area_Range/10;
        }
        else
        {
            if (Area_Range<1000)
            {
                Area_Range=10+Area_Range/100;
            }
            else
            {
                Area_Range=20+Area_Range/1000;
            }
        }
        out<<X_dis<<"\t\t"<<Y_dis<<"\t\t"<<Area_Range<<"\t\t"<<Luminosity_dis<<"\t\t"<<TypeOfSteel<<"\t\t"<<Steel_Plate_Thickness<<"\t\t";
        int Fault=0;
        for (int i = 0; i < 7; ++i)
        {
            if (attr[i+27]!=0)
            {
                Fault=i;
            }

        }
        out<<Fault<<endl;
        count++;
    }
    in.close();
    return 0;
}