package computerArchitecture;

public class Instructions {
	int ID;
   String instruction;
   String opcode;
   ControlSignals controlSignal;
   int AluResult;
   String stage;
     
   public Instructions(int ID,String instruction) {
	   this.ID=ID;
	   this.instruction =instruction;
	   this.opcode=instruction.substring(0, 4);
	   stage="Decode";
   }
   public void decode() {
	   opcode=this.instruction.substring(0, 4);
   }
   public void getControlSignals() {
	  int op= Integer.parseInt(opcode, 2);
	   controlSignal=new ControlSignals(op);
   }
   
}

