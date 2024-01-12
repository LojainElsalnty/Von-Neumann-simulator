# 1.1 Memory Architecture
a) Architecture: Von Neumann


• Von Neumann Architecture is a digital computer architecture whose design is based on the concept of stored program computers where program data and instruction data are stored in the same memory.


b) Memory Size: 2048 * 32


• The main memory is word addressable.


• Addresses from 0 to 1023 contain the program instructions.


• Addresses from 1024 to 2048 contain the data.



c) Registers: 33


• Size: 32 bits


• 31 General-Purpose Registers (GPRS)


– Names: R1 to R31 • 1 Zero Register


– Name: R0


– Hard-wired value “0” (cannot be overwritten by any instruction).
 • 1 Program Counter


– Name: PC


– A program counter is a register in a computer processor that contains the address (location) of the instruction being executed at the current time.


– As each instruction gets fetched, the program counter is incremented to point to the next instruction to be executed.

# 1.2 Instruction Set Architecture


a) Instruction Size: 32 bits 


b) Instruction Types: 3


- R-Format


OPCODE R1 R2 R3 SHAMT
  4    5  5  5   13


-I-Format


OPCODE R1 R2 IMMEDIATE
  4    5  5   18


-J-Format


 OPCODE ADDRESS
   4     28


c) Instruction Count: 12
• The opcodes are from 0 to 11 according to the instructions order in the following table:


# 1.3 Datapath
a) Stages: 5


• All instructions regardless of their type must pass through all 5 stages even if they do not need
to access a particular stage.


• Instruction Fetch (IF): Fetches the next instruction from the main memory using the address in the PC (Program Counter), and increments the PC.


• Instruction Decode (ID): Decodes the instruction and reads any operands required from the register file.


• Execute (EX): Executes the instruction. In fact, all ALU operations are done in this stage.


• Memory (MEM): Performs any memory access required by the current instruction. For loads, it would load an operand from the main memory, while for stores, it would store an operand into the main memory.


• Write Back (WB): For instructions that have a result (a destination register), the Write Back writes this result back to the register file.


b) Pipeline: 4 instructions (maximum) running in parallel
• Instruction Fetch (IF) and Memory (MEM) can not be done in parallel since they access the same physical memory.


• At a given clock cycle, you can either have the IF, ID, EX, WB stages active, or the ID, EX, MEM, WB stages active.


• Number of clock cycles: 7 + ((n − 1) ∗ 2), where n = number of instructions
– Imagine a program with 7 instructions: ∗ 7+(6∗2)=19clockcycles


– You are required to understand the pattern in the example and implement it.



