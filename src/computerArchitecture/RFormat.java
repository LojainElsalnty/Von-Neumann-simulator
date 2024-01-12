package computerArchitecture;

public class RFormat extends Instructions {

	int R1index;

	int R2index;

	int R3index;

	int shamt;

	int readData1;

	int readData2;
	
	int readData3;

	public RFormat(int ID,String instruction) {

		super(ID,instruction);

	}

	public void decode() {

		super.decode();

		String register1 = instruction.substring(4, 9);

		R1index = Integer.parseInt(register1, 2);

		String register2 = instruction.substring(9, 14);

		R2index = Integer.parseInt(register2, 2);
		


		String register3 = instruction.substring(14, 19);

		R3index = Integer.parseInt(register3, 2);
		


		String shamttemp = instruction.substring(19, 32);

		shamt = Integer.parseInt(shamttemp, 2);

	}

}