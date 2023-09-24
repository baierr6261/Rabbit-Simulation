/**
 * Reid Baier
 * CS 245 - Advanced Programming and Data Structures
 * Created 2/24/22
 * 
 * This file is to simulate the growth of a rabbit population starting
 * with a given number of does (female rabbits) and bucks (male rabbits)
 * starting at day 0. There will be 10 trials and each trial will
 * simulate a full year.
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//used to clear the RabbitStats array after each test
import java.util.Random;
//used to determine litter size and gestation period
import java.util.Scanner;

public class RabbitSimulation
{
	/*
	 * This method is to run the RabbitSimulation program w/o having issues/errors due to the static keyword
	 */
	public void start() throws FileNotFoundException
	{
		
		/*just to remind myself about the rows/cols of the array:
		 
		 		  Does | Bucks -> this is not an actual row
		 		  initial # of rabbits
		 Trial 0: numD | numB
		 ...
		 Trial n:
		 
		 */
		
		// first row in the 2D array should just contain the initial # of rabbits
		int[][] rabbitStats = new int[3][11];
		ArrayList<Rabbit> al = new ArrayList<Rabbit>();
		
		File f = new File("src\\text.txt");
		Scanner sc = new Scanner(f);
		
		// this is to set the initial # of rabbits in the rabbitStats array
		while(sc.hasNextLine())
		{
			rabbitStats[1][0] = sc.nextInt();
			rabbitStats[2][0] = sc.nextInt();
		
			//# of trials will always be 10
			int trialCount = 0;
			
			//this loop goes through 10 trials
			while(trialCount < 10)
			{	
				int dTemp = 0;
				int bTemp = 0;
				
				dTemp = rabbitStats[1][0];
				rabbitStats[1][trialCount + 1] = dTemp;
				bTemp = rabbitStats[2][0];
				rabbitStats[2][trialCount + 1] = bTemp;
				

				//the next 2 while loops create the initial set of rabbits and update the stats array
				while(dTemp > 0)
				{
					createRabbit(al, "female");
					dTemp--;
				}
				
				while(bTemp > 0)
				{
					createRabbit(al, "male");
					bTemp--;
				}
				
				//the actual simulation
				int currentDay = 0;
				while(currentDay < 365)
				{
					simulate(al, rabbitStats, trialCount);
					currentDay++;
				}
				
				trialCount++;
				
				// this should clear the arrayList of rabbits at the end of each trial
				al.clear();
			}
			
				//prints out the array for the current test after 10 trials
				print(rabbitStats);
		}
		
		sc.close();
	}
	
	/*
	 * This method is for simulating the rabbit breeding
	 */
	public void simulate(ArrayList<Rabbit> al, int[][] stats, int trialCount)
	{	
		Random rand = new Random();
		//this is for updating all of the rabbits each day
		for(int i = 0; i < al.size() - 1; i++)
		{
			Rabbit r = al.get(i);
			// increments a rabbit's age by 1
			r.setAge(r.getAge() + 1);
			
			// checks to see if there is at least 1 female and 1 male
			if(stats[1][trialCount + 1] != 0 || stats[2][trialCount + 1] != 0)
			{

				if(r.getGender().equals("female"))
				{			
					int limit = r.getGestationLimit();
					int end = r.getGestationPeriod();
					int temp = r.getLastPregnancy();
					
					if(r.isPregnant())
					{
						r.setLastPregnancy(0);
						r.setGestationLimit(limit + 1);

						// checks to see if the doe can give breed
						if(end <= limit)
						{
							r.setPregnant(false);
							int n = rand.nextInt(5) + 3;
							int j = 0;
							while(j < n)
							{
								// determines if the rabbit is a doe or buck
								int num = rand.nextInt(2);
								if(num == 0)
								{
									createRabbit(al, "female");
									statUpdate(stats, trialCount, "female");
								}
								else
								{
									createRabbit(al, "male");
									statUpdate(stats, trialCount, "male");
								}
								
								j++;
							}
						}
					}
		
					// checks to see if the female rabbit isn't pregnant and if it has been 7 days since last pregnancy
					// if so, then 50% for the rabbit to get pregnant; otherwise increment the last pregnancy value
					else if(!r.isPregnant() && temp >= 7)
					{
						//So fun fact, as n gets closer to 0, the higher the chances an infinite loop occurs. I can kind of guess why, but not 100% sure.
						int n = rand.nextInt(100);
						if(n == 0)
						{
							r.setPregnant(true);
							r.setLastPregnancy(0);
							r.setGestationPeriod(rand.nextInt(32 - 28) + 28);
						}
						
						else
						{
							r.setLastPregnancy(temp + 1);
						}
					}
					
					else
					{
						r.setLastPregnancy(temp + 1);
					}
				}
				
				// increments lastPregnancy value if none of the above statements go through
				else
				{
					r.setLastPregnancy(r.getLastPregnancy() + 1);
					r.setGestationLimit(0);
					r.setGestationPeriod(0);
				}
			}
		}
	}
	/*
	 * This method is called when a new rabbit is born.
	 */
	public void statUpdate(int[][] stats, int trialCount, String gender)
	{
		if(gender.equals("female"))
		{
			stats[1][trialCount + 1]++;
		}
		else
		{
			stats[2][trialCount + 1]++;
		}
	}
	
	/*
	 * This method is used to create a newborn doe or buck and add it to the arraylist
	 */
	public void createRabbit(ArrayList<Rabbit> al, String gender)
	{

		Rabbit r;
		if(gender.equals("female"))
		{
			r = new Rabbit("female", 0, false, 0, 0, 0);
			al.add(r);
		}
		
		else
		{
			r = new Rabbit("male", 0, false, 0, 0, 0);
			al.add(r);
		}
	}
	
	/*
	 * This method will print out the results. It will be called after each test/trial run.
	 */
	public void print(int[][] arr)
	{
		int does = 0;
		int bucks = 0;
		int buckTotal = 0;
		int doeTotal = 0;
		int rabbitTotal = 0;
		int lastTotal = 0;
		float totalAvg = 0;
		float buckAvg = 0;
		float doeAvg = 0;
		float totalSd;
		float buckSd;
		float doeSd;
		
		int trialTemp = 0;
		
		// prints out the # of starting rabbits
		System.out.println("Starting with " + arr[1][0] + " doe(s) and "
				+ arr[2][0] + " buck(s):");
		
		while(trialTemp < 10)
		{
			does = arr[1][trialTemp + 1];
			bucks = arr[2][trialTemp + 1];
			
			rabbitTotal = does + bucks;
			
			// prints out the # of rabbits (total, female, and male) for each trial
			System.out.println("Trial " + trialTemp + ": " + rabbitTotal + 
				" is the final population of rabbits; " + 
				does + " does, " + bucks + " bucks.");
			
			lastTotal += rabbitTotal;
			doeTotal += does;
			buckTotal += bucks;
			trialTemp++;
		}
				
		totalAvg = lastTotal / 10;
		doeAvg = doeTotal / 10;
		buckAvg = buckTotal / 10;
		
		totalSd = 0;
		doeSd = 0;
		buckSd = 0;
		
		//this loop if for calculating standard deviation, and afterwards calculates the true standard deviation....hopefully
		int count = 0;
		while(count < 10)
		{
			totalSd += (float) Math.pow(((arr[1][count + 1] + arr[2][count + 1]) - totalAvg), 2);
			doeSd += (float) Math.pow(arr[1][count + 1] - doeAvg, 2);
			buckSd += (float) Math.pow(arr[2][count + 1] - buckAvg, 2);
			count++;
		}
		
		totalSd = (float) Math.sqrt(totalSd / 10);
		doeSd = (float) Math.sqrt(doeSd / 10);
		buckSd = (float) Math.sqrt(buckSd / 10);
		
		// prints out the avg # of does and bucks per trial(?)
		System.out.print("Average number of rabbits: ");
		System.out.printf("%.3f", totalAvg);
		System.out.print(" with a standard deviation of ");
		System.out.printf("%.3f", totalSd);
		System.out.println();
		
		System.out.print("Average number of does: ");
		System.out.printf("%.3f", doeAvg);
		System.out.print(" with a standard deviation of ");
		System.out.printf("%.3f", doeSd);
		System.out.println();
		
		System.out.print("Average number of bucks: ");
		System.out.printf("%.3f", buckAvg);
		System.out.print(" with a standard deviation of ");
		System.out.printf("%.3f", buckSd);
		System.out.println();
		
	}
	

}
