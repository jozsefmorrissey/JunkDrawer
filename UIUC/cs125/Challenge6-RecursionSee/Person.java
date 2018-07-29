//UIUC CS125 FALL 2014 MP. File: Person.java, CS125 Project: Challenge6-RecursionSee, Version: 2014-11-02T14:20:38-0600.643794355
/**
 * @author jtmorri2
 *
 */
public class Person
{
private final String name;
private final int age;
private final char gender;
private final Person child1; //left child
private final Person child2; //right child
private static int location = 0;

public Person(String name, int age, char gender, Person c1, Person c2)
{
    this.name=name;
    this.age=age;
    this.gender=gender;
    this.child1 = c1;
    this.child2 = c2;
}

public String toString()
{
    return name+"*"+age+"*"+gender;
}

public String getName() 
{
	return name;
}

public int getAge() 
{
	return age;
}

public char getGender() 
{
	return gender;
}

public boolean equals(Person p)
{
	return (this.name.equals(p.name)) &&
			(this.age==p.age) &&
			(this.gender==p.gender);
}


public void print() 
{
   System.out.println(this);
   if(child1 != null)
       child1.print();
   if(child2 != null)
       child2.print();
   
}

public int count() // total person count including this object
{
	int count = 1;
	
	Person c1 = this.child1;
	Person c2 = this.child2;
	
	if(c1!=null)
		count+=c1.count();
	
	if(c2!=null)
		count+=c2.count();
	
	return count;
}
public int countGrandChildren() // but not greatGrandChildren
{
	long count1=1;
	long count2=1;
	boolean origin=false, grand = false;
	location++;
	String record1 = Long.toString(count1);
	String record2 = "";
	String tots = "";
	
	
	
	if(location == 1)
		origin=true;

	Person c1 = this.child1;
	Person c2 = this.child2;
	
	if(c1!=null)
		record1+=Long.toString(c1.countGrandChildren());
	else
		record1+=Integer.toString(0);
	
	if(c2!=null)
		record2+=Long.toString(c2.countGrandChildren());
	else
		record2+=Integer.toString(0);
	
	char[] rec1 = record1.toCharArray();
	char[] rec2 = record2.toCharArray();
	
	for(int i=0; i<Math.min(rec1.length, rec2.length); i++)
		tots += rec1[i] + "" + rec2[i];
	
	if(rec2.length<rec1.length)
		tots+=record1.substring(rec2.length, rec1.length);

	if(rec2.length>rec1.length)
		tots+=record2.substring(rec1.length, rec2.length);
	
	if(tots.length()>10)
		tots = tots.substring(0, 9);
	
	//System.out.println(tots);
	
	if(origin && tots.length()>3){
		char[] sum = tots.toCharArray();
		int count=0;
		for(int i=3; i<7;i++)
			count += Character.getNumericValue(sum[i]);
		//System.out.println("The count: " + count);
		location = 0;
		origin = false;
		return count;
		
	}
	else if(origin){
		location = 0;
		return 0;
	}
	
	return (int) Long.parseLong(tots);
	//System.out.println("ct3: " + count1);
	//System.out.println("ct2: " + count2);
}

public int countMaxGenerations()
{
	int count=0, count1=0, count2=0; 
	
	Person c1 = this.child1;
	Person c2 = this.child2;
	
	
	
	if(c1 == null && c2 == null){
	count=1;
	}		
	if(c1 != null){
		count1 = c1.countMaxGenerations() + 1;
	}	
	if(c2 != null){
		count2 = c2.countMaxGenerations() + 1;
	}//System.out.println(record);
	
	count += Math.max(count1, count2);
	
	//System.out.println(count);
	
	return count;
}

public int countGender(char gen)
{
	int count=0, count1=0, count2=0; 
	
	Person c1 = this.child1;
	Person c2 = this.child2;
	
	if(this.gender==gen)
		count++;
	
	if(c1 != null){
		count += c1.countGender(gen);
	}	
	if(c2 != null){
		count += c2.countGender(gen);
	}//System.out.println(record);
	
	//System.out.println(count);
	
	return count;
}


public Person search(String name, int maxGeneration)
{	
	{	
		boolean origin = false;
		Person c1 = this.child1;
		Person c2 = this.child2;
		Person temp1 = null;
		Person temp2 = null;
		
		if(location == 0){
			location++;
			origin =true;
		}
		//System.out.println(name.equals(this.getName()));
		if(name.equals(this.getName()))
			return this;
		
		if(c1!=null)
			temp1 = c1.search(name, maxGeneration);
		
		if(c2!=null)
			temp2 = c2.search(name, maxGeneration);
		
		if(origin && temp1!=null)
		//System.out.println(this.countMaxGenerations() - temp1.countMaxGenerations());
		
		if(origin && temp2!=null)
			System.out.println(this.countMaxGenerations() + 4 - temp2.countMaxGenerations());
		
		if(origin && temp1!=null && maxGeneration>=this.countMaxGenerations() - temp1.countMaxGenerations()){
			location = 0;
			return temp1;
		}
		else if(temp1!=null && !origin)
			return temp1;
		
		if(origin && temp2!=null && maxGeneration>=this.countMaxGenerations() - temp2.countMaxGenerations()){
			location = 0;
			return temp2;
		}
		else if(temp2!=null && !origin)
			return temp2;
		
		if(origin)
			location = 0;
		
		return null;
	}
}

} // end of class
