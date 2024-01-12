package computerArchitecture;

public class ALU {

	boolean zeroFlag;
	int result;
	
	public ALU() {

		this.zeroFlag = true;
		this.result = 0;
	}
	
	public int execute(int data1, int data2, String operation) {
	

		switch(operation) {
		case "000": result =data1+data2 ;break;
		case "001": result= data1-data2;break;
		case "010": result = data1*data2; break;
		case "011": result = data1<<data2; break;
		case "100": result = data1>>>data2; break;
		case "101": result = data1&data2; break;
		case "110": String x =String.format("%4s",Integer.toBinaryString(data1) ).replace(' ', '0') ;
					String y=String.format("%28s",Integer.toBinaryString(data2) ).replace(' ', '0') ; 
					result=Integer.parseInt(x+""+y,2);break;
		case "111": result = data1|data2;
		}
		if (result ==0){
			zeroFlag = true;
		}
		else {
			zeroFlag = false;
		}
		return result;
	}
	
	
	
	
}
