
`define NUM_BITS 4  // Try 1, 2, 3, 4, 5...


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


//4bit Mux
module Mux_4bits(out,s0,s1,in0,in1,in2,in3);
    input s0,s1,in0,in1,in2,in3;
    output out;
    wire [3:0]w;
    wire [0:1]s;
    not(s[0],s0);
    not(s[1],s1);
    and(w[0],in0,s[0],s[1]);
    and(w[1],in1,s[0],s1);
    and(w[2],in2,s0,s[1]);
    and(w[3],in3,s0,s1);
    or(out,w[0],w[1],w[2],w[3]);
endmodule

//8 bit Mux
module Mux_8bits(out,s0,s1,s2,in0,in1,in2,in3,in4,in5,in6,in7);
    input s0,s1,s2,in0,in1,in2,in3,in4,in5,in6,in7;
    output out;
    wire [7:0]w;
    wire [0:2]s;

    not(s[0],s0);
    not(s[1],s1);
    not(s[2],s2);

    and(w[0],in0,s[0],s[1],s[2]);
    and(w[1],in1,s[0],s[1],s2);
    and(w[2],in2,s[0],s1,s[2]);
    and(w[3],in3,s[0],s1,s2);
    and(w[4],in4,s0,s[1],s[2]);
    and(w[5],in5,s0,s[1],s2);
    and(w[6],in6,s0,s1,s[2]);
    and(w[7],in7,s0,s1,s2);
    or(out,w[0],w[1],w[2],w[3],w[4],w[5],w[6],w[7]);
endmodule


module ArthmiticCir(cin,s0,s1,A,B, out,carr);
   parameter N = `NUM_BITS-1;
    input cin,s0,s1;

    input [N:0]A,B;
    
    wire Bcomp[N:0];
    
    output [N:0] out;
    output carr;
    generate
        
    genvar i;
   for(i=0;i<=N;i=i+1)
   begin
    not(Bcomp[i],B[i]);
    end    

    wire [N:0]w;
   for(i=0;i<=N;i=i+1)
   begin
    Mux_4bits mux1(w[i],s0,s1,B[i],Bcomp[i],1'b0,1'b1);
    end
   

    wire [N:0]ci;



    full_adder f1(out[0],ci[0],A[0],w[0],cin);
   
   for(i=1;i<=N;i=i+1)
   begin
    full_adder f2(out[i],ci[i],A[i],w[i],ci[i-1]);
    end

    endgenerate

    buf(carr,ci[N]);
endmodule

// Right shift for 4-bit numbers with save the sign
module RightShift(A,B);
   parameter N = `NUM_BITS-1;
    
    input [N:0]A;
    output [N:0]B;
generate
    
    genvar i;
   for(i=0;i<=N-1;i=i+1)
   begin
    buf(B[i],A[i+1]);
    end

    
    buf(B[N],A[N]);
endgenerate

endmodule

module And( A, B,c);
   parameter N = `NUM_BITS-1;

	input [N:0] A,B;
	output [N:0] c;
    
    and(c[0],A[0],B[0]);	
generate
    
	genvar  i;
   for(i=0;i<=N;i=i+1)
   begin
    and(c[i],A[i],B[i]);		
    end
endgenerate
 
endmodule

module OR( A, B,c);
   parameter N = `NUM_BITS-1;

	input [N:0] A,B;
	output [N:0] c;
generate
    
genvar i;    
 	for(i=0;i<=N;i=i+1)
   begin
    or(c[i],A[i],B[i]);	
   end
   
endgenerate
 
    
endmodule
module XOR( A, B,c);
   parameter N = `NUM_BITS-1;

	input [N:0] A,B;
	output [N:0] c;
generate

genvar i;

for(i=0;i<=N;i=i+1)
   begin
    xor(c[i],A[i],B[i]);	
   end
   

endgenerate

endmodule


module ALU(a,b,out,carr,s0,s1,s2);
   parameter N = `NUM_BITS-1;
    //2 Numbers input 
    input [N:0]a,b;
    
    //3 selection lines
    input s0,s1,s2;
    
    //4 Bits output and carry 
    output carr;
    output [N:0]out;
    //4 Bits wires for each Operation
    wire [N:0] c0,c1,c2,c3,c4,c5,c6,c7;
    
    //store the carry of each operation
    wire [7:0]cout;
    
    //*1 add A + b
    ArthmiticCir Add_2_Nums(1'b0,1'b0,1'b0,a,b,c0,cout[0]);
  
    

    
    //  *2 subtract B from A 
    ArthmiticCir Subtract_2_Nums(1'b1,1'b0,1'b1,a,b,c1,cout[1]);
    
    // *3 Adding 1 to  A 
    ArthmiticCir Add_1_to_A(1'b1,1'b1,1'b0,a,b,c2,cout[2]);
    
     //*4 Subtracting 1 from A .
    ArthmiticCir Decrement_A(1'b0,1'b1,1'b1,a,b,c3,cout[3]);


	//*5 Bitwise ANDing A and B .
    And myand(a,b,c4);

    //*6 Bitwise ORing two 4-bit numbers.
    OR myOr(a,b,c5);
    
    //*7 Bitwise XORing two 4-bit numbers.
    XOR myXor(a,b,c6);
    
    //*8 Right shift for 4-bit 'A' numbers and save the sign 
    RightShift myshift(a,c7);

    generate
        
     genvar  i;
for(i=0;i<=N;i=i+1)
   begin
    Mux_8bits M1(out[i],s0,s1,s2,c0[i],c1[i],c2[i],c3[i], c4[i],c5[i],c6[i],c7[i]);
    end
 	endgenerate


	Mux_8bits M5(carr,s0,s1,s2,cout[0],cout[1],cout[2],cout[3],cout[4],cout[5],cout[6],cout[7]);

endmodule


module Test();

   parameter N = `NUM_BITS-1;
    
	reg [N:0] A,B;
    wire [N:0]out;
    wire carry;
	reg s0,s1,s2;
    integer i;
	ALU my(A,B,out,carry,s0,s1,s2);

    initial	
	begin
        
        //Change The Numbers
		A<= 4'b1001;
		B<= 4'b0101;

        //Change the selection
        s0=0;s1=0;s2=0;

		#1
        $display("\nSelection = %b %b %b\n",s0,s1,s2);
		
        
        $write("A = ");

            for (i =0;i<=N ;i=i+1 ) begin
                $write("%b ",A[N-i]);                
                
            end

        $write("\nB = ");
        
            for (i =0;i<=N ;i=i+1 ) begin
                $write("%b ",B[N-i]);                
                
            end
    

    
        if (s0==0 && s1==0 && s2==0) $write("\nA + B = ");
        
        if (s0==0&&s1==0&&s2==1)  $write("\nA - B = ");

        if (s0==0&&s1==1&&s2==0)  $write("\nA + 1 = ");

        if (s0==0&&s1==1&&s2==1)  $write("\nA - 1 = ");

        if (s0==1&&s1==0&&s2==0)  $write("\nA AND B  = ");
        
        if (s0==1&&s1==0&&s2==1)  $write("\nA OR B = ");
       
        if (s0==1&&s1==1&&s2==0)  $write("\nA XOR B = ");
        
        if (s0==1&&s1==1&&s2==1)  $write("\nShiftRight(A) = ");
        
        
            
            for (i =0;i<=N ;i=i+1 ) begin
                $write("%b ",out[N-i]);                
                
            end
                        
        

    $display("\ncarry = %b",carry);
    end

endmodule