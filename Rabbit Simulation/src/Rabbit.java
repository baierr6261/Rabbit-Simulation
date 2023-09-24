/**
 * Reid Baier
 * CS 245 - Advanced Programming and Data Structures
 * Created 2/24/22
 * 
 * This file is to model a rabbit. Which the data will be used for
 * the RabbitSimulation.java file.
 *
 */

public class Rabbit 
{
	private String gender;
	private int age; // age will be in days
	private boolean pregnant; // only females can be pregnant and 7 days need to pass to be pregnant again
	private int lastPregnancy; // determines how many days past since last pregnancy
	private int gestationLimit; // determines how long the doe has been pregnant until the doe can give birth
	private int gestationPeriod; // how long a rabbit is pregnant (28-32 days)

	
	public Rabbit(String gender, int age, boolean pregnant, 
			int lastPregnancy, int gestationLimit, int gestationPeriod)
	{
		this.gender = gender;
		this.age = age;
		this.pregnant = pregnant;
		this.lastPregnancy = lastPregnancy;
		this.gestationLimit = gestationLimit;
		this.gestationPeriod = gestationPeriod;
	}

	public String getGender() 
	{
		return gender;
	}

	public void setGender(String gender) 
	{
		this.gender = gender;
	}

	public int getAge() 
	{
		return age;
	}

	public void setAge(int age) 
	{
		this.age = age;
	}
	
	public boolean isPregnant() 
	{
		return pregnant;
	}

	public void setPregnant(boolean pregnant) 
	{
		this.pregnant = pregnant;
	}

	public int getGestationLimit() 
	{
		return gestationLimit;
	}

	public void setGestationLimit(int gestationLimit) 
	{
		this.gestationLimit = gestationLimit;
	}

	public int getLastPregnancy() 
	{
		return lastPregnancy;
	}

	public void setLastPregnancy(int lastPregnancy) 
	{
		this.lastPregnancy = lastPregnancy;
	}

	public int getGestationPeriod() 
	{
		return gestationPeriod;
	}

	public void setGestationPeriod(int gestationPeriod) 
	{
		this.gestationPeriod = gestationPeriod;
	}
}
