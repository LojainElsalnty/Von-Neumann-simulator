package computerArchitecture;

public class ControlSignals {
	String AluOp;
	
	public ControlSignals(int Opcode) {
		switch (Opcode) {
		    // ADD
		case 0b0000:
			AluOp = "000";break;
			// SUB
		case 0b0001:
			AluOp = "001";break;
			// MULI
		case 0b0010:
			AluOp = "010";break;
			// ADDI
		case 0b0011:
			AluOp = "000";break;
			// BNE
		case 0b0100:
			AluOp = "001";break;
			// ANDI
		case 0b0101:
			AluOp = "101";break;
			// ORI
		case 0b0110:
			AluOp = "111";break;
			// Jump
		case 0b0111:
			AluOp = "110";break;
			// SLL
		case 0b1000:
			AluOp = "011";break;
			// SRL
		case 0b1001:
			AluOp = "100";break;
			// LW
		case 0b1010:
			AluOp = "000";break;
			// SW
		case 0b1011:
			AluOp = "000";break;
		}
	}
}
