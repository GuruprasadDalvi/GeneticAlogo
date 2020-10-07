import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * main
 */
public class main {

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Your String");
        String targeString=sc.nextLine();
        //Initiating Population Of 10 DNAs
        population pop = new population(targeString);
        //Staring Main CrossOver 
        pop.doCrossOver();
        
    }
}
















/**
 * DNA
 */
class DNA {
    int targetLen;
    float fitness;
    String targetString;
    String gene;
    DNA(int targetLen,String targetString){
        this.gene="";
        this.targetLen=targetLen;
        this.targetString=targetString;
        genrateString();
        fitness=getFittness();
    }
    void genrateString(){
        //Genrating Random String As GENE
        for (int i = 0; i < this.targetLen; i++) {
            int c= ThreadLocalRandom.current().nextInt(65,123);
            //Replacing None Requried chrecters With SPACE and '.' 
                if(c>90&&c<97){
                    if(c%2==0){
                        c=32;
                    }
                    else{
                        c=46;
                    }
                }
            this.gene=this.gene+(char)c;
        }
    }
    float getFittness(){
        //Calculating Fitness by comparing Each char of Target String And Gene
        int score=0;
        for (int i = 0; i < this.targetLen; i++) {
            char a=this.targetString.charAt(i);
            char b=this.gene.charAt(i);
            if (a==b){
                score++;
            }
        }
        return ((score)*100.00f)/this.targetLen;
    }
    
    DNA crossOver(DNA anotherParent){
        DNA child=new DNA(this.targetLen, this.targetString);
        String ChildDNA;
        int mid;
        mid=(int)this.targetLen/2;
        ChildDNA=this.gene.substring(0, mid)+anotherParent.gene.substring(mid);
        child.gene=ChildDNA;
        child=child.mutate(child);
        child.fitness=child.getFittness();
        //System.out.println(ChildDNA+" "+Float.toString(child.fitness));
        return child;
    }

    DNA mutate(DNA Child){
        String newDna="";
        for (int i = 0; i < this.targetLen; i++) {
            if(new Random().nextInt(100)==28){
                int c= ThreadLocalRandom.current().nextInt(65,123);
                if(c>90&&c<97){
                    if(c%2==0){
                        c=32;
                    }
                    else{
                        c=46;
                    }
                }
                newDna=newDna+(char)(c);
            }
            else{
                newDna=newDna+Child.gene.charAt(i);
            }
        }
        Child.gene=newDna;
        return(Child);
    }
}


















/**
 * population
 */
class population {
    List<DNA> population,matingPool;
    String targetString;
    population(String targetString){
        this.targetString=targetString;
        this.population=new ArrayList<DNA>();
        genratePopulation();
    }

    void genratePopulation(){
        for (int i = 0; i < 20; i++) {
            DNA newDna=new DNA(this.targetString.length(),this.targetString);
            population.add(newDna);
        }
    }

    void makeMatingPool(){
        //Making Mating Pool
        this.matingPool=new ArrayList<DNA>();
        for (int i = 0; i < this.population.size(); i++) {
            if(this.population.get(i).fitness>0){
                int n=(int)(this.population.get(i).fitness);
                for (int j = 0; j < n; j++) {
                    matingPool.add(this.population.get(i));
                    
                }
            }
            else{
                matingPool.add(this.population.get(i));
            }
        }
    }

    void doCrossOver(){
        int gen=1;
        float maxFitness=0;
        String BestFit="";
        while(maxFitness!=100){
            makeMatingPool();
            List<DNA> newPopulation=new ArrayList<DNA>();
        for (int i = 0; i < 100; i++) {
            DNA a=this.matingPool.get(new Random().nextInt(this.matingPool.size()));
            DNA b=this.matingPool.get(new Random().nextInt(this.matingPool.size()));
            DNA child=a.crossOver(b);
            newPopulation.add(child);
            if(child.fitness>maxFitness){
                maxFitness=child.fitness;
                BestFit=child.gene;
                if(maxFitness==100){
                    System.out.println("Best Fit: '"+BestFit+"' Best Fitness: '"+maxFitness+"%', Current: "+child.gene+" "+Float.toString(child.fitness)+"% Genratin: "+gen);
                    System.out.println("Final: "+child.gene);
                    break;
                }
            }
            System.out.println("Best Fit: '"+BestFit+"' Best Fitness: '"+maxFitness+"%', Current: "+child.gene+" "+Float.toString(child.fitness)+"% Genratin: "+gen);
            
        }
        this.population=newPopulation;
        gen++;
        }
    }
    
}