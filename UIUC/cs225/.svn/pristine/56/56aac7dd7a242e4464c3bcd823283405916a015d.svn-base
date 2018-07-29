/**
 * @file cartalk_puzzle.cpp
 * Holds the function which solves a CarTalk puzzler.
 *
 * @author Matt Joras
 * @date Winter 2013
 */

#include <fstream>

#include "cartalk_puzzle.h"

using namespace std;

/**
 * Solves the CarTalk puzzler described here:
 * http://www.cartalk.com/content/wordplay-anyone.
 * @return A vector of "StringTriples" (a typedef'd std::tuple, see top of 
 * cartalk_puzzle.h). Returns an empty vector if no solutions are found.
 * @param d The PronounceDict to be used to solve the puzzle.
 * @param word_list_fname The filename of the word list to be used.
 */
vector< StringTriple > cartalk_puzzle(PronounceDict d,
                                      const string& word_list_fname)
{
    /* Your code goes here! */
        StringTriple triple;
        vector<StringTriple> vect;
       
        ifstream words(word_list_fname);
        string one, two, og;
        if(words.is_open())
        {
         /* Reads a line from words into word until the file ends. */
                while(getline(words, og))
                 {
                        //create word1 and word2
                        if (og.size() > 1)
                        {
                                two = og[0] + og.substr(2, og.size());
                                one = og.substr(1, og.size());
                       
                                if (d.homophones(one, og) && d.homophones(two, og))    
                                {
                                        triple = std::make_tuple(one, og, two);
                                        vect.push_back(triple);
                                }              
                        }              
                   }
 
        }
    return vect;
}


