package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class CPU{
    private String[] memory;
    private int accumulator;
    private int programCounter;
    private int f_flag;
    private boolean prog;

    public CPU(){
        memory = new String[256];
        programCounter =0;
        accumulator = 0;
        prog = false;
        f_flag = 0;
    }
    public int getPC(){
        return programCounter;
    }
    public boolean getProg(){
        return prog;
    }
    public String[] getMemory(){
        return memory;
    }

    public boolean START(){
        programCounter++;
        prog = true;
        return prog;
    }

    public void LOAD(int x){
        accumulator = x;
        programCounter++;
    }

    public void LOADM(int i){
        if(i<256){
            accumulator = Integer.parseInt(memory[i]);
            programCounter++;
        }
        else{
            System.err.println("Memory cell doesn't exist.");
        }

    }

    public void STORE(int i){
        if(i<256){
            memory[i] = String.valueOf(accumulator);
            programCounter++;
        }
        else{
            System.err.println("Memory cell does not exist.");
        }
    }

    public void CMPM(int i){
        if(accumulator>Integer.parseInt(memory[i])){
            f_flag = 1;
            programCounter++;
        }
        else if(accumulator<Integer.parseInt(memory[i])){
            f_flag = -1;
            programCounter++;
        }
        else{
            f_flag = 0;
            programCounter++;
        }
    }

    public void CJMP(int x){
        if(x<256){
            if(f_flag == 1){
                programCounter = x;
            }
            else{
                programCounter++;
            }
        }
        else
            System.out.println("Memory cell does not exist.");
    }

    public void JMP(int x){
        if(x<256){
            programCounter = x;
        }
        else
            System.out.println("Memory cell does not exist.");
    }

    public void ADD(int x){
        accumulator = accumulator + x ;
        programCounter++;
    }

    public void ADDM(int i){
        if(i<256){
            accumulator = accumulator + Integer.parseInt(memory[i]);
            programCounter++;
        }
        else{
            System.err.println("Memory cell does not exist.");
        }
    }

    public void SUBM(int i){
        if(i<256){
            accumulator = accumulator - Integer.parseInt(memory[i]);
            programCounter++;
        }
        else{
            System.err.println("Memory cell does not exist.");
        }
    }

    public void SUB(int x){
        accumulator = accumulator - x ;
        programCounter++;
    }

    public void MUL(int n){
        accumulator = accumulator * n ;
        programCounter++;
    }

    public void MULM(int i){
        if(i<256){
            accumulator = accumulator * Integer.parseInt(memory[i]);
            programCounter++;
        }
        else{
            System.err.println("Memory cell does not exist.");
        }
    }

    public void DISP(){
        System.out.println(accumulator);
        programCounter++;
    }

    public boolean HALT(){
        programCounter = 0;
        prog = false;
        resetMemory();
        return prog;
    }
    public void resetMemory(){
        for(int i = 0; i<256; i++){
            memory[i]=null;
        }
    }
}

public class Midterm_20200808073 {

    public static void main(String[] args)throws IOException {
        CPU cpu = new CPU();
        read_text(cpu,"program.txt");
        String[] memo = cpu.getMemory();
        String[] temp = new String[2];
        int programCounter = cpu.getPC();
        if(memo[programCounter].equals("START")){ 
            cpu.START();
            while(cpu.getProg()){
                programCounter = cpu.getPC();
                if(memo[programCounter].length()>4){
                    temp = memo[programCounter].split(" ");
                    String str = temp[0];
                    int val = Integer.parseInt(temp[1]);
                    if(str.equals("LOAD")){
                        cpu.LOAD(val);
                    }
                    else if(str.equals("LOADM")){
                        cpu.LOADM(val);
                    }
                    else if(str.equals("STORE")){
                        cpu.STORE(val);
                    }
                    else if(str.equals("CMPM")){
                        cpu.CMPM(val);
                    }
                    else if(str.equals("CJMP")){
                        cpu.CJMP(val);
                    }
                    else if(str.equals("JMP")){
                        cpu.JMP(val);
                    }
                    else if(str.equals("ADD")){
                        cpu.ADD(val);
                    }
                    else if(str.equals("ADDM")){
                        cpu.ADDM(val);
                    }
                    else if(str.equals("SUBM")){
                        cpu.SUBM(val);
                    }
                    else if(str.equals("SUB")){
                        cpu.SUB(val);
                    }
                    else if(str.equals("MUL")){
                        cpu.MUL(val);
                    }
                    else if(str.equals("MULM")){
                        cpu.MULM(val);;
                    }
                    else{
                        System.out.println("SYNTAX ERROR!");
                        cpu.HALT();
                    }
                }
                else{
                    String str = memo[programCounter];
                    if(str.equals("DISP")){
                        cpu.DISP();
                    }
                    else {
                        cpu.HALT();
                    }
                }


            }
        }
        else{
            System.out.println("Program does not started.");
        }

    }
    public static void read_text(CPU cpu,String direction)throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(direction));
        String[] array = new String[2];
        String x;
        int i = 0;
        while ((x = reader.readLine()) != null) {
            array = x.split(" ",2);
            cpu.getMemory()[i] = array[1];
            i++;
        }
        reader.close();
    }
}
