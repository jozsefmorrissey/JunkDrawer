#include "camelCaser.h"

/*
 * Converts a each sentence in a string of multiple sentences to camelCase.
 *
 * @param   string  A string adhering to the above description.
 * @return          An array of strings, each element of which is a sentence in camelCase.
 *                  Caller is expected to free the returned array of strings.
 *                  Returns NULL if parameter is NULL.
 */
char ** camel_caser(char * string) {
    // TODO: Implement this function.
	int numStrings = 0;
	if(string == NULL)
		return NULL;

	char ** fml = stringCheese(string, &numStrings);
	


	return killWhitty(squareWave(fml, numStrings), numStrings);
} 


char ** stringCheese(char * input, int * numStrings){
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

char ** squareWave(char ** strings, int numStrings){
	for(int i = 0; i < numStrings; i++){

		for(int j = 0; j < strlen(strings[i]); j++){
			if(strings[i][j] > 'A' - 1 && strings[i][j] < 'Z' + 1)
				strings[i][j] += 32;
		}
	}


	return strings;
}

char ** killWhitty(char ** strings, int numStrings){

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


void shiftArray(char ** bastard, int index, int * numStrings){
	for(int i = index; bastard[i + 1]; i++){
		bastard[i] = bastard[i + 1];
		index++;
	}
	free(bastard[index]);
	bastard[index] = NULL;
	*numStrings -= 1;
}




