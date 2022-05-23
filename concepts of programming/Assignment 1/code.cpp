#include <iostream>
#include <stack>
using namespace std;


enum pType {a,b,c,d,res};
struct MyCall
{
    pType type;
    int n;
    int res;
};

int F(int n)
{
    stack <MyCall> r;
    stack <MyCall> s;
    MyCall fCall;
    fCall.n=n;
    fCall.type=res;
    s.push(fCall);
    while (!s.empty())
    {
        MyCall call=s.top();
        s.pop();

        if (call.type==res)
        {
            if (call.n<=1)
            {
                call.res=1;
                r.push(call);
            }
            else
            {
                if (!r.empty() && r.top().type==d && r.top().n==call.n)
                {
                    call.res=0;
                    //get d and add it to the result
                    call.res+=r.top().res;
                    r.pop();
                    //pop c
                    r.pop();
                    //get b
                    call.res+=r.top().res;
                    r.pop();
                    //get a
                    call.res+=r.top().res;
                    r.pop();
                    r.push(call);

                }
                else
                {
                    s.push(call);
                    MyCall tempD;
                    tempD.type=d;
                    tempD.n=call.n;
                    s.push(tempD);

                    MyCall tempC;
                    tempC.type=c;
                    tempC.n=call.n;
                    s.push(tempC);

                    MyCall tempB;
                    tempB.type=b;
                    tempB.n=call.n;
                    s.push(tempB);

                    MyCall tempA;
                    tempA.type=a;
                    tempA.n=call.n;
                    s.push(tempA);

                }
            }
        }
        else if (call.type==a)
        {
            if (!r.empty() && r.top().type==res && call.n-1==r.top().n)
            {
                    MyCall rcall=r.top();
                    r.pop();
                    rcall.type=a;
                    rcall.res+=call.n;
                    r.push(rcall);
            }
            else
            {
                MyCall resCall;
                resCall.type=res;
                resCall.n=call.n-1;
                s.push(call);
                s.push(resCall);
            }
        }
        else if (call.type==b)
        {
            if (!r.empty() && r.top().type==res && call.n /2==r.top().n)
            {
                MyCall rcall=r.top();
                r.pop();
                rcall.type=b;
                rcall.res*=call.n;
                r.push(rcall);
            }
            else
            {
                MyCall resCall;
                resCall.type=res;
                resCall.n=call.n/2;
                s.push(call);
                s.push(resCall);
            }
        }
        else if (call.type==c)
        {

                MyCall rB=r.top();
                r.pop();
                MyCall rA=r.top();
                r.pop();
                MyCall rC;
                rC.type=c;
                rC.res=call.n-2-(rA.res+rB.res)%2;
                r.push(rA);
                r.push(rB);
                r.push(rC);
        }
        else if (call.type==d)
        {
            if (!r.empty() && r.top().type==c)
            {
                MyCall rcall;
                rcall.type=res;
                rcall.n=r.top().res;
                s.push(call);
                s.push(rcall);
            }
            else if (!r.empty() && r.top().type==res)
            {
                MyCall resCall;
                resCall.type=d;
                resCall.n=call.n;
                resCall.res=r.top().res;
                r.pop();
                r.push(resCall);
            }
        }
    }
    return r.top().res;
}

int FF(int n)
{
    if(n<=1) return 1;
    int a=n+FF(n-1);
    int b=n*FF(n/2);
    int c=n-2-(a+b)%2;
    int d=FF(c);
    return a+b+d;
}

int main()
{
    cout <<FF(3)<<"\n";
    cout <<F(3)<<"\n";
}
