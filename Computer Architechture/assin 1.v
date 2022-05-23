
module half_adder(output sum,output carry,input a,input b);
   xor(sum, a, b);
   and(carry, a, b);
endmodule

module full_adder(output sum,output carry,input x,input y,input z);
   
   wire s1, c1, c2;
   half_adder h1(s1, c1, x, y);
   half_adder h2(sum, c2, s1, z);
   or(carry, c1, c2);
endmodule

module Add( a,b,cin,sum,cout);
    input [3:0]a,b;input cin;
    output [3:0]sum; output cout;

    wire [2:0]ci;
    full_adder fa0(sum[0],ci[0],a[0],b[0],cin);
    full_adder fa1(sum[1],ci[1],a[1],b[1],ci[0]);
    full_adder fa2(sum[2],ci[2],a[2],b[2],ci[1]);
    full_adder fa3(sum[3],cout,a[3],b[3],ci[2]);
endmodule

module TwosComplement(A,res);
	input [3:0] A; 		
	output [3:0] res;
	wire  [3:0]in;
	not(in[0],A[0]);
	not(in[1],A[1]);
	not(in[2],A[2]);
	not(in[3],A[3]);
	Add myadd(in,4'b0001,1'b0,res,cout);
endmodule

module Substract(a,b,c,cout); 
	input [3:0] a,b;
	output [3:0] c;
	output cout;
	wire [3:0] out;
	TwosComplement tw(b,out);
	Add myad(a,out,1'b0,c,cout);
endmodule

module Mux(out,s0,s1,s2,i0,i1,i2,i3,i4,i5,i6,i7);
    input s0,s1,s2,i0,i1,i2,i3,i4,i5,i6,i7;
    output out;
    wire [7:0]w;
    wire [0:2]s;

    not(s[0],s0);
    not(s[1],s1);
    not(s[2],s2);

    and(w[0],i0,s[0],s[1],s[2]);
    and(w[1],i1,s[0],s[1],s2);
    and(w[2],i2,s[0],s1,s[2]);
    and(w[3],i3,s[0],s1,s2);
    and(w[4],i4,s0,s[1],s[2]);
    and(w[5],i5,s0,s[1],s2);
    and(w[6],i6,s0,s1,s[2]);
    and(w[7],i7,s0,s1,s2);
    or(out,w[0],w[1],w[2],w[3],w[4],w[5],w[6],w[7]);
endmodule


module RightShift(A,B);
    
    input [3:0]A;
    output [3:0]B;
    buf(B[0],A[1]);
    buf(B[1],A[2]);
    buf(B[2],A[3]);
    buf(B[3],A[3]);

endmodule

module ALU(a,b,outp1,outp2,outp3,outp4,carr,s0,s1,s2);

    input [3:0]a,b;
    input s0,s1,s2;
    
    output outp1,outp2,outp3,outp4,carr;
	wire [3:0] c0,c1,c2,c3,c4,c5,c6,c7;
    wire [7:0]cout;

    //*1 add A + b
    Add myadd(a,b,1'b0,c0,cout[0]);
    
  //  *2 subtract B from A
    Substract sub(a,b,c1,cout[1]);
   

   // *3 Adding 1 to  A .
    Add add1(a,4'b0001,1'b0,c2,cout[2]);
    //*4 Subtracting 1 from A .
    Substract sub1(a,4'b0001,c3,cout[3]);


    //*5 Bitwise ANDing A and B .
    And myand(a,b,c4);

    //*6 Bitwise ORing two 4-bit numbers.
    OR myOr(a,b,c5);
    
    //7 Bitwise XORing two 4-bit numbers.
    XOR myXor(a,b,c6);
    RightShift myshift(a,c7);

    Mux b0(outp1,s0,s1,s2,c0[3],c1[3],c2[3],c3[3],c4[3],c5[3],c6[3],c7[3]);
	Mux b1(outp2,s0,s1,s2,c0[2],c1[2],c2[2],c3[2],c4[2],c5[2],c6[2],c7[2]);	
	Mux b2(outp3,s0,s1,s2,c0[1],c1[1],c2[1],c3[1],c4[1],c5[1],c6[1],c7[1]);
	Mux b3(outp4,s0,s1,s2,c0[0],c1[0],c2[0],c3[0],c4[0],c5[0],c6[0],c7[0]);
	Mux b4(carr,s0,s1,s2,cout[0],cout[1],cout[2],cout[3],cout[4],cout[5],cout[6],cout[7]);

endmodule

module And( a, b,c);
	input [3:0] a,b;
	output [3:0] c;
    and(c[0],a[0],b[0]);	
	and(c[1],a[1],b[1]);
	and(c[2],a[2],b[2]);
	and(c[3],a[3],b[3]);
endmodule

module OR(a, b,c);
    input [3:0] a,b;
	output [3:0] c;
    
    or(c[0],a[0],b[0]);
    or(c[1],a[1],b[1]);
    or(c[2],a[2],b[2]);
    or(c[3],a[3],b[3]);
endmodule

module XOR(a,b,c);
    input [3:0] a,b;
	output [3:0] c;
    
    xor(c[0],a[0],b[0]);
    xor(c[1],a[1],b[1]);
    xor(c[2],a[2],b[2]);
    xor(c[3],a[3],b[3]);
endmodule


module Test;
    
	reg [3:0] a,b;
	wire opt1,opt2,opt3,opt4;
    wire carry;
	reg s0,s1,s2;
	ALU my(a,b,opt1,opt2,opt3,opt4,carry,s0,s1,s2);
        
    initial	
	begin
		a<= 4'b1011;
		b<= 4'b0010;
        s0=1;s1=0;s2=0;	
		#1 
        $display("s0 = %b , s1 = %b , s2 = %b\n",s0,s1,s2);
        $display("a = %b %b %b %b\n",a[3],a[2],a[1],a[0]);

        $display("b = %b %b %b %b\n",b[3],b[2],b[1],b[0]);


            if (s0=0&&s0=0&&s0=0) begin
                $display("A + B =%b %b %b %b\n",);
                    
        end
		$display("ANSWER = %b %b %b %b\n",opt1,opt2,opt3,opt4);
		$display(" carry = %b",carry);
		
	end
endmodule
	