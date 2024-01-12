package computerArchitecture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Memory {
	private String[] memory;
	private int count;
	private int numberOfClockCycles ;
	

	public Memory() {
		memory = new String[2048];
		count = 0;
		PutIntoMemory();
		clockCycles(count);
	}

	public void PutIntoMemory() {
		File file = new File("Instructions.txt");
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String st = br.readLine();
			String opcode;
			while (st != null) {
				String[] instruction = st.split(" ");
				String ins=convertToBinary(instruction);
				
				if(count<1024) { //ask manar
					memory[count]=ins;
					count++;
				
				}
				st=br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeToMem(int add,int  data) {
		String data1=String.format("%32s", Integer.toBinaryString(Integer.parseInt(data+""))).replace(' ', '0') ;
		memory[add]=data1;
		 System.out.println("Memory content of index "+add +" was changed to "+ data);

	}

	public String readFromMem(int add) {
		
 return memory[add];
	}

	public static String convertToBinary( String [] instruction) {
		String result ="";
		result+= getOpcode(instruction[0]);
		if(instruction.length==5) {
	      result+=getRegister(instruction[1]);
	      result+=getRegister(instruction[2]);
	      result+=getRegister(instruction[3]);
	      result+=String.format("%13s", Integer.toBinaryString(Integer.parseInt(instruction[4]))).replace(' ', '0') ;
        }
		if(instruction.length==4) {
			result+=getRegister(instruction[1]);
			result+=getRegister(instruction[2]);
			try {
				int val=Integer.parseInt(instruction[3]);
			
				if(val>=0)
		    result+=String.format("%18s", Integer.toBinaryString(val)).replace(' ', '0') ;
				else {
					String binaryForm=Integer.toBinaryString(val);
					result+=binaryForm.substring(binaryForm.length() - 18);
					
                  
				}
			}catch(Exception e) {
				

				  result+=getRegister(instruction[3]);
				  result+=String.format("%13s", Integer.toBinaryString(Integer.parseInt("0"))).replace(' ', '0') ;
			}
		}
		if   (instruction.length==2)
		{
			result+= String.format("%28s", Integer.toBinaryString(Integer.parseInt(instruction[1]))).replace(' ', '0') ;
		}
		return result ;
		
	}

	private static String getOpcode(String stringOpcode1 ) {
		String stringOpcode="";
		  switch(stringOpcode1) {
		    case "ADD" :stringOpcode="0000";break;
		    case "SUB" :stringOpcode="0001";break;
	  	    case "MULI":stringOpcode="0010";break;
		    case "ADDI":stringOpcode="0011";break;
		    case "BNE" :stringOpcode="0100";break;
		    case "ANDI":stringOpcode="0101";break;
		    case "ORI" :stringOpcode="0110";break;
		    case "J"   :stringOpcode="0111";break;
		    case "SLL" :stringOpcode="1000";break;
		    case "SRL" :stringOpcode="1001";break;
		    case "LW"  :stringOpcode="1010";break;
		    case "SW"  :stringOpcode="1011";break;
		}

			return stringOpcode;
		}


	private static String getRegister(String Register) {
		String result="";
		switch (Register) {
		case "R0":result="00000";break;
		case "R1":result="00001";break;
		case "R2":result="00010";break;
		case "R3":result="00011";break;
		case "R4":result="00100";break;
		case "R5":result="00101";break;
		case "R6":result="00110";break;
		case "R7":result="00111";break;
		case "R8":result="01000";break;
		case "R9":result="01001";break;
		case "R10":result="01010";break;
		case "R11":result="01011";break;
		case "R12":result="01100";break;
		case "R13":result="01101";break;
		case "R14":result="01110";break;
		case "R15":result="01111";break;
		case "R16":result="10000";break;
		case "R17":result="10001";break;
		case "R18":result="10010";break;
		case "R19":result="10011";break;
		case "R20":result="10100";break;
		case "R21":result="10101";break;
		case "R22":result="10110";break;
		case "R23":result="10111";break;
		case "R24":result="11000";break;
		case "R25":result="11001";break;
		case "R26":result="11010";break;
		case "R27":result="11011";break;
		case "R28":result="11100";break;
		case "R29":result="11101";break;
		case "R30":result="11110";break;
		case "R31":result="11111";break;
		default:result="00000";break;
		
		}
		return result;
	}
	public String[] getMemory() {
		return memory;
	}

	public int getCount() {
		return count;
	}

	
	public void setCount(int count) {
		this.count = count;
	}
    public int clockCycles(int count) {
		
		int instructionsNumber=count;
		
		this.numberOfClockCycles = 7+((instructionsNumber-1)*2);
		return numberOfClockCycles;
		
	}
    public int getNumberOfClockCycles() {
    	
    	return numberOfClockCycles;
    }
 public void setNumberOfClockCycles(int num) {
    	
    numberOfClockCycles=num;
    }
    public void printMem() {
    	System.out.println("Memory Content");
    	for(int i=0 ; i<memory.length ; i++) {
    		System.out.print("Index "+i+":"+memory[i]+ " ");
    	}
    	System.out.println("");
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Memory mem=new Memory();
		//mem.PutIntoMemory();
		mem.printMem();
		

		

	}

	
}
