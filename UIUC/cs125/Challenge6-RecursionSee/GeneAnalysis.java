//UIUC CS125 FALL 2014 MP. File: GeneAnalysis.java, CS125 Project: Challenge6-RecursionSee, Version: 2014-11-02T14:20:38-0600.643794355
/**
 * @author jtmorri2
 *
 */
public class GeneAnalysis{
	/** Wrapper method. Returns the length of the longest 
	 * common subsequence
	 */
	public static int score(String gene1, String gene2)
	{	int start = 0;
		/** Wrapper method. Returns the length of the longest 
		 * common subsequence
		 */
		
			char[] g1 = gene1.toCharArray();
			char[] g2 = gene2.toCharArray();
			int score = 0;
			
			score = score(g1, g2, 0);
			
			
			
			
			return score;
			
			// Hint: Use toCharArray() to convert each string to a char array.
			 // call your recursive implementation here with
			// the genes as char arrays, starting at the end of each gene.
		}

	private static int score(char[] g1, char[]g2, int start){
		int max=0, score;
		
		score = Math.max(score(g1, g2, start, 0), score(g2, g1, start, 0));
		
		if(start<Math.min(g1.length, g2.length)-1)
			max= Math.max(score, score(g1, g2, ++start));
		
		return max;
	}
		/** Implements longest common subsequence recursive search
	The recursive case is defined as
						S(i-1, j)
	S(i,j) = max {		S(i,j-1)
						S(i-1,j-1)
						S(i-1,j-1) +1 if gene1[i] = gene2[j]

	NB  0<=i < gene1.length
	    0<=j < gene2.length

	You need to figure out the base case.
		 * */
//		define a private recursive Class method 'score' that 
//		returns an integer the score.
//		The method should take four parameters- 
//		two char arrays and two integers (gene1,gene2,i,j)
//		i and j are the indices into gene1 and gene2 respectively.
	private static int score(char[] g1, char[] g2,int i,int j){
		int score=0;
		int max = 0;
		int start=0;
		
		
		
		if(i<g1.length && j<g2.length && g1[i]==g2[j]){	
			i++;
			score+=score(g1, g2, i, ++j)+1;
			
			return score;
		}
		
		else if(i<g1.length && j<g2.length && g1[i]!=g2[j]){
			
			max = Math.max(score, max);
			score+=score(g1, g2, i, ++j);
			return score;
		}
		
		return score;
					
	}
	}
	// Use local variables to store a recursive result so that you  do not need to calculate it again.

	// Do not use a loops.