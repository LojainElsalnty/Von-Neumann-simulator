package computerArchitecture;

public class RegisterFile {
  private int[] generalRegisters;
  private int PC;
  

	public RegisterFile() {
		generalRegisters=new int[32];
		generalRegisters[0]=0;
		//toberemoved
		generalRegisters[1]=2;
		generalRegisters[2]=8;


		


	}
	public void setPc(int PC) {
		this.PC=PC;
	}
	public int getPc() {
		return this.PC;
	}
	public int[] getRegister() {
		return generalRegisters;
	}
	public void printReg() {
		System.out.println("PC " +": "+PC+" ");
	   for(int i=0 ; i<generalRegisters.length ; i++) {
		   if(i==16)
			   System.out.println();
		   if(i<16) {
		   System.out.print("R"+i +":"+generalRegisters[i]+"  ");
		   }
		   
		   else 
			   System.out.print("R"+i +":"+generalRegisters[i]+"  ");
	   }
	   System.out.println("");
	}
	
}