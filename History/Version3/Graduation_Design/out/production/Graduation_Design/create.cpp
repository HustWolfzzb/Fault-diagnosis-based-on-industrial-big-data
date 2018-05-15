#include<iostream>
#include<string>
#include<fstream>
using namespace std;

int main()
{
    string line;
    int count=0;
    for (int i = 0; i < 68; ++i)
    {
        cin>>line;
        // cout<<line<<" varchar(30) not null,"<<endl;
        cout<<"\""<<line<<"\",";
        count++;
        if (count%34==0 && count!=0)
        {
            cout<<endl;
        }
    }
}
