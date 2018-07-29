/**
 * @file word_counter.cpp
 * Implementation of the WordFreq class.
 *
 * @author Chase Geigle
 * @date Spring 2011
 * @date Spring 2012
 */
#include <iostream>

using std::vector;
using std::pair;
using std::string;
using std::ifstream;
using std::istringstream;
using std::cout;
using std::endl;

template <template <class K, class V> class Dict>
WordFreq<Dict>::WordFreq( const string & infile ) : filename( infile ) { 
    /* nothing */
}

template <template <class K, class V> class Dict>
vector< pair<string, int> > WordFreq<Dict>::getWords( int threshold ) const {

   TextFile infile( filename );

    vector< pair<string, int> > ret;
    /**
     * @todo Implement this function.
     * @see char_counter.cpp if you're having trouble.
     */

	Dict<string, int> hasheeshTable(256);

	while(infile.good())
	{

		string word = infile.getNextWord();
		hasheeshTable[word]++;
	}

	for(auto it = hasheeshTable.begin(); it != hasheeshTable.end(); it++)
	{

		if(it->second >= threshold)
			ret.push_back(*it);
	}

    return ret;
}



 /*   Dict<char, int> hashTable(256);
    while( infile.good() ) {
        string word = infile.getNextWord();
        for( size_t i = 0; i < word.length(); i++ )
            hashTable[ word[i] ]++;
    }
    vector< pair<char, int> > ret;
    // we iterate over the hash tables using iterators: it->first will give
    // us the key, it->second will give us the value. it++ moves to the
    // next (key, value) pair in the HashTable.
    typename Dict<char, int>::iterator it;
    for( it = hashTable.begin(); it != hashTable.end(); it++ ) {
        if( it->second >= threshold )
            ret.push_back( *it );
    }
    return ret;*/
