package computerArchitecture;

public class Simulation {
	private Memory mem;
	private RegisterFile reg;
	private ALU Alu;
	private String[] instructions;
	private Instructions[] pipelineInst;
	int count;
	private String currDecode;
	private Instructions toDecode;
	private Instructions currExecute;
	private Instructions currWriteBack;
	private Instructions currMem;
	int countFetch;
	int countDecode;
	int countExecute;
	int countMem;
	int countWriteBack;
	int pipelineCount;
	int decodePointer;
	RegisterFile HazardRegisters;
	Boolean flush;
	Boolean jumpFlush;
	Boolean Fetch;
	Boolean Decode;
	Boolean Execute;
	Boolean Mem;
	Boolean check;
	Boolean checkDecode;
	Boolean checkExec;

	

	public Simulation() {
		mem = new Memory();
		reg = new RegisterFile();
		Alu = new ALU();
		instructions = new String[4];
		pipelineInst = new Instructions[4];
		count = 0;
		countFetch = 0;
		countDecode = 0;
		countExecute = 0;
		countMem = 0;
		countWriteBack = 0;
		decodePointer = 0;
		pipelineCount = 0;
		currDecode = null;
		currExecute = null;
		currMem = null;
		currWriteBack = null;
		toDecode = null;
		HazardRegisters=new RegisterFile();
		flush=false;
		Fetch=true;
		Decode=true;
		Execute=true;
		Mem=true;
		check=false;
		checkDecode=false;
		checkExec=false;
		jumpFlush = false;
	}

	public void fetch() {
		int pc = reg.getPc();
		System.out.println("Input to fetch stage: PC "+pc);
		String currentInstruction = mem.readFromMem(pc);
		instructions[count] = currentInstruction;
		count++;
		reg.setPc(++pc);
		countFetch++;
	
	
	
	}

	public void decode() {

		currDecode = instructions[decodePointer];
		decodePointer++;

		String opcode = currDecode.substring(0, 4);

		// control signals
		switch (opcode) {
		case "0000":
			toDecode = new RFormat(reg.getPc(),currDecode);
			break;
		case "0001":
			toDecode = new RFormat(reg.getPc(),currDecode);
			break;
		case "0010":
			toDecode = new IFormat(reg.getPc(),currDecode);
			break;
		case "0011":

			toDecode = new IFormat(reg.getPc(),currDecode);
			break;
		case "0100":
			toDecode = new IFormat(reg.getPc(),currDecode);
			break;
		case "0101":
			toDecode = new IFormat(reg.getPc(),currDecode);
			break;
		case "0110":
			toDecode = new IFormat(reg.getPc(),currDecode);
			break;
		case "0111":
			toDecode = new JFormat(reg.getPc(),currDecode);
			break;
		case "1000":
			toDecode = new RFormat(reg.getPc(),currDecode);
			break;
		case "1001":
			toDecode = new RFormat(reg.getPc(),currDecode);
			break;
		case "1010":
			toDecode = new IFormat(reg.getPc(),currDecode);
			break;
		case "1011":
			toDecode = new IFormat(reg.getPc(),currDecode);
			break;
		}
		pipelineInst[pipelineCount] = toDecode;
		pipelineCount++;
		toDecode.decode();
		toDecode.getControlSignals();
		if (toDecode instanceof RFormat) {
			RFormat temp = (RFormat) toDecode;
			temp.readData1 = (reg.getRegister())[temp.R2index];
			temp.readData2 = (reg.getRegister())[temp.R3index];

		

		}
		if (toDecode instanceof IFormat) {

			IFormat temp = (IFormat) toDecode;
			temp.readData1 = (reg.getRegister())[temp.R1index];
			temp.readData2 = (reg.getRegister())[temp.R2index];
			
		}
		toDecode.stage = "execute";
		countDecode++;
		System.out.println("Input to decode stage:PC "+reg.getPc()+" current Instruction: "+currDecode);


	}

	public void execute() {

		// currExecute=toDecode;

		if (currExecute instanceof RFormat) {
			RFormat temp = (RFormat) currExecute;
			String AluOp = temp.controlSignal.AluOp;// get from dareen
			// ADD SUB
			if (temp.opcode.equals("0000") || temp.opcode.equals("0001")) {
				temp.readData1 = Alu.execute(temp.readData2, temp.readData3, AluOp);
			} else {
				// SLL SRL
				if (temp.opcode.equals("1000") || temp.opcode.equals("1001")) {
					temp.readData1 = Alu.execute(temp.readData2, temp.shamt, AluOp);
				}
			}
			System.out.println("Input to execute stage:PC "+temp.ID+" Read data 1: "+temp.readData1+" Read data 2: "+temp.readData2 +" Write Register: "+temp.R1index);

		}
		// jump
		if (currExecute instanceof JFormat) {
			JFormat temp = (JFormat) currExecute;
			String AluOp = temp.controlSignal.AluOp;
			int pcTemp = 0b11110000000000000000000000000000 & reg.getPc();
			pcTemp = pcTemp >>> 28;
			temp.AluResult = Alu.execute(reg.getPc(), temp.address, AluOp);
			//reg.setPc(temp.AluResult); // jump takes place,pc updated
			// flush pipeline
			temp.jumpFlag=true;
			System.out.println("Input to execute PC [31:28] : "+pcTemp+" Address: "+temp.address );

		}

		// IFormat
				if (currExecute instanceof IFormat) {
					IFormat temp = (IFormat) currExecute;
					String AluOp = temp.controlSignal.AluOp;
					//BNE
					if (temp.opcode.equals("0100")) {
						int tempValue = Alu.execute(temp.readData1, temp.readData2, AluOp);
						if (!Alu.zeroFlag) {
							temp.AluResult = Alu.execute(reg.getPc(), temp.Immediate, "000");
							temp.branch=true;
							  // BNE takes place
//							 reg.setPc(temp.AluResult);
//							 mem.setNumberOfClockCycles((mem.clockCycles(mem.getCount()-temp.Immediate)));
						}

					} else {
						// MULI ADDI ANDI ORI LW SW
						temp.AluResult = Alu.execute(temp.readData2, temp.Immediate, AluOp);
					}
					System.out.println("Input to execute stage:PC "+temp.ID+" Read data 1: "+temp.readData1+" Read data 2:"+temp.readData2 +" Immediate:"+temp.Immediate+"Write Register: "+temp.R1index);

				}

				countExecute++;
				currExecute.stage = "memory";
			}

	public void memory() {
				// currMem=currExecute;
				if (currMem instanceof IFormat) {
					IFormat temp = (IFormat) currMem;
					if (temp.opcode.equals("1010")) {
					    temp.dataFromMem = mem.readFromMem(temp.AluResult);
						//Dareen changed this part
					}
					else {
						if(temp.opcode.equals("0100") && temp.branch==true) {
							flush =true;
							reg.setPc(temp.AluResult);
						}
					}
					// SW
					if (temp.opcode.equals("1011")) {
						if (temp.AluResult >= 1024 && temp.AluResult <= 2047) {
						    temp.readData1=temp.AluResult;
							mem.writeToMem(temp.AluResult, temp.readData1);
						}
					
					}
					boolean Falu=false;
					if(temp.AluResult==0) {
						Falu=true;
					}
					System.out.println("Input to memory stage:PC "+temp.ID+" ALU result:"+temp.AluResult+" zero flag:"+Falu+" Read data 2: "+temp.readData2);
					
				}
				if(currMem instanceof RFormat) {
					RFormat temp = (RFormat) currMem;
					System.out.println("Input to write back stage: write back register " + temp.R1index);
					boolean Falu=false;
					if(temp.AluResult==0) {
						Falu=true;
					}
					System.out.println("Input to memory stage:PC "+temp.ID+" ALU result:"+temp.AluResult+" zero flag:"+Falu+" Read data 2: "+temp.readData3);

				}
				if(currMem instanceof JFormat) {
					JFormat temp = (JFormat) currMem;
					boolean Falu=false;
					if(temp.AluResult==0) {
						Falu=true;
					}
					System.out.println("Input to memory stage:PC "+temp.ID+" ALU result:"+temp.AluResult+" zero flag:"+Falu);

				}
				currMem.stage = "writeBack";
				countMem++;
				

	}
	public void flushPipeline() {
		// remove instructions that are in instructions list that came after the bne/j
		System.out.println("FLUSH IS CALLED");
		instructions[0]=null;
		instructions[1]=null;
		instructions[2]=null;
		instructions[3]=null;
		pipelineInst[0]=null;
		pipelineInst[1]=null;
		pipelineInst[2]=null;
		pipelineInst[3]=null;
		count=0;
		decodePointer=0;
		pipelineCount=0;
	}
	public void writeback() {
		// currWriteBack =currMem;
		if (!((currWriteBack.opcode).equals("0111") || (currWriteBack.opcode).equals("0100")
				|| (currWriteBack.opcode).equals("1011"))) {
			if (currWriteBack instanceof RFormat) {
				RFormat temp = (RFormat) currWriteBack;
				if(temp.R1index==0)
					return;
				reg.getRegister()[temp.R1index] = temp.AluResult;
				System.out.println("Change in Register file R" + temp.R1index + " content is changed to " + temp.AluResult);
			}
			if (currWriteBack instanceof IFormat) {

				IFormat temp = (IFormat) currWriteBack;
				if(temp.opcode.equals("1010")) {
					System.out.println("Input to write back stage: write back register " + temp.R1index+" data from memory"+ temp.dataFromMem + " at address "+ temp.AluResult);

				}else {
				System.out.println("Input to write back stage: write back register " + temp.R1index);
				}
				if(temp.R1index==0)
					return;
				if (temp.opcode.equals("1010")) {
					reg.getRegister()[temp.R1index] = Integer.parseInt(temp.dataFromMem, 2);
					System.out.println(
							"Change in Register file R" + temp.R1index + " content is changed to " + Integer.parseInt(temp.dataFromMem, 2));

				} else {
					reg.getRegister()[temp.R1index] = temp.AluResult;
					System.out.println("Change in Register file R" + temp.R1index + " content is changed to " + temp.AluResult);
				}
			}
		}
//		instructions[0] = null;
//		pipelineInst[0] = null;
//		for (int i = 1; i<4; i++) {
//			if(instructions[i]!=null) {
//				instructions[i-1]=instructions[i];
//				pipelineInst[i-1]=pipelineInst[i];
//			}
//			
//		}
		for (int i = 0; i < 3; i++) {
			if (instructions[i + 1] != null) {
				instructions[i] = instructions[i + 1];
				pipelineInst[i] = pipelineInst[i + 1];
			}
		}
		instructions[3] = null;
		pipelineInst[3] = null;
		if(!flush) {
		count--;
		decodePointer--;
		pipelineCount--;
		}else {
			if(currWriteBack instanceof IFormat) {
				IFormat temp = (IFormat) currWriteBack;
				if(temp.branch) {
			      flush=false;
				}
			}
			if(currWriteBack instanceof JFormat) {
				JFormat temp = (JFormat) currWriteBack;
				if(temp.jumpFlag) {
			      jumpFlush=false;
				}
			}
		}
		countWriteBack++;
		currWriteBack=null;
	}
	public boolean isEmpty() {
		int count=0;
		boolean countF=false;
		for(int i=0 ; i<4 ; i++) {
			if(pipelineInst[i]!=null) {
				count++;
			}
		}
		if(count==1) {
		countF=true;	
		}
		return countF;
		
		
	}

	public void simulate() {
		boolean executeSwitch = true;
        int instDecode=0;
        int instExecute=0;
        int FetchCount=0;
        
		for (int i = 1; i <= mem.getNumberOfClockCycles(); i++) {
			
				
			System.out.println( "flag  " + flush);
			System.out.print("Clock Cycle: " + i+"   ");
			System.out.println("PC"+reg.getPc());
			if(flush || jumpFlush) {
				flushPipeline();
				currExecute=null;
				currDecode=null;
				toDecode=null;
				currMem=null;
				//flush=false;
			}
			if(reg.getPc()==mem.getCount() && check==false) {
				FetchCount=i;
				Fetch=false;
				check=true;
			}
			
			boolean flag = false;
			int pcVal=reg.getPc();
			if (i % 2 == 1 && countFetch < mem.getCount() && pcVal<mem.getCount()) {
				fetch();
				System.out.println("Fetch : Instruction " + reg.getPc());
				flag = true;
			} else {
				System.out.println("Fetch : ");
			}
			//DECODE
			int temp = FetchCount+2;
			if( Fetch==false && i==temp && checkDecode==false) {
				Decode = false;
				System.out.println("Decode :");
				checkDecode=true;
			}else {
			if (i % 2 == 0 && countDecode < mem.getCount()) {
				decode();
				System.out.println("Decode : Instruction " + toDecode.ID + " " + toDecode.instruction);
				instDecode=toDecode.ID;
			} else {
				if (currDecode != null && countDecode > 0 && countDecode < mem.getCount())
					System.out.println("Decode : Instruction " + instDecode + " " + toDecode.instruction);
				else {
					if (countDecode == mem.getCount()) {

						System.out.println("Decode : Instruction " +instDecode + " " + toDecode.instruction);
						countDecode++;

					} else {
						System.out.println("Decode :");
					}
				}
			}
			}
			int temp1 = FetchCount + 4;
			if( Decode==false && i==temp1 && checkExec==false) {
				Execute = false;
				System.out.println("Execute :");
				checkExec=true;
			}
			else {
			if (i % 2 == 0 && i >= 4 && countExecute < mem.getCount() && currExecute !=null) {
				execute();
				executeSwitch = false;
				System.out.println("Execute : Instruction " + currExecute.ID + "   " + currExecute.instruction);
				instExecute=currExecute.ID;
			} 
			else {
				if (currExecute != null && countExecute > 0 && countExecute < mem.getCount()) {
					executeSwitch = true;
					System.out.println("Execute : Instruction " + instExecute + "   " + currExecute.instruction);
				} else {
					if (countExecute == mem.getCount()) {
						System.out.println("Execute : Instruction " + instExecute + "   " + currExecute.instruction);
						countExecute++;
					} else {
						System.out.println("Execute : ");
					}
				}
			  }
			}
			
			if (i % 2 == 0 && i >= 6 && !flag && countMem < mem.getCount()  && currMem != null) {
				memory();
				System.out.println("Memory : Instruction " + currMem.ID + " " + currMem.instruction);

			} else {
				System.out.println("Memory : ");
			}
			if (i % 2 == 1 && i >= 7 && countWriteBack < mem.getCount() && currWriteBack!=null) {
				
				System.out.println("WriteBack : Instruction " + currWriteBack.ID + " " + currWriteBack.instruction);
				writeback();
				System.out.println("----------------------END----------------------");


			} else {
				System.out.println("WriteBack :  ");
				System.out.println("----------------------END----------------------");
			}
			for (int k = 0; k < pipelineInst.length; k++) {
				// System.out.println("pp"+pipelineInst[k]);
				if (pipelineInst[k] != null) {
					switch (pipelineInst[k].stage) {
					case "execute":
						if (executeSwitch && countDecode > 0)
							currExecute = pipelineInst[k];
					//	System.out.println("DAREEEEN"+currExecute.AluResult +""+currExecute.opcode+""+currExecute.instruction);
						break;
					case "memory":
						currMem = pipelineInst[k];
						break;
					case "writeBack":
						currWriteBack = pipelineInst[k];
						break;
					}
				}
			}

		}
		reg.printReg();
		mem.printMem();

	}
 public void simulateHabd(Instructions lastIns) {
    	//fetching
    	String x=lastIns.stage;
    	
       switch(x) {
         
       case "execute": execute();
		System.out.println("Execute : Instruction " + lastIns.ID + "   " + lastIns.instruction); break;
		
       case "fetch" : 
    	   fetch();
   		System.out.println("Fetch : Instruction " + reg.getPc()); break;
   		
       case "decode" : 
    	   int id  = lastIns.ID;
    	   decode();
			System.out.println("Decode : Instruction " +id + " " + toDecode.instruction); break;
			
       case "memory" : 	
    	   memory();
			System.out.println("Memory : Instruction " + currMem.ID + " " + currMem.instruction); break;
			
		default: writeback(); return;
		
       }    
       
    	
    	
    	
	}

	public static void main(String[] args) {
		Simulation test = new Simulation();
		test.simulate();

	}

}
