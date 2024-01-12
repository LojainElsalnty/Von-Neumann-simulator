package computerArchitecture;

public class IFormat extends Instructions{
    int R1index;
    int R2index;
    int Immediate;
    int readData1;
    int readData2;
    String dataFromMem;
    Boolean branch;
	public IFormat(int ID,String instruction) {
		super(ID,instruction);
		branch =false;
		
	
	}
	public void decode() {
		super.decode();
		String register1=instruction.substring(4,9);
		R1index=Integer.parseInt(register1,2);
		String register2=instruction.substring(9,14);
		R2index=Integer.parseInt(register2,2);
		String Imm=instruction.substring(14,32);
		Immediate=binaryStringToDecimal(Imm);
		
	}
	   public static int binaryStringToDecimal(String binaryString) {
	        int decimalValue = 0;
	        int sign = 1;

	        if (binaryString.charAt(0) == '1') {
	            // Negative value, perform 2's complement
	            sign = -1;
	            binaryString = invertBits(binaryString);
	            decimalValue = -(Integer.parseInt(binaryString, 2) + 1);
	        } else {
	            decimalValue = Integer.parseInt(binaryString, 2);
	        }

	        return decimalValue;
	    }

	    public static String invertBits(String binaryString) {
	        StringBuilder invertedString = new StringBuilder();
	        for (char c : binaryString.toCharArray()) {
	            if (c == '0') {
	                invertedString.append('1');
	            } else {
	                invertedString.append('0');
	            }
	        }
	        return invertedString.toString();
	    }
	public static void main(String[] args) {
//	IFormat x=new IFormat("00110100000000111111111111111000");
	//	x.decode();
	//System.out.print(binaryStringToDecimal("111111111111111000"));
		//System.out.println(twosCompliment("111111111111111000"));

	}
}