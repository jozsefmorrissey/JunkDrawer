#include "camelCaser_tests.h"
#include "camelCaser.h"

/*
 * Testing function for various implementations of camelCaser.
 *
 * @param  camelCaser   A pointer to the target camelCaser function.
 * @return              Correctness of the program (0 for wrong, 1 for correct).
 */
int test_camelCaser (char ** (* camelCaser)(char *))
{
    // TODO: Return 1 if the passed in function works properly; 0 if it doesn't.
	char * trial = "Testing function for various impleme@$%&*()!@$%^&*()_+{}|:\">?<,/;'[]\\=-0ntations of camelCaser..   How about a fresh bottle of scotch. Today is a good day to die. I LIKE SKITTLES IN THE RAIN.....pOOp is AcumulatED      durING TiMes of hardship. some more non-sense for my pleasure. i like to dance in the rain on tuesdays. Suddenly, on the sand berm 100 meters ahead, an Iraqi soldier stands. Troy stops in his tracks, out of breath, and stares at the figure on the berm. The Iraqi flutters a white flag over his head, then puts it down and picks up a gun. Troy turns around, we see his face for the first time. perfume smells like shit when worn naked. THIS IS ALL CAPITALIZED BECAUSE APARENTLY I HAVE TROUBLE WITH THAT. this does not end in a period";
	
	
	char ** retVal = camelCaser(trial);
	char ** ritVal = tcamel_caser(trial);

	int i = 0;
	while(retVal[i] && ritVal[i]){
			//printf("%d\t%s\n", strcmp(ritVal[i], retVal[i]), ritVal[i]);
		if(strcmp(ritVal[i], retVal[i])){
			destroy(retVal, ritVal, trial);
			return 0;
		}
		i++;
	}
	if(retVal[i] || ritVal[i]){
		destroy(retVal, ritVal, trial);
		return 0;
	}

	char ** nada = camelCaser(NULL);
	if(nada != NULL)
		return 0;

	destroy(retVal, ritVal, trial);
    return 1;
}

void destroy(char ** m, char ** n, char * mm){
	int i = 1;
	free(m[0]);
	free(n[0]);
	free(m);
	free(n);

}


char ** tcamel_caser(char * string) {
    // TODO: Implement this function.
	int numStrings = 0;

	char ** fml = stringCheese(string, &numStrings);

	return killWhitty(squareWave(fml, numStrings), numStrings);
} 


char ** tstringCheese(char * input, int * numStrings){
	char * string = malloc(strlen(input) +1);
	strcpy(string, input);

	char ** strings = malloc(strlen(string)*8 + 8);
	strings[0] = strtok(string, ".");

	int index = (int)strlen(strings[0]) + 1;	
	for(int i = 1; strings[i-1] != NULL; i++){
		if(index < (int)strlen(input) /*- 1*/ && input[index] == '.'){
			strings[i] = "";
		}
		else
			strings[i] = strtok(NULL, ".");
		*numStrings = i;
		if(index < (int)strlen(input) &&strings[i-1] != NULL){

			index += (int)strlen(strings[i]) + 1;
		}
	} 
	if(input[(int)strlen(input) - 1] != '.'){
		strings[(*numStrings) - 1] = NULL;
		*numStrings-=1;
	}
    return strings;
}

char ** tsquareWave(char ** strings, int numStrings){
	for(int i = 0; i < numStrings; i++){

		for(int j = 0; j < strlen(strings[i]); j++){
			if(strings[i][j] > 'A' - 1 && strings[i][j] < 'Z' + 1)
				strings[i][j] += 32;
		}
	}


	return strings;
}

char ** tkillWhitty(char ** strings, int numStrings){

	int charCount = 0;
	int whiteCount = 0;
	int removed = 0;

	for(int i = 0; i < numStrings; i++){
		int charCount = 0;
		int whiteCount = 0;
		int removed = 0;
		while(strings[i][0] != '\0' && strings[i][charCount] != 0){
				strings[i][whiteCount++] = strings[i][charCount];
			if(strings[i][charCount++] == ' '){
				/*if(strings[i][charCount] == ' '){
					shiftArray(strings, i, &numStrings);
					i--;
					break;
				}*/
				whiteCount--;
				if(strings[i][charCount] > 'a' - 1 && strings[i][charCount] < 'z' + 1 && whiteCount != 0)
					strings[i][charCount] -= 32;
				removed++;
			}
		}
		if(strings[i][0] != '\0')	
			strings[i][whiteCount] = '\0';
	}
	return strings;
}


void tshiftArray(char ** bastard, int index, int * numStrings){
	for(int i = index; bastard[i + 1]; i++){
		bastard[i] = bastard[i + 1];
		index++;
	}
	free(bastard[index]);
	bastard[index] = NULL;
	*numStrings -= 1;
}




