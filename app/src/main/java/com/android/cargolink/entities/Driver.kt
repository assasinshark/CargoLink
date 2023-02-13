package com.android.cargolink.entities

/**
 *
 * data class to store the driver entity information, it has been
 * decided to create a class for future growth and better data definition
 *
 * @constructor creates a driver instance with a name, made of name and
 * lastname separated by space (" ") example: "Name LastName"
 *
 * Assumptions:
 * - Name format described above, Names will always come with
 * one name and one last name
 * - Vowels and consonants will be only the 27 english abecedary letters
 * - Name will not contain special characters like ', -, _ or anything
 * different than letters. To simplify method, will only take into account
 * letters and no characters, the vowels and consonants property
 * intializations can be easily modified to be able to handle these characters
 *
 */
data class Driver(val fullName: String) {

    private val VOWELS = setOf<Char>('a','e','i','o','u','A','E','I','O','U') //To avoid converting to lowercase
    // and then finding the vowels, will add lowercase and uppercase vowels to set.

    val firstName: String by lazy {
        fullName.substringBefore(' ')
    }

    val lastName: String by lazy {
        fullName.substringAfter(' ')
    }
    /*
    //I could do the vowel identification on a String class extension inside
    // scheduleBuilding class but as this is for now only for drivers name,
    // will leave as a lazy property, same for consonants
    val vowels: Int by lazy {
        var res = 0
        for(letter: Char in firstName.toCharArray()) {
            if(VOWELS.contains(letter))
                res += 1
        }
        res
    }



    val consonants: Int by lazy {
        firstName.length - vowels
    }
     */
}